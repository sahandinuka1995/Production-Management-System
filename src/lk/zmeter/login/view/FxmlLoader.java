/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.zmeter.login.view;

import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

/**
 *
 * @author Sahan
 */
public class FxmlLoader {

    private Pane view;

    public Pane getPage(String fileName) {
        try {
            URL filUrl = Home.class.getResource(fileName + ".fxml");

            if (filUrl == null) {
                throw new java.io.FileNotFoundException("FXML file not found");
            }
            view = FXMLLoader.load(filUrl);
        } catch (Exception e) {
            System.out.println("file not found");
        }
        return view;
    }
}
