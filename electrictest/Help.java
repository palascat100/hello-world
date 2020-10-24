package electrictest;

/*
 * Imports necessary classes
 */
import java.awt.BorderLayout; 
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Help extends JDialog {
	/**
	 * Declaration of variables: Jpanel for the display and dialog
	 * to prevent static reference errors
	 */
	private final JPanel contentPanel = new JPanel();
	Help dialog = this;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Help dialog = new Help();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Help() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 680, 554);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("This program was made to try and visualize the electric field around charged particles following");
		lblNewLabel.setBounds(6, 6, 668, 16);
		contentPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("proper conventions");
		lblNewLabel_1.setBounds(6, 26, 668, 16);
		contentPanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("DISCLAIMER: This is a very beginner program, and it can be very slow so don't spam buttons");
		lblNewLabel_2.setBounds(6, 54, 668, 16);
		contentPanel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("The +, -, and textbox is for determining the number of lines per nc of charge");
		lblNewLabel_3.setBounds(6, 108, 668, 16);
		contentPanel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("For example, if the field says 3 then a charge of 6 nc will have 18 lines");
		lblNewLabel_4.setBounds(6, 122, 668, 16);
		contentPanel.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("The add charge button will add a charged particle. There is a delay before it appears.");
		lblNewLabel_5.setBounds(6, 170, 668, 16);
		contentPanel.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("The change positions button allows you to change the position of your particles. There is no other way.");
		lblNewLabel_6.setBounds(6, 215, 668, 16);
		contentPanel.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("The shuffle order button shuffles the order of which charged particle's lines are calculated first.");
		lblNewLabel_7.setBounds(6, 261, 668, 16);
		contentPanel.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("In this program, the electric field is visualized by calculating the lines around one particle,");
		lblNewLabel_8.setBounds(6, 275, 668, 16);
		contentPanel.add(lblNewLabel_8);
		
		JLabel lblNewLabel_9 = new JLabel(" then going to the next and so on. The shuffle order button changes this order. ");
		lblNewLabel_9.setBounds(6, 289, 668, 16);
		contentPanel.add(lblNewLabel_9);
		
		JLabel lblNewLabel_10 = new JLabel("It could possibly change what is displayed, it could possibly not.");
		lblNewLabel_10.setBounds(6, 303, 668, 16);
		contentPanel.add(lblNewLabel_10);
		
		JLabel lblNewLabel_11 = new JLabel("The shuffle theta button changes the spatial arrangement of the lines.");
		lblNewLabel_11.setBounds(6, 342, 668, 16);
		contentPanel.add(lblNewLabel_11);
		
		JLabel lblNewLabel_12 = new JLabel("The clear theta button returns the spatial arrangement back to its original form.");
		lblNewLabel_12.setBounds(6, 391, 668, 16);
		contentPanel.add(lblNewLabel_12);
		
		JLabel lblNewLabel_13 = new JLabel("The clear button clears the entire panel.");
		lblNewLabel_13.setBounds(6, 439, 668, 16);
		contentPanel.add(lblNewLabel_13);
		
		JLabel lblNewLabel_14 = new JLabel("If the application is not responding, it is most likely calculating");
		lblNewLabel_14.setBounds(6, 69, 668, 16);
		contentPanel.add(lblNewLabel_14);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				/**
				 * Closes the dialog when the person has read enough
				 */
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) 
					{
						dialog.dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
}
