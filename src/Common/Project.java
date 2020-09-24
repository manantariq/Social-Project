/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import User.Administrator;
import User.User;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author Manan
 */
public class Project implements Serializable{
    
    private final Administrator admin;
    private final String project_name;
    private final String project_id;
    private boolean start;
    private final ArrayList<Activity> activities;
    private State state;
    
    public Project(String id,String name,Administrator administrator){
        this.project_name = name;
        this.admin = administrator;
        this.start = false;
        this.project_id = id;
        activities = new ArrayList<>();
        this.state = State.UNCOMPLETED;
    }
    
    public Activity createActivity(String id,String activityName,String desc,String place,String date){
        
        Activity activity = new Activity(id,activityName,desc,place,date);
        //activities.add(activity);
        return activity;
    }

    public Administrator getAdmin() {
        return this.admin;
    }

    public String getProject_name() {
        return project_name;
    }

    public ArrayList<Activity> getActivities() {
        return activities;
    }
    public void addActivity(Activity act){
        this.activities.add(act);
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public String getProject_id() {
        return project_id;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
