package GUI;


import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import User.User;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.Font;

public class ContactList extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private static ArrayList<User> friends = new ArrayList<>();
    
   
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 * @throws IOException 
	 * @throws RemoteException 
	 */
	public ContactList(User user) throws RemoteException, IOException {
	    int y = 132;
		setIconImage(Toolkit.getDefaultToolkit().getImage(ContactList.class.getResource("/GUI/Original.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		 
		String user_id=user.getId();
		
		
	
		
		
        UserGuiHandler userhandler = UserGuiHandler.getInstance();
        ArrayList<User> users = userhandler.getUserList();
         friends = userhandler.getFriendsList(user_id);
        boolean found = false;
        for (User u : users) {
            found = false;
            if (u.getId().equalsIgnoreCase(user_id)) {
                continue;
            }
            if (friends != null) {
                for (User f : friends) {
                    if (u.getId().equalsIgnoreCase(f.getId())) {
                        found = true;
                    }
                }
            }
            if (found == true) {
                continue;
            }
            
   		 JPanel panel_1 = new JPanel();
   		panel_1.setBackground(Color.WHITE);
   		panel_1.setBounds(44, y, 162, 28);
   		contentPane.add(panel_1);
   		panel_1.setLayout(null);
   		
   		JLabel lblNewLabel = new JLabel(u.getFirstName());
   		lblNewLabel.setBounds(27, 0, 100, 20);
   		panel_1.add(lblNewLabel);
   		
   		JPanel panel_2 = new JPanel();
   		panel_2.setBackground(Color.WHITE);
   		panel_2.setBounds(284, y, 42, 28);
   		contentPane.add(panel_2);
   		
   		JLabel lblAdd = new JLabel("ADD");
   		lblAdd.setFont(new Font("Showcard Gothic", Font.PLAIN, 11));
   		lblAdd.setBackground(new Color(240, 240, 240));
   		panel_2.add(lblAdd);
   		
   		
   		panel_2.addMouseListener(new MouseAdapter() {
   			@Override
   			public void mouseClicked(MouseEvent e) {
   				try {
   					panel_1.setVisible(false);
   					panel_2.setVisible(false);
   					userhandler.newFriend(user_id, u.getId());
					userhandler.newFriend(u.getId(), user_id);
					
   					
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
   			}
   			@Override
   			public void mouseEntered(MouseEvent e) {
   				panel_2.setBackground(Color.LIGHT_GRAY);
   			}
   			@Override
   			public void mouseExited(MouseEvent e) {
   				panel_2.setBackground(Color.WHITE);
   			}
   		});
     		
   			y=y+38;
        }
  		
  		JPanel panel_3 = new JPanel();
  		panel_3.setBackground(Color.WHITE);
  		panel_3.setBounds(0, 0, 384, 75);
  		contentPane.add(panel_3);
  		panel_3.setLayout(null);
  		
  		
  		
  		
  		
  		JLabel label_1 = new JLabel(new ImageIcon(ContactList.class.getResource("Original_transparent_218x75.png")));
  		label_1.setBounds(0, 0, 230, 82);
  		panel_3.add(label_1);
  		
  		
  		
  		JLabel label = new JLabel(new ImageIcon(ContactList.class.getResource("contact.jpg")));
  		label.setBounds(0, 0, 384, 741);
  		contentPane.add(label);
  		
  		
		
		
		
		
		
		
	}
	
	
}
