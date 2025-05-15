package com.exemple;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.exemple.dao.ArticleDao;
import com.exemple.dao.UtilisateurDao;


public class App {
    public static void main(String[] args) {
        System.out.println("Démarrage de l'application");
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure()
            .build();
        Metadata metadata = new MetadataSources(registry).buildMetadata();
        SessionFactory sessionFactory = metadata.buildSessionFactory();
        System.out.println("Connexion réussie !");

        // CREATE USERS ARTICLES IN MEMORY
        List<Utilisateur> uList = new ArrayList<>();
        List<Article> aList = new ArrayList<>();
        Utilisateur u0 = new Utilisateur("Bob", "bob@mail.com", new ArrayList<>());
        Utilisateur u1 = new Utilisateur("Alice", "alice@mail.com", new ArrayList<>());
        uList.add(u0);
        uList.add(u1);
        Article a0 = new Article("The Once & Future King", "Arthouuur ! C'est la guerrrre !", u1);
        Article a1 = new Article("H2G2", "The Hitchhiker's Guide to the Galaxy", u1);
        aList.add(a0);
        aList.add(a1);

        // DB OPERATIONS
        UtilisateurDao uDao = new UtilisateurDao(sessionFactory);
        ArticleDao aDao = new ArticleDao(sessionFactory);
        for (Utilisateur u : uList) uDao.creer(u);
        a0.getAuteur().getArticles().add(a0);
        a1.getAuteur().getArticles().add(a1);
        for (Article a : aList) aDao.creer(a);
        
        // DISPLAY        
        for (Utilisateur u : uDao.tout()) System.out.println(u.toString());
        for (Article a : aDao.tout()) System.out.println(a.toString());

        // CLEAN UP
        // for (Utilisateur u : uList) uService.supprimer(u.getId());
    }
}
