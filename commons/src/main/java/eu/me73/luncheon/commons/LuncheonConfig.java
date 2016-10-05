package eu.me73.luncheon.commons;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "luncheon")
public class LuncheonConfig {

    private Integer ordering;
    private Integer year;
    private Double employee;
    private Double visitor;
    private Double partial;
    private String export;
    private String powerName;
    private String powerPassword;
    private String adminName;
    private String adminPassword;

    public Integer getOrdering() {
        return ordering;
    }

    public void setOrdering(Integer ordering) {
        this.ordering = ordering;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Double getEmployee() {
        return employee;
    }

    public void setEmployee(Double employee) {
        this.employee = employee;
    }

    public Double getVisitor() {
        return visitor;
    }

    public void setVisitor(Double visitor) {
        this.visitor = visitor;
    }

    public Double getPartial() {
        return partial;
    }

    public void setPartial(Double partial) {
        this.partial = partial;
    }

    public String getExport() {
        return export;
    }

    public void setExport(String exportName) {
        this.export = exportName;
    }

    public String getPowerName() {
        return powerName;
    }

    public void setPowerName(String powerName) {
        this.powerName = powerName;
    }

    public String getPowerPassword() {
        return powerPassword;
    }

    public void setPowerPassword(String powerPassword) {
        this.powerPassword = powerPassword;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }
}
