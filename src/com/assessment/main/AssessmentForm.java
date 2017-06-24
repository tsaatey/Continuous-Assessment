
package com.assessment.main;

import com.assessment.utils.JavaDB;
import com.assessment.bussinessmodel.CrudOperations;
import com.assessment.models.Assignment;
import com.assessment.models.Score;
import com.assessment.models.Student;
import com.assessment.utils.ProgressBar;
import java.awt.event.*;
import javax.swing.JOptionPane;
import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

public class AssessmentForm extends javax.swing.JFrame {

    String grade = "", effort = "";
    double overallTotal,thirty,seventy,totalScore,classWork1;
    double homeWork1, classTest1, examScore;
    JTextField studentIDField = new JTextField();
    JTextField classIDField = new JTextField();
    JTextField subjectIDField = new JTextField();
    boolean isOpened = false;
    boolean isDetailed = false;
    
    
    static Connection con = null;
    static PreparedStatement pst = null;
    static ResultSet rs = null;
    private ProgressBar progressBar;
    
    public AssessmentForm() {
        initComponents();
        this.setIconImage(new ImageIcon(getClass().getResource("logo.png")).getImage());
        setLocationRelativeTo(null);
        ExcelDialog.setLocationRelativeTo(null);
        ReportDialog.setLocationRelativeTo(null);
        StudentDialog.setLocationRelativeTo(null);
        ScoreBoard.setLocationRelativeTo(null);
        AssignmentDialog.setLocationRelativeTo(null);
        subjectTeacherPanel.setVisible(false);
        classTeacherPanel.setVisible(false);
        dateLabel.setText(null);
        timeLabel.setText(null);

        DateTime();
        con = JavaDB.DBConnection();
        
        updateClassCombo();
        
        
    }
    //Method to display date and time
    private void DateTime() {
        Thread th = new Thread() {
            @Override
            public void run() {
                for (;;) {
                    Calendar cal = new GregorianCalendar();
                    int month = cal.get(Calendar.MONTH);
                    int year = cal.get(Calendar.YEAR);
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    dateLabel.setText(+day + "-" + (month + 1) + "-" + year);

                    int second = cal.get(Calendar.SECOND);
                    int minutes = cal.get(Calendar.MINUTE);
                    int hour = cal.get(Calendar.HOUR);
                    int AM_PM = cal.get(Calendar.AM_PM);
                    String day_night;
                    if (AM_PM == 1) {
                        day_night = "PM";
                    } else {
                        day_night = "AM";
                    }
                    timeLabel.setText(+hour + " : " + minutes + " : " + second + " " + day_night);
                    try {
                        sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(AssessmentForm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        th.start();
    }
    
    private void idsForScore(){
        CrudOperations op = new CrudOperations();
        String classname = (String)scoreCombo.getSelectedItem().toString();
        try{
            op.FillScoreFields(scoreList, classname, classIDField, studentIDField, subjectIDField);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    //Method to save data to database
    private void Save(Score score){
        CrudOperations op = new CrudOperations();
        //getting user inputs
       //Convert all necessary user inputs to their appropriate datatypes
        classWork1 = Double.parseDouble(classWorkField.getText());
        homeWork1 = Double.parseDouble(homeWorkField.getText());
        classTest1 = Double.parseDouble(classTestField.getText());
        examScore = Double.parseDouble(examScoreField.getText());
        
        //Computer overall work done during class tutions
        totalScore = classWork1 + homeWork1 + classTest1;
        
        //Computing the 70% of the exam score
        seventy = Math.round(examScore * (0.7));
        
        //Computing the total work done for the term
        overallTotal = totalScore + seventy;
        
        //Grading scheme for senior high schools
         if (86 <= overallTotal && overallTotal <= 100) {
            grade = "A1";
        } else if (81 <= overallTotal && overallTotal <= 85) {
            grade = "B2";
        } else if (76 <= overallTotal && overallTotal <= 80) {
            grade = "B3";
        } else if (67 <= overallTotal && overallTotal <= 75) {
            grade = "C4";
        } else if (59 <= overallTotal && overallTotal <= 66) {
            grade = "C5";
        } else if (51 <= overallTotal && overallTotal <= 58) {
            grade = "C6";
        } else if (46 <= overallTotal && overallTotal <= 50) {
            grade = "D7";
        } else if (40 <= overallTotal && overallTotal <= 45) {
            grade = "E8";
        } else if (0 <= overallTotal && overallTotal <= 39) {
            grade = "F9";
        }

        //The remark layout
        if (86 <= overallTotal && overallTotal <= 100) {
            effort = "Excellent";
        } else if (81 <= overallTotal && overallTotal <= 85) {
            effort = "Very Good";
        } else if (76 <= overallTotal && overallTotal <= 80) {
            effort = "Good";
        } else if (67 <= overallTotal && overallTotal <= 75) {
            effort = "Credit";
        } else if (59 <= overallTotal && overallTotal <= 66) {
            effort = "Credit";
        } else if (51 <= overallTotal && overallTotal <= 58) {
            effort = "Credit";
        } else if (46 <= overallTotal && overallTotal <= 50) {
            effort = "Pass";
        } else if (40 <= overallTotal && overallTotal <= 45) {
            effort = "Weak Pass";
        } else if (0 <= overallTotal && overallTotal <= 39) {
            effort = "Fail";
        }
        //Saving to database
        
        try{
            score.setStudentId(Integer.parseInt(studentIDField.getText()));
            score.setSubjectId(Integer.parseInt(subjectIDField.getText()));
            score.setClassId(Integer.parseInt(classIDField.getText()));
            score.setHomework(Double.parseDouble(homeWorkField.getText()));
            score.setClasswork(Double.parseDouble(classWorkField.getText()));
            score.setClasstest(Double.parseDouble(classTestField.getText()));
            score.setThirty(totalScore);
            score.setExam(examScore);
            score.setSeventy(seventy);
            score.setTotal(overallTotal);
            score.setGrade(grade);
            score.setEffort(effort);
            score.setPosition("");
            
            if (op.InsertScore(score)){
                JOptionPane.showMessageDialog(null, "Score Added!");
                op.RemoveSelectedItem(scoreList);
                ClearScoreFields();
            }   
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "A Score exits for this student already. You can only update.", "Duplicate Scoring", JOptionPane.ERROR_MESSAGE);
            op.RemoveSelectedItem(scoreList);
        }
    }
    //Method to clear fields
    private void Clear(){
        txt_studentId.setText(null);
        txt_classId.setText(null);
        classwork1Field.setText(null);
        homework1Field.setText(null);
        classtest1Field.setText(null);
        examscoreField.setText(null);
    }
    
    private void ClearScoreFields(){
     
        classWorkField.setText(""+0);
       
        homeWorkField.setText(""+0);
       
        classTestField.setText(""+0);
        
        examScoreField.setText(""+0);
    }
    
    //Method to update records
    private void Update(Score score){
        CrudOperations op = new CrudOperations();
        
        classWork1 = Double.parseDouble(classwork1Field.getText());
        homeWork1 = Double.parseDouble(homework1Field.getText());
        classTest1 = Double.parseDouble(classtest1Field.getText());
        examScore = Double.parseDouble(examscoreField.getText());
        
        //Peforming the calculations
        //Computer overall work done during class tutions
        thirty = classWork1 + homeWork1 + classTest1;
        
        //Computing the 70% of the exam score
        seventy = Math.round(examScore * (0.7));
        
        //Computing the total work done for the term
        overallTotal = thirty + seventy;
        
        //Grading scheme for senior high schools
        if (86 <= overallTotal && overallTotal <= 100) {
            grade = "A1";
        } else if (81 <= overallTotal && overallTotal <= 85) {
            grade = "B2";
        } else if (76 <= overallTotal && overallTotal <= 80) {
            grade = "B3";
        } else if (67 <= overallTotal && overallTotal <= 75) {
            grade = "C4";
        } else if (59 <= overallTotal && overallTotal <= 66) {
            grade = "C5";
        } else if (51 <= overallTotal && overallTotal <= 58) {
            grade = "C6";
        } else if (46 <= overallTotal && overallTotal <= 50) {
            grade = "D7";
        } else if (40 <= overallTotal && overallTotal <= 45) {
            grade = "E8";
        } else if (0 <= overallTotal && overallTotal <= 39) {
            grade = "F9";
        }

        //The effort layout
        if (86 <= overallTotal && overallTotal <= 100) {
            effort = "Excellent";
        } else if (81 <= overallTotal && overallTotal <= 85) {
            effort = "Very Good";
        } else if (76 <= overallTotal && overallTotal <= 80) {
            effort = "Good";
        } else if (67 <= overallTotal && overallTotal <= 75) {
            effort = "Credit";
        } else if (59 <= overallTotal && overallTotal <= 66) {
            effort = "Credit";
        } else if (51 <= overallTotal && overallTotal <= 58) {
            effort = "Credit";
        } else if (46 <= overallTotal && overallTotal <= 50) {
            effort = "Pass";
        } else if (40 <= overallTotal && overallTotal <= 45) {
            effort = "Weak Pass";
        } else if (0 <= overallTotal && overallTotal <= 39) {
            effort = "Fail";
        }
        try {
            score.setSubjectId(1);
            score.setClassId(Integer.parseInt(txt_classId.getText()));
            score.setHomework(Double.parseDouble(homework1Field.getText()));
            score.setClasswork(Double.parseDouble(classwork1Field.getText()));
            score.setClasstest(Double.parseDouble(classtest1Field.getText()));
            score.setThirty(thirty);
            score.setExam(examScore);
            score.setSeventy(seventy);
            score.setTotal(overallTotal);
            score.setGrade(grade);
            score.setEffort(effort);
            score.setPosition("");
            score.setStudentId(Integer.parseInt(txt_studentId.getText()));
            
            if (op.UpdateScore(score)) {
                JOptionPane.showMessageDialog(null, "Score has been updated!");
                Table();
                Clear();
                txt_filter.setText(null);
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    //Method to delete a record
    private void Delete() {
        int choice = JOptionPane.showConfirmDialog(null, "Are you sure want to delete?");
        if (choice == JOptionPane.YES_OPTION) {
            try {

                Clear();
                Table();
                JOptionPane.showMessageDialog(null, "Delete Successful!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Delete Cancelled!");
        }
    }
    //Method to populate table
    private void Table() {
        CrudOperations op = new CrudOperations();
        try {
            op.PopulateTable(jTable1, searchCombo.getSelectedItem().toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
        
    private void Assign(Assignment assign) {
        CrudOperations op = new CrudOperations();
        try {
            if (studentIdField.getText().trim().length() > 0 && classIdField.getText().trim().length() > 0 && subjectIdField.getText().trim().length() > 0) {
                assign.setStudentId(Integer.parseInt(studentIdField.getText()));
                assign.setClassId(Integer.parseInt(classIdField.getText()));
                assign.setSubjectId(Integer.parseInt(subjectIdField.getText()));

                if (op.AssignStudent(assign)) {
                    JOptionPane.showMessageDialog(null, fullNameField.getText() + " successfully assigned!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    op.RemoveSelectedItem(assignStudentList);
                    ClearAssignFields();
                }
            }else{
                JOptionPane.showMessageDialog(null, "Field(s) is/are empty", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, fullNameField.getText() + " has already been assigned!", "Duplicate Assignment", JOptionPane.ERROR_MESSAGE);
            op.RemoveSelectedItem(assignStudentList);
        }
    }
    
    private void ScoreList(){
        String classname = (String)scoreCombo.getSelectedItem().toString();
        CrudOperations op = new CrudOperations();
        try{
            op.PopulateScoreList(scoreList, classname);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void PopulateAsignList(){
        String classname = (String)assignCombo.getSelectedItem().toString();
        CrudOperations op = new CrudOperations();
        try{
            op.PopulateAssignList(assignStudentList, classname);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void SaveStudent(Student student) {
        CrudOperations op = new CrudOperations();
        int classid = 0;
        try {
            if (op.getClassId(cmb_student) > 0) {
                classid = op.getClassId(cmb_student);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AssessmentForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (text_firstname.getText().trim().length() > 0 && txt_lastname.getText().trim().length() > 0) {
            student.setClassid(classid);
            student.setFirstname(text_firstname.getText());
            student.setLastname(txt_lastname.getText());
            student.setMiddlename(txt_middlename.getText());
            student.setGender(genderCombo.getSelectedItem().toString());
            try {
                if (op.InsertStudent(student)) {
                    JOptionPane.showMessageDialog(null, "Details saved", "Success", JOptionPane.INFORMATION_MESSAGE);
                    ClearStudentFields();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Databse Error", JOptionPane.ERROR_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Field(s) is/are empty!", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }
    
    //Method to update class combo box on the excel form
    private void updateClassCombo(){
        CrudOperations op = new CrudOperations();
        try {
           op.PopulateClassCombo(searchCombo, scoreCombo,assignCombo,cmb_student, reportCombo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    private void ClearStudentFields(){
        text_firstname.setText(null);
        txt_lastname.setText(null);
        txt_middlename.setText(null);
        cmb_student.resetKeyboardActions();
    }
    
    private void FillAssignFields(){
        CrudOperations op = new CrudOperations();
        try{
            op.FillAssignFields(assignStudentList, assignCombo.getSelectedItem().toString(), studentIdField, fullNameField, classIdField, classNameField, subjectIdField, subjectNameField);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void ClearAssignFields() {
        studentIdField.setText("");
        fullNameField.setText("");
        classIdField.setText(""); 
        classNameField.setText("");
        subjectIdField.setText(""); 
        subjectNameField.setText("");
    }
    
    //Method to populate text fields
    private void populateTextFields(){
        CrudOperations op = new CrudOperations();
        try{
            op.PopulateScoreUpdateFields(jTable1, searchCombo.getSelectedItem().toString(), txt_studentId, txt_classId, classwork1Field, homework1Field, classtest1Field, examscoreField);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE);
        }  
    }
    
    private void Filter(){
        CrudOperations op = new CrudOperations();
        String query = txt_filter.getText();
        op.FilterTable(jTable1, query);
    }
    //Methods to generate spreadsheet report
//    private void subjectTeacherSpreadsheet() {
//        //Specify the path of the file
//        String filename = "Single_Subject.csv";
//
//        try {
//            FileWriter writer = new FileWriter(filename);
//            writer.append("Name of Student");
//            writer.append(',');
//            writer.append("30%");
//            writer.append(',');
//            writer.append("70%");
//            writer.append(',');
//            writer.append("Total (30% + 70%)");
//            writer.append(',');
//            writer.append("Grade");
//            writer.append(',');
//            writer.append("Remarks");
//            writer.append('\n');
//            
//            String sql = "select * from Assessment where staffid = ? and class = ? and term = ? and subject = ? and year = ? order by OverallTotal desc";
//            pst = con.prepareStatement(sql);
//            pst.setInt(1, Integer.parseInt((String)staffidCombo.getSelectedItem()));
//            pst.setInt(2, Integer.parseInt((String)classCombo.getSelectedItem()));
//            pst.setInt(3, Integer.parseInt((String)termCombo.getSelectedItem()));
//            pst.setString(4, (String)subjectCombo.getSelectedItem());
//            pst.setInt(5, Integer.parseInt((String) yearCombo1.getSelectedItem()));
//            rs = pst.executeQuery();
//            
//            while (rs.next()) {
//                writer.append(rs.getString(3));
//                writer.append(',');
//                writer.append(Double.toString(rs.getDouble(26)));
//                writer.append(',');
//                writer.append(Double.toString(rs.getDouble(28)));
//                writer.append(',');
//                writer.append(Double.toString(rs.getDouble(29)));
//                writer.append(',');
//                writer.append(rs.getString(30));
//                writer.append(',');
//                writer.append(rs.getString(31));
//                writer.append('\n');
//            }
//            try {
//                Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " + "Single_Subject.csv");
//            } catch (Exception e) {
//                JOptionPane.showMessageDialog(null, e);
//            }
//            writer.flush();
//            writer.close();
//
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e);
//        } finally {
//            try {
//                rs.close();
//                pst.close();
//                //con.close();
//            } catch (Exception e) {
//                JOptionPane.showMessageDialog(null, e);
//
//            }
//        }
//        ClearSheet();
//    }
//    private void classTeacherSpreadsheet(){
//        String filename = "Master_Sheet.csv";
//
//        try {
//            FileWriter writer = new FileWriter(filename);
//            writer.append("Name of Student");
//            writer.append(',');
//            writer.append("30%");
//            writer.append(',');
//            writer.append("70%");
//            writer.append(',');
//            writer.append("Total(30% + 70%)");
//            writer.append(',');
//            writer.append("Grade");
//            writer.append(',');
//            writer.append("Remarks");
//            writer.append('\n');
//            
//            String sql = "select * from Assessment where class = ? and term = ? and year = ? order by OverallTotal desc";
//            pst = con.prepareStatement(sql);
//            pst.setInt(1, Integer.parseInt((String)class2Combo.getSelectedItem()));
//            pst.setInt(2, Integer.parseInt((String)term2Combo.getSelectedItem()));
//            pst.setInt(3, Integer.parseInt((String) yearCombo2.getSelectedItem()));
//            rs = pst.executeQuery();
//            
//            while (rs.next()) {
//                writer.append(rs.getString(3));
//                writer.append(',');
//                writer.append(Double.toString(rs.getDouble(26)));
//                writer.append(',');
//                writer.append(Double.toString(rs.getDouble(28)));
//                writer.append(',');
//                writer.append(Double.toString(rs.getDouble(29)));
//                writer.append(',');
//                writer.append(rs.getString(30));
//                writer.append(',');
//                writer.append(rs.getString(31));
//                writer.append('\n');
//            }
//            try {
//                Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " + "Master_Sheet.csv");
//            } catch (Exception e) {
//                JOptionPane.showMessageDialog(null, e);
//            }
//            writer.flush();
//            writer.close();
//
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e);
//        } finally {
//            try {
//                rs.close();
//                pst.close();
//                //con.close();
//            } catch (Exception e) {
//                JOptionPane.showMessageDialog(null, e);
//
//            }
//        }
//        ClearSheet();
//    
//    }


    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ExcelDialog = new javax.swing.JDialog();
        jPanel5 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        subjectTeacherRadio = new javax.swing.JRadioButton();
        classTeacherRadio = new javax.swing.JRadioButton();
        subjectTeacherPanel = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        yearCombo1 = new javax.swing.JComboBox();
        jLabel44 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        continueLabel1 = new javax.swing.JLabel();
        subjectCombo = new javax.swing.JComboBox();
        staffidCombo = new javax.swing.JComboBox();
        classCombo = new javax.swing.JComboBox();
        termCombo = new javax.swing.JComboBox();
        classTeacherPanel = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        yearCombo2 = new javax.swing.JComboBox();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        continueLabel2 = new javax.swing.JLabel();
        class2Combo = new javax.swing.JComboBox();
        term2Combo = new javax.swing.JComboBox();
        buttonGroup1 = new javax.swing.ButtonGroup();
        ReportDialog = new javax.swing.JDialog();
        jPanel6 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        reportPanel = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        reportCombo = new javax.swing.JComboBox();
        jLabel24 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        report_progress = new javax.swing.JProgressBar();
        report_progress_label = new javax.swing.JLabel();
        StudentDialog = new javax.swing.JDialog();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        text_firstname = new javax.swing.JTextField();
        txt_lastname = new javax.swing.JTextField();
        txt_middlename = new javax.swing.JTextField();
        genderCombo = new javax.swing.JComboBox();
        saveStudentLabel = new javax.swing.JLabel();
        updateStudentLabel = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cmb_student = new javax.swing.JComboBox();
        AssignmentDialog = new javax.swing.JDialog();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        assignStudentList = new javax.swing.JList();
        jLabel17 = new javax.swing.JLabel();
        assignStudentCombo = new javax.swing.JPanel();
        assignCombo = new javax.swing.JComboBox();
        jLabel63 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel66 = new javax.swing.JLabel();
        classNameField = new javax.swing.JTextField();
        jLabel69 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        fullNameField = new javax.swing.JTextField();
        jLabel64 = new javax.swing.JLabel();
        subjectNameField = new javax.swing.JTextField();
        subjectIdField = new javax.swing.JTextField();
        studentIdField = new javax.swing.JTextField();
        classIdField = new javax.swing.JTextField();
        jLabel68 = new javax.swing.JLabel();
        assignStudentLabel = new javax.swing.JLabel();
        ScoreBoard = new javax.swing.JDialog();
        jPanel12 = new javax.swing.JPanel();
        jLabel70 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        scoreCombo = new javax.swing.JComboBox();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        scoreList = new javax.swing.JList();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        classWorkField = new javax.swing.JTextField();
        homeWorkField = new javax.swing.JTextField();
        classTestField = new javax.swing.JTextField();
        examScoreField = new javax.swing.JTextField();
        addScoreLabel = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        homework1Field = new javax.swing.JTextField();
        updateLabel = new javax.swing.JLabel();
        examscoreField = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        classwork1Field = new javax.swing.JTextField();
        clearLabel = new javax.swing.JLabel();
        deleteLabel = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        classtest1Field = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        txt_studentId = new javax.swing.JTextField();
        txt_classId = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        searchCombo = new javax.swing.JComboBox();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        dateLabel = new javax.swing.JLabel();
        timeLabel = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        txt_filter = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem7 = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        jMenuItem8 = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItem5 = new javax.swing.JMenuItem();

        ExcelDialog.setTitle("Spreadsheet Report");
        ExcelDialog.setMinimumSize(new java.awt.Dimension(500, 400));

        jPanel5.setBackground(new java.awt.Color(204, 204, 255));

        jLabel37.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel37.setText("To proceed answer the question below");

        jLabel38.setText("Who are you?");

        buttonGroup1.add(subjectTeacherRadio);
        subjectTeacherRadio.setText("Subject Teacher");
        subjectTeacherRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subjectTeacherRadioActionPerformed(evt);
            }
        });

        buttonGroup1.add(classTeacherRadio);
        classTeacherRadio.setText("Class Teacher");
        classTeacherRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                classTeacherRadioActionPerformed(evt);
            }
        });

        subjectTeacherPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel42.setText("Class/Form");

        jLabel43.setText("Term");

        yearCombo1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2038", "2039", "2040" }));

        jLabel44.setText("Year");

        jLabel39.setText("Please select appropriate credentials below and click Continue");

        jLabel40.setText("Staff ID");

        jLabel41.setText("Subject");

        continueLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        continueLabel1.setForeground(new java.awt.Color(0, 153, 0));
        continueLabel1.setText("Continue");
        continueLabel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        continueLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                continueLabel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout subjectTeacherPanelLayout = new javax.swing.GroupLayout(subjectTeacherPanel);
        subjectTeacherPanel.setLayout(subjectTeacherPanelLayout);
        subjectTeacherPanelLayout.setHorizontalGroup(
            subjectTeacherPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, subjectTeacherPanelLayout.createSequentialGroup()
                .addGroup(subjectTeacherPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(subjectTeacherPanelLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel39))
                    .addGroup(subjectTeacherPanelLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(subjectTeacherPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel40)
                            .addComponent(jLabel41))
                        .addGap(28, 28, 28)
                        .addGroup(subjectTeacherPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(subjectCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, subjectTeacherPanelLayout.createSequentialGroup()
                                .addComponent(staffidCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel42)))
                        .addGroup(subjectTeacherPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(subjectTeacherPanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                                .addComponent(jLabel44)
                                .addGap(31, 31, 31))
                            .addGroup(subjectTeacherPanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(classCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(subjectTeacherPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(yearCombo1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(subjectTeacherPanelLayout.createSequentialGroup()
                                .addComponent(jLabel43)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(termCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGap(32, 32, 32))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, subjectTeacherPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(continueLabel1)
                .addGap(151, 151, 151))
        );
        subjectTeacherPanelLayout.setVerticalGroup(
            subjectTeacherPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subjectTeacherPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(subjectTeacherPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(jLabel42)
                    .addComponent(jLabel43)
                    .addComponent(staffidCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(classCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(termCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(subjectTeacherPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(jLabel44)
                    .addComponent(yearCombo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(subjectCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(continueLabel1)
                .addContainerGap())
        );

        classTeacherPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel46.setText("Class/Form");

        jLabel47.setText("Term");

        yearCombo2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2038", "2039", "2040" }));

        jLabel48.setText("Year");

        jLabel49.setText("Please select credentials below and click Continue");

        continueLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        continueLabel2.setForeground(new java.awt.Color(0, 153, 0));
        continueLabel2.setText("Continue");
        continueLabel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        continueLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                continueLabel2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout classTeacherPanelLayout = new javax.swing.GroupLayout(classTeacherPanel);
        classTeacherPanel.setLayout(classTeacherPanelLayout);
        classTeacherPanelLayout.setHorizontalGroup(
            classTeacherPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(classTeacherPanelLayout.createSequentialGroup()
                .addGroup(classTeacherPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(classTeacherPanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(classTeacherPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, classTeacherPanelLayout.createSequentialGroup()
                                .addGroup(classTeacherPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel46)
                                    .addComponent(jLabel48))
                                .addGap(26, 26, 26)
                                .addGroup(classTeacherPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(yearCombo2, 0, 77, Short.MAX_VALUE)
                                    .addComponent(class2Combo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(17, 17, 17))
                            .addComponent(continueLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGroup(classTeacherPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(classTeacherPanelLayout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(term2Combo, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, classTeacherPanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel47)
                                .addGap(22, 22, 22))))
                    .addGroup(classTeacherPanelLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel49)))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        classTeacherPanelLayout.setVerticalGroup(
            classTeacherPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(classTeacherPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel49)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(classTeacherPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(class2Combo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel47))
                .addGap(18, 18, 18)
                .addGroup(classTeacherPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(yearCombo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(term2Combo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(continueLabel2)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addComponent(subjectTeacherRadio)
                        .addGap(45, 45, 45)
                        .addComponent(classTeacherRadio))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(211, 211, 211)
                        .addComponent(jLabel38))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(classTeacherPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(74, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel37)
                        .addGap(125, 125, 125))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(subjectTeacherPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel38)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(subjectTeacherRadio)
                    .addComponent(classTeacherRadio))
                .addGap(13, 13, 13)
                .addComponent(subjectTeacherPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(classTeacherPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout ExcelDialogLayout = new javax.swing.GroupLayout(ExcelDialog.getContentPane());
        ExcelDialog.getContentPane().setLayout(ExcelDialogLayout);
        ExcelDialogLayout.setHorizontalGroup(
            ExcelDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        ExcelDialogLayout.setVerticalGroup(
            ExcelDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        ReportDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        ReportDialog.setMinimumSize(new java.awt.Dimension(492, 374));
        ReportDialog.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                ReportDialogWindowClosing(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(204, 204, 255));

        jLabel45.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 0, 0));
        jLabel45.setText("For Class Teacher Use Only");

        reportPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel58.setText("Submit");
        jLabel58.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel58.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel58MouseClicked(evt);
            }
        });

        jLabel53.setText("Please select a class below and submit");

        jLabel52.setFont(new java.awt.Font("Rockwell", 1, 14)); // NOI18N
        jLabel52.setText("Welcome to Terminal Generator");

        jLabel59.setText("Discard");
        jLabel59.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel24.setText("Class name:");

        buttonGroup2.add(jRadioButton1);
        jRadioButton1.setText("Simple Report");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        buttonGroup2.add(jRadioButton2);
        jRadioButton2.setText("Detailed Report");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout reportPanelLayout = new javax.swing.GroupLayout(reportPanel);
        reportPanel.setLayout(reportPanelLayout);
        reportPanelLayout.setHorizontalGroup(
            reportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reportPanelLayout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addGroup(reportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reportPanelLayout.createSequentialGroup()
                        .addComponent(jLabel52)
                        .addGap(31, 31, 31))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reportPanelLayout.createSequentialGroup()
                        .addComponent(jLabel53)
                        .addGap(51, 51, 51))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reportPanelLayout.createSequentialGroup()
                        .addGroup(reportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioButton1)
                            .addComponent(jLabel24))
                        .addGap(157, 157, 157))))
            .addGroup(reportPanelLayout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(jLabel58)
                .addGap(33, 33, 33)
                .addGroup(reportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel59)
                    .addComponent(reportCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        reportPanelLayout.setVerticalGroup(
            reportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reportPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel52)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel53)
                .addGap(18, 18, 18)
                .addGroup(reportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(reportCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addGap(18, 18, 18)
                .addGroup(reportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2))
                .addGap(18, 18, 18)
                .addGroup(reportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel58)
                    .addComponent(jLabel59))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        report_progress_label.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        report_progress_label.setForeground(new java.awt.Color(0, 204, 0));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addComponent(jLabel45))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(report_progress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(reportPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(127, 127, 127)
                        .addComponent(report_progress_label, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(104, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel45)
                .addGap(26, 26, 26)
                .addComponent(reportPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(report_progress_label, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(report_progress, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout ReportDialogLayout = new javax.swing.GroupLayout(ReportDialog.getContentPane());
        ReportDialog.getContentPane().setLayout(ReportDialogLayout);
        ReportDialogLayout.setHorizontalGroup(
            ReportDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        ReportDialogLayout.setVerticalGroup(
            ReportDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        StudentDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        StudentDialog.setTitle("Student Details");
        StudentDialog.setMinimumSize(new java.awt.Dimension(420, 420));
        StudentDialog.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                StudentDialogWindowClosing(evt);
            }
        });

        jPanel7.setBackground(new java.awt.Color(204, 204, 255));

        jLabel1.setText("Enter Student Details Below");

        jLabel4.setText("First Name:");

        jLabel6.setText("Last Name:");

        jLabel7.setText("Middle Name:");

        jLabel8.setText("Gender:");

        genderCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Male", "Female", "Other" }));

        saveStudentLabel.setBackground(new java.awt.Color(255, 255, 255));
        saveStudentLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        saveStudentLabel.setText("Save");
        saveStudentLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        saveStudentLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveStudentLabelMouseClicked(evt);
            }
        });

        updateStudentLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        updateStudentLabel.setText("Update");
        updateStudentLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel9.setText("CLass:");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addGap(43, 43, 43)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel1)
                                .addComponent(text_firstname)
                                .addComponent(txt_lastname)
                                .addComponent(txt_middlename, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE))
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(cmb_student, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(genderCombo, javax.swing.GroupLayout.Alignment.LEADING, 0, 82, Short.MAX_VALUE))))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(187, 187, 187)
                        .addComponent(saveStudentLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateStudentLabel)))
                .addContainerGap(132, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(51, 51, 51)
                                .addComponent(jLabel4))
                            .addComponent(text_firstname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6))
                    .addComponent(txt_lastname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txt_middlename, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(genderCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cmb_student, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveStudentLabel)
                    .addComponent(updateStudentLabel))
                .addContainerGap(66, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout StudentDialogLayout = new javax.swing.GroupLayout(StudentDialog.getContentPane());
        StudentDialog.getContentPane().setLayout(StudentDialogLayout);
        StudentDialogLayout.setHorizontalGroup(
            StudentDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        StudentDialogLayout.setVerticalGroup(
            StudentDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        AssignmentDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        AssignmentDialog.setMinimumSize(new java.awt.Dimension(500, 550));
        AssignmentDialog.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                AssignmentDialogWindowClosing(evt);
            }
        });

        jPanel8.setBackground(new java.awt.Color(204, 204, 255));

        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        assignStudentList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                assignStudentListMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(assignStudentList);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );

        jLabel17.setText("Names found in this class. Click to select");

        assignStudentCombo.setBorder(javax.swing.BorderFactory.createTitledBorder("Select A Class"));

        assignCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignComboActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout assignStudentComboLayout = new javax.swing.GroupLayout(assignStudentCombo);
        assignStudentCombo.setLayout(assignStudentComboLayout);
        assignStudentComboLayout.setHorizontalGroup(
            assignStudentComboLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(assignStudentComboLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(assignCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        assignStudentComboLayout.setVerticalGroup(
            assignStudentComboLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, assignStudentComboLayout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addComponent(assignCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        jLabel63.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel63.setText("Assign A Student");

        jPanel11.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel66.setText("Class ID");
        jPanel11.add(jLabel66, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, 20));
        jPanel11.add(classNameField, new org.netbeans.lib.awtextra.AbsoluteConstraints(252, 49, 186, -1));

        jLabel69.setText("Subject Name");
        jPanel11.add(jLabel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 90, -1, 20));

        jLabel67.setText("Class Name");
        jPanel11.add(jLabel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, -1, -1));

        jLabel65.setText("Full Name");
        jPanel11.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, -1, 20));
        jPanel11.add(fullNameField, new org.netbeans.lib.awtextra.AbsoluteConstraints(252, 11, 186, -1));

        jLabel64.setText("Student ID");
        jPanel11.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 20));
        jPanel11.add(subjectNameField, new org.netbeans.lib.awtextra.AbsoluteConstraints(252, 92, 186, -1));
        jPanel11.add(subjectIdField, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, 31, -1));
        jPanel11.add(studentIdField, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 32, -1));
        jPanel11.add(classIdField, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 32, -1));

        jLabel68.setText("Subject ID");
        jPanel11.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, 20));

        assignStudentLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        assignStudentLabel.setText("Assign");
        assignStudentLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        assignStudentLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                assignStudentLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(assignStudentCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(154, 154, 154)
                                .addComponent(jLabel63)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel17)
                        .addGap(79, 79, 79))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(assignStudentLabel)
                .addGap(204, 204, 204))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel63)
                .addGap(30, 30, 30)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addComponent(assignStudentCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(72, 72, 72)))
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(assignStudentLabel)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout AssignmentDialogLayout = new javax.swing.GroupLayout(AssignmentDialog.getContentPane());
        AssignmentDialog.getContentPane().setLayout(AssignmentDialogLayout);
        AssignmentDialogLayout.setHorizontalGroup(
            AssignmentDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        AssignmentDialogLayout.setVerticalGroup(
            AssignmentDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        ScoreBoard.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        ScoreBoard.setMinimumSize(new java.awt.Dimension(650, 600));
        ScoreBoard.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                ScoreBoardWindowClosing(evt);
            }
        });

        jPanel12.setBackground(new java.awt.Color(204, 204, 255));

        jLabel70.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel70.setText("Score Board");

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("Select A Class"));

        scoreCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scoreComboActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scoreCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scoreCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel14.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        scoreList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                scoreListMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(scoreList);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel71.setText("Students found in this class. Click to select");

        jLabel72.setText("Class Work");

        jLabel73.setText("Home Work");

        jLabel74.setText("Class Test");

        jLabel75.setText("Exam Score");

        classWorkField.setText("0");

        homeWorkField.setText("0");

        classTestField.setText("0");

        examScoreField.setText("0");

        addScoreLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        addScoreLabel.setText("Add Score");
        addScoreLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        addScoreLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addScoreLabelMouseClicked(evt);
            }
        });

        jLabel10.setText("10");

        jLabel11.setText("10");

        jLabel22.setText("10");

        jLabel23.setText("100");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel70)
                    .addComponent(jLabel71))
                .addGap(146, 146, 146))
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel72)
                            .addComponent(jLabel73)
                            .addComponent(jLabel75))
                        .addGap(37, 37, 37)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(classTestField)
                            .addComponent(classWorkField)
                            .addComponent(homeWorkField)
                            .addComponent(examScoreField, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)))
                    .addComponent(jLabel74))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(addScoreLabel)
                        .addGap(177, 177, 177))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel23))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel70)
                .addGap(34, 34, 34)
                .addComponent(jLabel71)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGap(56, 56, 56)
                                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(73, 73, 73)
                        .addComponent(jLabel72))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(classWorkField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel73)
                    .addComponent(homeWorkField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel74)
                    .addComponent(classTestField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addScoreLabel)
                    .addComponent(jLabel22))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel75)
                    .addComponent(examScoreField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout ScoreBoardLayout = new javax.swing.GroupLayout(ScoreBoard.getContentPane());
        ScoreBoard.getContentPane().setLayout(ScoreBoardLayout);
        ScoreBoardLayout.setHorizontalGroup(
            ScoreBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        ScoreBoardLayout.setVerticalGroup(
            ScoreBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconImages(null);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 204, 255));

        jLabel2.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 204));
        jLabel2.setText("[ART]Softs CONTINUOUS ASSESSMENT");

        jPanel2.setBackground(new java.awt.Color(204, 255, 255));
        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel15.setText("Exam Score");

        jLabel3.setText("Student ID");

        homework1Field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                homework1FieldActionPerformed(evt);
            }
        });
        homework1Field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                homework1FieldKeyTyped(evt);
            }
        });

        updateLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        updateLabel.setForeground(new java.awt.Color(102, 102, 0));
        updateLabel.setText("Update");
        updateLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        updateLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updateLabelMouseClicked(evt);
            }
        });

        examscoreField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                examscoreFieldKeyTyped(evt);
            }
        });

        jLabel13.setText("Home Work");

        classwork1Field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                classwork1FieldKeyTyped(evt);
            }
        });

        clearLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        clearLabel.setForeground(new java.awt.Color(0, 0, 204));
        clearLabel.setText("Clear");
        clearLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        clearLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clearLabelMouseClicked(evt);
            }
        });

        deleteLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        deleteLabel.setForeground(new java.awt.Color(204, 0, 0));
        deleteLabel.setText("Delete");
        deleteLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        deleteLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteLabelMouseClicked(evt);
            }
        });

        jLabel12.setText("Class Work");

        jLabel14.setText("Class Test");

        classtest1Field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                classtest1FieldKeyTyped(evt);
            }
        });

        jLabel5.setText("Class ID");

        jLabel50.setText("10");

        jLabel60.setText("10");

        jLabel61.setText("10");

        jLabel62.setText("100");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addComponent(homework1Field, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(jLabel60))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel14)
                .addGap(24, 24, 24)
                .addComponent(classtest1Field, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(jLabel61))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(clearLabel)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(examscoreField, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(jLabel62))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(updateLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(deleteLabel)
                        .addGap(18, 18, 18))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel12))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel3))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel5)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(classwork1Field, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                    .addComponent(txt_studentId)
                    .addComponent(txt_classId))
                .addGap(14, 14, 14)
                .addComponent(jLabel50))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_studentId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_classId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel12))
                    .addComponent(classwork1Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel50)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel13))
                    .addComponent(homework1Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel60)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel14))
                    .addComponent(classtest1Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel61)))
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel62)
                    .addComponent(examscoreField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(53, 53, 53)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updateLabel)
                    .addComponent(clearLabel)
                    .addComponent(deleteLabel)))
        );

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel20.setText("Please change values below");

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel21.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/assessment/main/search.jpg"))); // NOI18N
        jLabel21.setText("Search");

        searchCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchComboActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21)
                .addGap(18, 18, 18)
                .addComponent(searchCombo, 0, 118, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(searchCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 204));
        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
                .addContainerGap())
        );

        dateLabel.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        dateLabel.setText("Date Label");

        timeLabel.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        timeLabel.setText("Time Label");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel16.setText("To populate the table select a class on your upper left corner.");

        jLabel18.setText("Click on");

        jLabel19.setText("a record in");

        jLabel25.setText("the table");

        jLabel26.setText("to display");

        jLabel27.setText("on the left.");

        jLabel28.setText("You can");

        jLabel29.setText("edit values");

        jLabel30.setText("and click on");

        jLabel31.setText("the update");

        jLabel32.setText("button to");

        jLabel33.setText("save");

        jLabel34.setText("changes");

        jLabel35.setText("made.");

        jLabel36.setText("NOTE:");

        jPanel10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txt_filter.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, null, null, new java.awt.Color(255, 255, 204), new java.awt.Color(0, 102, 51)));
        txt_filter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_filterKeyReleased(evt);
            }
        });

        jLabel51.setText("Filter Results");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel51)
                .addGap(18, 18, 18)
                .addComponent(txt_filter, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_filter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel51))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel20)
                        .addGap(52, 52, 52)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19)
                    .addComponent(jLabel25)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29)
                    .addComponent(jLabel30)
                    .addComponent(jLabel31)
                    .addComponent(jLabel32)
                    .addComponent(jLabel33)
                    .addComponent(jLabel34)
                    .addComponent(jLabel35)
                    .addComponent(jLabel36)
                    .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel26))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel16)
                        .addGap(266, 266, 266))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(106, 106, 106)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(62, 62, 62)
                                .addComponent(dateLabel)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 147, Short.MAX_VALUE)
                        .addComponent(timeLabel)
                        .addGap(34, 34, 34))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dateLabel)
                    .addComponent(jLabel2)
                    .addComponent(timeLabel))
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(117, 117, 117)
                                .addComponent(jLabel20)
                                .addGap(17, 17, 17)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(145, 145, 145)
                                .addComponent(jLabel36)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel26)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel27)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel28)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel29)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel30)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel31)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel32)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel33)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel34)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel35)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(576, 576, 576))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1250, 660));

        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Add Student");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);
        jMenu1.add(jSeparator1);

        jMenuItem7.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem7.setText("Assign A Student");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem7);
        jMenu1.add(jSeparator6);

        jMenuItem8.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem8.setText("Enter Score");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem8);
        jMenu1.add(jSeparator5);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Print Terminal Report");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);
        jMenu1.add(jSeparator2);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setText("Generate Spreadsheet");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);
        jMenu1.add(jSeparator4);

        jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem6.setText("Exit");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem6);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Help");

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setText("Manual");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);
        jMenu2.add(jSeparator3);

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem5.setText("Logout");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void homework1FieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homework1FieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_homework1FieldActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        ExcelDialog.setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void subjectTeacherRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subjectTeacherRadioActionPerformed
        // TODO add your handling code here:
        subjectTeacherPanel.setVisible(true);
        classTeacherPanel.setVisible(false);
    }//GEN-LAST:event_subjectTeacherRadioActionPerformed

    private void classTeacherRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_classTeacherRadioActionPerformed
       
    }//GEN-LAST:event_classTeacherRadioActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed

        ReportDialog.setVisible(true);
        isOpened = true;
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void clearLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearLabelMouseClicked
        // TODO add your handling code here:
        Clear();
    }//GEN-LAST:event_clearLabelMouseClicked

    private void updateLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateLabelMouseClicked
        // TODO add your handling code here:
        Score score = new Score();
        Update(score);
    }//GEN-LAST:event_updateLabelMouseClicked

    private void deleteLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteLabelMouseClicked
        // TODO add your handling code here:
        Delete();
    }//GEN-LAST:event_deleteLabelMouseClicked

    private void classwork1FieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_classwork1FieldKeyTyped
        // TODO add your handling code here:
         char c = evt.getKeyChar();
        if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE ) || (c==KeyEvent.VK_DELETE))){
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_classwork1FieldKeyTyped

    private void homework1FieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_homework1FieldKeyTyped
        // TODO add your handling code here:
         char c = evt.getKeyChar();
        if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE ) || (c==KeyEvent.VK_DELETE))){
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_homework1FieldKeyTyped

    private void classtest1FieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_classtest1FieldKeyTyped
        // TODO add your handling code here:
         char c = evt.getKeyChar();
        if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE ) || (c==KeyEvent.VK_DELETE))){
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_classtest1FieldKeyTyped

    private void examscoreFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_examscoreFieldKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE ) || (c==KeyEvent.VK_DELETE))){
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_examscoreFieldKeyTyped

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        populateTextFields();
    }//GEN-LAST:event_jTable1MouseClicked

    private void continueLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_continueLabel1MouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() == 1){
//            subjectTeacherSpreadsheet();
        }
    }//GEN-LAST:event_continueLabel1MouseClicked

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        new LoginForm().show();
        this.setVisible(false);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        StudentDialog.setVisible(true);
        isOpened = true;
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        int choice = JOptionPane.showConfirmDialog(null, "This action will close everything. Continue?");
        if(choice == JOptionPane.YES_OPTION){
        System.exit(0);
        }
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void continueLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_continueLabel2MouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() == 1){
//            classTeacherSpreadsheet();
        }
    }//GEN-LAST:event_continueLabel2MouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        int choice  = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?");
        if(choice == JOptionPane.YES_OPTION){
            this.dispose();
            new LoginForm().setVisible(true);
        }
    }//GEN-LAST:event_formWindowClosing

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        new LoginForm().displayManual();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
        AssignmentDialog.setVisible(true);
        isOpened = true;
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
        ScoreBoard.setVisible(true);
        isOpened = true;
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void assignStudentLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_assignStudentLabelMouseClicked
        if (evt.getClickCount() == 1){
            Assignment assign  = new Assignment();
            Assign(assign);
        }
    }//GEN-LAST:event_assignStudentLabelMouseClicked

    private void scoreListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scoreListMouseClicked
        idsForScore();
    }//GEN-LAST:event_scoreListMouseClicked

    private void addScoreLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addScoreLabelMouseClicked
        Score score = new Score();
        if (evt.getClickCount() == 1){
            Save(score);
        }
    }//GEN-LAST:event_addScoreLabelMouseClicked

    private void scoreComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scoreComboActionPerformed
        if ((int)scoreCombo.getSelectedItem().toString().trim().length() > 0){
           ScoreList(); 
        }else{
            JOptionPane.showMessageDialog(null, "No selection made", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        
    }//GEN-LAST:event_scoreComboActionPerformed

    private void assignComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignComboActionPerformed
        if ((int)assignCombo.getSelectedItem().toString().trim().length() > 0){
            PopulateAsignList();
        }else{
            JOptionPane.showMessageDialog(null, "No selection made", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_assignComboActionPerformed

    private void saveStudentLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveStudentLabelMouseClicked
       Student student = new Student();
        if (evt.getClickCount() == 1){
            SaveStudent(student);
        }
    }//GEN-LAST:event_saveStudentLabelMouseClicked

    private void assignStudentListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_assignStudentListMouseClicked
        FillAssignFields();
    }//GEN-LAST:event_assignStudentListMouseClicked

    private void searchComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchComboActionPerformed
         if (!isOpened){
             Table();
             Clear();
         }
        
    }//GEN-LAST:event_searchComboActionPerformed

    private void StudentDialogWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_StudentDialogWindowClosing
        isOpened = false;
    }//GEN-LAST:event_StudentDialogWindowClosing

    private void AssignmentDialogWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_AssignmentDialogWindowClosing
        isOpened = false;
    }//GEN-LAST:event_AssignmentDialogWindowClosing

    private void ScoreBoardWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_ScoreBoardWindowClosing
        isOpened = false;
    }//GEN-LAST:event_ScoreBoardWindowClosing

    private void ReportDialogWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_ReportDialogWindowClosing
        isOpened = false;
    }//GEN-LAST:event_ReportDialogWindowClosing

    private void jLabel58MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel58MouseClicked
        if (evt.getClickCount() == 1){
            progressBar = new ProgressBar(report_progress, report_progress_label, reportCombo.getSelectedItem().toString(), isDetailed);
            progressBar.execute();
        }
    }//GEN-LAST:event_jLabel58MouseClicked

    private void txt_filterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_filterKeyReleased
        Filter();
    }//GEN-LAST:event_txt_filterKeyReleased

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
//        if (evt.getKeyCode() == KeyEvent.VK_UP || evt.getKeyCode() == KeyEvent.VK_DOWN){
//            populateTextFields();
//        }
    }//GEN-LAST:event_jTable1KeyPressed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        isDetailed = true;
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        isDetailed = false;
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AssessmentForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AssessmentForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AssessmentForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AssessmentForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AssessmentForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog AssignmentDialog;
    private javax.swing.JDialog ExcelDialog;
    private javax.swing.JDialog ReportDialog;
    private javax.swing.JDialog ScoreBoard;
    private javax.swing.JDialog StudentDialog;
    private javax.swing.JLabel addScoreLabel;
    private javax.swing.JComboBox assignCombo;
    private javax.swing.JPanel assignStudentCombo;
    private javax.swing.JLabel assignStudentLabel;
    private javax.swing.JList assignStudentList;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox class2Combo;
    private javax.swing.JComboBox classCombo;
    private javax.swing.JTextField classIdField;
    private javax.swing.JTextField classNameField;
    private javax.swing.JPanel classTeacherPanel;
    private javax.swing.JRadioButton classTeacherRadio;
    private javax.swing.JTextField classTestField;
    private javax.swing.JTextField classWorkField;
    private javax.swing.JTextField classtest1Field;
    private javax.swing.JTextField classwork1Field;
    private javax.swing.JLabel clearLabel;
    private javax.swing.JComboBox cmb_student;
    private javax.swing.JLabel continueLabel1;
    private javax.swing.JLabel continueLabel2;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JLabel deleteLabel;
    private javax.swing.JTextField examScoreField;
    private javax.swing.JTextField examscoreField;
    private javax.swing.JTextField fullNameField;
    private javax.swing.JComboBox genderCombo;
    private javax.swing.JTextField homeWorkField;
    private javax.swing.JTextField homework1Field;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox reportCombo;
    private javax.swing.JPanel reportPanel;
    private javax.swing.JProgressBar report_progress;
    private javax.swing.JLabel report_progress_label;
    private javax.swing.JLabel saveStudentLabel;
    private javax.swing.JComboBox scoreCombo;
    private javax.swing.JList scoreList;
    private javax.swing.JComboBox searchCombo;
    private javax.swing.JComboBox staffidCombo;
    private javax.swing.JTextField studentIdField;
    private javax.swing.JComboBox subjectCombo;
    private javax.swing.JTextField subjectIdField;
    private javax.swing.JTextField subjectNameField;
    private javax.swing.JPanel subjectTeacherPanel;
    private javax.swing.JRadioButton subjectTeacherRadio;
    private javax.swing.JComboBox term2Combo;
    private javax.swing.JComboBox termCombo;
    private javax.swing.JTextField text_firstname;
    private javax.swing.JLabel timeLabel;
    private javax.swing.JTextField txt_classId;
    private javax.swing.JTextField txt_filter;
    private javax.swing.JTextField txt_lastname;
    private javax.swing.JTextField txt_middlename;
    private javax.swing.JTextField txt_studentId;
    private javax.swing.JLabel updateLabel;
    private javax.swing.JLabel updateStudentLabel;
    private javax.swing.JComboBox yearCombo1;
    private javax.swing.JComboBox yearCombo2;
    // End of variables declaration//GEN-END:variables
}
