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

import com.assessment.models.Assignment;
import com.assessment.models.Score;
import com.assessment.models.Student;
import com.assessment.utils.JavaDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

public class CrudOperations {
    
    private static Connection con;
    private static ResultSet rs;
    private static PreparedStatement pst;
    
    /**
     * 
     * @param student
     * @return
     * @throws SQLException 
     */
    
    public boolean InsertStudent(Student student) throws SQLException{
        con = JavaDB.DBConnection();
        
        String sql = "INSERT INTO Student(classid, firstname, lastname, middlename, gender) VALUES(?,?,?,?,?)";
        pst = con.prepareStatement(sql);
        pst.setInt(1, student.getClassid());
        pst.setString(2, student.getFirstname());
        pst.setString(3, student.getLastname());
        pst.setString(4, student.getMiddlename());
        pst.setString(5, student.getGender());
        
        pst.execute();
        
        return true;
    }
    
    /**
     * 
     * @param student
     * @return
     * @throws SQLException 
     */
    
    public boolean UpdateStudent(Student student) throws SQLException{
        con = JavaDB.DBConnection();
        
        String sql = "UPDATE Student SET classid = ? firstname = ?, lastname = ?, middlename = ?, gender = ? WHERE studentid = ?";
        pst = con.prepareStatement(sql);
        pst.setInt(1, student.getClassid());
        pst.setString(2, student.getFirstname());
        pst.setString(3, student.getLastname());
        pst.setString(4, student.getMiddlename());
        pst.setString(5, student.getGender());
        pst.setInt(6, student.getStudentId());
        
        pst.execute();
        
        return true;
    }
    
    /**
     * 
     * @param student
     * @return
     * @throws SQLException 
     */
    
    public boolean DeleteStudent(Student student) throws SQLException{
        con = JavaDB.DBConnection();
        
        String sql = "DELETE FROM Student WHERE studentid = ?";
        pst = con.prepareStatement(sql);
        pst.setInt(1, student.getStudentId());
        
        pst.execute();
        
        return true;
    }
    
    /**
     * 
     * @param assign
     * @return
     * @throws SQLException 
     */
    
    public boolean AssignStudent(Assignment assign) throws SQLException{
        con = JavaDB.DBConnection();
        
        String sql = "INSERT INTO Assignment(studentid, classid, subjectid) VALUES(?,?,?)"; 
        pst = con.prepareStatement(sql);
        pst.setInt(1, assign.getStudentId());
        pst.setInt(2, assign.getClassId());
        pst.setInt(3, assign.getSubjectId());
        
        pst.execute();
        
        return true;
    }
    
    /**
     * 
     * @param assign
     * @return
     * @throws SQLException 
     */
    
    public boolean UpdateAssignment(Assignment assign) throws SQLException{
        con = JavaDB.DBConnection();
        
        String sql = "UPDATE Assignment SET classId = ?, subjectId = ? WHERE studentId = ?";
        pst = con.prepareStatement(sql);
        
        pst.setInt(1, assign.getClassId());
        pst.setInt(2, assign.getSubjectId());
        pst.setInt(3, assign.getStudentId());
        
        pst.execute();
        
        return true;
    }
    
    /**
     * 
     * @param assign
     * @return
     * @throws SQLException 
     */
    
    public boolean DeleteAssignment(Assignment assign) throws SQLException{
        con = JavaDB.DBConnection();
        String sql = "DELETE FROM Assignment WHERE StudentId = ?";
        pst = con.prepareStatement(sql);
        pst.setInt(1, assign.getStudentId());
        pst.execute();
        
        return true;
    }
    
    /**
     * 
     * @param score
     * @return
     * @throws SQLException 
     */
    
