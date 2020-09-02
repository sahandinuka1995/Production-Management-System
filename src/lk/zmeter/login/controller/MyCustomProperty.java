/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.zmeter.login.controller;

import animatefx.animation.Shake;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 *
 * @author Sahan
 */
public class MyCustomProperty {

    public void setButtonDefaultEffect(Button btn) {
        btn.setStyle("-fx-background-color: transparent;"
                + " -fx-border-width: 1;"
                + " -fx-border-color: #F3AA18;"
                + " -fx-border-radius: 10;"
                + " -fx-pref-width: 80px;"
                + " -fx-text-fill: #F3AA18");
        btn.setGraphic(new ImageView("/img/Edit_Property_16px.png"));
    }

    public void setButtonEffect(Button btn) {
        btn.setOnMouseEntered((e) -> {
            btn.setStyle("-fx-background-color: #F3AA18;"
                    + " -fx-border-width: 1;"
                    + " -fx-border-color: #F3AA18;"
                    + " -fx-border-radius: 10;"
                    + " -fx-pref-width: 80px;"
                    + " -fx-text-fill: #ffffff;"
                    + "-fx-effect: dropshadow(gaussian, #F5B462, 10,"
                    + " 0.2, 0.0, 0.0);");
            btn.setGraphic(new ImageView("/img/Edit_white_Property_16px.png"));
        });

        btn.setOnMouseExited((e) -> {
            btn.setStyle("-fx-background-color: transparent;"
                    + " -fx-border-width: 1;"
                    + " -fx-border-color: #F3AA18;"
                    + " -fx-border-radius: 10;"
                    + " -fx-pref-width: 80px;"
                    + " -fx-text-fill: #F3AA18");
            btn.setGraphic(new ImageView("/img/Edit_Property_16px.png"));
        });
    }

    public void setAlert(String type, String title, String text) {
        if (null != type) {
            switch (type) {
                case "success": {
                    Image i = new Image("/img/img_success.png", true);
                    Notifications n = Notifications.create()
                            .title(title)
                            .text(text)
                            .graphic(new ImageView(i))
                            .hideAfter(Duration.seconds(5))
                            .position(Pos.TOP_RIGHT);
                    n.show();
                    break;
                }
                case "info": {
                    Image i = new Image("/img/img_info.png", true);
                    Notifications n = Notifications.create()
                            .title(title)
                            .text(text)
                            .graphic(new ImageView(i))
                            .hideAfter(Duration.seconds(5))
                            .position(Pos.TOP_RIGHT);
                    n.show();
                    break;
                }
                case "danger": {
                    Image i = new Image("/img/img_danger.png", true);
                    Notifications n = Notifications.create()
                            .title(title)
                            .text(text)
                            .graphic(new ImageView(i))
                            .hideAfter(Duration.seconds(5))
                            .position(Pos.TOP_RIGHT);
                    n.show();
                    break;
                }
                case "attention":
                    Image i = new Image("/img/img_attention.png", true);
                    Notifications n = Notifications.create()
                            .title(title)
                            .text(text)
                            .graphic(new ImageView(i))
                            .hideAfter(Duration.seconds(5))
                            .position(Pos.TOP_RIGHT);
                    n.show();
                    break;
            }
        }

    }

    public void setCompletedButtonDefaultEffect(Button btn) {
        btn.setStyle("-fx-background-color: transparent;"
                + " -fx-text-fill: #2FA84F;");
        btn.setGraphic(new ImageView("/img/Checkmark_16px.png"));
    }

    public void setCompleteButtonDefaultEffect(Button btn) {
        btn.setStyle("-fx-background-color: transparent;"
                + " -fx-border-width: 1;"
                + " -fx-border-color: #F3AA18;"
                + " -fx-border-radius: 10;"
                + " -fx-pref-width: 100px;"
                + " -fx-text-fill: #F3AA18");
        btn.setGraphic(new ImageView("/img/Edit_Property_16px.png"));
    }

