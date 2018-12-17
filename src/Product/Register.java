package Product;

import Connecter.ConnectSql;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.sql.*;

public class Register {
    public TextField tf_Rpass;
    public TextField tf_Ruser;
    public TextField tf_REpass;
    public TextField tf_income;
    public Button bnSignup;
    public Button bnCancle;
    public TextField tfName;
    public Hyperlink link1;
    public Pane justBg;
    Alert alert = new Alert(AlertType.INFORMATION);

    public void bnCancle(ActionEvent actionEvent) {
        tf_Ruser.setText("");
        tf_Rpass.setText("");
        tf_REpass.setText("");
        tf_income.setText("");
    }

    public void bnSignup(ActionEvent actionEvent){
        ConnectSql connectSql = new ConnectSql();
        Connection connection = connectSql.GetConnect();
        String name = tfName.getText();
        String user = tf_Ruser.getText();
        String pass = tf_Rpass.getText();
        String re_pass = tf_REpass.getText();
        double income = 0;
        double use = 0;
        double must = 0;
        double save = 0;

        if(name.equals("")){
            alert.setTitle("MoneyManager");
            alert.setHeaderText("Not Complete!!");
            String s ="add your name please.";
            alert.setContentText(s);
            alert.show();
        }else if(user.equals("")){
            alert.setTitle("MoneyManager");
            alert.setHeaderText("Not Complete!!");
            String s ="add username you want.";
            alert.setContentText(s);
            alert.show();
        }else if(pass.equals("") || !pass.equals(re_pass)){
            alert.setTitle("MoneyManager");
            alert.setHeaderText("Not Complete!!");
            String s ="Please recheck your password.";
            alert.setContentText(s);
            alert.show();
        }else if(tf_income.getText().equals("")){
            income = 0;
            use = 0.4*income;
            must = 0.4*income;
            save = 0.2*income;
            alert.setTitle("MoneyManager");
            alert.setHeaderText("Not Complete!!");
            String s ="You need to enter your income/month or have some income.";
            alert.setContentText(s);
            alert.show();
        }else{
            income = Double.parseDouble(tf_income.getText());
            use = 0.4*income;
            must = 0.4*income;
            save = 0.2*income;

            String query = "INSERT INTO test.USER(u_name,u_username,u_pass,u_income,u_use,u_save,u_pay) VALUES (?,?,?,?,?,?,?)";
            try {
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setString(1, name);
                pstmt.setString(2, user);
                pstmt.setString(3, pass);
                pstmt.setDouble(4, income);
                pstmt.setDouble(5, use);
                pstmt.setDouble(6, save);
                pstmt.setDouble(7, must);

                if (pstmt.executeUpdate() > 0) {
                    alert.setTitle("MoneyManager");
                    alert.setHeaderText("Complete");
                    String s = "New User Added!";
                    alert.setContentText(s);
                    alert.show();
                }
                System.out.println("INSERT SUCCESS");
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println(name);
        System.out.println(user);
        System.out.println(pass);
        System.out.println(income);
        System.out.println(use);
        System.out.println(save);
        System.out.println(must);
    }

    public void link1(ActionEvent actionEvent) throws IOException {
        Parent login = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene login_scene = new Scene(login);
        Stage login_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        login_stage.setScene(login_scene);
        login_stage.show();

    }

}
