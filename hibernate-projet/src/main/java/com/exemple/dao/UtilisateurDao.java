package com.exemple.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.exemple.Utilisateur;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class UtilisateurDao extends GenericDaoImp<Utilisateur, Long>{
    
    public UtilisateurDao(SessionFactory sessionFactory){
        super(sessionFactory, Utilisateur.class);
    }
    
    public Utilisateur trouverParEmail(String email){
        try(Session session = sessionFactory.openSession()){
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Utilisateur> cq = cb.createQuery(Utilisateur.class);
            Root<Utilisateur> root = cq.from(Utilisateur.class);
            cq.select(root)
                .where(cb.like(root.get("email"), email));
        }
        return null;
    }
}
