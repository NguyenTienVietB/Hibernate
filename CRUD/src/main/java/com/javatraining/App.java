package com.javatraining;

import java.util.List;
import java.util.Scanner;


import com.javatraining.model.*;

import com.javatraining.utils.HibernateUtils;
import jakarta.persistence.criteria.*;
import org.hibernate.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class App {

    public static void main(String[] args) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();

            Scanner scanner = new Scanner(System.in);

            boolean isRunning = true;

            while (isRunning) {
                System.out.println("Choose an operation:");
                System.out.println("1. Create");
                System.out.println("2. Read");
                System.out.println("3. Update");
                System.out.println("4. Delete");
                System.out.println("5. Exit");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        createUser(session, scanner);
                        break;
                    case 2:
                        readUser(session, scanner);
                        break;
                    case 3:
                        updateUser(session, scanner);
                        break;
                    case 4:
                        deleteUser(session, scanner);
                        break;
                    case 5:
                        isRunning = false;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            }

            session.getTransaction().commit();
        }
    }


    private static void createUser(Session session, Scanner scanner) {
        User newUser = new User();
        System.out.println("Enter fullname:");
        newUser.setFullname(scanner.next());
        System.out.println("Enter username:");
        newUser.setUsername(scanner.next());
        System.out.println("Enter password:");
        newUser.setPassword(scanner.next());
        newUser.setCreatedAt(new java.util.Date());
        newUser.setModifiedAt(new java.util.Date());
        session.persist(newUser);
    }

    private static void readUser(Session session, Scanner scanner) {
        System.out.println("Enter user ID to read:");
        int userId = scanner.nextInt();
        User retrievedUser = session.get(User.class, userId);
        if (retrievedUser != null) {
            System.out.println("Fullname: " + retrievedUser.getFullname());
            System.out.println("Username: " + retrievedUser.getUsername());
            // ... (Hiển thị các thông tin khác)
        } else {
            System.out.println("User not found.");
        }
    }

    private static void updateUser(Session session, Scanner scanner) {
        System.out.println("Enter user ID to update:");
        int userId = scanner.nextInt();
        User retrievedUser = session.get(User.class, userId);
        if (retrievedUser != null) {
            System.out.println("Enter new fullname:");
            retrievedUser.setFullname(scanner.next());
            session.update(retrievedUser);
        } else {
            System.out.println("User not found.");
        }
    }

    private static void deleteUser(Session session, Scanner scanner) {
        System.out.println("Enter user ID to delete:");
        int userId = scanner.nextInt();
        User retrievedUser = session.get(User.class, userId);
        if (retrievedUser != null) {
            session.delete(retrievedUser);
        } else {
            System.out.println("User not found.");
        }
    }
}