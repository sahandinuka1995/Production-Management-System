/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.zmeter.login.controller;

import javafx.collections.ObservableList;
import lk.zmeter.login.model.Employee;

/**
 *
 * @author Sahan
 */
public class UserSession {
    private static String username;
    private static ObservableList<Employee> loggedEmployee;
    
    public UserSession() {}
    
    public UserSession(ObservableList<Employee> loggedEmployee) {
        this.loggedEmployee=loggedEmployee;
    }
    
    public String getUsername(){
        return loggedEmployee.get(0).getUsername();
    }
    
    public ObservableList<Employee> getEmployee(){
        return loggedEmployee;
    }
}
