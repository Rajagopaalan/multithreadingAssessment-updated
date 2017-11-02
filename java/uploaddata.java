import java.io.BufferedReader;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.mysql.jdbc.Connection;

public class uploaddata  extends Thread{
	
	//<String> st = new ArrayBlockingQueue<String>(1000);
	List<String> st = new ArrayList<String>();
	
	int threadCount=10;
    ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadCount);
    int i=0; 
    
	public void insert() throws SQLException, ClassNotFoundException, InterruptedException{

		String csvFile = "C:\\Users\\Rajagopaalan\\workspace4\\Multithreadingproject\\src\\ratings.dat";
        BufferedReader br = null;
        String line = "";
     


        try {
                 br = new BufferedReader(new FileReader(csvFile));
            int x=1;
         
            {
            while ((line = br.readLine()) != null) {
            	
            	System.out.println("producer"+x);
            //	if(st.size()<1000)
            			st.add(line);            	  			 
             
                         x++;
            	  }
            
      
        		
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
       
	}
	
	public  void  upload(int start,int n, Connection conn) throws ClassNotFoundException, SQLException, InterruptedException{
		  String cvsSplitBy = "::";
		 final PreparedStatement stmt=conn.prepareStatement("insert into movie1 values(?,?,?,?)");  
	 	 	int r=start;
	 	 	int temp = 0;
	 	 	//wait();
	 		while(r<st.size() ){
	 		while(temp==500){
	 		   Thread tin1 =new Thread(new Runnable()
		        {
		       
		            public void run()
		            {
		                try
		                {
		                	stmt.executeBatch() ;
		    	 			stmt.clearBatch();
		    	 		
		                 System.out.println("hweweweweeererererer");
		                }
		                catch (SQLException e) {
							
							e.printStackTrace();
						}
		            }
		        });
              // threadPool.execute(t); 
	 		  threadPool.execute(tin1);  
	 		// tin1.destroy();
	 		// tin1.j
	 			temp=0;
	 		
	 		}
	 			
		 String[]  movie= st.get(r).split(cvsSplitBy);		
 		stmt.setString(1, movie[0]);  
 		stmt.setString(2,movie[1]);  
 		stmt.setString(3,movie[2]); 
 		stmt.setString(4,movie[3]); 
        stmt.addBatch();
 		//int w =stmt.executeUpdate() ;
	 			
 	   	System.out.println("conusmer"+r);
 		r=r+n;
 		temp++;

		}
	 		System.out.println(r+"-"+st.size());
	 //	  stmt.executeBatch();
	 	
           }
	

	public static void main(String[] args) throws ClassNotFoundException, SQLException, InterruptedException {
		// TODO Auto-generated method stub
	 final uploaddata pc = new  uploaddata();
		 final int interval = 2;
	        // Create producer thread
		 
		 int threadCount=10;
		    ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadCount);

	        Thread t1 = new Thread(new Runnable()
	        {
	            public void run()
	            {
	                try
	                {
	                    pc.insert();
	                }
	                catch(InterruptedException e)
	                {
	                    e.printStackTrace();
	                } catch (ClassNotFoundException e) {
				
						e.printStackTrace();
					} catch (SQLException e) {
				
						e.printStackTrace();
					}
	            }
	        });
	 
	        Thread t2 = new Thread(new Runnable()
	        {
	           
	            public void run()
	            {
	                try
	                {
	                	 Class.forName("com.mysql.jdbc.Driver");
	         	 		Connection conn = null;
	         	 		conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/db","root", "root");  
	         	 		
	                	
	                    pc.upload(0,interval,conn );
	                }
	                catch(InterruptedException e)
	                {
	                    e.printStackTrace();
	                } catch (ClassNotFoundException e) {
						
						e.printStackTrace();
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
	            }
	        });
	        
	        Thread t3 = new Thread(new Runnable()
	        {
	            public void run()
	            {
	                try
	                {
	                	 Class.forName("com.mysql.jdbc.Driver");
		         	 		Connection conn = null;
		         	 		conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/db","root", "root");  
		         	 		
	                    pc.upload(1,interval ,conn);
	                }
	                catch(InterruptedException e)
	                {
	                    e.printStackTrace();
	                } catch (ClassNotFoundException e) {
						
						e.printStackTrace();
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
	            }
	        });
	       
	        
	        
	        
	       
	  
	        t1.start();
	        t2.start();
	       t3.start();
	      
	       //t1.join();
	     //  t2.join();
	      // t3.join();
	       
    }

}
