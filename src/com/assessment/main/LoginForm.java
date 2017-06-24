/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assessment.main;

import com.assessment.bussinessmodel.User_Login;
import com.assessment.models.Teacher;
import static com.assessment.utils.MD5.MD5;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class LoginForm extends javax.swing.JFrame {

    String status = "";
    
    
    public LoginForm() {
        initComponents();
        this.setIconImage(new ImageIcon(getClass().getResource("logo.png")).getImage());
        setLocationRelativeTo(null);
        NewAccountDialog.setLocationRelativeTo(null);
        PasswordResetDialog.setLocationRelativeTo(null);
        passwordPanel.setVisible(false);
        passwordLabel.setText(null);
        hintLabel1.setText(null);
        hintLabel2.setText(null);
        informationLabel.setText(null);
        matchLabel.setText(null);
        invalidLabel.setText(null);
        forgetPasswordLabel.setText(null);
    }
    //Method to login
    private void newAccount(Teacher teacher){
        String a = passwordField1.getText();
        String b = passwordField2.getText();
        User_Login login = new User_Login();
        
        if(a.equals(b)){
            try {
                teacher.setStaffID(Integer.parseInt(staffidField.getText()));
                teacher.setFirstname(firstNameField.getText());
                teacher.setLastname(lastNameField.getText());
                teacher.setMiddlename(middleNameField.getText());
                teacher.setSex((String)sexCombo.getSelectedItem());
                teacher.setStatus(status);
                teacher.setUsername(usernameField.getText());
                teacher.setPassword(MD5(passwordField2.getText()));
                teacher.setPasswordHint(passwordHintField.getText());
                
                if (login.SignUp(teacher)) {
                    JOptionPane.showMessageDialog(null, "Account successfully created.");
                    passwordLabel.setText(null);
                    clearFields();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        else{
            passwordLabel.setText("Passwords do not match!!");
        }
    }
    //Method to create a new account
    private void Login(Teacher teacher){
        User_Login login = new User_Login();
        try{
            teacher.setUsername(usernameTextField.getText());
            teacher.setPassword(MD5(passwordField.getText()));
    
            if(login.SignIn(teacher)){
               new AssessmentForm().setVisible(true);
               usernameTextField.setText(null);
               passwordField.setText(null);
               this.hide();
            }
            else{
                invalidLabel.setText("Invalid Username or Password!");
                forgetPasswordLabel.setText("Forget password? Click Here");
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    //Method to reset password
    private void FieldVerification(Teacher teacher){
        User_Login login = new User_Login();
        try{
            teacher.setUsername(usernameResetField.getText());
            teacher.setPasswordHint(passwordHintResetField.getText());
      
            if(login.ResetVerification(teacher)){
                passwordPanel.setVisible(true);
            }
            else{
                informationLabel.setText("Information Provided is not Correct!");
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    //Method to clear fields
    private void clearFields(){
        staffidField.setText(null);
        firstNameField.setText(null);
        lastNameField.setText(null);
        middleNameField.setText(null);
        usernameField.setText(null);
        passwordField1.setText(null);
        passwordField2.setText(null);
        passwordHintField.setText(null);
    }
    //Method to Reset Password
    private void PasswordReset(Teacher teacher){
        User_Login login = new User_Login();
        String a = newPasswordField1.getText();
        String b = newPasswordField2.getText();
        if(a.equals(b)){
            try {
                teacher.setPassword(MD5(newPasswordField2.getText()));
                teacher.setUsername(usernameResetField.getText());

                if (login.ReplacePassword(teacher)) {
                    JOptionPane.showMessageDialog(null, "Password Reset Successful!");
                    resetClear();
                    PasswordResetDialog.setVisible(false);
                }

            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }else{
            matchLabel.setText("Passwords do not Match!");
        }
    }
    //Method to clear passwrod reset fields
    private void resetClear(){
        usernameResetField.setText(null);
        passwordHintResetField.setText(null);
        newPasswordField1.setText(null);
        newPasswordField2.setText(null);
    }
    //Method to open user manual
    protected void displayManual() {
        try {
            Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " + "[ART] Softs Continuous Assessment.pdf");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        NewAccountDialog = new javax.swing.JDialog();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        passwordField2 = new javax.swing.JPasswordField();
        passwordField1 = new javax.swing.JPasswordField();
        usernameField = new javax.swing.JTextField();
        sexCombo = new javax.swing.JComboBox();
        middleNameField = new javax.swing.JTextField();
        lastNameField = new javax.swing.JTextField();
        firstNameField = new javax.swing.JTextField();
        staffidField = new javax.swing.JTextField();
        passwordHintField = new javax.swing.JTextField();
        hintLabel1 = new javax.swing.JLabel();
        hintLabel2 = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        submitLabel1 = new javax.swing.JLabel();
        discardLabel = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        subjectTeacherRadio = new javax.swing.JRadioButton();
        classTeacherRadio = new javax.swing.JRadioButton();
        jLabel27 = new javax.swing.JLabel();
        PasswordResetDialog = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        informationPanel = new javax.swing.JPanel();
        informationLabel = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        usernameResetField = new javax.swing.JTextField();
        passwordHintResetField = new javax.swing.JTextField();
        passwordPanel = new javax.swing.JPanel();
        newPasswordField1 = new javax.swing.JPasswordField();
        matchLabel = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        newPasswordField2 = new javax.swing.JPasswordField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        usernameTextField = new javax.swing.JTextField();
        passwordField = new javax.swing.JPasswordField();
        loginButton = new javax.swing.JButton();
        newAccountLabel = new javax.swing.JLabel();
        invalidLabel = new javax.swing.JLabel();
        forgetPasswordLabel = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        helpLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        NewAccountDialog.setTitle("Create a new Account");
        NewAccountDialog.setMinimumSize(new java.awt.Dimension(490, 600));

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));
        jPanel2.setFocusable(false);
        jPanel2.setMinimumSize(new java.awt.Dimension(500, 400));

        jLabel6.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(153, 0, 153));
        jLabel6.setText("To create a new account please fill the form below and submit");

        jLabel7.setText("Staff ID");

        jLabel9.setText("First Name");

        jLabel10.setText("Last Name");

        jLabel11.setText("Middle Name");

        jLabel12.setText("Sex");

        jLabel13.setText("Username");

        jLabel14.setText("Password");

        jLabel15.setText("Re-type Password");

        jLabel16.setText("Password Hint");

        sexCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Male", "Female", "Other" }));

        middleNameField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                middleNameFieldKeyTyped(evt);
            }
        });

        lastNameField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                lastNameFieldKeyTyped(evt);
            }
        });

        firstNameField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                firstNameFieldKeyTyped(evt);
            }
        });

        staffidField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                staffidFieldKeyTyped(evt);
            }
        });

        hintLabel1.setForeground(new java.awt.Color(255, 51, 0));
        hintLabel1.setText("Password hint field");

        hintLabel2.setForeground(new java.awt.Color(255, 51, 0));
        hintLabel2.setText("cannot be empty!");

        passwordLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        passwordLabel.setForeground(new java.awt.Color(255, 0, 0));
        passwordLabel.setText("Passwords do not match!!");

        submitLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        submitLabel1.setForeground(new java.awt.Color(0, 0, 204));
        submitLabel1.setText("Submit");
        submitLabel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        submitLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                submitLabel1MouseClicked(evt);
            }
        });

        discardLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        discardLabel.setForeground(new java.awt.Color(153, 0, 153));
        discardLabel.setText("Discard");
        discardLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        discardLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                discardLabelMouseClicked(evt);
            }
        });

        jLabel23.setText("Status");

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

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 51, 0));
        jLabel27.setText("Clear");
        jLabel27.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel27MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel6))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addComponent(passwordLabel))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(171, 171, 171)
                        .addComponent(submitLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel27)
                        .addGap(18, 18, 18)
                        .addComponent(discardLabel))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel23)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel14))
                                .addGap(46, 46, 46)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(middleNameField)
                                    .addComponent(lastNameField)
                                    .addComponent(firstNameField)
                                    .addComponent(staffidField)
                                    .addComponent(sexCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(subjectTeacherRadio)
                                        .addGap(18, 18, 18)
                                        .addComponent(classTeacherRadio))
                                    .addComponent(usernameField)
                                    .addComponent(passwordField1)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel16))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(passwordField2)
                                    .addComponent(passwordHintField))))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(hintLabel2)
                            .addComponent(hintLabel1))))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(21, 21, 21)
                .addComponent(passwordLabel)
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel7))
                    .addComponent(staffidField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(firstNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(lastNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(middleNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(sexCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(subjectTeacherRadio)
                    .addComponent(classTeacherRadio))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(passwordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(passwordField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(passwordHintField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hintLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hintLabel2)
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(submitLabel1)
                    .addComponent(discardLabel)
                    .addComponent(jLabel27))
                .addGap(81, 81, 81))
        );

        javax.swing.GroupLayout NewAccountDialogLayout = new javax.swing.GroupLayout(NewAccountDialog.getContentPane());
        NewAccountDialog.getContentPane().setLayout(NewAccountDialogLayout);
        NewAccountDialogLayout.setHorizontalGroup(
            NewAccountDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        NewAccountDialogLayout.setVerticalGroup(
            NewAccountDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        PasswordResetDialog.setTitle("Reset Your Password");
        PasswordResetDialog.setMinimumSize(new java.awt.Dimension(460, 420));

        jPanel3.setBackground(new java.awt.Color(204, 204, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel17.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel17.setText("Welcome to Password Reset");
        jPanel3.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(92, 6, -1, -1));

        jLabel18.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel18.setText("Complete the form below");
        jPanel3.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, -1, -1));

        informationPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        informationLabel.setForeground(new java.awt.Color(255, 0, 51));
        informationLabel.setText("Information Provided is not Correct!");

        jLabel19.setText("Username");

        jLabel21.setText("Cancel");
        jLabel21.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel21MouseClicked(evt);
            }
        });

        jLabel20.setText("Password Hint");

        jLabel22.setText("Submit");
        jLabel22.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel22MouseClicked(evt);
            }
        });

        usernameResetField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameResetFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout informationPanelLayout = new javax.swing.GroupLayout(informationPanel);
        informationPanel.setLayout(informationPanelLayout);
        informationPanelLayout.setHorizontalGroup(
            informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(informationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, informationPanelLayout.createSequentialGroup()
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addComponent(jLabel20))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(passwordHintResetField, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                            .addComponent(usernameResetField)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, informationPanelLayout.createSequentialGroup()
                        .addGap(0, 121, Short.MAX_VALUE)
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, informationPanelLayout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel22)
                                .addGap(36, 36, 36))
                            .addComponent(informationLabel, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        informationPanelLayout.setVerticalGroup(
            informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(informationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(informationLabel)
                .addGap(11, 11, 11)
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(usernameResetField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordHintResetField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jLabel22))
                .addContainerGap())
        );

        jPanel3.add(informationPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(61, 80, 320, -1));

        passwordPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        matchLabel.setForeground(new java.awt.Color(255, 0, 51));
        matchLabel.setText("Passwords do not Match!");

        jLabel24.setText("Type New Password");

        jLabel25.setText("Re-type New Password");

        jLabel26.setText("Done");
        jLabel26.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel26MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout passwordPanelLayout = new javax.swing.GroupLayout(passwordPanel);
        passwordPanel.setLayout(passwordPanelLayout);
        passwordPanelLayout.setHorizontalGroup(
            passwordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(passwordPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(passwordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(passwordPanelLayout.createSequentialGroup()
                        .addGroup(passwordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25)
                            .addComponent(jLabel24))
                        .addGap(18, 18, 18)
                        .addGroup(passwordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(matchLabel)
                            .addGroup(passwordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(newPasswordField2)
                                .addComponent(newPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(passwordPanelLayout.createSequentialGroup()
                        .addGap(183, 183, 183)
                        .addComponent(jLabel26)))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        passwordPanelLayout.setVerticalGroup(
            passwordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(passwordPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(matchLabel)
                .addGap(18, 18, 18)
                .addGroup(passwordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(newPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(passwordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(newPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel26)
                .addContainerGap())
        );

        jPanel3.add(passwordPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(61, 236, 320, -1));

        javax.swing.GroupLayout PasswordResetDialogLayout = new javax.swing.GroupLayout(PasswordResetDialog.getContentPane());
        PasswordResetDialog.getContentPane().setLayout(PasswordResetDialogLayout);
        PasswordResetDialogLayout.setHorizontalGroup(
            PasswordResetDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
        );
        PasswordResetDialogLayout.setVerticalGroup(
            PasswordResetDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login Form");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 204));
        jLabel2.setText("[ART]Softs CONTINUOUS ASSESSMENT");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 360, -1));

        jLabel3.setFont(new java.awt.Font("Comic Sans MS", 1, 24)); // NOI18N
        jLabel3.setText("User login");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 110, -1, -1));

        jLabel4.setText("Username");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 230, -1, -1));

        jLabel5.setText("Password");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 270, -1, 20));

        usernameTextField.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        usernameTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                usernameTextFieldKeyPressed(evt);
            }
        });
        jPanel1.add(usernameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 230, 170, -1));

        passwordField.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        passwordField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                passwordFieldKeyPressed(evt);
            }
        });
        jPanel1.add(passwordField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 270, 170, -1));

        loginButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/assessment/main/login-button1.jpg"))); // NOI18N
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });
        jPanel1.add(loginButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 300, 90, 30));

        newAccountLabel.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        newAccountLabel.setText("New? Create an Account");
        newAccountLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newAccountLabelMouseClicked(evt);
            }
        });
        jPanel1.add(newAccountLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 60, -1, -1));

        invalidLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        invalidLabel.setForeground(new java.awt.Color(153, 0, 51));
        invalidLabel.setText("Invalid Username or Password");
        jPanel1.add(invalidLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 190, -1, -1));

        forgetPasswordLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        forgetPasswordLabel.setForeground(new java.awt.Color(51, 0, 204));
        forgetPasswordLabel.setText("Forget password? Click Here");
        forgetPasswordLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                forgetPasswordLabelMouseClicked(evt);
            }
        });
        jPanel1.add(forgetPasswordLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 210, 170, -1));

        jLabel8.setFont(new java.awt.Font("Courier New", 2, 11)); // NOI18N
        jLabel8.setText("Powered by: [ART]Softs (c) 2015. tsaatey@gmail.com. 0249640111");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, -1, -1));

        helpLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        helpLabel.setForeground(new java.awt.Color(0, 0, 153));
        helpLabel.setText("Need Help? Read Manual");
        helpLabel.setToolTipText("You need a PDF reader to open this file");
        helpLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                helpLabelMouseClicked(evt);
            }
        });
        jPanel1.add(helpLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 360, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/assessment/main/19795831-3d-white-person-with-a-pen-clipboard-and-blank-space-to-fill-with-text-isolated-white-background.jpg"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 450, 410));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 450, 410));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void newAccountLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newAccountLabelMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() == 1){
            NewAccountDialog.setVisible(true);
        }
    }//GEN-LAST:event_newAccountLabelMouseClicked

    private void firstNameFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_firstNameFieldKeyTyped
        // TODO add your handling code here:
         char c = evt.getKeyChar();
        if (!((c=='a') || (c=='b') || (c=='c') || (c=='d') || (c=='d') || (c=='e') || (c=='f') || (c=='g') || (c=='h') || (c=='i') || (c=='j') || (c=='k') || (c=='l') || (c=='m') || (c=='n') || (c=='o') || (c=='p') || (c=='q') || (c=='r') || (c=='s') || (c=='t') || (c=='u') || (c=='v') || (c=='w') || (c=='x') || (c=='y') || (c=='z') || (c=='A') || (c=='B') || (c=='C') || (c=='D') || (c=='E') || (c=='E') || (c=='F') || (c=='G') || (c=='H') || (c=='I') || (c=='J') || (c=='K') || (c=='L') || (c=='M') || (c=='N')|| (c=='O') || (c=='P') || (c=='Q') || (c=='R') || (c=='S') || (c=='T') || (c=='U') || (c=='V') || (c=='W') || (c=='X') || (c=='Y') || (c=='Z') || (c==KeyEvent.VK_BACK_SPACE) || (c==KeyEvent.VK_DELETE) || (c==KeyEvent.VK_SPACE) || (c==KeyEvent.VK_SHIFT)))
        {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_firstNameFieldKeyTyped

    private void usernameResetFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameResetFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameResetFieldActionPerformed

    private void forgetPasswordLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_forgetPasswordLabelMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() == 1){
            PasswordResetDialog.setVisible(true);
        }
    }//GEN-LAST:event_forgetPasswordLabelMouseClicked

    private void subjectTeacherRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subjectTeacherRadioActionPerformed
        // TODO add your handling code here:
        status = "Subject Teacher";
    }//GEN-LAST:event_subjectTeacherRadioActionPerformed

    private void classTeacherRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_classTeacherRadioActionPerformed
        // TODO add your handling code here:
        status = "Class Teacher";
    }//GEN-LAST:event_classTeacherRadioActionPerformed

    private void submitLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_submitLabel1MouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() == 1){
            Teacher teacher = new Teacher();
            newAccount(teacher);
        }
    }//GEN-LAST:event_submitLabel1MouseClicked

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        // TODO add your handling code here:
        Teacher teacher = new Teacher();
        Login(teacher);
    }//GEN-LAST:event_loginButtonActionPerformed

    private void passwordFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwordFieldKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            Teacher teacher = new Teacher();
            Login(teacher);
        }
        else if(evt.getKeyCode() == KeyEvent.VK_BACK_SPACE){
             invalidLabel.setText(null);
             //forgetPasswordLabel.setText(null);
         }
    }//GEN-LAST:event_passwordFieldKeyPressed

    private void usernameTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_usernameTextFieldKeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            Teacher teacher = new Teacher();
             Login(teacher);
        }
         else if(evt.getKeyCode() == KeyEvent.VK_BACK_SPACE){
             invalidLabel.setText(null);
             //forgetPasswordLabel.setText(null);
         }
    }//GEN-LAST:event_usernameTextFieldKeyPressed

    private void jLabel27MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel27MouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() == 1){
            clearFields();
        }
    }//GEN-LAST:event_jLabel27MouseClicked

    private void discardLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_discardLabelMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() == 1){
            NewAccountDialog.setVisible(false);
        }
    }//GEN-LAST:event_discardLabelMouseClicked

    private void jLabel22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel22MouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() == 1){
            Teacher teacher = new Teacher();
            FieldVerification(teacher);
        }
    }//GEN-LAST:event_jLabel22MouseClicked

    private void jLabel26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel26MouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() == 1){
            Teacher teacher = new Teacher();
            PasswordReset(teacher);
        }
    }//GEN-LAST:event_jLabel26MouseClicked

    private void staffidFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_staffidFieldKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE ) || (c==KeyEvent.VK_DELETE))){
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_staffidFieldKeyTyped

    private void lastNameFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lastNameFieldKeyTyped
        // TODO add your handling code here:
         char c = evt.getKeyChar();
        if (!((c=='a') || (c=='b') || (c=='c') || (c=='d') || (c=='d') || (c=='e') || (c=='f') || (c=='g') || (c=='h') || (c=='i') || (c=='j') || (c=='k') || (c=='l') || (c=='m') || (c=='n') || (c=='o') || (c=='p') || (c=='q') || (c=='r') || (c=='s') || (c=='t') || (c=='u') || (c=='v') || (c=='w') || (c=='x') || (c=='y') || (c=='z') || (c=='A') || (c=='B') || (c=='C') || (c=='D') || (c=='E') || (c=='E') || (c=='F') || (c=='G') || (c=='H') || (c=='I') || (c=='J') || (c=='K') || (c=='L') || (c=='M') || (c=='N')|| (c=='O') || (c=='P') || (c=='Q') || (c=='R') || (c=='S') || (c=='T') || (c=='U') || (c=='V') || (c=='W') || (c=='X') || (c=='Y') || (c=='Z') || (c==KeyEvent.VK_BACK_SPACE) || (c==KeyEvent.VK_DELETE) || (c==KeyEvent.VK_SPACE) || (c==KeyEvent.VK_SHIFT)))
        {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_lastNameFieldKeyTyped

    private void middleNameFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_middleNameFieldKeyTyped
        // TODO add your handling code here:
         char c = evt.getKeyChar();
        if (!((c=='a') || (c=='b') || (c=='c') || (c=='d') || (c=='d') || (c=='e') || (c=='f') || (c=='g') || (c=='h') || (c=='i') || (c=='j') || (c=='k') || (c=='l') || (c=='m') || (c=='n') || (c=='o') || (c=='p') || (c=='q') || (c=='r') || (c=='s') || (c=='t') || (c=='u') || (c=='v') || (c=='w') || (c=='x') || (c=='y') || (c=='z') || (c=='A') || (c=='B') || (c=='C') || (c=='D') || (c=='E') || (c=='E') || (c=='F') || (c=='G') || (c=='H') || (c=='I') || (c=='J') || (c=='K') || (c=='L') || (c=='M') || (c=='N')|| (c=='O') || (c=='P') || (c=='Q') || (c=='R') || (c=='S') || (c=='T') || (c=='U') || (c=='V') || (c=='W') || (c=='X') || (c=='Y') || (c=='Z') || (c==KeyEvent.VK_BACK_SPACE) || (c==KeyEvent.VK_DELETE) || (c==KeyEvent.VK_SPACE) || (c==KeyEvent.VK_SHIFT)))
        {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_middleNameFieldKeyTyped

    private void jLabel21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() == 1){
            PasswordResetDialog.setVisible(true);
        }
    }//GEN-LAST:event_jLabel21MouseClicked

    private void helpLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_helpLabelMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() == 1){
            displayManual();
        }
    }//GEN-LAST:event_helpLabelMouseClicked

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
//                //if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
                UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
                UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try{
                    Thread.sleep(2000);
                }catch(Exception e){
                    
                }
                new LoginForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog NewAccountDialog;
    private javax.swing.JDialog PasswordResetDialog;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JRadioButton classTeacherRadio;
    private javax.swing.JLabel discardLabel;
    private javax.swing.JTextField firstNameField;
    private javax.swing.JLabel forgetPasswordLabel;
    private javax.swing.JLabel helpLabel;
    private javax.swing.JLabel hintLabel1;
    private javax.swing.JLabel hintLabel2;
    private javax.swing.JLabel informationLabel;
    private javax.swing.JPanel informationPanel;
    private javax.swing.JLabel invalidLabel;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField lastNameField;
    private javax.swing.JButton loginButton;
    private javax.swing.JLabel matchLabel;
    private javax.swing.JTextField middleNameField;
    private javax.swing.JLabel newAccountLabel;
    private javax.swing.JPasswordField newPasswordField1;
    private javax.swing.JPasswordField newPasswordField2;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JPasswordField passwordField1;
    private javax.swing.JPasswordField passwordField2;
    private javax.swing.JTextField passwordHintField;
    private javax.swing.JTextField passwordHintResetField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JPanel passwordPanel;
    private javax.swing.JComboBox sexCombo;
    private javax.swing.JTextField staffidField;
    private javax.swing.JRadioButton subjectTeacherRadio;
    private javax.swing.JLabel submitLabel1;
    private javax.swing.JTextField usernameField;
    private javax.swing.JTextField usernameResetField;
    private javax.swing.JTextField usernameTextField;
    // End of variables declaration//GEN-END:variables
}
