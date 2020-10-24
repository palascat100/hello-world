package electrictest;

/*
 * imports necessary classes
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.RepaintManager;

/*
 * The panel for the main application
 */

public class RealPanelBasic extends JPanel {
	/*
	 * Declaration of global variables: g2d and g1 being graphics management, infoex transferring 
	 * data, track determines if the panel needs to be repainted, set determines whether or not the 
	 * order of charges needs to be shuffled, set2 determines if the theta needs to be shifted over,
	 * track4 keeps track of whether or not the panel needs to be repainted, numcharge is the number of charges,
	 * points keeps track of all the points and nums keeps track of how many points are in each line.
	 */
	Graphics2D g2d;
	InfoExBasic infoex;
	Graphics g1;
	int track;
	double set;
	double set2=1;
	int track4=0;
	int numcharge;
	 ArrayList<Double> points= new ArrayList<Double>();
	 ArrayList<Double> nums= new ArrayList<Double>();
	/**
	 * Create the panel.
	 */
	public RealPanelBasic(InfoExBasic info) 
	{
			infoex=info;
			set=1;
			
	}
	
	/*
	 * Creates the grid lines and determines if the electric field lines
	 * need to be recalculated/redrawn
	 */
	 @Override
	 public void paintComponent(Graphics g) 
	 {
		 track=infoex.getrepaint();
		 
		 super.paintComponent(g);
		 int i;
		 g2d=(Graphics2D) g;
		
		 g.setColor(Color.LIGHT_GRAY);
		 for(i=24;i<489;i+=24)
		 {
			
			 g2d.drawLine(i, 0, i, 462);	
		 }
		 for(i=24;i<462;i+=24)
		 {
			
			 g2d.drawLine(0, i, 489, i);	
		 }
		 g.setColor(Color.black);
		 g2d.drawLine(20, 20, 44, 20);
		 if(track==1)
		 {
		 
		 drawLines(g);
		 set=1.0;
		 }
		 else
		 {
			 if(track==3)
			 {
				 RepaintManager.currentManager(this).markCompletelyClean(this);
			 }
			 else
			 {
				drawLines2(g);
			 	RepaintManager.currentManager(this).markCompletelyClean(this);
			 }
		 }
	 }

