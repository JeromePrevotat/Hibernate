package com.exemple;

import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Utilisateur {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    private String nom;
    private String email;

    @OneToMany(targetEntity=Article.class, mappedBy="auteur", cascade=CascadeType.ALL, orphanRemoval=true, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Article> articles;

    public Utilisateur() {}

    public Utilisateur(String nom, String email, List<Article> articles) {
        this.nom = nom;
        this.email = email;
        this.articles = articles;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public void addArticle(Article a){
        if (a != null){
            this.articles.add(a);
            a.setAuteur(this);
        }
    }

    public void rmArticle(Article a){
        if (a != null){
            articles.remove(a);
            a.setAuteur(null);
        }
    }

    @Override
    public String toString(){
         return ("Utilisateur: " + this.id + " " + this.nom + " " + this.email);
    }

    
}
