package com.exemple.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.exemple.Article;

public class ArticleDao extends GenericDaoImp<Article, Long>{
    
    public ArticleDao(SessionFactory sessionFactory){
        super(sessionFactory, Article.class);
    }
    
    public List<Article> trouverParAuteur(long utilisateur_id){
        try(Session session = sessionFactory.openSession()){
            String hql = "FROM Article a WHERE a.auteur = :utilisateur_id";
            return session.createQuery(hql, Article.class)
                            .setParameter("utilisateur_id", utilisateur_id)
                            .getResultList();
        }
    }

    public List<Article> chercherParTitre(String motcle){
        try(Session session = sessionFactory.openSession()){
            String hql = "FROM Article a WHERE a.titre LIKE :motcle";
            return session.createQuery(hql, Article.class)
                            .setParameter("motcle", "%" + motcle + "%")
                            .getResultList();
        }
    }
}