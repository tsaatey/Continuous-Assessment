/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assessment.utils;


import com.assessment.files.JasperReport;
import java.sql.SQLException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

/**
 *
 * @author ARTLIB
 */
public class ProgressBar extends SwingWorker<Void, Void> {
    
    private JProgressBar bar;
    private JLabel label;
    private final String classname;
    private final boolean isDetailed;
    
    public ProgressBar(){
        bar.setIndeterminate(false);
        label.setText(null);
        classname = null;
        isDetailed = false;
    }
    
    public ProgressBar(JProgressBar bar, JLabel label, String classname, boolean isDetailed){
        this.bar = bar;
        this.label = label;
        this.classname = classname;
        this.isDetailed = isDetailed;
    }
    @Override
    public Void doInBackground()  {
        bar.setIndeterminate(true);
        label.setText("Generating report. Please wait...");
        CreateReport();
        return null;
    }
    @Override
    public void done(){
        bar.setIndeterminate(false);
        label.setText(null);
    }
    
    public void CreateReport(){
        JasperReport jrp = new JasperReport();
        try{
            if (isDetailed){
              jrp.DisplayDetailedReport(classname);  
            } else {
                jrp.DisplaySimpleReport(classname);
            }   
        }catch(SQLException e){
          JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
