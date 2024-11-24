package com.example.tesis_01;

public class Producto_statInfo {

    int id_pro;
    String cod_pro;
    String des_pro;
    int cant_ven;
    float pre_pro;
    float total_pro;

    public Producto_statInfo(int id_pro, String cod_pro, String des_pro, int cant_ven, float pre_pro, float total_pro) {
        this.id_pro = id_pro;
        this.cod_pro = cod_pro;
        this.des_pro = des_pro;
        this.cant_ven = cant_ven;
        this.pre_pro = pre_pro;
        this.total_pro = total_pro;
    }

    public int getId_pro() {
        return id_pro;
    }

    public void setId_pro(int id_pro) {
        this.id_pro = id_pro;
    }

    public String getCod_pro() {
        return cod_pro;
    }

    public void setCod_pro(String cod_pro) {
        this.cod_pro = cod_pro;
    }

    public String getDes_pro() {
        return des_pro;
    }

    public void setDes_pro(String des_pro) {
        this.des_pro = des_pro;
    }

    public int getCant_ven() {
        return cant_ven;
    }

    public void setCant_ven(int cant_ven) {
        this.cant_ven = cant_ven;
    }

    public float getPre_pro() {
        return pre_pro;
    }

    public void setPre_pro(float pre_pro) {
        this.pre_pro = pre_pro;
    }

    public float getTotal_pro() {
        return total_pro;
    }

    public void setTotal_pro(float total_pro) {
        this.total_pro = total_pro;
    }
}
