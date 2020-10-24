package electrictest;

/*
 * Imports necessary classes
 */
import java.util.ArrayList; 
/*
 * Class that performs all calculations and keeps track of all details of charge location
 * and values
 */
public class InfoExBasic
{
	/**
	 * Declaration of variables: arraylists that keep track of charge locations and charge stats
	 */
	 static ArrayList<Double> list= new ArrayList<Double>();
	 static ArrayList<Double> listloc= new ArrayList<Double>();
	 static ArrayList<Double> line= new ArrayList<Double>();
	 static ArrayList<Double> numline= new ArrayList<Double>();
	 static ArrayList<Double> mustline= new ArrayList<Double>();
	 int numcharge=0,keeptrack=0,numberline,numtrack,track,track2;
	 double[] location=new double[2];
	 double q;
	 ElectricFieldBasic elec;
	 double theta;
	 int trackrepaint=0;
     int numtimes;
     int keeptrack2=0;
	
     /*
      * Initiates the class, clearing all lists and setting variables to default values.
      */
	public InfoExBasic(ElectricFieldBasic el)
	{
		numline.clear();
		mustline.clear();
		track2=0;
		elec=el;
	}
	/*
     * When a charge is added it adds the default values a charge is given when a charge 
     * is first created
     */
	public void getCRN(double[] crn)
	{
	    list.add(crn[0]);
	    list.add(crn[1]);
		numline.add(0.0);
		mustline.add(Math.abs(crn[1])*numberline);
		elec.calcmax();

	}
	/*
     * When a charge is deleted it removes all the data
     * and recalculates the number of max lines per charge
     */
	public void remove(double[] remove)
	{
		list.remove((int)(2*(remove[2]-1)));
		list.remove((int)(2*(remove[2]-1)));
		mustline.remove((int)(remove[2]-1));
		elec.calcmax();
	}
	/*
     * When a charge is modified it changes all the data
     * to the proper values
     * and recalculates the number of max lines per charge
     */
	public void modify(double[]mod)
	{
		
		list.set((int)(2*(mod[2]-1)), (mod[0]));
		list.set((int)((2*(mod[2]-1))+1),mod[1]);
		mustline.set((int)(mod[2]-1),Math.abs(mod[0])*numberline);
		elec.calcmax();
	
	}
	
	/*
     * When a charge is first created it adds its initial location
     */
	public void getLoc(double[] crn)
	{
		listloc.add(crn[0]/24);
	    listloc.add(crn[1]/24);
	}

	/*
     * When a charge is removed it deletes its location from the array
     * that keeps track of all the locations
     */
	public void removeLoc(double[] remove)
	{
		listloc.remove((int)(2*(remove[2]-1)));
		listloc.remove((int)(2*(remove[2]-1)));
	}
	
	/*
     * When a charge is moved it modifies its location in the array
     * that keeps track of all the locations
     */
	public void modifyloc(double[]mod)
	{
		
		listloc.set((int)(2*(mod[2]-1)), (mod[0])/24);
		listloc.set((int)((2*(mod[2]-1))+1),mod[1]/24);

	}
	
	/*
     * Method that calculates force using the formula k*q1*q2/r^2 but modified to include 
     * the vector directions and not have to handle such small/big values that may 
     * overload the computer
     * If the force is close to 0 then keeptrack is changed to prevent an infinite loop 
     */
	public double[] getForce(double distance, double charge1,double charge2, double[] pos)
	{
		double[] forcetemp= new double[2];
		forcetemp[0]=((9.0*charge1*charge2/(Math.pow(distance,3)))*pos[0])/10.0;
		forcetemp[1]=((9.0*charge1*charge2/(Math.pow(distance,3)))*pos[1])/10.0;
		if(forcetemp[0]<.000000001 && forcetemp[1]<.000000001 && forcetemp[0]>-.000000001 && forcetemp[1]>-.000000001) 
		{
			keeptrack++;
		}
		return forcetemp;
	}
	
