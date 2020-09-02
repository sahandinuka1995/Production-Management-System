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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import lk.zmeter.login.database.DBConnect;
import lk.zmeter.login.model.Component;
import lk.zmeter.login.model.Employee;

/**
 * FXML Controller class
 *
 * @author Sahan
 */
public class FXMLHomeComponentController implements Initializable {

    MyCustomProperty myCustomProperty;
    ObservableList<Employee> employeeDetails;

    @FXML
    private TextField txtSearch;
    @FXML
    private TextField txtComId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtQty;
    @FXML
    private TextField txtUnitPrice;
    @FXML
    private TextField txtUpdateQty;
    @FXML
    private TextField txtUpdateUnitPrice;

    @FXML
    private TableView<Component> tblComponent;
    @FXML
    private TableColumn<Component, String> componentId;
    @FXML
    private TableColumn<Component, String> name;
    @FXML
    private TableColumn<Component, String> qty;
    @FXML
    private TableColumn<Component, Button> edit;
    @FXML
    private TableColumn<Component, String> unitPrice;
    @FXML
    private TableColumn comIcon;
    @FXML
    private TableColumn delete;

    Connection conn;
    ObservableList<Component> componentList;

    @FXML
    private Pane pnlMain;
    @FXML
    private Pane pnlAddComponent;
    @FXML
    private Pane pnlUpdate;
    @FXML
    private Pane pnlDelete;

