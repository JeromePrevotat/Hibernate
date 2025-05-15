package com.exemple.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.exemple.Article;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class ArticleDao extends GenericDaoImp<Article, Long>{
    // String HQL_CHERCHER_PAR_TITRE = "FROM Article a WHERE a.titre LIKE :motcle";
    
    public ArticleDao(SessionFactory sessionFactory){
        super(sessionFactory, Article.class);
    }
    

    // USING HQL
    // public List<Article> trouverParAuteur(long utilisateur_id){
    //     try(Session session = sessionFactory.openSession()){
    //         String hql = "FROM Article a WHERE a.auteur = :utilisateur_id";
    //         return session.createQuery(hql, Article.class)
    //                         .setParameter("utilisateur_id", utilisateur_id)
    //                         .getResultList();
    //     }
    // }

    // public List<Article> chercherParTitre(String motcle){
    //     try(Session session = sessionFactory.openSession()){
    //         String hql = HQL_CHERCHER_PAR_TITRE;
    //         return session.createQuery(hql, Article.class)
    //                         .setParameter("motcle", "%" + motcle + "%")
    //                         .getResultList();
    //     }
    // }

    // USING CRITERIA
    public List<Article> trouverParAuteur(long utilisateur_id){
            try(Session session = sessionFactory.openSession()){
                CriteriaBuilder cb = session.getCriteriaBuilder();
                CriteriaQuery<Article> cq = cb.createQuery(Article.class);
                Root<Article> root = cq.from(Article.class);
                cq.select(root)
                    .where(cb.equal(root.get("auteur"), utilisateur_id));
                return session.createQuery(cq)
                                .getResultList();
            }
        }

    public List<Article> chercherParTitre(String motcle){
        try(Session session = sessionFactory.openSession()){
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Article> cq = cb.createQuery(Article.class);
            Root<Article> root = cq.from(Article.class);
            cq.select(root)
                .where(cb.like(root.get("titre"), motcle));
            return session.createQuery(cq)
                            .getResultList();
        }
    }

    public List<Article> criteriaSeach(Optional<String> titre, Optional<Long> auteur_id, String order, Optional<Integer> limit){
        try(Session session = sessionFactory.openSession()){
            Query<Article> query = null;
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Article> cq = cb.createQuery(Article.class);
            Root<Article> root = cq.from(Article.class);
            // LIST DES ARGUMENTS OPTIONNELS
            List<Predicate> predicates = new ArrayList<>();
            // CHECK IF OPTIONAL ARGUMENT IS PASSED ON (isPresent = !=null // isEmpty = != "")
            if (titre.isPresent() && !titre.isEmpty() && titre.get() != null && !titre.get().equals("")){
                predicates.add(cb.like(cb.lower(root.get("titre")), "%" + titre.get().toLowerCase() + "%"));
            }
            if (auteur_id.isPresent() && !auteur_id.isEmpty()){
                predicates.add(cb.equal(root.get("auteur").get("id"), auteur_id.get()));
            }
            // ADD EACH PASSED ON ARGUMENT IF ANY TO THE REQUEST
            cq.select(root)
                .where(cb.and(predicates.toArray(Predicate[]::new)));
            // ORDER BY CLAUSE
            if (order != null && !order.isEmpty()){
                if (order.toLowerCase().equals("asc")) cq.orderBy(cb.asc(root.get("titre")));
                if (order.toLowerCase().equals("desc")) cq.orderBy(cb.desc(root.get("titre")));
            }
            // LIMIT CLAUSE WIP
            if (limit.isPresent() && !limit.isEmpty()){
                query = session.createQuery(cq);
                query.setFirstResult(0);
                query.setMaxResults(limit.get());
            }
            return (query != null) ? query.getResultList() : session.createQuery(cq).getResultList();
        }
    }
}