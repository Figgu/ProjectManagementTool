package com.projectmanagementtoolapp.pkgData;

import android.content.Intent;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.Date;
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
    private ArrayList<User> users;
    private ArrayList<Project> projects;
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
            user.setUsername(rs.getString("username"));
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
        PreparedStatement statement = conn.prepareStatement("insert into user03 (username, password, email) values (?, ?, ?)");
        statement.setString(1, username);
        statement.setString(2, password);
        statement.setString(3, email);
        statement.executeQuery();
        statement.close();
    }

    public void changeUser(String username, String password, String email, String Userid) throws ClassNotFoundException, SQLException {
        PreparedStatement statement = conn.prepareStatement("update user03 set username = ?, password = ?, email = ? where userid = ? ");
        statement.setString(1, username);
        statement.setString(2, password);
        statement.setString(3, email);
        statement.setInt(4, Integer.parseInt(Userid));
        statement.executeUpdate();
        statement.close();
    }

    //Only called by the async task
    public void insertProject(Project project) throws ClassNotFoundException, SQLException {
        PreparedStatement statement = conn.prepareStatement("insert into project03 (name, description, projectbeginn) values (?, ?, ?)");
        statement.setString(1, project.getName());
        statement.setString(2, project.getDescription());
        statement.setDate(3, new Date(project.getStartDate().getTime()));
        statement.executeQuery();
        conn.commit();;
        statement.close();
    }

    //Only called by async task
    public void insertUserInProject(int userID, int projectID) throws ClassNotFoundException, SQLException {
        PreparedStatement statement = conn.prepareStatement("insert into USERISINPROJECTWITHROLE03 (UserID, ProjectID) values (?, ?)");
        statement.setInt(1, userID);
        statement.setInt(2, projectID);
        statement.executeQuery();
        statement.close();
    }

    //Only called by async task
    public void getAllProjects() throws SQLException {
        PreparedStatement statement = conn.prepareStatement("select * from project03");
        ResultSet rs = statement.executeQuery();
        projects = new ArrayList<>();

        while(rs.next()) {
            Project project = new Project();
            project.setProjectID(rs.getInt("projectID"));
            project.setName(rs.getString("name"));
            project.setDescription(rs.getString("description"));

            projects.add(project);
        }

        System.out.println("projects: " + projects);
        statement.close();
        rs.close();
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

    public void editPicture(String UserID, String path) throws SQLException, FileNotFoundException {

        PreparedStatement pstmt = conn.prepareStatement("update user03 set ProfilePicture = ? where id = ?");
        File blob = new File(path);
        FileInputStream in = new FileInputStream(blob);
        // the cast to int is necessary because with JDBC 4 there is
        // also a version of this method with a (int, long)
        // but that is not implemented by Oracle
        pstmt.setBinaryStream(1, in, (int)blob.length());
        pstmt.setInt(2, Integer.parseInt(UserID));  // set the PK value
        pstmt.executeUpdate();
        conn.commit();
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

    public Project getProjectByName(String name) {
        Iterator<Project> it = projects.iterator();
        Project project = null;

        while (it.hasNext()) {
            Project currentProject = it.next();
            if (currentProject.getName().equals(name)) {
                project = currentProject;
            }
        }

        return project;
    }

    public void setCurrentUser(User user) {
        currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}