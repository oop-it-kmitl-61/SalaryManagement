package Product;

import Connecter.ConnectSql;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.chart.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.chart.*;
import javafx.scene.Group;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.text.DecimalFormat;

import java.net.URL;
import java.util.ResourceBundle;

public class Home implements Initializable {
    public Label nameLabel;
    public ImageView dashboard;
    public ImageView add;
    public ImageView debt;
    public ImageView setting;
    public ComboBox<String> CB;
    public TextField TfExpen;
    public TextField TfIncome;
    public TextField TfRec;
    public TextField tfBalance;
    public Circle recCir;
    public Pane hist;
    public TextArea history;
    private String name, username, password, stuff, data;
    private String memo = "";
    private double balance, necessary, saving, rec, period, money_debt;
    private static DecimalFormat df2 = new DecimalFormat(".##");


    public void setName(String name) {
        this.nameLabel.setText(name);
        this.name = name;
    }

    public void setTfRec(Double money) {
        this.TfRec.setText(String.valueOf(df2.format(money)) + " ฿");
        this.rec = money;
    }

    public void setTfBalance(Double money) {
        this.tfBalance.setText(String.valueOf(df2.format(money)) + " Bath");
        this.balance = money;
    }

    public void setHistory(String his) {
        this.history.setText(his);
    }

    public void setAccount(String username, String password, Double necessary, Double saving, String stuff, Double period, Double money_debt) {
        this.username = username;
        this.password = password;
        this.necessary = necessary;
        this.saving = saving;
        this.stuff = stuff;
        this.period = period;
        this.money_debt = money_debt;
    }