	/*
     * Method that calculates distance based on x and y components
     */
	public double getRadius(double[]pos, double[] poscharge)
	{
		
		double radius= Math.sqrt((Math.pow((pos[0]-poscharge[0]),2))+(Math.pow((pos[1]-poscharge[1]),2)));
		return radius;
	}
	
	/*
     * Method that gets the number of charges present on the panel
     */
	public void getNum(int num) 
	{
		numcharge=num;
		
	}
	
	/*
     * Method that uses RK4 to calculate the new position
     */
	public double[] rungeKutta(double [] loc,double charge)
	{
		
		double max;
		max=returnmax();
		double tau=.038-(.002*max*numcharge);
		if(tau<.02)
		{
			tau=.02;
		}
		double[] force= new double[2];
		double[] velocity=new double[2];
		track=0;
		velocity[0]=0.0;
		velocity[1]=0.0;
		force[0]=0.0;
		force[1]=0.0;
		double rmag;
		double[] tempposloc= new double[2];
		double[] postemp= new double[2];
		double[] linetemp=new double[2];
		double[] linetempfinal=new double[2];
		double[] k2=new double[2];
		double[] k3=new double[2];
		double[] k4=new double[2];
		linetemp=loc;
		double r;
		int i,d,e,f;
		int p;
		linetempfinal=loc;
		
		for (i=0;i<numcharge;i++)
		{
			tempposloc[0]=listloc.get(i*2);
			tempposloc[1]=listloc.get(i*2+1);
			rmag=getRadius(loc,tempposloc)+.0000000000001;
			r=list.get(1+i*2)/2;
			if(rmag>=r)
			{
				postemp[0]=loc[0]-tempposloc[0];
				postemp[1]=loc[1]-tempposloc[1];
				force[0]+=getForce(rmag,list.get(2*(i)),charge,postemp)[0];
				force[1]+=getForce(rmag,list.get(2*(i)),charge,postemp)[1];
			}
			else
			{
				keeptrack++;
				if(track==0)
				{
					numline.set(i,numline.get(i)+1);
					track++;
				}
				
			}
			if(force[0]==0&& force[1]==0)
			{
				keeptrack++;
			}
		}
		if(keeptrack==0)
		{
			double[] k1=velocity;
			linetemp[0]=linetemp[0]+tau*.5*velocity[0];
			linetemp[1]=linetemp[1]+tau*.5*velocity[1];
			velocity[0]=velocity[0]+tau*.5*force[0]/5;
			velocity[1]=velocity[1]+tau*.5*force[1]/5;
			force[0]=0.0;
			force[1]=0.0;
		
			for (d=0;d<numcharge;d++)
			{
		
				tempposloc[0]=listloc.get(d*2);
				tempposloc[1]=listloc.get(d*2+1);
				rmag=getRadius(linetemp,tempposloc)+.0000000000001;
				r=list.get(1+d*2)/2;
				if(rmag>=r)
				{
					postemp[0]=linetemp[0]-tempposloc[0];
					postemp[1]=linetemp[1]-tempposloc[1];
					force[0]+=getForce(rmag,list.get(2*(d)),charge,postemp)[0];
					force[1]+=getForce(rmag,list.get(2*(d)),charge,postemp)[1];
					
				}
				else
				{
					keeptrack++;
					if(track==0)
					{
					
						numline.set(d,numline.get(d)+1);
						track++;
						
					}
				}
				if(force[0]==0&& force[1]==0)
				{
					keeptrack++;
				}
			}
			if(keeptrack==0)
			{
				k2[0]=velocity[0];
				k2[1]=velocity[1];
				linetemp[0]=linetemp[0]+tau*.5*velocity[0];
				linetemp[1]=linetemp[1]+tau*.5*velocity[1];
				velocity[0]=velocity[0]+tau*.5*force[0]/5;
				velocity[1]=velocity[1]+tau*.5*force[1]/5;
				force[0]=0.0;
				force[1]=0.0;
			
				for (e=0;e<numcharge;e++)
				{
					tempposloc[0]=listloc.get(e*2);
					tempposloc[1]=listloc.get(e*2+1);
					rmag=getRadius(linetemp,tempposloc)+.0000000000001;
					r=list.get(1+e*2)/2;
					if(rmag>=r)
					{
						postemp[0]=linetemp[0]-tempposloc[0];
						postemp[1]=linetemp[1]-tempposloc[1];
						force[0]+=getForce(rmag,list.get(2*(e)),charge,postemp)[0];
						force[1]+=getForce(rmag,list.get(2*(e)),charge,postemp)[1];
					}
					else
					{
						keeptrack++;
						if(track==0)
						{
							numline.set(e,numline.get(e)+1);
							track++;
							
						}
					}
					if(force[0]==0&& force[1]==0)
					{
						keeptrack++;
					}
				}
				if(keeptrack==0)
				{
					k3[0]=velocity[0];
					k3[1]=velocity[1];
					linetemp[0]=linetemp[0]+tau*velocity[0];
					linetemp[1]=linetemp[1]+tau*velocity[1];
					velocity[0]=velocity[0]+tau*force[0]/5;
					velocity[1]=velocity[1]+tau*force[1]/5;
					force[0]=0.0;
					force[1]=0.0;
					for (f=0;f<numcharge;f++)
						{
							tempposloc[0]=listloc.get(f*2);
							tempposloc[1]=listloc.get(f*2+1);
							rmag=getRadius(linetemp,tempposloc)+.0000000000001;
							r=list.get(1+f*2)/2;
							if(rmag>=r)
							{
								postemp[0]=linetemp[0]-tempposloc[0];
								postemp[1]=linetemp[1]-tempposloc[1];
								force[0]+=getForce(rmag,list.get(2*(f)),charge,postemp)[0];
								force[1]+=getForce(rmag,list.get(2*(f)),charge,postemp)[1];
							}
							else
							{
								keeptrack++;
								if(track==0)
								{
									numline.set(f,numline.get(f)+1);
									track++;
									
								}
							}
							if(force[0]==0&& force[1]==0)
							{
								keeptrack++;
							}
						}
						
						if(keeptrack==0)
						{
							k4[0]=velocity[0];
							k4[1]=velocity[1];
							linetempfinal[0]=loc[0]+tau*(1/6)*(k1[0]+2*k2[0]+2*k3[0]+k4[0]);
							linetempfinal[1]=loc[1]+tau*(1/6)*(k1[1]+2*k2[1]+2*k3[1]+k4[1]);
							for (p=0;p<numcharge;p++)
							{
								tempposloc[0]=listloc.get(p*2);
								tempposloc[1]=listloc.get(p*2+1);
								rmag=getRadius(linetempfinal,tempposloc)+.0000000000001;
								r=list.get(1+p*2)/2;
								if(rmag<=r)
								{
									keeptrack++;
									if(track==0)
									{
										
										numline.set(p,numline.get(p)+1);
										track++;
									}
								}
							}
						}
				}
			}
		}
		numtimes++;
		return linetempfinal;
	}
	
