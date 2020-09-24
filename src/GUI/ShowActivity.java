package GUI;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Common.Activity;
import Common.Project;
import User.User;
import java.awt.Toolkit;

public class ShowActivity extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static UserGuiHandler userhandler;
	private static ArrayList<Project> projects = new ArrayList<>();
	private static ListActivities list;

	public ShowActivity(User user) throws RemoteException {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ShowActivity.class.getResource("/GUI/Original.png")));
		int y = 109;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel logo = new JLabel(new ImageIcon(ShowActivity.class.getResource("Original_transparent_218x75.png")));
		logo.setBounds(0, 0, 220, 75);
		contentPane.add(logo);
		
		JPanel Header = new JPanel();
		Header.setBackground(Color.WHITE);
		Header.setBounds(0, 0, 484, 75);
		contentPane.add(Header);

		userhandler = UserGuiHandler.getInstance();
		projects = userhandler.getActivities(user.getId());

		for (Project pro : projects) {
			ArrayList<Activity> activities = pro.getActivities();

			JPanel panel = new JPanel();
			panel.setBackground(Color.WHITE);
			panel.setBounds(100, y, 273, 47);
			contentPane.add(panel);
			panel.setLayout(null);
			panel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					list = new ListActivities(activities, user, pro, userhandler);
					list.setVisible(true);
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					panel.setBackground(Color.CYAN);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					panel.setBackground(Color.WHITE);
				}
			});

			JLabel lblNewLabel = new JLabel("Admin :");
			lblNewLabel.setBounds(10, 11, 46, 14);
			panel.add(lblNewLabel);

			JLabel lblNewLabel_1 = new JLabel(pro.getProject_name());
			lblNewLabel_1.setBounds(166, 11, 46, 14);
			panel.add(lblNewLabel_1);

			String state = String.valueOf(pro.getState());
			JLabel lblNewLabel_2 = new JLabel("State : ");
			lblNewLabel_2.setBounds(10, 33, 46, 14);
			panel.add(lblNewLabel_2);

			JLabel lblNewLabel_3 = new JLabel(state);
			lblNewLabel_3.setBounds(166, 33, 90, 15);
			panel.add(lblNewLabel_3);

			y = y + 57;

		}
		JLabel backg = new JLabel(new ImageIcon(ShowActivity.class.getResource("case.jpg")));
		backg.setBounds(0, 0, 500, 700);
		contentPane.add(backg);
	}
	
	public static ListActivities getList(){
		return list;
	}
	
}
