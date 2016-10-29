package eu.me73.luncheon.order.impl;

import static eu.me73.luncheon.commons.LuncheonConstants.USER_ORDERS_EMPTY;
import static eu.me73.luncheon.commons.LuncheonConstants.USER_ORDERS_NOTHING_TO_STORE;
import static eu.me73.luncheon.commons.LuncheonConstants.USER_ORDERS_WRONG_USER;

import ch.qos.logback.classic.Logger;
import eu.me73.luncheon.commons.DateUtils;
import eu.me73.luncheon.commons.LuncheonConfig;
import eu.me73.luncheon.lunch.api.Lunch;
import eu.me73.luncheon.lunch.api.LunchService;
import eu.me73.luncheon.order.api.DailyReport;
import eu.me73.luncheon.order.api.DailyReportSummary;
import eu.me73.luncheon.order.api.MonthlyReport;
import eu.me73.luncheon.order.api.Order;
import eu.me73.luncheon.order.api.OrderService;
import eu.me73.luncheon.order.api.UserOrder;
import eu.me73.luncheon.repository.lunch.LunchEntity;
import eu.me73.luncheon.repository.order.OrderDaoService;
import eu.me73.luncheon.repository.order.OrderEntity;
import eu.me73.luncheon.repository.users.UserRelation;
import eu.me73.luncheon.user.api.User;
import eu.me73.luncheon.user.api.UserService;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.Collator;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServicesImpl implements OrderService {

    private final Logger LOG = (Logger) LoggerFactory.getLogger(OrderServicesImpl.class);

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("d.M.yyyy", Locale.ENGLISH);
    private static final Collator SVK_COLLATOR = Collator.getInstance(Locale.forLanguageTag("sk"));

    @Autowired
    OrderDaoService service;

    @Autowired
    LunchService lunchService;

    @Autowired
    UserService userService;

    @Autowired
    DateUtils dateUtils;

    @Autowired
    LuncheonConfig config;

    private  Map<Long, String> numberingMap = new HashMap<>();
    private final String [] soupStrings = {"1","2"};
    private final String [] mealStrings = {"1","2","3","4","5"};

    @Override
    public void save(final Order order) {
        if (Objects.nonNull(order)) {
            service.save(order.toEntity());
            if (LOG.isDebugEnabled()) {
                LOG.debug("Order {} saved.", order);
            }
        }
    }

    @Override
    public void save(final Collection<Order> orders) {
        Objects.requireNonNull(orders, "Collection cannot be null if saving required");
        orders
                .stream()
                .map(Order::toEntity)
                .forEach(orderEntity -> service.save(orderEntity));
    }

    @Override
    public Collection<Order> getAllOrders() {
        return service
                .findAll()
                .stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Order> importOrdersFromFile(final BufferedReader importFile) throws IOException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Importing orders from file {} ", importFile);
        }
        String line;
        long xid = 1;
        Collection<Order> orders = new ArrayList<>();

        while (Objects.nonNull(line = importFile.readLine())) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("Read line {}", line);
            }
            String[] s = line.split(";");
            if (s.length >= 4) {
                LocalDate date = LocalDate.parse(remakeOldDateString(s[0]), FORMATTER);
                final int meal = Integer.parseInt(s[3]);
                if ((meal != 99) && (date.getYear() >= config.getYear())) {
                    Order order = this.updateOrder(xid++, date, s[1], Integer.parseInt(s[2]), meal);
                    orders.add(order);
                    if (LOG.isTraceEnabled()) {
                        LOG.trace("Adding order {}, orders count {}", order, orders.size());
                    }
                } else {
                    if ((meal == 99) && (date.getYear() >= config.getYear())) {
                        Order order = this.updateOrder(xid++, date, s[1], Integer.parseInt(s[2]), meal);
                        if (orders.contains(order)) {
                            orders.remove(order);
                        }
                        if (LOG.isTraceEnabled()) {
                            LOG.trace("Removing order {}, orders count {}", order, orders.size());
                        }
                    }
                }
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Returning {} orders", orders.size());
        }
        return orders;
    }

    @Override
    public Collection<UserOrder> getOrdersForUser(final Long id, final LocalDate fromDate, final LocalDate toDate) {

        Collection<Lunch> lunches = lunchService.getAllBetweenDates(fromDate, toDate);

        Collection<Order> orders = service
                .findByUserAndDateGreaterThanEqualAndDateLessThanEqualOrderByDate(id, fromDate, toDate)
                .stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());

        Collection<Order> ordersWithDescription = orders
                .stream()
                .filter(order -> (Objects.nonNull(order.getDescription()) && !order.getDescription().isEmpty()))
                .collect(Collectors.toCollection(ArrayList::new));

        Collection<UserOrder> userOrders = lunches
                .stream()
                .map(lunch -> new UserOrder(
                        id,
                        lunchInOrders(orders, lunch),
                        lunch,
                        dateUtils.itsChangeable(lunch.getDate()),
                        getDescriptionInOrders(ordersWithDescription, lunch)))
                .collect(Collectors.toList());

        return userOrders;
    }

    @Override
    public String storeOrdersForUser(final Collection<UserOrder> userOrders, final User user) {
        Objects.requireNonNull(userOrders, "If storing users orders collection cannot be null");
        if (userOrders.isEmpty()) {
            LOG.warn(USER_ORDERS_EMPTY);
            return USER_ORDERS_EMPTY;
        }

        if (LOG.isTraceEnabled()) {
            LOG.trace("Saving users orders by logged user {}", user.getLongName());
        }

        ArrayList<UserOrder> userOrderArrayList = userOrders
                .stream()
//                .filter(UserOrder::isChangeable)
                .collect(Collectors.toCollection(ArrayList::new));

//        if (userOrderArrayList.isEmpty()) {
//            return "0";
//        }

        Long lunchUserId = userOrderArrayList.get(0).getUser();
        if (!user.getRelation().equals(UserRelation.POWER_USER)) {
            if (!Objects.equals(lunchUserId, user.getId())) {
                LOG.warn(USER_ORDERS_WRONG_USER);
                return USER_ORDERS_WRONG_USER;
            }
        }

        LocalDate firstDate = userOrderArrayList.get(0).getLunch().getDate();
        LocalDate lastDate = userOrderArrayList.get(userOrderArrayList.size()-1).getLunch().getDate();

        Collection<Order> orders = service
                .findByUserAndDateGreaterThanEqualAndDateLessThanEqualOrderByDate(lunchUserId, firstDate, lastDate)
                .stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());

        if (LOG.isDebugEnabled()) {
            LOG.debug("Found {} stored orders for user {} and days from {} to {}",
                    orders.size(),
                    userOrderArrayList.get(0).getUser(),
                    firstDate,
                    lastDate);
        }

        delete(orders);

        Collection<UserOrder> updatedUserOrders = userOrders
                .stream()
                .filter(UserOrder::isOrdered)
                .collect(Collectors.toList());

        if (updatedUserOrders.isEmpty()) {
            LOG.warn("Nothing to store for user {}", lunchUserId);
            return USER_ORDERS_NOTHING_TO_STORE;
        }

        Collection<Order> updatedOrders = createOrdersFromUserOrders(updatedUserOrders,user);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Found {} updated orders for user {} and days from {} to {}",
                    updatedOrders.size(),
                    userOrderArrayList.get(0).getUser(),
                    firstDate,
                    lastDate);
        }

        save(updatedOrders);
        return String.valueOf(updatedOrders.size());
    }

    private Collection<Order> createOrdersFromUserOrders(final Collection<UserOrder> updatedUserOrders, final User user) {

        ArrayList<UserOrder> userOrderArrayList = (ArrayList<UserOrder>) updatedUserOrders;

        Long userFromData = userOrderArrayList.get(0).getUser();

        Collection<LocalDate> dates = updatedUserOrders
                .stream()
                .map(this::gainDateFromUserOrder)
                .distinct()
                .collect(Collectors.toList());

        Collection<Order> orders = new ArrayList<>();

        for (LocalDate date : dates) {
            Order order = new Order();
            order.updateDate(date);
            order.updateUser(userService.getUserById(userFromData));
            order.updateSoup(findSoup(date, updatedUserOrders));
            order.updateMeal(findMeal(date, updatedUserOrders));
            order.updateDescription(findDescription(date, updatedUserOrders));
            order.updateChangedBy(user);
            orders.add(order);
        }

        return orders;
    }

    private LocalDate gainDateFromUserOrder(final UserOrder userOrder) {
        return userOrder.getLunch().getDate();
    }

    private boolean isSoup(final UserOrder userOrder) {
        return userOrder.getLunch().getSoup();
    }

    private boolean hasDescription(final UserOrder userOrder) {
        return (Objects.nonNull(userOrder.getDescription()) && !userOrder.getDescription().isEmpty());
    }

    private Lunch findMeal(final LocalDate date, final Collection<UserOrder> updatedUserOrders) {
        Optional<UserOrder> userOrderOptional = updatedUserOrders
                .stream()
                .filter(userOrder -> date.equals(gainDateFromUserOrder(userOrder)) && (!isSoup(userOrder)))
                .findFirst();
        return userOrderOptional.isPresent() ? userOrderOptional.get().getLunch() : null;
    }

    private Lunch findSoup(final LocalDate date, final Collection<UserOrder> updatedUserOrders) {
        Optional<UserOrder> userOrderOptional = updatedUserOrders
                .stream()
                .filter(userOrder -> date.equals(gainDateFromUserOrder(userOrder)) && (isSoup(userOrder)))
                .findFirst();
        return userOrderOptional.isPresent() ? userOrderOptional.get().getLunch() : null;
    }

    private String findDescription(final LocalDate date, final Collection<UserOrder> updatedUserOrders) {
        Optional<UserOrder> userOrderOptional = updatedUserOrders
                .stream()
                .filter(userOrder -> date.equals(gainDateFromUserOrder(userOrder)) && (hasDescription(userOrder)))
                .findFirst();
        return userOrderOptional.isPresent() ? userOrderOptional.get().getDescription() : null;
    }

    @Override
    public void delete(final Collection<Order> orders) {
        Objects.requireNonNull(orders, "Collection cannot be null if deleting required");
        orders
                .stream()
                .map(Order::toEntity)
                .forEach(orderEntity -> service.delete(orderEntity));
    }

    @Override
    public Order updateOrder(final Long id,
                             final LocalDate date,
                             final String pid,
                             final int soup,
                             final int meal) {
        Order order = new Order();
        order.setId(id);
        order.updateDate(date);
        order.updateUser(userService.getUserByPid(pid));
        order.updateSoup(lunchService.getLunchByDayIndex(date, soup, true));
        order.updateMeal(lunchService.getLunchByDayIndex(date, meal, false));
        return order;
    }

    @Override
    public Order getOrderForId(final Long id) {
        return fromEntity(service.findOne(id));
    }

    @Override
    public Collection<DailyReport> createReport(final LocalDate date) {

        Collection<OrderEntity> orders = service.findByDateOrderByUser(date);
        Collection<LunchEntity> soups = lunchService.findByDateAndSoupOrderById(date, true);
        Collection<LunchEntity> meals = lunchService.findByDateAndSoupOrderById(date, false);

        if (Objects.isNull(orders) || Objects.isNull(soups) || Objects.isNull(meals)) {
            return null;
        }

        int index = 0;
        for (LunchEntity soup : soups) {
            numberingMap.put(soup.getId(), soupStrings[index]);
            index++;
        }

        index = 0;
        for (LunchEntity meal : meals) {
            numberingMap.put(meal.getId(), mealStrings[index]);
            index++;
        }

        return orders.stream()
                .map(this::dailyReportFromEntity)
                .collect(Collectors.toList())
                .stream()
                .sorted((o1, o2) -> SVK_COLLATOR.compare(o1.getName(),o2.getName()))
                .collect(Collectors.toList());

    }

    @Override
    public Collection<DailyReportSummary> createDailySummary(final LocalDate date){

        Collection<OrderEntity> orders = service.findByDateOrderByUser(date);
        Collection<LunchEntity> soups = lunchService.findByDateAndSoupOrderById(date, true);
        Collection<LunchEntity> meals = lunchService.findByDateAndSoupOrderById(date, false);

        if (Objects.isNull(orders) || Objects.isNull(soups) || Objects.isNull(meals)) {
            return null;
        }

        Collection<DailyReportSummary> summaries = new ArrayList<>();

        int index = 0;
        for (LunchEntity soup : soups) {
            summaries.add(new DailyReportSummary(soup.getId(), soupStrings[index], soup.getDescription()));
            index++;
        }

        index = 0;
        for (LunchEntity meal : meals) {
            summaries.add(new DailyReportSummary(meal.getId(), mealStrings[index], meal.getDescription()));
            index++;
        }

        for (OrderEntity order : orders) {
            summaries.stream().filter(summary -> summary.getId() == order.getSoup()).forEach(DailyReportSummary::incCount);
            summaries.stream().filter(summary -> summary.getId() == order.getMeal()).forEach(DailyReportSummary::incCount);
        }

        return summaries;
    }

    @Override
    public Collection<DailyReportSummary> createDailySummaryMorning(final LocalDate date){

        Collection<OrderEntity> orders = service.findByDateOrderByUser(date);
        Collection<LunchEntity> soups = lunchService.findByDateAndSoupOrderById(date, true);
        Collection<LunchEntity> meals = lunchService.findByDateAndSoupOrderById(date, false);

        if (Objects.isNull(orders) || Objects.isNull(soups) || Objects.isNull(meals)) {
            return null;
        }

        Collection<DailyReportSummary> summaries = new ArrayList<>();

        int index = 0;
        for (LunchEntity soup : soups) {
            summaries.add(new DailyReportSummary(soup.getId(), soupStrings[index], soup.getDescription()));
            index++;
        }

        index = 0;
        for (LunchEntity meal : meals) {
            summaries.add(new DailyReportSummary(meal.getId(), mealStrings[index], meal.getDescription()));
            index++;
        }

        for (OrderEntity order : orders) {
            summaries.stream().filter(summary -> (summary.getId() == order.getSoup() && order.getDate().isEqual(order.getChanged().toLocalDate()))).forEach(DailyReportSummary::incCount);
            summaries.stream().filter(summary -> (summary.getId() == order.getMeal() && order.getDate().isEqual(order.getChanged().toLocalDate()))).forEach(DailyReportSummary::incCount);
        }

        return summaries;
    }

    @Override
    public Collection<MonthlyReport> createMonthlyReport(final LocalDate date) {
        Collection<MonthlyReport> monthlyReports = new ArrayList<>();

        Collection<Object[]> tuples = service.findByDateGreaterThanEqualAndDateLessThanEqualOrderByDate(date.withDayOfMonth(1), date.withDayOfMonth(date.lengthOfMonth()));

        for(Object[] tuple : tuples) {
            User user = userService.getUserById((Long) tuple[0]);
            if (Objects.isNull(user)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Cannot find user with id {}", tuple[0]);
                }
                continue;
            }
            double price = config.getEmployee();
            boolean employee = true;
            if (user.getRelation().equals(UserRelation.PARTIAL)) {
                price = config.getPartial();
                employee = false;
            } else {
                if (user.getRelation().equals(UserRelation.VISITOR)) {
                    price = config.getVisitor();
                    employee = false;
                }
            }
            MonthlyReport monthlyReport = new MonthlyReport(
                    user.getPid(),
                    user.getLongName(),
                    (Long) tuple[1],
                    price,
                    employee
            );
            monthlyReports.add(monthlyReport);
        }
        return monthlyReports
                .stream()
                .sorted((o1, o2) -> SVK_COLLATOR.compare(o1.getName(),o2.getName()))
                .sorted((o1, o2) -> Double.compare(o1.getPrice(),o2.getPrice()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public String createOlympFile(final LocalDate date) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Trying to create export file {}", config.getExport());
        }
        BufferedWriter myFile = null;
        try {
            myFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(config.getExport()), "UTF8"));
            myFile.write("##&\tOSCISLO\tZR_STRAVNE");
            myFile.newLine();
            Collection<MonthlyReport> summary = createMonthlyReport(date);
            if (LOG.isTraceEnabled()) {
                LOG.debug("Getting month summary, number of objects: {}", summary.size());
            }
            final BufferedWriter finalMyFile = myFile;
            summary.forEach((ms) -> {
                try {
                    if (ms.getEmployee()) {
                        String newLine = " \t" + ms.getPid() + "\t";
                        newLine += Double.toString(ms.getSum());
                        finalMyFile.write(newLine);
                        finalMyFile.newLine();
                        if (LOG.isTraceEnabled()) {
                            LOG.trace("Writing line: {}", newLine);
                        }
                    }
                } catch (IOException e) {
                    LOG.warn("IO Exception error by creating export file {}", config.getExport());
                    if (LOG.isTraceEnabled()) {
                        LOG.trace("Exception: ", e);
                    }
                }
            });
            myFile.close();
        } catch (final IOException ex) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("Exception: ", ex);
            }
        }
        return config.getExport();
    }

    private boolean lunchInOrders(final Collection<Order> orders, final Lunch lunch) {
        for (Order order : orders) {
            if (lunch.equals(order.getSoup()) || lunch.equals(order.getMeal())) {
                return true;
            }
        }
        return false;
    }

    private String getDescriptionInOrders(final Collection<Order> orders, final Lunch lunch) {
        for (Order order : orders) {
            if (lunch.equals(order.getSoup()) || lunch.equals(order.getMeal())) {
                return order.getDescription();
            }
        }
        return "";
    }

    private Order fromEntity(final OrderEntity entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        if (LOG.isTraceEnabled()) {
            LOG.trace("Creating order from order entity: {}", entity);
        }
        Order order = new Order();
        order.setId(entity.getId());
        order.updateDate(entity.getDate());
        order.updateUser(userService.getUserById(entity.getUser()));
        order.updateSoup(lunchService.getLunchById(entity.getSoup()));
        order.updateMeal(lunchService.getLunchById(entity.getMeal()));
        if (Objects.nonNull(entity.getDescription()) && !entity.getDescription().isEmpty()) {
            order.updateDescription(entity.getDescription());
        }
        order.updateChangedBy(userService.getUserById(entity.getChangedBy()));
        if (LOG.isTraceEnabled()) {
            LOG.trace("Created order {} from order entity", order);
        }
        return order;
    }

    private DailyReport dailyReportFromEntity(final OrderEntity entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        DailyReport dailyReport = new DailyReport();
        User user = userService.getUserById(entity.getUser());
        User changedBy = userService.getUserById(entity.getChangedBy());
        if (UserRelation.VISITOR.equals(user.getRelation()) &&
            Objects.nonNull(entity.getDescription()) &&
            !entity.getDescription().isEmpty()) {
                dailyReport.setName(user.getLongName() + " (" + entity.getDescription() + ")");
        } else {
            dailyReport.setName(user.getLongName());
        }
        dailyReport.setSoup(numberingMap.get(entity.getSoup()));
        dailyReport.setMeal(numberingMap.get(entity.getMeal()));
        dailyReport.setChanged(entity.getChanged());
        if (Objects.nonNull(changedBy)) {
            dailyReport.setChangedBy(changedBy.getLongName());
        }
        return dailyReport;
    }

    private String soupName(final OrderEntity entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        return lunchService.getLunchById(entity.getSoup()).getDescription();
    }

    private String mealName(final OrderEntity entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        return lunchService.getLunchById(entity.getMeal()).getDescription();
    }

    private CharSequence remakeOldDateString(final String oldDateString) {
        String replaced = oldDateString.substring(0, oldDateString.length() - 7).replaceAll("\\s", "");
        String [] replacedAndSplitted = replaced.split("\\.");
        if (replacedAndSplitted.length < 3) {
            String [] replacedAndSeparated = replaced.split("/");
            if (replacedAndSeparated.length < 3) {
                LOG.warn("Bad date {} ! Creating date 1.1.1990", Arrays.toString(replacedAndSeparated));
                replaced = "1.1.1990";
            } else {
                replaced = replacedAndSeparated[0]+"."+replacedAndSeparated[1]+".";
                if (replacedAndSeparated[2].length() > 4) {
                    replaced += replacedAndSeparated[2].substring(0, 4);
                } else {
                    replaced += replacedAndSeparated[2];
                }
            }
        } else {
            replaced = replacedAndSplitted[0]+"."+replacedAndSplitted[1]+".";
            if (replacedAndSplitted[2].length() == 2) {
                replaced += "20" + replacedAndSplitted[2];
            } else {
                if (replacedAndSplitted[2].length() > 4) {
                    replaced += replacedAndSplitted[2].substring(0, 4);
                } else {
                    replaced += replacedAndSplitted[2];
                }
            }
        }
        return replaced;
    }


}