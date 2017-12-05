package com.projectmanagementtoolapp.pkgData;

import android.content.Intent;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    private ArrayList<Project> myProjects;
    private ArrayList<Role> roles;
    private ArrayList<Right> rights;
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

    //212.152.179.117
    //192.168.128.152
    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:oracle:thin:@212.152.179.117:1521:ora11g", user, pwd);
    }

    //Only called by the async task
    public void selectAllUsers() throws ClassNotFoundException, SQLException {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("select * from user03");
        users = new ArrayList<>();

        while(rs.next()) {
            User user = new User();
            user.setUserID(rs.getInt("userID"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));
            user.setProfilePicture(rs.getBytes("profilepicture"));
            users.add(user);
        }

        System.out.println("users: " + users);
        statement.close();
        rs.close();
    }

    //Only called by async task
    public void selectAllUsersFromProject(Project project) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("select u.userid, u.username, u.password, u.email, u.profilepicture from userisinprojectwithrole03 p join user03 u on p.userid = u.userid where p.projectid = ?");
        statement.setInt(1, project.getProjectID());
        ResultSet rs = statement.executeQuery();
        ArrayList<User> usersOfProject = new ArrayList<>();

        while(rs.next()) {
            User user = new User();
            user.setUserID(rs.getInt("userID"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));
            user.setProfilePicture(rs.getBytes("profilepicture"));
            usersOfProject.add(user);
        }

        project.setContributors(usersOfProject);
        int indexOfProject = getIndexOfProject(project.getProjectID())-1;
        myProjects.set(indexOfProject, project);           //Set the user to the project
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

    //Only called by the async task
    public void insertUserWithPicture(String username, String password, String email, byte[] picture) throws ClassNotFoundException, SQLException {
        //InputStream targetStream = new ByteArrayInputStream(picture);
        PreparedStatement statement = conn.prepareStatement("insert into user03 (username, password, email, profilepicture) values (?, ?, ?, ?)");
        statement.setString(1, username);
        statement.setString(2, password);
        statement.setString(3, email);
        statement.setBytes(4, picture);
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

    public void updateProject(Project project) throws ClassNotFoundException, SQLException {
        PreparedStatement statement = conn.prepareStatement("update project03 set name = ?, description = ?, projectbeginn = ? where projectid = ? ");
        statement.setString(1, project.getName());
        statement.setString(2, project.getDescription());
        statement.setDate(3, new Date(project.getStartDate().getTime()));
        statement.setInt(4, project.getProjectID());
        statement.executeUpdate();
        statement.close();

        int indexOfProject = getIndexOfProject(project.getProjectID())-1;
        myProjects.set(indexOfProject, project);
    }

    //Only called by the async task
    public void insertProject(Project project) throws ClassNotFoundException, SQLException {
        PreparedStatement statement = conn.prepareStatement("insert into project03 (name, description, projectbeginn) values (?, ?, ?)");
        statement.setString(1, project.getName());
        statement.setString(2, project.getDescription());
        statement.setDate(3, new Date(project.getStartDate().getTime()));
        statement.executeQuery();
        conn.commit();
        statement.close();
    }

    //Only called by the async task
    public void insertSprint(Sprint sprint, Project project) throws ClassNotFoundException, SQLException {
        PreparedStatement statement = conn.prepareStatement("insert into sprint03 (projectID, startdate, endDate) values (?, ?, ?)");
        statement.setInt(1, project.getProjectID());
        statement.setDate(2, new Date(sprint.getStartDate().getTime()));
        statement.setDate(3, new Date(sprint.getEndDate().getTime()));
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
    public void deleteUsersFromProject(int projectID) throws ClassNotFoundException, SQLException {
        PreparedStatement statement = conn.prepareStatement("delete from USERISINPROJECTWITHROLE03 where projectID = ?");
        statement.setInt(1, projectID);
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

    //Only called by async task
    public void getMyProjects(User user) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("select po.projectid, po.name, po.description, po.projectbeginn from userisinprojectwithrole03 p join user03 u on u.userid = p.userid join project03 po on p.projectid = po.projectid where u.userid = ?");
        statement.setInt(1, user.getUserID());
        ResultSet rs = statement.executeQuery();
        myProjects = new ArrayList<>();

        while(rs.next()) {
            Project project = new Project();
            project.setProjectID(rs.getInt("projectID"));
            project.setName(rs.getString("name"));
            project.setDescription(rs.getString("description"));
            project.setStartDate(rs.getDate("projectbeginn"));

            myProjects.add(project);
        }

        System.out.println("projects: " + projects);
        statement.close();
        rs.close();
    }

    //Only called by async task
    public void getAllSprints(Project project) throws SQLException {
        System.out.println("id in select sprints: " + project.getProjectID());
        PreparedStatement statement = conn.prepareStatement("select * from sprint03 where projectid = ?");
        statement.setInt(1, project.getProjectID());
        ResultSet rs = statement.executeQuery();
        ArrayList<Sprint> sprints = new ArrayList<>();

        while(rs.next()) {
            Sprint sprint = new Sprint();
            sprint.setSprintID(rs.getInt("sprintID"));
            sprint.setProject(getProjectByID(rs.getInt("projectID")));
            sprint.setStartDate(rs.getDate("startDate"));
            sprint.setEndDate(rs.getDate("endDate"));

            sprints.add(sprint);
        }

        project.setSprints(sprints);
        int indexOfProject = getIndexOfProject(project.getProjectID())-1;
        myProjects.set(indexOfProject, project);           //Set the sprints to the project
        System.out.println("sprints in select: " + myProjects.get(indexOfProject).getSprints());
        statement.close();
        rs.close();
    }

    private int getIndexOfProject(int ID) {
        Iterator<Project> it = myProjects.iterator();
        int counter = 0;
        boolean flag = true;

        while (it.hasNext() && flag) {
            Project currentProject = it.next();
            System.out.println("currentProject in get index: " + currentProject);

            if (currentProject.getProjectID() == ID) {
                flag = false;
            }

            counter++;
            System.out.println("index of project:" + counter);
        }

        return counter;
    }

    public void editPicture(String UserID, String path) throws SQLException, FileNotFoundException {

        PreparedStatement pstmt = conn.prepareStatement("update user03 set ProfilePicture = ? where userid = ?");
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
            if (it.next().getEmail().equals(email) )
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
        boolean flag = true;

        while (it.hasNext()) {
            Project currentProject = it.next();
            if (currentProject.getName().equals(name) && flag) {
                project = currentProject;
                flag = false;
            }

            System.out.println("name: " + currentProject.getName() + " id: " + currentProject.getProjectID());
        }

        return project;
    }

    public Project getProjectByID(int id) {
        Iterator<Project> it = myProjects.iterator();
        Project project = null;
        boolean flag = true;

        while (it.hasNext()) {
            Project currentProject = it.next();
            if (currentProject.getProjectID() == id && flag) {
                project = currentProject;
                flag = false;
            }

            System.out.println("name: " + currentProject.getName() + " id: " + currentProject.getProjectID());
        }

        return project;
    }

    public void setCurrentUser(User user) {
        currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Project> getProjects() {
        return projects;
    }

    public void insertRole(Role role) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("insert into role03 (name, description, ISUNIQUE) values (?, ?, ?)");
        statement.setString(1, role.getName());
        statement.setString(2, role.getDescription());
        statement.setString(3, String.valueOf(role.isUnique()));
        statement.executeQuery();
        statement.close();
    }

    public ArrayList<Role> getAllRoles() throws SQLException {
        PreparedStatement statement = conn.prepareStatement("select * from role03");
        ResultSet rs = statement.executeQuery();
        roles = new ArrayList<>();

        while(rs.next()) {
            Role role = new Role();
            role.setRoleID(rs.getInt("roleID"));
            role.setName(rs.getString("name"));
            role.setDescription(rs.getString("description"));
            role.setUnique(Boolean.parseBoolean(rs.getString("isunique")));
            roles.add(role);
        }
        statement.close();
        rs.close();
        return roles;
    }

    public void removeRole(Role role) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("delete from role03 where roleid = ?");
        statement.setInt(1, role.getRoleID());
        ResultSet rs = statement.executeQuery();
        statement.close();
        rs.close();
    }

    public void deleteProject(Project project) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("delete from project03 where projectid = ?");
        statement.setInt(1, project.getProjectID());
        ResultSet rs = statement.executeQuery();
        statement.close();
        rs.close();

        myProjects.remove(project);
        projects.remove(project);
    }

    public ArrayList<Role> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<Role> roles) {
        this.roles = roles;
    }

    public ArrayList<Project> getMyProjects() {
        return myProjects;
    }
}