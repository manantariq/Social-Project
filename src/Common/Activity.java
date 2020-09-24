/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import User.Executioner;
import User.Responsible;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Manan
 */
public class Activity implements Serializable{
    private final String activity_name;
    private final String description;
    private final String place;
    private final String date;
    private final String id_activity;
    private State state;
    private boolean start;
    private ArrayList<Executioner> executors;
    private ArrayList<Responsible> responsibles;

    

    
    public Activity(String id,String activityName, String desc, String place, String date) {
        
        this.id_activity = id;
        this.activity_name = activityName;
        this.description = desc;
        this.place = place;
        this.date = date;
        this.state = State.UNCOMPLETED;
        this.start = false;
        executors = new ArrayList<>();
        responsibles = new ArrayList<>();        
    }
    
    public State getState(){
        return this.state;
    }
    public void setState(State state){
        this.state = state;
    }
    public boolean getStart(){
        return start;
    }
    public void setStart(boolean start){
        this.start = start;
    }

    public String getActivity_name() {
        return activity_name;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Executioner> getExecutors() {
        return executors;
    }

    public String getId_activity() {
        return id_activity;
    }

    public String getPlace() {
        return place;
    }

    public ArrayList<Responsible> getResponsibles() {
        return responsibles;
    }

    public void addExecutors(Executioner executors) {
        this.executors.add(executors);
    }

    public void addResponsibles(Responsible responsibles) {
        this.responsibles.add(responsibles);
    }
}
