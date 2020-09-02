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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import lk.zmeter.login.database.DBConnect;
import lk.zmeter.login.model.Customer;
import lk.zmeter.login.model.Employee;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author Sahan
 */
public class FXMLHomeCustomerController implements Initializable {

    Connection conn;
    MyCustomProperty myCustomProperty;
    ObservableList<Employee> employeeDetails;

    @FXML
    private TableView<Customer> tblCustomers;

    @FXML
    private TableColumn<Customer, String> cusNic;
    @FXML
    private TableColumn<Customer, String> cusName;
    @FXML
    private TableColumn<Customer, String> cusAddress;
    @FXML
    private TableColumn<Customer, String> cusPhoneNo;
    @FXML
    private TableColumn<Customer, String> cusVehicleNo;
    @FXML
    private TableColumn<Customer, ImageView> cusIcon;
    @FXML
    private TableColumn delete;

    @FXML
    private TableColumn edit;

    @FXML
    private TextField txtNic;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtPhoneNo;

    @FXML
    private TextField txtVehicleNo;

    @FXML
    private TextField txtUpdateName;

    @FXML
    private TextField txtUpdateAddress;

    @FXML
    private TextField txtUpdatePhoneNo;

    @FXML
    private TextField txtUpdateVehicleNo;

    @FXML
    private TextField txtSearch;

    @FXML
    private ImageView imgFound;
    @FXML
    private ImageView imgNotFound;
    @FXML
    private ImageView imgPhoneNoCorrect;
    @FXML
    private ImageView imgPhoneNoNotCorrect;
    @FXML
    private ImageView imgNotCorrectUpdatePhoneNo;
    @FXML
    private ImageView imgCorrectUpdatePhoneNo;

    ObservableList<Customer> customerList;

    @FXML
    public BorderPane pnlMain;
    @FXML
    private Pane pnlAddCustomer;
    @FXML
    private Pane pnlUpdate;
    @FXML
    private Pane pnlDelete;

    @FXML
    private Label lblUpdateNic;
    @FXML
    private Label lblDeleteCustomerHeader;
    @FXML
    private Label lblDeleteCustomer;

    public void getAllCustomers() {
        try {
            conn = DBConnect.getInstance().getConnection();

            PreparedStatement pst = conn.prepareStatement("SELECT * FROM customer");
            ResultSet rs = pst.executeQuery();

            customerList = FXCollections.observableArrayList();

            customerList.clear();
            while (rs.next()) {
                Button btn = new Button("Edit");
                myCustomProperty.setButtonDefaultEffect(btn);
                myCustomProperty.setButtonEffect(btn);
                ImageView icon = new ImageView("/img/User_32pxz.png");

                Button btnDelete = new Button("Delete");
                myCustomProperty.setDeleteButtonEffect(btnDelete);
                myCustomProperty.setDeleteButtonDefaultEffect(btnDelete);

                if (employeeDetails.get(0).getType() == 1) {
                    btnDelete.setDisable(false);
                } else {
                    btnDelete.setDisable(true);
                }

                Customer cus = new Customer(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        btn,
                        icon,
                        btnDelete);
                customerList.add(cus);

                btn.setOnAction((e) -> {
                    openUpdatePanel(pnlUpdate, cus);
                });

                btnDelete.setOnAction((e) -> {
                    openDeleteConfirmPanel(pnlDelete, cus.getNic(), cus.getName());
                });
            }
            showCustomers(customerList);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLHomeCustomerController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    private void showCustomers(ObservableList<Customer> cusomerList) {
        cusIcon.setCellValueFactory(new PropertyValueFactory<>("cusIcon"));
        cusNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        cusName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cusAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        cusPhoneNo.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));
        cusVehicleNo.setCellValueFactory(new PropertyValueFactory<>("vehicleNo"));
        edit.setCellValueFactory(new PropertyValueFactory<>("btn"));
        delete.setCellValueFactory(new PropertyValueFactory<>("btnDelete"));

        tblCustomers.setItems(cusomerList);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        myCustomProperty = new MyCustomProperty();
        employeeDetails = new UserSession().getEmployee();
        getAllCustomers();
    }

    @FXML
    private void actionAddCustomer(ActionEvent event) {
        openPanel(pnlAddCustomer);
    }

