package com.aby.mvc_app.model;


public class Client {
    public long id;
    public String nom;
    public String ville;

    public Client(long id, String nom, String ville) {
        this.id = id;
        this.nom = nom;
        this.ville = ville;
    }

    @Override
    public String toString() {
        return id + " - " + nom + " - " + ville;
    }
}