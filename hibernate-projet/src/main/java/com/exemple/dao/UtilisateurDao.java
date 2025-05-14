package com.exemple.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.exemple.Utilisateur;

public class UtilisateurDao extends GenericDaoImp implements GenericDao<Utilisateur, Long>{
    
    public UtilisateurDao(SessionFactory sessionFactory){
        super(sessionFactory);
    }
    
    @Override
    public List<Utilisateur> tout() {
        List<Utilisateur> uList;
        try(Session session = sessionFactory.openSession()){
                session.beginTransaction();
                uList = session.createQuery("FROM Utilisateur", Utilisateur.class).list();
                session.getTransaction().commit();
            }
            return uList;
    }
}
