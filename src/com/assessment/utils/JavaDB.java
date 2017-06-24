
package com.assessment.utils;

import java.sql.*;
import javax.swing.*;

public class JavaDB {
    
    private static Connection con = null;
    private static String host;
    private static String username;
    private static String password;
    
    public static Connection DBConnection(){
        try{
            host = "jdbc:mysql://localHost:3307/AssessmentDatabase";
            username = "root";
            password = "tsaatey";
            con = DriverManager.getConnection(host, username, password);
            return con;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        return null;
    }
}
