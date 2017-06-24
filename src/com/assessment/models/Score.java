/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assessment.models;

/**
 *
 * @author ARTLIB
 */
public class Score {
    
    private int scoreId;
    private int studentId;
    private int subjectId;
    private int classId;
    private double homework;
    private double classwork;
    private double classtest;
    private double exam;
    private double thirty;
    private double seventy;
    private double total;
    private String grade;
    private String effort;
    private String position;

    public Score() {
    }

    public int getScoreId() {
        return scoreId;
    }

    public void setScoreId(int scoreId) {
        this.scoreId = scoreId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public double getHomework() {
        return homework;
    }

    public void setHomework(double homework) {
        this.homework = homework;
    }

    public double getClasswork() {
        return classwork;
    }

    public void setClasswork(double classwork) {
        this.classwork = classwork;
    }

    public double getClasstest() {
        return classtest;
    }

    public void setClasstest(double classtest) {
        this.classtest = classtest;
    }

    public double getExam() {
        return exam;
    }

    public void setExam(double exam) {
        this.exam = exam;
    }

    public double getThirty() {
        return thirty;
    }

    public void setThirty(double thirty) {
        this.thirty = thirty;
    }

    public double getSeventy() {
        return seventy;
    }

    public void setSeventy(double seventy) {
        this.seventy = seventy;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getEffort() {
        return effort;
    }

    public void setEffort(String effort) {
        this.effort = effort;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
       
}
