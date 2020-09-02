/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.zmeter.login.controller;

import animatefx.animation.Shake;
import animatefx.animation.ZoomIn;
import animatefx.animation.ZoomOut;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import lk.zmeter.login.database.DBConnect;
import lk.zmeter.login.model.Component;
import lk.zmeter.login.model.Employee;

/**
 * FXML Controller class
 *
 * @author Sahan
 */
public class FXMLHomeUserController implements Initializable {

    Connection conn;
    ObservableList<Employee> employeeList;
    MyCustomProperty myCustomProperty;
    ObservableList<Employee> employeeDetails;

    @FXML
    private Pane pnlDelete;
    @FXML
    private BorderPane pnlMain;
    @FXML
    private Pane pnlAddUser;
    @FXML
    private Pane pnlUpdate;

    @FXML
    private TextField txtSearch;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtPhoneNo;
    @FXML
    private TextField txtUpdatePassword;

    @FXML
    private ComboBox<String> cbType;
    @FXML
    private ComboBox<String> cbUpdateType;

    @FXML
    private TableView<Employee> tblUsers;
    @FXML
    private TableColumn<?, ?> userIcon;
    @FXML
    private TableColumn<?, ?> username;
    @FXML
    private TableColumn<?, ?> name;
    @FXML
    private TableColumn<Employee, String> password;
    @FXML
    private TableColumn<?, ?> phoneNo;
    @FXML
    private TableColumn<?, ?> last_log;
    @FXML
    private TableColumn<Employee, String> type;
    @FXML
    private TableColumn<?, ?> first_login;
    @FXML
    private TableColumn<?, ?> delete;
    @FXML
    private TableColumn<?, ?> edit;

    @FXML
    private Button btnAdd;

    @FXML
    private Label lblDeleteUserHeader;
    @FXML
    private Label lblDeleteUser;
    @FXML
    private Label lblUserUpdateHeader;

    @FXML
    private ImageView imgFound;
    @FXML
    private ImageView imgNotFound;

    @FXML
    void actionAddUser(ActionEvent event) {
        openPanel(pnlAddUser);
        loadUserType();
    }

