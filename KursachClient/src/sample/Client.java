package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.time.LocalDate;
import java.util.Date;

public class Client {

    Socket soc;
    DataOutputStream os;
    DataInputStream is;
    public boolean isAdmin;

    Client() {
        try {
            soc = new Socket("localhost", 9876);
            os = new DataOutputStream(soc.getOutputStream());
            os.flush();
            is = new DataInputStream(soc.getInputStream());
            System.out.println("Server initialized");
        } catch (IOException e) {
            try {
                soc.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public int login(String user, String pass) {
        int chek=0;
        try {
            os.writeInt(0);
            os.writeUTF(user);
            os.writeUTF(pass);
            chek = is.readInt();
            isAdmin = is.readBoolean();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return chek;
    }

    public void addFilm(String name, String ganr, int price) {
        try {
            os.writeInt(1);
            os.writeUTF(name);
            os.writeUTF(ganr);
            os.writeInt(price);
        } catch (IOException e) {
            e.printStackTrace();
            try {
                soc.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void giveFilm(String name, String username,LocalDate getDate, LocalDate backDate ){
        try {
            os.writeInt(2);
            os.writeUTF(name);
            os.writeUTF(username);
            os.writeLong(getDate.toEpochDay() * 24 * 60 * 60 * 1000);
            os.writeLong(backDate.toEpochDay() * 24 * 60 * 60 * 1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getFilm(String name, String username,LocalDate getDate, LocalDate realreturnDate )
    {
        try {
            os.writeInt(3);
            os.writeUTF(name);
            os.writeUTF(username);
            os.writeLong(getDate.toEpochDay() * 24 * 60 * 60 * 1000);
            os.writeLong(realreturnDate.toEpochDay() * 24 * 60 * 60 * 1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int statistic(LocalDate begindate, LocalDate enddate)
    {
        int r =0;
        try {
            os.writeInt(4);
            os.writeLong(begindate.toEpochDay() * 24 * 60 * 60 * 1000);
            os.writeLong(enddate.toEpochDay() * 24 * 60 * 60 * 1000);
            r=is.readInt();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return r;
    }

    public ObservableList<History> tabHistory() {
        ObservableList<History> arr = FXCollections.observableArrayList();
        try {
            os.writeInt(5);
            int count = is.readInt();
            for (int i = 0; i < count; ++i) {
                Date factReturnDate=null;
                int id_h=is.readInt();
                int id = is.readInt();
                String name = is.readUTF();
                int cost = is.readInt();
                Date getDate = new Date(is.readLong());
                Date returnDate = new Date(is.readLong());
                String lastName = is.readUTF();
                int chek = is.readInt();
                if(chek==1)
                    factReturnDate= new Date(is.readLong());
                arr.add(new History(id_h, id, name, getDate, returnDate, factReturnDate, lastName, cost));
            }
        } catch (IOException e) {
            e.printStackTrace();
            try {
                soc.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return null;
        }
        return  arr;
    }

    public ObservableList<Film> tabFilm() {
        ObservableList<Film> arr = FXCollections.observableArrayList();
        try {
            os.writeInt(6);
            int count = is.readInt();
            for (int i = 0; i < count; ++i) {
                Date returnate=null;
                int id = is.readInt();
                String name = is.readUTF();
                int cost = is.readInt();
                String status = is.readUTF();
                String ganr = is.readUTF();
                String lastName = is.readUTF();
                int chek = is.readInt();
                if(chek==1)
                    returnate= new Date(is.readLong());
                arr.add(new Film(id, name, cost, ganr, status, returnate, lastName));
            }
        } catch (IOException e) {
            e.printStackTrace();
            try {
                soc.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return null;
        }
        return  arr;
    }

    public ObservableList<Film> search(String namefilm, String username) {
        ObservableList<Film> array = FXCollections.observableArrayList();
        try {
            os.writeInt(7);
            os.writeUTF(namefilm);
            os.writeUTF(username);
            int count = is.readInt();
            for (int i = 0; i < count; ++i) {
                Date returnate=null;
                int id = is.readInt();
                String name = is.readUTF();
                int cost = is.readInt();
                String status = is.readUTF();
                String ganr = is.readUTF();
                String lastName = is.readUTF();
                int chek = is.readInt();
                if(chek==1)
                    returnate= new Date(is.readLong());
                array.add(new Film(id, name, cost, ganr, status, returnate, lastName));
            }
        } catch (IOException e) {
            e.printStackTrace();
            try {
                soc.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return null;
        }
        return  array;
    }
}