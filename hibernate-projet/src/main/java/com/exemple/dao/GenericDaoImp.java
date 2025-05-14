package com.exemple.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public abstract class GenericDaoImp{
    protected SessionFactory sessionFactory;

    public GenericDaoImp(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public void creer(Object entity) {
        if (entity != null){
            try(Session session = sessionFactory.openSession()){
                session.beginTransaction();
                // session.persist(entity);
                session.save(entity);
                session.getTransaction().commit();
            }
        }
    }

    public void mettreAJour(Object entity) {
        if (entity != null){
            try (Session session = sessionFactory.openSession()){
                session.beginTransaction();
                session.merge(entity);
                session.getTransaction().commit();
            }
        }
    }

    // public Object lire(String ObjectClass, Long id) {
    public Object lire(Long id) {
        if (id < 0) return null;
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            Object o = session.get(Object.class, id);
            session.getTransaction().commit();
            if (o != null) return o;
        }
        return null;
    }

    public void supprimer(Long id) {
        Object o;
        if (id >= 0){
            try(Session session = sessionFactory.openSession()){
                session.beginTransaction();
                o = lire(id);
                if (o != null){
                    session.remove(o);
                }
                session.getTransaction().commit();
            }
        }
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
