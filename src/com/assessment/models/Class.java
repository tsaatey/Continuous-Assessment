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
public class Class {
    
    private int classId;
    private String classname;
    private int numberOnRoll;

    public Class() {
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public int getNumberOnRoll() {
        return numberOnRoll;
    }

    public void setNumberOnRoll(int numberOnRoll) {
        this.numberOnRoll = numberOnRoll;
    }
    
}
