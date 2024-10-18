package com.example.tesis_01;

public class Pedidos_lista {

    private int id_pe;
    private int id_cli;
    private int id_use;
    private float pe_total;
    private String estado;

    public Pedidos_lista(int id_pe, float pe_total, int id_cli, int id_use, String estado) {
        this.id_pe = id_pe;
        this.pe_total = pe_total;
        this.id_cli = id_cli;
        this.id_use = id_use;
        this.estado = estado;
    }

    public int getId_pe() {
        return id_pe;
    }

    public void setId_pe(int id_pe) {
        this.id_pe = id_pe;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public float getPe_total() {
        return pe_total;
    }

    public void setPe_total(float pe_total) {
        this.pe_total = pe_total;
    }

    public int getId_cli() {
        return id_cli;
    }

    public void setId_cli(int id_cli) {
        this.id_cli = id_cli;
    }

    public int getId_use() {
        return id_use;
    }

    public void setId_use(int id_use) {
        this.id_use = id_use;
    }
}
