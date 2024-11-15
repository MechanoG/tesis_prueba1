package com.example.tesis_01;

public class Producto {
    private int id;
    private String codigo;
    private String descripcion;
    private int cantidad;
    private float precio;


     public Producto(int id, float precio, int cantidad, String descripcion, String codigo) {
        this.id = id;
        this.precio = precio;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.codigo = codigo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
