package GUI;


import java.awt.EventQueue;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import Common.Project;
import Common.State;
import Server.Server;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Toolkit;

public class ServerWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel StatitcsPanel = new JPanel();
	private JLabel lblTotalUser = new JLabel("Total user");
	private static JLabel NTP = new JLabel("");
	private static JLabel NumAc = new JLabel("");
	private static JPanel panel_1 = new JPanel();
	private static JLabel label_3 = new JLabel("");
	private static JLabel label_4 = new JLabel("");
	private static Timer timer = new Timer();
	private static JPanel NumTotalUser = new JPanel();
	private static JLabel label = new JLabel("");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerWindow frame = new ServerWindow();
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
	public ServerWindow() {
		setResizable(false);
		setBackground(Color.WHITE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ServerWindow.class.getResource("/GUI/Original.png")));
		setTitle("Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel MainServer = new JPanel();
		MainServer.setBounds(0, 0, 800, 500);
		contentPane.add(MainServer);
		MainServer.setLayout(null);

		JPanel StartServer = new JPanel();
		StartServer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MainServer.setVisible(false);
				StatitcsPanel.setVisible(true);

				try {
					Server srv = new Server();
					Registry reg = LocateRegistry.createRegistry(1234);
					reg.bind("SocialProject", srv);
					
					timer.schedule(new TimerTask() {
						
						@Override
						public void run() {
							
							try {
								
								label.setText(null);
								label.setText(showUserCount());
							} catch (RemoteException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							try {
								showProjectStatistic();
							} catch (RemoteException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
						}
					},0,60000);
					

				} catch (Exception c) {
					c.printStackTrace();
				}

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				StartServer.setBackground(Color.LIGHT_GRAY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				StartServer.setBackground(Color.WHITE);
			}
		});
		StartServer.setBackground(new Color(255, 255, 255));
		StartServer.setBounds(303, 204, 160, 33);
		MainServer.add(StartServer);
		StartServer.setLayout(null);
		
		label.setBounds(10, 0, 22, 27);
		NumTotalUser.add(label);

		JLabel Start = new JLabel("Start Server");
		Start.setFont(new Font("Wide Latin", Font.PLAIN, 14));
		Start.setBounds(10, 0, 150, 36);
		StartServer.add(Start);

		JLabel Logo = new JLabel(new ImageIcon(ServerWindow.class.getResource("Original_transparent_218x75.png")));
		Logo.setBounds(0, 0, 218, 75);
		MainServer.add(Logo);

		JPanel Header = new JPanel();
		Header.setBackground(Color.WHITE);
		Header.setBounds(0, 0, 800, 75);
		MainServer.add(Header);

		JPanel StatusPanel = new JPanel();
		StatusPanel.setBackground(Color.WHITE);
		StatusPanel.setBounds(566, 105, 206, 24);
		MainServer.add(StatusPanel);
		StatusPanel.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Server Status :");
		lblNewLabel_1.setFont(new Font("Segoe UI Black", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(0, 0, 105, 25);
		StatusPanel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("OFFLINE\r\n");
		lblNewLabel_2.setForeground(Color.RED);
		lblNewLabel_2.setFont(new Font("Segoe UI Black", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(115, 0, 81, 25);
		StatusPanel.add(lblNewLabel_2);

		JLabel ImageServer = new JLabel(new ImageIcon(ServerWindow.class.getResource("Server.jpg")));
		ImageServer.setBounds(0, 0, 800, 500);
		MainServer.add(ImageServer);

		StatitcsPanel.setBounds(0, 0, 800, 500);
		contentPane.add(StatitcsPanel);

		StatitcsPanel.setVisible(false);
		StatitcsPanel.setLayout(null);

		JPanel HeadPanel = new JPanel();
		HeadPanel.setBackground(Color.WHITE);
		HeadPanel.setBounds(0, 0, 800, 75);
		StatitcsPanel.add(HeadPanel);
		HeadPanel.setLayout(null);

		JLabel Logo2 = new JLabel(new ImageIcon(ServerWindow.class.getResource("Original_transparent_218x75.png")));
		Logo2.setBounds(0, 0, 218, 75);
		HeadPanel.add(Logo2);

		JLabel lblNewLabel = new JLabel("Server Statistics");
		lblNewLabel.setFont(new Font("Segoe UI Black", Font.PLAIN, 20));
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setBounds(332, 11, 204, 53);
		HeadPanel.add(lblNewLabel);

		JPanel TotalUser = new JPanel();
		TotalUser.setBackground(Color.WHITE);
		TotalUser.setBounds(38, 96, 170, 27);
		StatitcsPanel.add(TotalUser);
		TotalUser.setLayout(null);

		lblTotalUser.setFont(new Font("Segoe UI Black", Font.PLAIN, 15));
		lblTotalUser.setBounds(10, 0, 91, 27);
		TotalUser.add(lblTotalUser);

		
		NumTotalUser.setBackground(Color.WHITE);
		NumTotalUser.setBounds(233, 96, 32, 27);
		StatitcsPanel.add(NumTotalUser);
		NumTotalUser.setLayout(null);

		

		JPanel TotalProject = new JPanel();
		TotalProject.setLayout(null);
		TotalProject.setBackground(Color.WHITE);
		TotalProject.setBounds(38, 134, 170, 27);
		StatitcsPanel.add(TotalProject);

		JLabel Tp = new JLabel("Total Project");
		Tp.setFont(new Font("Segoe UI Black", Font.PLAIN, 15));
		Tp.setBounds(10, 0, 122, 27);
		TotalProject.add(Tp);

		JPanel NumTotProj = new JPanel();
		NumTotProj.setLayout(null);
		NumTotProj.setBackground(Color.WHITE);
		NumTotProj.setBounds(233, 134, 32, 27);
		StatitcsPanel.add(NumTotProj);

		NTP.setBounds(10, 0, 22, 27);
		NumTotProj.add(NTP);

		JPanel Active = new JPanel();
		Active.setLayout(null);
		Active.setBackground(Color.WHITE);
		Active.setBounds(38, 172, 170, 27);
		StatitcsPanel.add(Active);

		JLabel lblActiveProjects = new JLabel("Active Projects");
		lblActiveProjects.setFont(new Font("Segoe UI Black", Font.PLAIN, 15));
		lblActiveProjects.setBounds(10, 0, 122, 27);
		Active.add(lblActiveProjects);

		JPanel NumActive = new JPanel();
		NumActive.setLayout(null);
		NumActive.setBackground(Color.WHITE);
		NumActive.setBounds(233, 172, 32, 27);
		StatitcsPanel.add(NumActive);

		NumAc.setBounds(10, 0, 22, 27);
		NumActive.add(NumAc);

		JPanel NonActivePanel = new JPanel();
		NonActivePanel.setLayout(null);
		NonActivePanel.setBackground(Color.WHITE);
		NonActivePanel.setBounds(38, 210, 170, 27);
		StatitcsPanel.add(NonActivePanel);

		JLabel lblNonActiveProjects = new JLabel("Non Active Projects");
		lblNonActiveProjects.setFont(new Font("Segoe UI Black", Font.PLAIN, 15));
		lblNonActiveProjects.setBounds(10, 0, 150, 27);
		NonActivePanel.add(lblNonActiveProjects);

		panel_1.setLayout(null);
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(38, 248, 170, 27);
		StatitcsPanel.add(panel_1);

		JLabel lblCompleteProjects = new JLabel("Complete Projects");
		lblCompleteProjects.setFont(new Font("Segoe UI Black", Font.PLAIN, 15));
		lblCompleteProjects.setBounds(10, 0, 150, 27);
		panel_1.add(lblCompleteProjects);

		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBackground(Color.WHITE);
		panel_2.setBounds(233, 210, 32, 27);
		StatitcsPanel.add(panel_2);

		label_3.setBounds(10, 0, 22, 27);
		panel_2.add(label_3);

		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBackground(Color.WHITE);
		panel_3.setBounds(233, 248, 32, 27);
		StatitcsPanel.add(panel_3);

		label_4.setBounds(10, 0, 22, 27);
		panel_3.add(label_4);

		JPanel Status = new JPanel();
		Status.setLayout(null);
		Status.setBackground(Color.WHITE);
		Status.setBounds(568, 96, 206, 24);
		StatitcsPanel.add(Status);

		JLabel label_1 = new JLabel("Server Status :");
		label_1.setFont(new Font("Segoe UI Black", Font.PLAIN, 14));
		label_1.setBounds(0, 0, 105, 25);
		Status.add(label_1);

		JLabel lblOnline = new JLabel("ONLINE");
		lblOnline.setForeground(Color.GREEN);
		lblOnline.setFont(new Font("Segoe UI Black", Font.PLAIN, 14));
		lblOnline.setBounds(115, 0, 81, 25);
		Status.add(lblOnline);

		JLabel ImageServer2 = new JLabel(new ImageIcon(ServerWindow.class.getResource("dedicatedserver.png")));
		ImageServer2.setBounds(0, -11, 800, 500);
		StatitcsPanel.add(ImageServer2);

	}

	public static void showProjectStatistic() throws RemoteException {

		ServerGuiHandler svg = ServerGuiHandler.getInstance();
		ArrayList<Project> projects = svg.getProjects();
		String TotalProject = String.valueOf(projects.size());
		NTP.setText(TotalProject);
		int active = 0;
		int completed = 0;
		for (Project pro : projects) {
			if (pro.isStart()) {
				active++;
			}
			if (pro.getState().equals(State.COMPLETED)) {
				completed++;
			}
		}
		String Active = String.valueOf(active);
		NumAc.setText(null);
		NumAc.setText(Active);
		String Non = String.valueOf(projects.size() - active);
		label_3.setText(null);
		label_3.setText(Non);
		String com = String.valueOf(completed);
		label_4.setText(null);
		label_4.setText(com);
	}

	public static String showUserCount() throws RemoteException {

		ServerGuiHandler svg = ServerGuiHandler.getInstance();
		int userCount = svg.getUserCount();
		String Count = String.valueOf(userCount);
		return Count;
	}
}
