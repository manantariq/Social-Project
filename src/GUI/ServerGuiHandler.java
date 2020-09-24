/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Common.Project;
import Interface.ServerInterface;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

/**
 *
 * @author Manan
 */
public class ServerGuiHandler {

    private ServerInterface srv;
    private static ServerGuiHandler instance = null;

    private ServerGuiHandler() {
        try {
            Registry reg = LocateRegistry.getRegistry(1234);
            srv = (ServerInterface) reg.lookup("SocialProject");

        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    public static ServerGuiHandler getInstance() {
        if (instance == null) {
            instance = new ServerGuiHandler();
        }
        return instance;
    }

    public int getUserCount() throws RemoteException {
        return srv.getUserNumber();
    }

    public ArrayList<Project> getProjects() throws RemoteException {
        return srv.getProjectList();
    }

}
