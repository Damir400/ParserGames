package com.damir.parsing.service;

import com.damir.parsing.ICrudable;
import com.damir.parsing.entity.Games;
import com.damir.parsing.entity.Tags;
import com.fasterxml.classmate.AnnotationConfiguration;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import java.util.List;

public class Repository <T extends ICrudable> {
    SessionFactory factory;
    public Repository(Class<T> iCrudableClass){
         factory = new Configuration().
                configure("hibernate.cfg.xml").addAnnotatedClass(iCrudableClass).
                buildSessionFactory();
    }

    public int addItems(List<T> entities){
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        for(T entity : entities){
            session.persist(entity);
        }
        session.getTransaction().commit();
        return entities.size();
    }

    public int addItems(T[] entities){
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        for(T entity : entities){
            session.persist(entity);
        }
        session.getTransaction().commit();
        return entities.length;
    }

    public List<Games> getItems(List<Games> entities){
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        entities = session.createQuery("SELECT a FROM Games a", Games.class).getResultList();
        session.getTransaction().commit();
         return entities;
    }

    public List<Games> getItems(int count, int offset){
        List<Games> entities;
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT a FROM Games a")
                .setFirstResult(offset)
                .setMaxResults(count);
        entities = query.getResultList();
        session.getTransaction().commit();
        return entities;
    }

    public Long getCountItems(){
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT COUNT (a) FROM Games a");
        Long res = (Long) query.uniqueResult();
        session.getTransaction().commit();
        return res;
    }

    public void delItems(){
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("DELETE FROM Games");
        query.executeUpdate();
        session.getTransaction().commit();
    }

    public int addTags(T[] entities){
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        for(T entity : entities){
            session.persist(entity);
        }
        session.getTransaction().commit();
        return entities.length;
    }

    public List<Games> searchItems(String name){
        List<Games> entities;
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        //Query query = session.createQuery("SELECT a FROM games a WHERE name ILIKE '%" + name + "%'");

        Criteria criteria = session.createCriteria(Games.class);
        criteria.add(Restrictions.ilike("name",name, MatchMode.ANYWHERE));
        List<Games> result = criteria.list();

        //entities = query.getResultList();
        session.getTransaction().commit();
        return result;
    }
}
