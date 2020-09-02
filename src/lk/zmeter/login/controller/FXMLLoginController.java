/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.zmeter.login.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.zmeter.login.database.DBConnect;
import lk.zmeter.login.model.Employee;
import lk.zmeter.login.view.Home;
import lk.zmeter.login.view.Register;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author Sahan
 */
public class FXMLLoginController implements Initializable {

    MyCustomProperty myCustomProperty;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    @FXML
    private Button btnLogin;

    Connection conn;
    boolean isFound = false;

    public FXMLHomeController homeController;

    @FXML
    private ImageView imgLoginClose;

    @FXML
    private Label lblRegister;

    @FXML
    public void exitPressed(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    public void actionLogin(ActionEvent event) {
        openHomeWindow(event);
    }

    @FXML
    void eventLoginEnter(KeyEvent event) {
        openHomeWindow(event);
    }

    private void openHomeWindow(Event event) {
        if (isFound) {
            try {
                if (setLastLogged(txtUsername.getText())) {
                    new Home().start(new Stage());
                    ((Stage) ((Node) event.getSource()).getScene()
                            .getWindow()).close();
                }
            } catch (IOException ex) {
                Logger.getLogger(FXMLLoginController.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    public void registerPressed(MouseEvent event) {
        try {
            new Register().start(new Stage());

            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        } catch (IOException ex) {
            Logger.getLogger(FXMLLoginController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        myCustomProperty = new MyCustomProperty();
    }

    @FXML
    private void checkUsernamePassword(KeyEvent event) {
        try {
            conn = DBConnect.getInstance().getConnection();

            String sql = "SELECT * FROM employee "
                    + "WHERE username=? AND password=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, txtUsername.getText());
            pst.setString(2, encryptPassword(txtPassword.getText()));

            ResultSet rs = pst.executeQuery();
            ObservableList<Employee> employeeList = FXCollections.observableArrayList();

            if (rs.next()) {
                employeeList.add(new Employee(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getInt(8),
                        null));

                if (rs.getBoolean(9)) {
                    enablePermission();
                    setUserData(employeeList);
                } else {
                    myCustomProperty.setAlert("danger",
                            "Access Denied!",
                            "Admin must approve your registration");
                }
            } else {
                btnLogin.setDisable(true);
                isFound = false;
            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLLoginController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void eventHoverClose(MouseEvent event) {
        Glow g = new Glow();
        g.setLevel(2);
        imgLoginClose.setEffect(g);
    }

    @FXML
    void eventExitClose(MouseEvent event) {
        Glow g = new Glow();
        g.setLevel(0);
        imgLoginClose.setEffect(g);
    }

    @FXML
    void eventHoverRegister(MouseEvent event) {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.PURPLE);
        dropShadow.setOffsetX(0f);
        dropShadow.setOffsetY(0f);
        dropShadow.setHeight(30);
        dropShadow.setWidth(30);

        lblRegister.setEffect(dropShadow);
    }

    @FXML
    void eventExitRegister(MouseEvent event) {
        lblRegister.setEffect(null);
    }

    @FXML
    void eventHoverLogin(MouseEvent event) {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.PURPLE);
        dropShadow.setOffsetX(0f);
        dropShadow.setOffsetY(0f);
        dropShadow.setHeight(30);
        dropShadow.setWidth(30);

        btnLogin.setEffect(dropShadow);
    }

    @FXML
    void eventExitLogin(MouseEvent event) {
        btnLogin.setEffect(null);
    }

    private void enablePermission() {
        btnLogin.setDisable(false);
        isFound = true;
        myCustomProperty.setAlert("success",
                "Login details are Correct!",
                "You can click login button and enter to the system");
    }

    private boolean setLastLogged(String username) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        try {
            conn = DBConnect.getInstance().getConnection();

            String sql = "UPDATE employee "
                    + "SET last_log=? "
                    + "WHERE username=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, dtf.format(now));
            pst.setString(2, username);
            int executeUpdate = pst.executeUpdate();

            if (executeUpdate > 0) {
                return true;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLLoginController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private void setUserData(ObservableList<Employee> employeeList) {
        new UserSession(employeeList);
    }

    private String encryptPassword(String text) {
        return new PasswordEncrypt().encrypt(text);
    }
}