    @FXML
    private Label lblHeader;
    @FXML
    private Label lblDeleteComponentHeader;
    @FXML
    private Label lblDeleteComponent;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        employeeDetails = new UserSession().getEmployee();
        myCustomProperty = new MyCustomProperty();
        getAllComponent();
    }

    private void getAllComponent() {
        try {
            conn = DBConnect.getInstance().getConnection();

            String sql = "SELECT * FROM component";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            componentList = FXCollections.observableArrayList();

            while (rs.next()) {
                Button btn = new Button("Edit");
                MyCustomProperty editButton = new MyCustomProperty();
                editButton.setButtonDefaultEffect(btn);
                editButton.setButtonEffect(btn);
                ImageView icon = new ImageView("/img/ic_32px.png");

                Button btnDelete = new Button("Delete");
                myCustomProperty.setDeleteButtonEffect(btnDelete);
                myCustomProperty.setDeleteButtonDefaultEffect(btnDelete);

                if (employeeDetails.get(0).getType() == 1) {
                    btnDelete.setDisable(false);
                } else {
                    btnDelete.setDisable(true);
                }

                Component com = new Component(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        btn,
                        rs.getInt(4),
                        icon, btnDelete);

                btn.setOnAction((ActionEvent e) -> {
                    openUpdatePanel(pnlUpdate,
                            com.getComId(),
                            com.getQty(),
                            com.getUnitPrice());
                });

                btnDelete.setOnAction((e) -> {
                    openDeleteConfirmPanel(pnlDelete, com.getComId());
                });

                componentList.add(com);
            }
            showComponent(componentList);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLHomeComponentController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    private void showComponent(ObservableList<Component> componentList) {
        comIcon.setCellValueFactory(new PropertyValueFactory<>("comIcon"));
        componentId.setCellValueFactory(new PropertyValueFactory<>("comId"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        qty.setCellValueFactory(new PropertyValueFactory<>("qty"));

        unitPrice.setCellValueFactory(film -> {
            SimpleStringProperty property = new SimpleStringProperty();

            property.setValue("Rs." + film.getValue().getUnitPrice());
            return property;
        });

        edit.setCellValueFactory(new PropertyValueFactory<>("btn"));
        delete.setCellValueFactory(new PropertyValueFactory<>("btnDelete"));

        tblComponent.setItems(componentList);
    }

    @FXML
    void actionAddComponent(ActionEvent event) {
        openPanel(pnlAddComponent);
    }

    private void openPanel(Pane pnl) {
        GaussianBlur blur = new GaussianBlur(10);
        pnlMain.setEffect(blur);

        pnl.setDisable(false);
        pnl.setVisible(true);

        ZoomIn in = new ZoomIn(pnl);
        in.setSpeed(2);
        in.play();

        setComponentId();
    }

    @FXML
    void actionAddNewComponent(ActionEvent event) {
        if (checkAllFields()) {
            try {
                conn = DBConnect.getInstance().getConnection();

                String sql = "INSERT INTO component VALUES(?,?,?,?)";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, txtComId.getText());
                pst.setString(2, txtName.getText());
                pst.setString(3, txtQty.getText());
                pst.setInt(4, Integer.parseInt(txtUnitPrice.getText()));
                int executeUpdate = pst.executeUpdate();

                if (executeUpdate > 0) {
                    myCustomProperty.setAlert("success",
                            txtComId.getText() + " Component Added Successfully!",
                            "Added row will highlight");
                    getAllComponent();
                    hidePanel(pnlAddComponent);
                    lastAdded(txtComId.getText());
                    clearAllFields();
                } else {
                    myCustomProperty.setAlert("danger",
                            "Something went wrong!",
                            "Please check database or contact developer");
                    shakePanel(pnlAddComponent);
                }
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(FXMLHomeComponentController.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    void actionCancelAddPanel(ActionEvent event) {
        clearAllFields();
        hidePanel(pnlAddComponent);
    }

    private void hidePanel(Pane pnl) {
        pnlMain.setEffect(null);

        ZoomOut out = new ZoomOut(pnl);
        out.setSpeed(2);
        out.play();

        pnl.setVisible(false);
        pnl.setDisable(true);
    }

    private boolean checkAllFields() {
        if (!"".equals(txtComId.getText())
                & !"".equals(txtName.getText())
                & !"".equals(txtQty.getText())
                & !"".equals(txtUnitPrice.getText())) {

            if (txtQty.getText().matches("[0-9]*")) {
                if (txtUnitPrice.getText().matches("[0-9]*")) {
                    return true;
                } else {
                    myCustomProperty.setAlert("attention",
                            "Please Check Unit Price",
                            "Unit Price should be numbers");
                    shakePanel(pnlAddComponent);
                }
            } else {
                myCustomProperty.setAlert("attention",
                        "Please Check Qty",
                        "Qty should be numbers");
                shakePanel(pnlAddComponent);
            }
        } else {
            myCustomProperty.setAlert("danger",
                    "Please fill all Fields!",
                    "You have to complete all fields to continue");

            shakePanel(pnlAddComponent);
        }
        return false;
    }

    private void clearAllFields() {
        txtComId.setText(null);
        txtName.setText(null);
        txtQty.setText(null);
        txtUnitPrice.setText(null);
    }

    @FXML
    void eventSearch(KeyEvent event) {
        try {
            conn = DBConnect.getInstance().getConnection();

            String search = txtSearch.getText();
            String sql = "SELECT * FROM component "
                    + "WHERE com_id LIKE '" + search + "%' "
                    + "OR name LIKE '" + search + "%'";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            componentList = FXCollections.observableArrayList();

            while (rs.next()) {
                Button btn = new Button("Edit");
                MyCustomProperty editButton = new MyCustomProperty();
                editButton.setButtonDefaultEffect(btn);
                editButton.setButtonEffect(btn);
                ImageView icon = new ImageView("/img/ic_32px.png");

                Button btnDelete = new Button("Delete");
                myCustomProperty.setDeleteButtonEffect(btnDelete);
                myCustomProperty.setDeleteButtonDefaultEffect(btnDelete);

                if (employeeDetails.get(0).getType() == 1) {
                    btnDelete.setDisable(false);
                } else {
                    btnDelete.setDisable(true);
                }

                Component com = new Component(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        btn,
                        rs.getInt(4),
                        icon, btnDelete);

                btn.setOnAction((ActionEvent e) -> {
                    openUpdatePanel(pnlUpdate,
                            com.getComId(),
                            com.getQty(),
                            com.getUnitPrice());
                });

                btnDelete.setOnAction((e) -> {
                    openDeleteConfirmPanel(pnlDelete, com.getComId());
                });

                componentList.add(com);
            }
            showComponent(componentList);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLHomeComponentController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    private void shakePanel(Pane pnl) {
        Shake s = new Shake(pnl);
        s.setSpeed(2);
        s.play();
    }

    private void lastAdded(String id) {
        for (int i = 0; i < tblComponent.getItems().size(); i++) {
            if (id.equals(tblComponent.getItems().get(i).getComId())) {
                tblComponent.requestFocus();
                tblComponent.getSelectionModel().select(i);
                tblComponent.getFocusModel().focus(i);
            }
        }
    }

    @FXML
    void actionCancelUpdate(ActionEvent event) {
        hidePanel(pnlUpdate);
    }

    private void openUpdatePanel(Pane pnl, String id, String qty, int unitPrice) {
        GaussianBlur blur = new GaussianBlur(10);
        pnlMain.setEffect(blur);

        pnl.setDisable(false);
        pnl.setVisible(true);
        lblHeader.setText(id);
        txtUpdateQty.setText(qty);
        txtUpdateUnitPrice.setText(String.valueOf(unitPrice));

        ZoomIn in = new ZoomIn(pnl);
        in.setSpeed(2);
        in.play();
    }

    @FXML
    void actionUpdateComponent(ActionEvent event) {
        if (checkUpdateFields()) {
            try {
                conn = DBConnect.getInstance().getConnection();

                String sql = "UPDATE component "
                        + "SET qty=?, unit_price=?"
                        + "WHERE com_id=?";

                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, txtUpdateQty.getText());
                pst.setString(2, txtUpdateUnitPrice.getText());
                pst.setString(3, lblHeader.getText());
                int executeUpdate = pst.executeUpdate();

                if (executeUpdate > 0) {
                    myCustomProperty.setAlert("success",
                            lblHeader.getText() + " Component Updated Successfully!",
                            "Updated row will hightlight");
                    hidePanel(pnlUpdate);
                    lastUpdateAdded(lblHeader.getText());
                    clearAllFields();
                    getAllComponent();
                } else {
                    myCustomProperty.setAlert("danger",
                            "Something went wrong!",
                            "Please check database or contact developer");
                    shakePanel(pnlUpdate);
                }
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(FXMLHomeComponentController.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
    }

    private void lastUpdateAdded(String id) {
        for (int i = 0; i < tblComponent.getItems().size(); i++) {
            if (id.equals(tblComponent.getItems().get(i).getComId())) {
                tblComponent.requestFocus();
                tblComponent.getSelectionModel().select(i);
                tblComponent.getFocusModel().focus(i);
            }
        }
    }

    private boolean checkUpdateFields() {
        if (!"".equals(txtUpdateQty.getText())
                & !"".equals(txtUpdateUnitPrice.getText())) {
            if (txtUpdateQty.getText().matches("[0-9]*")) {
                if (txtUpdateUnitPrice.getText().matches("[0-9]*")) {
                    return true;
                } else {
                    myCustomProperty.setAlert("attention",
                            "Please Check UnitPrice",
                            "UnitPrice should be numbers");
                    shakePanel(pnlUpdate);
                }
            } else {
                myCustomProperty.setAlert("attention",
                        "Please Check Qty",
                        "Qty should be numbers");
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

    private void setComponentId() {
        try {
            conn = DBConnect.getInstance().getConnection();

            String sql = "SELECT com_id "
                    + "FROM component "
                    + "ORDER BY com_id DESC "
                    + "LIMIT 1;";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String temp[] = rs.getString(1).split("M");
                int id = Integer.parseInt(temp[1]);
                id++;

                String newId = null;
                if (id < 10) {
                    newId = "COM00" + id;
                } else if (id < 100) {
                    newId = "COM0" + id;
                } else {
                    newId = "COM" + id;
                }
                txtComId.setText(newId);
            } else {
                txtComId.setText("COM001");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLHomeMeterController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void eventDeleteComponentCancel(ActionEvent event) {
        hidePanel(pnlDelete);
    }

    @FXML
    void eventDeleteComponent(ActionEvent event) {
        try {
            conn = DBConnect.getInstance().getConnection();

            String sql = "DELETE FROM component "
                    + "WHERE com_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, lblDeleteComponent.getText());
            int executeUpdate = pst.executeUpdate();

            if (executeUpdate > 0) {
                myCustomProperty.setAlert("success",
                        "Component Removed Successfully!",
                        "You can't undo this operation");
                hidePanel(pnlDelete);
                getAllComponent();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLHomeCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void openDeleteConfirmPanel(Pane pnl, String comId) {
        GaussianBlur blur = new GaussianBlur(10);
        pnlMain.setEffect(blur);

        lblDeleteComponent.setText(comId);
        lblDeleteComponentHeader.setText(comId);

        pnl.setDisable(false);
        pnl.setVisible(true);
        ZoomIn in = new ZoomIn(pnl);
        in.setSpeed(2);
        in.play();
    }
}
