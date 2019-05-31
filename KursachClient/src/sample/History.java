package  sample;

import java.util.Date;

public class History {

    int id_h;
    int id;
    String name;
    Date getDate;
    Date returnDate;
    Date factReturnDate;
    String lastName;
    int cost;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getGetDate() {
        return getDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public Date getFactReturnDate() {
        return factReturnDate;
    }

    public String getLastName() {
        return lastName;
    }

    public int getCost() {
        return cost;
    }

    public int getId_h() {
        return id_h;
    }

    public History(int id_h, int id, String name, Date getDate, Date returnDate, Date factReturnDate, String lastName, int cost) {

        this.id_h=id_h;
        this.id = id;
        this.name = name;
        this.getDate = getDate;
        this.returnDate = returnDate;
        this.factReturnDate = factReturnDate;
        this.lastName = lastName;
        this.cost = cost;
    }
}
