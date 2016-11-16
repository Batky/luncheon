package eu.me73.luncheon.order.api;

import java.util.Collection;

public class UserStatistics {

    private String name;
    private int lunchesThisMonth;
    private boolean lunchesMo;
    private boolean lunchesTu;
    private boolean lunchesWe;
    private boolean lunchesTh;
    private boolean lunchesFr;

    public UserStatistics(String name, int lunchesThisMonth, boolean lunchesMo, boolean lunchesTu, boolean lunchesWe, boolean lunchesTh, boolean lunchesFr) {
        this.name = name;
        this.lunchesThisMonth = lunchesThisMonth;
        this.lunchesMo = lunchesMo;
        this.lunchesTu = lunchesTu;
        this.lunchesWe = lunchesWe;
        this.lunchesTh = lunchesTh;
        this.lunchesFr = lunchesFr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLunchesThisMonth() {
        return lunchesThisMonth;
    }

    public void setLunchesThisMonth(int lunchesThisMonth) {
        this.lunchesThisMonth = lunchesThisMonth;
    }

    public boolean isLunchesMo() {
        return lunchesMo;
    }

    public void setLunchesMo(boolean lunchesMo) {
        this.lunchesMo = lunchesMo;
    }

    public boolean isLunchesTu() {
        return lunchesTu;
    }

    public void setLunchesTu(boolean lunchesTu) {
        this.lunchesTu = lunchesTu;
    }

    public boolean isLunchesWe() {
        return lunchesWe;
    }

    public void setLunchesWe(boolean lunchesWe) {
        this.lunchesWe = lunchesWe;
    }

    public boolean isLunchesTh() {
        return lunchesTh;
    }

    public void setLunchesTh(boolean lunchesTh) {
        this.lunchesTh = lunchesTh;
    }

    public boolean isLunchesFr() {
        return lunchesFr;
    }

    public void setLunchesFr(boolean lunchesFr) {
        this.lunchesFr = lunchesFr;
    }

    @Override
    public String toString() {
        return "UserStatistics{" +
                "name='" + name + '\'' +
                ", lunchesThisMonth=" + lunchesThisMonth +
                ", lunchesMo=" + lunchesMo +
                ", lunchesTu=" + lunchesTu +
                ", lunchesWe=" + lunchesWe +
                ", lunchesTh=" + lunchesTh +
                ", lunchesFr=" + lunchesFr +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserStatistics that = (UserStatistics) o;

        if (lunchesThisMonth != that.lunchesThisMonth) return false;
        if (lunchesMo != that.lunchesMo) return false;
        if (lunchesTu != that.lunchesTu) return false;
        if (lunchesWe != that.lunchesWe) return false;
        if (lunchesTh != that.lunchesTh) return false;
        if (lunchesFr != that.lunchesFr) return false;
        return name != null ? name.equals(that.name) : that.name == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + lunchesThisMonth;
        result = 31 * result + (lunchesMo ? 1 : 0);
        result = 31 * result + (lunchesTu ? 1 : 0);
        result = 31 * result + (lunchesWe ? 1 : 0);
        result = 31 * result + (lunchesTh ? 1 : 0);
        result = 31 * result + (lunchesFr ? 1 : 0);
        return result;
    }
}
