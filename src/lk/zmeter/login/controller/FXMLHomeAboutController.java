/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.zmeter.login.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Sahan
 */
public class FXMLHomeAboutController implements Initializable {

    @FXML
    private ImageView imgLinkedin;
    @FXML
    private ImageView imgWeb;
    @FXML
    private ImageView imgYoutube;
    @FXML
    private ImageView imgFacebook;
    @FXML
    private ImageView imgInstagram;
    @FXML
    private ImageView imgTwitter;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    void linkedIn(ActionEvent event) {
        new FXMLLinks().linkedInPage();
    }

    @FXML
    void web(ActionEvent event) {
        new FXMLLinks().webPage();
    }

    @FXML
    void youTube(ActionEvent event) {
        new FXMLLinks().youTubePage();
    }

    @FXML
    void faceBook(ActionEvent event) {
        new FXMLLinks().facebookPage();
    }

    @FXML
    void instagram(ActionEvent event) {
        new FXMLLinks().instagramPage();
    }

    @FXML
    void twitter(ActionEvent event) {
        new FXMLLinks().twitterPage();
    }

    @FXML
    void eventLinkedinZoom(MouseEvent event) {
        zoomBtnImg(imgLinkedin, "in");
    }

    @FXML
    void eventLinkedinZoomOut(MouseEvent event) {
        zoomBtnImg(imgLinkedin, "out");
    }

    @FXML
    void eventWebZoomIn(MouseEvent event) {
        zoomBtnImg(imgWeb, "in");
    }

    @FXML
    void eventWebZoomOut(MouseEvent event) {
        zoomBtnImg(imgWeb, "out");
    }

    @FXML
    void eventYtZoomIn(MouseEvent event) {
        zoomBtnImg(imgYoutube, "in");
    }

    @FXML
    void eventYtZoomOut(MouseEvent event) {
        zoomBtnImg(imgYoutube, "out");
    }

    @FXML
    void eventFacebookZoomIn(MouseEvent event) {
        zoomBtnImg(imgFacebook, "in");
    }

    @FXML
    void eventFacebookZoomOut(MouseEvent event) {
        zoomBtnImg(imgFacebook, "out");
    }

    @FXML
    void eventInstagramZoomIn(MouseEvent event) {
        zoomBtnImg(imgInstagram, "in");
    }

    @FXML
    void eventInstagramZoomOut(MouseEvent event) {
        zoomBtnImg(imgInstagram, "out");
    }

    @FXML
    void eventTwitterZoomIn(MouseEvent event) {
        zoomBtnImg(imgTwitter, "in");
    }

    @FXML
    void eventTwitterZoomOut(MouseEvent event) {
        zoomBtnImg(imgTwitter, "out");
    }

    private void zoomBtnImg(ImageView img, String state) {
        if ("in".equals(state)) {
            img.setFitHeight(35);
            img.setFitWidth(35);
        } else {
            img.setFitHeight(30);
            img.setFitWidth(30);
        }
    }
}
