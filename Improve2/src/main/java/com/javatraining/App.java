package com.javatraining;

import java.util.List;


import com.javatraining.model.*;

import com.javatraining.utils.HibernateUtils;
import jakarta.persistence.Query;
import org.hibernate.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    public static void main(String[] args) {
        try (Session session = HibernateUtils.getSessionFactory().openSession();) {
            // Begin a unit of work
            session.beginTransaction();

            User user1 = new User();
            user1.setFullname("Alice Johnson");
            user1.setUsername("alice");
            user1.setPassword("hashed_password");
            user1.setCreatedAt(new java.util.Date());
            user1.setModifiedAt(new java.util.Date());

            UserProfile userProfile1 = new UserProfile();
            userProfile1.setAddress("123 Main St");
            userProfile1.setGender(1);

            user1.setUserProfile(userProfile1);
            session.persist(user1);

            User user2 = new User();
            user2.setFullname("Bob Smith");
            user2.setUsername("bob");
            user2.setPassword("hashed_password");
            user2.setCreatedAt(new java.util.Date());
            user2.setModifiedAt(new java.util.Date());

            UserProfile userProfile2 = new UserProfile();
            userProfile2.setAddress("456 Main St");
            userProfile2.setGender(2);
            user2.setUserProfile(userProfile2);

            session.persist(user2);

            User user3 = new User();
            user3.setFullname("Eva Williams");
            user3.setUsername("eva");
            user3.setPassword("hashed_password");
            user3.setCreatedAt(new java.util.Date());
            user3.setModifiedAt(new java.util.Date());

            UserProfile userProfile3 = new UserProfile();
            userProfile3.setAddress("789 Main St");
            userProfile3.setGender(1);

            user3.setUserProfile(userProfile3);

            session.persist(user3);


            // Chèn dữ liệu vào bảng Role
            Role role1 = new Role();
            role1.setName("USER");
            session.persist(role1);

            Role role2 = new Role();
            role2.setName("EDITOR");
            session.persist(role2);

            Role role3 = new Role();
            role3.setName("GUEST");
            session.persist(role3);

            // Chèn dữ liệu vào bảng Category
            Category category1 = new Category();
            category1.setName("Science");
            session.persist(category1);

            Category category2 = new Category();
            category2.setName("Art");
            session.persist(category2);

            // Chèn dữ liệu vào bảng Post
            Post post1 = new Post();
            post1.setTitle("Introduction to SQL");
            post1.setContent("This is a post about SQL basics.");
            post1.setUser(user1);
            post1.setCategory(category1);
            session.persist(post1);

            Post post2 = new Post();
            post2.setTitle("The Art of Painting");
            post2.setContent("Exploring different painting techniques.");
            post2.setUser(user2);
            post2.setCategory(category2);
            session.persist(post2);




            // FROM
            String hql = "FROM Post";
            List<Post> posts = session.createQuery(hql, Post.class).list();
            for (Post post : posts) {
                System.out.println(post);
            }

            //SELECT
            String hql1 = "SELECT u.username FROM User u WHERE u.id = :id";
            String username = session.createQuery(hql1, String.class).setParameter("id", 1L).uniqueResult();

            //WHERE
            String hql2 = "FROM User u WHERE month(u.createdAt) = month(sysdate())";
            List<User> users2 = session.createQuery(hql2, User.class).list();

            //ORDER BY
            String hql3 = "FROM User u WHERE month(u.createdAt) = month(sysdate()) ORDER BY u.createdAt DESC";
            List<User> users3 = session.createQuery(hql3, User.class).list();

            //GROUP BY
            String hql8 = "SELECT u.username, count(u.id) FROM User u GROUP BY u.username";
            List<Object[]> users4 = session.createQuery(hql8, Object[].class).list();


            //UPDATE
           // String hql4 = "UPDATE User SET fullname = 'Alice', password = 'password' WHERE id = 1L";
            //session.createQuery(hql4).executeUpdate();


            //INSERT
            String hql6 = "INSERT INTO User (fullname, username, password) VALUES ('John', 'john', 'hashed_password')";
            session.createQuery(hql6).executeUpdate();

            //DELETE
           // String hql5 = "DELETE FROM User WHERE id = 4L";
            //session.createQuery(hql5).executeUpdate();

            //JOIN
            String hql7 = "FROM User u LEFT JOIN u.userProfile p WHERE u.id = :id";
            List<Object[]> users = session.createQuery(hql7, Object[].class).setParameter("id", 3L).list();



            session.getTransaction().commit();
            for (Post post : posts) {
                System.out.println(post);
            }
            System.out.println("--------------------");

            System.out.println("Username with id 1: " + username);

            System.out.println("--------------------");
            System.out.println("Users created this month: " + users2.size());
            System.out.println("--------------------");
            System.out.println("Users created this month (order): ");
           for (User user : users3) {
                System.out.println(user);
            }

            System.out.println("--------------------");
            for (Object[] user : users) {
                System.out.println("Username: " + user[0]);
                System.out.println("Address: " + user[1]);
            }


        }


    }
}