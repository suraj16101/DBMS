import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantLock;

class flight
{
	int flightid;
	int seats;
	
	
	public flight(int flightid, int seats)
	{
		this.flightid= flightid;
		this.seats = seats;
		
	}
	public void setseats(int seats)
	{
		this.seats = seats;
	}
}

public class passenger implements Runnable
{
	static long startTime=System.nanoTime();
	static long endTime=(long) ((Math.pow(10,9)*5)+startTime);
	static ArrayList<flight> flights = new ArrayList<flight>();
	static double number =0;
	static int totalseats=0;
	ReentrantLock l1=new ReentrantLock();
	ReentrantLock l2=new ReentrantLock();
	ReentrantLock l3=new ReentrantLock();
	ReentrantLock l4=new ReentrantLock();
	ReentrantLock l5=new ReentrantLock();
	
	ReentrantLock lp1=new ReentrantLock();
	ReentrantLock lp2=new ReentrantLock();
	ReentrantLock lp3=new ReentrantLock();
	ReentrantLock lp4=new ReentrantLock();
	ReentrantLock lp5=new ReentrantLock();
	
	static ArrayList<passenger> lis = new ArrayList<passenger>();
	int pasid;
	ArrayList<flight> f=new ArrayList<flight>() ;
	
	public passenger(int pasid, ArrayList f) 
	{
		this.pasid=0;
		this.f=f;
		
		
	}
	
