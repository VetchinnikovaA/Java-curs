package sample;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        ServerSocket serverSoc;
        dbHandler dbHand = new dbHandler();
        Serv serv = new Serv();

        try {
            serverSoc = new ServerSocket(9876);
            while (true) {
                Socket client = serverSoc.accept();
                serv.add(client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