    public boolean InsertScore(Score score) throws SQLException{
      con = JavaDB.DBConnection();
      String sql = "INSERT INTO score(studentid, subjectid, classid, homework, classwork, "
              + "classtest, thirty, exam, seventy, total, grade, effort, position) "
              + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
      pst = con.prepareStatement(sql);
      pst.setInt(1, score.getStudentId());
      pst.setInt(2, score.getSubjectId());
      pst.setInt(3, score.getClassId());
      pst.setDouble(4, score.getHomework());
      pst.setDouble(5, score.getClasswork());
      pst.setDouble(6, score.getClasstest());
      pst.setDouble(7, score.getThirty());
      pst.setDouble(8, score.getExam());
      pst.setDouble(9, score.getSeventy());
      pst.setDouble(10, score.getTotal());
      pst.setString(11, score.getGrade());
      pst.setString(12, score.getEffort());
      pst.setString(13, score.getPosition());
      pst.execute();
      
      return true;
    }
    
    /**
     * 
     * @param score
     * @return
     * @throws SQLException 
     */
    
    public boolean UpdateScore(Score score) throws SQLException {
        con = JavaDB.DBConnection();

        String sql = "UPDATE score SET subjectid = ?, classid = ?, homework = ?, "
                + "classwork = ?, classtest = ?, thirty = ?, exam = ?, seventy = ?, total = ?, "
                + "grade = ?, effort = ?, position = ? WHERE studentid = ?";
        pst = con.prepareStatement(sql);

        
        pst.setInt(1, score.getSubjectId());
        pst.setInt(2, score.getClassId());
        pst.setDouble(3, score.getHomework());
        pst.setDouble(4, score.getClasswork());
        pst.setDouble(5, score.getClasstest());
        pst.setDouble(6, score.getThirty());
        pst.setDouble(7, score.getExam());
        pst.setDouble(8, score.getSeventy());
        pst.setDouble(9, score.getTotal());
        pst.setString(10, score.getGrade());
        pst.setString(11, score.getEffort());
        pst.setString(12, score.getPosition());
        pst.setInt(13, score.getStudentId());
        pst.execute();
        return true;
    }
    
    /**
     * 
     * @param score
     * @return
     * @throws SQLException 
     */
    
    public boolean DeleteScore(Score score) throws SQLException {
        con = JavaDB.DBConnection();

        String sql = "DELETE FROM score WHERE studentid = ?";
        pst = con.prepareStatement(sql);

        pst.setInt(1, score.getStudentId());

        pst.execute();
        return true;
    }
    
    /**
     * 
     * @param table
     * @param classname
     * @throws SQLException 
     */
    
