package com.exemple;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ArticleService {
    private SessionFactory sessionFactory;

    public ArticleService(SessionFactory sessionFactory){
        if (sessionFactory != null) this.sessionFactory = sessionFactory;
    }

    public void creer(Article article){
        if (article != null){
            try(Session session = sessionFactory.openSession()){
                session.beginTransaction();
                session.persist(article);
                session.getTransaction().commit();
            }
        }
    }

    public Article lire(Long id){
        if (id < 0) return null;
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            Article a = session.get(Article.class, id);
            session.getTransaction().commit();
            if (a != null) return a;
        }
        return null;
    }

    public void mettreAJour(Article article){
        if (article != null){
            try (Session session = sessionFactory.openSession()){
                session.beginTransaction();
                session.merge(article);
                session.getTransaction().commit();
            }
        }
    }
    
    public void supprimer(Long id){
        Article a;
        if (id >= 0){
            try(Session session = sessionFactory.openSession()){
                session.beginTransaction();
                a = lire(id);
                if (a != null) session.remove(lire(id));
                session.getTransaction().commit();
            }
        }
    }

}
