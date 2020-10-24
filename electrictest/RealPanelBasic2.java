package electrictest;

import java.awt.Color;  
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;


public class RealPanelBasic2 extends JPanel {
	
	Graphics2D g2d;
	/**
	 * Create the panel.
	 */
	public RealPanelBasic2() 
	{
		this.setBounds(0, 0, 489, 485);
	}
	
	 @Override
	 public void paintComponent(Graphics g) 
	 {
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
	 }
}