    public void PopulateTable(JTable table, String classname) throws SQLException {
        table.setRowSorter(null);
        con = JavaDB.DBConnection();

        String ColumnNames[] = {"Name of Student", "30%", "70%", "Total", "Position", "Grade", "Remarks"};
        DefaultTableModel model = new DefaultTableModel();
        DefaultTableCellRenderer align = new DefaultTableCellRenderer();
        
        align.setHorizontalAlignment(SwingConstants.CENTER);
        model.setColumnIdentifiers(ColumnNames);
        table.setModel(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        ResizeTable(table);
        table.getColumn("30%").setCellRenderer(align);
        table.getColumn("70%").setCellRenderer(align);
        table.getColumn("Total").setCellRenderer(align);
        table.getColumn("Position").setCellRenderer(align);
        table.getColumn("Grade").setCellRenderer(align);
        table.getColumn("Remarks").setCellRenderer(align);

        String name;
        String thirty;
        String seventy;
        String total;
        String grade;
        String remark;
        String position;

        String sql = "SELECT Name, Thirty, Seventy, Total, Grade, Remark, Position FROM "
                + "(SELECT CONCAT(student.firstname,' ',student.middlename,' ',student.lastname) AS 'Name', "
                + "score.thirty AS 'Thirty', score.seventy AS 'Seventy', score.total AS 'Total', score.grade AS 'Grade', "
                + "score.effort AS 'Remark', @curRank := IF(@prevRank = score.total, @curRank, @incRank) AS 'Position', "
                + "@incRank := @incRank + 1, @prevRank := score.total FROM student, class, score, "
                + "(SELECT @curRank :=0, @prevRank := NULL, @incRank := 1) r WHERE student.studentid = score.studentid "
                + "AND score.classid = class.classid AND class.classname = ? ORDER BY score.total DESC)s";
        pst = con.prepareStatement(sql);
        pst.setString(1, classname);
        rs = pst.executeQuery();

        while (rs.next()) {
            name = rs.getString("Name");
            thirty = Double.toString(rs.getDouble("Thirty"));
            seventy = Double.toString(rs.getDouble("Seventy"));
            total = Double.toString(rs.getDouble("Total"));
            grade = rs.getString("Grade");
            remark = rs.getString("Remark");
            position = rs.getString("Position");
            model.addRow(new Object[]{name, thirty, seventy, total, position, grade, remark});
        }
    }

    /**
     * 
     * @param table
     * @param classname
     * @param fields
     * @throws SQLException 
     */
    
    public void PopulateScoreUpdateFields(JTable table, String classname, JTextField... fields) throws SQLException {
        con = JavaDB.DBConnection();
        int row = table.getSelectedRow();
        String fullname = (table.getModel().getValueAt(table.convertRowIndexToModel(row), 0)).toString();
        String firstname = "";
        String middlename = "";
        String lastname = "";
        String names[] = fullname.split("\\s+");
        if (names.length == 2) {
            firstname = names[0];
            lastname = names[1];
        } else if (names.length == 3) {
            firstname = names[0];
            middlename = names[1];
            lastname = names[2];
        } else if (names.length == 4) {
            firstname = names[0];
            middlename = names[1] + " " + names[2];
            lastname = names[3];
        } else if (names.length == 5) {
            firstname = names[0];
            middlename = names[1] + " " + names[2] + " " + names[3];
            lastname = names[4];
        }
        if (names.length > 0 && names.length <= 3) {
            if (!isFound(table, classname)) {
                firstname = firstname + " " + middlename;
                middlename = "";
            }
        }

        String sql = "SELECT score.studentid, score.classid, score.homework, score.classwork, "
                + "score.classtest, score.exam FROM class, score, student WHERE "
                + "student.studentid = score.studentid AND class.classid = score.classid "
                + "AND class.classname = ? AND student.firstname = ? AND student.lastname = ? "
                + "AND student.middlename = ?";
        
        pst = con.prepareStatement(sql);
        pst.setString(1, classname);
        pst.setString(2, firstname);
        pst.setString(3, lastname);
        pst.setString(4, middlename);
        rs = pst.executeQuery();
        
        while (rs.next()) {
            fields[0].setText("" + rs.getInt("score.studentid"));
            fields[1].setText("" + rs.getInt("score.classid"));
            fields[2].setText("" + rs.getDouble("score.classwork"));
            fields[3].setText("" + rs.getDouble("score.homework"));
            fields[4].setText("" + rs.getDouble("score.classtest"));
            fields[5].setText("" + rs.getDouble("score.exam"));
        }
    }
    
    /**
     * 
     * @param list
     * @param classname
     * @throws SQLException 
     */
    
    public void PopulateScoreList(JList list, String classname) throws SQLException{
        con = JavaDB.DBConnection();
        DefaultListModel dlm = new DefaultListModel();
        
        String sql = "SELECT student.firstname, student.lastname, student.middlename FROM student, "
                + "class, assignment WHERE assignment.classid = class.classid AND "
                + "assignment.studentid = student.studentid AND class.classname = ?";
                
        pst = con.prepareStatement(sql);
        pst.setString(1, classname);
        rs = pst.executeQuery();
        
        while (rs.next()){
            String firstname = rs.getString("student.firstname");
            String lastname = rs.getString("student.lastname");
            String middlename = rs.getString("student.middlename");
            String fullname = firstname +" "+middlename+" "+lastname;
            dlm.addElement(fullname);
        }
        list.setModel(dlm);
    }
    
    public void PopulateAssignList(JList list, String classname) throws SQLException{
        con = JavaDB.DBConnection();
        DefaultListModel dlm = new DefaultListModel();

        String sql = "SELECT firstname, lastname, middlename FROM student, class "
                + "WHERE class.classid = student.classid AND class.classname = ?";
                
        pst = con.prepareStatement(sql);
        pst.setString(1, classname);
        rs = pst.executeQuery();
        
        while (rs.next()){
            String firstname = rs.getString("student.firstname");
            String lastname = rs.getString("student.lastname");
            String middlename = rs.getString("student.middlename");
            String fullname = firstname +" "+middlename+" "+lastname;
            dlm.addElement(fullname);
        }
        list.setModel(dlm);
    }
    
    public void PopulateClassCombo(JComboBox... combo) throws SQLException{
        con = JavaDB.DBConnection();
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        String sql = "SELECT class.classname FROM class";
        pst = con.prepareStatement(sql);
        rs = pst.executeQuery();
        while (rs.next()){
            String classname = rs.getString("classname");
            model.addElement(classname);
        }
        for (int i = 0, n = combo.length; i < n; i++) {
            combo[i].setModel(model);
        }
    }
    
    /**
     * 
     * @param list
     * @param classname
     * @return
     * @throws SQLException 
     */
    
    public boolean isFound(JList list, String classname) throws SQLException{
        con = JavaDB.DBConnection();
        String firstname = "";
        String lastname = "";
        String middlename = "";
        String fullname = list.getSelectedValue().toString();
        String array[] = fullname.split("\\s+");
        if (array.length == 3){
            firstname = array[0];
            middlename = array[1];
            lastname = array[2];
        }
        String sql = "SELECT student.studentid FROM student, class WHERE "
                + "student.classid = class.classid AND class.classname = ? AND student.firstname = ? "
                + "AND student.lastname = ? AND student.middlename = ?";
        pst = con.prepareStatement(sql);
        pst.setString(1, classname);
        pst.setString(2, firstname);
        pst.setString(3, lastname);
        pst.setString(4, middlename);
        rs = pst.executeQuery();
        
        return rs.next();
    }
    
    /**
     * 
     * @param table
     * @param classname
     * @return
     * @throws SQLException 
     */
    
    public boolean isFound(JTable table, String classname) throws SQLException{
        con = JavaDB.DBConnection();
        String firstname = "";
        String lastname = "";
        String middlename = "";
        int row = table.getSelectedRow();
        String fullname = (table.getModel().getValueAt(table.convertRowIndexToModel(row), 0)).toString();
        String array[] = fullname.split("\\s+");
        if (array.length == 3){
            firstname = array[0];
            middlename = array[1];
            lastname = array[2];
        }
        String sql = "SELECT student.studentid FROM student, class WHERE "
                + "student.classid = class.classid AND class.classname = ? AND student.firstname = ? "
                + "AND student.lastname = ? AND student.middlename = ?";
        pst = con.prepareStatement(sql);
        pst.setString(1, classname);
        pst.setString(2, firstname);
        pst.setString(3, lastname);
        pst.setString(4, middlename);
        rs = pst.executeQuery();
        
        return rs.next();
    }
    
    /**
     * 
     * @param list
     * @param classname
     * @param fields 
     * @throws SQLException 
     */
    
    public void FillAssignFields(JList list, String classname, JTextField... fields) throws SQLException {
        con = JavaDB.DBConnection();

        String fullname = list.getSelectedValue().toString();
        String namearray[] = fullname.split("\\s+");
        String firstname = "";
        String lastname = "";
        String middlename = "";

        if (namearray.length == 2) {
            firstname = namearray[0];
            lastname = namearray[1];
        } else if (namearray.length == 3) {
            firstname = namearray[0];
            middlename = namearray[1];
            lastname = namearray[2];
        } else if (namearray.length == 4) {
            firstname = namearray[0];
            middlename = namearray[1] + " " + namearray[2];
            lastname = namearray[3];
        } else if (namearray.length == 5) {
            firstname = namearray[0];
            middlename = namearray[1] + " " + namearray[2] + " " + namearray[3];
            lastname = namearray[4];
        }
        if (namearray.length > 0 && namearray.length <= 3) {
            if (!isFound(list, classname)) {
                firstname = firstname + " " + middlename;
                middlename = "";
            }
        }

        String sql = "SELECT student.studentid, class.classid, subject.subjectid, subject.subjectname FROM "
                + "student, class, subject WHERE class.classname = ? AND student.firstname = ? "
                + "AND student.lastname = ? AND middlename = ?";
        pst = con.prepareStatement(sql);
        pst.setString(1, classname);
        pst.setString(2, firstname);
        pst.setString(3, lastname);
        pst.setString(4, middlename);

        rs = pst.executeQuery();
        while (rs.next()) {
            fields[0].setText("" + rs.getInt("student.studentid"));
            fields[1].setText("" + fullname);
            fields[2].setText("" + rs.getInt("class.classid"));
            fields[3].setText("" + classname);
            fields[4].setText("" + rs.getInt("subject.subjectid"));
            fields[5].setText("" + rs.getString("subject.subjectname"));
        }
    }
    
    /**
     * 
     * @param list 
     */
    
    public void RemoveSelectedItem(JList list){
        DefaultListModel model = (DefaultListModel)list.getModel();
        int index = list.getSelectedIndex();
        if (index != - 1) 
            model.remove(index);
    }
    
    /**
     * 
     * @param table
     * @param text 
     */
    
    public void FilterTable(JTable table, String text) {
        DefaultTableModel dfm = (DefaultTableModel)table.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dfm);
        table.setRowSorter(tr);
        
        if (text.length() == 0){
            tr.setRowFilter(null);
        }else {
            tr.setRowFilter(RowFilter.regexFilter(text)); 
        }
    }
    /**
     * 
     * @param list
     * @param classname
     * @param fields
     * @throws SQLException 
     */
    
