package com.exemple;


import org.hibernate.annotations.OnDelete;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    private String titre;

    @NotNull
    private String contenu;

    @ManyToOne
    @JoinColumn(name="auteur_id")
    private Utilisateur auteur;

    public Article(){}

    public Article (String titre, String contenu, Utilisateur auteur){
        this.titre = titre;
        this.contenu = contenu;
        this.auteur = auteur;
    }

    public Long getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getContenu() {
        return contenu;
    }

    public Utilisateur getAuteur() {
        return auteur;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public void setAuteur(Utilisateur auteur) {
        this.auteur = auteur;
    }

    @Override
    public String toString(){
        return ("Titre: " + this.titre + "\nAuteur: " + this.getAuteur().getNom() + "\nContenu: " + this.contenu);
    }
}
