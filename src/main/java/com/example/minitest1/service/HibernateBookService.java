package com.example.minitest1.service;

import com.example.minitest1.model.Book;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
public class HibernateBookService implements IBookService {
    private static SessionFactory sessionFactory;
    private static EntityManager entityManager;

    static {
        try {
            sessionFactory = new Configuration()
                    .configure("hibernate.conf.xml")
                    .buildSessionFactory();
            entityManager = sessionFactory.createEntityManager();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Book> findAll() {
        String queryStr = "select c from Book as c";
        TypedQuery<Book> query = entityManager.createQuery(queryStr, Book.class);
        return query.getResultList();
    }


    @Override
    public Book findById(int id) {
        String queryStr = "select c from Book as c where c.id = :id";
        TypedQuery<Book> query = entityManager.createQuery(queryStr, Book.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public void save(Book book) {
        Transaction transaction = null;
        Book origin;
        if (book.getId() == 0) {
            origin = new Book();
        } else {
            origin = findById(book.getId());
        }
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            origin.setName(book.getName());
            origin.setAuthor(book.getAuthor());
            origin.setPrice(book.getPrice());
            origin.setImg(book.getImg());
            session.saveOrUpdate(origin);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void edit(Book book) {
        Transaction transaction = null;
        Book origin;
        if (book.getId() == 0) {
            origin = new Book();
        } else {
            origin = findById(book.getId());
        }
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            origin.setName(book.getName());
            origin.setAuthor(book.getAuthor());
            origin.setPrice(book.getPrice());
            origin.setImg(book.getImg());
            session.saveOrUpdate(origin);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void delete(int id) {
        Book book = findById(id);
        if (book != null) {
            Transaction transaction = null;
            try (Session session = sessionFactory.openSession()) {
                transaction = session.beginTransaction();
                session.remove(book);
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
    }
}
