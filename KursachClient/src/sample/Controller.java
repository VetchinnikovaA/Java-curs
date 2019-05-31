package sample;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import animations.Shake;


public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane basepanel;

    @FXML
    private AnchorPane filmpanel;

    @FXML
    private TextField nameusersearch;

    @FXML
    private TextField namefilmsearch;

    @FXML
    private Button searchpanelbutton;

    @FXML
    private TableView<Film> filmtable1;

    @FXML
    private TableColumn<Film, Integer> idfilm1;

    @FXML
    private TableColumn<Film, String > namefilm1;

    @FXML
    private TableColumn<Film, String> ganrefilm1;

    @FXML
    private TableColumn<Film, Integer> pricefilm1;

    @FXML
    private TableColumn<Film, String> statusfilm1;

    @FXML
    private TableColumn<Film, String> datefilm1;

    @FXML
    private TableColumn<Film, String> userfilmname1;

    @FXML
    private AnchorPane startpanel;

    @FXML
    private AnchorPane logopanel;

    @FXML
    private AnchorPane outlogopanel;

    @FXML
    private Button signoutbutton;

    @FXML
    private TextField username;

    @FXML
    private AnchorPane inlogopanel;

    @FXML
    private PasswordField password;

    @FXML
    private TextField login;

    @FXML
    private Button signinbutton;

    @FXML
    private Button closebutton;

    @FXML
    private Button filmbutton;

    @FXML
    private Button historybutton;

    @FXML
    private Button prokatbutton;

    @FXML
    private Button statisticbutton;

    @FXML
    private Button addfilmbutton;

    @FXML
    private AnchorPane historypanel;

    @FXML
    private TableView<History> historytable;

    @FXML
    private TableColumn<History, Integer> id_h;

    @FXML
    private TableColumn<History, Integer> id_f;

    @FXML
    private TableColumn<History, String> name_h;

    @FXML
    private TableColumn<History, Integer> price_h;

    @FXML
    private TableColumn<History, String> getdate_h;

    @FXML
    private TableColumn<History, String> returndate_h;

    @FXML
    private TableColumn<History, String> userfilmname;

    @FXML
    private TableColumn<History, String> returndate_h1;

    @FXML
    private AnchorPane Prokatpanel;

    @FXML
    private TextField namefilmprokat;

    @FXML
    private TextField nameuserprokat;

    @FXML
    private DatePicker getdate;

    @FXML
    private DatePicker datereturn;

    @FXML
    private Button prokatpanelbutton;

    @FXML
    private Button returnpanelbutton;

    @FXML
    private AnchorPane statisticpanel;

    @FXML
    private DatePicker datebegininhperiod;

    @FXML
    private DatePicker dateendperiod;

    @FXML
    private Button statisticpanelbutton;

    @FXML
    private Text Statisticfield;

    @FXML
    private AnchorPane addfilmpanel;

    @FXML
    private TextField nameaddfilmpanel;

    @FXML
    private TextField ganraddfilmpanel;

    @FXML
    private TextField priceaddfilmpanel;

    @FXML
    private Button addfilmpanelbutton;
    boolean system;

    Client Client;

    @FXML
    void initialize() {
        try {
            Client = new Client();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setFilmTable(Client.tabFilm());

        filmbutton.setOnAction((event) -> {
            changeScene(filmpanel);
            setFilmTable(Client.tabFilm());
        });

        historybutton.setOnAction(event -> {
            changeScene(historypanel);
            setHistoryTable(Client.tabHistory());
        });

        prokatbutton.setOnAction(event -> {
            changeScene(Prokatpanel);

        });

        statisticbutton.setOnAction(event -> {
            if (Client.isAdmin == true)
                changeScene(statisticpanel);
            dateendperiod.setValue(null);
            datebegininhperiod.setValue(null);
            Statisticfield.setText("");
        });

        addfilmbutton.setOnAction(event -> {
            if (Client.isAdmin == true)
                changeScene(addfilmpanel);
        });

        signoutbutton.setOnAction(event -> {
            changeScene(startpanel);
            outlogopanel.setVisible(false);
            inlogopanel.setVisible(true);
            changeScene(startpanel);
            system = false;
            Client.isAdmin = false;
        });

        prokatpanelbutton.setOnAction(event -> {
            if (namefilmprokat.getText().trim().length() > 0 && nameuserprokat.getText().trim().length() > 0 &&
                    getdate.getValue()!=null && datereturn.getValue()!=null)
                Client.giveFilm(namefilmprokat.getText().trim(), nameuserprokat.getText(), getdate.getValue(), datereturn.getValue());
            else{
                Shake a=new Shake(namefilmprokat);
                Shake b=new Shake(nameuserprokat);
                a.playAnim();
                b.playAnim();
            }
            namefilmprokat.clear();
            nameuserprokat.clear();
            datereturn.setValue(null);
            getdate.setValue(null);

        });

        returnpanelbutton.setOnAction(event -> {
            if (namefilmprokat.getText().trim().length() > 0 && nameuserprokat.getText().trim().length() > 0 &&
                    getdate.getValue()!=null && datereturn.getValue()!=null)
                  Client.getFilm(namefilmprokat.getText().trim(), nameuserprokat.getText(), getdate.getValue(), datereturn.getValue());
            else{ Shake a=new Shake(namefilmprokat);
                Shake b=new Shake(nameuserprokat);
                Shake c=new Shake(getdate);
                Shake d=new Shake(datereturn);
                a.playAnim();
                b.playAnim();
                c.playAnim();
                d.playAnim();
            }
            namefilmprokat.clear();
            nameuserprokat.clear();
            datereturn.setValue(null);
            getdate.setValue(null);
        });

        closebutton.setOnAction(event -> {
            Platform.exit();
        });

        signinbutton.setOnAction(event -> {
            int chek;
            try {
                Client.isAdmin = false;
                if (login.getText().trim().length() > 0 && password.getText().trim().length() > 0) {
                   chek=Client.login(login.getText(), password.getText());
                   if(chek>0) {
                       username.setText(login.getText());
                       system = true;
                       inlogopanel.setVisible(false);
                       outlogopanel.setVisible(true);
                       changeScene(filmpanel);
                   }
                   else{
                       Shake loginAnim=new Shake(login);
                       Shake passAnim=new Shake(password);
                       loginAnim.playAnim();
                       passAnim.playAnim();
                   }
                    login.clear();
                    password.clear();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        addfilmpanelbutton.setOnAction(event -> {
            if (nameaddfilmpanel.getText().trim().length() > 0 && ganraddfilmpanel.getText().trim().length() > 0
                    && priceaddfilmpanel.getText().trim().length() > 0)
                Client.addFilm(nameaddfilmpanel.getText(), ganraddfilmpanel.getText(), Integer.parseInt(priceaddfilmpanel.getText()));
            else{
                Shake a=new Shake(nameaddfilmpanel);
                Shake b=new Shake(ganraddfilmpanel);
                Shake c=new Shake(priceaddfilmpanel);
                a.playAnim();
                b.playAnim();
                c.playAnim();
            }
            nameaddfilmpanel.clear();
            ganraddfilmpanel.clear();
            priceaddfilmpanel.clear();
        });

        statisticpanelbutton.setOnAction(event -> {
            int res=0;
            if(datebegininhperiod.getValue()!=null && dateendperiod.getValue()!=null) {
                res = Client.statistic(datebegininhperiod.getValue(), dateendperiod.getValue());
                Statisticfield.setText(" Доход за заданный период составил " + res);
            }
            else {
                Shake a=new Shake(datebegininhperiod);
                Shake b=new Shake(dateendperiod);
                a.playAnim();
                b.playAnim();
            }
        });

        searchpanelbutton.setOnAction(event -> {
               setFilmTable(Client.search(namefilmsearch.getText().trim(),nameusersearch.getText().trim()));
               nameusersearch.clear();
               namefilmsearch.clear();
        });

    }

    void changeScene(Node activeSc) {
        if (system == true) {

            filmpanel.setVisible(false);
            historypanel.setVisible(false);
            addfilmpanel.setVisible(false);
            Prokatpanel.setVisible(false);
            startpanel.setVisible(false);
            statisticpanel.setVisible(false);

            activeSc.setVisible(true);
        }

    }

    private void setHistoryTable(ObservableList<History> list) {
        price_h.setCellValueFactory(new PropertyValueFactory<>("cost"));
        userfilmname.setCellValueFactory(new PropertyValueFactory<>("factReturnDate"));
        getdate_h.setCellValueFactory(new PropertyValueFactory<>("getDate"));
        id_f.setCellValueFactory(new PropertyValueFactory<>("id"));
        returndate_h1.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        name_h.setCellValueFactory(new PropertyValueFactory<>("name"));
        returndate_h.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        id_h.setCellValueFactory(new PropertyValueFactory<>("id_h"));
        historytable.setItems(list);

    }

    private void setFilmTable(ObservableList<Film> list) {
        pricefilm1.setCellValueFactory(new PropertyValueFactory<>("price"));
        ganrefilm1.setCellValueFactory(new PropertyValueFactory<>("ganr"));
        statusfilm1.setCellValueFactory(new PropertyValueFactory<>("status"));
        idfilm1.setCellValueFactory(new PropertyValueFactory<>("id"));
        userfilmname1.setCellValueFactory(new PropertyValueFactory<>("username"));
        namefilm1.setCellValueFactory(new PropertyValueFactory<>("namefilm"));
        datefilm1.setCellValueFactory(new PropertyValueFactory<>("backDate"));
        filmtable1.setItems(list);

    }
}
