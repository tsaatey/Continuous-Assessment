/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assessment.bussinessmodel;

/**
 *
 * @author ARTLIB
 */

import com.assessment.models.Teacher;
import com.assessment.utils.JavaDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User_Login {
    
    private static Connection con;
    private static PreparedStatement pst;
    private static ResultSet rs;
    
    /**
     * 
     * @param teacher
     * @return
     * @throws SQLException 
     */
    public boolean SignUp(Teacher teacher) throws SQLException {
        con = JavaDB.DBConnection();

        String sql = "INSERT INTO User_Login VALUES(?,?,?,?,?,?,?,?,?)";
        pst = con.prepareStatement(sql);

        pst.setInt(1, teacher.getStaffID());
        pst.setString(2, teacher.getFirstname());
        pst.setString(3, teacher.getLastname());
        pst.setString(4, teacher.getMiddlename());
        pst.setString(5, teacher.getSex());
        pst.setString(6, teacher.getStatus());
        pst.setString(7, teacher.getUsername());
        pst.setString(8, teacher.getPassword());
        pst.setString(9, teacher.getPasswordHint());

        pst.execute();
        return true;
    }
    
    /**
     * 
     * @param teacher
     * @return
     * @throws SQLException 
     */
    
    public boolean SignIn(Teacher teacher) throws SQLException{
        con = JavaDB.DBConnection();
        
        String sql = "SELECT Username, Password FROM User_Login where Username = ? AND Password = ?";
        pst = con.prepareStatement(sql);
        pst.setString(1, teacher.getUsername());
        pst.setString(2, teacher.getPassword());
        rs = pst.executeQuery();
        return rs.next();
    }
    
    public boolean ResetVerification(Teacher teacher) throws SQLException{
        con = JavaDB.DBConnection();
        
        String sql = "SELECT Username, PasswordHint FROM User_Login WHERE Username = ? AND PasswordHint = ?";
        pst = con.prepareStatement(sql);
        pst.setString(1, teacher.getUsername());
        pst.setString(2, teacher.getPasswordHint());
        rs = pst.executeQuery();
        return rs.next();
    }
    
    public boolean ReplacePassword(Teacher teacher) throws SQLException{
        con = JavaDB.DBConnection();
        
        String sql = "UPDATE User_Login SET Password = ? WHERE Username = ?";
        pst = con.prepareStatement(sql);
        pst.setString(1, teacher.getPassword());
        pst.setString(2, teacher.getUsername());
        pst.execute();
        return true;
    }
}
