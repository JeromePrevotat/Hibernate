package com.exemple;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

// SINGLE_TABLE - JOINED - TABLE_PER_CLASS
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Entity
@DiscriminatorColumn(name="type_article", discriminatorType = DiscriminatorType.STRING)
// @MappedSuperclass
public abstract class Publication {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String titre;

    @NotBlank
    private String contenu;

    @NotBlank
    @PastOrPresent
    @JsonIgnore
    private LocalDate datePublication;

    public Publication(){}

    public Publication(String titre, String contenu, LocalDate datePublication) {
        this.titre = titre;
        this.contenu = contenu;
        this.datePublication = datePublication;
    }

    // GETTER
    public Long getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getContenu() {
        return contenu;
    }

    public LocalDate getDatePublication() {
        return datePublication;
    }

    // SETTER
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public void setDatePublication(LocalDate datePublication) {
        this.datePublication = datePublication;
    }

    
}
