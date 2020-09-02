/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.zmeter.login.controller;

import animatefx.animation.Bounce;
import animatefx.animation.FadeIn;
import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideOutLeft;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.zmeter.login.model.Employee;
import lk.zmeter.login.view.FxmlLoader;
import lk.zmeter.login.view.Login;

/**
 * FXML Controller class
 *
 * @author Sahan
 */
public class FXMLHomeController implements Initializable {

    ObservableList<Employee> loggedEmployee;
    MyCustomProperty myCustomProperty;

    @FXML
    private BorderPane pnlMain;
    @FXML
    public static Pane view;
    @FXML
    private Pane pnlTop;
    @FXML
    private Pane pnlUserOption;

    @FXML
    private Label lblHeader;
    @FXML
    public Label lblUsername;

    @FXML
    private Circle circle1;
    @FXML
    private Circle circle2;
    @FXML
    private Circle circle3;

    @FXML
    private ImageView imgDashboard;
    @FXML
    private ImageView imgProfileImage;
    @FXML
    private ImageView imgCustomer;
    @FXML
    private ImageView imgJob;
    @FXML
    private ImageView imgComponent;
    @FXML
    private ImageView imgMeter;
    @FXML
    private ImageView imgAbout;

    @FXML
    private Label lblDashboard;
    @FXML
    private Label lblCustomer;
    @FXML
    private Label lblJob;
    @FXML
    private Label lblComponent;
    @FXML
    private Label lblMeter;
    @FXML
    private Label lblAbout;
    @FXML
    private Label lblDateTime;

    @FXML
    private Button btnLogout;

    @FXML
    public void dashboardPressed(ActionEvent event) {
        loadSubPanel("FXMLHomeDashboard", "Dashboard");
    }

    @FXML
    public void customerPressed(ActionEvent event) {
        loadSubPanel("FXMLHomeCustomer", "Customers");
    }

    @FXML
    public void meterPressed(ActionEvent event) {
        loadSubPanel("FXMLHomeMeter", "Meters");
    }

    @FXML
    public void jobPressed(ActionEvent event) {
        loadSubPanel("FXMLHomeJob", "Jobs");
    }

    @FXML
    public void componentPressed(ActionEvent event) {
        loadSubPanel("FXMLHomeComponent", "Components");
    }

    @FXML
    public void aboutPressed(ActionEvent event) {
        loadSubPanel("FXMLHomeAbout", "About");
    }

    @FXML
    public void userPressed(ActionEvent event) {
        if (loggedEmployee.get(0).getType() == 1) {
            loadSubPanel("FXMLHomeUser", "Users");
        } else {
            myCustomProperty.setAlert("danger", "Access Denied!", "You haven't permission to view this");
        }
    }

