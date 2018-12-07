import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
class passenger
{
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
}

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

public class serial
{

	public static void main(String[] args) 
	{
		long startTime=System.nanoTime();
		long endTime=(long) ((Math.pow(10,9)*5)+startTime);
		double number=0;
		ArrayList<String> val = new ArrayList<String>();
		val.add("1");val.add("2");val.add("3");val.add("4");val.add("5");
		Lock lock = new ReentrantLock();
		flight f1 = new flight(0,8);
		flight f2 = new flight(1,24);
		flight f3 = new flight(2,9);
		flight f4 = new flight(3,15);
		flight f5 = new flight(4,20);
		ArrayList<flight> flights = new ArrayList<flight>();
		flights.add(f1);flights.add(f2);flights.add(f2);flights.add(f3);flights.add(f4);flights.add(f5);
		int totalseats = 76;
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
		
		ArrayList<passenger> lis = new ArrayList<passenger>();
		lis.add(p1);lis.add(p2);lis.add(p3);lis.add(p4);lis.add(p5);
		
		Random rn =new Random();
		
	
		while (val.size()!=0)
		{
			if(System.nanoTime()>endTime) {
				break;
			}

			int aa=rn.nextInt(5)+1 ;
			
			if(aa==1 && val.contains("1")) //Reserve 
			{
				int flag=0;
				//lock
				lock.lock();
				try {
				while (flag == 0)
				{
					int resran = rn.nextInt(10);
					if (resran!=0)
					{
						
						int pid = rn.nextInt(5)+1;
						int fid = rn.nextInt(5);
						
						if (flights.get(fid).seats!=0)
						{	
							number++;	
							totalseats++;
							int s =lis.get(pid-1).f.get(fid).seats+1;
							lis.get(pid-1).setf(fid,s);
							flights.get(fid).setseats(flights.get(fid).seats-1);
							System.out.println("Flight " + fid +" is booked by passenger " + pid);
						}	
					}
					else {
						flag=1;
					}
				}
				}
				finally {
					flag=1;
					val.remove("1");
					lock.unlock();
					//release lock
				}
					
				
				
			}
			else if(aa==2 && val.contains("2")) //Cancel
			
				
			{
				int flag=0;
				//lock
				lock.lock();
				try {
				while (flag == 0)
				{
					
					int resran = rn.nextInt(10);
					if (resran!=0)
					{
						
						int pid = rn.nextInt(5)+1;
						int fid = rn.nextInt(5);
						
						if (lis.get(pid-1).f.get(fid).seats!=0)
						{	
							number++;	
							totalseats++;
							int s =lis.get(pid-1).f.get(fid).seats-1;
							lis.get(pid-1).setf(fid,s);
							flights.get(fid).setseats(flights.get(fid).seats+1);
							System.out.println("Flight " + fid +" is cancelled by passenger " + pid);
						}	
					}
					else {
						flag=1;
					}
				}
				}
				finally
					
					{	
						
						val.remove("2");
						lock.unlock();
						//release lock
					}
				
			}
			else if(aa==3 && val.contains("3")) //Myflight
			{
				int flag=0;
				//lock
				lock.lock();
				try {
				while (flag == 0)
				{
					int resran = rn.nextInt(10);
					if (resran!=0)
					{
							
						int pid = rn.nextInt(5)+1;
						int noflights=5;
						for (int i=0; i<noflights;i++)
						{
							number++;
							int ss =lis.get(pid-1).f.get(i).flightid;
							int s =lis.get(pid-1).f.get(i).seats;
							System.out.println("Flight ID : " + ss + " Seats reserved : " + s);
						}
						
					}
					else {
						flag=1;
					}
				}
				}
					finally
					{	
						
						val.remove("3");
						lock.unlock();
						//release lock
					}
				
			}
			else if(aa==4 && val.contains("4")) //Total Reservation
			{
				
				int flag=0;
				//lock
				lock.lock();
				try {
				while (flag == 0)
				{
					int resran = rn.nextInt(10);
					if (resran!=0)
					{
							
						int sum =0;
						int noflights=5;
						for (int i=0; i<noflights;i++)
						{
							number++;
							sum =flights.get(i).seats+sum;
						}
						int jj=totalseats-sum;
						System.out.println("Sum of total reservations : " + jj);
						
					}
					else {
						flag=1;
					}
				}
				}
					finally
					{	
						flag=1;
						val.remove("4");
						lock.unlock();
						//release lock
					}
				
			}
			else if(aa==5 && val.contains("5")) //Transfer
			{
				
				int flag=0;
				//lock
				lock.lock();
				try {
				while (flag == 0)
				{
					
					int resran = rn.nextInt(10);
					if (resran!=0)
					{	
						int pid = rn.nextInt(5)+1;
						int fid1 = rn.nextInt(5);
						int fid2 = rn.nextInt(5);
						
						if (fid1 != fid2)
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
							}	
						}
						
						
					}
					else 
					{
						flag=1;
					}
				}
				}
					finally
					{	
						
						val.remove("5");
						lock.unlock();
						//release lock
					}
				}
				
			}
			
			//long TimeFinal=(endTime-startTime)/1000;
			//double fff=number/TimeFinal;
			System.out.println(number);
			//System.out.println("Throughput " + fff );
			
			

		}
		
	

}

