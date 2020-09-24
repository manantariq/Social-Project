package GUI;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import Common.Activity;
import Common.Project;
import User.Administrator;
import User.Executioner;
import User.Responsible;
import User.User;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Toolkit;

public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final JPanel Principal = new JPanel();
	private JTextField textField;
	private JPasswordField passwordField;
	private JPanel LoginForm = new JPanel();
	private JPanel Username = new JPanel();
	private JPanel Userame = new JPanel();
	private JPanel Login = new JPanel();
	private JPanel Home = new JPanel();
	private JPanel RegisterForm = new JPanel();
	private JTextField RegisterUsername;
	private JTextField FirstName;
	private JTextField LastName;
	private JTextField Email;
	private JPasswordField Password;
	private User user = null;
	private JPanel UserPanel = new JPanel();
	private JPanel CenterPanel = new JPanel();
	private JLabel label_10 = new JLabel("");
	private JLabel label_9 = new JLabel("");
	private JLabel label_8 = new JLabel("");
	private JLabel label_7 = new JLabel("");
	private JLabel label_6 = new JLabel("");
	private JLabel label_5 = new JLabel("");
	private JLabel label_12 = new JLabel("");
	private static MainWindow frame;
	private static Timer timer = new Timer();
	private static ShowFriends show;
	private static AddProject add;
	private static ShowProject showpro;
	private static ShowActivity sho;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/GUI/Original.png")));
		setResizable(false);
		setTitle("ProShare");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		Principal.setBounds(0, 0, 800, 500);
		Principal.setLayout(null);
		contentPane.add(Principal);
		JPanel registerButton = new JPanel();
		JPanel LoginButton = new JPanel();

		LoginButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Principal.setVisible(false);
				LoginForm.setVisible(true);

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				LoginButton.setBackground(Color.GRAY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				LoginButton.setBackground(Color.WHITE);
			}
		});
		LoginButton.setBackground(Color.WHITE);
		LoginButton.setBounds(324, 306, 226, 47);
		Principal.add(LoginButton);
		LoginButton.setLayout(null);

		JLabel LoginLabel = new JLabel("Login");
		LoginLabel.setForeground(new Color(0, 191, 255));
		LoginLabel.setFont(new Font("Wide Latin", Font.PLAIN, 14));
		LoginLabel.setBounds(72, 0, 115, 42);
		LoginButton.add(LoginLabel);

		registerButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Principal.setVisible(false);
				RegisterForm.setVisible(true);

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				registerButton.setBackground(Color.GRAY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				registerButton.setBackground(Color.WHITE);
			}
		});
		registerButton.setBackground(Color.WHITE);
		registerButton.setBounds(324, 364, 226, 47);
		Principal.add(registerButton);
		registerButton.setLayout(null);

		JLabel RegisterLabel = new JLabel("Register");
		RegisterLabel.setForeground(new Color(0, 191, 255));
		RegisterLabel.setFont(new Font("Wide Latin", Font.PLAIN, 15));
		RegisterLabel.setBounds(47, 11, 133, 25);
		registerButton.add(RegisterLabel);

		JLabel Logo = new JLabel(new ImageIcon(MainWindow.class.getResource("Original_transparent_218x75.png")));
		Logo.setBounds(10, 11, 218, 75);
		Principal.add(Logo);

		JLabel MainBackground = new JLabel(new ImageIcon(MainWindow.class.getResource("mobile.png")));
		MainBackground.setBounds(0, 0, 800, 500);
		Principal.add(MainBackground);

		LoginForm.setBounds(0, 0, 800, 500);
		contentPane.add(LoginForm);
		LoginForm.setLayout(null);

		Home.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				LoginForm.setVisible(false);
				Principal.setVisible(true);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				Home.setBackground(Color.gray);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				Home.setBackground(Color.WHITE);
			}
		});
		Home.setLayout(null);
		Home.setBackground(Color.WHITE);
		Home.setBounds(174, 346, 226, 47);
		LoginForm.add(Home);

		JLabel lblHome = new JLabel("Home");
		lblHome.setFont(new Font("Wide Latin", Font.PLAIN, 14));
		lblHome.setBounds(68, 0, 115, 42);
		Home.add(lblHome);

		// -----------------------------------------------------------------------------------------------------------------Login
		// button functions;
		Login.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				try {
					user = userlogin(textField, passwordField);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if (user == null) {

					JOptionPane.showMessageDialog(null, "The User doesn't exist! Check Password and Username");

				}

				else {
					timer.schedule(new TimerTask() {

						@Override
						public void run() {

							List<List<String>> details = new ArrayList<>();
							UserGuiHandler u = UserGuiHandler.getInstance();

							String ProjectCount = "";
							String ActivitesCount = "";
							String FriendsCount = "";

							try {
								details = u.getUserDestails(user.getId());
								ProjectCount = details.get(0).get(1);
								ActivitesCount = details.get(1).get(1);
								FriendsCount = details.get(2).get(1);
							} catch (RemoteException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							label_10.setText(null);
							label_12.setText(null);
							label_9.setText(null);
							label_5.setText(null);
							label_6.setText(null);
							label_7.setText(null);
							label_8.setText(null);

							label_10.setText(ActivitesCount);
							label_12.setText(FriendsCount);
							label_9.setText(ProjectCount);
							label_5.setText(user.getUsername());
							label_6.setText(user.getFirstName());
							label_7.setText(user.getLastName());
							label_8.setText(user.getEmail());

						}
					}, 0, 1);

					LoginForm.setVisible(false);
					UserPanel.setVisible(true);

				}

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				Login.setBackground(Color.GRAY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				Login.setBackground(Color.WHITE);
			}
		});
		Login.setLayout(null);
		Login.setBackground(Color.WHITE);
		Login.setBounds(491, 346, 226, 47);
		LoginForm.add(Login);

		JLabel label = new JLabel("Login");
		label.setFont(new Font("Wide Latin", Font.PLAIN, 14));
		label.setBounds(68, 0, 115, 42);
		Login.add(label);

		passwordField = new JPasswordField();
		passwordField.setBounds(394, 250, 255, 30);
		LoginForm.add(passwordField);

		textField = new JTextField();
		textField.setBounds(394, 153, 255, 30);
		LoginForm.add(textField);
		textField.setColumns(10);

		Userame.setBackground(Color.WHITE);
		Userame.setBounds(226, 250, 117, 30);
		LoginForm.add(Userame);

		JLabel lblNewLabel_3 = new JLabel("Password");
		lblNewLabel_3.setFont(new Font("Impact", Font.PLAIN, 16));
		Userame.add(lblNewLabel_3);

		Username.setBackground(Color.WHITE);
		Username.setBounds(226, 153, 117, 30);
		LoginForm.add(Username);

		JLabel lblNewLabel_2 = new JLabel("Username");
		Username.add(lblNewLabel_2);
		lblNewLabel_2.setFont(new Font("Impact", Font.PLAIN, 16));
		lblNewLabel_2.setBackground(Color.BLACK);

		// ---------------------------------------------------------------------------------------------------------------
		// Add the logo on the login form
		JLabel Logo1 = new JLabel(new ImageIcon(MainWindow.class.getResource("Original_transparent_218x75.png")));
		Logo1.setBounds(10, 11, 218, 75);
		LoginForm.add(Logo1);

		JLabel Main1 = new JLabel(new ImageIcon(MainWindow.class.getResource("progetti.png")));
		Main1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		Main1.setBounds(0, 0, 800, 500);
		LoginForm.add(Main1);

		RegisterForm.setBounds(0, 0, 800, 500);
		contentPane.add(RegisterForm);
		RegisterForm.setLayout(null);

		// --------------------------------------------------------------------------------------------------------Register
		// button function

		JPanel Register = new JPanel();
		Register.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					user = userRegister(RegisterUsername, FirstName, LastName, Email, Password);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if (user != null) {
					RegisterForm.setVisible(false);
					Principal.setVisible(true);
					JOptionPane.showMessageDialog(null, "The Register is gone well!");

				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				Register.setBackground(Color.WHITE);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				Register.setBackground(Color.LIGHT_GRAY);
			}
		});
		Register.setBackground(Color.LIGHT_GRAY);
		Register.setBounds(492, 346, 225, 46);
		RegisterForm.add(Register);
		Register.setLayout(null);

		JLabel RegisterButton = new JLabel("Register");
		RegisterButton.setFont(new Font("Wide Latin", Font.PLAIN, 14));
		RegisterButton.setBounds(54, 0, 115, 42);
		Register.add(RegisterButton);

		JPanel HomeRegister = new JPanel();
		HomeRegister.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				RegisterForm.setVisible(false);
				Principal.setVisible(true);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				HomeRegister.setBackground(Color.WHITE);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				HomeRegister.setBackground(Color.LIGHT_GRAY);
			}
		});
		HomeRegister.setBackground(Color.LIGHT_GRAY);
		HomeRegister.setBounds(172, 346, 225, 47);
		RegisterForm.add(HomeRegister);
		HomeRegister.setLayout(null);

		JLabel HomeButton = new JLabel("Home");
		HomeButton.setFont(new Font("Wide Latin", Font.PLAIN, 14));
		HomeButton.setBounds(59, 0, 115, 42);
		HomeRegister.add(HomeButton);

		JPanel UsernamePanel = new JPanel();
		UsernamePanel.setBackground(Color.LIGHT_GRAY);
		UsernamePanel.setBounds(219, 102, 95, 30);
		RegisterForm.add(UsernamePanel);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Impact", Font.PLAIN, 16));
		UsernamePanel.add(lblUsername);

		RegisterUsername = new JTextField();
		RegisterUsername.setBackground(Color.LIGHT_GRAY);
		RegisterUsername.setColumns(10);
		RegisterUsername.setBounds(384, 102, 255, 30);
		RegisterForm.add(RegisterUsername);

		JPanel FirstNamePanel = new JPanel();
		FirstNamePanel.setBackground(Color.LIGHT_GRAY);
		FirstNamePanel.setBounds(219, 140, 95, 30);
		RegisterForm.add(FirstNamePanel);

		JLabel lblFirstname = new JLabel("FirstName");
		lblFirstname.setFont(new Font("Impact", Font.PLAIN, 16));
		FirstNamePanel.add(lblFirstname);

		FirstName = new JTextField();
		FirstName.setBackground(Color.LIGHT_GRAY);
		FirstName.setColumns(10);
		FirstName.setBounds(384, 140, 255, 30);
		RegisterForm.add(FirstName);

		JPanel LastNamePanel = new JPanel();
		LastNamePanel.setBackground(Color.LIGHT_GRAY);
		LastNamePanel.setBounds(219, 181, 95, 30);
		RegisterForm.add(LastNamePanel);

		JLabel lblLastname = new JLabel("Lastname");
		lblLastname.setFont(new Font("Impact", Font.PLAIN, 16));
		LastNamePanel.add(lblLastname);

		LastName = new JTextField();
		LastName.setBackground(Color.LIGHT_GRAY);
		LastName.setColumns(10);
		LastName.setBounds(384, 181, 255, 30);
		RegisterForm.add(LastName);

		JPanel EmailPanel = new JPanel();
		EmailPanel.setBackground(Color.LIGHT_GRAY);
		EmailPanel.setBounds(219, 222, 95, 30);
		RegisterForm.add(EmailPanel);

		JLabel lblEmail = new JLabel("E-mail");
		lblEmail.setFont(new Font("Impact", Font.PLAIN, 16));
		EmailPanel.add(lblEmail);

		Email = new JTextField();
		Email.setBackground(Color.LIGHT_GRAY);
		Email.setColumns(10);
		Email.setBounds(384, 222, 255, 30);
		RegisterForm.add(Email);

		JPanel PasswordPanel = new JPanel();
		PasswordPanel.setBackground(Color.LIGHT_GRAY);
		PasswordPanel.setBounds(219, 263, 95, 30);
		RegisterForm.add(PasswordPanel);

		JLabel label_1 = new JLabel("Password");
		PasswordPanel.add(label_1);
		label_1.setFont(new Font("Impact", Font.PLAIN, 16));

		Password = new JPasswordField();
		Password.setBackground(Color.LIGHT_GRAY);
		Password.setBounds(384, 263, 255, 30);
		RegisterForm.add(Password);

		JLabel LogoRegister = new JLabel(
				new ImageIcon(MainWindow.class.getResource("Original_transparent_218x75.png")));
		LogoRegister.setBounds(0, 0, 218, 75);
		RegisterForm.add(LogoRegister);

		JLabel RegisterImage = new JLabel(new ImageIcon(MainWindow.class.getResource("Competenze.jpg")));
		RegisterImage.setBounds(0, 0, 800, 500);
		RegisterForm.add(RegisterImage);

		JPanel panel_12 = new JPanel();
		panel_12.setBackground(Color.WHITE);
		panel_12.setBounds(190, 66, 331, 29);
		CenterPanel.add(panel_12);

		label_5.setForeground(new Color(0, 191, 255));
		label_5.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 16));
		panel_12.add(label_5);

		JPanel panel_13 = new JPanel();
		panel_13.setBackground(Color.WHITE);
		panel_13.setBounds(190, 101, 331, 29);
		CenterPanel.add(panel_13);

		label_6.setForeground(new Color(0, 191, 255));
		label_6.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 16));
		panel_13.add(label_6);

		JPanel panel_14 = new JPanel();
		panel_14.setBackground(Color.WHITE);
		panel_14.setBounds(190, 133, 331, 29);
		CenterPanel.add(panel_14);

		label_7.setForeground(new Color(0, 191, 255));
		label_7.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 16));
		panel_14.add(label_7);

		JPanel panel_15 = new JPanel();
		panel_15.setBackground(Color.WHITE);
		panel_15.setBounds(190, 167, 331, 29);
		CenterPanel.add(panel_15);

		label_8.setForeground(new Color(0, 191, 255));
		label_8.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 16));
		panel_15.add(label_8);

		JPanel panel_16 = new JPanel();
		panel_16.setBackground(Color.WHITE);
		panel_16.setBounds(190, 200, 331, 29);
		CenterPanel.add(panel_16);

		label_9.setForeground(new Color(0, 191, 255));
		label_9.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 16));
		panel_16.add(label_9);

		JPanel panel_17 = new JPanel();
		panel_17.setBackground(Color.WHITE);
		panel_17.setBounds(190, 234, 331, 29);
		CenterPanel.add(panel_17);

		label_10.setBackground(new Color(0, 191, 255));
		label_10.setForeground(new Color(0, 191, 255));
		label_10.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 16));
		panel_17.add(label_10);

		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.WHITE);
		panel_5.setBounds(243, 26, 94, 29);
		CenterPanel.add(panel_5);

		JLabel label_4 = new JLabel("User Info");
		label_4.setForeground(new Color(0, 191, 255));
		label_4.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 16));
		panel_5.add(label_4);

		JPanel panel_6 = new JPanel();
		panel_6.setBackground(Color.WHITE);
		panel_6.setBounds(40, 67, 109, 29);
		CenterPanel.add(panel_6);

		JLabel lblUsername_1 = new JLabel("Username ");
		lblUsername_1.setBackground(new Color(240, 240, 240));
		panel_6.add(lblUsername_1);
		lblUsername_1.setForeground(new Color(0, 191, 255));
		lblUsername_1.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 16));

		JPanel panel_7 = new JPanel();
		panel_7.setBackground(Color.WHITE);
		panel_7.setBounds(40, 101, 109, 29);
		CenterPanel.add(panel_7);

		JLabel lblName = new JLabel("Name ");
		lblName.setForeground(new Color(0, 191, 255));
		lblName.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 16));
		panel_7.add(lblName);

		JPanel panel_8 = new JPanel();
		panel_8.setBackground(Color.WHITE);
		panel_8.setBounds(40, 133, 109, 29);
		CenterPanel.add(panel_8);

		JLabel lblSurname = new JLabel("Surname");
		lblSurname.setForeground(new Color(0, 191, 255));
		lblSurname.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 16));
		panel_8.add(lblSurname);

		JPanel panel_9 = new JPanel();
		panel_9.setBackground(Color.WHITE);
		panel_9.setForeground(Color.WHITE);
		panel_9.setBounds(40, 167, 109, 29);
		CenterPanel.add(panel_9);

		JLabel lblEmail_1 = new JLabel("E-mail");
		lblEmail_1.setForeground(new Color(0, 191, 255));
		lblEmail_1.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 16));
		panel_9.add(lblEmail_1);

		JPanel panel_10 = new JPanel();
		panel_10.setBackground(Color.WHITE);
		panel_10.setBounds(40, 201, 109, 29);
		CenterPanel.add(panel_10);

		JLabel lblProjects = new JLabel("Projects");
		lblProjects.setForeground(new Color(0, 191, 255));
		lblProjects.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 16));
		panel_10.add(lblProjects);

		JPanel panel_11 = new JPanel();
		panel_11.setBackground(Color.WHITE);
		panel_11.setBounds(40, 234, 109, 29);
		CenterPanel.add(panel_11);

		JLabel lblActivities = new JLabel("Activities");
		lblActivities.setForeground(new Color(0, 191, 255));
		lblActivities.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 16));
		panel_11.add(lblActivities);

		JPanel panel_18 = new JPanel();
		panel_18.setBounds(40, 274, 109, 29);
		CenterPanel.add(panel_18);
		panel_18.setBackground(Color.WHITE);

		JLabel lblFriends = new JLabel("Friends");
		lblFriends.setForeground(new Color(0, 191, 255));
		lblFriends.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 16));
		panel_18.add(lblFriends);

		JPanel panel_19 = new JPanel();
		panel_19.setBackground(Color.WHITE);
		panel_19.setBounds(190, 274, 331, 29);
		CenterPanel.add(panel_19);

		label_12.setForeground(new Color(0, 191, 255));
		label_12.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 16));
		label_12.setBackground(new Color(0, 191, 255));
		panel_19.add(label_12);

		JLabel UserInfoLogo = new JLabel(new ImageIcon(MainWindow.class.getResource("pop.jpg")));
		UserInfoLogo.setBackground(Color.BLUE);
		UserInfoLogo.setForeground(Color.BLACK);
		UserInfoLogo.setBounds(0, 0, 652, 398);
		CenterPanel.add(UserInfoLogo);

		UserPanel.setBounds(0, 0, 800, 500);
		contentPane.add(UserPanel);
		UserPanel.setLayout(null);

		JPanel HeadPanel = new JPanel();
		HeadPanel.setBackground(Color.WHITE);
		HeadPanel.setBounds(0, 0, 800, 75);
		UserPanel.add(HeadPanel);
		HeadPanel.setLayout(null);

		JLabel HeadLogo = new JLabel(new ImageIcon(MainWindow.class.getResource("Original_transparent_218x75.png")));
		HeadLogo.setBounds(0, 0, 230, 82);
		HeadPanel.add(HeadLogo);

		JPanel LeftPanel = new JPanel();
		LeftPanel.setBackground(new Color(0, 191, 255));
		LeftPanel.setBounds(0, 0, 181, 489);
		UserPanel.add(LeftPanel);
		LeftPanel.setLayout(null);

		JPanel UserInfo = new JPanel();
		UserInfo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				UserPanel.setVisible(true);

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				UserInfo.setBackground(Color.LIGHT_GRAY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				UserInfo.setBackground(Color.WHITE);
			}
		});
		UserInfo.setBackground(Color.WHITE);
		UserInfo.setBounds(10, 90, 161, 25);
		LeftPanel.add(UserInfo);

		JLabel lblUserInfo = new JLabel("User Info");
		lblUserInfo.setForeground(new Color(0, 191, 255));
		lblUserInfo.setFont(new Font("Engravers MT", Font.PLAIN, 14));
		UserInfo.add(lblUserInfo);

		JPanel Notif = new JPanel();
		Notif.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				NotificationWindow frame2;
				try {
					frame2 = new NotificationWindow(user);
					frame2.setVisible(true);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				Notif.setBackground(Color.LIGHT_GRAY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				Notif.setBackground(Color.WHITE);
			}
		});
		Notif.setBackground(Color.WHITE);
		Notif.setBounds(10, 126, 161, 25);
		LeftPanel.add(Notif);

		JLabel lblSeeNotification = new JLabel("Notification");
		lblSeeNotification.setForeground(new Color(0, 191, 255));
		lblSeeNotification.setFont(new Font("Engravers MT", Font.PLAIN, 14));
		Notif.add(lblSeeNotification);

		JPanel AddCont = new JPanel();
		AddCont.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				try {

					ContactList con = new ContactList(user);
					con.setVisible(true);

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				AddCont.setBackground(Color.LIGHT_GRAY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				AddCont.setBackground(Color.WHITE);
			}
		});
		AddCont.setBackground(Color.WHITE);
		AddCont.setBounds(10, 161, 161, 25);
		LeftPanel.add(AddCont);

		JLabel lblAddContact = new JLabel("Add contact");
		lblAddContact.setForeground(new Color(0, 191, 255));
		lblAddContact.setFont(new Font("Engravers MT", Font.PLAIN, 14));
		AddCont.add(lblAddContact);

		JPanel ShowFriends = new JPanel();
		ShowFriends.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				try {
					show = new ShowFriends(user);
					show.setVisible(true);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				ShowFriends.setBackground(Color.LIGHT_GRAY);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				ShowFriends.setBackground(Color.WHITE);
			}
		});
		ShowFriends.setBackground(Color.WHITE);
		ShowFriends.setBounds(10, 192, 161, 25);
		LeftPanel.add(ShowFriends);

		JLabel lblShowFriends = new JLabel("Show Friends");
		lblShowFriends.setForeground(new Color(0, 191, 255));
		lblShowFriends.setFont(new Font("Engravers MT", Font.PLAIN, 14));
		ShowFriends.add(lblShowFriends);
		//----------------------------------------------------------------------------------------------------Add Project Button Function
		JPanel AddProject = new JPanel();
		AddProject.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					
					UserGuiHandler userhandler = UserGuiHandler.getInstance();

					ArrayList<User> friends = userhandler.getFriendsList(user.getId());
					if (friends.isEmpty()) {
						JOptionPane.showMessageDialog(null, "You have no friends, add some!");
						ContactList contact = new ContactList(user);
						contact.setVisible(true);

					} else {
					 add = new AddProject(user);
					add.setVisible(true);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				AddProject.setBackground(Color.LIGHT_GRAY);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				AddProject.setBackground(Color.WHITE);
			}
		});
		AddProject.setBackground(Color.WHITE);
		AddProject.setBounds(10, 228, 161, 25);
		LeftPanel.add(AddProject);

		JLabel lblAddProject = new JLabel("Add Project");
		lblAddProject.setForeground(new Color(0, 191, 255));
		lblAddProject.setFont(new Font("Engravers MT", Font.PLAIN, 14));
		AddProject.add(lblAddProject);

		JPanel ShowPro = new JPanel();
		ShowPro.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				try {
					showpro = new ShowProject(user);
					showpro.setVisible(true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				ShowPro.setBackground(Color.LIGHT_GRAY);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				ShowPro.setBackground(Color.WHITE);
			}
		});
		ShowPro.setBackground(Color.WHITE);
		ShowPro.setBounds(10, 264, 161, 25);
		LeftPanel.add(ShowPro);

		JLabel lblShowProjects = new JLabel("Show Projects");
		lblShowProjects.setForeground(new Color(0, 191, 255));
		lblShowProjects.setFont(new Font("Engravers MT", Font.PLAIN, 14));
		ShowPro.add(lblShowProjects);

		JPanel ShowAct = new JPanel();
		ShowAct.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					sho = new ShowActivity(user);
					sho.setVisible(true);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				ShowAct.setBackground(Color.LIGHT_GRAY);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				ShowAct.setBackground(Color.WHITE);
			}
		});
		ShowAct.setBackground(Color.WHITE);
		ShowAct.setBounds(10, 300, 161, 25);
		LeftPanel.add(ShowAct);

		JLabel lblShowActivities = new JLabel("Show Activities");
		lblShowActivities.setForeground(new Color(0, 191, 255));
		lblShowActivities.setFont(new Font("Engravers MT", Font.PLAIN, 14));
		ShowAct.add(lblShowActivities);

		CenterPanel.setBounds(148, 76, 652, 398);
		UserPanel.add(CenterPanel);
		CenterPanel.setLayout(null);

		UserPanel.setVisible(false);

		LoginForm.setVisible(false);
		RegisterForm.setVisible(false);

	}

	public static User userlogin(JTextField textfield, JPasswordField passwordField)
			throws RemoteException, IOException {

		String username = textfield.getText();
		char[] password1 = passwordField.getPassword();
		String password = String.valueOf(password1);
		UserGuiHandler userhandler = UserGuiHandler.getInstance();
		User user = userhandler.login(username, password);
		textfield.setText(null);
		passwordField.setText(null);
		return user;
	}

	public static User userRegister(JTextField Username, JTextField Firstname, JTextField Lastname, JTextField Email,
			JPasswordField Password) throws IOException {
		User user;
		boolean isregister = false;

		String username = Username.getText();
		if (username.isEmpty() == true) {

			JOptionPane.showMessageDialog(null, "The text area user is not correct!");
			user = null;
			return user;

		}

		String firstname = Firstname.getText();
		if (firstname.isEmpty() == true) {
			JOptionPane.showMessageDialog(null, "The Firstname area  is not correct!");
			user = null;
			return user;
		}

		String lastname = Lastname.getText();
		if (lastname.isEmpty() == true) {
			JOptionPane.showMessageDialog(null, "The Lastname area  is not correct!");
			user = null;
			return user;
		}

		String email = Email.getText();
		if ((email.isEmpty() == true) || ((email.contains("@") == false) || (email.contains(".") == false))) {
			JOptionPane.showMessageDialog(null, "The Email area  is not correct!");
			user = null;
			return user;
		}

		char[] password2 = Password.getPassword();
		String password = String.copyValueOf(password2);
		if (password.isEmpty() == true) {
			JOptionPane.showMessageDialog(null, "The Password area  is not correct!");
			user = null;
			return user;

		}

		String id = firstname + (int) (Math.random() * 100);
		user = new User(username, password, email, id, firstname, lastname);
		UserGuiHandler userhandler = UserGuiHandler.getInstance();
		isregister = userhandler.registerUser(user);
		if (isregister == false) {
			JOptionPane.showMessageDialog(null, "The Username or E-mail already exist on this site!");
			user = null;
			return user;
		}
		return user;
	}

	public static void addnewproject(User user) throws IOException {

		UserGuiHandler userhandler = UserGuiHandler.getInstance();

		ArrayList<User> friends = userhandler.getFriendsList(user.getId());
		if (friends == null) {
			System.out.println("Error: Add some Friends!!");

		} else {
			friends.add(user);
			System.out.println("New Project Name: ");
			BufferedReader rdr = new BufferedReader(new InputStreamReader(System.in));
			String projectName = rdr.readLine();
			String id = projectName + (int) (Math.random() * 100);
			Administrator admin = new Administrator(user.getUsername(), user.getPassword(), user.getEmail(),
					user.getId(), user.getFirstName(), user.getLastName());
			Project project = userhandler.newProject(id, projectName, admin);
			String scelta;
			int i = 0;
			String c;
			do {
				System.out.println("New Activity");
				System.out.println("");
				System.out.println("Name Activity: ");
				String actName = rdr.readLine();
				System.out.println("Description: ");
				String desc = rdr.readLine();
				System.out.println("Place: ");
				String place = rdr.readLine();
				System.out.println("Date: ");
				String date = rdr.readLine();
				Activity activity = project.createActivity(Integer.toString(i), actName, desc, place, date);

				ArrayList<String> resp_id = new ArrayList<>();
				while (resp_id.isEmpty()) {
					for (User u : friends) {
						do {
							if (u.getId().equalsIgnoreCase(user.getId())) {
								System.out.print(u.getFirstName() + " (You):" + " add as responsible Y o N: ");
							} else {
								System.out.print(u.getFirstName() + ": add as responsible Y o N: ");
							}
							c = rdr.readLine();
						} while (!(c.equalsIgnoreCase("y")) && !(c.equalsIgnoreCase("n")));
						if (c.equalsIgnoreCase("y")) {
							Responsible resp = new Responsible(u.getUsername(), u.getPassword(), u.getEmail(),
									u.getId(), u.getFirstName(), u.getLastName());
							activity.addResponsibles(resp);
							resp_id.add(u.getId());
						}
					}
					if (resp_id.isEmpty()) {
						System.out.println("");
						System.out.println("Error........: please add at least one Responsible ");
						System.out.println("");
					}
				}
				for (User u : friends) {
					if (resp_id.contains(u.getId())) {
						continue;
					}
					do {
						if (u.getId().equalsIgnoreCase(user.getId())) {
							System.out.print(u.getFirstName() + " (You):" + " add as Executor Y o N: ");
						} else {
							System.out.print(u.getFirstName() + ": add as Executor Y o N: ");
						}
						c = rdr.readLine();
					} while (!(c.equalsIgnoreCase("y")) && !(c.equalsIgnoreCase("n")));
					if (c.equalsIgnoreCase("y")) {
						Executioner exe = new Executioner(u.getUsername(), u.getPassword(), u.getEmail(), u.getId(),
								u.getFirstName(), u.getLastName());
						activity.addExecutors(exe);
					}
				}
				project.addActivity(activity);
				i++;
				do {
					System.out.println("do you want to add a new activity: y or n");
					scelta = rdr.readLine();
				} while (!(scelta.equalsIgnoreCase("y")) && !(scelta.equalsIgnoreCase("n")));
			} while (scelta.equalsIgnoreCase("y"));
			userhandler.addProject(project);
		}
	}
	
	public static AddProject getAddProject(){
		return add;
	}
	
	public static ShowProject getShowProject(){
		return showpro;
		
	}
	
	public static ShowActivity getShow(){
		return sho;
	}
	
	

}
