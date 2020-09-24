/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Common.Project;
import User.User;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Manan
 */
public class GestoreDatabase {

    private final String userTable = "CREATE TABLE IF NOT EXISTS user("
            + "oid int(11) NOT NULL AUTO_INCREMENT,"
            + "object BLOB NOT NULL, "
            + "PRIMARY KEY (oid)"
            + ")";

    private final String projectTable = "CREATE TABLE IF NOT EXISTS project("
            + "id_project VARCHAR(20) NOT NULL,"
            + "object BLOB NOT NULL, "
            + "PRIMARY KEY (id_project)"
            + ")";

    private final String friendTable = "CREATE TABLE IF NOT EXISTS friend("
            + "oid int(11) NOT NULL AUTO_INCREMENT,"
            + "id_user varchar(20) NOT NULL,"
            + "id_friend varchar(20) NOT NULL,"
            + "PRIMARY KEY (oid)"
            + ")";
    
    private final String notificationTable = "CREATE TABLE IF NOT EXISTS notifications("
            + "oid int(11) NOT NULL AUTO_INCREMENT,"
            + "id_user varchar(20) NOT NULL,"
            + "msg varchar(500) NOT NULL,"
            + "isnotify varchar(4) NOT NULL,"
            + "PRIMARY KEY (oid)"
            + ")";
    private final String roleTable = "CREATE TABLE IF NOT EXISTS roles("
            + "oid int(11) NOT NULL AUTO_INCREMENT,"
            + "id_user varchar(20) NOT NULL,"
            + "id_project VARCHAR(20) NOT NULL,"
            + "id_act VARCHAR(20) NOT NULL,"
            + "roleType VARCHAR(20) NOT NULL,"
            + "PRIMARY KEY (oid)"
            + ")";
    // database roles
    private final String writeInDBRoles = "INSERT INTO roles (id_user,id_project,id_act,roleType) VALUES (?,?,?,?)";
    private final String takeFromDBRoles = "SELECT * FROM roles";
    private final String deleteFromDBRoles = "DELETE FROM roles WHERE id_project=?";
    
    //database users
    private final String writeInDBUser = "INSERT INTO user (object) VALUES (?)";
    private final String takeFromDBUser = "SELECT object FROM user";
    //private final String deleteFromDBUser = "DELETE FROM user WHERE username=?";
    //private final String updateDBUser = "UPDATE user SET object=? WHERE username=?";

    //database friends
    private final String writeInDBFriend = "INSERT INTO friend (id_user,id_friend) VALUES (?,?)";
    private final String takeFromDBFriend = "SELECT * FROM friend";
    private final String deleteFromDBFriend = "DELETE FROM friend WHERE id_user=? and id_friend=?";
    //private final String updateDBFriend = "UPDATE friend SET object=? WHERE id_user=?";

    //database projects
    private final String writeInDBProject = "INSERT INTO project VALUES (?,?)";
    private final String takeFromDBProject = "SELECT object FROM project";
    private final String deleteFromDBProject = "DELETE FROM project WHERE id_project=?";
    private final String updateDBProject = "UPDATE project SET object=? WHERE id_project=?";
    
    //database notifications
    private final String writeInDBNotifications = "INSERT INTO notifications (id_user,msg,isnotify) VALUES (?,?,?)";
    private final String takeFromDBNotifications = "SELECT * FROM notifications where isnotify=?";
    //private final String deleteFromDBNotifications = "DELETE FROM notifications WHERE id_user=? and id_friend=?";
    private final String updateDBNotifications = "UPDATE notifications SET isnotify=? WHERE id_user=?";
    
    ConnectionPool connectionpool;

    private Connection getConnection() {

        Connection connection = null;
        try {
            //Class.forName(driver);
            //connection=DriverManager.getConnection(db,user,pass);
            connectionpool = ConnectionPool.getConnectionPool();
            connection = connectionpool.getConnection();

        } catch (ClassNotFoundException ex) {
            System.out.println("Errore connessione al driver");
            ex.printStackTrace();
        } catch (SQLException ex) {
            System.out.println("Errore connessione al database");
            ex.printStackTrace();
        }
        return connection;
    }

