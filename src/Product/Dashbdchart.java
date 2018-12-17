/*package Product;
import javax.swing.JOptionPane;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.sql.*;
import java.util.ArrayList;

public abstract class Dashbdchart implements Initializable{
    ArrayList<Integer> var = new ArrayList<Integer>();
    ArrayList<String> name = new ArrayList<String>();
    Connection con;
    public void loadData(){
        try{
        con = DriverManager.getConnection("jdbc:mysql://35.196.197.231:3306/test?autoReconnect=true&useSSL=false", "db", "somedb");
        JOptionPane.showMessageDialog(null, "Connected");
        String query = "SELECT * FROM test.TRANS WHERE u_username = ? and  DATE(date) = DATE(?)";
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs;
        rs = ps.executeQuery();
        while (rs.next()){
            rs.getD
        }


        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);

        }
    }
}*/
