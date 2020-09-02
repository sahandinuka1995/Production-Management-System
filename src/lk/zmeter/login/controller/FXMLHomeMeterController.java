package lk.zmeter.login.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.util.StringConverter;
import lk.zmeter.login.database.DBConnect;
import lk.zmeter.login.model.Customer;
import lk.zmeter.login.model.Employee;
import lk.zmeter.login.model.Meter;
import lk.zmeter.login.model.MeterType;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author Sahan
 */
public class FXMLHomeMeterController implements Initializable {

    Connection conn;
    MyCustomProperty myCustomProperty;
    ObservableList<Employee> employeeDetails;

    @FXML
    private TableView<Meter> tblMeters;

    @FXML
    private TableColumn<Meter, String> meterSn;
    @FXML
    private TableColumn<Meter, String> meterTypeId;
    @FXML
    private TableColumn<Meter, String> cusNic;
    @FXML
    private TableColumn edit;
    @FXML
    private TableColumn delete;
    @FXML
    private TableColumn meterIcon;

    @FXML
    private ComboBox<MeterType> cbMeterType;
    @FXML
    private ComboBox<Customer> cbCustomers;
    @FXML
    private ComboBox<MeterType> cbUpdateMeterType;
    @FXML
    private ComboBox<Customer> cbUpdateCustomers;
    @FXML
    private ComboBox cbMeterVersion;
    @FXML
    private ComboBox cbMeterial;
    @FXML
    private ComboBox cbPrinter;

    @FXML
    private TextField txtSearch;
    @FXML
    private TextField txtMeterSn;
    @FXML
    private TextField txtMeterTypeId;

    @FXML
    private ImageView imgFound;
    @FXML
    private ImageView imgNotFound;

    @FXML
    private Label lblUpdateMeterSn;
    @FXML
    private Label lblDeleteMeterHeader;
    @FXML
    private Label lblDeleteMeter;

    ObservableList<Meter> meterList;
    ObservableList<MeterType> meterTypeList;
    ObservableList<Customer> customerList;