	/*
     * Method that returns an arraylist with all the points that create
     * the lines that display the electric field
     */
	public ArrayList<Double> getLines(double[] loc, double charge, int num)
	{
		numtimes=0;
		theta=-100.0;
		track2=0;
		int i;
		numtrack=num;
		line.clear();
		location=loc;
		q=charge;
		keeptrack=0;
		line.add(location[0]);
		line.add(location[1]);

		while(location[0]>=0 && location[1]>=0 && location[0]<=(489.0/24.0) && location[1]<=(462.0/24.0) && keeptrack==0)
		{
			
			location=rungeKutta(location,q);
			if(keeptrack==0)
			{
				line.add(location[0]);
				line.add(location[1]);
			}
		}
		
		for(i=0;i<numcharge;i++)
		{
			if(numline.get(i)>mustline.get(i))
			{
				track2++;
				numline.set(i,numline.get(i)-1);
			}
		}
		if(track2!=0)
		{ 
			line.clear();	
		}
		else
		{
			if(numline.get(numtrack)<mustline.get(numtrack))
			{
				numline.set(numtrack, numline.get(numtrack)+1);
			}
		}

		
		return line;
	}
	
	/*
     * Method that creates the number of lines each charge needs
     * depending on their charge and the set number of lines
     * per charge
     */
	public void getNumLines(int num)
	{
		int i;
		numberline=num;
		if(mustline.size()!=0)
		{
			for (i=0;i<mustline.size();i++)
			{
				mustline.set((int)(i),Math.abs(list.get(2*(i)))*numberline);
			}
		}
	}
	/*
     * Methods that return info to other classes
     */
	public ArrayList<Double> getInfo()
	{
		return mustline;
	}
	
