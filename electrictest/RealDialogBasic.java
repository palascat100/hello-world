package electrictest;
/*
 * Imports necessary classes
 */

import javax.swing.JFrame;   
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/*
 * Dialog class that appears when the user clicks the charge to change its characteristics
 */

public class RealDialogBasic extends JDialog 
{
	/*
	 * Imports global variables: data being an array that carries what charge is on the 
	 * particle and it's size, chargeText holds the charge of the particle, sizeText 
	 * holds the size of the particle, (chargeLabel, sizeLabel, and all the JButtons 
	 * are for design), dialog is the actual application, and infoex holds all the info 
	 */
	private double [] data;
	private JTextField chargeText;
	private JTextField sizeText;
	JLabel chargeLabel;
	JLabel sizeLabel;
	JButton addCharge;
	JButton subCharge;
	JButton subRadius;
	JButton addRadius;
	JButton ok;
	JButton no;
	JButton beGONE;
	JDialog dialog= new JDialog();
	InfoExBasic infoex;
	
	public RealDialogBasic(JFrame parent,double num, ChargesBasic charge, double[] initial,InfoExBasic ex) 
	{
		/*
		 * assigns initial values to variables and creates application
		 */
		infoex=ex;
		infoex.setrepaint(0);
	    JPanel panel = new JPanel();
	    dialog.getContentPane().add(panel);
	    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	    dialog.setVisible(true);
	    dialog.setLocationRelativeTo(null);
	    panel.setLayout(null);
	    dialog.setSize(450,300);
	    panel.setVisible(true);
		data=new double[4];
		data[0]=initial[0];
		data[1]=initial[1];
		data[2]=num;
		data[3]=initial[3];
		chargeLabel = new JLabel("Charge(nC)");
		chargeLabel.setBounds(6, 39, 84, 16);
		panel.add(chargeLabel);
		
		/*
		 * Displays the charge and is able to be manipulated by the user
		 */
		chargeText = new JTextField();
		chargeText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				data[0]=Double.parseDouble(chargeText.getText());
				if(data[0]>10)
				{
					data[0]=10;
				}
				if(data[0]<-10)
				{
					data[0]=-10;
				}
				if(data[0]==0)
				{
					data[0]=1;
				}
				chargeText.setText(Double.toString(data[0]));
			}
		});
		
		/*
		 * Label that indicates what the text field/buttons do
		 */
		chargeText.setBounds(134, 36, 249, 21);
		chargeText.setText(Double.toString(data[0]));
		panel.add(chargeText);
		chargeText.setColumns(10);
		
		/*
		 * Label that indicates what the text field/buttons do
		 */
		sizeLabel = new JLabel("Diameter(nm)");
		sizeLabel.setBounds(6, 131, 116, 16);
		panel.add(sizeLabel);
		
		/*
		 * Displays the size and is able to be manipulated by the user
		 */
		sizeText = new JTextField();
		sizeText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				data[1]=Double.parseDouble(sizeText.getText());
				if(data[1]>5)
				{
					data[1]=5;
				}
				if(data[1]<1)
				{
					data[1]=1;
				}
				sizeText.setText(Double.toString(data[1]));
			}
		});
		sizeText.setBounds(134, 128, 249, 21);
		sizeText.setText(Double.toString(data[1]));
		panel.add(sizeText);
		sizeText.setColumns(10);
		
		/*
		 * Increases charge by 1
		 */
		addCharge = new JButton("+");
		addCharge.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if(data[0]==-1)
				{
					data[0]+=2;
				}
				else
				{
					data[0]+=1;
					if(data[0]>10)
					{
						data[0]=10;
					}
				}
				chargeText.setText(Double.toString(data[0]));
			}
		});
		addCharge.setBounds(134, 69, 117, 29);
		panel.add(addCharge);
		
		/*
		 * Decreases charge by 1
		 */
		subCharge = new JButton("-");
		subCharge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if(data[0]==1)
				{
					data[0]-=2;
				}
				else
				{
					data[0]-=1;
					if(data[0]<-10)
					{
						data[0]=-10;
					}
				}
				chargeText.setText(Double.toString(data[0]));
			}
		});
		
		subCharge.setBounds(266, 69, 117, 29);
		panel.add(subCharge);
		
		/*
		 * Decreases radius by 1
		 */
		subRadius = new JButton("-");
		subRadius.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
					if(data[1]==1) 
					{
						
					}
					else
					{
						data[1]-=1;
					}
					sizeText.setText(Double.toString(data[1]));
				}
				catch (Exception e1)
				{
					
				}
			}
		});
		subRadius.setBounds(266, 161, 117, 29);
		panel.add(subRadius);
		
		/*
		 * Increases radius by 1
		 */
		addRadius = new JButton("+");
		addRadius.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
					data[1]+=1;
					if(data[1]>5)
					{
						data[1]=5;
					}
					sizeText.setText(Double.toString(data[1]));	
				}
				catch (Exception e1)
				{
					
				}
			}
		});
		addRadius.setBounds(134, 161, 117, 29);
		panel.add(addRadius);

		/*
		 * Closes application and indicates the user would like to keep
		 * the values they inputed
		 */
	    ok = new JButton("Ok");
	    ok.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) 
	    	{
	    		 data[0] = Double.parseDouble(chargeText.getText());
		         data[1] = Double.parseDouble(sizeText.getText());
		     
		         charge.getStats(data);
		         data[3]+=1.0;
		         dialog.dispose(); 
	    	}
	    });
		ok.setBounds(24, 218, 117, 29);
		panel.add(ok);
		
		/*
		 * Closes application and indicates the user would not like to keep
		 * the values they inputed
		 */
		no = new JButton("Cancel");
		no.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				
				dialog.dispose();
			}
		});
		no.setBounds(172, 218, 117, 29);
		panel.add(no);
		
		/*
		 * Closes application and indicates the user would like to delete
		 * the charge
		 */
		beGONE = new JButton("Delete");
		beGONE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				data[0] = -1000.0;
		        data[1] = -1000.0;
		        charge.getStats(data);
		        dialog.dispose();
			}
		});
		beGONE.setBounds(308, 218, 117, 29);
		panel.add(beGONE);
	}
}
