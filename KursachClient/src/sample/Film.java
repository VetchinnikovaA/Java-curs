package sample;

import java.util.Date;

public class Film {

    int id;
    String namefilm;
    String ganr;
    String status;
    int price;
    Date backDate=null;
    String username;

    public Film(int id, String namefilm, int price, String ganr, String status, Date backDate, String username) {
        this.id = id;
        this.namefilm = namefilm;
        this.price = price;
        this.ganr = ganr;
        this.status = status;
        this.backDate = backDate;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public String getNamefilm() {
        return namefilm;
    }

    public String getGanr() {
        return ganr;
    }

    public int getPrice(){
        return price;
    }

    public String getStatus() {
        return status;
    }

    public Date getBackDate() {
            return backDate;
    }

    public String getUsername() {
        if (username == null)
            return "";
        return username;
    }
}
