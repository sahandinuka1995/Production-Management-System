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
public class Customer {

    private String nic;
    private String name;
    private String address;
    private String phoneNo;
    private String vehicleNo;
    private Button btn;
    private ImageView cusIcon;
    private Button btnDelete;

    public Customer(String nic, String name, String address, String phoneNo, String vehicleNo, Button btn, ImageView cusIcon, Button btnDelete) {
        this.nic = nic;
        this.name = name;
        this.address = address;
        this.phoneNo = phoneNo;
        this.vehicleNo = vehicleNo;
        this.btn = btn;
        this.cusIcon = cusIcon;
        this.btnDelete = btnDelete;
    }

    /**
     * @return the nic
     */
    public String getNic() {
        return nic;
    }

    /**
     * @param nic the nic to set
     */
    public void setNic(String nic) {
        this.nic = nic;
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
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the phoneNo
     */
    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     * @param phoneNo the phoneNo to set
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    /**
     * @return the vehicleNo
     */
    public String getVehicleNo() {
        return vehicleNo;
    }

    /**
     * @param vehicleNo the vehicleNo to set
     */
    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    /**
     * @return the btn
     */
    public Button getBtn() {
        return btn;
    }

    /**
     * @param btn the btn to set
     */
    public void setBtn(Button btn) {
        this.btn = btn;
    }

    /**
     * @return the cusIcon
     */
    public ImageView getCusIcon() {
        return cusIcon;
    }

    /**
     * @param cusIcon the cusIcon to set
     */
    public void setCusIcon(ImageView cusIcon) {
        this.cusIcon = cusIcon;
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

    
}
