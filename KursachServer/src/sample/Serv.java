package sample;

import java.net.Socket;
import java.util.ArrayList;

public class Serv {
    ArrayList<Connection> mas = new ArrayList<>();

    void add(Socket soc) {
        mas.add(new Connection(soc, this));
    }
}
