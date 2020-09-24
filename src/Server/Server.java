/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Common.Activity;
import Common.Project;
import Common.State;
import Interface.ServerInterface;
import User.Administrator;
import User.Executioner;
import User.Responsible;
import User.User;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author Manan
 */
public class Server extends UnicastRemoteObject implements ServerInterface {

    private ArrayList<User> usersList;
    private ArrayList<Project> projects;
    private List<List<String>> friends;
    private List<List<String>> notifications;
    private List<List<String>> roleList;
    private GestoreDatabase db;

    public Server() throws RemoteException {
        super();
        usersList = new ArrayList<>();
        projects = new ArrayList<>();
        friends = new ArrayList<>();
        notifications = new ArrayList<>();
        roleList = new ArrayList<>();

        db = new GestoreDatabase();
        initialize();
    }

    private void initialize() {
        // usersList = db.readUserObject();
        if (db.readUserObject() != null) {
            for (User u : db.readUserObject()) {
                usersList.add(u);
            }
        }
        if (db.readProjectObject() != null) {
            for (Project pro : db.readProjectObject()) {
                projects.add(pro);
            }
        }
        if (db.readFriend() != null) {
            for (List<String> list : db.readFriend()) {
                friends.add(list);
            }
        }
        if (db.readNotifications() != null) {
            for (List<String> list : db.readNotifications()) {
                notifications.add(list);
            }
        }
        if (db.readRoles() != null) {
            for (List<String> list : db.readRoles()) {
                roleList.add(list);
            }
        }
    }