	 /*
	  * Calculates and draws the lines
	  */
	 public void drawLines(Graphics g)
	 {
		 numcharge=0;
		 if(set>2)
		 {
			 infoex.shuffleOrder();
		 }
		 points.clear();
		 nums.clear();
		 double[] loc= new double[2];
		 double theta,thetastep;
		 ArrayList<Double> list= new ArrayList<Double>();
		 ArrayList<Double> mustline= new ArrayList<Double>();
		 ArrayList<Double> line= new ArrayList<Double>();
		 ArrayList<Double> charge= new ArrayList<Double>();
		 ArrayList<Double> location= new ArrayList<Double>();
		 ArrayList<Double> numline= new ArrayList<Double>();
		 ArrayList<Double> thetatrack= new ArrayList<Double>();
		 infoex.resetlines();
		 g2d=(Graphics2D) g;
		 int i,f,e;
		 double h,j,k,l;
		 int num;
		 f=0;
		 j=1;
		 l=0;
		 num=0;
		 list=infoex.getInfo2();
		 mustline=infoex.getInfo();
		 location=infoex.getInfo3();
		 charge.clear();
		 numcharge=0;
		 if(list.size()!=0)
		 {
			 
		 for(i=0;i<mustline.size();i++)
		 {
			 numcharge+=mustline.get(i);
			 if(list.get(2*(i))>0)
			 {
				 charge.add(1.6);
			 }
			 else
			 {
				 charge.add(-1.6);
			 }
		 }
		
		 
		 num=mustline.size();
		 for(f=0;f<num;f++)
		 {
			 thetatrack.clear();
			 numline=infoex.getInfo4();
			 thetastep=360.0/mustline.get(f);
			 theta=0;
			 if(set2!=1)
			 {
				 theta=thetastep*(1/(set2));
			 }
			 if(set!=1)
			 {
				 theta+=thetastep*(1/(set));
			 }
			 h=mustline.get(f)-numline.get(f);
			 l=0.0;
			 j=1;
			 int m;
			 for(e=0;e<h;e++)
			 {
				 for(m=0;m<thetatrack.size();m++)
				 {
					 if(thetatrack.get(m)==theta)
					 {
						 theta+=thetastep/(j);
					 }
				 }
				 loc[0]=(location.get(2*f)+list.get(1+(2*f))*(Math.cos(Math.toRadians(theta)))/2);
				 loc[1]=(location.get(2*f+1)+list.get(1+(2*f))*(Math.sin(Math.toRadians(theta)))/2);
				 line=infoex.getLines(loc,charge.get(f),f);
				 if(line.size()==0)
				 {
					
					 e--;
					
				 }
				 else
				 {
					 points.addAll(line);
					 nums.add((double) (line.size()));
					 for(k=0;k<line.size()/4;k++)
					 {
						 Line2D line1 = new Line2D.Double((line.get((int)(4*k))*24),(line.get((int)(4*k+1)))*24,line.get((int)(4*k+2))*24,line.get((int)(4*k+3))*24);
						 g2d.draw(line1);
					 }
					
					
					 
				 }
				 l++;
				 thetatrack.add(theta);
				 if(l>mustline.get(f)*j-1)
				 {
					 j++;
					 theta=0;
					 theta+=thetastep/(j);
					 if(l>50)
					 {
						 setSet();
						 drawLines(g);
					 }
				 }
				 else
				 {
					 theta+=thetastep;
				 }
				 if(theta>=360)
				 {
					 theta-=360;
				 }
				 int didShuffle=infoex.didShuffle();
				 if(didShuffle!=0)
				 {
					 infoex.redo();
				 }
				 
				
				
			 
		 }
		 }
	 }
     if(track4==0)
     {
    	 infoex.setrepaint(0);
     }
     	
	 }
	 
	
	 /*
	  * Sets the repaint based on whether or not the panel needs to be
	  * repainted
	  */
	 public void setRepaint(int num)
	 {
		 track4=num;
	 }
	 
	 /*
	  * Draws the beginning of the lines to maintain the entire line without having 
	  * to recalculate everything
	  */
	 public void drawLines2(Graphics g)
	 {
		 int k,beginningnum,l;
		 beginningnum=0;
		 for(k=0;k<nums.size();k++)
		 {
			 
			for(l=(beginningnum+10)/4;l<(nums.get(k)/4+beginningnum)/4;l++)
			{
				Line2D line1 = new Line2D.Double((points.get((int)(4*l))*24),(points.get((int)(4*l+1)))*24,points.get((int)(4*l+2))*24,points.get((int)(4*l+3))*24);
				g2d.draw(line1);
			}
			beginningnum+=nums.get(k);
		 }
	 }
	 /*
	  * Keeps track of how many times the calculation loop has gone through
	  */
	 public void setSet()
	 {
		 set++;
		 
		 
	 }
	 
	 /*
	  * Terminates calculations and clears panel if a solution can not be calculated
	  */
	 public void terminate()
	 {
		 infoex.setrepaint(3);
		  JOptionPane.showMessageDialog(this,"Computer was unable to calculate valid solution");
		  infoex.clearLists();
		  this.removeAll();
		  points.clear();
		  nums.clear();
		  infoex.setrepaint(1);
		  infoex.deallabel();
		  this.repaint();
		  
	 }
	 
	 /*
	  * Clears panel if the user chooses to
	  */
	 public void clear()
	 {
		 infoex.clearLists();
		 this.removeAll();
		 infoex.setrepaint(1);
		 this.repaint();
		 points.clear();
		 nums.clear();
		 infoex.deallabel();
		 infoex.setrepaint(0);

	 }
	 
	 /*
	  * manages set2, the amount the theta is shifted over depending
	  * on the number of times the loop has gone through
	  */
	 public void set2()
	 {
		 set2++;
	 }
	 
	 public void set2_2()
	 {
		 set2=1;
	 }


	 
	

}
