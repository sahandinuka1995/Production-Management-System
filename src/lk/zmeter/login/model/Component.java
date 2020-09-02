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
public class Component {

    private String comId;
    private String name;
    private String qty;
    private Button btn;
    private int unitPrice;
    private ImageView comIcon;
    private Button btnDelete;

    public Component(String comId, String name, String qty, Button btn, int unitPrice, ImageView comIcon, Button btnDelete) {
        this.comId = comId;
        this.name = name;
        this.qty = qty;
        this.btn = btn;
        this.unitPrice = unitPrice;
        this.comIcon = comIcon;
        this.btnDelete = btnDelete;
    }

    /**
     * @return the comId
     */
    public String getComId() {
        return comId;
    }

    /**
     * @param comId the comId to set
     */
    public void setComId(String comId) {
        this.comId = comId;
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
     * @return the qty
     */
    public String getQty() {
        return qty;
    }

    /**
     * @param qty the qty to set
     */
    public void setQty(String qty) {
        this.qty = qty;
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
     * @return the unitPrice
     */
    public int getUnitPrice() {
        return unitPrice;
    }

    /**
     * @param unitPrice the unitPrice to set
     */
    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * @return the comIcon
     */
    public ImageView getComIcon() {
        return comIcon;
    }

    /**
     * @param comIcon the comIcon to set
     */
    public void setComIcon(ImageView comIcon) {
        this.comIcon = comIcon;
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
