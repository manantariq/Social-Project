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
public class Responsible extends User {

    public Responsible(String username,String password,String email,String id,String firstname,String lastname) throws RemoteException {
        super(username,password,email,id,firstname,lastname);
    }
    
}
