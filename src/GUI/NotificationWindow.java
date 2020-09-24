package GUI;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import User.User;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Toolkit;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.awt.Color;



public class NotificationWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static JLabel lblNewLabel = new JLabel();;
	private static JPanel panel = new JPanel();

	public NotificationWindow(User user) throws RemoteException {
		setTitle("Notifications");
		setIconImage(Toolkit.getDefaultToolkit().getImage(NotificationWindow.class.getResource("/GUI/Original.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 100, 350, 556);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		panel.setBounds(0, 0, 334, 661);
		contentPane.add(panel);
		panel.setLayout(null);
		
		
		JLabel label_1 = new JLabel(new ImageIcon (NotificationWindow.class.getResource("Original_transparent_218x75.png")));
		label_1.setBounds(0, 0, 234, 75);
		panel.add(label_1);
		
		
		
		seeNotification(user);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 334, 75);
		panel.add(panel_1);
		panel_1.setBackground(Color.WHITE);
		
		JLabel label = new JLabel(new ImageIcon(NotificationWindow.class.getResource("documenti.jpg")));
		label.setBounds(0, 0, 335, 556);
		panel.add(label);
		
	}
	
	 public static void seeNotification(User user) throws RemoteException {
		 int y=86;
	        UserGuiHandler userhandler = UserGuiHandler.getInstance();
	        ArrayList<String> notifications = userhandler.getUserNotifications(user.getId());
	        if (notifications.isEmpty()) {
	        	JOptionPane.showMessageDialog(null, "No, new notifications");
	        } else {
	            for (String notif : notifications) {
	            	
	            	lblNewLabel.setText(notif);
	            	lblNewLabel.setBackground(Color.WHITE);
	        		lblNewLabel.setBounds(10, y, 197, 14);
	        		panel.add(lblNewLabel);
	        		y= y+20;
	            	
	            }
	        }
	        
	    }
}
