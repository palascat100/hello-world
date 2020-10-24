package electrictest;

/*
 * Imports necessary classes
 */
import java.awt.Dimension;   

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.Color;
 
public class ChargesBasic2 extends JButton implements MouseListener,MouseMotionListener
{
	/**
	 * This is the class that creates the charges for the dialog where you can drag them around
	 */
	/**
	 * Declaration of variables including arrays to contain data about the charge, and reference 
	 * variables for the frame and panel the charge will be on. Frame1 is for the frame, panel1 is for the panel, screenWidth 
	 * and screenHeight are the width and height of the screen, num1 is for identifying the position of it's information in the 
	 * location and stat arrays present in the InfoExBasic class (which are stored in array data and dataloc), elec is the "masterclass"
	 * infoex is where all information is stored, the icon is the image displayed by the charge, info and location are for storing 
	 * where the mouse if and actualSize is in case the user has changed how large the charge is.
	 */
	PointerInfo info;
	Point location;
	private JDialog frame1;
	private JPanel panel1;
	Dimension actualSize;
	int screenWidth,screenHeight;
	double num1;
	double[] data;
	double[] dataloc;
	ElectricFieldBasic elec;
	InfoExBasic infoex;
	ImageIcon icon= new ImageIcon(this.getClass().getResource("/negativeFixed.png"));
	
	
	/**
	 * Create the application.
	 */
	public ChargesBasic2(JDialog frame, JPanel panel,double num, ElectricFieldBasic electric,InfoExBasic ex) 
	{
		setForeground(new Color(204, 255, 255));
		setBackground(new Color(204, 255, 255));
		frame1=frame;
		panel1=panel;
		addMouseListener(this);
		addMouseMotionListener(this);
		data= new double[4];
		dataloc= new double[3];
		num1=num;
		data[2]=num1;
		dataloc[2]=num1;
		elec=electric;
		infoex=ex;
		infoex.setrepaint(0);
		screenWidth=489;
		screenHeight=485;
	}


	public void mouseClicked(MouseEvent e)
	{
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}
	
	/**
	 * When released checks to see if it's not out of the screen, otherwise it removes them
	 */
	@Override
	public void mouseReleased(MouseEvent e) 
	{
		
		  if(this.getX()<0 && this.getX()>screenWidth-this.getWidth()&&this.getY()<0&&this.getY()>screenHeight-this.getHeight())
		  {
			  infoex.remove(data);
			  infoex.removeLoc(dataloc);
			  elec.setnum(true);
			  infoex.setrepaint(1);
			  panel1.remove(this);
			  panel1.repaint();
			  
			  
		  }
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	/**
	 * Allows use to drag the charge around to where they would like to place them 
	 * and if outside the screen removes the charge. Otherwise, it reports the change to 
	 * the info class.
	 */
	@Override
	public void mouseDragged(MouseEvent e) 
	{
		
		final PointerInfo pointerInfo = MouseInfo.getPointerInfo();
		if ( pointerInfo != null ) // Mouse not present?
		{
		  screenWidth=489;
		  screenHeight=485;
		  location = MouseInfo.getPointerInfo().getLocation();
		  if(this.getX()>=0 && this.getX()<=screenWidth-this.getWidth()&&this.getY()>=23&&this.getY()<=screenHeight-this.getHeight()+23)
		  {
			  this.setBounds((int)(-frame1.getX()-panel1.getX()+location.getX()-(this.getWidth()>>1)), (int)(-frame1.getY()-panel1.getY()+location.getY()-23-(this.getHeight()>>1)),(int)(this.getWidth()),(int)(this.getHeight()));
			  dataloc[0]=this.getX()+(this.getWidth()>>1);
			  dataloc[1]=this.getY()+(this.getHeight()>>1);
			  infoex.modifyloc(dataloc);
		  }
		  else
		  {
			  infoex.remove(data);
			  infoex.removeLoc(dataloc);
			  elec.setnum(true);
			  infoex.setrepaint(1);
			  panel1.remove(this);
		  }	
		  panel1.repaint();
		  
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) 
	{
		
		
	}
	
	
	
}
	