    private void release(Connection con) {
        connectionpool.releaseConnection(con);
    }

    public GestoreDatabase() {
        createTable();
    }

    private void createTable() {
        Connection con = null;
        try {
            con = getConnection();

            try (Statement stat = con.createStatement()) {
                stat.executeUpdate(userTable);
            }
            try (Statement stat = con.createStatement()) {
                stat.executeUpdate(projectTable);
            }
            try (Statement stat = con.createStatement()) {
                stat.executeUpdate(friendTable);
            }
            try (Statement stat = con.createStatement()) {
                stat.executeUpdate(notificationTable);
            }
            try (Statement stat = con.createStatement()) {
                stat.executeUpdate(roleTable);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (con != null) {
                release(con);
            }
        }
    }

    public void writeUserObject(Object object) {

        Connection con = getConnection();

        try {
            try (PreparedStatement pst = con.prepareStatement(writeInDBUser)) {
                pst.setObject(1, object);
                pst.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println("Errore creazione statement, class: writeUserObject");
            ex.printStackTrace();
        }
    }

    public void writeProjectObject(Object object, String id_project) {

        Connection con = getConnection();
        String support = "SELECT * FROM project WHERE id_project='" + id_project + "'";
        if (executeQuery(support) == true) {
            try {
                try (PreparedStatement pst = con.prepareStatement(updateDBProject)) {
                    pst.setObject(1, object);
                    pst.setString(2, id_project);
                    pst.executeUpdate();
                }
            } catch (SQLException ex) {
                System.out.println("Errore creazione statement, class: writeUserObject");
                ex.printStackTrace();
            }
        } else {
            try {
                try (PreparedStatement pst = con.prepareStatement(writeInDBProject)) {
                    pst.setString(1, id_project);
                    pst.setObject(2, object);
                    pst.executeUpdate();
                }
            } catch (SQLException ex) {
                System.out.println("Errore creazione statement, class: writeProjectObject");
                ex.printStackTrace();
            }
        }
    }

    public void writeFriend(String id_friend, String user) {

        Connection con = getConnection();

        try {
            try (PreparedStatement pst = con.prepareStatement(writeInDBFriend)) {
                pst.setString(1, user);
                pst.setString(2, id_friend);
                pst.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println("Errore creazione statement, class: writeFriendTable");
            ex.printStackTrace();
        }
    }
    
    public void writeRole(String id_user,String id_project,String Id_act,String roleType){
        
        Connection con = getConnection();

        try {
            try (PreparedStatement pst = con.prepareStatement(writeInDBRoles)) {
                pst.setString(1, id_user);
                pst.setString(2, id_project);
                pst.setString(3, Id_act);
                pst.setString(4, roleType);
                pst.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println("Errore creazione statement, class: writeRoleTable");
            ex.printStackTrace();
        }
    }
    
    public void deleteRole(String id_project){
        
         Connection con = getConnection();
        try {
            try (PreparedStatement pst = con.prepareStatement(deleteFromDBRoles)) {
                pst.setString(1, id_project);
                pst.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println("Errore creazione statement, class: deleteRoles");
            ex.printStackTrace();
        }
    }
    
    public void insertInNotifications(String id_user,String msg,String isnotify) {

        Connection con = getConnection();

        try {
            try (PreparedStatement pst = con.prepareStatement(writeInDBNotifications)) {
                pst.setString(1, id_user);
                pst.setString(2, msg);
                pst.setString(3, isnotify);
                pst.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println("Errore creazione statement, class: insertNotifications");
            ex.printStackTrace();
        }
    }
    
    public void updateNotifications(String id_user,String isnotify) {

        Connection con = getConnection();

        try {
            try (PreparedStatement pst = con.prepareStatement(updateDBNotifications)) {
                pst.setString(1, isnotify);
                pst.setString(2, id_user);
                pst.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println("Errore creazione statement, class: updateNotifications");
            ex.printStackTrace();
        }
    }

    public void deleteFriend(String user, String id_friend) {

        Connection con = getConnection();
        try {
            try (PreparedStatement pst = con.prepareStatement(deleteFromDBFriend)) {
                pst.setString(1, user);
                pst.setString(2, id_friend);
                pst.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println("Errore creazione statement, class: deleteFriendObject");
            ex.printStackTrace();
        }
    }

    public void deleteProjectObject(String id_project) {

        Connection con = getConnection();
        try {
            try (PreparedStatement pst = con.prepareStatement(deleteFromDBProject)) {
                pst.setString(1, id_project);
                pst.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println("Errore creazione statement, class: deleteProjectObject");
            ex.printStackTrace();
        }
    }

    //Restituisce null se l'utente non è presente, altrimenti restituisce l'oggetto.
    public ArrayList<User> readUserObject() {

        Connection con = getConnection();
        String name;
        User support = null;
        ArrayList<User> users = new ArrayList<>();
        try {
            try (PreparedStatement pst = con.prepareStatement(takeFromDBUser)) {
                ResultSet rs = pst.executeQuery();
                //strapolare l'oggetto dal database
                byte[] buf = null;
                ObjectInputStream objectIn = null;
                while (rs.next()) {
                    buf = null;
                    objectIn = null;
                    buf = rs.getBytes(1);
                    if (buf != null) {
                        objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));
                    }
                    support = (User) objectIn.readObject();
                    users.add(support);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        } catch (SQLException ex) {
            System.out.println("Errore creazione statement, class: readUserObject");
            ex.printStackTrace();
        }
        return users;
    }

    public ArrayList<Project> readProjectObject() {

        Connection con = getConnection();
        Object support = null;
        ArrayList<Project> projects = new ArrayList<>();
        try {
            PreparedStatement pst = con.prepareStatement(takeFromDBProject);
            ResultSet rs = pst.executeQuery();
            //strapolare l'oggetto dal database
            byte[] buf = null;
            ObjectInputStream objectIn = null;
            while (rs.next()) {

                buf = rs.getBytes(1);
                if (buf != null) {
                    objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));
                }
                support = objectIn.readObject();
                projects.add((Project) support);
            }
        } catch (SQLException ex) {
            System.out.println("Errore creazione statement, class: readProjectObject");
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return projects;
    }

    public List<List<String>> readFriend() {

        Connection con = getConnection();
        List<List<String>> friend = new ArrayList<>();
        try {
            PreparedStatement pst = con.prepareStatement(takeFromDBFriend);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                List<String> row = new ArrayList<>();
                row.add(rs.getString("id_user"));
                row.add(rs.getString("id_friend"));
                friend.add(row);
            }
        } catch (SQLException ex) {
            System.out.println("Errore creazione statement, class: readFriendObject");
            ex.printStackTrace();
        }
        return friend;
    }
    
    public List<List<String>> readNotifications() {

        Connection con = getConnection();
        List<List<String>> notify = new ArrayList<>();
        try {
            PreparedStatement pst = con.prepareStatement(takeFromDBNotifications);
            pst.setString(1, "0");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                List<String> row = new ArrayList<>();
                row.add(rs.getString("id_user"));
                row.add(rs.getString("msg"));
                row.add(rs.getString("isnotify"));
                notify.add(row);
            }
        } catch (SQLException ex) {
            System.out.println("Errore creazione statement, class: readnotifications");
            ex.printStackTrace();
        }
        return notify;
    }
    
    public List<List<String>> readRoles(){
        
         Connection con = getConnection();
        List<List<String>> roles = new ArrayList<>();
        try {
            PreparedStatement pst = con.prepareStatement(takeFromDBRoles);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                List<String> row = new ArrayList<>();
                row.add(rs.getString("id_user"));
                row.add(rs.getString("id_project"));
                row.add(rs.getString("id_act"));
                row.add(rs.getString("roleType"));
                roles.add(row);
            }
        } catch (SQLException ex) {
            System.out.println("Errore creazione statement, class: readRolesTable");
            ex.printStackTrace();
        }
        return roles;
    }

    private boolean executeQuery(String query) {
        boolean valido = false;
        try {
            Statement st = getConnection().createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                valido = true;
            }

        } catch (SQLException ex) {
            System.out.println("Erroe in executeQuery");
            ex.printStackTrace();
        } finally {
            return valido;
        }
    }
}
