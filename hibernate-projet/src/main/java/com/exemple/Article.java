package com.exemple;


import java.time.LocalDate;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@DiscriminatorValue("Article")
public class Article extends Publication{

    @NotBlank
    @Size(max = 100)
    private String titre;

    @NotNull
    private String contenu;

    @ManyToOne
    @JoinColumn(name="auteur_id")
    @NotBlank
    // @NotNull  NOT POSSIBLE IN SINGLE_TABLE
    private Utilisateur auteur;

    @NotBlank
    private int nbVues = 0;

    public Article(){ super(); }

    public Article (String titre, String contenu, Utilisateur auteur, LocalDate datePublication){
        super(titre, contenu, datePublication);
        this.auteur = auteur;
    }

    public Utilisateur getAuteur() {
        return auteur;
    }

    public int getNbVues(){
        return this.nbVues;
    }

    public void setAuteur(Utilisateur auteur) {
        this.auteur = auteur;
    }

    public void setNbVues(int nbVues){
        this.nbVues = nbVues;
    }

    @Override
    public String toString(){
        return ("Titre: " + this.titre +
        "\nAuteur: " + this.getAuteur().getNom() +
        "\nContenu: " + this.contenu) +
        "\nDate de Publication: " + this.getDatePublication() +
        "\nNombre de Vues: " + this.nbVues;
    }
}
