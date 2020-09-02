/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.zmeter.login.controller;

import java.util.Base64;

/**
 *
 * @author Sahan
 */
public class PasswordEncrypt {
    Base64.Encoder encoder = Base64.getEncoder();  
        byte byteArr[] = {1,2};  
        byte byteArr2[] = encoder.encode(byteArr);  
        byte byteArr3[] = new byte[5];
        
        int x = encoder.encode(byteArr,byteArr3);

        public String encrypt(String password){
        return encoder.encodeToString(password.getBytes()); 
        }
        
        public String decrypt(String password){
        Base64.Decoder decoder = Base64.getDecoder();  
        return new String(decoder.decode(password));  
        }
}
