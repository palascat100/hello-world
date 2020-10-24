package electrictest;
    
/*
 * Imports necessary classes
 */
import javax.swing.JDialog;
import javax.swing.ImageIcon;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Image;

/*
 * Dialog that appears when the user tries to change the position of the 
 * charges
 */

public class RealDialogBasic2 extends JDialog implements WindowListener
{
	/*
	 * Declares variables, including the frame and panel all the items lie on, infoex 
	 * transmits information, electric is the main class, and then a this variable to 
	 * prevent static reference errors.  
	 */
	JDialog frame;
	RealPanelBasic2 panel= new RealPanelBasic2();
	InfoExBasic infoex;
	ElectricFieldBasic electric;
	RealDialogBasic2 basic=this;
	
	/**
	 * Create the application.
	 */

	public RealDialogBasic2(InfoExBasic ex,ElectricFieldBasic elec) 
	{
		ex.setrepaint(0);
		infoex=ex;
		electric=elec;
		initialize();
		whereCharges();
	}
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		frame = new JDialog();
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) 
			{
				infoex.setrepaint(1);
				electric.redoCharges();
				basic.dispose();
				
			}
			@Override
			public void windowActivated(WindowEvent e) 
			{
				
			}
		});
		frame.setBounds(0, 0, 489, 485);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.getContentPane().add(panel);
		panel.setBounds(0,0,489,462);
		panel.setBackground(new Color(204, 255, 255));
		panel.setLayout(null);
		panel.setVisible(true);
	}
	
	/*
	 * Sets up the panel with the charges in the spots they were on the original 
	 * panel
	 */
	private void whereCharges()
	{
		
		 ImageIcon icon= new ImageIcon(this.getClass().getResource("/negativeFixed.png"));
		 int i;
		 ArrayList<Double> list= new ArrayList<Double>();
		 ArrayList<Double> location= new ArrayList<Double>();
		 list=infoex.getInfo2();
		 location=infoex.getInfo3();
		 if(list.size()!=0)
		 {
	     for(i=0;i<location.size()/2;i++)
		 {
			 ChargesBasic2 charge= new ChargesBasic2(frame,panel,i+1,electric,infoex);
			 panel.add(charge);
			 charge.setBorderPainted(false);
			 charge.setContentAreaFilled(false);
		     charge.setFocusPainted(false);
			 charge.setOpaque(false);
			 if(list.get(i*2)>0)
				{
					charge.setIcon(new ImageIcon(this.getClass().getResource("/positiveFixed.png")));
					icon=new ImageIcon(this.getClass().getResource("/positiveFixed.png"));
				
				}
				else
				{
					charge.setIcon(new ImageIcon(this.getClass().getResource("/negativeFixed.png")));
					icon=new ImageIcon(this.getClass().getResource("/negativeFixed.png"));
				
				}
			    charge.setBounds((int)(24*location.get(i*2)-list.get((i*2+1))*12),(int)(24*location.get(i*2+1)-list.get((i*2+1))*12), (int)(24*list.get(i*2+1)),(int)(24*list.get(i*2+1)));
				Image newImage = icon.getImage().getScaledInstance((int)(24*list.get(i*2+1)),(int)(24*list.get(i*2+1)), Image.SCALE_DEFAULT);
				icon.setImage(newImage);
				charge.setIcon(icon);
				charge.setVisible(true);
		 }
		 }
		 panel.repaint();
	}

	@Override
	public void windowOpened(WindowEvent e) 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void windowClosing(WindowEvent e) 
	{
		// TODO Auto-generated method stub
	}
    

	
	@Override
	public void windowClosed(WindowEvent e) 
	{
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
