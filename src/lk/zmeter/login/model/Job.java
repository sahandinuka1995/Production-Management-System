/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.zmeter.login.model;

import java.sql.Date;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 *
 * @author Sahan
 */
public class Job {

    private String meterRepairId;
    private String meterSn;
    private String fault;
    private Button completeJob;
    private String component;
    private String price;
    private Button qcMeterRepId;
    private Label qcStatus;
    private String currierNo;
    private Date date;
    private boolean isRepaired;
    private Button currier;
    private ImageView jobIcon;
    private Button btnDelete;

    public Job(String meterRepairId, String meterSn, String fault, Button completeJob, String component, String price, Button qcMeterRepId, Label qcStatus, String currierNo, Date date, boolean isRepaired, Button currier, ImageView jobIcon, Button btnDelete) {
        this.meterRepairId = meterRepairId;
        this.meterSn = meterSn;
        this.fault = fault;
        this.completeJob = completeJob;
        this.component = component;
        this.price = price;
        this.qcMeterRepId = qcMeterRepId;
        this.qcStatus = qcStatus;
        this.currierNo = currierNo;
        this.date = date;
        this.isRepaired = isRepaired;
        this.currier = currier;
        this.jobIcon = jobIcon;
        this.btnDelete = btnDelete;
    }

    /**
     * @return the meterRepairId
     */
    public String getMeterRepairId() {
        return meterRepairId;
    }

    /**
     * @param meterRepairId the meterRepairId to set
     */
    public void setMeterRepairId(String meterRepairId) {
        this.meterRepairId = meterRepairId;
    }

    /**
     * @return the meterSn
     */
    public String getMeterSn() {
        return meterSn;
    }

    /**
     * @param meterSn the meterSn to set
     */
    public void setMeterSn(String meterSn) {
        this.meterSn = meterSn;
    }

    /**
     * @return the fault
     */
    public String getFault() {
        return fault;
    }

    /**
     * @param fault the fault to set
     */
    public void setFault(String fault) {
        this.fault = fault;
    }

    /**
     * @return the completeJob
     */
    public Button getCompleteJob() {
        return completeJob;
    }

    /**
     * @param completeJob the completeJob to set
     */
    public void setCompleteJob(Button completeJob) {
        this.completeJob = completeJob;
    }

    /**
     * @return the component
     */
    public String getComponent() {
        return component;
    }

    /**
     * @param component the component to set
     */
    public void setComponent(String component) {
        this.component = component;
    }

    /**
     * @return the price
     */
    public String getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * @return the qcMeterRepId
     */
    public Button getQcMeterRepId() {
        return qcMeterRepId;
    }

    /**
     * @param qcMeterRepId the qcMeterRepId to set
     */
    public void setQcMeterRepId(Button qcMeterRepId) {
        this.qcMeterRepId = qcMeterRepId;
    }

    /**
     * @return the qcStatus
     */
    public Label getQcStatus() {
        return qcStatus;
    }

    /**
     * @param qcStatus the qcStatus to set
     */
    public void setQcStatus(Label qcStatus) {
        this.qcStatus = qcStatus;
    }

    /**
     * @return the currierNo
     */
    public String getCurrierNo() {
        return currierNo;
    }

    /**
     * @param currierNo the currierNo to set
     */
    public void setCurrierNo(String currierNo) {
        this.currierNo = currierNo;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the isRepaired
     */
    public boolean isIsRepaired() {
        return isRepaired;
    }

    /**
     * @param isRepaired the isRepaired to set
     */
    public void setIsRepaired(boolean isRepaired) {
        this.isRepaired = isRepaired;
    }

    /**
     * @return the currier
     */
    public Button getCurrier() {
        return currier;
    }

    /**
     * @param currier the currier to set
     */
    public void setCurrier(Button currier) {
        this.currier = currier;
    }

    /**
     * @return the jobIcon
     */
    public ImageView getJobIcon() {
        return jobIcon;
    }

    /**
     * @param jobIcon the jobIcon to set
     */
    public void setJobIcon(ImageView jobIcon) {
        this.jobIcon = jobIcon;
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
