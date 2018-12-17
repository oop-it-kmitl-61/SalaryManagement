package sample;

import Connecter.ConnectSql;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Controller {
    public TextField tfUser;
    public TextField tfPass;
    public Button bnSubmit;
    public Button bnCancle;

    public void bnSubmit(ActionEvent actionEvent) {
        ConnectSql connectSql = new ConnectSql();
        Connection connection = connectSql.GetConnect();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs =  stmt.executeQuery("SELECT name FROM USER2 WHERE age = 15");
            while (rs.next()){
                String name = rs.getString(1);
                tfUser.setText(name);
            }
            stmt.close();
            System.out.println("connected");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void bnCancle(ActionEvent actionEvent) {

    }
}
