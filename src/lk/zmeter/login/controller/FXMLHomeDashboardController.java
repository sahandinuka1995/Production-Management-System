/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.zmeter.login.controller;

import animatefx.animation.FadeIn;
import animatefx.animation.FadeInDown;
import animatefx.animation.ZoomIn;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import static java.time.LocalDateTime.now;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import lk.zmeter.login.database.DBConnect;

/**
 * FXML Controller class
 *
 * @author Sahan
 */
public class FXMLHomeDashboardController implements Initializable {

    @FXML
    private Label lblCustomer;
    @FXML
    private Label lblMeter;
    @FXML
    private Label lblJob;
    @FXML
    private Label lblNotification;

    Connection conn;

    @FXML
    private Pane pnlTotalCustomer;
    @FXML
    private Pane pnlTotalMeter;
    @FXML
    private Pane pnlTotalJob;
    @FXML
    private Pane pnlNotification;
    @FXML
    private Pane pnlExpandNotification;

    @FXML
    private AreaChart<?, ?> chartJobs;
    @FXML
    private CategoryAxis x;
    @FXML
    private NumberAxis y;

    @FXML
    private PieChart pieChart;

    @FXML
    private ListView lvNotification;

    @FXML
    private ImageView imgNotificationImage;

    boolean showPanel = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getTotalCustomers();
        getTotalMeters();
        getTotalJobs();
        getNotifications();
        loadDataToChart();
        loadDataToPieChart();
    }

    private void getTotalCustomers() {
        String sql = "SELECT COUNT(nic) FROM customer";
        lblCustomer.setText(String.valueOf(getTotalAmounts(sql)));
    }

    private void getTotalMeters() {
        String sql = "SELECT COUNT(sn) FROM meter";
        lblMeter.setText(String.valueOf(getTotalAmounts(sql)));
    }

    private void getTotalJobs() {
        String sql = "select COUNT(meter_rep_id) from meter_repair";
        lblJob.setText(String.valueOf(getTotalAmounts(sql)));
    }

    private int getTotalAmounts(String sql) {
        try {
            conn = DBConnect.getInstance().getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

            conn.close();
            pst.close();
            rs.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLHomeDashboardController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @FXML
    void eventHoverTotalCustomer(MouseEvent event) {
        mainButtonEffect(pnlTotalCustomer, "#3877D3");
    }

    @FXML
    void eventExitTotalCustomer(MouseEvent event) {
        pnlTotalCustomer.setEffect(null);
    }

    @FXML
    void eventHoverTotalMeter(MouseEvent event) {
        mainButtonEffect(pnlTotalMeter, "#7C3CD2");
    }

    @FXML
    void eventExitTotalMeter(MouseEvent event) {
        pnlTotalMeter.setEffect(null);
    }

    @FXML
    void eventHoverTotalJob(MouseEvent event) {
        mainButtonEffect(pnlTotalJob, "#3EAE4D");
    }

    @FXML
    void eventExitTotalJob(MouseEvent event) {
        pnlTotalJob.setEffect(null);
    }

    @FXML
    void eventHoverNotification(MouseEvent event) {
        mainButtonEffect(pnlNotification, "#E85D43");
    }

    @FXML
    void eventExitNotification(MouseEvent event) {
        pnlNotification.setEffect(null);
    }

    private void loadDataToChart() {
        AreaChart.Series set1 = new AreaChart.Series<>();

        set1.getData().add(new AreaChart.Data<>("Day 1",
                getJobsByDate(getToday(-5))));
        set1.getData().add(new AreaChart.Data<>("Day 2",
                getJobsByDate(getToday(-4))));
        set1.getData().add(new AreaChart.Data<>("Day 3",
                getJobsByDate(getToday(-3))));
        set1.getData().add(new AreaChart.Data<>("Day 4",
                getJobsByDate(getToday(-2))));
        set1.getData().add(new AreaChart.Data<>("Day 5",
                getJobsByDate(getToday(-1))));
        set1.getData().add(new AreaChart.Data<>("Today",
                getJobsByDate(getToday(0))));
        chartJobs.getData().addAll(set1);
    }

    private void loadDataToPieChart() {

        ObservableList<PieChart.Data> list = FXCollections.observableArrayList();
        list.add(new PieChart.Data("Jobs", noOfJobs()));
        list.add(new PieChart.Data("Completed", noOfCompleted()));
        list.add(new PieChart.Data("QC", noOfQc()));
        list.add(new PieChart.Data("Currier", noOfCurrier()));

        pieChart.setData(list);
    }

    private void mainButtonEffect(Pane pnl, String color) {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.web(color));
        dropShadow.setOffsetX(0f);
        dropShadow.setOffsetY(0f);
        dropShadow.setHeight(30);
        dropShadow.setWidth(30);
        pnl.setEffect(dropShadow);
    }

    private double noOfJobs() {
        try {
            conn = DBConnect.getInstance().getConnection();

            String sql = "SELECT COUNT(meter_rep_id) "
                    + "FROM meter_repair";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLHomeDashboardController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    private double noOfCompleted() {
        try {
            conn = DBConnect.getInstance().getConnection();

            String sql = "SELECT COUNT(meter_rep_id) "
                    + "FROM repair_component";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLHomeDashboardController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    private double noOfQc() {
        try {
            conn = DBConnect.getInstance().getConnection();

            String sql = "SELECT COUNT(qc_id) "
                    + "FROM repair_qc";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLHomeDashboardController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    private double noOfCurrier() {
        try {
            conn = DBConnect.getInstance().getConnection();

            String sql = "SELECT COUNT(currier_no) "
                    + "FROM repair_currier";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLHomeDashboardController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    private double getJobsByDate(String date) {
        try {
            conn = DBConnect.getInstance().getConnection();

            String sql = "SELECT COUNT(meter_rep_id) "
                    + "FROM meter_repair "
                    + "WHERE rep_date LIKE '" + date + "%';";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLHomeDashboardController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    private String getToday(int day) {
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        cal.add(Calendar.DATE, day);
        return dateFormat.format(cal.getTime());
    }

    private void getNotifications() {
        ObservableList<String> notList = FXCollections.observableArrayList();
        int notCount = 0;

        Timeline t = new Timeline(
                new KeyFrame(Duration.seconds(0.5),
                        evt -> alertOn()),
                new KeyFrame(Duration.seconds(1.5),
                        evt -> alertOff()));
        t.setCycleCount(Animation.INDEFINITE);

        if (lowStockWarning() != null) {
            for (int i = 0; i < lowStockWarning().size(); i++) {
                notCount++;
                lblNotification.setText(String.valueOf(notCount));
                notList.add(lowStockWarning().get(i) + " low in stock");
            }

            t.play();
            showPanel = true;
        } else {
            t.stop();
        }

        lvNotification.setItems(notList);
        lvNotification.setCellFactory(param -> new ListCell<String>() {
            private ImageView image = new ImageView();

            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    image.setImage(new Image("/img/Warning Shield_16px.png"));
                    setText(name);
                    setGraphic(image);
                }
            }
        });
    }

    private void alertOn() {
        mainButtonEffect(pnlNotification, "#E85D43");
        imgNotificationImage.setImage(
                new Image("/img/Notification_104px.png"));
    }

    private void alertOff() {
        pnlNotification.setEffect(null);
        imgNotificationImage.setImage(
                new Image("/img/Push Notifications_96px.png"));
    }

    private ArrayList<String> lowStockWarning() {
        ArrayList<String> comList = new ArrayList<String>();
        try {
            conn = DBConnect.getInstance().getConnection();

            String sql = "SELECT * FROM component";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            boolean e = false;

            while (rs.next()) {
                if (rs.getInt(3) < 100) {
                    comList.add(rs.getString(2));
                    e = true;
                }
            }

            if (e) {
                return comList;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLHomeDashboardController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @FXML
    void eventExpandPanel(MouseEvent event) {
        openNotifictionPanel(pnlExpandNotification);
    }

    private void openNotifictionPanel(Pane pnl) {
        if (showPanel) {
            if (pnl.isVisible()) {
                pnl.setDisable(true);
                pnl.setVisible(false);
            } else {
                pnl.setDisable(false);
                pnl.setVisible(true);
            }

            FadeIn down = new FadeIn(pnl);
            down.setSpeed(1);
            down.play();
        }
    }

}
