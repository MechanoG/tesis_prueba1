package com.example.tesis_01;

public class Pedidos_lista {

    private int id_pe;
    private String  Cliente;
    private String vendedor;
    private float pe_total;
    private String ti_pago;
    private String vencimiento;
    private String estado;

    public Pedidos_lista(int id_pe, String estado, String vencimiento, float pe_total, String ti_pago, String vendedor, String cliente) {
        this.id_pe = id_pe;
        this.estado = estado;
        this.vencimiento = vencimiento;
        this.pe_total = pe_total;
        this.ti_pago = ti_pago;
        this.vendedor = vendedor;
        Cliente = cliente;
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

    public String getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(String vencimiento) {
        this.vencimiento = vencimiento;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public float getPe_total() {
        return pe_total;
    }

    public void setPe_total(float pe_total) {
        this.pe_total = pe_total;
    }

    public String getTi_pago() {
        return ti_pago;
    }

    public void setTi_pago(String ti_pago) {
        this.ti_pago = ti_pago;
    }

    public String getCliente() {
        return Cliente;
    }

    public void setCliente(String cliente) {
        Cliente = cliente;
    }
}
