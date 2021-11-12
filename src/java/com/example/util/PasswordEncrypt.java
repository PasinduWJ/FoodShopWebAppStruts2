
package com.example.util;

import java.util.Base64;

public class PasswordEncrypt {
    
    public static String pEncode(String password){
        return Base64.getEncoder().encodeToString(password.getBytes());
    }
    
    public static String pDecode(String password){
        return new String(Base64.getMimeDecoder().decode(password));
    }
}
