package Product;

import Connecter.ConnectSql;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Dashboard implements Initializable {
    public TextField debt_left;
    public TextField month_pay;
    public TextField total_save;
    public Button back;
    public PieChart pieChart;
    public BarChart<String, Double> barChart;
    public Label savelabel;
    public CategoryAxis xAxis;
    public NumberAxis yAxis;
    private String username, name, stuff, data;
    private String password;
    private double necessary, total_saving;
    private double saving, use, rec, period, money_debt;
    private double income;
    private double payment;
    private double etc;
    private int count_etc;
    private double food;
    private int count_food ;
    private double transport;
    private int count_transport;
    private double waterbill;
    private double electbill;
    private double phonebill;
    private double housebill;

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
        if(stuff == null){
            debt_left.setText("Congratulations! you don't have any debt");
        }else{
            debt_left.setText(stuff + " for "+ period + " period.");
        }
        month_pay.setText(Double.toString(necessary) + " Bath");
        savelabel.setText(Double.toString(saving) + " Bath");
    }

    public void setGraph(double income, double payment, double etc, int count_etc, double food, int count_food, double transport, int count_transport, double waterbill, double electbill, double phonebill, double housebill ){
        HashMap<String, Double> hs = new HashMap<>();

        this.income = income;
        this.payment = payment;
        this.etc = etc;
        this.count_etc = count_etc;
        this.transport = transport;
        this.count_transport = count_transport;
        this.food = food;

        hs.put("Food", food);
        hs.put("Transport", transport);
        hs.put("Etc", etc);
        hs.put("Water bill", waterbill);
        hs.put("Electric bill", electbill);
        hs.put("Phone bill", phonebill);
        hs.put("House bill", housebill);



        this.count_food = count_food;
        this.waterbill =waterbill;
        this.electbill = electbill;
        this.phonebill = phonebill;
        this.housebill = housebill;
        System.out.println("ss" + this.income);
        System.out.println("GG" + this.payment);

        xAxis = new CategoryAxis();
        xAxis.setLabel("Item");
        yAxis = new NumberAxis();
        yAxis.setLabel("amount");
//        barChart = new BarChart(xAxis, yAxis);
        barChart.setTitle("Payment");
        for(String s : hs.keySet()){
            double pay = hs.get(s);
            XYChart.Series<String, Double> series = new XYChart.Series<>();
            series.setName(s);
            series.getData().add(new XYChart.Data<>(s, pay));
            barChart.getData().add(series);
        }
//        XYChart.Series<String, Double> series = new XYChart.Series<>();
//        series.setName("Item amount");
//        series.getData().add(new XYChart.Data<>("Food", food));
//        series.getData().add(new XYChart.Data<>("Transport", transport));
//        series.getData().add(new XYChart.Data<>("Etc", etc));
//        series.getData().add(new XYChart.Data<>("Water bill", waterbill));
//        series.getData().add(new XYChart.Data<>("Electric bill", electbill));
//        series.getData().add(new XYChart.Data<>("Phone bill", phonebill));
//        series.getData().add(new XYChart.Data<>("House bill", housebill));
//        barChart.getData().add(series);


        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("payment", this.payment),
                        new PieChart.Data("income", this.income));
        this.pieChart.setTitle("use/save comparison");
        this.pieChart.setData(pieChartData);
    }

    public void  setUsing(Double use, Double rec, Double total_saving){
        this.use = use;
        this.rec = rec;
        this.total_saving = total_saving;
        if(total_saving == 0){
            total_save.setText(Double.toString(this.saving) + " Bath");
        }else{
            total_save.setText(Double.toString(this.total_saving) + " Bath");
        }
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
        home.setAccount(this.username, this.password, this.necessary, this.saving,  this.stuff, this.period, this.money_debt);
        home.setHistory(this.data);

        Parent p = Loader.getRoot();
        Stage home_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        home_stage.setTitle("MoneyManeger");
        home_stage.setScene(new Scene(p));
        home_stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
