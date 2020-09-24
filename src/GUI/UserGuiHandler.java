/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Common.Project;
import Interface.ServerInterface;
import User.Administrator;
import User.User;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Manan
 */
public class UserGuiHandler {
    
    private ServerInterface srv; 
    private static UserGuiHandler instance = null;
    private UserGuiHandler(){
        try{
                Registry reg = LocateRegistry.getRegistry(1234);
                srv = (ServerInterface) reg.lookup("SocialProject");
                
        }catch (RemoteException | NotBoundException e){
            e.printStackTrace();
        }
    }
    
    public static UserGuiHandler getInstance(){
        if(instance == null){
            instance = new UserGuiHandler();
        }
        return instance;
    }
    
    public User login(String username,String password) throws RemoteException{
        
        return srv.validate(username,password);
    }
    
    public boolean registerUser(User user) throws RemoteException{
        return srv.register(user);
    }
    
    public Project newProject(String id,String name,Administrator admin) throws RemoteException{
        return srv.createProject(id,name, admin);
    }
    
    public ArrayList<User> getUserList() throws RemoteException{
        return srv.getUserList();
    }

    public ArrayList<Project> showProject(String id) throws RemoteException {
        return srv.getUserProject(id);
    }
    
    public void addProject(Project project) throws RemoteException{
        srv.saveProject(project);
    }
    
    public void newFriend(String user_id,String friend_id) throws RemoteException{
        srv.addFriends(user_id, friend_id);
    }
    
    public ArrayList<User> getFriendsList(String user_id) throws RemoteException{
        return srv.getFriends(user_id);
    }
    
    public ArrayList<String> getUserNotifications(String user_id) throws RemoteException{
        return srv.getNotifications(user_id);
    }

    public void startProject(String project_id) throws RemoteException {
        srv.startProject(project_id);
    }
    
    public void updateActivityState(String user_id,String proj_id,String act_id) throws RemoteException{
        srv.completeActivity(user_id, proj_id, act_id);
    }
    
    public ArrayList<Project> getActivities(String user_id) throws RemoteException{
        return srv.getUserActivities(user_id);
    }
    
    public void CompleteActivity(String user_id,String proj_id,String act_id) throws RemoteException{
        srv.completeActivity(user_id, proj_id, act_id);
    }
    
    public void deleteFriends(String user_id,String friend_id) throws RemoteException{
        srv.deleteFriend(user_id, friend_id);
    }
    
    public void deleteProjects(String id_project) throws RemoteException{
        srv.deleteProject(id_project);
    }

    public List<List<String>> getUserDestails(String user_id) throws RemoteException{
        return srv.getUserInfo(user_id);
    }
}
