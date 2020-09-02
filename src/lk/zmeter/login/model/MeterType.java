/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.zmeter.login.model;

/**
 *
 * @author Sahan
 */
public class MeterType {

    private String meterTypeId;
    private String version;
    private String meterial;
    private String printer;

    public MeterType(String meterTypeId, String version, String meterial, String printer) {
        this.meterTypeId = meterTypeId;
        this.version = version;
        this.meterial = meterial;
        this.printer = printer;
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
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the meterial
     */
    public String getMeterial() {
        return meterial;
    }

    /**
     * @param meterial the meterial to set
     */
    public void setMeterial(String meterial) {
        this.meterial = meterial;
    }

    /**
     * @return the printer
     */
    public String getPrinter() {
        return printer;
    }

    /**
     * @param printer the printer to set
     */
    public void setPrinter(String printer) {
        this.printer = printer;
    }
}