    @FXML
    void eventSearchUser(KeyEvent event) {
        try {
            conn = DBConnect.getInstance().getConnection();

            String search = txtSearch.getText();
            String sql = "SELECT * FROM employee "
                    + "WHERE username LIKE '" + search + "%' "
                    + "OR phone_no LIKE '" + search + "%'";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            employeeList = FXCollections.observableArrayList();

            while (rs.next()) {
                ImageView icon = new ImageView("/img/" + rs.getString(7) + ".png");
                icon.setFitHeight(25);
                icon.setFitWidth(25);

                Button btnDelete = new Button("Delete");
                myCustomProperty.setDeleteButtonEffect(btnDelete);
                myCustomProperty.setDeleteButtonDefaultEffect(btnDelete);

                if (employeeDetails.get(0).getType() == 1) {
                    if (rs.getBoolean(8)) {
                        btnDelete.setDisable(true);
                    }
                } else {
                    btnDelete.setDisable(true);
                }

                Employee emp = new Employee(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(8),
                        setAccept(rs.getBoolean(9), rs.getInt(1)),
                        icon, btnDelete, setEditBtn(rs.getInt(1), rs.getString(4)));

                btnDelete.setOnAction((e) -> {
                    openDeleteConfirmPanel(pnlDelete, emp.getId(), emp.getUsername());
                });

                employeeList.add(emp);
            }
            showComponent(employeeList);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLHomeComponentController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        employeeDetails = new UserSession().getEmployee();
        myCustomProperty = new MyCustomProperty();
        getAllUsers();
    }

    private void getAllUsers() {
        try {
            conn = DBConnect.getInstance().getConnection();

            String sql = "SELECT * FROM employee";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            employeeList = FXCollections.observableArrayList();

            while (rs.next()) {
                ImageView icon = new ImageView("/img/" + rs.getString(7) + ".png");
                icon.setFitHeight(25);
                icon.setFitWidth(25);

                Button btnDelete = new Button("Delete");
                myCustomProperty.setDeleteButtonEffect(btnDelete);
                myCustomProperty.setDeleteButtonDefaultEffect(btnDelete);

                if (employeeDetails.get(0).getType() == 1) {
                    if (rs.getBoolean(8)) {
                        btnDelete.setDisable(true);
                    }
                } else {
                    btnDelete.setDisable(true);
                }

                Employee emp = new Employee(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(8),
                        setAccept(rs.getBoolean(9), rs.getInt(1)),
                        icon, btnDelete, setEditBtn(rs.getInt(1), rs.getString(4)));

                btnDelete.setOnAction((e) -> {
                    openDeleteConfirmPanel(pnlDelete, emp.getId(), emp.getUsername());
                });

                employeeList.add(emp);
            }
            showComponent(employeeList);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLHomeComponentController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    private void showComponent(ObservableList<Employee> employeeList) {
        userIcon.setCellValueFactory(new PropertyValueFactory<>("userImage"));
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        password.setCellValueFactory(film -> {
            SimpleStringProperty property = new SimpleStringProperty();
            String noOfChat = "";
            for (int i = 0; i < film.getValue().getPassword().length(); i++) {
                noOfChat += "●";
            }
            property.setValue(noOfChat);
            return property;
        });

        phoneNo.setCellValueFactory(new PropertyValueFactory<>("phone_no"));
        last_log.setCellValueFactory(new PropertyValueFactory<>("last_log"));

        type.setCellValueFactory(film -> {
            SimpleStringProperty property = new SimpleStringProperty();

            property.setValue(film.getValue().getType() == 1 ? "Admin" : "User");
            return property;
        });

        first_login.setCellValueFactory(new PropertyValueFactory<>("first_login"));
        edit.setCellValueFactory(new PropertyValueFactory<>("edit"));
        delete.setCellValueFactory(new PropertyValueFactory<>("btnDelete"));

        tblUsers.setItems(employeeList);
    }

    private Button setAccept(boolean aBoolean, int userId) {
        Button accept = new Button("Accept");
        myCustomProperty.setAcceptButtonDefaultEffect(accept);
        myCustomProperty.setAcceptButtonEffect(accept);

        if (aBoolean) {
            accept.setDisable(true);
            accept.setText("Accepted");
            myCustomProperty.setAcceptButtonDefaultDisableEffect(accept);
        }

        accept.setOnAction((e) -> {
            acceptUser(userId);
        });
        return accept;
    }

    private void acceptUser(int userId) {
        try {
            conn = DBConnect.getInstance().getConnection();

            String sql = "UPDATE employee SET first_login=? "
                    + "WHERE id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setBoolean(1, true);
            pst.setInt(2, userId);
            int executeUpdate = pst.executeUpdate();

            if (executeUpdate > 0) {
                myCustomProperty.setAlert("success", "User Accepted!", "Now user can login to the System");
                getAllUsers();
            } else {
                myCustomProperty.setAlert("danger", "User Not Accepted!", "Something went wrong");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLHomeUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void eventDeleteUser(ActionEvent event) {
        try {
            conn = DBConnect.getInstance().getConnection();

            String sql = "DELETE FROM employee "
                    + "WHERE id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, lblDeleteUserHeader.getText());
            int executeUpdate = pst.executeUpdate();

            if (executeUpdate > 0) {
                myCustomProperty.setAlert("success",
                        "User Removed Successfully!",
                        "You can't undo this operation");
                hideAllPanel(pnlDelete);
                getAllUsers();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLHomeCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void eventDeleteUserCancel(ActionEvent event) {
        hideAllPanel(pnlDelete);
    }

    private void openDeleteConfirmPanel(Pane pnl, int id, String username) {
        GaussianBlur blur = new GaussianBlur(10);
        pnlMain.setEffect(blur);

        lblDeleteUser.setText(username);
        lblDeleteUserHeader.setText(String.valueOf(id));
        pnl.setDisable(false);
        pnl.setVisible(true);

        ZoomIn in = new ZoomIn(pnl);
        in.setSpeed(2);
        in.play();
    }

    private void hideAllPanel(Pane pnl) {
        pnlMain.setEffect(null);

        ZoomOut out = new ZoomOut(pnl);
        out.setSpeed(2);
        out.play();

        pnl.setVisible(false);
        pnl.setDisable(true);
    }

    @FXML
    void actionCancelAddUser(ActionEvent event) {
        hideAllPanel(pnlAddUser);
    }

    @FXML
    void actionAddNewUser(ActionEvent event) {
        if (checkAllAddFields()) {
            try {
                conn = DBConnect.getInstance().getConnection();

                String sql = "INSERT INTO employee "
                        + "(username,name,password,phone_no,type,first_login) "
                        + "VALUES (?,?,?,?,?,?)";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, txtUsername.getText());
                pst.setString(2, txtName.getText());
                pst.setString(3, encryptPassword(txtPassword.getText()));
                pst.setString(4, txtPhoneNo.getText());
                pst.setInt(5, getType(cbType.getSelectionModel().getSelectedItem()));
                pst.setBoolean(6, true);
                int executeUpdate = pst.executeUpdate();

                if (executeUpdate > 0) {
                    myCustomProperty.setAlert("success",
                            "User Added Successfully!",
                            "Added user will highlight");
                    clearAddAllFields();
                    hideAllPanel(pnlAddUser);
                    getAllUsers();
                }
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(FXMLHomeUserController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
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
    void eventCheckUser(KeyEvent event) {
        if (!checkUserName()) {
            imgFound.setVisible(true);
            imgNotFound.setVisible(false);
        } else {
            imgFound.setVisible(false);
            imgNotFound.setVisible(true);
        }
    }

    private boolean checkUserName() {
        try {
            conn = DBConnect.getInstance().getConnection();

            String sql = "SELECT username FROM employee "
                    + "WHERE username=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, txtUsername.getText());
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLHomeUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private boolean checkAllAddFields() {
        if (!txtUsername.getText().isEmpty()
                & !txtName.getText().isEmpty()
                & !txtPassword.getText().isEmpty()
                & !txtPhoneNo.getText().isEmpty()
                & !cbType.getSelectionModel().isEmpty()) {

            if (!checkUserName()) {
                if (txtPhoneNo.getText().matches("[0-9]*")) {
                    return true;
                } else {
                    myCustomProperty.setAlert("attention",
                            "Please Check Phone Number",
                            "Phone number should be numbers");
                    shakePanel(pnlAddUser);
                }
            } else {
                myCustomProperty.setAlert("attention",
                        "Wrong Username!",
                        "User already registered in this NIC");
            }
        } else {
            myCustomProperty.setAlert("danger",
                    "Please fill all Fields!",
                    "You have to complete all fields to continue");
            shakePanel(pnlAddUser);
        }
        return false;
    }

    private void shakePanel(Pane pnl) {
        Shake s = new Shake(pnl);
        s.setSpeed(2);
        s.play();
    }

    private void loadUserType() {
        ObservableList<String> type = FXCollections.observableArrayList();
        type.add("Admin");
        type.add("User");
        cbType.setItems(type);
    }

    private int getType(String selectedItem) {
        if (selectedItem == "Admin") {
            return 1;
        } else {
            return 0;
        }
    }

    private void clearAddAllFields() {
        txtUsername.setText(null);
        txtName.setText(null);
        txtPassword.setText(null);
        txtPhoneNo.setText(null);
        cbType.getSelectionModel().clearSelection();
    }

    private Button setEditBtn(int id, String pass) {
        Button btn = new Button("Edit");
        myCustomProperty.setButtonDefaultEffect(btn);
        myCustomProperty.setButtonEffect(btn);

        btn.setOnAction((e) -> {
            openEditUserPanel(id, pass, pnlUpdate);
        });

        return btn;
    }

    private void openEditUserPanel(int id, String pass, Pane pnl) {
        GaussianBlur blur = new GaussianBlur(10);
        pnlMain.setEffect(blur);

        lblUserUpdateHeader.setText(String.valueOf(id));
        txtUpdatePassword.setText(pass);
        
        ObservableList<String> type = FXCollections.observableArrayList();
        type.add("Admin");
        type.add("User");
        cbUpdateType.setItems(type);

        pnl.setDisable(false);
        pnl.setVisible(true);
        ZoomIn in = new ZoomIn(pnl);
        in.setSpeed(2);
        in.play();
    }

    @FXML
    void actionCancelUpdateUser(ActionEvent event) {
        hideAllPanel(pnlUpdate);
    }

    @FXML
    void actionUpdateUser(ActionEvent event) {
        if (checkUpdateFields()) {
            try {
                conn = DBConnect.getInstance().getConnection();

                String sql = "UPDATE employee SET password=?, type=? "
                        + "WHERE id=?";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, txtUpdatePassword.getText());
                pst.setInt(2, getType(cbUpdateType.getSelectionModel().getSelectedItem()));
                pst.setInt(3, Integer.parseInt(lblUserUpdateHeader.getText()));
                int executeUpdate = pst.executeUpdate();

                if (executeUpdate > 0) {
                    myCustomProperty.setAlert("success",
                            "User Update Successfully!",
                            "Updated user row will highlight");
                    clearAllUpdateFields();
                    getAllUsers();
                    hideAllPanel(pnlUpdate);
                }
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(FXMLHomeUserController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private boolean checkUpdateFields() {
        if (!txtUpdatePassword.getText().isEmpty()
                & cbUpdateType.getSelectionModel().getSelectedItem()!=null) {
            return true;
        } else {
            myCustomProperty.setAlert("danger",
                    "Please fill all Fields!",
                    "You have to complete all fields to continue");
            shakePanel(pnlUpdate);
        }
        return false;
    }

    private void clearAllUpdateFields() {
        txtUpdatePassword.setText(null);
        cbUpdateType.getSelectionModel().clearSelection();
    }

    private String encryptPassword(String text) {
        return new PasswordEncrypt().encrypt(text);
    }
}
