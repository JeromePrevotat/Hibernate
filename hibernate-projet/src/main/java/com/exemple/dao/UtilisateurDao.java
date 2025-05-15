package com.exemple.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.exemple.Utilisateur;

public class UtilisateurDao extends GenericDaoImp<Utilisateur, Long>{
    
    public UtilisateurDao(SessionFactory sessionFactory){
        super(sessionFactory, Utilisateur.class);
    }
    
    public Utilisateur trouverParEmail(String email){
        try(Session session = sessionFactory.openSession()){
            
        }
        return null;
    }
}
