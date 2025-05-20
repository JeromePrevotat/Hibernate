package com.exemple.dao;

import java.lang.reflect.Method;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public abstract class GenericDaoImp<T, ID>{
    private final Class<T> tType;
    protected SessionFactory sessionFactory;


    public GenericDaoImp(SessionFactory sessionFactory, Class<T> tType) {
        this.sessionFactory = sessionFactory;
        this.tType = tType;
    }
    
    public void creer(T entity) {
        if (entity != null){
            Method m = null;
            try(Session session = sessionFactory.openSession()){
                session.beginTransaction();
                if (entity.getClass().getName().equals("com.exemple.Article")){
                    m = entity.getClass().getMethod("getTitre");
                    System.out.println("GENERICDAO BEFORE PERSIST: " + m.invoke(entity));
                }
                session.persist(entity);
                if (entity.getClass().getName().equals("com.exemple.Article")) System.out.println("GENERICDAO AFTER PERSIST: " + m.invoke(entity));
                session.getTransaction().commit();
            } catch (Exception e){
                System.err.println(e.getMessage());
            }
        }
    }

    public void mettreAJour(T entity) {
        if (entity != null){
            try (Session session = sessionFactory.openSession()){
                session.beginTransaction();
                session.merge(entity);
                session.getTransaction().commit();
            }
        }
    }

    public T lire(ID id) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            T o = session.get(tType, id);
            session.getTransaction().commit();
            if (o != null) return o;
        }
        return null;
    }

    public void supprimer(ID id) {
        Object o;
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            o = lire(id);
            if (o != null){
                session.remove(o);
            }
            session.getTransaction().commit();
        }
    }

    public List<T> tout() {
        List<T> tList;
        try(Session session = sessionFactory.openSession()){
                session.beginTransaction();
                tList = session.createQuery("FROM " + tType.getName(), tType).list();
                session.getTransaction().commit();
            }
            return tList;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}


// package com.exemple.dao;

// import org.hibernate.Session;
// import org.hibernate.SessionFactory;

// public abstract class GenericDaoImp{
//     protected SessionFactory sessionFactory;

//     public GenericDaoImp(SessionFactory sessionFactory) {
//         this.sessionFactory = sessionFactory;
//     }
    
//     public void creer(Object entity) {
//         if (entity != null){
//             try(Session session = sessionFactory.openSession()){
//                 session.beginTransaction();
//                 session.persist(entity);
//                 // session.save(entity);
//                 session.getTransaction().commit();
//             }
//         }
//     }

//     public void mettreAJour(Object entity) {
//         if (entity != null){
//             try (Session session = sessionFactory.openSession()){
//                 session.beginTransaction();
//                 session.merge(entity);
//                 session.getTransaction().commit();
//             }
//         }
//     }

//     // public Object lire(String ObjectClass, Long id) {
//     public Object lire(Long id) {
//         if (id < 0) return null;
//         try(Session session = sessionFactory.openSession()){
//             session.beginTransaction();
//             Object o = session.get(Object.class, id);
//             session.getTransaction().commit();
//             if (o != null) return o;
//         }
//         return null;
//     }

//     public void supprimer(Long id) {
//         Object o;
//         if (id >= 0){
//             try(Session session = sessionFactory.openSession()){
//                 session.beginTransaction();
//                 o = lire(id);
//                 if (o != null){
//                     session.remove(o);
//                 }
//                 session.getTransaction().commit();
//             }
//         }
//     }

//     public SessionFactory getSessionFactory() {
//         return sessionFactory;
//     }

//     public void setSessionFactory(SessionFactory sessionFactory) {
//         this.sessionFactory = sessionFactory;
//     }

// }