    public void setCompleteButtonEffect(Button btn) {
        btn.setOnMouseEntered((e) -> {
            btn.setStyle("-fx-background-color: #F3AA18;"
                    + " -fx-border-width: 1;"
                    + " -fx-border-color: #F3AA18;"
                    + " -fx-border-radius: 10;"
                    + " -fx-pref-width: 100px;"
                    + " -fx-text-fill: #ffffff;"
                    + "-fx-effect: dropshadow(gaussian, #F5B462, 10, 0.2,"
                    + " 0.0, 0.0);");
            btn.setGraphic(new ImageView("/img/Edit_white_Property_16px.png"));
        });

        btn.setOnMouseExited((e) -> {
            btn.setStyle("-fx-background-color: transparent;"
                    + " -fx-border-width: 1;"
                    + " -fx-border-color: #F3AA18;"
                    + " -fx-border-radius: 10;"
                    + " -fx-pref-width: 100px;"
                    + " -fx-text-fill: #F3AA18");
            btn.setGraphic(new ImageView("/img/Edit_Property_16px.png"));
        });
    }

    public String setPriceFormat(String price) {
        if (price != null) {
            return "Rs." + price;
        }
        return "";
    }
    
    public void shakePanel(Pane pnl) {
        Shake s = new Shake(pnl);
        s.setSpeed(2);
        s.play();
    }
    
    public void setDeleteButtonDefaultEffect(Button btn) {
        btn.setStyle("-fx-background-color: transparent;"
                + " -fx-border-width: 1;"
                + " -fx-border-color: #E86343;"
                + " -fx-border-radius: 10;"
                + " -fx-pref-width: 80px;"
                + " -fx-text-fill: #E86343");
        btn.setGraphic(new ImageView("/img/Trash_16px.png"));
    }

    public void setDeleteButtonEffect(Button btn) {
        btn.setOnMouseEntered((e) -> {
            btn.setStyle("-fx-background-color: #E86343;"
                    + " -fx-border-width: 1;"
                    + " -fx-border-color: #E86343;"
                    + " -fx-border-radius: 10;"
                    + " -fx-pref-width: 80px;"
                    + " -fx-text-fill: #ffffff;"
                    + "-fx-effect: dropshadow(gaussian, #E86343, 10,"
                    + " 0.2, 0.0, 0.0);");
            btn.setGraphic(new ImageView("/img/Trash_white_16px.png"));
        });

        btn.setOnMouseExited((e) -> {
            btn.setStyle("-fx-background-color: transparent;"
                    + " -fx-border-width: 1;"
                    + " -fx-border-color: #E86343;"
                    + " -fx-border-radius: 10;"
                    + " -fx-pref-width: 80px;"
                    + " -fx-text-fill: #E86343");
            btn.setGraphic(new ImageView("/img/Trash_16px.png"));
        });
    }
    
    public void setAcceptButtonDefaultEffect(Button btn) {
        btn.setStyle("-fx-background-color: transparent;"
                + " -fx-border-width: 1;"
                + " -fx-border-color: #3EAE52;"
                + " -fx-border-radius: 10;"
                + " -fx-pref-width: 100px;"
                + " -fx-text-fill: #3EAE52");
        btn.setGraphic(new ImageView("/img/Checked_16px.png"));
    }

    public void setAcceptButtonEffect(Button btn) {
        btn.setOnMouseEntered((e) -> {
            btn.setStyle("-fx-background-color: #3EAE52;"
                    + " -fx-border-width: 1;"
                    + " -fx-border-color: #3EAE52;"
                    + " -fx-border-radius: 10;"
                    + " -fx-pref-width: 100px;"
                    + " -fx-text-fill: #ffffff;"
                    + "-fx-effect: dropshadow(gaussian, #3EAE52, 10,"
                    + " 0.2, 0.0, 0.0);");
            btn.setGraphic(new ImageView("/img/Checked_white_16px.png"));
        });

        btn.setOnMouseExited((e) -> {
            btn.setStyle("-fx-background-color: transparent;"
                    + " -fx-border-width: 1;"
                    + " -fx-border-color: #3EAE52;"
                    + " -fx-border-radius: 10;"
                    + " -fx-pref-width: 100px;"
                    + " -fx-text-fill: #3EAE52");
            btn.setGraphic(new ImageView("/img/Checked_16px.png"));
        });
    }

    void setAcceptButtonDefaultDisableEffect(Button btn) {
        btn.setStyle("-fx-background-color: transparent;"
                + " -fx-border-width: 1;"
                + " -fx-border-color: #A6A6A6;"
                + " -fx-border-radius: 10;"
                + " -fx-pref-width: 100px;"
                + " -fx-text-fill: #A6A6A6");
        btn.setGraphic(new ImageView("/img/Checked_grey_16px.png"));
    }
}
