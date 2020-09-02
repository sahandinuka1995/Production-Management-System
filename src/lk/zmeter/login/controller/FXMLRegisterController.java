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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.zmeter.login.database.DBConnect;
import lk.zmeter.login.view.Login;
import lk.zmeter.login.view.Login;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author Sahan
 */
public class FXMLRegisterController implements Initializable {

    MyCustomProperty myCustomProperty;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhoneNumber;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtRepeatPassword;

    @FXML
    private ImageView imgPasswordError;

    @FXML
    private ImageView imgPasswordCorrect;

    @FXML
    private ImageView imgUsernameError;

    @FXML
    private ImageView imgUsernameCorrect;

    @FXML
    private ImageView imgRegisterClose;

    @FXML
    private ImageView imgPhoneNoError;

    @FXML
    private Button btnRegister;

    @FXML
    private Label lblLogin;

    Connection conn;

    @FXML
    public void exitPressed(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    public void loginPressed(MouseEvent event) {
        try {
            new Login().start(new Stage());

            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        } catch (IOException ex) {
            Logger.getLogger(FXMLRegisterController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void actionRegister(ActionEvent event) {
        if (validateForm()) {
            try {
                conn = DBConnect.getInstance().getConnection();
                String sql = "INSERT INTO employee (username,name,phone_no,password)"
                        + "VALUES (?,?,?,?)";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, txtUsername.getText());
                pst.setString(2, txtName.getText());
                pst.setInt(3, Integer.parseInt(txtPhoneNumber.getText()));
                pst.setString(4, encryptPassword(txtPassword.getText()));

                int executeUpdate = pst.executeUpdate();
                if (executeUpdate > 0) {
                    myCustomProperty.setAlert("success",
                            "User Registration Successfully!",
                            "You have to wait till admin approval");
                    setAllHide();
                } else {
                    myCustomProperty.setAlert("danger",
                            "Something went wrong!",
                            "Please check database or contact developer");
                }
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(FXMLRegisterController.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        myCustomProperty = new MyCustomProperty();
    }

    private boolean validateForm() {
        if (!"".equals(txtName.getText())
                & !"".equals(txtUsername.getText())
                & !"".equals(txtPhoneNumber.getText())
                & !"".equals(txtPassword.getText())
                & !"".equals(txtRepeatPassword.getText())) {

            if (checkPasswordRepeatPassword() && !checkUsernameInDB() 
                    && checkPhoneNo()) {
                return true;
            }

            if (!checkPhoneNo()) {
                myCustomProperty.setAlert("attention",
                        "Please Check Phone Number",
                        "Phone number should be numbers");
            }
        }
        return false;
    }

    @FXML
    private void checkConfirmed(KeyEvent event) {
        if (!"".equals(txtPassword.getText())) {
            if (!checkPasswordRepeatPassword()) {
                imgPasswordError.setVisible(true);
                imgPasswordCorrect.setVisible(false);
                btnProperty(true);
            } else {
                imgPasswordError.setVisible(false);
                imgPasswordCorrect.setVisible(true);
                btnProperty(false);
            }
        } else {
            imgPasswordError.setVisible(false);
            imgPasswordCorrect.setVisible(false);
        }
    }

    private boolean checkPasswordRepeatPassword() {
        return (txtPassword.getText().equals(txtRepeatPassword.getText()));
    }

    @FXML
    private void checkUsername(KeyEvent event) {
        if (event.getCode() == KeyCode.SPACE) {
            imgUsernameCorrect.setVisible(false);
            imgUsernameError.setVisible(true);
        } else {
            if (!checkUsernameInDB()) {
                imgUsernameCorrect.setVisible(true);
                imgUsernameError.setVisible(false);
            } else {
                imgUsernameCorrect.setVisible(false);
                imgUsernameError.setVisible(true);
            }
        }

        if ("".equals(txtUsername.getText())) {
            imgUsernameCorrect.setVisible(false);
            imgUsernameError.setVisible(false);
        }
    }

    private boolean checkUsernameInDB() {
        try {
            conn = DBConnect.getInstance().getConnection();

            String sql = "SELECT username FROM employee WHERE username=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, txtUsername.getText());

            ResultSet rs = pst.executeQuery();

            return (rs.next());
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLRegisterController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private void setAllHide() {
        //Set textfields to null
        txtUsername.setText("");
        txtName.setText("");
        txtPhoneNumber.setText("");
        txtPassword.setText("");
        txtRepeatPassword.setText("");

        //Set validation images to hide
        imgPasswordCorrect.setVisible(false);
        imgPasswordError.setVisible(false);
        imgUsernameCorrect.setVisible(false);
        imgUsernameError.setVisible(false);
    }

    @FXML
    void eventHoverClose() {
        Glow g = new Glow();
        g.setLevel(2);
        imgRegisterClose.setEffect(g);
    }

    @FXML
    void eventExitClose() {
        imgRegisterClose.setEffect(null);
    }

    void btnProperty(boolean status) {
        if (!"".equals(txtName.getText())
                & !"".equals(txtUsername.getText())
                & !"".equals(txtPhoneNumber.getText())
                & !"".equals(txtPassword.getText())
                & !"".equals(txtRepeatPassword.getText())) {

            btnRegister.setDisable(status);
        }
    }

    private boolean checkPhoneNo() {
        return txtPhoneNumber.getText().matches("[0-9]*");
    }

    @FXML
    void eventCheckPhoneNo(KeyEvent event) {
        if (!"".equals(txtPhoneNumber.getText()) & checkPhoneNo()) {
            btnProperty(false);
            imgPhoneNoError.setVisible(false);
        } else {
            btnProperty(true);
            imgPhoneNoError.setVisible(true);
        }
    }

    @FXML
    void eventHoverLogin(MouseEvent event) {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.PURPLE);
        dropShadow.setOffsetX(0f);
        dropShadow.setOffsetY(0f);
        dropShadow.setHeight(30);
        dropShadow.setWidth(30);

        lblLogin.setEffect(dropShadow);
    }

    @FXML
    void eventExitLogin(MouseEvent event) {
        lblLogin.setEffect(null);
    }

    @FXML
    void eventHoverRegister(MouseEvent event) {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.PURPLE);
        dropShadow.setOffsetX(0f);
        dropShadow.setOffsetY(0f);
        dropShadow.setHeight(30);
        dropShadow.setWidth(30);

        btnRegister.setEffect(dropShadow);
    }

    @FXML
    void eventExitRegister(MouseEvent event) {
        btnRegister.setEffect(null);
    }

    @FXML
    void eventRemoveSpaces(KeyEvent event) {
        if (event.getCode() == KeyCode.SPACE) {
            myCustomProperty.setAlert("attention",
                    "Don't use Space!",
                    "Don't add spaces between username");
        }
    }

    private String encryptPassword(String text) {
        return new PasswordEncrypt().encrypt(text);
    }
}
