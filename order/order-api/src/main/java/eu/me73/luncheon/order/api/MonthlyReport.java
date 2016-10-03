package eu.me73.luncheon.order.api;

public class MonthlyReport {

    private String name;
    private Long count;
    private Double price;
    private Double sum;

    public MonthlyReport() {
    }

    public MonthlyReport(final String name,
                         final Long count,
                         final Double price) {
        this.name = name;
        this.count = count;
        this.price = price;
        this.sum = Math.round(count * price * 100) / 100.0;
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

    @Override
    public String toString() {
        return "MonthlyReport{" +
                "name='" + name + '\'' +
                ", count=" + count +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MonthlyReport that = (MonthlyReport) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (count != null ? !count.equals(that.count) : that.count != null) return false;
        return price != null ? price.equals(that.price) : that.price == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (count != null ? count.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }
}
