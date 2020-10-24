package electrictest;

/*
 * Imports necessary classes
 */
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
 
public class ChargesBasic extends JButton 
{
	/**
	 * This is the class that creates the charges for the main screen
	 */
	/**
	 * Declaration of variables including arrays to contain data about the charge, and reference 
	 * variables for the frame and panel the charge will be on. Frame1 is for the frame, panel1 is for the panel, screenWidth 
	 * and screenHeight are the width and height of the screen, num1 is for identifying the position of it's information in the 
	 * location and stat arrays present in the InfoExBasic class (which are stored in array data and dataloc), elec is the "masterclass"
	 * infoex is where all information is stored, the icon is the image displayed by the charge, and charge is used to avoid 
	 * static reference errors. 
	 */
	private JFrame frame1;
	private RealPanelBasic panel1;
	int screenWidth,screenHeight;
	double num1;
	double[] data;
	double[] dataloc;
	ElectricFieldBasic elec;
	InfoExBasic infoex;
	ImageIcon icon= new ImageIcon(this.getClass().getResource("/negativeFixed.png"));
	ChargesBasic charge=this;
	/**
	 * Create the application.
	 */
	public ChargesBasic(JFrame frame, RealPanelBasic panel,double num, ElectricFieldBasic electric,InfoExBasic ex,double track) 
	{
		/*
		 * Intakes all the info about what frame/panel the charge is on and sets it equal
		 * to their respective declared variables above
		 */
		frame1=frame;
		panel1=panel;
		num1=num;
		infoex=ex;
		elec=electric;
		/**
		 * When clicking the charges creates a dialog that allows you to change their characteristics
		 */
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				ex.setrepaint(0);
				panel1.setRepaint(0);
				RealDialogBasic dialog= new RealDialogBasic(frame1,num1,charge,data,infoex);
				
			}
		});
		/*
		 * Gets all the stats of the charges in case this is not the first time this charge is being added to the panel
		 * If it is the first time the spot that the charge supposedly should fill in the array will not exist
		 */
		ArrayList<Double> list= new ArrayList<Double>();
		list=infoex.getInfo2();
		data= new double[4];
		dataloc= new double[3];
		dataloc[2]=num1;
		if(track==1)
		{
		/*
		 * Set up of the charges if it is their first time being added to the panel with default conditions and reports them to 
		 * the infoex class
		 */
		this.setBounds(245, 231, 24, 24);
		data[0]=1.0;
		data[1]=1.0;
		data[2]=num1;
		data[3]=2.0;
		dataloc[0]=245+(this.getWidth()>>1);
		dataloc[1]=231+(this.getHeight()>>1);
		infoex.getCRN(data);
		infoex.getLoc(dataloc);
		this.setIcon(new ImageIcon(this.getClass().getResource("/positiveFixed.png")));
		charge.setBorderPainted(false);
		 charge.setContentAreaFilled(false);
	     charge.setFocusPainted(false);
		 charge.setOpaque(false);
	    }
		else
		{
			/*
			 * Set up of the charges if it is NOT their first time being added to the panel with their recorded conditions
			 */
			data[0]=list.get((int)((num1-1)*2));
			data[1]=list.get((int)((num1-1)*2+1));
			data[2]=num1;
			data[3]=2.0;
		}
		/*
		 * Makes the charge background transparent regardless of it is the first time being added or not
		 */
		charge.setBorderPainted(false);
		 charge.setContentAreaFilled(false);
	     charge.setFocusPainted(false);
	     charge.setOpaque(false);
	}

	/*
	 * After modification allows the charge to get its new stats and modify itself to fit those stats
	 */
	public void getStats(double[] array)
	{
		/*
		 * If the charge is chosen to be deleted
		 * If gets rid of all of its info in the arrays
		 * and tells the panel to repaint itself since one of the charges has not been removed
		 * after removing the object
		 */
		data=array;
		if(data[0]==-1000 && data[1]==-1000)
		{
			infoex.remove(data);
			infoex.removeLoc(dataloc);
			elec.setnum(true);
			elec.redoCharges();
			panel1.remove(this);
			panel1.repaint();
		}
		else
		{
			/*
			 * If the charge is chosen to be modified
			 * It changes the info in the arrays and asks
			 * the panel to repaint itself
			 */
			panel1.setRepaint(1);
			infoex.modify(data);
			dataloc[0]=this.getX()+this.getWidth()/2;
			dataloc[1]=this.getY()+this.getHeight()/2;
			infoex.modifyloc(dataloc);	
			elec.redoCharges();
		}
		
	}
}
	