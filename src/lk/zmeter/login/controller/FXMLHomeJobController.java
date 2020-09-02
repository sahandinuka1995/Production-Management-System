package lk.zmeter.login.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import animatefx.animation.Shake;
import animatefx.animation.ZoomIn;
import animatefx.animation.ZoomOut;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.util.StringConverter;
import static javax.swing.text.html.HTML.Tag.MAP;
import lk.zmeter.login.database.DBConnect;
import lk.zmeter.login.model.Component;
import lk.zmeter.login.model.Employee;
import lk.zmeter.login.model.Job;
import lk.zmeter.login.model.Meter;

import org.controlsfx.control.Notifications;
import java.util.*;
import static javax.swing.text.html.HTML.Tag.MAP;


/**
 * FXML Controller class
 *
 * @author Sahan
 */
public class FXMLHomeJobController implements Initializable {

    Connection conn;

    ObservableList<Job> jobList;
    ObservableList<Meter> meterList;
    ObservableList<Component> componentList;
    ObservableList<Object> tempComponents;
    ArrayList<Boolean> jobCompleteStatus;
    ObservableList<Employee> employeeDetails;

    MyCustomProperty myCustomProperty;

    @FXML
    private TableView<Job> tblJobs;
    @FXML
    private TableColumn<Job, String> repairId;
    @FXML
    private TableColumn<Job, String> meterSn;
    @FXML
    private TableColumn<Job, String> fault;
    @FXML
    private TableColumn<Job, Button> Complete;
    @FXML
    private TableColumn<Job, Object> components;
    @FXML
    private TableColumn<Job, String> price;
    @FXML
    private TableColumn<Job, String> currierNo;
    @FXML
    private TableColumn<Job, Label> qcStatus;
    @FXML
    private TableColumn<Job, Button> qc;
    @FXML
    private TableColumn<Job, String> repairDate;
    @FXML
    private TableColumn<Job, Button> currier;
    @FXML
    private TableColumn jobIcon;
    @FXML
    private TableColumn delete;

    @FXML
    private Pane pnlAddJob;
    @FXML
    private Pane pnlMain;
    @FXML
    private Pane pnlCompleteJob;
    @FXML
    private Pane pnlQc;
    @FXML
    private Pane pnlJobCurrier;
    @FXML
    private Pane pnlDelete;

    @FXML
    private TextField txtRepId;
    @FXML
    private TextField txtFault;
    @FXML
    private TextField txtQty;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtCurrierNo;
    @FXML
    private TextField txtCurrierName;
    @FXML
    private TextField txtSearch;

    @FXML
    private ComboBox<Meter> cbMeterSn;
    @FXML
    private ComboBox<Component> cbComponents;

    @FXML
    private ListView<Object> comList;

    @FXML
    private Label lblHeader;
    @FXML
    private Label lblQcHeader;
    @FXML
    private Label lblCurrierHeader;
    @FXML
    private Label lblDeleteJob;
    @FXML
    private Label lblDeleteJobHeader;

