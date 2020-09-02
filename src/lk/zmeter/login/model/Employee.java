/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.zmeter.login.model;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 *
 * @author Sahan
 */
public class Employee {
    private int id;
    private String username;
    private String name;
    private String password;
    private String phone_no;
    private String last_log;
    private String user_image_id;
    private int type;
    private Button first_login;
    private ImageView userImage;
    private Button btnDelete;
    private Button edit;

    public Employee(int id, String username, String name, String password, String phone_no, String last_log, int type, Button first_login, ImageView userImage, Button btnDelete, Button edit) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.password = password;
        this.phone_no = phone_no;
        this.last_log = last_log;
        this.type = type;
        this.first_login = first_login;
        this.userImage = userImage;
        this.btnDelete=btnDelete;
        this.edit=edit;
    }

    public Employee(int id, String username, String name, String password, String phone_no, String last_log, String user_image_id, int type, Button first_login) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.password = password;
        this.phone_no = phone_no;
        this.last_log = last_log;
        this.user_image_id = user_image_id;
        this.type = type;
        this.first_login = first_login;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the phone_no
     */
    public String getPhone_no() {
        return phone_no;
    }

    /**
     * @param phone_no the phone_no to set
     */
    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    /**
     * @return the last_log
     */
    public String getLast_log() {
        return last_log;
    }

    /**
     * @param last_log the last_log to set
     */
    public void setLast_log(String last_log) {
        this.last_log = last_log;
    }

    /**
     * @return the user_image_id
     */
    public String getUser_image_id() {
        return user_image_id;
    }

    /**
     * @param user_image_id the user_image_id to set
     */
    public void setUser_image_id(String user_image_id) {
        this.user_image_id = user_image_id;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return the first_login
     */
    public Button isFirst_login() {
        return first_login;
    }

    /**
     * @param first_login the first_login to set
     */
    public void setFirst_login(Button first_login) {
        this.first_login = first_login;
    }

    /**
     * @return the userImage
     */
    public ImageView getUserImage() {
        return userImage;
    }

    /**
     * @param userImage the userImage to set
     */
    public void setUserImage(ImageView userImage) {
        this.userImage = userImage;
    }

    /**
     * @return the btnDelete
     */
    public Button getBtnDelete() {
        return btnDelete;
    }

    /**
     * @param btnDelete the btnDelete to set
     */
    public void setBtnDelete(Button btnDelete) {
        this.btnDelete = btnDelete;
    }

    /**
     * @return the edit
     */
    public Button getEdit() {
        return edit;
    }

    /**
     * @param edit the edit to set
     */
    public void setEdit(Button edit) {
        this.edit = edit;
    }

    
}