    @FXML
    private Pane pnlMain;
    @FXML
    private Pane pnlAddMeter;
    @FXML
    private Pane pnlUpdate;
    @FXML
    private Pane pnlAddMeterType;
    @FXML
    private Pane pnlDelete;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        myCustomProperty = new MyCustomProperty();
        employeeDetails = new UserSession().getEmployee();
        getAllMeters();
    }

    @FXML
    private void eventSearchMeter(KeyEvent event) {
        try {
            conn = DBConnect.getInstance().getConnection();

            String search = txtSearch.getText();
            String sql = "SELECT * FROM meter "
                    + "WHERE sn LIKE '" + search + "%'"
                    + "OR nic LIKE '" + search + "%'";
            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery(sql);
            meterList = FXCollections.observableArrayList();

            while (rs.next()) {
                Button btn = new Button("Edit");
                myCustomProperty.setButtonDefaultEffect(btn);
                myCustomProperty.setButtonEffect(btn);
                ImageView icon = new ImageView("/img/Speedometer_32px.png");

                Button btnDelete = new Button("Delete");
                myCustomProperty.setDeleteButtonEffect(btnDelete);
                myCustomProperty.setDeleteButtonDefaultEffect(btnDelete);

                if (employeeDetails.get(0).getType() == 1) {
                    btnDelete.setDisable(false);
                } else {
                    btnDelete.setDisable(true);
                }

                Meter meter = new Meter(
                        rs.getString(1),
                        rs.getString(2),
                        getCustomerName(rs.getString(3)),
                        btn, icon, btnDelete);

                meterList.add(meter);

                btn.setOnAction((e) -> {
                    openUpdatePanel(pnlUpdate, meter);
                });
                
                btnDelete.setOnAction((e) -> {
                    openDeleteConfirmPanel(pnlDelete, meter.getSn());
                });
            }

            showMeters(meterList);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLHomeMeterController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void actionAddMeter(ActionEvent event) {
        openPanel(pnlAddMeter);
    }

    private void getAllMeters() {
        try {
            conn = DBConnect.getInstance().getConnection();

            String sql = "SELECT * FROM meter";
            PreparedStatement pst = conn.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            meterList = FXCollections.observableArrayList();

            while (rs.next()) {
                Button btn = new Button("Edit");
                myCustomProperty.setButtonDefaultEffect(btn);
                myCustomProperty.setButtonEffect(btn);
                ImageView icon = new ImageView("/img/Speedometer_32px.png");

                Button btnDelete = new Button("Delete");
                myCustomProperty.setDeleteButtonEffect(btnDelete);
                myCustomProperty.setDeleteButtonDefaultEffect(btnDelete);

                if (employeeDetails.get(0).getType() == 1) {
                    btnDelete.setDisable(false);
                } else {
                    btnDelete.setDisable(true);
                }

                Meter meter = new Meter(
                        rs.getString(1),
                        rs.getString(2),
                        getCustomerName(rs.getString(3)),
                        btn, icon, btnDelete);

                meterList.add(meter);

                btn.setOnAction((e) -> {
                    openUpdatePanel(pnlUpdate, meter);
                });

                btnDelete.setOnAction((e) -> {
                    openDeleteConfirmPanel(pnlDelete, meter.getSn());
                });
            }

            showMeters(meterList);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLHomeMeterController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    private void showMeters(ObservableList<Meter> meterList) {
        meterIcon.setCellValueFactory(new PropertyValueFactory<>("meterIcon"));
        meterSn.setCellValueFactory(new PropertyValueFactory<>("sn"));
        meterTypeId.setCellValueFactory(new PropertyValueFactory<>("meterTypeId"));
        cusNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        edit.setCellValueFactory(new PropertyValueFactory<>("btn"));
        delete.setCellValueFactory(new PropertyValueFactory<>("btnDelete"));

        tblMeters.setItems(meterList);
    }

    private void getMeterTypes() {
        try {
            conn = DBConnect.getInstance().getConnection();

            String sql = "SELECT * FROM meter_type";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            meterTypeList = FXCollections.observableArrayList();

            while (rs.next()) {
                meterTypeList.add(new MeterType(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)));
            }

            showMeterTypes(meterTypeList);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLHomeMeterController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    private void showMeterTypes(ObservableList<MeterType> meterTypeList) {
        cbMeterType.setItems(meterTypeList);
        cbMeterType.setConverter(new StringConverter<MeterType>() {

            @Override
            public String toString(MeterType object) {
                String printer;
                if ("1".equals(object.getPrinter())) {
                    printer = "with Printer";
                } else {
                    printer = "without printer";
                }
                return object.getMeterTypeId() + " - " + object.getVersion()
                        + " - " + object.getMeterial() + " - " + printer;
            }

            @Override
            public MeterType fromString(String string) {
                return null;
            }
        });
    }

    private void getCustomers() {
        try {
            conn = DBConnect.getInstance().getConnection();

            String sql = "SELECT * FROM customer";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            customerList = FXCollections.observableArrayList();

            while (rs.next()) {
                customerList.add(new Customer(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        new Button(),
                        new ImageView(), null));
            }

            showCustomers(customerList);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLHomeMeterController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    private void showCustomers(ObservableList<Customer> customerList) {
        cbCustomers.setItems(customerList);
        cbCustomers.setConverter(new StringConverter<Customer>() {

            @Override
            public String toString(Customer object) {
                return object.getNic() + " - " + object.getName();
            }

            @Override
            public Customer fromString(String string) {
                return null;
            }
        });
    }

    private boolean checkAllFields() {
        if (!"".equals(txtMeterSn.getText())
                & cbMeterType.getSelectionModel().getSelectedItem() != null
                & cbCustomers.getSelectionModel().getSelectedItem() != null) {

            if (txtMeterSn.getText().matches("[0-9]*")) {
                if (!checkMeterFromDb()) {
                    return true;
                } else {
                    myCustomProperty.setAlert("warning",
                            "Please check Meter SN!",
                            "This meter already registered for another customer");

                    shakePanel(pnlAddMeter);
                }
            } else {
                myCustomProperty.setAlert("attention",
                        "Please Check Meter SN!",
                        "Meter SN should be numbers");

                shakePanel(pnlAddMeter);
            }
        } else {
            myCustomProperty.setAlert("danger",
                    "Please fill all Fields!",
                    "You have to complete all fields to continue");
            shakePanel(pnlAddMeter);
        }
        return false;
    }

    private void clearAllFields() {
        txtMeterSn.setText(null);
        cbMeterType.getSelectionModel().clearSelection();
        cbCustomers.getSelectionModel().clearSelection();
    }

    private void openPanel(Pane pnl) {
        GaussianBlur blur = new GaussianBlur(10);
        pnlMain.setEffect(blur);

        pnl.setDisable(false);
        pnl.setVisible(true);
        ZoomIn in = new ZoomIn(pnl);
        in.setSpeed(2);
        in.play();

        setAddMeterData();
    }

    @FXML
    void actionCancel(ActionEvent event) {
        clearAllFields();
        hideAllPanel(pnlAddMeter);
    }

    private void hideAllPanel(Pane pnl) {
        pnlMain.setEffect(null);

        ZoomOut out = new ZoomOut(pnl);
        out.setSpeed(2);
        out.play();

        pnl.setVisible(false);
        pnl.setDisable(true);
    }

    private void setAddMeterData() {
        getMeterTypes();
        getCustomers();
    }

    @FXML
    void actionAddNewMeter() {
        if (checkAllFields()) {
            try {
                conn = DBConnect.getInstance().getConnection();

                String sql = "INSERT INTO meter VALUES(?,?,?)";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, txtMeterSn.getText());
                pst.setString(2, cbMeterType.getSelectionModel()
                        .getSelectedItem().getMeterTypeId());
                pst.setString(3, cbCustomers.getSelectionModel()
                        .getSelectedItem().getNic());
                int executeUpdate = pst.executeUpdate();

                if (executeUpdate > 0) {
                    myCustomProperty.setAlert("success",
                            "Meter Added Successfully!",
                            "Added row will highlight");
                    getAllMeters();
                    lastAdded(txtMeterSn.getText());
                    clearAllFields();
                    hideAllPanel(pnlAddMeter);
                } else {
                    myCustomProperty.setAlert("danger",
                            "Something went wrong!",
                            "Please check database or contact developer");
                }
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(FXMLHomeMeterController.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    void checkMeter(KeyEvent event) {
        if (!checkMeterFromDb()) {
            imgFound.setVisible(true);
            imgNotFound.setVisible(false);
        } else {
            imgNotFound.setVisible(true);
            imgFound.setVisible(false);
        }
    }

    private boolean checkMeterFromDb() {
        try {
            conn = DBConnect.getInstance().getConnection();

            String sql = "SELECT * FROM meter WHERE sn=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, txtMeterSn.getText());
            ResultSet rs = pst.executeQuery();

            return rs.next();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLHomeCustomerController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private void shakePanel(Pane pnl) {
        Shake s = new Shake(pnl);
        s.setSpeed(2);
        s.play();
    }

    private void lastAdded(String id) {
        for (int i = 0; i < tblMeters.getItems().size(); i++) {
            if (id.equals(tblMeters.getItems().get(i).getSn())) {
                tblMeters.requestFocus();
                tblMeters.getSelectionModel().select(i);
                tblMeters.getFocusModel().focus(i);
            }
        }
    }

    private void openUpdatePanel(Pane pnl, Meter meter) {
        GaussianBlur blur = new GaussianBlur(10);
        pnlMain.setEffect(blur);

        pnl.setDisable(false);
        pnl.setVisible(true);
        ZoomIn in = new ZoomIn(pnl);
        in.setSpeed(2);
        in.play();

        lblUpdateMeterSn.setText(meter.getSn());

        getUpdateMeterTypes(meter);
        getUpdateCustomers();
    }

    private void getUpdateMeterTypes(Meter m) {
        try {
            conn = DBConnect.getInstance().getConnection();

            String sql = "SELECT * FROM meter_type";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            meterTypeList = FXCollections.observableArrayList();

            while (rs.next()) {
                meterTypeList.add(new MeterType(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)));
            }
            showUpdateMeterTypes(meterTypeList, m);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLHomeMeterController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    private void showUpdateMeterTypes(ObservableList<MeterType> meterTypeList,
            Meter m) {
        cbUpdateMeterType.setItems(meterTypeList);
        cbUpdateMeterType.setConverter(new StringConverter<MeterType>() {

            @Override
            public String toString(MeterType object) {
                String printer;
                if ("1".equals(object.getPrinter())) {
                    printer = "with Printer";
                } else {
                    printer = "without printer";
                }
                return object.getMeterTypeId() + " - " + object.getVersion()
                        + " - " + object.getMeterial() + " - " + printer;
            }

            @Override
            public MeterType fromString(String string) {
                return null;
            }
        });
    }

    private void getUpdateCustomers() {
        try {
            conn = DBConnect.getInstance().getConnection();

            String sql = "SELECT * FROM customer";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            customerList = FXCollections.observableArrayList();

            while (rs.next()) {
                customerList.add(new Customer(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        new Button(),
                        new ImageView(), null));
            }
            showUpdateCustomers(customerList);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLHomeMeterController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    private void showUpdateCustomers(ObservableList<Customer> customerList) {
        cbUpdateCustomers.setItems(customerList);
        cbUpdateCustomers.setConverter(new StringConverter<Customer>() {

            @Override
            public String toString(Customer object) {
                return object.getNic() + " - " + object.getName();
            }

            @Override
            public Customer fromString(String string) {
                return null;
            }
        });
    }

    @FXML
    void actionUpdateCancel(ActionEvent event) {
        hideAllPanel(pnlUpdate);
    }

    @FXML
    void actionUpdateMeter(ActionEvent event) {
        if (checkAllUpdateFields()) {
            try {
                conn = DBConnect.getInstance().getConnection();

                String sql = "UPDATE meter SET meter_type_id=?, nic=?"
                        + "WHERE sn=?";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, cbUpdateMeterType.getSelectionModel()
                        .getSelectedItem().getMeterTypeId());
                pst.setString(2, cbUpdateCustomers.getSelectionModel()
                        .getSelectedItem().getNic());
                pst.setString(3, lblUpdateMeterSn.getText());
                int executeUpdate = pst.executeUpdate();

                if (executeUpdate > 0) {
                    lastUpdateAdded(lblUpdateMeterSn.getText());
                    myCustomProperty.setAlert("success",
                            "Meter Updated Successfully!",
                            "Updated row will highlight");
                    getAllMeters();
                    clearAllUpdateFields();
                    hideAllPanel(pnlUpdate);
                } else {
                    myCustomProperty.setAlert("danger",
                            "Something went wrong!",
                            "Please check database or contact developer");
                }
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(FXMLHomeMeterController.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
    }

    private boolean checkAllUpdateFields() {
        if (cbUpdateMeterType.getSelectionModel().getSelectedItem() != null
                & cbUpdateCustomers.getSelectionModel()
                .getSelectedItem() != null) {

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
        cbUpdateMeterType.getSelectionModel().clearSelection();
        cbUpdateCustomers.getSelectionModel().clearSelection();
    }

    private void lastUpdateAdded(String id) {
        for (int i = 0; i < tblMeters.getItems().size(); i++) {
            if (id.equals(tblMeters.getItems().get(i).getSn())) {
                tblMeters.requestFocus();
                tblMeters.getSelectionModel().select(i);
                tblMeters.getFocusModel().focus(i);
            }
        }
    }

    private String getCustomerName(String nic) {
        try {
            conn = DBConnect.getInstance().getConnection();

            String sql = "SELECT name FROM customer WHERE nic=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, nic);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return nic + " (" + rs.getString(1) + ")";
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLHomeMeterController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @FXML
    void actionOpenAddMeterType(ActionEvent event) {
        openMeterTypePanel(pnlAddMeterType);
    }

    private void openMeterTypePanel(Pane pnl) {
        GaussianBlur blur = new GaussianBlur(10);
        pnlMain.setEffect(blur);

        pnl.setDisable(false);
        pnl.setVisible(true);

        ZoomIn in = new ZoomIn(pnl);
        in.setSpeed(2);
        in.play();

        setMeterTypeId();
        loadComboBoxData();
    }

    @FXML
    void actionCancelMeterType(ActionEvent event) {
        clearMeterTypeFields();
        hideAllPanel(pnlAddMeterType);
    }

    private void loadComboBoxData() {
        cbMeterVersion.getItems().clear();
        cbMeterVersion.getItems().addAll("Old", "New");

        cbMeterial.getItems().clear();
        cbMeterial.getItems().addAll("Iron", "Plastic");

        cbPrinter.getItems().clear();
        cbPrinter.getItems().addAll("Available", "Not Available");
    }

    private void setMeterTypeId() {
        try {
            conn = DBConnect.getInstance().getConnection();

            String sql = "SELECT meter_type_id "
                    + "FROM meter_type "
                    + "ORDER BY meter_type_id DESC "
                    + "LIMIT 1;";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String temp[] = rs.getString(1).split("T");
                int id = Integer.parseInt(temp[1]);
                id++;

                String newId = null;
                if (id < 10) {
                    newId = "MT00" + id;
                } else if (id < 100) {
                    newId = "MT0" + id;
                } else {
                    newId = "MT" + id;
                }
                txtMeterTypeId.setText(newId);
            } else {
                txtMeterTypeId.setText("MT001");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLHomeMeterController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void actionAddMeterType(ActionEvent event) {
        if (checkMeterTypeFields()) {
            try {
                conn = DBConnect.getInstance().getConnection();

                String sql = "INSERT INTO meter_type "
                        + "VALUES(?,?,?,?)";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, txtMeterTypeId.getText());
                pst.setString(2, cbMeterVersion.getSelectionModel()
                        .getSelectedItem().toString());
                pst.setString(3, cbMeterial.getSelectionModel()
                        .getSelectedItem().toString());

                boolean printer = false;
                if ("Available".equals(cbPrinter.getSelectionModel()
                        .getSelectedItem().toString())) {
                    printer = true;
                }
                pst.setBoolean(4, printer);
                int executeUpdate = pst.executeUpdate();
                if (executeUpdate > 0) {
                    myCustomProperty.setAlert("success",
                            "Meter Type Added Successfully!",
                            "You can now assign meter type");
                    clearMeterTypeFields();
                    hideAllPanel(pnlAddMeterType);
                }
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(FXMLHomeMeterController.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
    }

    private boolean checkMeterTypeFields() {
        if (!txtMeterTypeId.getText().isEmpty()
                & cbMeterVersion.getSelectionModel().getSelectedItem() != null
                & cbMeterial.getSelectionModel().getSelectedItem() != null
                & cbPrinter.getSelectionModel().getSelectedItem() != null) {
            return true;
        } else {
            myCustomProperty.setAlert("danger",
                    "Please fill all Fields!",
                    "You have to complete all fields to continue");
            shakePanel(pnlAddMeterType);
        }
        return false;
    }

    private void clearMeterTypeFields() {
        txtMeterTypeId.setText(null);
        cbMeterVersion.getSelectionModel().clearSelection();
        cbMeterial.getSelectionModel().clearSelection();
        cbPrinter.getSelectionModel().clearSelection();
    }

    private void openDeleteConfirmPanel(Pane pnl, String sn) {
        GaussianBlur blur = new GaussianBlur(10);
        pnlMain.setEffect(blur);

        lblDeleteMeter.setText(sn);
        lblDeleteMeterHeader.setText(sn);

        pnl.setDisable(false);
        pnl.setVisible(true);
        ZoomIn in = new ZoomIn(pnl);
        in.setSpeed(2);
        in.play();
    }

    @FXML
    void eventDeleteMeterCancel(ActionEvent event) {
        hideAllPanel(pnlDelete);
    }

    @FXML
    void eventDeleteMeter(ActionEvent event) {
        try {
            conn = DBConnect.getInstance().getConnection();

            String sql = "DELETE FROM meter "
                    + "WHERE sn=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, lblDeleteMeter.getText());
            int executeUpdate = pst.executeUpdate();

            if (executeUpdate > 0) {
                myCustomProperty.setAlert("success",
                        "Meter Removed Successfully!",
                        "You can't undo this operation");
                hideAllPanel(pnlDelete);
                getAllMeters();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLHomeCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