    @FXML
    private CheckBox cbPowerOn;
    @FXML
    private CheckBox cbButtons;
    @FXML
    private CheckBox cbCovers;
    @FXML
    private CheckBox cbTaxiGreen;
    @FXML
    private CheckBox cbRemote;
    @FXML
    private CheckBox cbCheckAll;
    @FXML
    private CheckBox cbDisplay;
    @FXML
    private CheckBox cbConnectors;
    @FXML
    private CheckBox cbFrontLense;
    @FXML
    private CheckBox cbTaxiRed;
    @FXML
    private CheckBox cbPulse;
    @FXML
    private CheckBox cbUncheckAll;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        employeeDetails = new UserSession().getEmployee();
        myCustomProperty = new MyCustomProperty();
        getAllJobs();
    }

    private void getAllJobs() {
        try {
            conn = DBConnect.getInstance().getConnection();

            String sql = "SELECT * FROM meter_repair mr "
                    + "LEFT JOIN repair_component rcom "
                    + "ON mr.meter_rep_id=rcom.meter_rep_id "
                    + "LEFT JOIN repair_payment rpay "
                    + "ON rcom.meter_rep_id=rpay.meter_rep_id "
                    + "LEFT JOIN repair_qc rqc "
                    + "ON rpay.meter_rep_id=rqc.meter_rep_id "
                    + "LEFT JOIN repair_currier rcurr "
                    + "ON rqc.meter_rep_id=rcurr.meter_rep_id "
                    + "ORDER BY mr.rep_date DESC";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            jobList = FXCollections.observableArrayList();

            while (rs.next()) {
                Label qcStatus = new Label(rs.getString(15));
                Button btn = null;
                Button btnQc = null;
                Button btnCurrier = null;

                if (rs.getBoolean(5)) {
                    btn = new Button("Completed");
                    myCustomProperty.setCompletedButtonDefaultEffect(btn);
                    btn.setDisable(true);

                    if (rs.getBoolean(15)) {
                        btnQc = new Button("Checked");
                        myCustomProperty.setCompletedButtonDefaultEffect(btnQc);
                        btnQc.setDisable(true);
                    } else {
                        btnQc = new Button("QC");
                        myCustomProperty.setCompleteButtonDefaultEffect(btnQc);
                        myCustomProperty.setCompleteButtonEffect(btnQc);

                        btnQc.setDisable(false);
                        btnQc.setVisible(true);
                    }
                } else {
                    btn = new Button("Complete");
                    myCustomProperty.setCompleteButtonDefaultEffect(btn);
                    myCustomProperty.setCompleteButtonEffect(btn);

                    btn.setDisable(false);
                    btn.setVisible(true);

                    btnQc = new Button("QC");
                    myCustomProperty.setCompleteButtonDefaultEffect(btnQc);
                    myCustomProperty.setCompleteButtonEffect(btnQc);

                    btnQc.setDisable(true);
                    btnQc.setVisible(false);
                }

                if (rs.getString(12) == null) {
                    qcStatus.setVisible(false);
                    qcStatus.setDisable(true);
                } else {
                    qcStatus.setVisible(true);
                    qcStatus.setDisable(false);
                }

                if (rs.getBoolean(15)) {
                    if (rs.getString(18) == null) {
                        btnCurrier = new Button("Currier");
                        myCustomProperty.setCompleteButtonDefaultEffect(btnCurrier);
                        myCustomProperty.setCompleteButtonEffect(btnCurrier);

                        btnCurrier.setDisable(false);
                        btnCurrier.setVisible(true);
                    } else {
                        btnCurrier = new Button("Curriered");
                        myCustomProperty.setCompletedButtonDefaultEffect(btnCurrier);

                        btnCurrier.setDisable(true);
                        btnCurrier.setVisible(true);
                    }
                } else {
                    btnCurrier = new Button("Currier");
                    myCustomProperty.setCompleteButtonDefaultEffect(btnCurrier);
                    myCustomProperty.setCompleteButtonEffect(btnCurrier);

                    btnCurrier.setDisable(true);
                    btnCurrier.setVisible(false);
                }

                Button btnDelete = new Button("Delete");
                myCustomProperty.setDeleteButtonEffect(btnDelete);
                myCustomProperty.setDeleteButtonDefaultEffect(btnDelete);

                if (employeeDetails.get(0).getType() == 1) {
                    btnDelete.setDisable(false);
                } else {
                    btnDelete.setDisable(true);
                }

                ImageView icon = new ImageView("/img/job_icon_32px.png");

                Job job = new Job(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        btn,
                        rs.getString(7),
                        setCurrency(rs.getString(11)),
                        btnQc,
                        setQcStatus(qcStatus),
                        rs.getString(18),
                        rs.getDate(4),
                        rs.getBoolean(5),
                        btnCurrier,
                        icon, btnDelete);

                btn.setOnAction((e) -> {
                    openJobCompletePanel(pnlCompleteJob, job.getMeterRepairId());
                });

                btnQc.setOnAction((e) -> {
                    openJobQcPanel(pnlQc, job.getMeterRepairId());
                });

                btnCurrier.setOnAction((e) -> {
                    openCurrierPanel(pnlJobCurrier, job.getMeterRepairId());
                });

                btnDelete.setOnAction((e) -> {
                    openDeleteConfirmPanel(pnlDelete, job.getMeterRepairId());
                    //printBill(job);
                });

                jobList.add(job);
            }

            showJobs(jobList);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLHomeJobController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    private void showJobs(ObservableList<Job> jobList) {
        jobIcon.setCellValueFactory(new PropertyValueFactory<>("jobIcon"));
        repairId.setCellValueFactory(new PropertyValueFactory<>("meterRepairId"));
        meterSn.setCellValueFactory(new PropertyValueFactory<>("meterSn"));
        fault.setCellValueFactory(new PropertyValueFactory<>("fault"));
        Complete.setCellValueFactory(new PropertyValueFactory<>("completeJob"));
        components.setCellValueFactory(new PropertyValueFactory<>("component"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        qcStatus.setCellValueFactory(new PropertyValueFactory<>("qcStatus"));
        qc.setCellValueFactory(new PropertyValueFactory<>("qcMeterRepId"));
        repairDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        currier.setCellValueFactory(new PropertyValueFactory<>("currier"));
        currierNo.setCellValueFactory(new PropertyValueFactory<>("currierNo"));
        delete.setCellValueFactory(new PropertyValueFactory<>("btnDelete"));

        tblJobs.setItems(jobList);
    }

    private Label setQcStatus(Label qcStatus) {
        if ("1".equals(qcStatus.getText())) {
            qcStatus.setText("Pass");
            qcStatus.setStyle("-fx-text-fill: #ffffff; "
                    + "-fx-background-color: #00ba4e; "
                    + "-fx-padding: 0 15 0 15; "
                    + "-fx-background-radius: 5 5 5 5;");
            return qcStatus;
        } else {
            qcStatus.setText("Fail");
            qcStatus.setStyle("-fx-text-fill: #ffffff; "
                    + "-fx-background-color: #d8004c; "
                    + "-fx-padding: 0 17 0 17; "
                    + "-fx-background-radius: 5 5 5 5;");
            return qcStatus;
        }
    }

    @FXML
    void actionAddJob(ActionEvent event) {
        openPanel(pnlAddJob);
    }

    private void openPanel(Pane pnl) {
        GaussianBlur blur = new GaussianBlur(10);
        pnlMain.setEffect(blur);

        pnl.setDisable(false);
        pnl.setVisible(true);

        setMeterSn();

        ZoomIn in = new ZoomIn(pnl);
        in.setSpeed(2);
        in.play();
    }

    @FXML
    void actionAddJobCancel(ActionEvent event) {
        clearAllAddFields();
        hidePanel(pnlAddJob);
    }

    private void hidePanel(Pane pnl) {
        pnlMain.setEffect(null);

        ZoomOut out = new ZoomOut(pnl);
        out.setSpeed(2);
        out.play();

        pnl.setVisible(false);
        pnl.setDisable(true);
    }

    @FXML
    void actionAddNewJob(ActionEvent event) {
        if (checkAllFields()) {
            try {
                conn = DBConnect.getInstance().getConnection();

                String sql = "INSERT INTO meter_repair (meter_rep_id,sn,fault) "
                        + "VALUES(?,?,?)";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, txtRepId.getText());
                pst.setString(2, cbMeterSn.getSelectionModel()
                        .getSelectedItem().getSn());
                pst.setString(3, txtFault.getText());
                int executeUpdate = pst.executeUpdate();

                if (executeUpdate > 0) {
                    myCustomProperty.setAlert("success",
                            "Job Added Successfully!",
                            "Added row will highlight");
                    getAllJobs();
                    lastAdded(txtRepId.getText());
                    clearAllAddFields();
                    hidePanel(pnlAddJob);

                }
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(FXMLHomeJobController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private boolean checkAllFields() {
        if (!"".equals(txtRepId.getText())
                & !"".equals(txtFault.getText())
                & cbMeterSn.getSelectionModel().getSelectedItem() != null) {

            return true;
        } else {
            myCustomProperty.setAlert("danger",
                    "Please fill all Fields!",
                    "You have to complete all fields to continue");

            shakePanel(pnlAddJob);
        }
        return false;
    }

    private void shakePanel(Pane pnl) {
        Shake s = new Shake(pnl);
        s.setSpeed(2);
        s.play();
    }

    private void setMeterSn() {
        try {
            conn = DBConnect.getInstance().getConnection();

            String sql = "SELECT * FROM meter";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            meterList = FXCollections.observableArrayList();

            while (rs.next()) {
                meterList.add(new Meter(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        null, null, null));
            }
            showAddMeterList(meterList);

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLHomeJobController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showAddMeterList(ObservableList<Meter> meterList) {
        cbMeterSn.setItems(meterList);
        cbMeterSn.setConverter(new StringConverter<Meter>() {

            @Override
            public String toString(Meter object) {
                return object.getSn();
            }

            @Override
            public Meter fromString(String string) {
                return null;
            }
        });
    }

    private void lastAdded(String id) {
        for (int i = 0; i < tblJobs.getItems().size(); i++) {
            if (id.equals(tblJobs.getItems().get(i).getMeterRepairId())) {
                tblJobs.requestFocus();
                tblJobs.getSelectionModel().select(i);
                tblJobs.getFocusModel().focus(i);
            }
        }
    }

    private void clearAllAddFields() {
        txtRepId.setText(null);
        cbMeterSn.getSelectionModel().clearSelection();
        txtFault.setText(null);
    }

    @FXML
    void actionAddToList(ActionEvent event) {
        System.out.println(cbComponents.getSelectionModel()
                .getSelectedItem().getComId());
    }

    @FXML
    void actionCompleteJobCancel(ActionEvent event) {
        clearAllJobCompleteFields();
        hidePanel(pnlCompleteJob);
    }

    private void openJobCompletePanel(Pane pnl, String repId) {
        GaussianBlur blur = new GaussianBlur(10);
        pnlMain.setEffect(blur);

        pnl.setDisable(false);
        pnl.setVisible(true);

        lblHeader.setText(repId);
        setComponents();

        ZoomIn in = new ZoomIn(pnl);
        in.setSpeed(2);
        in.play();
    }

    private void openJobQcPanel(Pane pnl, String repId) {
        GaussianBlur blur = new GaussianBlur(10);
        pnlMain.setEffect(blur);

        pnl.setDisable(false);
        pnl.setVisible(true);

        lblQcHeader.setText(repId);

        ZoomIn in = new ZoomIn(pnl);
        in.setSpeed(2);
        in.play();
    }

    private void setComponents() {
        try {
            conn = DBConnect.getInstance().getConnection();

            String sql = "SELECT * FROM component";
            PreparedStatement pst = conn.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();
            componentList = FXCollections.observableArrayList();

            while (rs.next()) {
                componentList.add(new Component(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        new Button(),
                        rs.getInt(4),
                        null, null));
            }
            setComboBoxComponents(componentList);

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLHomeJobController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setComboBoxComponents(ObservableList<Component> componentList) {
        cbComponents.setItems(componentList);
        cbComponents.setConverter(new StringConverter<Component>() {

            @Override
            public String toString(Component object) {
                return "(" + object.getComId() + ") " + object.getName();
            }

            @Override
            public Component fromString(String string) {
                return null;
            }
        });
    }

    @FXML
    void actionCompleteJob(ActionEvent event) throws SQLException {
        if (checkCompleteJobFields()) {
            try {
                conn = DBConnect.getInstance().getConnection();
                conn.setAutoCommit(false);

                if (completeJobTable(conn)) {
                    if (updateComponent(conn)) {
                        if (addJobPrice(conn)) {
                            if (updateRepairTable(conn)) {
                                conn.commit();
                                conn.setAutoCommit(true);

                                myCustomProperty.setAlert("success",
                                        "Job Completed Successfully!",
                                        "Completed row will highlight");

                                clearAllJobCompleteFields();
                                hidePanel(pnlCompleteJob);
                                getAllJobs();
                            } else {
                                conn.rollback();
                            }
                        } else {
                            conn.rollback();
                        }
                    } else {
                        conn.rollback();
                    }
                } else {
                    conn.rollback();
                }
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(FXMLHomeJobController.class.getName())
                        .log(Level.SEVERE, null, ex);
            } finally {
                conn.setAutoCommit(false);
            }
        }
    }

    @FXML
    void actionRemoveListItem(ActionEvent event) {
        if (comList.getSelectionModel().getSelectedItem() != null) {
            comList.getItems().remove(comList.getSelectionModel()
                    .getSelectedIndex());
        } else {
            showListErrorMsg();
        }
    }

    private void showListErrorMsg() {
        Image i = new Image("/img/Cancel_64px.png", true);
        Notifications n = Notifications.create()
                .title("Action Fail!")
                .text("Please select an item from list")
                .graphic(new ImageView(i))
                .hideAfter(Duration.seconds(5))
                .position(Pos.TOP_RIGHT);

        n.show();

        shakeWindow(pnlCompleteJob);
    }

    private void shakeWindow(Pane pnl) {
        Shake s = new Shake(pnl);
        s.setSpeed(2);
        s.play();
    }

    private boolean updateRepairTable(Connection conn)
            throws ClassNotFoundException, SQLException {
        String id = lblHeader.getText();
        String sql = "UPDATE meter_repair SET isrepaired=? WHERE meter_rep_id=?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setBoolean(1, true);
        pst.setString(2, id);
        int executeUpdate = pst.executeUpdate();

        if (executeUpdate > 0) {
            return true;
        }
        return false;
    }

    private void clearAllJobCompleteFields() {
        cbComponents.getSelectionModel().clearSelection();
        txtQty.setText(null);
        txtPrice.setText(null);
    }

    private boolean checkCompleteJobFields() {
        if (cbComponents.getSelectionModel().getSelectedItem() != null
                & !txtQty.getText().isEmpty()
                & !txtPrice.getText().isEmpty()) {

            if (txtQty.getText().matches("[0-9]*")) {
                if (txtPrice.getText().matches("[0-9]*")) {

                    if (availableInStock(cbComponents.getSelectionModel()
                            .getSelectedItem().getComId()) == 0) {

                        myCustomProperty.setAlert("danger", "Low Stock Warning",
                                cbComponents.getSelectionModel().getSelectedItem()
                                .getName() + " not in stock, can't continue this process");
                        
                        shakePanel(pnlCompleteJob);
                    } else if (availableInStock(cbComponents.getSelectionModel()
                            .getSelectedItem().getComId()) < 100) {

                        myCustomProperty.setAlert("attention", "Low Stock Warning",
                                cbComponents.getSelectionModel().getSelectedItem()
                                .getName() + " low in stock, please inform store keeper");
                        
                        shakePanel(pnlCompleteJob);
                        return true;
                    } else {
                        return true;
                    }
                } else {
                    myCustomProperty.setAlert("attention",
                            "Please check price!",
                            "Price should be numbers");
                    shakePanel(pnlCompleteJob);
                }
            } else {
                myCustomProperty.setAlert("attention",
                        "Please check Qty!",
                        "Qty should be numbers");
                shakePanel(pnlCompleteJob);
            }
        } else {
            myCustomProperty.setAlert("danger",
                    "Please fill all Fields!",
                    "You have to complete all fields to continue");
            shakePanel(pnlCompleteJob);
        }
        return false;
    }

    private boolean addJobPrice(Connection conn)
            throws ClassNotFoundException, SQLException {
        String sql = "INSERT INTO repair_payment (price,meter_rep_id) "
                + "VALUES(?,?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, txtPrice.getText());
        pst.setString(2, lblHeader.getText());
        int executeUpdate = pst.executeUpdate();

        if (executeUpdate > 0) {
            return true;
        }
        return false;
    }

    private boolean completeJobTable(Connection conn)
            throws ClassNotFoundException, SQLException {
        String sql = "INSERT INTO repair_component (meter_rep_id,com_id,qty)"
                + "VALUES(?,?,?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, lblHeader.getText());
        pst.setString(2, cbComponents.getSelectionModel()
                .getSelectedItem().getComId());
        pst.setString(3, txtQty.getText());
        int executeUpdate = pst.executeUpdate();

        if (executeUpdate > 0) {
            return true;
        }
        return false;
    }

    private String setCurrency(String price) {
        if (price != null) {
            return "Rs." + price;
        }
        return "";
    }

    private boolean updateComponent(Connection conn) throws SQLException {
        String comId = cbComponents.getSelectionModel()
                .getSelectedItem().getComId();
        int available = getAvailableComAmount(comId, conn);
        int qty = available - Integer.parseInt(txtQty.getText());

        String sql = "UPDATE component SET qty=? WHERE com_id=?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, qty);
        pst.setString(2, comId);
        int executeUpdate = pst.executeUpdate();

        if (executeUpdate > 0) {
            return true;
        }
        return false;
    }

    private int getAvailableComAmount(String comId, Connection conn)
            throws SQLException {
        String sql = "SELECT qty FROM component WHERE com_id=?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, comId);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    @FXML
    void actionCancelQcPanel(ActionEvent event) {
        uncheckAllCheckBox();
        hidePanel(pnlQc);
    }

    @FXML
    void actionCheckAll(ActionEvent event) {
        if (cbCheckAll.isSelected()) {
            cbPowerOn.setSelected(true);
            cbButtons.setSelected(true);
            cbCovers.setSelected(true);
            cbTaxiGreen.setSelected(true);
            cbRemote.setSelected(true);
            cbDisplay.setSelected(true);
            cbConnectors.setSelected(true);
            cbFrontLense.setSelected(true);
            cbTaxiRed.setSelected(true);
            cbPulse.setSelected(true);
        } else {
            cbPowerOn.setSelected(false);
            cbButtons.setSelected(false);
            cbCovers.setSelected(false);
            cbTaxiGreen.setSelected(false);
            cbRemote.setSelected(false);
            cbDisplay.setSelected(false);
            cbConnectors.setSelected(false);
            cbFrontLense.setSelected(false);
            cbTaxiRed.setSelected(false);
            cbPulse.setSelected(false);
        }

    }

    @FXML
    void actionQcJob(ActionEvent event) {

        if (checkAllCheckBox()) {
            try {
                conn = DBConnect.getInstance().getConnection();

                String sql = "INSERT INTO repair_qc (status,meter_rep_id) "
                        + "VALUES(?,?)";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setBoolean(1, true);
                pst.setString(2, lblQcHeader.getText());
                int executeUpdate = pst.executeUpdate();

                if (executeUpdate > 0) {
                    myCustomProperty.setAlert("success",
                            "Job QC Successfully!",
                            "QC row will highlight");
                    uncheckAllCheckBox();
                    hidePanel(pnlQc);
                    getAllJobs();
                }
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(FXMLHomeJobController.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }

    }

    private void uncheckAllCheckBox() {
        cbCheckAll.setSelected(false);
        cbPowerOn.setSelected(false);
        cbButtons.setSelected(false);
        cbCovers.setSelected(false);
        cbTaxiGreen.setSelected(false);
        cbRemote.setSelected(false);
        cbDisplay.setSelected(false);
        cbConnectors.setSelected(false);
        cbFrontLense.setSelected(false);
        cbTaxiRed.setSelected(false);
        cbPulse.setSelected(false);
    }

    private boolean checkAllCheckBox() {
        if (cbPowerOn.isSelected()
                & cbButtons.isSelected()
                & cbCovers.isSelected()
                & cbTaxiGreen.isSelected()
                & cbRemote.isSelected()
                & cbDisplay.isSelected()
                & cbConnectors.isSelected()
                & cbFrontLense.isSelected()
                & cbTaxiRed.isSelected()
                & cbPulse.isSelected()) {

            return true;
        } else {
            myCustomProperty.setAlert("danger", "QC Fail!", "Please check all");
            shakePanel(pnlQc);
        }
        return false;
    }

    private void openCurrierPanel(Pane pnl, String meterRepairId) {
        GaussianBlur blur = new GaussianBlur(10);
        pnlMain.setEffect(blur);

        lblCurrierHeader.setText(meterRepairId);
        pnl.setDisable(false);
        pnl.setVisible(true);

        ZoomIn in = new ZoomIn(pnl);
        in.setSpeed(2);
        in.play();
    }

    @FXML
    void actionCancelCurrierPanel(ActionEvent event) {
        clearCurrierFields();
        hidePanel(pnlJobCurrier);
    }

    @FXML
    void actionCurrierJob(ActionEvent event) {
        if (checkCurrierFields()) {
            try {
                conn = DBConnect.getInstance().getConnection();

                String sql = "INSERT INTO repair_currier "
                        + "(currier_no,meter_rep_id,curr_ser_name) "
                        + "VALUES(?,?,?)";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, txtCurrierNo.getText());
                pst.setString(2, lblCurrierHeader.getText());
                pst.setString(3, txtCurrierName.getText());
                int executeUpdate = pst.executeUpdate();

                if (executeUpdate > 0) {
                    myCustomProperty.setAlert("success",
                            "Job Currier Successfully!",
                            txtCurrierNo.getText() + " updated row will highlight");
                    clearCurrierFields();
                    hidePanel(pnlJobCurrier);
                    getAllJobs();
                }
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(FXMLHomeJobController.class.getName())
                        .log(Level.SEVERE, null, ex);
            }

        }
    }

    private boolean checkCurrierFields() {
        if (!txtCurrierNo.getText().isEmpty()
                & !txtCurrierName.getText().isEmpty()) {
            return true;
        } else {
            myCustomProperty.setAlert("danger",
                    "Please fill all Fields!",
                    "You have to complete all fields to continue");
            shakePanel(pnlJobCurrier);
        }
        return false;
    }

    private void clearCurrierFields() {
        txtCurrierNo.setText(null);
        txtCurrierName.setText(null);
    }

    @FXML
    void eventCalculatePrice(KeyEvent event) {
        if (txtQty.getText().matches("[0-9]*") & !txtQty.getText().isEmpty()
                & !cbComponents.getSelectionModel().isEmpty()) {

            int unitPrice = getUnitPrice(cbComponents.getSelectionModel()
                    .getSelectedItem().getComId());
            txtPrice.setText(String.valueOf(unitPrice
                    * Integer.parseInt(txtQty.getText())));
        } else if (txtQty.getText().isEmpty()) {
            txtPrice.setText(null);
        }
    }

    private int getUnitPrice(String comId) {
        try {
            conn = DBConnect.getInstance().getConnection();

            String sql = "SELECT unit_price FROM component WHERE com_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, comId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLHomeJobController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @FXML
    void eventSearchJob(KeyEvent event) {
        String search = txtSearch.getText();
        try {
            conn = DBConnect.getInstance().getConnection();

            String sql = "SELECT * FROM meter_repair mr "
                    + "LEFT JOIN repair_component rcom "
                    + "ON mr.meter_rep_id=rcom.meter_rep_id "
                    + "LEFT JOIN repair_payment rpay "
                    + "ON rcom.meter_rep_id=rpay.meter_rep_id "
                    + "LEFT JOIN repair_qc rqc "
                    + "ON rpay.meter_rep_id=rqc.meter_rep_id "
                    + "LEFT JOIN repair_currier rcurr "
                    + "ON rqc.meter_rep_id=rcurr.meter_rep_id "
                    + "WHERE mr.meter_rep_id LIKE '" + search + "%' "
                    + "OR mr.sn LIKE '" + search + "%' "
                    + "OR rpay.price LIKE '" + search + "%' "
                    + "OR mr.rep_date LIKE '" + search + "%' "
                    + "OR rcurr.currier_no LIKE '" + search + "%'"
                    + "ORDER BY mr.rep_date DESC";
            Statement cs = conn.createStatement();
            ResultSet rs = cs.executeQuery(sql);
            jobList = FXCollections.observableArrayList();

            while (rs.next()) {
                Label qcStatus = new Label(rs.getString(15));
                Button btn = null;
                Button btnQc = null;
                Button btnCurrier = null;

                if (rs.getBoolean(5)) {
                    btn = new Button("Completed");
                    myCustomProperty.setCompletedButtonDefaultEffect(btn);
                    btn.setDisable(true);

                    if (rs.getBoolean(15)) {
                        btnQc = new Button("Checked");
                        myCustomProperty.setCompletedButtonDefaultEffect(btnQc);
                        btnQc.setDisable(true);
                    } else {
                        btnQc = new Button("QC");
                        myCustomProperty.setCompleteButtonDefaultEffect(btnQc);
                        myCustomProperty.setCompleteButtonEffect(btnQc);

                        btnQc.setDisable(false);
                        btnQc.setVisible(true);
                    }
                } else {
                    btn = new Button("Complete");
                    myCustomProperty.setCompleteButtonDefaultEffect(btn);
                    myCustomProperty.setCompleteButtonEffect(btn);

                    btn.setDisable(false);
                    btn.setVisible(true);

                    btnQc = new Button("QC");
                    myCustomProperty.setCompleteButtonDefaultEffect(btnQc);
                    myCustomProperty.setCompleteButtonEffect(btnQc);

                    btnQc.setDisable(true);
                    btnQc.setVisible(false);
                }

                if (rs.getString(12) == null) {
                    qcStatus.setVisible(false);
                    qcStatus.setDisable(true);
                } else {
                    qcStatus.setVisible(true);
                    qcStatus.setDisable(false);
                }

                if (rs.getBoolean(15)) {
                    if (rs.getString(18) == null) {
                        btnCurrier = new Button("Currier");
                        myCustomProperty.setCompleteButtonDefaultEffect(btnCurrier);
                        myCustomProperty.setCompleteButtonEffect(btnCurrier);

                        btnCurrier.setDisable(false);
                        btnCurrier.setVisible(true);
                    } else {
                        btnCurrier = new Button("Curriered");
                        myCustomProperty.setCompletedButtonDefaultEffect(btnCurrier);

                        btnCurrier.setDisable(true);
                        btnCurrier.setVisible(true);
                    }
                } else {
                    btnCurrier = new Button("Currier");
                    myCustomProperty.setCompleteButtonDefaultEffect(btnCurrier);
                    myCustomProperty.setCompleteButtonEffect(btnCurrier);

                    btnCurrier.setDisable(true);
                    btnCurrier.setVisible(false);
                }

                Button btnDelete = new Button("Delete");
                myCustomProperty.setDeleteButtonEffect(btnDelete);
                myCustomProperty.setDeleteButtonDefaultEffect(btnDelete);

                if (employeeDetails.get(0).getType() == 1) {
                    btnDelete.setDisable(false);
                } else {
                    btnDelete.setDisable(true);
                }

                ImageView icon = new ImageView("/img/job_icon_32px.png");

                Job job = new Job(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        btn,
                        rs.getString(7),
                        setCurrency(rs.getString(11)),
                        btnQc,
                        setQcStatus(qcStatus),
                        rs.getString(18),
                        rs.getDate(4),
                        rs.getBoolean(5),
                        btnCurrier,
                        icon, btnDelete);

                btn.setOnAction((e) -> {
                    openJobCompletePanel(pnlCompleteJob, job.getMeterRepairId());
                });

                btnQc.setOnAction((e) -> {
                    openJobQcPanel(pnlQc, job.getMeterRepairId());
                });

                btnCurrier.setOnAction((e) -> {
                    openCurrierPanel(pnlJobCurrier, job.getMeterRepairId());
                });

                btnDelete.setOnAction((e) -> {
                    openDeleteConfirmPanel(pnlDelete, job.getMeterRepairId());
                });

                jobList.add(job);
            }

            showJobs(jobList);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLHomeJobController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void eventDeleteJob(ActionEvent event) {
        try {
            conn = DBConnect.getInstance().getConnection();

            String sql = "DELETE FROM meter_repair "
                    + "WHERE meter_rep_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, lblDeleteJob.getText());
            int executeUpdate = pst.executeUpdate();

            if (executeUpdate > 0) {
                myCustomProperty.setAlert("success",
                        "Job Removed Successfully!",
                        "You can't undo this operation");
                hidePanel(pnlDelete);
                getAllJobs();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLHomeCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void eventDeleteJobCancel(ActionEvent event) {
        hidePanel(pnlDelete);
    }

    private void openDeleteConfirmPanel(Pane pnl, String meterRepairId) {
        GaussianBlur blur = new GaussianBlur(10);
        pnlMain.setEffect(blur);

        lblDeleteJob.setText(meterRepairId);
        lblDeleteJobHeader.setText(meterRepairId);

        pnl.setDisable(false);
        pnl.setVisible(true);
        ZoomIn in = new ZoomIn(pnl);
        in.setSpeed(2);
        in.play();
    }

    private void printBill(Job job) {
//        new PrintReport().showReport();
    }

    private int availableInStock(String id) {
        try {
            conn = DBConnect.getInstance().getConnection();

            String sql = "SELECT Qty FROM component WHERE com_id='"+id+"'";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLHomeJobController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    @FXML
    void clearTextFields(ActionEvent event){
        txtQty.setText(null);
        txtPrice.setText(null);
    }
}
