package com.example.crud;

import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

public class Etudiant {
    private int id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private Float moyenne;
    private String matricule;

    public Etudiant() {
    }

    public Etudiant(int id, String matricule, String nom, String prenom, LocalDate dateNaissance, Float moyenne) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.moyenne = moyenne;
        this.matricule = matricule;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public Float getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(Float moyenne) {
        this.moyenne = moyenne;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }
}
