package com.exemple;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.exemple.dao.UtilisateurDao;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/utilisateurs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UtilisateurResource {
    private final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    private final UtilisateurDao utilisateurDao = new UtilisateurDao(sessionFactory);

    @GET
    public List<Utilisateur> getAll(){
        return utilisateurDao.tout();
    }

    @GET
    @Path("/{id}")
    public Utilisateur getById(@PathParam("id") long id){
        return utilisateurDao.lire(id);
    }

    @POST
    public void create(Utilisateur utilisateur){
        utilisateurDao.creer(utilisateur);
    }

    @PUT
    @Path("/{id}")
    public void update(@PathParam("id") long id, Utilisateur utilisateur){
        utilisateur.setId(id);
        utilisateurDao.mettreAJour(utilisateur);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") long id){
        utilisateurDao.supprimer(id);
    }
}