    @Override
    public User validate(String username, String password) {

        for (User user : usersList) {
            if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equalsIgnoreCase(password)) {
                System.out.println("");
                System.out.print(user.getId() + " " + user.getFirstName() + " " + user.getLastName() + " has logged");
                System.out.println("");
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean register(User user) throws RemoteException {

        for (User u : usersList) {
            if (user.getUsername().equalsIgnoreCase(u.getUsername()) || user.getEmail().equalsIgnoreCase(u.getEmail())) {
                return false;
            }
        }
        usersList.add(user);
        db.writeUserObject(user);

        System.out.print(user.getFirstName() + " " + user.getLastName() + " è stato registrato ");
        System.out.println("");
        return true;
    }

    @Override
    public Project createProject(String id, String name, Administrator admin) {

        Project project = new Project(id, name, admin);
        return project;
    }

    @Override
    public ArrayList<Project> getUserProject(String id) throws RemoteException {
        ArrayList<Project> temp = new ArrayList<>();

        for (Project pro : projects) {
            Administrator admin = pro.getAdmin();
            if (admin.getId().equalsIgnoreCase(id)) {
                temp.add(pro);
            }
        }
        return temp;
    }

    @Override
    public ArrayList<User> getUserList() {
        return this.usersList;
    }

    @Override
    public ArrayList<Project> getProjectList() {
        return projects;
    }

    @Override
    public void saveProject(Project project) {

        this.projects.add(project);
        db.writeProjectObject(project, project.getProject_id());

        String msg;
        String act_id;
        ArrayList<Activity> activities = project.getActivities();
        for (Activity act : activities) {

            for (Responsible resp : act.getResponsibles()) {
                msg = project.getProject_name() + ":   " + "you are responsible for a new activity: " + act.getActivity_name();
                notify(resp.getId(), msg);
                addnewRole(resp.getId(), project.getProject_id(), act.getId_activity(), "responsible");
            }
            for (Executioner exe : act.getExecutors()) {
                msg = project.getProject_name() + ":   " + "your new task to complete: " + act.getActivity_name();
                notify(exe.getId(), msg);
                addnewRole(exe.getId(), project.getProject_id(), act.getId_activity(), "executor");
            }
        }
    }

    public void addnewRole(String user_id, String proj_id, String act_id, String roleType) {
        synchronized (roleList) {
            List<String> row = new ArrayList<>();
            row.add(user_id);
            row.add(proj_id);
            row.add(act_id);
            row.add(roleType);
            roleList.add(row);
            db.writeRole(user_id, proj_id, act_id, roleType);
        }
    }

    @Override
    public void deleteProject(String id_project) {
        synchronized (projects) {
            for (Project pro : projects) {
                if (pro.getProject_id().equalsIgnoreCase(id_project)) {
                    projects.remove(pro);
                    deleteRole(id_project);
                    db.deleteProjectObject(id_project);
                    break;
                }
            }
        }
    }

    public void deleteRole(String proj_id) {
        synchronized (roleList) {
            ListIterator<List<String>> listIterator = roleList.listIterator();
            while (listIterator.hasNext()) {
                List<String> role = listIterator.next();
                if (role.get(1).equalsIgnoreCase(proj_id)) {
                    listIterator.remove();
                }
            }
            db.deleteRole(proj_id);
        }
    }

    @Override
    public void addFriends(String user_id, String friend_id) throws RemoteException {
        synchronized (friends) {
            List<String> row = new ArrayList<>();
            row.add(user_id);
            row.add(friend_id);
            friends.add(row);
            db.writeFriend(friend_id, user_id);
        }
    }

    @Override
    public void deleteFriend(String user_id, String friend_id) throws RemoteException {
        synchronized (friends) {
            for (List<String> frnd : friends) {
                if (frnd.get(0).equalsIgnoreCase(user_id) && frnd.get(1).equalsIgnoreCase(friend_id)) {
                    friends.remove(frnd);
                    db.deleteFriend(user_id, friend_id);
                    break;
                }
            }
            for (List<String> frnd : friends) {
                if (frnd.get(0).equalsIgnoreCase(friend_id) && frnd.get(1).equalsIgnoreCase(user_id)) {
                    friends.remove(frnd);
                    db.deleteFriend(friend_id, user_id);
                    break;
                }
            }
        }
    }

    @Override
    public ArrayList<User> getFriends(String user_id) throws RemoteException, java.lang.IndexOutOfBoundsException {
        ArrayList<User> temp = new ArrayList<>();

        if (friends.isEmpty()) {
            return temp;
        } else {
            for (List<String> frnd : friends) {
                if (frnd.size() > 0) {
                    if (frnd.get(0).equalsIgnoreCase(user_id)) {
                        for (User u : usersList) {
                            if (u.getId().equalsIgnoreCase(frnd.get(1))) {
                                temp.add(u);
                            }
                        }
                    }
                }
            }
        }
        return temp;
    }

    public void notify(String user_id, String msg) {
        synchronized (notifications) {
            ArrayList<String> row = new ArrayList<>();
            row.add(user_id);
            row.add(msg);
            row.add("0");
            notifications.add(row);
            db.insertInNotifications(user_id, msg, "0");
        }
    }

    @Override
    public ArrayList<String> getNotifications(String user_id) throws RemoteException {
        ArrayList<String> temp = new ArrayList<>();
        boolean found = false;
        if (notifications.isEmpty()) {
            return temp;
        } else {
            for (List<String> msgs : notifications) {
                if (msgs.get(0).equalsIgnoreCase(user_id) && (msgs.get(2).equalsIgnoreCase("0"))) {
                    temp.add(msgs.get(1));
                    msgs.set(2, "1");
                    found = true;
                }
            }
            if (found == true) {
                db.updateNotifications(user_id, "1");
                //return temp;
            }
        }
        return temp;
    }

    @Override
    public void startProject(String project_id) throws RemoteException {
        for (Project pro : projects) {
            if (pro.getProject_id().equalsIgnoreCase(project_id)) {
                pro.setStart(true);
                String msg;
                ArrayList<Activity> activities = pro.getActivities();
                for (Activity act : activities) {
                    for (Responsible resp : act.getResponsibles()) {
                        msg = pro.getProject_name() + ":   " + "Project Started: ";
                        notify(resp.getId(), msg);
                    }
                    for (Executioner exe : act.getExecutors()) {
                        msg = pro.getProject_name() + ":   " + "Project Started: ";
                        notify(exe.getId(), msg);
                    }
                }
                db.writeProjectObject(pro, project_id);
                nextActivity(project_id);
                break;
            }
        }
    }

    public void nextActivity(String project_id) {

        String msg;
        boolean found_uncompleted_activity = false;
        for (Project pro : projects) {
            Administrator admin = pro.getAdmin();
            if (pro.getProject_id().equalsIgnoreCase(project_id)) {
                ArrayList<Activity> activities = pro.getActivities();
                for (Activity act : activities) {
                    if (act.getState().equals(State.UNCOMPLETED)) {
                        for (Responsible resp : act.getResponsibles()) {
                            msg = pro.getProject_name() + ":   " + "Responsible: " + act.getActivity_name() + " Your task is now active";
                            notify(resp.getId(), msg);
                        }
                        for (Executioner exe : act.getExecutors()) {
                            msg = pro.getProject_name() + ":   " + "Executor: " + act.getActivity_name() + " Your task is now active";
                            notify(exe.getId(), msg);
                        }
                        found_uncompleted_activity = true;
                        msg = pro.getProject_name() + ":   " + "Admin: " + act.getActivity_name() + " A new task is now active. The executors and the responsibles were alerted.";
                        notify(admin.getId(), msg);
                        act.setStart(true);
                        break;
                    }
                }
                if (!found_uncompleted_activity) {
                    pro.setState(State.COMPLETED);
                    msg = pro.getProject_name() + ":   " + "Admin: Project is completed.";
                    notify(admin.getId(), msg);
                }
                db.writeProjectObject(pro, project_id);
                break;
            }
        }
    }

    @Override
    public void completeActivity(String user_id, String proj_id, String act_id) {

        for (Project pro : projects) {
            Administrator admin = pro.getAdmin();
            if (pro.getProject_id().equalsIgnoreCase(proj_id)) {
                ArrayList<Activity> activities = pro.getActivities();
                for (Activity act : activities) {
                    if (act.getId_activity().equalsIgnoreCase(act_id) && act.getState().equals(State.UNCOMPLETED) && act.getStart()) {
                        act.setState(State.COMPLETED);
                        act.setStart(false);
                        String msg = pro.getProject_name() + ":   " + "Admin: " + act.getActivity_name() + " Task is completed.";
                        notify(admin.getId(), msg);
                        nextActivity(proj_id);
                    }
                }
                db.writeProjectObject(pro, proj_id);
            }
        }
    }

    @Override
    public ArrayList<Project> getUserActivities(String user_id) {

        ArrayList<Project> temp = new ArrayList<>();

        for (Project pro : projects) {
            for (List<String> userAct : roleList) {
                if (userAct.get(0).equalsIgnoreCase(user_id)) {
                    ArrayList<Activity> activities = pro.getActivities();
                    if (pro.getProject_id().equalsIgnoreCase(userAct.get(1))) {
                        for (Activity act : activities) {
                            if (act.getId_activity().equalsIgnoreCase(userAct.get(2))) {
                                temp.add(pro);
                            }
                        }
                        break;
                    }
                }
            }
        }
        return temp;
    }

    @Override
    public int getUserNumber() throws RemoteException {
        return usersList.size();
    }

    @Override
    public List<List<String>> getUserInfo(String user_id) throws RemoteException {

        List<List<String>> info = new ArrayList<>();
        List<String> row = new ArrayList<>();
        int proCount = 0;
        int actCount = 0;
        int friendCount = 0;
        for (Project pro : projects) {
            if (pro.getAdmin().getId().equalsIgnoreCase(user_id)) {
                proCount++;
            }
        }
        for (List<String> userAct : roleList) {
            if (userAct.get(0).equalsIgnoreCase(user_id)) {
                actCount++;
            }
        }
        for (List<String> frnd : friends) {
            if (frnd.get(0).equalsIgnoreCase(user_id)) {
                friendCount++;
            }
        }
        row.add("projects");
        row.add(Integer.toString(proCount));
        info.add(row);
        List<String> row1 = new ArrayList<>();
        row1.add("activities");
        row1.add(Integer.toString(actCount));
        info.add(row1);
        List<String> row2 = new ArrayList<>();
        row2.add("friends");
        row2.add(Integer.toString(friendCount));
        info.add(row2);
        
        return info;
    }
}
