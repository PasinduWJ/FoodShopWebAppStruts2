
package com.example.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeSetting {
    
    public static String getCurrentDateTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        Date date = new Date();  
        return formatter.format(date);
    } 
    
    public static String setDateTime(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        return formatter.format(date);
    } 
}
