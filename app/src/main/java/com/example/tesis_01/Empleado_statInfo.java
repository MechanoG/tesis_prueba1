package com.example.tesis_01;

public class Empleado_statInfo {

    int emp_id;
    String emp_nam;
    String emp_user;
    String zona;
    int emp_ped;
    float emp_total;

    public Empleado_statInfo(int emp_id, String emp_nam, String emp_user, String zona, int emp_ped, float emp_total) {
        this.emp_id = emp_id;
        this.emp_nam = emp_nam;
        this.emp_user = emp_user;
        this.zona = zona;
        this.emp_ped = emp_ped;
        this.emp_total = emp_total;
    }

    public int getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(int emp_id) {
        this.emp_id = emp_id;
    }

    public String getEmp_nam() {
        return emp_nam;
    }

    public void setEmp_nam(String emp_nam) {
        this.emp_nam = emp_nam;
    }

    public String getEmp_user() {
        return emp_user;
    }

    public void setEmp_user(String emp_user) {
        this.emp_user = emp_user;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public int getEmp_ped() {
        return emp_ped;
    }

    public void setEmp_ped(int emp_ped) {
        this.emp_ped = emp_ped;
    }

    public float getEmp_total() {
        return emp_total;
    }

    public void setEmp_total(float emp_total) {
        this.emp_total = emp_total;
    }
}
