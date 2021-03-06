package eu.me73.luncheon.boot;

import ch.qos.logback.classic.Logger;
import eu.me73.luncheon.user.api.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LuncheonRestController {

    private final Logger LOG = (Logger) LoggerFactory.getLogger(LuncheonRestController.class);

    @Autowired
    UserService service;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    String getIndex() {
        boolean isUser = false;
        boolean isAdmin = false;
        boolean isSpecial = false;
        for (GrantedAuthority grantedAuthority : service.getActualUser().getAuthorities()) {
            if (grantedAuthority.getAuthority().equals("ROLE_SPECIAL")) {
                isSpecial = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
                isUser = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
                isAdmin = true;
                break;
            }
        }
        if (isSpecial) {
            LOG.info("Special user logged in !");
            return "/security";
        }
        LOG.info("Loged user is {}", isAdmin ? "admin" : "user");
        return isAdmin ? "/lunches" : "/orders";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    String getLogin() {
        return "pages/login.html";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    String getLogout() {
        return "pages/logout.html";
    }

    @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
    String getAccessDenied() {
        return "pages/access.html";
    }

    @RequestMapping(value = "/lunches", method = RequestMethod.GET)
    String getLunches() {
        return "pages/lunches.html";
    }

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    String getOrders() {
        return "pages/myorders.html";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    String getOrdersAdmin() {
        return "pages/powerorders.html";
    }

    @RequestMapping(value = "/securitydaily", method = RequestMethod.GET)
    String getOrdersAdminDaily() {
        return "pages/dailya.html";
    }

    @RequestMapping(value = "/daily", method = RequestMethod.GET)
    String getOrdersDaily() {
        return "pages/daily.html";
    }

    @RequestMapping(value = "/monthly", method = RequestMethod.GET)
    String getOrdersMonthly() {
        return "pages/monthly.html";
    }

    @RequestMapping(value = "/weekly", method = RequestMethod.GET)
    String getOrdersWeekly() {
        return "pages/weekly.html";
    }

    @RequestMapping(value = "/security", method = RequestMethod.GET)
    String getSecurityPage() {
        return "pages/admin.html";
    }

}
