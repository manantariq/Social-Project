/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

import java.rmi.RemoteException;

/**
 *
 * @author Manan
 */
public class Executioner extends User{
    
    public Executioner(String username,String password,String email,String id, String firstName, String lastName) throws RemoteException {
        super(username,password,email,id,firstName,lastName);
    }
    
}
