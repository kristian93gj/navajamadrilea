package com.example.krist.navajamadrilea;

import java.util.ArrayList;

public class Actuacion {
    String nombre,direccion,descripcion,categoria,foto,finicio,ffin,precio;
    double latitud,longitud;
    static ArrayList<Actuacion> actua;
    public Actuacion() {
    }

    public Actuacion(String nombre, String direccion, String descripcion, String categoria, String foto, String finicio, String ffin,String precio, double latitud, double longitud) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.foto = foto;
        this.finicio = finicio;
        this.ffin = ffin;
        this.precio = precio;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public Actuacion(String nombre, String direccion, String descripcion, String categoria, String foto, String finicio, String ffin, String precio) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.foto = foto;
        this.finicio = finicio;
        this.ffin = ffin;
        this.precio = precio;

    }

    public String getFinicio() {

        return finicio;
    }

    public void setFinicio(String finicio) {
        this.finicio = finicio;
    }

    public String getFfin() {
        return ffin;
    }

    public void setFfin(String ffin) {
        this.ffin = ffin;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public static ArrayList<Actuacion> getActua() {
        return actua;
    }

    public void setarray(ArrayList<Actuacion> actua){ this.actua = actua;}

    public Actuacion(String nombre, String direccion, String descripcion, String categoria, String foto) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.foto = foto;
    }

    public Actuacion(String nombre, String direccion, String descripcion, String categoria, String foto, double latitud, double longitud) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.foto = foto;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
