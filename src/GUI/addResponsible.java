package GUI;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Common.Activity;
import Common.Project;
import User.Responsible;
import User.User;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;

public class addResponsible extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static addExecutor exe;
	private int z = 0;


	public addResponsible(User user , ArrayList<String> resp_id, Activity activity,ArrayList<User> friends , Project project ) throws RemoteException {
		int y = 138;
		setIconImage(Toolkit.getDefaultToolkit().getImage(addResponsible.class.getResource("/GUI/Original.png")));
		setResizable(false);
		setType(Type.UTILITY);
		setTitle("Add responsible\r\n");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 500,700 );
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	
		
		
		
		
		
		
		
		for (User u : friends) {
			
			
			JPanel panel = new JPanel();
			panel.setBounds(40, y, 328, 31);
			contentPane.add(panel);
			JLabel lblNewLabel = new JLabel(u.getUsername());
			panel.setBackground(Color.WHITE);
			panel.add(lblNewLabel);
			
			JPanel panel_1 = new JPanel();
			panel_1.setBounds(378, y, 73, 31);
			contentPane.add(panel_1);
			JLabel lblNewLabel_1 = new JLabel("ADD");
			panel_1.add(lblNewLabel_1);
			panel_1.setBackground(Color.WHITE);
			panel_1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {

					Responsible resp;
					try {
						resp_id.clear();
						panel.setVisible(false);
						panel_1.setVisible(false);
						resp = new Responsible(u.getUsername(), u.getPassword(), u.getEmail(),
								u.getId(), u.getFirstName(), u.getLastName());
						activity.addResponsibles(resp);
						resp_id.add(u.getId());
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				@Override
				public void mouseEntered(MouseEvent e) {
					panel_1.setBackground(Color.LIGHT_GRAY);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					panel_1.setBackground(Color.WHITE);
				}
			});
			
			
			y=y+46;
			
			z=y;
			}
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		panel_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(resp_id.isEmpty()){
					JOptionPane.showMessageDialog(null, "Please, add at least a responsible.");
				}
				else{
					addResponsible resp = AddProject.getResponsible();
					AddProject ad = MainWindow.getAddProject();
					ad.setVisible(false);
					resp.dispose();
					 exe= new addExecutor(user,resp_id,activity, friends,project);
					exe.setVisible(true);
					
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
		panel_2.setBounds(101, z, 290, 32);
		contentPane.add(panel_2);
		
		JLabel lblAddExecutor = new JLabel("Add Executor");
		lblAddExecutor.setFont(new Font("Segoe UI Black", Font.PLAIN, 18));
		panel_2.add(lblAddExecutor);
		
		
		
		
		
		JLabel label = new JLabel(new ImageIcon(addResponsible.class.getResource("Original_transparent_218x75.png")));
		label.setBounds(0, 0, 218, 75);
		contentPane.add(label);
		
		JLabel lblNewLabel_2 = new JLabel(new ImageIcon(addResponsible.class.getResource("cio.jpg")));
		lblNewLabel_2.setBounds(0, 0, 494, 671);
		contentPane.add(lblNewLabel_2);
		
		}
		
	
	public static addExecutor getExecutor(){
		return exe;
	}
		
		
		
		
	}


