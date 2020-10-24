package electrictest;

import java.awt.Color; 
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.RepaintManager;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
 

public class ElectricFieldBasic 
{
	/*
	 * Declares all needed global variables
	 */
	private JFrame frame;
	private JTextField numLine;
	JButton addLine;
	JButton subLine;
	JButton addcharge;
	JLabel labellines;
	JLabel labellines2;
	int trackLines=4,maxcharge;
	int keepTrack=1;
	ElectricFieldBasic elec=this;
	InfoExBasic infoex=new InfoExBasic(elec);
	RealPanelBasic panel=new RealPanelBasic(infoex);
	JLabel scale = new JLabel("1 nm= 1 box");
	Graphics g1;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					ElectricFieldBasic window = new ElectricFieldBasic();
					window.frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ElectricFieldBasic() 
	{
		initialize();
		Help help= new Help();
		help.setVisible(true);
		help.setLocationRelativeTo(null);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() 
	{
		infoex.getNumLines(trackLines);
		infoex.getNum(keepTrack-1);
		frame = new JFrame();
		frame.setBounds(0, 0, 678, 502);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		addLine = new JButton("+");
		//when user wants more lines
		addLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					panel.setRepaint(0);
					trackLines+=1;
					if(trackLines>=1)
					{
						numLine.setText(Integer.toString(trackLines));
						if(trackLines>maxcharge)
						{
							trackLines=maxcharge;
							numLine.setText(Integer.toString(trackLines));
						}
					}
					else
					{
						trackLines=1;
						numLine.setText(Integer.toString(trackLines));
	
					}
					infoex.getNumLines(trackLines);
					infoex.setrepaint(1);
					panel.repaint();
				}
				catch (Exception e1)
				{
					
				}
			}
		});
		scale.setBounds(20, 40, 82, 16);
		panel.add(scale);
		
		
		scale.setHorizontalAlignment(SwingConstants.CENTER);
		addLine.setBounds(511, 70, 20, 20);
		frame.getContentPane().add(addLine);
		
		//how user sees num of lines or can put in their own
		numLine = new JTextField();
		numLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
					panel.setRepaint(0);
					trackLines=Integer.parseInt(numLine.getText());
					if(trackLines<1)
					{
						trackLines=1;
						numLine.setText(Integer.toString(trackLines));
					}
					if(trackLines>maxcharge)
					{
						trackLines=maxcharge;
						numLine.setText(Integer.toString(trackLines));
					}
					infoex.getNumLines(trackLines);
					infoex.setrepaint(1);
					panel.repaint();
				}
				catch (Exception e1)
				{
					
				}
			}
		});
		numLine.setBounds(543, 70, 78, 20);
		numLine.setText(Integer.toString(trackLines));
		frame.getContentPane().add(numLine);
		numLine.setColumns(10);
		
		//when user wants to subtract lines
		subLine = new JButton("-");
		subLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
					panel.setRepaint(0);
					trackLines-=1;
					if(trackLines>=1)
					{
						numLine.setText(Integer.toString(trackLines));
					}
					else
					{
						trackLines=1;
						numLine.setText(Integer.toString(trackLines));
					}
					infoex.getNumLines(trackLines);
					infoex.setrepaint(1);
					panel.repaint();
				}
				catch (Exception e1)
				{
					
				}
			}
		});
		subLine.setBounds(633, 70, 20, 20);
		frame.getContentPane().add(subLine);
		
		//when user wants to add charges
		addcharge = new JButton("Add Charge");
		addcharge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if(keepTrack<6)
				{
					ChargesBasic charge= new ChargesBasic(frame,panel,keepTrack,elec,infoex,1);
					panel.add(charge);
					charge.setBorderPainted(false);
					charge.setContentAreaFilled(false);
					charge.setFocusPainted(false);
					charge.setOpaque(false);
					infoex.getNumLines(trackLines);
					keepTrack+=1;
					infoex.getNum(keepTrack-1);
					infoex.setrepaint(1);
					panel.repaint();
					calcmax();
				}
			}
		});
		addcharge.setBounds(511, 124, 142, 29);
		frame.getContentPane().add(addcharge);
		
		//labels to show what the buttons do
		labellines = new JLabel("Number of lines ");
		labellines.setHorizontalAlignment(SwingConstants.CENTER);
		labellines.setBounds(511, 23, 142, 15);
		frame.getContentPane().add(labellines);
		
		labellines2 = new JLabel("per nc");
		labellines2.setHorizontalAlignment(SwingConstants.CENTER);
		labellines2.setBounds(511, 40, 142, 15);
		frame.getContentPane().add(labellines2);
		
		//panel that displays charges and electric field
		frame.getContentPane().add(panel);
		panel.setBounds(10, 10, 489, 462);
		panel.setBackground(new Color(204, 255, 255));
		panel.setLayout(null);
		
		//button that allows user to change where electric charges are
		JButton PosChange = new JButton("Change Positions");
		PosChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				infoex.setrepaint(0);
				panel.setRepaint(0);
				RealDialogBasic2 dialog= new RealDialogBasic2(infoex,elec);
				RepaintManager.currentManager(panel).markCompletelyClean(panel);
			}
		});
		PosChange.setBounds(511, 174, 142, 29);
		frame.getContentPane().add(PosChange);
		
		//button that allows user to change which charge's lines are calculated first
		JButton btnNewButton = new JButton("Shuffle Order");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				infoex.shuffleOrderWant();
			}
		});
		btnNewButton.setBounds(511, 227, 142, 29);
		frame.getContentPane().add(btnNewButton);
		
		//button that allows user to change the line distribution
		JButton btnNewButton_1 = new JButton("Shuffle Theta");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				panel.set2();
				infoex.setrepaint(1);
				panel.repaint();
			}
		});
		btnNewButton_1.setBounds(511, 279, 142, 29);
		frame.getContentPane().add(btnNewButton_1);
		
		//button that allows user to clear the whole panel
		JButton btnNewButton_2 = new JButton("Clear");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				panel.clear();
			}
		});
		btnNewButton_2.setBounds(511, 372, 142, 29);
		frame.getContentPane().add(btnNewButton_2);
		
		//button that allows user to get instructions on how to use the application
		JButton btnNewButton_3 = new JButton("Help");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				Help help= new Help();
				help.setVisible(true);
			}
		});
		btnNewButton_3.setBounds(511, 428, 142, 29);
		frame.getContentPane().add(btnNewButton_3);
		
		//button that allows user to makes the line distribution go back to its normal behavior
		JButton btnNewButton_4 = new JButton("Clear Theta");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				panel.set2_2();
				infoex.setrepaint(1);
				panel.repaint();
			}
		});
		btnNewButton_4.setBounds(511, 331, 142, 29);
		frame.getContentPane().add(btnNewButton_4);
		

		panel.setVisible(true);
		calcmax();
		
	}
	
	//function that is used after a charge is deleted to make sure that an out of index error does not occur
	public void setnum(boolean subtract)
	{
		if(subtract==true)
		{
			keepTrack-=1;
			infoex.getNum(keepTrack-1);
			
		}
	}
	
	//method that calculates how many lines per nc of charge so that the screen is not filled with 500 lines
	public void calcmax()
	{
		double track=infoex.returnmax();
		if(track!=0 && keepTrack!=1)
		{
			maxcharge=(int)(Math.round(32/(track*(keepTrack-1))));
			if(maxcharge>10)
			{
				maxcharge=10;
			}
			if(maxcharge<2)
			{
				maxcharge=2;
			}
		}
		else
		{
			maxcharge=10;
		}
		trackLines=Integer.parseInt(numLine.getText());
		if(trackLines<1)
		{
			trackLines=1;
			numLine.setText(Integer.toString(trackLines));
		}
		if(trackLines>maxcharge)
		{
			trackLines=maxcharge;
			numLine.setText(Integer.toString(trackLines));
			infoex.setrepaint(1);
			panel.repaint();
		}
		infoex.getNumLines(trackLines);
	}
	
	//redos charges position after the position of one or more is changed
	public void redoCharges()
	{
	     panel.setRepaint(0);
		 panel.removeAll();
		 scale.setHorizontalAlignment(SwingConstants.CENTER);
		 scale.setBounds(6, 31, 82, 16);
		 panel.add(scale);
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
			 ChargesBasic charge= new ChargesBasic(frame,panel,i+1,elec,infoex,2);
			 charge.setBounds((int)(24*location.get(i*2)-list.get((i*2+1))*12),(int)(24*location.get(i*2+1)-list.get((i*2+1))*12), (int)(24*list.get(i*2+1)),(int)(24*list.get(i*2+1)));
			 panel.add(charge);
			 charge.setVisible(true);
			 panel.repaint();
			 icon=getImage(list.get(i*2+1),list.get(i*2));
			 charge.setIcon(icon);
			 charge.setContentAreaFilled(false);
		     charge.setFocusPainted(false);
			 charge.setBorderPainted(false);
			 charge.setOpaque(false);
			 panel.repaint();
		 }
		 }
		 infoex.setrepaint(1);
		 panel.repaint();
	}
	
	//method that determines the image of the charge depending on charge and size
	public ImageIcon getImage(double num,double num2)
	{
		ImageIcon icon;
		icon=new ImageIcon(this.getClass().getResource("/positiveFixed.png"));
		if(num2>0)
		{
			if(num==1)
			{
				icon=new ImageIcon(this.getClass().getResource("/positiveFixed.png"));
			}
			if(num==2)
			{
				icon=new ImageIcon(this.getClass().getResource("/positiveFixed2.png"));
			}
			if(num==3)
			{
				icon=new ImageIcon(this.getClass().getResource("/positiveFixed3.png"));
			}
			if(num==4)
			{
				icon=new ImageIcon(this.getClass().getResource("/positiveFixed4.png"));
			}
			if(num==5)
			{
				icon=new ImageIcon(this.getClass().getResource("/positiveFixed5.png"));
			}
		}
		else
		{
			if(num==1)
			{
				icon=new ImageIcon(this.getClass().getResource("/negativeFixed.png"));
			}
			if(num==2)
			{
				icon=new ImageIcon(this.getClass().getResource("/negativeFixed2.png"));
			}
			if(num==3)
			{
				icon=new ImageIcon(this.getClass().getResource("/negativeFixed3.png"));
			}
			if(num==4)
			{
				icon=new ImageIcon(this.getClass().getResource("/negativeFixed4.png"));
			}
			if(num==5)
			{
				icon=new ImageIcon(this.getClass().getResource("/negativeFixed5.png"));
			}
		}
		return icon;
	}
	
	//clears panel when calculations are taking too long
	public void terminatePanel()
	{
		panel.terminate();
	}
	
	//when screen is cleared makes sure the label with the scale is not also removed
	public void deallabel2()
	{
		 panel.add(scale);
	}
	
	
	
	
	
	
}
