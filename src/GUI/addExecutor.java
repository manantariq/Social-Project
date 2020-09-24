package GUI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import Common.Activity;
import Common.Project;
import User.Executioner;
import User.User;
import java.awt.Color;
import javax.swing.ImageIcon;

public class addExecutor extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private boolean ok = false;
	private int z = 0 ;
	
	//Create the window when the user can select the executors.

	public addExecutor(User user, ArrayList<String> resp_id, Activity activity, ArrayList<User> friends, Project project) {
		setType(Type.UTILITY);
		int y =128;
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 500, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		

		for (User u : friends) {
			if (resp_id.contains(u.getId())) {        //If condition to not add the executor if the friend is 
				continue;							  //already a responsible, because a responsible is also an 	
			}										 //Executor
			
			
			JPanel panel = new JPanel();
			panel.setBounds(40, y, 328, 31);
			panel.setBackground(Color.WHITE);
			contentPane.add(panel);

			JLabel lblNewLabel = new JLabel(u.getUsername());
			panel.add(lblNewLabel);

			JPanel panel_1 = new JPanel();
			panel_1.setBounds(378, y, 73, 31);
			contentPane.add(panel_1);
			JLabel lblNewLabel_1 = new JLabel("ADD");
			panel_1.add(lblNewLabel_1);
			
			
			panel_1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
							
					ok = true;
					
					try {
						Executioner exe = new Executioner(u.getUsername(), u.getPassword(), u.getEmail(), u.getId(),
								u.getFirstName(), u.getLastName());
						activity.addExecutors(exe);
						panel.setVisible(false);
						panel_1.setVisible(false);
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
			
			
			y = y+46;
			z=y;

		}
		
		JPanel addac = new JPanel();
		addac.setBackground(Color.WHITE);
		addac.setBounds(121, z, 263, 32);
		contentPane.add(addac);
		addac.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(ok==false){
					JOptionPane.showMessageDialog(null, "Please, add at least one Executioner");
				}
				else{
					project.addActivity(activity);
					JOptionPane.showMessageDialog(null, "Activity added with succes");
					addExecutor res = addResponsible.getExecutor();
					res.dispose();
					AddProject ad = MainWindow.getAddProject();
					ad.setVisible(true);
					JPanel main = AddProject.getJPpanel();
					main.setVisible(true);
				
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				addac.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				addac.setBackground(Color.WHITE);
			}
		});
		
		
		JLabel lblAddActivity = new JLabel("Go to Create the activity");
		addac.add(lblAddActivity);
		
		JLabel label = new JLabel(new ImageIcon(addExecutor.class.getResource("Original_transparent_218x75.png")));
		label.setBounds(0, 0, 218, 75);
		contentPane.add(label);
		
		JLabel lblNewLabel_2 = new JLabel(new ImageIcon(addExecutor.class.getResource("article.jpg")));
		lblNewLabel_2.setBounds(0, 0, 484, 661);
		contentPane.add(lblNewLabel_2);

	}

}