	public void setpasid(int pasid)
	{
		this.pasid=pasid;
		
	}
	public void setf(int flightid, int seat) 
	{
		flight ff = new flight(flightid,seat);
		this.f.add(ff);
		
	}
	
	
	public int getpasid()
	{
		return pasid;
	}
	public ArrayList getf()
	{
		return f;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Random rn =new Random();
		
		while (true)
		{
			if(System.nanoTime()>endTime) {
				break;
			}
			int aa=rn.nextInt(5)+1 ;
			
			if(aa==1 ) //Reserve 
			{
				
				int pid = rn.nextInt(5)+1;
				int fid = rn.nextInt(5);
				boolean as = false;
				boolean bs = false;
				do{  
					if(fid==0){
						as=l1.tryLock();
					}
					else if(fid==1) {
						as=l2.tryLock();
					}
					else if(fid==2) {
						as=l3.tryLock();
					}
					else if(fid==3) {
						as=l4.tryLock();
					}
					else if(fid==4) {
						as=l5.tryLock();
					}
					
			     
			    }while(as==false);
				
				if(as==true) {
					do{  
						if(pid==1){
							bs=lp1.tryLock();
						}
						else if(pid==2) {
							bs=lp2.tryLock();
						}
						else if(pid==3) {
							bs=lp3.tryLock();
						}
						else if(pid==4) {
							bs=lp4.tryLock();
						}
						else if(pid==5) {
							bs=lp5.tryLock();
						}
						
				     
				    }while(bs==false);
				}
			    
			  
				if (flights.get(fid).seats!=0 && bs==true && as ==true)
				{	
					number++;
					totalseats++;
					int s =lis.get(pid-1).f.get(fid).seats+1;
					lis.get(pid-1).setf(fid,s);
					flights.get(fid).setseats(flights.get(fid).seats-1);
					System.out.println("Flight " + fid +" is booked by passenger " + pid);
					
					if(fid==0){
						l1.unlock();
					}
					else if(fid==1) {
						l2.unlock();
					}
					else if(fid==2) {
						l3.unlock();
					}
					else if(fid==3) {
						l4.unlock();
					}
					else if(fid==4) {
						l5.unlock();
					}
					if(pid==1){
						lp1.unlock();
					}
					else if(pid==2) {
						lp2.unlock();
					}
					else if(pid==3) {
						lp3.unlock();
					}
					else if(pid==4) {
						lp4.unlock();
					}
					else if(pid==5) {
						lp5.unlock();
					}
					
				}	
				
				
				
				
			}
			else if(aa==2 ) //Cancel
			{
				
				int pid = rn.nextInt(5)+1;
				int fid = rn.nextInt(5);
				boolean as = false;
				boolean bs = false;
				do{  
					if(fid==0){
						as=l1.tryLock();
					}
					else if(fid==1) {
						as=l2.tryLock();
					}
					else if(fid==2) {
						as=l3.tryLock();
					}
					else if(fid==3) {
						as=l4.tryLock();
					}
					else if(fid==4) {
						as=l5.tryLock();
					}
					
			     
			    }while(as==false);
				
				if(as==true) {
					do{  
						if(pid==1){
							bs=lp1.tryLock();
						}
						else if(pid==2) {
							bs=lp2.tryLock();
						}
						else if(pid==3) {
							bs=lp3.tryLock();
						}
						else if(pid==4) {
							bs=lp4.tryLock();
						}
						else if(pid==5) {
							bs=lp5.tryLock();
						}
						
				     
				    }while(bs==false);
				}
			    
			  
				if (lis.get(pid-1).f.get(fid).seats!=0 && bs==true && as ==true)
				{	
					number++;
					totalseats++;
					int s =lis.get(pid-1).f.get(fid).seats-1;
					lis.get(pid-1).setf(fid,s);
					flights.get(fid).setseats(flights.get(fid).seats+1);
					System.out.println("Flight " + fid +" is cancelled by passenger " + pid);
					
					if(fid==0){
						l1.unlock();
					}
					else if(fid==1) {
						l2.unlock();
					}
					else if(fid==2) {
						l3.unlock();
					}
					else if(fid==3) {
						l4.unlock();
					}
					else if(fid==4) {
						l5.unlock();
					}
					if(pid==1){
						lp1.unlock();
					}
					else if(pid==2) {
						lp2.unlock();
					}
					else if(pid==3) {
						lp3.unlock();
					}
					else if(pid==4) {
						lp4.unlock();
					}
					else if(pid==5) {
						lp5.unlock();
					}
					
				}	
				
			}
			else if(aa==3 ) //Myflight
			{
				
				int pid = rn.nextInt(5)+1;
				boolean bs;
				boolean as;
				boolean cs;
				boolean ds;
				boolean es;
					if(pid==1){
						do{ 
						bs=lp1.tryLock();
						}while(bs==false);
						if(bs == true) {
							number++;
							for (int i=0; i<5;i++)
							{
								int ss =lis.get(pid-1).f.get(i).flightid;
								int s =lis.get(pid-1).f.get(i).seats;
								System.out.println("Flight ID : " + ss + " Seats reserved : " + s);
							}
							lp1.unlock();
						}
					}
					else if(pid==2) {
						do{ 
							as=lp2.tryLock();
							}while(as==false);
						if(as == true) {
							number++;
							for (int i=0; i<5;i++)
							{
								int ss =lis.get(pid-1).f.get(i).flightid;
								int s =lis.get(pid-1).f.get(i).seats;
								System.out.println("Flight ID : " + ss + " Seats reserved : " + s);
							}
							lp2.unlock();
						}
					}
					else if(pid==3) {
						do{ 
							cs=lp3.tryLock();
							}while(cs==false);
						if(cs == true) {
							number++;
							for (int i=0; i<5;i++)
							{
								int ss =lis.get(pid-1).f.get(i).flightid;
								int s =lis.get(pid-1).f.get(i).seats;
								System.out.println("Flight ID : " + ss + " Seats reserved : " + s);
							}
							lp3.unlock();
						}
					}
					else if(pid==4) {
						do{ 
							ds=lp4.tryLock();
							}while(ds==false);
						if(ds == true) {
							number++;
							for (int i=0; i<5;i++)
							{
								int ss =lis.get(pid-1).f.get(i).flightid;
								int s =lis.get(pid-1).f.get(i).seats;
								System.out.println("Flight ID : " + ss + " Seats reserved : " + s);
							}
							lp4.unlock();
						}
					}
					else if(pid==5) {
						do{ 
							es=lp5.tryLock();
							}while(es==false);
						if(es == true) {
							number++;
							for (int i=0; i<5;i++)
							{
								int ss =lis.get(pid-1).f.get(i).flightid;
								int s =lis.get(pid-1).f.get(i).seats;
								System.out.println("Flight ID : " + ss + " Seats reserved : " + s);
							}
							lp5.unlock();
						}
					}
					
					
					
			     
			    
			}
			else if(aa==4) //Total Reservation
			{		
				number++;
				boolean bs;
				boolean as;
				boolean cs;
				boolean ds;
				boolean es;
				do{ 
					bs=l1.tryLock();
				}while(bs==false);
				do{ 
					as=l2.tryLock();
					}while(as==false);
				do{ 
					cs=l3.tryLock();
					}while(cs==false);
				do{ 
					ds=l4.tryLock();
					}while(ds==false);
				do{ 
					es=l5.tryLock();
					}while(es==false);
				if(bs==true&&as==true&&cs==true&&ds==true&&es==true) {
					number++;
					int sum =0;
					int noflights=5;
					for (int i=0; i<noflights;i++)
					{
						sum =flights.get(i).seats+sum;
					}
					int jj=totalseats-sum;
					System.out.println("Sum of total reservations : " + jj);
					l1.unlock();l2.unlock();l3.unlock();l4.unlock();l5.unlock();
				}
				
				
			}
			else if(aa==5 ) //Transfer
			{
					
					int pid = rn.nextInt(5)+1;
					int fid1 = rn.nextInt(5);
					int fid2 = rn.nextInt(5);
					boolean as = false;
					boolean bs = false;
					boolean cs = false;
					do{  
						if(fid1==0){
							as=l1.tryLock();
						}
						else if(fid1==1) {
							as=l2.tryLock();
						}
						else if(fid1==2) {
							as=l3.tryLock();
						}
						else if(fid1==3) {
							as=l4.tryLock();
						}
						else if(fid1==4) {
							as=l5.tryLock();
						}
						
				     
				    }while(as==false);
					
					if (as==true)
					{
						do{  
							if(fid2==0){
								cs=l1.tryLock();
							}
							else if(fid2==1) {
								cs=l2.tryLock();
							}
							else if(fid2==2) {
								cs=l3.tryLock();
							}
							else if(fid2==3) {
								cs=l4.tryLock();
							}
							else if(fid2==4) {
								cs=l5.tryLock();
							}
							
					     
					    }while(cs==false);
						
					}
					
					if(as==true && cs==true) {
						do{  
							if(pid==1){
								bs=lp1.tryLock();
							}
							else if(pid==2) {
								bs=lp2.tryLock();
							}
							else if(pid==3) {
								bs=lp3.tryLock();
							}
							else if(pid==4) {
								bs=lp4.tryLock();
							}
							else if(pid==5) {
								bs=lp5.tryLock();
							}
							
					     
					    }while(bs==false);
					}
				    
					if (fid1 != fid2)
					{
						if (as && bs && cs)
						{
					if (flights.get(fid2).seats!=0 || lis.get(pid-1).f.get(fid1).seats!=0)
					{	
						number++;
						int s =lis.get(pid-1).f.get(fid2).seats+1;
						int ss =lis.get(pid-1).f.get(fid1).seats-1;
						lis.get(pid-1).setf(fid2,s);
						lis.get(pid-1).setf(fid1,ss);
						flights.get(fid2).setseats(flights.get(fid2).seats-1);
						flights.get(fid1).setseats(flights.get(fid1).seats+1);
						System.out.println("Passenger " + pid +" is transferred from flight " + fid1 +" to " + fid2);
						
						if(fid1==0){
							l1.unlock();
						}
						else if(fid1==1) {
							l2.unlock();
						}
						else if(fid1==2) {
							l3.unlock();
						}
						else if(fid1==3) {
							l4.unlock();
						}
						else if(fid1==4) {
							l5.unlock();
						}
						if(fid2==0){
							l1.unlock();
						}
						else if(fid2==1) {
							l2.unlock();
						}
						else if(fid2==2) {
							l3.unlock();
						}
						else if(fid2==3) {
							l4.unlock();
						}
						else if(fid2==4) {
							l5.unlock();
						}
						if(pid==1){
							lp1.unlock();
						}
						else if(pid==2) {
							lp2.unlock();
						}
						else if(pid==3) {
							lp3.unlock();
						}
						else if(pid==4) {
							lp4.unlock();
						}
						else if(pid==5) {
							lp5.unlock();
						}
					}
					
					}	
					
				}
			}
				
		}
		
	}
	
	
	public static void main(String[] args) throws InterruptedException 
	{	
		
		flight f1 = new flight(0,8);
		flight f2 = new flight(1,24);
		flight f3 = new flight(2,9);
		flight f4 = new flight(3,15);
		flight f5 = new flight(4,20);
		
		flights.add(f1);flights.add(f2);flights.add(f2);flights.add(f3);flights.add(f4);flights.add(f5);
		totalseats = 76;
		ArrayList<flight> ff1 = new ArrayList<flight>();
		ff1.add(new flight(0,1));
		ff1.add(new flight(1,1));
		ff1.add(new flight(2,2));
		ff1.add(new flight(3,0));
		ff1.add(new flight(4,0));
		passenger p1 = new passenger(1,ff1);
		totalseats += 4;
		ArrayList<flight> ff2 = new ArrayList<flight>();
		ff2.add(new flight(0,1));
		ff2.add(new flight(1,0));
		ff2.add(new flight(2,2));
		ff2.add(new flight(3,4));
		ff2.add(new flight(4,0));
		passenger p2 = new passenger(2,ff2);
		totalseats += 7;
		ArrayList<flight> ff3 = new ArrayList<flight>();
		ff3.add(new flight(0,1));
		ff3.add(new flight(1,2));
		ff3.add(new flight(2,3));
		ff3.add(new flight(3,1));
		ff3.add(new flight(4,0));
		passenger p3 = new passenger(3,ff3);
		totalseats += 7;
		ArrayList<flight> ff4 = new ArrayList<flight>();
		ff4.add(new flight(0,0));
		ff4.add(new flight(1,0));
		ff4.add(new flight(2,0));
		ff4.add(new flight(3,2));
		ff4.add(new flight(4,2));
		passenger p4 = new passenger(4,ff4);
		totalseats += 4;
		ArrayList<flight> ff5 = new ArrayList<flight>();
		ff5.add(new flight(0,1));
		ff5.add(new flight(1,0));
		ff5.add(new flight(2,0));
		ff5.add(new flight(3,0));
		ff5.add(new flight(4,1));
		passenger p5 = new passenger(5,ff5);
		totalseats += 2;
		
		
		lis.add(p1);lis.add(p2);lis.add(p3);lis.add(p4);lis.add(p5);
		
		
		Thread t1=new Thread(p1);
		Thread t2=new Thread(p2);
		Thread t3=new Thread(p3);
		Thread t4=new Thread(p4);
		Thread t5=new Thread(p5);

		
		t1.start();t2.start();
		t3.start();t4.start();
		t5.start();
		
		t1.join(); t2.join();
		t3.join(); t4.join();
		t5.join();
		
		
		
			
			//long TimeFinal=(endTime-startTime)/1000;
			//double fff=number/TimeFinal;
			System.out.println(number);
			//System.out.println("Throughput " + fff );
			
			

		}

	
		
	

}

