/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Common.Project;
import User.Administrator;
import User.User;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Manan
 */
public interface ServerInterface extends Remote {

    public ArrayList<User> getUserList() throws RemoteException;

    public ArrayList<Project> getProjectList() throws RemoteException;

    public boolean register(User user) throws RemoteException;

    public Project createProject(String id,String name, Administrator admin) throws RemoteException;

    public ArrayList<Project> getUserProject(String id) throws RemoteException;

    public User validate(String username, String password) throws RemoteException;

    public void saveProject(Project project) throws RemoteException;
    
    public void addFriends(String user_id,String friend_id) throws RemoteException;
    
    public ArrayList<User> getFriends(String user_id) throws RemoteException;
    
    public ArrayList<String> getNotifications(String user_id) throws RemoteException;

    public void startProject(String project_id) throws RemoteException;
    
    public ArrayList<Project> getUserActivities(String user_id) throws RemoteException;
    
    public void completeActivity(String user_id,String proj_id,String act_id) throws RemoteException;
    
    public void deleteFriend(String user_id,String friend_id) throws RemoteException;
    
    public void deleteProject(String id_project) throws RemoteException;

    public int getUserNumber() throws RemoteException;

    public List<List<String>> getUserInfo(String user_id) throws RemoteException;
}
