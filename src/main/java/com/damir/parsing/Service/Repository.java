package com.damir.parsing.Service;


import com.damir.parsing.Entity.Games;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;


public class Repository <T>{
    SessionFactory factory;
    public Repository(){
         factory = new Configuration().
                configure("hibernate.cfg.xml").addAnnotatedClass(Games.class).
                buildSessionFactory();

    }


    public int addItems(List<T> entities){
//        try {
            Session session = factory.getCurrentSession();
            session.beginTransaction();
            for(T entity : entities){
                session.persist(entity);
            }
            session.getTransaction().commit();
//        }
//        finally {
//            factory.close();
//        }

        return entities.size();
    }

    public int addItems(T[] entities){
//        try {
            Session session = factory.getCurrentSession();
            session.beginTransaction();
            for(T entity : entities){
                session.persist(entity);
            }
            session.getTransaction().commit();
//        }
//        finally {
//            factory.close();
//        }

        return entities.length;
    }

    public List<Games> getItems(List<Games> entities){
//        try {
            Session session = factory.getCurrentSession();
            session.beginTransaction();

            entities = session.createQuery("SELECT a FROM Games a", Games.class).getResultList();
            session.getTransaction().commit();
//            for(int i=0; i<entities.size();i++){
//                session.get(entities.get(i).getClass(),entities.get(i));
//            }
//            session.getTransaction().commit();
//        }
//        catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//        finally {
//            factory.close();
//        }

         return entities;
    }

    public void delItems(){
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("DELETE FROM Games");
        query.executeUpdate();
        session.getTransaction().commit();
    }
}
