package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;

public class dbHandler extends dbConfig {
    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName +
                "?verifyServerCertificate=false" +
                "&useSSL=false" +
                "&requireSSL=false" +
                "&useLegacyDatetimeCode=false" +
                "&amp" +
                "&serverTimezone=UTC";

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return dbConnection;
    }

    public void addFilm(Film film) {
        String insert = "INSERT INTO " + Const.FILMS_TABLE + "(" +
                Const.FILM_COST + "," +
                Const.FILM_GANR + "," + Const.FILM_BACK_DATE + "," +
                Const.FILM_NAME + "," + Const.FILM_SECOND_NAME + "," +
                Const.FILM_STATUS + ")" + " VALUES(?,?,?,?,?,?)";

        try {
            PreparedStatement pr = getDbConnection().prepareStatement(insert);
            pr.setInt(1, film.getCost());
            pr.setString(2, film.getGanr());
            if (film.getBackDate() != null)
                pr.setDate(3, new Date(film.getBackDate().getTime()));
            else
                pr.setDate(3, null);
            pr.setString(4, film.getName());
            pr.setString(5, film.getSecondName());
            pr.setString(6, film.getStatus());

            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void giveFilm(String name, String username, java.util.Date getDate, java.util.Date returnDate) {
        String select = "SELECT * FROM " + Const.FILMS_TABLE + " WHERE " +
                Const.FILM_NAME + " = ? AND " + Const.FILM_STATUS + " = 'Доступен'";
        try {
            PreparedStatement pr = getDbConnection().prepareStatement(select);
            pr.setString(1, name);
            ResultSet set = pr.executeQuery();
            System.out.println("Запрашиваем доступность фильма");
            while (set.next()) {
                System.out.println("Фильм доступен");
                String update = "UPDATE " + Const.FILMS_TABLE + " SET " + Const.FILM_STATUS + " = ?, " + Const.FILM_BACK_DATE + " = ?, " +
                        Const.FILM_SECOND_NAME + " = ? WHERE " + Const.FILM_NAME + " = ?";
                pr = getDbConnection().prepareStatement(update);
                pr.setString(1, "Выдан");
                pr.setDate(2, new Date(returnDate.getTime()));
                pr.setString(3, username);
                pr.setString(4, name);
                int id = 0;
                int cost = 0;
                id = set.getInt("id");
                cost = set.getInt("cost");
                System.out.println("Получили id");
                pr.executeUpdate();
                addHistory(id, name, cost, username, getDate, returnDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
    }

    public void addHistory(int id, String name, int cost, String username, java.util.Date getDate, java.util.Date returnDate) {
        String insert = "INSERT INTO " + Const.HISTORY_TABLE + "(" +
                Const.HISTORY_ID + "," +
                Const.HISTORY_NAME + "," + Const.HISTORY_COST + "," +
                Const.HISTORY_GETDATE + "," + Const.HISTORY_RETURNDSTE + "," +
                Const.HISTORY_REALRETURNDATE + "," + Const.HISTORY_LASTNAME + ")" + " VALUES(?,?,?,?,?,?,?)";

        try {
            PreparedStatement pr = getDbConnection().prepareStatement(insert);
            pr.setInt(1, id);
            pr.setString(2, name);
            pr.setInt(3, cost);
            pr.setDate(4, new Date(getDate.getTime()));
            pr.setDate(5, new Date(returnDate.getTime()));
            pr.setDate(6, null);
            pr.setString(7, username);

            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void getFilm(String name, String username, java.util.Date getDate, java.util.Date realreturnDate) {
        String select = "SELECT * FROM " + Const.FILMS_TABLE + " WHERE " +
                Const.FILM_NAME + " = ? AND " + Const.FILM_SECOND_NAME + " = ? AND " + Const.FILM_STATUS + " = ?";
        try {
            PreparedStatement pr = getDbConnection().prepareStatement(select);
            pr.setString(1, name);
            pr.setString(2, username);
            pr.setString(3, "Выдан");
            ResultSet set = pr.executeQuery();
            System.out.println("Запрашиваем фильм");
            while (set.next()) {
                System.out.println("Фильм найден");
                String update = "UPDATE " + Const.FILMS_TABLE + " SET " + Const.FILM_STATUS + " = ?, " + Const.FILM_BACK_DATE + " = ?, " +
                        Const.FILM_SECOND_NAME + " = ? WHERE " + Const.FILM_NAME + " = ?";
                pr = getDbConnection().prepareStatement(update);
                pr.setString(1, "Доступен");
                pr.setDate(2, null);
                pr.setString(3, "");
                pr.setString(4, name);
                pr.executeUpdate();
                redHistory(name, username, getDate, realreturnDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
    }

    public void redHistory(String name, String username, java.util.Date getDate, java.util.Date realreturnDate) {
        String select = "SELECT * FROM " + Const.HISTORY_TABLE + " WHERE " +
                Const.HISTORY_NAME + " = ? AND " + Const.HISTORY_LASTNAME + " = ? AND " + Const.HISTORY_GETDATE + " = ?";
        try {
            PreparedStatement pr = getDbConnection().prepareStatement(select);
            pr.setString(1, name);
            pr.setString(2, username);
            pr.setDate(3, new Date(getDate.getTime()));
            ResultSet set = pr.executeQuery();
            System.out.println("Запрашиваем фильм");
            while (set.next()) {
                System.out.println("Фильм yyy");
                String update = "UPDATE " + Const.HISTORY_TABLE + " SET " + Const.HISTORY_REALRETURNDATE +
                        " = ? WHERE " + Const.HISTORY_NAME + " = ? AND " + Const.HISTORY_GETDATE + " = ?  AND " +
                        Const.HISTORY_LASTNAME + " = ?";
                pr = getDbConnection().prepareStatement(update);
                pr.setDate(1, new Date(realreturnDate.getTime()));
                pr.setString(2, name);
                pr.setDate(3, new Date(getDate.getTime()));
                pr.setString(4, username);
                pr.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
    }

    public int statistic(java.util.Date begindate, java.util.Date enddate) {
        String select = "SELECT * FROM " + Const.HISTORY_TABLE + " WHERE " + Const.HISTORY_GETDATE + " >= ? AND " + Const.HISTORY_REALRETURNDATE + " <= ?";
        int res = 0;
        int s = 0;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setDate(1, new Date(begindate.getTime()));
            prSt.setDate(2, new Date(enddate.getTime()));
            ResultSet set = prSt.executeQuery();
            while (set.next()) {
                Date dat1 = set.getDate(Const.HISTORY_RETURNDSTE);
                Date dat2 = set.getDate(Const.HISTORY_REALRETURNDATE);
                if (dat2 != null) {
                    if (dat1.compareTo(dat2) < 0) {
                        s = set.getInt("cost");
                        res += 2 * s;
                    }
                    if (dat1.compareTo(dat2) > 0) {
                        s = set.getInt("cost");
                        res += s / 2;
                    }
                    if (dat1.compareTo(dat2) == 0) {
                        System.out.println("hhh");
                        res += set.getInt("cost");
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    public ArrayList<History> getHistory() {
        String select = "SELECT * FROM " + Const.HISTORY_TABLE;
        ArrayList<History> arr = new ArrayList<>();
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            ResultSet set = prSt.executeQuery();
            while (set.next()) {
                int id_h = set.getInt(Const.HISTORY_ID_HIST);
                int id = set.getInt(Const.HISTORY_ID);
                int cost = set.getInt(Const.HISTORY_COST);
                String name = set.getString(Const.HISTORY_NAME);
                Date getDate = set.getDate(Const.HISTORY_GETDATE);
                Date returnDate = set.getDate(Const.HISTORY_RETURNDSTE);
                Date factReturnDate = set.getDate(Const.HISTORY_REALRETURNDATE);
                String lastName = set.getString(Const.HISTORY_LASTNAME);
                arr.add(new History(id_h, id, name, getDate, returnDate, factReturnDate, lastName, cost));
            }
            prSt.close();
            set.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return arr;
    }

    public ArrayList<Film> getFilms() {
        String select = "SELECT * FROM " + Const.FILMS_TABLE;
        ArrayList<Film> arr = new ArrayList<>();
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            ResultSet set = prSt.executeQuery();
            while (set.next()) {
                int id = set.getInt(Const.FILM_ID);
                int cost = set.getInt(Const.FILM_COST);
                String name = set.getString(Const.FILM_NAME);
                String status = set.getString(Const.FILM_STATUS);
                String ganr = set.getString(Const.FILM_GANR);
                Date returnDate = set.getDate(Const.FILM_BACK_DATE);
                String lastName = set.getString(Const.FILM_SECOND_NAME);
                arr.add(new Film(id, name, cost, ganr, status, returnDate, lastName));
            }
            prSt.close();
            set.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return arr;
    }

    public ArrayList<Film> searchFilm(String namefilm, String username) {
        if (namefilm.length() > 0) {
            System.out.println(namefilm);
            System.out.println(username);
            String select = "SELECT * FROM " + Const.FILMS_TABLE + " WHERE " +
                    Const.FILM_NAME + " = ?";
            ArrayList<Film> array = new ArrayList<>();
            try {
                PreparedStatement pr = getDbConnection().prepareStatement(select);
                pr.setString(1, namefilm);
                ResultSet set = pr.executeQuery();
                while (set.next()) {
                    int id = set.getInt(Const.FILM_ID);
                    int cost = set.getInt(Const.FILM_COST);
                    String name = set.getString(Const.FILM_NAME);
                    String status = set.getString(Const.FILM_STATUS);
                    String ganr = set.getString(Const.FILM_GANR);
                    Date returnDate = set.getDate(Const.FILM_BACK_DATE);
                    String lastName = set.getString(Const.FILM_SECOND_NAME);
                    array.add(new Film(id, name, cost, ganr, status, returnDate, lastName));
                }
                pr.close();
                set.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    dbConnection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return array;
        } else {
            String select = "SELECT * FROM " + Const.FILMS_TABLE + " WHERE " +
                    Const.FILM_SECOND_NAME + " = ?";
            ArrayList<Film> array = new ArrayList<>();
            try {
                PreparedStatement pr = getDbConnection().prepareStatement(select);
                pr.setString(1, username);
                ResultSet set = pr.executeQuery();
                while (set.next()) {
                    int id = set.getInt(Const.FILM_ID);
                    int cost = set.getInt(Const.FILM_COST);
                    String name = set.getString(Const.FILM_NAME);
                    String status = set.getString(Const.FILM_STATUS);
                    String ganr = set.getString(Const.FILM_GANR);
                    Date returnDate = set.getDate(Const.FILM_BACK_DATE);
                    String lastName = set.getString(Const.FILM_SECOND_NAME);
                    array.add(new Film(id, name, cost, ganr, status, returnDate, lastName));
                }
                pr.close();
                set.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    dbConnection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return array;
        }
    }
}