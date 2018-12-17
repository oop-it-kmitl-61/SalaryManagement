package Product;

import Connecter.ConnectSql;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Setting implements Initializable {
    public TextField tfDaily;
    public TextField tfMonthly;
    public TextField tfSaving;
    public Button bnCancle;
    public Button bnSave;
    public ImageView icon1;
    public ImageView icon2;
    public ImageView icon3;
    public TextField tfIncome;
    public ImageView icon;
    private String username, name, stuff, data;
    private String password;
    private double necessary;
    private double saving, use, rec, period, money_debt;

    public void setAccount(String name, String username, String password, Double necessary, Double saving, String stuff, Double period, Double money_debt, String data){
        this.name = name;
        this.username = username;
        this.password = password;
        this.necessary = necessary;
        this.saving = saving;
        this.stuff = stuff;
        this.period = period;
        this.money_debt = money_debt;
        this.data = data;
    }

    public void  setUsing(Double use, Double rec){
        this.use = use;
        this.rec = rec;
    }



    public void cancle(ActionEvent actionEvent) {
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
        home.setAccount(this.username, this.password, this.necessary, this.saving,  this.stuff, this.period, this.money_debt);
        home.setHistory(this.data);

        Parent p = Loader.getRoot();
        Stage home_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        home_stage.setTitle("MoneyManeger");
        home_stage.setScene(new Scene(p));
        home_stage.show();
    }

    public void save(ActionEvent actionEvent) {
        int count = 0;
        if(tfIncome.getText().equals("")){
            count += 1;
        }

        if(tfDaily.getText().equals("")){
            count += 1;
        }

        if(tfMonthly.getText().equals("")){
            count += 1;
        }

        if (tfSaving.getText().equals("")){
            count += 1;
        }

        if(count == 0) {
            double income = Double.parseDouble(tfIncome.getText());
            double per_daily = Double.parseDouble(tfDaily.getText());
            double per_monthly = Double.parseDouble(tfMonthly.getText());
            double per_saving =Double.parseDouble(tfSaving.getText());

            double daily = (per_daily/100)*income;
            double monthly = (per_daily/100)*income;
            double saves = (per_daily/100)*income;

            System.out.println(daily);
            System.out.println(monthly);
            System.out.println(saves);
            System.out.println(username);

            String query = "UPDATE test.USER SET u_income = ?, u_use = ?, u_save = ?, u_pay = ? WHERE u_username = ?";

            ConnectSql connectSql = new ConnectSql();
            Connection connection = connectSql.GetConnect();

            try {
                PreparedStatement pstm = connection.prepareStatement(query);
                pstm.setDouble(1, income);
                pstm.setDouble(2, daily);
                pstm.setDouble(3, saves);
                pstm.setDouble(4, monthly);
                pstm.setString(5, username);
                pstm.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Updated you still need to come change every month for not the default one.");
                alert.showAndWait();
                pstm.close();
                System.exit(0);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("You need to Enter!!! value all type of money.");

            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Tooltip.install(icon, new Tooltip("Enter how much Salary this month."));
        Tooltip.install(icon1, new Tooltip("Enter how much daily/this month EX -> food, Transportation, ETC."));
        Tooltip.install(icon2, new Tooltip("Enter how much Monthly/this month EX -> Elect Bill, Water Bill, Telephone bill, ETC"));
        Tooltip.install(icon3, new Tooltip("Enter how much save/this month EX -> Saving to bought or invest something."));
    }
}
