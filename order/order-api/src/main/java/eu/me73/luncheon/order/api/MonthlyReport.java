package eu.me73.luncheon.order.api;

public class MonthlyReport {

    private String pid;
    private String name;
    private Long count;
    private Double price;
    private Double sum;
    private Boolean employee;

    public MonthlyReport() {
    }

    public MonthlyReport(final String pid,
                         final String name,
                         final Long count,
                         final Double price,
                         final Boolean employee) {
        this.name = name;
        this.count = count;
        this.price = price;
        this.sum = Math.round(count * price * 100) / 100.0;
        this.pid = pid;
        this.employee = employee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Boolean getEmployee() {
        return employee;
    }

    public void setEmployee(Boolean employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "MonthlyReport{" +
                "pid='" + pid + '\'' +
                ", name='" + name + '\'' +
                ", count=" + count +
                ", price=" + price +
                ", sum=" + sum +
                ", employee=" + employee +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MonthlyReport that = (MonthlyReport) o;

        if (pid != null ? !pid.equals(that.pid) : that.pid != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return employee != null ? employee.equals(that.employee) : that.employee == null;

    }

    @Override
    public int hashCode() {
        int result = pid != null ? pid.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (employee != null ? employee.hashCode() : 0);
        return result;
    }
}
