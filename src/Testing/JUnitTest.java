/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testing;

import GUI.UserGuiHandler;
import User.Administrator;
import User.User;
import java.rmi.RemoteException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Manan
 */
public class JUnitTest {
    
    private UserGuiHandler ugh;
    
    public JUnitTest() {
        ugh = null;
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        ugh = UserGuiHandler.getInstance();
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void registerUserTest() throws RemoteException{
        
        User user = new User("manan1", "manan1", "manan@hotmail.com", "manan90", "manan", "tariq");
        
        boolean register = ugh.registerUser(user);
        
        assertTrue(register);    
    }
    
    @Test
    public void userLoginTest() throws RemoteException{
        
        User user = ugh.login("manan1", "manan1");
        
        assertNotNull(user);
    }
    
    @Test
    public void singletonTest(){
        
        UserGuiHandler userhanlder = UserGuiHandler.getInstance();
        
        assertSame("must be same instance", userhanlder, ugh);
    }
}