	public ArrayList<Double> getInfo2()
	{
		return list;
	}
	
	public ArrayList<Double> getInfo3()
	{
		return listloc;
	}
	
	public ArrayList<Double> getInfo4()
	{
		return numline;
	}
	/*
     *Resets all necessary lines when charges are deleted or changed
     */
	public void resetlines()
	{
		int i;
		for(i=0;i<numline.size();i++)
		{
			numline.set(i, (double) 0.0);
		}
	}
	/*
     *Returns the greatest charge present on the panel
     */
	public double returnmax()
	{
		int i;
		double maxcharge=0;
		for(i=0;i<list.size()/2;i++)
		{
			if(maxcharge<Math.abs(list.get(i*2)))
			{
				maxcharge=Math.abs(list.get(i*2));
			}
		}
		return maxcharge;
	}
	
	/*
     *The middle man between the panel and the objects on the panel
     */
	/*
     *Sets the repaint depending on if the panel needs to be repainted or not
     */
	public void setrepaint(int num)
	{
		trackrepaint=num;
	}
	
	/*
     *Returns the repaint
     */
	public int getrepaint()
	{
		return trackrepaint;
	}
	
	/*
     *Shuffles the order with which the electric charges are calculated in case
     *there seems to be no solution. After a certain number of times, the 
     *calculations terminate so the program is not stuck in an infinite loop
     */
	public void shuffleOrder()
	{
		list.add(list.get(0));
		list.add(list.get(1));
		list.remove(0);
		list.remove(0);
		listloc.add(listloc.get(0));
		listloc.add(listloc.get(1));
		listloc.remove(0);
		listloc.remove(0);
		mustline.add(mustline.get(0));
		mustline.remove(0);
		keeptrack2++;
		if(keeptrack2>numcharge)
		{
			elec.terminatePanel();
		}
	}
	/*
     *Clears all the lists when the screen is cleared
     */
	public void clearLists()
	{
		numline.clear();
		mustline.clear();
		list.clear();
		listloc.clear();
	}
	
	/*
     *Readds the label on when the screen is cleared
     */
	public void deallabel()
	{
		elec.deallabel2();
	}
	
	/*
     *Shuffles the order with which the electric charges are calculated
     *in case the user wants to see a different combination
     */
	public void shuffleOrderWant()
	{
		
		list.add(list.get(0));
		list.add(list.get(1));
		list.remove(0);
		list.remove(0);
		listloc.add(listloc.get(0));
		listloc.add(listloc.get(1));
		listloc.remove(0);
		listloc.remove(0);
		mustline.add(mustline.get(0));
		mustline.remove(0);
		
	}
	
	/*
     *If shuffled it returns a value that makes sure the panel
     *repaints
     */
	public int didShuffle()
	{
		return keeptrack2;
	}
	
	/*
     *Manually asks the program to redo it's entire screen
     */
	public void redo()
	{
		elec.redoCharges();
		keeptrack2=0;
		
	}
	
	
	

}
