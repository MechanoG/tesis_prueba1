package com.example.tesis_01;

public class Cliente_statInfo {

    int cli_id;
    String rif;
    String raz_soc;
    int pedidos;
    float total_com;

    public Cliente_statInfo(int cli_id, String rif, String raz_soc, int pedidos, float total_com) {
        this.cli_id = cli_id;
        this.rif = rif;
        this.raz_soc = raz_soc;
        this.pedidos = pedidos;
        this.total_com = total_com;
    }

    public int getCli_id() {
        return cli_id;
    }

    public void setCli_id(int cli_id) {
        this.cli_id = cli_id;
    }

    public String getRif() {
        return rif;
    }

    public void setRif(String rif) {
        this.rif = rif;
    }

    public String getRaz_soc() {
        return raz_soc;
    }

    public void setRaz_soc(String raz_soc) {
        this.raz_soc = raz_soc;
    }

    public int getPedidos() {
        return pedidos;
    }

    public void setPedidos(int pedidos) {
        this.pedidos = pedidos;
    }

    public float getTotal_com() {
        return total_com;
    }

    public void setTotal_com(float total_com) {
        this.total_com = total_com;
    }
}
