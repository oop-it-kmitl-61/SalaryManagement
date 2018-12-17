package Product;

import Connecter.ConnectSql;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class Login{
    public TextField tfUser;
    public PasswordField tfPass;
    public Button bnLogin;
    public Hyperlink link1;
    public Pane justBg;
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private Day day = new Day();
    private int days = day.getMonth();
    private int date = day.getDate();
    private String history = "";


    public void link1(ActionEvent actionEvent) throws IOException {
        Parent register = FXMLLoader.load(getClass().getResource("Register.fxml"));
        Scene register_scene = new Scene(register);
        Stage regis_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        regis_stage.setScene(register_scene);
        regis_stage.show();
    }

    public void bnLogin(ActionEvent actionEvent) {
        ResultSet rs, rsHis;
        String name = tfUser.getText();
        String pass = tfPass.getText();

        ConnectSql connectSql = new ConnectSql();
        Connection connection = connectSql.GetConnect();

        String queryHis = "SELECT * FROM test.TRANS WHERE u_username = ? and  DATE(date) = DATE(?)" ;

        String query1 = "UPDATE test.USER SET u_period = ?, u_count = ?, total_save = ?, u_use = ?, u_pay = ?, u_save = ? WHERE u_username = ?";

        String query2 = "UPDATE test.USER SET u_count = ? WHERE u_username = ?";

        String query3 = "UPDATE test.USER SET u_period = ?, u_mon = ?, u_count = ? WHERE u_username = ?";

        String query = "SELECT * FROM USER WHERE u_username = ? and u_pass = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, name);
            ps.setString(2, pass);

            rs = ps.executeQuery();

            if(rs.next()){
                int dayleft = this.days - this.date;
                String user = rs.getString("u_name");
                double use = rs.getDouble("u_use");
                double necessary = rs.getDouble("u_pay");
                double save = rs.getDouble("u_save");
                String stuff = rs.getString("u_stuff");
                double income = rs.getDouble("u_income");
                double total_save = rs.getDouble("total_save");
                double money_debt = rs.getDouble("u_mon");
                double period = rs.getDouble("u_period");
                int count = rs.getInt("u_count");
                double rec = use/dayleft;

                System.out.println(period);

                if(period == 0){
                    count = 0;
                    try {
                        PreparedStatement ps1 = connection.prepareStatement(query3);
                        ps1.setNull(1, Types.DOUBLE);
                        ps1.setNull(2, Types.DOUBLE);
                        ps1.setInt(3, 0);
                        ps1.setString(4, name);
                        ps1.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }

                if (this.date == 1 && count == 0){
                    double month_save = save + necessary;
                    total_save += month_save;
                    save = income * 0.2;
                    use = income * 0.4;
                    necessary = income * 0.4;
                    count += 1;
                    period -= 1;
                    try {
                        PreparedStatement ps1 = connection.prepareStatement(query1);
                        ps1.setDouble(1, period);
                        ps1.setInt(2, count);
                        ps1.setDouble(3, total_save);
                        ps1.setDouble(4, use);
                        ps1.setDouble(5, necessary);
                        ps1.setDouble(6, save);
                        ps1.setString(7, name);
                        ps1.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else{
                    count = 0;
                    try {
                        PreparedStatement ps1 = connection.prepareStatement(query2);
                        ps1.setInt(1, count);
                        ps1.setString(2, name);
                        ps1.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                PreparedStatement psHis = connection.prepareStatement(queryHis);
                psHis.setString(1, name);
                psHis.setString(2, new Day().dateFormat());
                rsHis = psHis.executeQuery();
                while(rsHis.next()){
                    this.history += (rsHis.getString("note")+" "+rsHis.getString("date")+" "+rsHis.getString("amount")+" ฿"+"\n");
                }

                FXMLLoader Loader = new FXMLLoader();
                Loader.setLocation(getClass().getResource("Home.fxml"));
                try {
                    Loader.load();
                } catch (IOException ex){
                    ex.printStackTrace();
                }
                Home home = Loader.getController();
                home.setName(user);
                home.setTfBalance(use);
                home.setTfRec(rec);
                home.setAccount(name, pass, necessary, save, stuff, period, money_debt);
                home.setHistory(this.history);


                Parent p = Loader.getRoot();
                Stage home_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                home_stage.setTitle("MoneyManeger");
                home_stage.setScene(new Scene(p));
                home_stage.show();

//                Parent home = FXMLLoader.load(getClass().getResource("Home.fxml"));
//                Scene home_scene = new Scene(home);
//                Stage home_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
//                home_stage.setScene(home_scene);
//                home_stage.show();
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("MoneyManager");
                alert.setHeaderText(null);
                alert.setContentText("The username or password don't match or don't have account.");

                alert.showAndWait();
            }

            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void bnLoginKey(KeyEvent e) {
        bnLogin.setOnKeyPressed(ex -> {
            if(ex.getCode() == KeyCode.ENTER){
                System.out.println("Fuck this!!");
            }
        });
    }
}