    public void FillScoreFields(JList list, String classname, JTextField... fields) throws SQLException {
        con = JavaDB.DBConnection();
        String fullname = list.getSelectedValue().toString();
        String compound_names[] = fullname.split("\\s+");
        String firstname = "";
        String lastname = "";
        String middlename = "";

        if (compound_names.length == 2) {
            firstname = compound_names[0];
            lastname = compound_names[1];
        } else if (compound_names.length == 3) {
            firstname = compound_names[0];
            middlename = compound_names[1];
            lastname = compound_names[2];
        } else if (compound_names.length == 4) {
            firstname = compound_names[0];
            middlename = compound_names[1] + " " + compound_names[2];
            lastname = compound_names[3];
        } else if (compound_names.length == 5) {
            firstname = compound_names[0];
            middlename = compound_names[1] + " " + compound_names[2] + " " + compound_names[3];
            lastname = compound_names[4];
        }
        if (compound_names.length > 0 && compound_names.length <= 3){
           if (!isFound(list, classname)) {
            firstname = firstname + " " + middlename;
            middlename = "";
        } 
        }
        

        String sql = "SELECT class.classid, student.studentid, subject.subjectid FROM "
                + "class, student, subject WHERE class.classname = ? AND "
                + "student.firstname = ? AND student.lastname = ? AND "
                + "student.middlename = ?";
        pst = con.prepareStatement(sql);
        pst.setString(1, classname);
        pst.setString(2, firstname);
        pst.setString(3, lastname);
        pst.setString(4, middlename);

        rs = pst.executeQuery();
        while (rs.next()) {
            fields[0].setText("" + rs.getInt("class.classid"));
            fields[1].setText("" + rs.getInt("student.studentid"));
            fields[2].setText("" + rs.getInt("subject.subjectid"));
        }
    }
    
    /**
     * 
     * @param combo
     * @return
     * @throws SQLException 
     */
    
    public int getClassId(JComboBox combo) throws SQLException{
        con = JavaDB.DBConnection();
        
        String sql = "SELECT class.classid FROM class where class.classname = ?";
        pst = con.prepareStatement(sql);
        pst.setString(1, combo.getSelectedItem().toString());
        rs = pst.executeQuery();
        
        if (rs.next()){
            return rs.getInt("class.classid");
        }
        return 0;
    }
    
    public void ResizeTable(JTable table){
        TableColumn column;
        for (int i = 0, n = table.getColumnCount(); i < n; i++){
            column = table.getColumnModel().getColumn(i);
            if (i == 0){
                column.setMaxWidth(200);
            } else {
                column.setMaxWidth(100);
            }
        }
    }
    
}
