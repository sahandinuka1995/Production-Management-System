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
public class Meter {

    private String sn;
    private String meterTypeId;
    private String nic;
    private Button btn;
    private ImageView meterIcon;
    private Button btnDelete;

    public Meter(String sn, String meterTypeId, String nic, Button btn, ImageView meterIcon, Button btnDelete) {
        this.sn = sn;
        this.meterTypeId = meterTypeId;
        this.nic = nic;
        this.btn = btn;
        this.meterIcon = meterIcon;
        this.btnDelete = btnDelete;
    }

    /**
     * @return the sn
     */
    public String getSn() {
        return sn;
    }

    /**
     * @param sn the sn to set
     */
    public void setSn(String sn) {
        this.sn = sn;
    }

    /**
     * @return the meterTypeId
     */
    public String getMeterTypeId() {
        return meterTypeId;
    }

    /**
     * @param meterTypeId the meterTypeId to set
     */
    public void setMeterTypeId(String meterTypeId) {
        this.meterTypeId = meterTypeId;
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
     * @return the meterIcon
     */
    public ImageView getMeterIcon() {
        return meterIcon;
    }

    /**
     * @param meterIcon the meterIcon to set
     */
    public void setMeterIcon(ImageView meterIcon) {
        this.meterIcon = meterIcon;
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
