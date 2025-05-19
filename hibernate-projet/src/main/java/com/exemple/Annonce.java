package com.exemple;


import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Annonce extends Publication{

    @NotBlank
    @Size(max = 100)
    private String titre;

    @NotNull
    private String contenu;

    @NotBlank
    @Future
    private LocalDate dateExpiration;

    @NotBlank
    @Email
    private String contactEmail;

    private BigDecimal prix;

    public Annonce(){ super(); }

    public Annonce (String titre, String contenu, LocalDate datePublication, LocalDate dateExpiration, String contactEmail, BigDecimal prix){
        super(titre, contenu, datePublication);
        this.dateExpiration = dateExpiration;
        this.contactEmail = contactEmail;
        this.prix = prix;
    }

    public LocalDate getDateExpiration() {
        return dateExpiration;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public void setDateExpiration(LocalDate dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

    @Override
    public String toString(){
        return ("Titre: " + this.titre +
        "\nContenu: " + this.contenu) +
        "\nDate de Publication: " + this.getDatePublication() +
        "\nDate d'Expiration: " + this.getDateExpiration() +
        "\nContact Mail: " + this.getContactEmail() +
        "\nPrix: " + this.getPrix();
    }
}
