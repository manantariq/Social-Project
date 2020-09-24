package GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import Common.Activity;
import Common.Project;
import User.Administrator;
import User.User;
import java.awt.Toolkit;
import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AddProject extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField ProjectName;
	private String name;
	private JTextField activityname;
	private JTextField description;
	private JTextField place;
	private JTextField date;
	private ArrayList<String> resp_id = new ArrayList<>();
	private JPanel AddActivity = new JPanel();
	private static UserGuiHandler userhandler = UserGuiHandler.getInstance();
	private static ArrayList<User> friends = new ArrayList<>();
	private static String id;
	private static Project project;
	private static int i = 1;
	private static addResponsible resp;
	private static JPanel AddActivity1 = new JPanel();

	public AddProject(User user) {
		setResizable(false);
		setTitle("Create Project");
		setIconImage(Toolkit.getDefaultToolkit().getImage(AddProject.class.getResource("/GUI/Original.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		AddActivity1.setBounds(0, 0, 894, 571);
		contentPane.add(AddActivity1);
		AddActivity1.setLayout(null);

		JPanel panel_6 = new JPanel();
		panel_6.setBackground(Color.WHITE);
		panel_6.setBounds(298, 85, 289, 32);
		AddActivity1.add(panel_6);
		panel_6.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Creation of project :");
		lblNewLabel_1.setFont(new Font("Segoe UI Black", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(10, 11, 149, 14);
		panel_6.add(lblNewLabel_1);

		JLabel lblNewLabel_5 = new JLabel(name);
		lblNewLabel_5.setBounds(131, 11, 148, 14);
		panel_6.add(lblNewLabel_5);

		JLabel label_3 = new JLabel(new ImageIcon(AddProject.class.getResource("Original_transparent_218x75.png")));
		label_3.setBounds(0, 0, 230, 82);
		AddActivity1.add(label_3);

		JPanel panel_8 = new JPanel();
		panel_8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AddProject ad = MainWindow.getAddProject();
				ad.dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				panel_8.setBackground(Color.LIGHT_GRAY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				panel_8.setBackground(Color.WHITE);
			}
		});
		panel_8.setBackground(Color.WHITE);
		panel_8.setBounds(209, 236, 116, 32);
		AddActivity1.add(panel_8);

		JLabel lblNewLabel_2 = new JLabel("Return Home");
		lblNewLabel_2.setFont(new Font("Segoe UI Black", Font.PLAIN, 14));
		panel_8.add(lblNewLabel_2);

		JPanel panel_9 = new JPanel();
		panel_9.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					friends = userhandler.getFriendsList(user.getId());
					friends.add(user);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				AddActivity1.setVisible(false);
				AddActivity.setVisible(true);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				panel_9.setBackground(Color.LIGHT_GRAY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				panel_9.setBackground(Color.WHITE);
			}
		});
		panel_9.setBackground(Color.WHITE);
		panel_9.setBounds(375, 236, 173, 32);
		AddActivity1.add(panel_9);

		JLabel lblNewLabel_3 = new JLabel("Add another activity");
		lblNewLabel_3.setFont(new Font("Segoe UI Black", Font.PLAIN, 14));
		panel_9.add(lblNewLabel_3);

		JPanel panel_11 = new JPanel();
		panel_11.setBackground(Color.WHITE);
		panel_11.setBounds(602, 236, 116, 32);
		AddActivity1.add(panel_11);
		panel_11.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					AddProject add = MainWindow.getAddProject();
					userhandler.addProject(project);
					add.dispose();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				panel_11.setBackground(Color.lightGray);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				panel_11.setBackground(Color.WHITE);
			}
		});

		JLabel lblNewLabel_4 = new JLabel("Save project");
		lblNewLabel_4.setFont(new Font("Segoe UI Black", Font.PLAIN, 14));
		panel_11.add(lblNewLabel_4);

		JLabel label_2 = new JLabel(new ImageIcon(AddProject.class.getResource("get.jpg")));
		label_2.setBounds(0, 0, 894, 571);
		AddActivity1.add(label_2);
		AddActivity1.setVisible(false);

		AddActivity.setLayout(null);
		AddActivity.setBounds(0, 0, 894, 571);
		contentPane.add(AddActivity);

		JLabel label_1 = new JLabel(new ImageIcon(AddProject.class.getResource("Original_transparent_218x75.png")));
		label_1.setBounds(0, 0, 230, 82);
		AddActivity.add(label_1);

		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.WHITE);
		panel_5.setBounds(97, 124, 133, 33);
		AddActivity.add(panel_5);

		JLabel lblActivityName = new JLabel("Activity name");
		lblActivityName.setFont(new Font("Segoe UI Black", Font.PLAIN, 14));
		panel_5.add(lblActivityName);

		JPanel AddAct = new JPanel();
		AddAct.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				boolean pass = true;

				String actname = activityname.getText();
				String descr = description.getText();
				String plac = place.getText();
				String dat = date.getText();

				activityname.setText(null);
				description.setText(null);
				place.setText(null);
				date.setText(null);

				if (actname.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Activity name is empty!");
					pass = false;
				}
				if (descr.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Activity description is empty!");
					pass = false;
				}
				if (plac.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Activity place is empty!");
					pass = false;
				}
				if (dat.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Activity date is empty!");
					pass = false;
				}

				if (pass) {
					Activity activity = project.createActivity(Integer.toString(i), actname, descr, plac, dat);

					try {
						resp = new addResponsible(user, resp_id, activity, friends, project);
						AddProject ad = MainWindow.getAddProject();
						ad.setVisible(false);
						resp.setVisible(true);
						AddActivity.setVisible(false);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					i++;
				}

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				AddAct.setBackground(Color.LIGHT_GRAY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				AddAct.setBackground(Color.WHITE);
			}
		});
		AddAct.setBackground(Color.WHITE);
		AddAct.setBounds(350, 348, 291, 33);
		AddActivity.add(AddAct);

		JLabel lblAddResponsible = new JLabel("Go to Add Responsible");
		lblAddResponsible.setFont(new Font("Segoe UI Black", Font.PLAIN, 14));
		AddAct.add(lblAddResponsible);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		panel_3.setBounds(97, 168, 133, 33);
		AddActivity.add(panel_3);

		JLabel lblDescription = new JLabel("Description");
		lblDescription.setFont(new Font("Segoe UI Black", Font.PLAIN, 14));
		panel_3.add(lblDescription);

		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.WHITE);
		panel_4.setBounds(97, 212, 133, 33);
		AddActivity.add(panel_4);

		JLabel lblPlace = new JLabel("Place");
		lblPlace.setFont(new Font("Segoe UI Black", Font.PLAIN, 14));
		panel_4.add(lblPlace);

		JPanel panel_7 = new JPanel();
		panel_7.setBackground(Color.WHITE);
		panel_7.setBounds(97, 256, 133, 33);
		AddActivity.add(panel_7);

		JLabel lblDate = new JLabel("Date");
		lblDate.setFont(new Font("Segoe UI Black", Font.PLAIN, 14));
		panel_7.add(lblDate);

		activityname = new JTextField();
		activityname.setColumns(10);
		activityname.setBounds(350, 124, 291, 33);
		AddActivity.add(activityname);

		description = new JTextField();
		description.setColumns(10);
		description.setBounds(350, 168, 291, 33);
		AddActivity.add(description);

		place = new JTextField();
		place.setColumns(10);
		place.setBounds(350, 212, 291, 33);
		AddActivity.add(place);

		date = new JTextField();
		date.setColumns(10);
		date.setBounds(350, 256, 291, 33);
		AddActivity.add(date);

		JPanel panel_10 = new JPanel();
		panel_10.setBackground(Color.WHITE);
		panel_10.setBounds(405, 49, 133, 33);
		AddActivity.add(panel_10);

		JLabel label_9 = new JLabel("Activity name");
		label_9.setFont(new Font("Segoe UI Black", Font.PLAIN, 14));
		panel_10.add(label_9);

		JLabel label_5 = new JLabel(new ImageIcon(AddProject.class.getResource("get.jpg")));
		label_5.setBounds(0, 0, 894, 571);
		AddActivity.add(label_5);

		AddActivity.setVisible(false);

		JPanel MainCreate = new JPanel();
		MainCreate.setBounds(0, 0, 894, 571);
		contentPane.add(MainCreate);
		MainCreate.setLayout(null);

		JLabel label = new JLabel(new ImageIcon(AddProject.class.getResource("Original_transparent_218x75.png")));
		label.setBounds(0, 0, 230, 82);
		MainCreate.add(label);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(408, 11, 142, 33);
		MainCreate.add(panel);

		JLabel lblNewLabel = new JLabel("Add New Project");
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Segoe UI Black", Font.PLAIN, 16));
		lblNewLabel.setForeground(Color.BLACK);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(97, 124, 133, 33);
		MainCreate.add(panel_1);

		JLabel lblProjectName = new JLabel("Project name");
		panel_1.add(lblProjectName);
		lblProjectName.setFont(new Font("Segoe UI Black", Font.PLAIN, 14));

		ProjectName = new JTextField();
		ProjectName.setBounds(350, 124, 291, 33);
		MainCreate.add(ProjectName);
		ProjectName.setColumns(10);
		// ---------------------------------------------------------------------------------------------------Add
		// Project button function
		JPanel panel_2 = new JPanel();
		panel_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					friends = userhandler.getFriendsList(user.getId());
					friends.add(user);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				name = ProjectName.getText();

				if (name.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Add a Project name!");
				} else {
					id = name + (int) (Math.random() * 100);
					Administrator admin;
					try {
						admin = new Administrator(user.getUsername(), user.getPassword(), user.getEmail(), user.getId(),
								user.getFirstName(), user.getLastName());
						project = userhandler.newProject(id, name, admin);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					MainCreate.setVisible(false);
					AddActivity.setVisible(true);

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
		panel_2.setBackground(Color.WHITE);
		panel_2.setBounds(262, 273, 379, 33);
		MainCreate.add(panel_2);

		JLabel lblCreateProject = new JLabel("Create project and add the first activity");
		lblCreateProject.setFont(new Font("Segoe UI Black", Font.PLAIN, 14));
		panel_2.add(lblCreateProject);

		JLabel Background = new JLabel(new ImageIcon(AddProject.class.getResource("get.jpg")));
		Background.setBounds(0, 0, 894, 571);
		MainCreate.add(Background);
	}

	public static addResponsible getResponsible() {
		return resp;
	}

	public static JPanel getJPpanel() {
		return AddActivity1;
	}

}
