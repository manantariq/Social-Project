package GUI;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import User.User;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Toolkit;

public class ShowFriends extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JPanel contentPane;
	private static ArrayList<User> friends;

	public ShowFriends(User user) throws IOException {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ShowFriends.class.getResource("/GUI/Original.png")));
		setTitle("Friends");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(500, 200, 300,500 );
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		
		UserGuiHandler userhandler = UserGuiHandler.getInstance();
	    friends = userhandler.getFriendsList(user.getId());
		
		if(friends.isEmpty()){
			JOptionPane.showMessageDialog(null, "You have no friends!");
		}
		
		else{
			showfriendlist(user.getId());
			
		}
		
		
		
		
		JLabel lblNewLabel = new JLabel(new ImageIcon(ShowFriends.class.getResource("conn.png")));
		lblNewLabel.setBounds(0, 0, 284, 461);
		contentPane.add(lblNewLabel);
		
		
		
		
		
	}





public static void showfriendlist(String user_id) throws RemoteException, IOException {

	int y=90;
    UserGuiHandler userhandler = UserGuiHandler.getInstance();
    friends = userhandler.getFriendsList(user_id);
        for (User u : friends) {
        	
        	JPanel Friend = new JPanel();
    		Friend.setBackground(Color.BLUE);
    		Friend.setBounds(10, y, 205, 25);
    		contentPane.add(Friend);
    		Friend.setLayout(null);
    		
    		JLabel label = new JLabel(u.getFirstName());
    		label.setForeground(Color.WHITE);
    		label.setBackground(Color.WHITE);
    		label.setBounds(20, 7, 82, 22);
    		Friend.add(label);
    		
    		JLabel label_1 = new JLabel(u.getLastName());
    		label_1.setForeground(Color.WHITE);
    		label_1.setBackground(Color.WHITE);
    		label_1.setBounds(110, 7, 82, 22);
    		Friend.add(label_1);
    		
    		JPanel panel_1 = new JPanel();
    		panel_1.setBackground(Color.RED);
    		panel_1.setBounds(225, y, 49, 25);
    		contentPane.add(panel_1);
    		panel_1.setLayout(null);
    		
    		JLabel lblNewLabel_1 = new JLabel("Delete");
    		lblNewLabel_1.setFont(new Font("Imprint MT Shadow", Font.PLAIN, 14));
    		lblNewLabel_1.setForeground(Color.BLACK);
    		lblNewLabel_1.setBounds(0, 0, 49, 25);
    		panel_1.add(lblNewLabel_1);
    		
    		y = y+30;
    		
    		panel_1.addMouseListener(new MouseAdapter(){
    			@Override
       			public void mouseClicked(MouseEvent e) {
    				Friend.setVisible(false);
    				panel_1.setVisible(false);
    				try {
						userhandler.deleteFriends(user_id, u.getId());
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
    			
    			}
    			
    			@Override
       			public void mouseEntered(MouseEvent e) {
    				
    			}
    			
    			@Override
       			public void mouseExited(MouseEvent e) {
    				
    			}
    		});
        
        }
        
        
    
 }
}