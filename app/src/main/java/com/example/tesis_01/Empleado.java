package com.example.tesis_01;

public class Empleado {

    private String Usuario;
    private String contraseña;
    private String tipo;
    private String nombre;
    private String apellido;
    private String cedula;
    private String sexo;
    private String telf;

    public Empleado(String telf, String sexo, String cedula, String usuario, String contraseña,
                    String tipo, String nombre, String apellido) {

        this.telf = telf;
        this.sexo = sexo;
        this.cedula = cedula;
        Usuario = usuario;
        this.contraseña = contraseña;
        this.tipo = tipo;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public String getTelf() {
        return telf;
    }

    public void setTelf(String telf) {
        this.telf = telf;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}


