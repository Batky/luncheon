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
    private Integer sameDayStartHour;
    private Integer sameDayStartMinutes;
    private Integer sameDayEndHour;
    private Integer sameDayEndMinutes;


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

    public Integer getSameDayStartHour() {
        return sameDayStartHour;
    }

    public void setSameDayStartHour(Integer sameDayStartHour) {
        this.sameDayStartHour = sameDayStartHour;
    }

    public Integer getSameDayEndHour() {
        return sameDayEndHour;
    }

    public void setSameDayEndHour(Integer sameDayEndHour) {
        this.sameDayEndHour = sameDayEndHour;
    }

    public Integer getSameDayStartMinutes() {
        return sameDayStartMinutes;
    }

    public void setSameDayStartMinutes(Integer sameDayStartMinutes) {
        this.sameDayStartMinutes = sameDayStartMinutes;
    }

    public Integer getSameDayEndMinutes() {
        return sameDayEndMinutes;
    }

    public void setSameDayEndMinutes(Integer sameDayEndMinutes) {
        this.sameDayEndMinutes = sameDayEndMinutes;
    }
}
