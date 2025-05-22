package com.exemple;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.exemple.dao.ArticleDao;

@TestInstance(Lifecycle.PER_CLASS)
public class ArticleDaoTest {
    private SessionFactory sessionFactory;
    private Session session;
    private ArticleDao artDao;
    private LocalDate datePublication;
    
    @Mock
    private Utilisateur u0;

    @InjectMocks
    private Article a0;
    private Article a1;
    
    @BeforeAll
    public void setUp(){
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure()
            .build();
        Metadata metadata = new MetadataSources(registry).buildMetadata();
        sessionFactory = metadata.buildSessionFactory();
    }

    @AfterAll
    public void tearDown(){
        sessionFactory.close();
    }

    @BeforeEach
    public void setUpEach(){
        session = sessionFactory.openSession();
        artDao = new ArticleDao(sessionFactory);
        a0 = new Article();
        datePublication = LocalDate.now();
        a1 = new Article("titre", "content", u0, datePublication);
    }

    @AfterEach
    public void tearDownEeach(){
        session.close();
        a0 = null;
        artDao = null;
    }

    @Test
    public void testCreerArticleNotNull(){
        artDao.creer(a0);
        assertNotNull(artDao.lire(a0.getId()));
    }

    @Test
    public void testCreerIdGreaterThanZero(){
        artDao.creer(a0);
        assertTrue(a0.getId() > 0);
    }

    @Test
    public void testCreerCorrectParams(){
        artDao.creer(a1);
        assertTrue(a1.getId() > 0
                    && a1.getTitre().equals("titre")
                    && a1.getContenu().equals("content")
                    && a1.getAuteur() == u0
                    && a1.getDatePublication() == datePublication);
    }


}
