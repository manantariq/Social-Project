package GUI;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import Common.Activity;
import User.Executioner;

public class ShowExecuters extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;


	public ShowExecuters(Activity act) {
		setType(Type.UTILITY);
		int y=100;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 300, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel logo = new JLabel(new ImageIcon(ShowExecuters.class.getResource("Original_transparent_218x75.png")));
		logo.setBounds(0, 0, 218, 75);
		contentPane.add(logo);
		
		JPanel header = new JPanel();
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 284, 75);
		contentPane.add(header);
		
		
		
		 for (Executioner exe : act.getExecutors()) {
            
			 JPanel panel = new JPanel();
				panel.setBounds(55, y, 170, 24);
				contentPane.add(panel);
				panel.setLayout(null);
				panel.setBackground(Color.CYAN);
				
				JLabel lblNewLabel = new JLabel(exe.getFirstName());
				lblNewLabel.setBounds(0, 0, 100, 14);
				panel.add(lblNewLabel);
				
				JLabel lblNewLabel_1 = new JLabel(exe.getLastName());
				lblNewLabel_1.setBounds(100, 0, 100, 14);
				panel.add(lblNewLabel_1);
				
				y= y+34;
		
		
		
		
	}
		 JLabel background = new JLabel(new ImageIcon(ShowExecuters.class.getResource("cert.jpg")));
		 background.setBounds(0, 0, 300, 700);
		 contentPane.add(background);

  } 
}