    ObservableList<String> list = FXCollections.observableArrayList("Water Bill", "Electric Bill", "House Bill", "Phone and Internet Bill", "Food and Drink", "Transportation", "ETC");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Tooltip.install(add, new Tooltip("Click to add money statement."));
        Tooltip.install(debt, new Tooltip("To check how much debt you need to"));
        Tooltip.install(setting, new Tooltip("Go to setting page."));
        Tooltip.install(dashboard, new Tooltip("Go to check your dashboard"));
        Tooltip tooltip1 = new Tooltip();
        tooltip1.setText("Select what you pay.");
        CB.setTooltip(tooltip1);
        CB.setItems(list);
    }


    public void CbPick(ActionEvent actionEvent) {


    }

    public String getUsername() {
        return this.username;
    }

    public void add(MouseEvent mouseEvent) {
        String type = CB.getValue();
        if (type == null) {
            type = "ETC";
        }

        String query;
        String str_payment = TfExpen.getText();
        String str_income = TfIncome.getText();

        if (str_payment.equals("") && str_income.equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("You need to fill something up");

            alert.showAndWait();
        } else {
            if (str_payment.equals("")) {
                str_payment = "0";
            }

            if (str_income.equals("")) {
                str_income = "0";
            }

            Double payment = Double.parseDouble(str_payment);
            Double income = Double.parseDouble(str_income);

            this.saving += income;
            Day day = new Day();
            String date = day.getFormat();
            String hash = new Salt().generateRandomPassword(25);

            String query1 = "INSERT INTO test.TRANS(u_username,u_id,date,type,amount,note) VALUES (?,?,?,?,?,?)";
            String query2 = "INSERT INTO test.TRANS(u_username,u_id,date,type,amount,note) VALUES (?,?,?,?,?,?)";

            if (type.equals("ETC") || type.equals("Food and Drink") || type.equals("Transportation")) {
                query = "UPDATE USER SET u_use = ?, u_save = ? WHERE u_username = ?";

                this.balance -= payment;
            } else {
                query = "UPDATE USER SET u_pay = ?, u_save = ? WHERE u_username = ?";
                this.necessary -= payment;
            }

            ConnectSql connectSql = new ConnectSql();
            Connection connection = connectSql.GetConnect();

            try {
                PreparedStatement pstmt = connection.prepareStatement(query);
                PreparedStatement pstmt1 = connection.prepareStatement(query1);
                PreparedStatement pstmt2 = connection.prepareStatement(query2);
                pstmt1.setString(1, this.username);
                pstmt1.setString(2, hash);
                pstmt1.setString(3, date);
                if (payment != 0 && income != 0) {
                    System.out.println("this");
                    pstmt1.setInt(4, 1);
                    pstmt1.setDouble(5, payment);
                    pstmt1.setString(6, type);

                    pstmt2.setString(1, this.username);
                    pstmt2.setString(2, new Salt().generateRandomPassword(24));
                    pstmt2.setString(3, date);
                    pstmt2.setInt(4, 0);
                    pstmt2.setDouble(5, income);
                    pstmt2.setString(6, "income");
                    pstmt2.executeUpdate();
                    pstmt2.close();
                } else if (payment == 0 && income != 0) {
                    pstmt1.setInt(4, 0);
                    pstmt1.setDouble(5, income);
                    pstmt1.setString(6, "income");
                } else if (payment != 0 && income == 0) {
                    pstmt1.setInt(4, 1);
                    pstmt1.setDouble(5, payment);
                    pstmt1.setString(6, type);
                }


                if (type.equals("ETC") || type.equals("Food and Drink") || type.equals("Transportation")) {
                    pstmt.setDouble(1, this.balance);
                } else {
                    pstmt.setDouble(1, this.necessary);
                }
                pstmt.setDouble(2, this.saving);
                pstmt.setString(3, this.username);

                pstmt1.execute();
                pstmt.executeUpdate();
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Your update success.");

                alert.showAndWait();
                pstmt1.close();
                pstmt.close();

                TfExpen.setText("");
                TfIncome.setText("");
                this.memo += "income" + " " + date + " " + income + " ฿" + "\n" + type + " " + date + " " + payment + " ฿";

                this.history.appendText(this.memo);
                this.history.appendText("\n");

                tfBalance.setText(Double.toString(this.balance));
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }


    public void setting(MouseEvent mouseEvent) {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("Setting.fxml"));
        try {
            Loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.data = this.history.getText();
        Setting setting = Loader.getController();
        setting.setAccount(name, username, password, necessary, saving, this.stuff, this.period, this.money_debt, this.data);
        setting.setUsing(balance, rec);

        Parent p = Loader.getRoot();
        Stage home_stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        home_stage.setTitle("MoneyManeger");
        home_stage.setScene(new Scene(p));
        home_stage.show();
    }

    public void debt(MouseEvent mouseEvent) {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("debt.fxml"));
        try {
            Loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        debt db = Loader.getController();
        this.data = this.history.getText();
        db.setAccount(name, username, password, necessary, saving, stuff, period, money_debt, this.data);
        db.setUsing(balance, rec);

        Parent p = Loader.getRoot();
        Stage home_stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        home_stage.setTitle("MoneyManager");
        home_stage.setScene(new Scene(p));
        home_stage.show();
    }


    public void dashboard(MouseEvent mouseEvent) {
        Day month = new Day();
        String query = "SELECT * FROM USER WHERE u_username = ? and u_pass = ?";
        String query1 = "SELECT * FROM test.TRANS WHERE u_username = ? and  MONTH(date) = ?";

        ConnectSql connectSql = new ConnectSql();
        Connection connection = connectSql.GetConnect();

        double income = 0;
        double payment = 0;
        double etc = 0;
        int count_etc = 0;
        double food = 0;
        int count_food = 0;
        double transport = 0;
        int count_transport = 0;
        double waterbill = 0;
        double electbill = 0;
        double phonebill = 0;
        double housebill = 0;
        ResultSet rs;
        PreparedStatement pstmt, pstmt1;
        double total_saving = 0;
        {
            try {
                pstmt = connection.prepareStatement(query);
                pstmt1 = connection.prepareStatement(query1);
                pstmt1.setString(1, this.username);
                pstmt1.setInt(2, month.monthly());
                pstmt.setString(1, this.username);
                pstmt.setString(2, this.password);
                rs = pstmt.executeQuery();
                ResultSet rsTrans = pstmt1.executeQuery();
                if (rs.next()) {
                    total_saving = rs.getDouble("total_save");
                } else {
                    System.out.println("Error");
                }

                while (rsTrans.next()) {
                    if (rsTrans.getInt("type") == 1) {
                        payment += rsTrans.getDouble("amount");
                        if (rsTrans.getString("note").equals("ETC")) {
                            etc += rsTrans.getDouble("amount");
                            count_etc += 1;
                        } else if (rsTrans.getString("note").equals("Food and Drink")) {
                            food += rsTrans.getDouble("amount");
                            count_food += 1;
                        } else if (rsTrans.getString("note").equals("Transportation")) {
                            transport += rsTrans.getDouble("amount");
                            count_transport += 1;
                        } else if (rsTrans.getString("note").equals("Water Bill")) {
                            waterbill += rsTrans.getDouble("amount");
                        } else if (rsTrans.getString("note").equals("Phone and Internet Bill")) {
                            phonebill += rsTrans.getDouble("amount");
                        } else if (rsTrans.getString("note").equals("Electric Bill")) {
                            electbill += rsTrans.getDouble("amount");
                        } else if (rsTrans.getString("note").equals("House Bill")) {
                            housebill += rsTrans.getDouble("amount");
                        }
                    } else if (rsTrans.getInt("type") == 0) {
                        income += rsTrans.getDouble("amount");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println(payment);
        }
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("Dashboard.fxml"));
        try {
            Loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Dashboard db = Loader.getController();
        this.data = this.history.getText();
        db.setAccount(this.name, this.username, this.password, this.necessary, this.saving, this.stuff, this.period, this.money_debt, this.data);
        db.setUsing(this.balance, this.rec, total_saving);
        System.out.println(payment);
        db.setGraph(income, payment, etc, count_etc, food, count_food, transport, count_transport, waterbill, electbill, phonebill, housebill);

        Parent p = Loader.getRoot();
        Stage home_stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        home_stage.setTitle("MoneyManager");
        home_stage.setScene(new Scene(p));
        home_stage.show();
    }
}
