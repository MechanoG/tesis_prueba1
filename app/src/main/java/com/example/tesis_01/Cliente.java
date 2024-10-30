package com.example.tesis_01;

public class Cliente {

    int cliente_id;
    String rif;
    String razon_social;


    public Cliente(String rif, String razon_social, int cliente_id) {
       this.rif = rif;
       this.razon_social = razon_social;
       this.cliente_id = cliente_id;
    }

    public int getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
    }

    public String getRif() {
       return rif;
   }

   public void setRif(String rif) {
       this.rif = rif;
   }

   public String getRazon_social() {
       return razon_social;
   }

   public void setRazon_social(String razon_social) {
       this.razon_social = razon_social;
   }

}
