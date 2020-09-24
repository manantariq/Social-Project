package GUI;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import Common.Activity;
import User.Responsible;
import java.awt.Color;
import javax.swing.ImageIcon;

public class ShowResponsibles extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	
	public ShowResponsibles(Activity act) {
		setType(Type.UTILITY);
		int y=80;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 300, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel header = new JPanel();
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 284, 75);
		contentPane.add(header);
		header.setLayout(null);
		
		JLabel Logo = new JLabel(new ImageIcon(ShowResponsibles.class.getResource("Original_transparent_218x75.png")));
		Logo.setBounds(0, 0, 218, 75);
		header.add(Logo);
		
		
		
		
		for (Responsible resp : act.getResponsibles()) {
			
			JPanel panel = new JPanel();
			panel.setBounds(20, y, 254, 20);
			contentPane.add(panel);
			panel.setLayout(null);
			panel.setBackground(Color.CYAN);
			
			JLabel lblNewLabel = new JLabel(resp.getFirstName());
			lblNewLabel.setBounds(10, 0, 70, 14);
			panel.add(lblNewLabel);
			
			JLabel lblNewLabel_1 = new JLabel(resp.getLastName());
			lblNewLabel_1.setBounds(150, 0, 70, 14);
			panel.add(lblNewLabel_1);
         
			y=y+30;
		
		
	   }
		JLabel back =new JLabel(new ImageIcon(ShowResponsibles.class.getResource("mana.jpg")));
		back.setBounds(0, 0 , 300 , 700);
		contentPane.add(back);
	}

}
