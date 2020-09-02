/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.zmeter.login.view;

import animatefx.animation.FadeIn;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.zmeter.login.controller.FXMLHomeController;
import lk.zmeter.login.controller.UserSession;

/**
 *
 * @author Sahan
 */
public class Home extends Application {

    private double x, y;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Home.class.getResource("FXMLHome.fxml"));
        Parent root = loader.load();
        //Parent root = FXMLLoader.load(getClass().getResource("FXMLHome.fxml"));

        Scene scene = new Scene(root);
        stage.getIcons().add(new Image("/img/icon.png"));
        stage.setScene(scene);
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);

        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });

        stage.show();
        
        FadeIn fadein=new FadeIn(root);
        fadein.setSpeed(1);
        fadein.play();
    }

    public void test() {
        System.out.println("test");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
