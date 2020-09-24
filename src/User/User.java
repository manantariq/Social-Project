/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author Manan
 */
public class User implements Serializable{
     private static final long serialVersionUID = 7526471155622776147L;
     
    private final String username;
    private final String password;
    private final String firstname;
    private final String lastname;
    private final String id;
    private final String email;
    
    public User(String username,String password,String email,String id,String firstname,String lastname) throws RemoteException {
        super();
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.email = email;
    }
    
    public String getFirstName(){
        return this.firstname;
    }
    
    public String getLastName(){
        return this.lastname;
    }
    
    public String getId(){
        return this.id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }
}
