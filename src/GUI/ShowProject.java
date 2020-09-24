package GUI;

 import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import Common.Activity;
import Common.Project;
import User.User;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import java.awt.Font;

public class ShowProject extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JPanel contentPane;
	private static JPanel panel_1;
	private static ListActivities list;

	public ShowProject(User user) throws RemoteException, IOException {
		setResizable(false);
		setType(Type.UTILITY);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ShowProject.class.getResource("/GUI/Original.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 700);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel logo = new JLabel(new ImageIcon(ShowProject.class.getResource("Original_transparent_218x75.png")));
		logo.setBounds(0, 0, 218, 75);
		contentPane.add(logo);
		

		JPanel Header = new JPanel();
		Header.setBackground(Color.WHITE);
		Header.setBounds(0, 0, 494, 75);
		contentPane.add(Header);
		Header.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("Project list");
		lblNewLabel_2.setFont(new Font("Segoe UI Black", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(316, 30, 100, 14);
		Header.add(lblNewLabel_2);
		
		showProject(user.getId(), user);

		JLabel lblNewLabel_3 = new JLabel(new ImageIcon(ShowProject.class.getResource("lista.png")));
		lblNewLabel_3.setBounds(0, 0, 494, 671);
		contentPane.add(lblNewLabel_3);

	}

	public static void showProject(String user_id, User user) throws RemoteException, IOException {

		int y = 150;
		UserGuiHandler userhandler = UserGuiHandler.getInstance();
		ArrayList<Project> projects = userhandler.showProject(user_id);
		for (Project pro : projects) {
			ArrayList<Activity> activities = pro.getActivities();
			JPanel panel = new JPanel();

			panel.setBounds(10, y, 306, 41);
			panel.setBackground(Color.CYAN);
			contentPane.add(panel);
			panel.setLayout(null);
			panel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					list = new ListActivities(activities, user,pro,userhandler);
					list.setVisible(true);

					try {
						userhandler.startProject(pro.getProject_id());
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					panel.setBackground(Color.WHITE);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					panel.setBackground(Color.CYAN);
				}
			});

			JLabel lblNewLabel = new JLabel(pro.getProject_name());
			lblNewLabel.setBounds(10, 11, 94, 14);
			panel.add(lblNewLabel);

			JLabel lblNewLabel_1 = new JLabel(pro.getAdmin().getFirstName());
			lblNewLabel_1.setBounds(178, 11, 89, 14);
			panel.add(lblNewLabel_1);
			
			if(pro.isStart()==false){
			panel_1 = new JPanel();
			panel_1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					panel_1.setVisible(false);
					ShowProject s = MainWindow.getShowProject();
					s.dispose();
					JOptionPane.showMessageDialog(null, "Project started with success");
					
					
					try {
						userhandler.startProject(pro.getProject_id());
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					panel_1.setBackground(new Color(154, 205, 50));
				}

				@Override
				public void mouseExited(MouseEvent e) {
					panel_1.setBackground(Color.GREEN);
				}
			});
			panel_1.setBackground(new Color(0, 255, 0));
			panel_1.setBounds(326, y, 57, 41);
			contentPane.add(panel_1);
			JLabel lblStart = new JLabel("Start");
			panel_1.add(lblStart);
			
			}

			

			JPanel panel_2 = new JPanel();
			panel_2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					panel_2.setVisible(false);
					panel_1.setVisible(false);
					panel.setVisible(false);

					try {
						userhandler.deleteProjects(pro.getProject_id());
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

				@Override
				public void mouseEntered(MouseEvent e) {
					panel_2.setBackground(new Color(220, 20, 60));
				}

				@Override
				public void mouseExited(MouseEvent e) {
					panel_2.setBackground(Color.RED);
				}
			});
			panel_2.setBackground(Color.RED);
			panel_2.setBounds(393, y, 57, 41);
			contentPane.add(panel_2);

			JLabel lblDelete = new JLabel("Delete");
			panel_2.add(lblDelete);

			y = y + 51;

		}
	}
	
	public static ListActivities getList(){
		return list;
	}

}