    @FXML
    private void eventSearchCustomer(KeyEvent event) {
        try {
            conn = DBConnect.getInstance().getConnection();

            String search = txtSearch.getText();
            String sql = "SELECT * FROM customer "
                    + "WHERE nic LIKE '" + search + "%' "
                    + "OR phone_no LIKE '" + search + "%' "
                    + "OR vehicle_no LIKE '" + search + "%'";
            Statement pst = conn.createStatement();

            ResultSet rs = pst.executeQuery(sql);
            customerList = FXCollections.observableArrayList();

            while (rs.next()) {
                Button btn = new Button("Edit");
                myCustomProperty.setButtonDefaultEffect(btn);
                myCustomProperty.setButtonEffect(btn);
                ImageView icon = new ImageView("/img/User_32pxz.png");
                
                Button btnDelete = new Button("Delete");
                myCustomProperty.setDeleteButtonEffect(btnDelete);
                myCustomProperty.setDeleteButtonDefaultEffect(btnDelete);

                if (employeeDetails.get(0).getType() == 1) {
                    btnDelete.setDisable(false);
                } else {
                    btnDelete.setDisable(true);
                }
                

                Customer cus = new Customer(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        btn,
                        icon, btnDelete);
                customerList.add(cus);

                btn.setOnAction((e) -> {
                    openUpdatePanel(pnlUpdate, cus);
                });
                
                btnDelete.setOnAction((e) -> {
                    openDeleteConfirmPanel(pnlDelete, cus.getNic(), cus.getName());
                });
            }

            showCustomers(customerList);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLHomeCustomerController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void actionCancel(ActionEvent event) {
        clearAllFields();
        imgFound.setVisible(false);
        imgNotFound.setVisible(false);
        imgPhoneNoCorrect.setVisible(false);
        imgPhoneNoNotCorrect.setVisible(false);
        hideAddPanel(pnlAddCustomer);
    }

    @FXML
    void actionAddNewCustomer(ActionEvent event) {
        if (checkAllFields()) {
            try {
                Connection conn = DBConnect.getInstance().getConnection();

                String sql = "INSERT INTO customer VALUES(?,?,?,?,?)";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, txtNic.getText());
                pst.setString(2, txtName.getText());
                pst.setString(3, txtAddress.getText());
                pst.setString(4, txtPhoneNo.getText());
                pst.setString(5, txtVehicleNo.getText());

                int executeUpdate = pst.executeUpdate();
                if (executeUpdate > 0) {
                    myCustomProperty.setAlert("success",
                            "Customer Added Successfully!",
                            "Added row will highlight");
                    getAllCustomers();
                    lastAdded(txtNic.getText());
                    hideAddPanel(pnlAddCustomer);
                    clearAllFields();
                } else {
                    myCustomProperty.setAlert("danger",
                            "mething went wrong!",
                            "Please check database or contact developer");
                }
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(FXMLHomeCustomerController.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
    }

    private boolean checkAllFields() {
        if (!"".equals(txtNic.getText())
                & !"".equals(txtName.getText())
                & !"".equals(txtAddress.getText())
                & !"".equals(txtPhoneNo.getText())
                & !"".equals(txtVehicleNo.getText())) {

            if (!checkNicFromDb()) {
                if (checkPhoneNo()) {
                    return true;
                } else {
                    myCustomProperty.setAlert("attention",
                            "Please Check Phone No!",
                            "Phone No should be numbers");
                    shakePanel(pnlAddCustomer);
                }
            } else {
                myCustomProperty.setAlert("danger",
                        "This NIC Already Registered!",
                        "Check customer in table or Add with another NIC");
                shakePanel(pnlAddCustomer);
            }
        } else {
            Image i = new Image("/img/Cancel_64px.png", true);
            Notifications n = Notifications.create()
                    .title("Please fill all Fields!")
                    .text("You have to complete all fields to continue")
                    .graphic(new ImageView(i))
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.TOP_RIGHT);

            n.show();

            Shake s = new Shake(pnlAddCustomer);
            s.setSpeed(2);
            s.play();
        }
        return false;
    }

    private boolean checkNicFromDb() {
        try {
            conn = DBConnect.getInstance().getConnection();

            String sql = "SELECT * FROM customer WHERE nic=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, txtNic.getText());
            ResultSet rs = pst.executeQuery();

            return rs.next();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLHomeCustomerController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @FXML
    void eventCheckNic(KeyEvent event) {
        if (checkNicFromDb()) {
            imgNotFound.setVisible(true);
            imgFound.setVisible(false);
        } else {
            imgNotFound.setVisible(false);
            imgFound.setVisible(true);
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

    private void clearAllFields() {
        txtNic.clear();
        txtName.clear();
        txtAddress.clear();
        txtPhoneNo.clear();
        txtVehicleNo.clear();
        imgFound.setVisible(false);
        imgNotFound.setVisible(false);
        imgPhoneNoCorrect.setVisible(false);
        imgPhoneNoNotCorrect.setVisible(false);
    }

    private void lastAdded(String id) {
        for (int i = 0; i < tblCustomers.getItems().size(); i++) {
            if (id.equals(tblCustomers.getItems().get(i).getNic())) {
                tblCustomers.requestFocus();
                tblCustomers.getSelectionModel().select(i);
                tblCustomers.getFocusModel().focus(i);
            }
        }
    }

    @FXML
    void actionUpdateCancel() {
        hideAddPanel(pnlUpdate);
        imgCorrectUpdatePhoneNo.setVisible(false);
        imgNotCorrectUpdatePhoneNo.setVisible(false);
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
    void eventUpdateCustomer() {
        if (checkUpdateAllFields()) {
            try {
                conn = DBConnect.getInstance().getConnection();

                String sql = "UPDATE customer "
                        + "SET name=?, address=?, phone_no=?, vehicle_no=? "
                        + "WHERE nic=?";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, txtUpdateName.getText());
                pst.setString(2, txtUpdateAddress.getText());
                pst.setString(3, txtUpdatePhoneNo.getText());
                pst.setString(4, txtUpdateVehicleNo.getText());
                pst.setString(5, lblUpdateNic.getText());

                int executeUpdate = pst.executeUpdate();
                if (executeUpdate > 0) {
                    myCustomProperty.setAlert("success",
                            "Customer Updated Successfully!",
                            txtUpdateName.getText() + " updated row will highlight");
                    getAllCustomers();
                    lastAdded(lblUpdateNic.getText());
                    hideAddPanel(pnlUpdate);
                    clearAllUpdateFields();
                } else {
                    myCustomProperty.setAlert("danger",
                            "Something went wrong!",
                            "Please check database or contact developer");
                }
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(FXMLHomeCustomerController.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
    }

    private boolean checkUpdateAllFields() {
        if (!"".equals(txtUpdateName.getText())
                & !"".equals(txtUpdateAddress.getText())
                & !"".equals(txtUpdatePhoneNo.getText())
                & !"".equals(txtUpdateVehicleNo.getText())) {

            if (checkUpdatePhoneNo()) {
                return true;
            } else {
                myCustomProperty.setAlert("attention",
                        "Please Check Phone No!",
                        "Phone no should be numbers");
                shakePanel(pnlUpdate);
            }
        } else {
            myCustomProperty.setAlert("danger",
                    "Please fill all Fields!",
                    "You have to complete all fields to continue");

            shakePanel(pnlUpdate);
        }
        return false;
    }

    private void clearAllUpdateFields() {
        txtUpdateName.clear();
        txtUpdateAddress.clear();
        txtUpdatePhoneNo.clear();
        txtUpdateVehicleNo.clear();
    }

    private void openUpdatePanel(Pane pnl, Customer cus) {
        GaussianBlur blur = new GaussianBlur(10);
        pnlMain.setEffect(blur);

        lblUpdateNic.setText(cus.getNic());
        pnl.setDisable(false);
        pnl.setVisible(true);
        ZoomIn in = new ZoomIn(pnl);
        in.setSpeed(2);
        in.play();

        txtUpdateName.setText(cus.getName());
        txtUpdateAddress.setText(cus.getAddress());
        txtUpdatePhoneNo.setText(cus.getPhoneNo());
        txtUpdateVehicleNo.setText(cus.getVehicleNo());
    }

    @FXML
    void eventCheckPhoneNo(KeyEvent event) {
        checkPhoneNo();
    }

    private boolean checkPhoneNo() {
        if (txtPhoneNo.getText().matches("[0-9]*")) {
            imgPhoneNoCorrect.setVisible(true);
            imgPhoneNoNotCorrect.setVisible(false);
            return true;
        } else {
            imgPhoneNoCorrect.setVisible(false);
            imgPhoneNoNotCorrect.setVisible(true);
        }
        return false;
    }

    private void shakePanel(Pane pnl) {
        Shake s = new Shake(pnl);
        s.setSpeed(2);
        s.play();
    }

    @FXML
    void eventCheckUpdatePhoneNo(KeyEvent event) {
        checkUpdatePhoneNo();
    }

    private boolean checkUpdatePhoneNo() {
        if (txtUpdatePhoneNo.getText().matches("[0-9]*")) {

            imgCorrectUpdatePhoneNo.setVisible(true);
            imgNotCorrectUpdatePhoneNo.setVisible(false);
            return true;
        } else {
            imgCorrectUpdatePhoneNo.setVisible(false);
            imgNotCorrectUpdatePhoneNo.setVisible(true);
        }
        return false;
    }

    private void openDeleteConfirmPanel(Pane pnl, String nic, String name) {
        GaussianBlur blur = new GaussianBlur(10);
        pnlMain.setEffect(blur);

        lblDeleteCustomer.setText(nic);
        lblDeleteCustomerHeader.setText(name);

        pnl.setDisable(false);
        pnl.setVisible(true);
        ZoomIn in = new ZoomIn(pnl);
        in.setSpeed(2);
        in.play();
    }
    
    @FXML
    void eventDeleteCustomerCancel(ActionEvent event){
        hideAddPanel(pnlDelete);
    }
    
    @FXML
    void eventDeleteCustomer(ActionEvent event){
        try {
            conn = DBConnect.getInstance().getConnection();
            
            String sql="DELETE FROM customer "
                    + "WHERE nic=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, lblDeleteCustomer.getText());
            int executeUpdate = pst.executeUpdate();
            
            if (executeUpdate>0) {
                myCustomProperty.setAlert("success",
                        "Customer Removed Successfully!",
                        "You can't undo this operation");
                hideAddPanel(pnlDelete);
                getAllCustomers();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLHomeCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
