package sample;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

public class Connection extends Thread {
    Socket soc;
    Serv serv;
    DataInputStream in;
    DataOutputStream out;
    dbHandler dbHand;
    public boolean isAdmin;

    public Connection(Socket soc, Serv serv) {
        dbHand = new dbHandler();
        this.soc = soc;
        this.serv = serv;
        System.out.println("Client connected");
        try {
            out = new DataOutputStream(soc.getOutputStream());
            out.flush();
            in = new DataInputStream(soc.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Client initialized");
        this.start();
    }

    @Override
    public void run() {
        while (true) {

            try {
                synchronized (in) {
                    System.out.println("Ждем команд");
                    int com = in.readInt();
                    System.out.println("Получена команда " + com);
                    switch (com) {
                        case 0:
                            int c=0;
                            String user = in.readUTF();
                            String pass = in.readUTF();
                            if(user.compareTo(pass)==0)
                                c=1;
                            if (user.compareTo("admin") == 0)
                                isAdmin = true;
                            else isAdmin=false;
                            out.writeInt(c);
                            out.writeBoolean(isAdmin);
                            break;
                        case 1:
                            String name = in.readUTF();
                            String ganr = in.readUTF();
                            int cost = in.readInt();
                            Film tmp = new Film(0, name, cost, ganr, "Доступен", null, "");
                            dbHand.addFilm(tmp);
                            break;
                        case 2:
                            dbHand.giveFilm(in.readUTF(), in.readUTF(),new Date(in.readLong()), new Date(in.readLong()));
                            break;
                        case 3:
                            dbHand.getFilm(in.readUTF(), in.readUTF(),new Date(in.readLong()), new Date(in.readLong()));
                            break;
                        case 4:
                            int r=dbHand.statistic(new Date(in.readLong()), new Date(in.readLong()));
                            out.writeInt(r);
                            out.flush();
                            break;
                        case 5:
                            ArrayList<History> arrHist = dbHand.getHistory();
                            out.writeInt(arrHist.size());
                            sendHistArr(out, arrHist);
                            break;
                        case 6:
                            ArrayList<Film> arrFilm= dbHand.getFilms();
                            out.writeInt(arrFilm.size());
                            sendFilmArr(out, arrFilm);
                            break;
                        case 7:
                            ArrayList<Film> arrayFilm= dbHand.searchFilm( in.readUTF(), in.readUTF());
                            out.writeInt(arrayFilm.size());
                            sendFilmArr(out, arrayFilm);
                            break;
                    }
                }
            } catch (IOException e) {
                try {
                    soc.close();
                    System.out.println("Соединение закрыто");
                    return;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
        }
    }

    public void sendHistArr(DataOutputStream out, ArrayList<History> arr) {
        for (int i = 0; i < arr.size(); i++)
        {
            History tmp = arr.get(i);
            try {
                out.writeInt(tmp.getId_h());
                out.writeInt(tmp.getId());
                out.writeUTF(tmp.getName());
                out.writeInt(tmp.getCost());
                out.writeLong(tmp.getGetDate().getTime());
                out.writeLong(tmp.getReturnDate().getTime());
                out.writeUTF(tmp.getLastName());
                if (tmp.getFactReturnDate() != null)
                {
                    out.writeInt(1);
                    out.writeLong(tmp.getFactReturnDate().getTime());
                }
                else
                    out.writeInt(0);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void sendFilmArr(DataOutputStream out, ArrayList<Film> arr) {
        for (int i = 0; i < arr.size(); i++)
        {
            Film tmp = arr.get(i);
            try {
                out.writeInt(tmp.getId());
                out.writeUTF(tmp.getName());
                out.writeInt(tmp.getCost());
                out.writeUTF(tmp.getStatus());
                out.writeUTF(tmp.getGanr());
                out.writeUTF(tmp.getSecondName());
                if (tmp.getBackDate() != null)
                {
                    out.writeInt(1);
                    out.writeLong(tmp.getBackDate().getTime());
                }
                else
                    out.writeInt(0);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}