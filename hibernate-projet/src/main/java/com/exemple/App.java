package com.exemple;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


public class App {
    public static void main(String[] args) {
        System.out.println("Démarrage de l'application");
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure()
            .build();
        Metadata metadata = new MetadataSources(registry).buildMetadata();
        SessionFactory sessionFactory = metadata.buildSessionFactory();
        System.out.println("Connexion réussie !");

        UtilisateurService uService = new UtilisateurService(sessionFactory);
        ArticleService aService = new ArticleService(sessionFactory);
        Utilisateur u0 = new Utilisateur("Bob", "bob@mail.com", new ArrayList<>());
        Utilisateur u1 = new Utilisateur("Alice", "alice@mail.com", new ArrayList<>());
        uService.creer(u0);
        System.out.println(uService.lire(u0.getId()).toString());
        
        // System.out.println(uService.lire(u1.getId()).toString());
        u0.setEmail("bobibob@gmail.com");
        uService.mettreAJour(u0);
        System.out.println(uService.lire(u0.getId()).toString());
        uService.supprimer(u0.getId());

        Article a0 = new Article("The Once & Future King", "Arthouuur ! C'est la guerrrre !", u1);
        Article a1 = new Article("H2G2", "The Hitchhiker's Guide to the Galaxy", u1);
        a0.getAuteur().getArticles().add(a0);
        a1.getAuteur().getArticles().add(a1);
        uService.creer(u1);
        // GET USER LIST
        List<Utilisateur> uList;
        try(Session session = sessionFactory.openSession()){
            uList = session.createQuery("FROM Utilisateur", Utilisateur.class).list();
        }
        System.out.println("----- USER LIST -----");
        for (Utilisateur u : uList){
            System.out.println(u.toString());
        }
        // aService.creer(a0);
        // aService.creer(a1);
        for (Article a : u1.getArticles()) System.out.println(a.toString());
        // for (Utilisateur u : uList){
        //     for (Article a : u.getArticles()){
        //         aService.supprimer(a.getId());
        //     }
        // }
        for (Utilisateur u : uList) uService.supprimer(u.getId());
        // while (true){}
    }
}
