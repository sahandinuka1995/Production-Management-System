/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.zmeter.login.controller;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sahan
 */
public class FXMLLinks {
    public void myFacebookPage(){
        try {
            Desktop.getDesktop().browse(
                    new URL("https://facebook.com/sahandinukasd").toURI());
        } catch (IOException | URISyntaxException ex) {
            Logger.getLogger(FXMLLinks.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
    public void linkedInPage(){
        try {
            Desktop.getDesktop().browse(
                    new URL("https://www.linkedin.com/in/sahan-dinuka-107409195").toURI());
        } catch (IOException | URISyntaxException ex) {
            Logger.getLogger(FXMLLinks.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
    public void webPage(){
        try {
            Desktop.getDesktop().browse(
                    new URL("http://sdctechnews.blogspot.com").toURI());
        } catch (IOException | URISyntaxException ex) {
            Logger.getLogger(FXMLLinks.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
    public void youTubePage(){
        try {
            Desktop.getDesktop().browse(
                    new URL("http://youtube.com/sdofficialsln").toURI());
        } catch (IOException | URISyntaxException ex) {
            Logger.getLogger(FXMLLinks.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
    public void facebookPage(){
        try {
            Desktop.getDesktop().browse(
                    new URL("https://www.facebook.com/allsdcomputers").toURI());
        } catch (IOException | URISyntaxException ex) {
            Logger.getLogger(FXMLLinks.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
    public void instagramPage(){
        try {
            Desktop.getDesktop().browse(
                    new URL("https://www.instagram.com/sahandinukasd").toURI());
        } catch (IOException | URISyntaxException ex) {
            Logger.getLogger(FXMLLinks.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
    public void twitterPage(){
        try {
            Desktop.getDesktop().browse(
                    new URL("https://twitter.com/sdofficialsl").toURI());
        } catch (IOException | URISyntaxException ex) {
            Logger.getLogger(FXMLLinks.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
}
