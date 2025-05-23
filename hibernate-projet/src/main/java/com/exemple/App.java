package com.exemple;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.exemple.dao.AnnonceDao;
import com.exemple.dao.ArticleDao;
import com.exemple.dao.PublicationDao;
import com.exemple.dao.UtilisateurDao;


public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Démarrage de l'application");
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure()
            .build();
        Metadata metadata = new MetadataSources(registry).buildMetadata();
        SessionFactory sessionFactory = metadata.buildSessionFactory();
        System.out.println("Connexion réussie !");
        
        // CREATE USERS ARTICLES IN MEMORY
        List<Utilisateur> uList = new ArrayList<>();
        List<Article> artList = new ArrayList<>();
        List<Annonce> annList = new ArrayList<>();
        Utilisateur u0 = new Utilisateur("Bob", "bob@mail.com", new ArrayList<>(), new ArrayList<>());
        Utilisateur u1 = new Utilisateur("Alice", "alice@mail.com", new ArrayList<>(), new ArrayList<>());
        uList.add(u0);
        uList.add(u1);
        Article art0 = new Article("The Once & Future King", "Arthouuur ! C'est la guerrrre !", u1, LocalDate.now());
        Article art1 = new Article("H2G2", "The Hitchhiker's Guide to the Galaxy", u1, LocalDate.now());
        artList.add(art0);
        artList.add(art1);
        Annonce ann0 = new Annonce("Annonce 1", "Ceci est une annonce", LocalDate.now(), (LocalDate.now().plusDays(1)), u0.getEmail(), BigDecimal.valueOf(1));
        Annonce ann1 = new Annonce("Annonce 2", "Ceci est une annonce", LocalDate.now(), (LocalDate.now().plusDays(2)), u0.getEmail(), BigDecimal.valueOf(2));
        annList.add(ann0);
        annList.add(ann1);

        // DB OPERATIONS
        UtilisateurDao uDao = new UtilisateurDao(sessionFactory);
        ArticleDao artDao = new ArticleDao(sessionFactory);
        AnnonceDao annDao = new AnnonceDao(sessionFactory);
        PublicationDao pubDao = new PublicationDao(sessionFactory);
        for (Utilisateur u : uList) uDao.creer(u);
        art0.getAuteur().getArticles().add(art0);
        art1.getAuteur().getArticles().add(art1);
        for (Article a : artList) artDao.creer(a);
        annDao.creer(ann0);
        annDao.creer(ann1);
        // u0.addAnnonce(ann0);
        // u0.addAnnonce(ann1);
        // for (Annonce a : annList) annDao.creer(a);

        for (Utilisateur u : uDao.tout()) System.out.println(u.toString());
        System.out.println("\n===== END MAIN =====\n");
        
        // RUN SERVER
        runServlet();

        // DISPLAY        
        // for (Utilisateur u : uDao.tout()) System.out.println(u.toString());
        // for (Article a : artDao.tout()) System.out.println(a.toString());
        // for (Article a : artDao.chercherParTitre("King")) System.out.println(a.toString());
        // System.out.println("------ QUERY RESULTS TITLE ------\n");
        // for (Article a : artDao.criteriaSeach(Optional.of("h2g2"), Optional.empty(), null, Optional.empty())) System.out.println(a.toString());
        // System.out.println("------ QUERY RESULTS AUTHOR ID ------\n");
        // for (Article a : artDao.criteriaSeach(Optional.empty(), Optional.of(u1.getId()),null, Optional.empty())) System.out.println(a.toString());
        // System.out.println("------ QUERY RESULTS TITLE + AUTHOR ID ------\n");
        // for (Article a : artDao.criteriaSeach(Optional.of("h2g2"), Optional.of(u1.getId()),null, Optional.empty())) System.out.println(a.toString());
        // System.out.println("------ QUERY RESULTS ORDER BY ------\n");
        // for (Article a : artDao.criteriaSeach(Optional.empty(), Optional.of(u1.getId()),"DESC", Optional.empty())) System.out.println(a.toString());
        // System.out.println("------ QUERY RESULTS LIMIT ------\n");
        // for (Article a : artDao.criteriaSeach(Optional.of("h2g2"), Optional.of(u1.getId()),"DESC",Optional.empty())) System.out.println(a.toString());

        // IHNERITANCE DB STRATEGY
        // DISPLAY
        // for (Article art : artDao.tout()) System.out.println(art.toString());
        // for (Annonce ann : annDao.tout()) System.out.println(ann.toString());

        // CLEAN UP DB
        // for(Publication p : pubDao.tout()) pubDao.supprimer(p.getId());
        // for(Utilisateur u : uDao.tout()) uDao.supprimer(u.getId());
    }

    private static void runServlet() throws Exception {
        ResourceConfig config = new ApiApplication();
        ServletHolder servlet = new ServletHolder(new ServletContainer(config));
        Server server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(server, "/");
        context.addServlet(servlet, "/*");
        server.start();
        server.join();
    }
}
