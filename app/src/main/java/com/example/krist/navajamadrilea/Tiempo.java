package com.example.krist.navajamadrilea;

public class Tiempo {
    String estado_cielo="",vientodirec="";
    int periodo,temperatura,precipitacion,sensTermica,humedadRelativa,vientofuerza;
    String orto,ocaso;
    public Tiempo(){};
    public Tiempo(String estado_cielo, String vientodirec, int vientofuerza, int periodo, int temperatura, int precipitacion, int sensTermica, int humedadRelativa) {
        this.estado_cielo = estado_cielo;
        this.vientodirec = vientodirec;
        this.vientofuerza = vientofuerza;
        this.periodo = periodo;
        this.temperatura = temperatura;
        this.precipitacion = precipitacion;
        this.sensTermica = sensTermica;
        this.humedadRelativa = humedadRelativa;

    }

    public String getEstado_cielo() {
        return estado_cielo;
    }

    public void setEstado_cielo(String estado_cielo) {
        this.estado_cielo = estado_cielo;
    }

    public String getVientodirec() {
        return vientodirec;
    }

    public void setVientodirec(String vientodirec) {
        this.vientodirec = vientodirec;
    }

    public int getVientofuerza() {
        return vientofuerza;
    }

    public void setVientofuerza(int vientofuerza) {
        this.vientofuerza = vientofuerza;
    }



    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public int getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(int temperatura) {
        this.temperatura = temperatura;
    }

    public int getPrecipitacion() {
        return precipitacion;
    }

    public void setPrecipitacion(int precipitacion) {
        this.precipitacion = precipitacion;
    }

    public int getSensTermica() {
        return sensTermica;
    }

    public void setSensTermica(int sensTermica) {
        this.sensTermica = sensTermica;
    }

    public int getHumedadRelativa() {
        return humedadRelativa;
    }

    public void setHumedadRelativa(int humedadRelativa) {
        this.humedadRelativa = humedadRelativa;
    }

    public String getOrto() {
        return orto;
    }

    public void setOrto(String orto) {
        this.orto = orto;
    }

    public String getOcaso() {
        return ocaso;
    }

    public void setOcaso(String ocaso) {
        this.ocaso = ocaso;
    }
}