    public void loadSubPanel(String pageName, String setText) {
        FxmlLoader object = new FxmlLoader();
        view = object.getPage(pageName);
        lblHeader.setText(setText);
        pnlMain.setCenter(view);

        setExitPanelVisibility();
        setTopAndMainPanelAnimation();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        myCustomProperty = new MyCustomProperty();
        ObservableList<Employee> loggedEmployee = new UserSession().getEmployee();
        this.loggedEmployee = loggedEmployee;

        setUserDetails();

        new Bounce(circle1).setCycleCount(50)
                .setDelay(Duration.valueOf("500ms")).play();
        new Bounce(circle2).setCycleCount(50)
                .setDelay(Duration.valueOf("600ms")).play();
        new Bounce(circle3).setCycleCount(50)
                .setDelay(Duration.valueOf("700ms")).play();

        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                }
                return null;
            }
        };

        sleeper.setOnSucceeded((WorkerStateEvent event) -> {
            openDashboard();
            setDateTime();
        });
        new Thread(sleeper).start();

        new Bounce(circle1).setCycleCount(50)
                .setDelay(Duration.valueOf("500ms")).stop();
        new Bounce(circle2).setCycleCount(50)
                .setDelay(Duration.valueOf("600ms")).stop();
        new Bounce(circle3).setCycleCount(50)
                .setDelay(Duration.valueOf("700ms")).stop();
    }

    private void openDashboard() {
        FxmlLoader object = new FxmlLoader();
        view = object.getPage("FXMLHomeDashboard");
        pnlMain.setCenter(view);

        FadeTransition ft = new FadeTransition(Duration.millis(3000), pnlTop);
        ft.setFromValue(0);
        ft.setToValue(1.0);
        ft.play();

        FadeTransition ft1 = new FadeTransition(Duration.millis(3000), pnlMain);
        ft1.setFromValue(0);
        ft1.setToValue(1.0);
        ft1.play();
    }

    public void setUserImage(String names) {
        //imgProfileImage.setImage(new Image("/img/" + names + ".png"));
    }

    @FXML
    void eventhoverAbout(MouseEvent event) {
        Glow g = new Glow();
        g.setLevel(2);
        imgAbout.setEffect(g);
        lblAbout.setEffect(g);
    }

    @FXML
    void eventExitAbout(MouseEvent event) {
        Glow g = new Glow();
        g.setLevel(0);
        imgAbout.setEffect(g);
        lblAbout.setEffect(g);
    }

    @FXML
    void eventhoverCom(MouseEvent event) {
        Glow g = new Glow();
        g.setLevel(2);
        imgComponent.setEffect(g);
        lblComponent.setEffect(g);
    }

    @FXML
    void eventExitCom(MouseEvent event) {
        Glow g = new Glow();
        g.setLevel(0);
        imgComponent.setEffect(g);
        lblComponent.setEffect(g);
    }

    @FXML
    void eventhoverCus(MouseEvent event) {
        Glow g = new Glow();
        g.setLevel(2);
        imgCustomer.setEffect(g);
        lblCustomer.setEffect(g);
    }

    @FXML
    void eventExitCus(MouseEvent event) {
        Glow g = new Glow();
        g.setLevel(0);
        imgCustomer.setEffect(g);
        lblCustomer.setEffect(g);
    }

    @FXML
    void eventhoverDash(MouseEvent event) {
        Glow g = new Glow();
        g.setLevel(2);
        imgDashboard.setEffect(g);
        lblDashboard.setEffect(g);

    }

    @FXML
    void actionGetHelp(ActionEvent event) throws IOException, URISyntaxException {
        new FXMLLinks().myFacebookPage();
        pnlUserOption.setVisible(false);
        pnlUserOption.setDisable(true);
    }

    @FXML
    void eventExitDash(MouseEvent event) {
        Glow g = new Glow();
        g.setLevel(0);
        imgDashboard.setEffect(g);
        lblDashboard.setEffect(g);
    }

    @FXML
    void eventhoverJob(MouseEvent event) {
        Glow g = new Glow();
        g.setLevel(2);
        imgJob.setEffect(g);
        lblJob.setEffect(g);
    }

    @FXML
    void eventExitJob(MouseEvent event) {
        Glow g = new Glow();
        g.setLevel(0);
        imgJob.setEffect(g);
        lblJob.setEffect(g);
    }

    @FXML
    void eventhoverMeter(MouseEvent event) {
        Glow g = new Glow();
        g.setLevel(2);
        imgMeter.setEffect(g);
        lblMeter.setEffect(g);
    }

    @FXML
    void eventExitMeter(MouseEvent event) {
        Glow g = new Glow();
        g.setLevel(0);
        imgMeter.setEffect(g);
        lblMeter.setEffect(g);
    }

    @FXML
    void actionBtnHide(ActionEvent event) {
        setExitPanelVisibility();

        SlideOutLeft slideOutLeft = new SlideOutLeft(pnlTop);
        slideOutLeft.setSpeed(1);
        slideOutLeft.play();

        SlideOutLeft slideOutLeft1 = new SlideOutLeft(pnlMain);
        slideOutLeft1.setSpeed(1);
        slideOutLeft1.play();
    }

    @FXML
    void eventExitWindow(ActionEvent event) {
        if (pnlUserOption.isVisible()) {
            pnlUserOption.setDisable(true);
            pnlUserOption.setVisible(false);

            FadeIn down = new FadeIn(pnlUserOption);
            down.setSpeed(0.5);
            down.play();
        } else {
            pnlUserOption.setDisable(false);
            pnlUserOption.setVisible(true);
        }
    }

    @FXML
    void actionBtnExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void actionBtnLogout(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnLogout.getScene().getWindow();
        stage.close();

        new Login().start(new Stage());
    }

    public void getRespondFromEditCustomer() {
        FxmlLoader object = new FxmlLoader();
        view = object.getPage("FXMLHomeCustomer");
        lblHeader.setText("Customers");
        pnlMain.setCenter(view);
    }

    private void setDateTime() {
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                return null;
            }
        };

        sleeper.setOnSucceeded((WorkerStateEvent event) -> {
            setWelcomeText();
            setSleeperDateTime();
        });
        new Thread(sleeper).start();

    }

    private void setExitPanelVisibility() {
        if (pnlUserOption.isVisible()) {
            pnlUserOption.setVisible(false);
        }
    }

    private void setTopAndMainPanelAnimation() {
        SlideInLeft slideInUp = new SlideInLeft(pnlTop);
        slideInUp.setSpeed(1);
        slideInUp.play();

        SlideInLeft slideInUp2 = new SlideInLeft(pnlMain);
        slideInUp2.setSpeed(1);
        slideInUp2.play();
    }

    private void setWelcomeText() {
        lblDateTime.setText("Hi, " + lblUsername.getText());
        FadeIn fadeIn = new FadeIn(lblDateTime);
        fadeIn.setSpeed(0.1);
        fadeIn.play();
    }

    private void setTextDateTime() {
        Timeline t = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter
                    .ofPattern("yyyy-MM-dd hh:mm:ss a");
            lblDateTime.setText("Today is : " + LocalDateTime.now()
                    .format(dateTimeFormatter));
        }),
                new KeyFrame(Duration.seconds(1))
        );

        t.setCycleCount(Animation.INDEFINITE);
        t.play();

        FadeIn f = new FadeIn(lblDateTime);
        f.setSpeed(1);
        f.play();
    }

    private void setSleeperDateTime() {
        Task<Void> sleeper1 = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                }
                return null;
            }
        };

        sleeper1.setOnSucceeded((WorkerStateEvent event) -> {
            setTextDateTime();
        });
        new Thread(sleeper1).start();
    }

    @FXML
    void actionBtnProfile(ActionEvent event) {
        FxmlLoader object = new FxmlLoader();
        view = object.getPage("FXMLUserAccount");
        lblHeader.setText("User Account");
        pnlMain.setCenter(view);

        setExitPanelVisibility();
        setTopAndMainPanelAnimation();
    }

    private void setUserDetails() {
        imgProfileImage.setImage(new Image("/img/" + loggedEmployee.get(0)
                .getUser_image_id() + ".png"));
        lblUsername.setText(loggedEmployee.get(0).getUsername());
    }
}
