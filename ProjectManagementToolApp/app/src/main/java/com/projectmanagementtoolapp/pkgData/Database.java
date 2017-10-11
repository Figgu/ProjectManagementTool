package com.projectmanagementtoolapp.pkgData;

import android.content.Intent;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import static java.sql.Connection.TRANSACTION_SERIALIZABLE;

/**
 * Created by alexk on 09.10.2017.
 */

public class Database {
    private static Database db;
    private String user = "d5b03";
    private String pwd = "d5b";
    private static ArrayList<User> users;
    private Connection conn;
    private User currentUser;       //Current logged in user

    public static Database getInstance() {
        if (db == null) {
            db = new Database();
        }
        return db;
    }

    //Only called by the async task
    public void openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        conn = createConnection();
        conn.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
}

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:oracle:thin:@192.168.128.152:1521:ora11g", user, pwd);
    }

    //Only called by the async task
    public void selectAllUsers() throws ClassNotFoundException, SQLException {
        PreparedStatement statement = conn.prepareStatement("select * from user03");
        ResultSet rs = statement.executeQuery();
        users = new ArrayList<>();

        while(rs.next()) {
            User user = new User();
            user.setUserID(rs.getInt("userID"));
            user.setUsername(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));

            users.add(user);
        }

        System.out.println("users: " + users);
        statement.close();
        rs.close();
    }

    //Only called by the async task
    public void insertUser(String username, String password, String email) throws ClassNotFoundException, SQLException {
        PreparedStatement statement = conn.prepareStatement("insert into user03 (name, password, email) values (?, ?, ?)");
        statement.setString(1, username);
        statement.setString(2, password);
        statement.setString(3, email);
        statement.executeQuery();
        statement.close();
    }

    public boolean loginCorrect(User user) {
        boolean retVal = false;
        Iterator<User> it = users.iterator();

        while (it.hasNext()) {
            User currentUser = it.next();
            if (currentUser.getUsername().equals(user.getUsername()) && currentUser.getPassword().equals(user.getPassword())) {
                retVal = true;
            }
        }

        return retVal;
    }

    public boolean usernameExists(String username) {
        boolean retVal = false;
        Iterator<User> it = users.iterator();

        while (it.hasNext()) {
            if (it.next().getUsername().equals(username))
                retVal = true;
        }

        return retVal;
    }

    public boolean emailExists(String email) {
        boolean retVal = false;
        Iterator<User> it = users.iterator();

        while (it.hasNext()) {
            if (it.next().getEmail().equals(email))
                retVal = true;
        }

        return retVal;
    }

    public User getUserByUsername (String username) {
        User user = null;
        Iterator<User> it = users.iterator();

        while (it.hasNext()) {
            User currentUser = it.next();
            if (currentUser.getUsername().equals(username)) {
                user = currentUser;
            }
        }

        return user;
    }

    public User getUserByEmail (String email) {
        User user = null;
        Iterator<User> it = users.iterator();

        while (it.hasNext()) {
            User currentUser = it.next();
            if (currentUser.getEmail().equals(email)) {
                user = currentUser;
            }
        }

        return user;
    }

    public void setCurrentUser(User user) {
        currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}