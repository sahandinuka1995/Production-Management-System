/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.zmeter.login.controller;

import animatefx.animation.FadeIn;
import animatefx.animation.ZoomIn;
import animatefx.animation.ZoomOut;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import lk.zmeter.login.database.DBConnect;

/**
 * FXML Controller class
 *
 * @author Sahan
 */
public class FXMLUserAccountController implements Initializable {

    @FXML
    private Pane pnlEditProfile;
    @FXML
    private Pane pnlChangeImage;
    @FXML
    private Pane pnlMain;

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtName;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private PasswordField txtRepeatPassword;
    @FXML
    private TextField txtPhoneNo;

    @FXML
    private Label lblName;
    @FXML
    private Label lblLastLog;

    private String userName;

    Connection conn;

    MyCustomProperty myCustomProperty;

    @FXML
    private Button btnSave;
    @FXML
    private Button btnEditProfile;
    @FXML
    private Button btnAddPhoto;
    @FXML
    private Button btnCancelEditProfile;

    @FXML
    private ImageView imgProfile;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        myCustomProperty = new MyCustomProperty();
        setUsername();
        loadUserData();
    }

    @FXML
    void actionEditProfile(ActionEvent event) {
        pnlEditProfile.setDisable(false);

        btnEditProfile.setDisable(true);
        btnEditProfile.setVisible(false);

        btnSave.setDisable(false);
        btnSave.setVisible(true);

        btnCancelEditProfile.setDisable(false);
        btnCancelEditProfile.setVisible(true);
    }

    @FXML
    void actionSave(ActionEvent event) {
        if (saveInDB()) {
            lblName.setText(txtName.getText());
            pnlEditProfile.setDisable(true);

            btnEditProfile.setDisable(false);
            btnEditProfile.setVisible(true);

            btnSave.setDisable(true);
            btnSave.setVisible(false);
        }
    }

    private void loadUserData() {
        try {
            conn = DBConnect.getInstance().getConnection();

            String sql = "SELECT * FROM employee WHERE username=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, userName);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                setFields(rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7));
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLUserAccountController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

    }

    public void setUsername() {
        UserSession session = new UserSession();
        userName = session.getUsername();
    }

    private void setFields(String id, String username, String name,
            String password, String phNo, String lastLog, String image) {

        imgProfile.setImage(new Image("/img/" + image + ".png"));
        new FXMLHomeController().setUserImage(image);

        lblName.setText(name);
        txtId.setText(id);
        txtUsername.setText(username);
        txtName.setText(name);
        txtPassword.setText(password);
        txtRepeatPassword.setText(password);
        txtPhoneNo.setText(phNo);

        loadUserLastLog(lastLog);
    }

    private boolean saveInDB() {
        if (checkAllFields()) {
            try {
                conn = DBConnect.getInstance().getConnection();

                String sql = "UPDATE employee "
                        + "SET username=?, name=?, password=?, phone_no=? "
                        + "WHERE id=?";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, txtUsername.getText());
                pst.setString(2, txtName.getText());
                pst.setString(3, txtRepeatPassword.getText());
                pst.setString(4, txtPhoneNo.getText());
                pst.setString(5, txtId.getText());
                int executeUpdate = pst.executeUpdate();

                if (executeUpdate > 0) {
                    myCustomProperty.setAlert("success",
                            "User Update Successfully!",
                            "Your all details save in Database");
                    return true;
                }
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(FXMLUserAccountController.class.getName())
                        .log(Level.SEVERE, null, ex);
            }

        }
        return false;
    }

    private boolean checkAllFields() {
        if (!"".equals(txtId.getText())
                & !"".equals(txtUsername.getText())
                & !"".equals(txtName.getText())
                & !"".equals(txtPassword.getText())
                & !"".equals(txtRepeatPassword.getText())
                & !"".equals(txtPhoneNo.getText())) {

            if (txtPassword.getText().equals(txtRepeatPassword.getText())) {
                if (txtPhoneNo.getText().matches("[0-9]*")) {
                    return true;
                } else {
                    myCustomProperty.setAlert("attention",
                            "Please Check Your Phone Number!",
                            "Phone Number should be numbers");
                }
            } else {
                myCustomProperty.setAlert("danger",
                        "Password Error!",
                        "Password and Repeat Password should be same");
            }
        } else {
            myCustomProperty.setAlert("danger",
                    "Please Check All Fields!",
                    "You have to fill all fields to continue");
        }
        return false;
    }

    @FXML
    void eventRemoveSpaces(KeyEvent event) {
        if (event.getCode() == KeyCode.SPACE) {
            myCustomProperty.setAlert("attention",
                    "Don't use Space!",
                    "Don't add spaces between username");
        }
    }

    @FXML
    void eventChangeIcon(MouseEvent event) {
        btnAddPhoto.setDisable(false);
        btnAddPhoto.setVisible(true);
    }

    @FXML
    void actionOpenChangeImage(ActionEvent event) {
        openPanel(pnlChangeImage);
    }

    private void openPanel(Pane pnl) {
        GaussianBlur blur = new GaussianBlur(10);
        pnlMain.setEffect(blur);

        pnl.setDisable(false);
        pnl.setVisible(true);
        ZoomIn in = new ZoomIn(pnl);
        in.setSpeed(2);
        in.play();
    }

    @FXML
    void chooseImage1(ActionEvent event) {
        if (setProfileImage("boy1")) {
            hideAddPanel(pnlChangeImage);
            loadUserData();
        }
    }

    @FXML
    void chooseImage2(ActionEvent event) {
        if (setProfileImage("boy2")) {
            hideAddPanel(pnlChangeImage);
            loadUserData();
        }
    }

    @FXML
    void chooseImage3(ActionEvent event) {
        if (setProfileImage("boy3")) {
            hideAddPanel(pnlChangeImage);
            loadUserData();
        }
    }

    @FXML
    void chooseImage4(ActionEvent event) {
        if (setProfileImage("girl1")) {
            hideAddPanel(pnlChangeImage);
            loadUserData();
        }
    }

    @FXML
    void chooseImage5(ActionEvent event) {
        if (setProfileImage("girl2")) {
            hideAddPanel(pnlChangeImage);
            loadUserData();
        }
    }

    @FXML
    void chooseImage6(ActionEvent event) {
        if (setProfileImage("girl3")) {
            hideAddPanel(pnlChangeImage);
            loadUserData();
        }
    }

    private void hideAddPanel(Pane pnl) {
        pnlMain.setEffect(null);

        ZoomOut out = new ZoomOut(pnl);
        out.setSpeed(2);
        out.play();

        pnl.setVisible(false);
        pnl.setDisable(true);
    }

    private boolean setProfileImage(String name) {
        try {
            conn = DBConnect.getInstance().getConnection();

            String sql = "UPDATE employee SET user_image_id=? "
                    + "WHERE id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, txtId.getText());
            int executeUpdate = pst.executeUpdate();

            if (executeUpdate > 0) {
                myCustomProperty.setAlert("success",
                        "Profile Picture Updated Successfully!",
                        "Profile image will display in next login");
                return true;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLUserAccountController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @FXML
    void actionCancelEditProfile(ActionEvent event) {
        pnlEditProfile.setDisable(true);

        btnEditProfile.setDisable(false);
        btnEditProfile.setVisible(true);

        btnSave.setDisable(true);
        btnSave.setVisible(false);

        btnCancelEditProfile.setDisable(true);
        btnCancelEditProfile.setVisible(false);
        
        loadUserData();
    }

    @FXML
    void actionCancelProfilePictureWindow(ActionEvent event) {
        hideAddPanel(pnlChangeImage);
    }

    private void loadUserLastLog(String last) {
        Task<Void> sleeper1 = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                }
                return null;
            }
        };

        sleeper1.setOnSucceeded((WorkerStateEvent event) -> {
            lblLastLog.setText(last);
            FadeIn fadeIn = new FadeIn(lblLastLog);
            fadeIn.setSpeed(1);
            fadeIn.play();
        });
        new Thread(sleeper1).start();
    }
}
