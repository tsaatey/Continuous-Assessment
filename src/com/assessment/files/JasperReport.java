
package com.assessment.files;

import com.assessment.utils.JavaDB;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;


public class JasperReport{
    
    private static Connection con;
    
    public void DisplaySimpleReport(String classname) throws SQLException{
        con = JavaDB.DBConnection();
        JFrame frame = new JFrame("Terminal Report Sheet");
        try {
            JasperDesign jd = JRXmlLoader.load("C:\\Users\\ARTLIB\\Dropbox\\NetBeansProjects\\ContinuousAssessment\\"+classname+".jrxml");
            net.sf.jasperreports.engine.JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, con);
            frame.getContentPane().add(new JRViewer(jp));
            frame.pack();
            frame.setSize(900, 700);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void DisplayDetailedReport(String classname){
        con = JavaDB.DBConnection();
        JFrame frame = new JFrame("Terminal Report Sheet");
        try {
            JasperDesign jd = JRXmlLoader.load("C:\\Users\\ARTLIB\\Dropbox\\NetBeansProjects\\ContinuousAssessment\\detailed_reports\\"+classname+".jrxml");
            net.sf.jasperreports.engine.JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, con);
            frame.getContentPane().add(new JRViewer(jp));
            frame.pack();
            frame.setSize(1100, 700);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
