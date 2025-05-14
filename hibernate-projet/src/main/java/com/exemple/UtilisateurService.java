package com.exemple;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class UtilisateurService {
    private SessionFactory sessionFactory;

    public UtilisateurService(SessionFactory sessionFactory){
        if (sessionFactory != null) this.sessionFactory = sessionFactory;
    }

    public void creer(Utilisateur utilisateur){
        if (utilisateur != null){
            try(Session session = sessionFactory.openSession()){
                session.beginTransaction();
                session.persist(utilisateur);
                session.getTransaction().commit();
            }
        }
    }

    public Utilisateur lire(Long id){
        if (id < 0) return null;
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            Utilisateur u = session.get(Utilisateur.class, id);
            session.getTransaction().commit();
            if (u != null) return u;
        }
        return null;
    }

    public void mettreAJour(Utilisateur utilisateur){
        if (utilisateur != null){
            try (Session session = sessionFactory.openSession()){
                session.beginTransaction();
                session.merge(utilisateur);
                session.getTransaction().commit();
            }
        }
    }
    
    public void supprimer(Long id){
        Utilisateur u;
        if (id >= 0){
            try(Session session = sessionFactory.openSession()){
                session.beginTransaction();
                u = lire(id);
                if (u != null){
                    System.out.println("USER TO DELETE: " + u.toString());
                    session.remove(u);
                }
                session.getTransaction().commit();
            }
        }
    }

}
