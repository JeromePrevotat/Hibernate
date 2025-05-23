package com.exemple;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.exemple.dao.ArticleDao;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/articles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ArticleResource {
    private final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    private final ArticleDao articleDao = new ArticleDao(sessionFactory);

    @GET
    public List<Article> getAll(){
        return articleDao.tout();
    }

    @GET
    @Path("/{id}")
    public Article getById(@PathParam("id") long id){
        return articleDao.lire(id);
    }

    @POST
    public void create(Article article){
        articleDao.creer(article);
    }

    @PUT
    @Path("/{id}")
    public void update(@PathParam("id") long id, Article article){
        article.setId(id);
        articleDao.mettreAJour(article);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") long id){
        articleDao.supprimer(id);
    }
}
