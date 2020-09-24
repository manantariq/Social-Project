package GUI;

import java.awt.Color;
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
import Common.State;
import User.Executioner;
import User.Responsible;
import User.User;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

public class ListActivities extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private boolean found = false;

	
	public ListActivities(ArrayList<Activity> activities, User user ,Project pro, UserGuiHandler userhandler ) {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ListActivities.class.getResource("/GUI/Original.png")));
		setTitle("Activites list");
		int y=90;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.WHITE);
		panel_4.setBounds(0, 0, 494, 75);
		contentPane.add(panel_4);
		panel_4.setLayout(null);
		
		JLabel Logo = new JLabel(new ImageIcon(ListActivities.class.getResource("Original_transparent_218x75.png")));
		Logo.setBounds(0, 0, 218, 75);
		panel_4.add(Logo);
		
		
		 
		
		
		

        for (Activity act : activities) {
            found = false;
            
            JPanel panel = new JPanel();
       		panel.setBackground(Color.CYAN);
       		panel.setBounds(25, y, 360, 148);
       		contentPane.add(panel);
       		panel.setLayout(null);
       		
       		JLabel lblNewLabel = new JLabel("Name : ");
       		lblNewLabel.setBounds(10, 11, 46, 14);
       		panel.add(lblNewLabel);
       		
       		JLabel lblNewLabel_1 = new JLabel(act.getActivity_name());
       		lblNewLabel_1.setBounds(50, 11, 110, 14);
       		panel.add(lblNewLabel_1);
       		
       		JLabel lblNewLabel_2 = new JLabel("Description : ");
       		lblNewLabel_2.setBounds(170, 11, 75, 14);
       		panel.add(lblNewLabel_2);
       		
       		JLabel lblNewLabel_3 = new JLabel(act.getDescription());
       		lblNewLabel_3.setBounds(250, 11, 104, 14);
       		panel.add(lblNewLabel_3);
       		
       		JLabel lblNewLabel_4 = new JLabel("Start :");
       		lblNewLabel_4.setBounds(10, 55, 46, 14);
       		panel.add(lblNewLabel_4);
       		
       		boolean start = act.getStart();
       		String start1 = String.valueOf(start);
       		
       		JLabel lblNewLabel_5 = new JLabel(start1);
       		lblNewLabel_5.setBounds(76, 55, 46, 14);
       		panel.add(lblNewLabel_5);
       		
       		String state = String.valueOf(act.getState());
       		
       		JLabel lblNewLabel_6 = new JLabel("State : ");
       		lblNewLabel_6.setBounds(132, 55, 70, 14);
       		panel.add(lblNewLabel_6);
       		
       		JLabel lblNewLabel_7 = new JLabel(state);
       		lblNewLabel_7.setBounds(228, 55, 90, 14);
       		panel.add(lblNewLabel_7);
       		
       		JPanel panel_2 = new JPanel();
       		panel_2.addMouseListener(new MouseAdapter() {
       			@Override
       			public void mouseClicked(MouseEvent arg0) {
       				ShowExecuters exec = new ShowExecuters(act);
       				exec.setVisible(true);
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
       		panel_2.setBounds(25, 102, 126, 20);
       		panel.add(panel_2);
       		panel_2.setBackground(Color.WHITE);
       		
       		JLabel lblShowExecuters = new JLabel("Show Executers");
       		panel_2.add(lblShowExecuters);
       		
       		JPanel panel_3 = new JPanel();
       		panel_3.addMouseListener(new MouseAdapter() {
       			@Override
       			public void mouseClicked(MouseEvent arg0) {
       				ShowResponsibles respn = new ShowResponsibles(act);
       				respn.setVisible(true);
       			}
       			@Override
       			public void mouseEntered(MouseEvent e) {
       				panel_3.setBackground(Color.LIGHT_GRAY);
       			}
       			@Override
       			public void mouseExited(MouseEvent e) {
       				panel_3.setBackground(Color.WHITE);
       			}
       		});
       		panel_3.setBounds(179, 102, 126, 20);
       		panel.add(panel_3);
       		panel_3.setBackground(Color.WHITE);
       		
       		JLabel lblShowResponsables = new JLabel("Show Responsables");
       		panel_3.add(lblShowResponsables);
            
         
    		
            for (Responsible resp : act.getResponsibles()) {
               
                if (act.getStart() && act.getState().equals(State.UNCOMPLETED) && resp.getId().equalsIgnoreCase(user.getId())) {
                    found = true;
                }
            }

            
            for (Executioner exe : act.getExecutors()) {
              
                if (act.getStart() && act.getState().equals(State.UNCOMPLETED) && exe.getId().equalsIgnoreCase(user.getId())) {
                    found = true;
                }
            }
            if(found){
            	JPanel panel_1 = new JPanel();
        		panel_1.setBackground(Color.CYAN);
        		panel_1.setBounds(390, y, 72, 59);
        		contentPane.add(panel_1);
        		
        		JLabel complete = new JLabel("Complete");
        		panel_1.add(complete);
        		
        		
        		panel_1.addMouseListener(new MouseAdapter() {
           			@Override
           			public void mouseClicked(MouseEvent arg0) {
           			 try {
						userhandler.CompleteActivity(user.getId(), pro.getProject_id(), act.getId_activity());
						panel_1.setVisible(false);
						ListActivities l = ShowProject.getList();
						ListActivities l1 = ShowActivity.getList();
						if(l != null){
						l.dispose();
						}
						if(l1!=null){
							l1.dispose();
						}
						ShowProject s = MainWindow.getShowProject();
						ShowActivity sh = MainWindow.getShow();
						if(s!=null){
						s.dispose();
						}
						if(sh!=null){
						sh.dispose();
						}
						JOptionPane.showMessageDialog(null, "Activity completed");
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
           			}
           			@Override
           			public void mouseEntered(MouseEvent e) {
           				panel_1.setBackground(Color.WHITE);
           			}
           			@Override
           			public void mouseExited(MouseEvent e) {
           				panel_1.setBackground(Color.CYAN);
           			}
           		});
            	
            }
            y=y+158;
        }
		
		JLabel ba = new JLabel(new ImageIcon(ListActivities.class.getResource("business.jpg")));
		ba.setBounds(0, 0, 500, 700);
		contentPane.add(ba);
	}

}
