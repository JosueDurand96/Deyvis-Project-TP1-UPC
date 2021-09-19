package com.example.greenfield.service;

public class Puntaje {
    private int id;
    private int disponible;
    private int ganado;
    private int canjeado;

    public Puntaje(int id, int disponible, int ganado, int canjeado) {
        this.id = id;
        this.disponible = disponible;
        this.ganado = ganado;
        this.canjeado = canjeado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDisponible() {
        return disponible;
    }

    public void setDisponible(int disponible) {
        this.disponible = disponible;
    }

    public int getGanado() {
        return ganado;
    }

    public void setGanado(int ganado) {
        this.ganado = ganado;
    }

    public int getCanjeado() {
        return canjeado;
    }

    public void setCanjeado(int canjeado) {
        this.canjeado = canjeado;
    }
}
