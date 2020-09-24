/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

import Common.Project;
import Interface.ServerInterface;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author Manan
 */
public class Administrator extends User{
    
    public Administrator(String username,String password,String email,String id,String firstname,String lastname) throws RemoteException {
        super(username,password,email,id,firstname,lastname);
    }
    
    public ArrayList<Project> getProjects(ServerInterface srv) throws RemoteException{
        
        return srv.getUserProject(this.getId());
    }
    
}
