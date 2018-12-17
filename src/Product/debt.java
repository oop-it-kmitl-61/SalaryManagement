package Product;

import Connecter.ConnectSql;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

public class debt {
    public Button bnCalculate;
    public TextField tfStuff;
    public TextField tfDown;
    public TextField tfYear;
    public TextField tfInterest;
    public TextField tfShow;
    public Button bnSave;
    public Button bnBack;
    public TextArea ta;
    public TextField tfValue;
    private String username, name, stuff, data;
    private String password;
    private double necessary;
    private double saving, use, rec, period, money_debt;
    private static DecimalFormat df2 = new DecimalFormat(".##");

    public void setAccount(String name, String username, String password, Double necessary, Double saving, String stuff, Double period, Double money_debt, String data){
        this.name = name;
        this.username = username;
        this.password = password;
        this.necessary = necessary;
        this.saving = saving;
        this.stuff = stuff;
        this.period = period;
        this.data = data;
        this.money_debt = money_debt;
        ta.setText(this.stuff + " "+this.money_debt + " Bath/Month " + "for " + this.period + " period");
    }

    public void  setUsing(Double use, Double rec){
        this.use = use;
        this.rec = rec;
    }

    public void Calculate(ActionEvent actionEvent) {
        String stuff1 = tfStuff.getText();
        if(stuff1.equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cannot complete task");
            alert.setHeaderText(null);
            alert.setContentText("You need to fill all stuff you want.");

            alert.showAndWait();
        }else if(tfValue.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cannot complete task");
            alert.setHeaderText(null);
            alert.setContentText("You need to fill value.");

            alert.showAndWait();
        }else if(tfDown.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cannot complete task");
            alert.setHeaderText(null);
            alert.setContentText("You need to fill down payment.");

            alert.showAndWait();
        }else if(tfYear.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cannot complete task");
            alert.setHeaderText(null);
            alert.setContentText("You need to fill how many year to pay.");

            alert.showAndWait();
        }else if(tfInterest.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cannot complete task");
            alert.setHeaderText(null);
            alert.setContentText("You need to fill interest per year.");

            alert.showAndWait();
        }else{
            Double interest = (Double.parseDouble(tfInterest.getText())) / 100;
            Double year = Double.parseDouble(tfYear.getText());
            Double down = (Double.parseDouble(tfDown.getText())) / 100;
            Double value = Double.parseDouble(tfValue.getText());
            Double down_value = value - (value * down);
            System.out.println(down_value);
            Double interest_all = down_value * interest * year;
            System.out.println(interest_all);
            Double total = down_value + interest_all;
            Double answer = total/(12*year);
            tfShow.setText(Double.toString(Double.parseDouble(df2.format(answer)))+" Bath");
        }
    }

    public void Save(ActionEvent actionEvent) {
        String stuff1 = tfStuff.getText();
        double interest = 0;
        double year = 0;
        double down = 0;
        double value = 0;
        System.out.println(this.stuff);

        if(stuff1.equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cannot complete task");
            alert.setHeaderText(null);
            alert.setContentText("You need to fill all stuff you want.");

            alert.showAndWait();
        }else if(tfValue.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cannot complete task");
            alert.setHeaderText(null);
            alert.setContentText("You need to fill value.");

            alert.showAndWait();
        }else if(tfDown.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cannot complete task");
            alert.setHeaderText(null);
            alert.setContentText("You need to fill down payment.");

            alert.showAndWait();
        }else if(tfYear.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cannot complete task");
            alert.setHeaderText(null);
            alert.setContentText("You need to fill how many year to pay.");

            alert.showAndWait();
        }else if(tfInterest.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cannot complete task");
            alert.setHeaderText(null);
            alert.setContentText("You need to fill interest per year.");

            alert.showAndWait();
        }else if(this.stuff == null){
            interest = (Double.parseDouble(tfInterest.getText())) / 100;
            year = Double.parseDouble(tfYear.getText());
            down = (Double.parseDouble(tfDown.getText())) / 100;
            value = Double.parseDouble(tfValue.getText());
            double down_value = value - (value * down);
            double interest_all = down_value * interest * year;
            double total = down_value + interest_all;
            double answer = total / (12 * year);
            double period = 12 * year;

            ConnectSql connectSql = new ConnectSql();
            Connection connection = connectSql.GetConnect();

            String query = "UPDATE test.USER SET u_stuff = ?, u_period = ?, u_mon = ? WHERE u_username = ?";

            if(answer > (this.necessary/2)){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("This is too much to handle right now");
                alert.setHeaderText(null);
                alert.setContentText("You cannot afford to pay for this yet");

                alert.showAndWait();
            }else{
                try {
                    PreparedStatement pstmt = connection.prepareStatement(query);
                    pstmt.setString(1, stuff1);
                    pstmt.setDouble(2, period);
                    pstmt.setDouble(3, answer);
                    pstmt.setString(4, this.username);
                    pstmt.executeUpdate();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Alert");
                    alert.setHeaderText(null);
                    alert.setContentText("Your debt update is complete.");

                    alert.showAndWait();
                    pstmt.close();
                    System.exit(0);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("There are debt already!");
            alert.setHeaderText(null);
            alert.setContentText("You need to pay all the period of debt then you can make new one.");

            alert.showAndWait();
        }

        System.out.println(this.stuff);
        System.out.println(stuff1);
        System.out.println(value);
        System.out.println(year);
        System.out.println(interest);
        System.out.println(down);
    }

    public void back(ActionEvent actionEvent) {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("Home.fxml"));
        try {
            Loader.load();
        } catch (IOException ex){
            ex.printStackTrace();
        }
        Home home = Loader.getController();
        home.setTfBalance(this.use);
        home.setTfRec(this.rec);
        home.setName(this.name);
        home.setAccount(this.username, this.password, this.necessary, this.saving, this.stuff, this.period, this.money_debt);
        home.setHistory(this.data);

        Parent p = Loader.getRoot();
        Stage home_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        home_stage.setTitle("MoneyManeger");
        home_stage.setScene(new Scene(p));
        home_stage.show();
    }
}
