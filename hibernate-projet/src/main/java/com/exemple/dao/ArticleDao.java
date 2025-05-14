package com.exemple.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.exemple.Article;

public class ArticleDao extends GenericDaoImp implements GenericDao<Article, Long>{
    
    public ArticleDao(SessionFactory sessionFactory){
        super(sessionFactory);
    }
    
    @Override
    public List<Article> tout() {
        List<Article> uList;
        try(Session session = sessionFactory.openSession()){
                session.beginTransaction();
                uList = session.createQuery("FROM Article", Article.class).list();
                session.getTransaction().commit();
            }
            return uList;
    }
}