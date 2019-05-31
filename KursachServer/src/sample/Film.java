package sample;

import java.util.Date;

public class Film {

    int id;
    String name;
    int cost;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public String getGanr() {
        return ganr;
    }

    public String getStatus() {
        return status;
    }

    public Date getBackDate() {
        return back_date;
    }

    public String getSecondName() {
        if (secondName == null)
            return "";
        return secondName;
    }

    String ganr;
    String status;
    Date back_date = null;
    String secondName;

    public Film(int id, String name, int cost, String ganr, String status, Date backDate, String secondName) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.ganr = ganr;
        this.status = status;
        this.back_date = backDate;
        this.secondName = secondName;
    }

}