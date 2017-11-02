  
  
import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.hibernate.cfg.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.hibernate.Session;  
import org.hibernate.SessionFactory;  
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import org.hibernate.cfg.AnnotatedClassType;
import org.hibernate.cfg.AnnotationBinder;
import com.fasterxml.classmate.AnnotationConfiguration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


  
public class StoreData extends Thread {  
	  
	private Session session = null;                                                 // initialize parameters such as session, start index and partition of the arraylist on which each thread runs
	 List<String> st ;
	 int start ;
	 int part ;
	 String name;
	   Date startdt;   
	           
	StoreData(int s,int p,String n) throws IOException {
		start = s;
		part = p;
		name = n;
		startdt = new Date();
		String csvFile = "src/main/java/ratings.dat";                                // Input file
	   // BufferedReader br = null;
	    String line = "";
	    st = new ArrayList<String>();
	    BufferedReader	 br = new BufferedReader(new FileReader(csvFile));
        
	   
	    while ((line = br.readLine()) != null) {                                    // loading csv file data to List datastructure
	    			st.add(line);            	  			 
	     
	    	  }
	    
	    Properties properties = new Properties();                                 // intialize hibernate configuration
	    properties.put("hibernate.dialect","org.hibernate.dialect.MySQL5Dialect");
	    properties.put("hibernate.show_sql", "true");
	    properties.put("hibernate.hbm2ddl.auto","update");
	    properties.put("hibernate.connection.url", "jdbc:mysql://localhost/db") ;
	    properties.put("hibernate.connection.username", "root") ;
	    properties.put("hibernate.connection.password", "root") ;


	    properties.put("hibernate.jdbc.batch_size",100);
	    StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder();
	    standardServiceRegistryBuilder.applySettings(properties);

	    MetadataSources metadataSources = new MetadataSources(standardServiceRegistryBuilder.build());
	    metadataSources.addAnnotatedClass(movie.class);
	   
	    SessionFactory factory = metadataSources.getMetadataBuilder().build().buildSessionFactory();
	    
	  
	      
		 session =factory.openSession();                                                 // open a new session
		
	}
	 @Override
	
	public void run() {
		
		
	    	    Transaction tx = session.beginTransaction();
	    	    Integer g=0;
	    	    for ( int i=start; i<st.size(); i=i+part ) {          				 // Thread processing. Each thread inserts the data to database from the array chunk  on which they are processing
	    	    	g++;
	    	    	//System.out.println("namme"+name);
	    	       movie m = new movie() ;
	    	        
	    	        String[] record = st.get(i).split("::") ;
	    	        m.setPrimaryid(g);                              					// Update Model parameters
	    	        m.setId(record[1]);
	    	        m.setRatings(record[0]);
	    	        m.setVid(record[2]);
	    	        m.setWid(record[3]);
	    	        
	    	   
	    	        session.save(m);
	    	     
	    	    	if( i % 50 == 0 ) {                                         		// Batch operation. Batch size: 50
	    	            session.flush();
	    	            session.clear();
	    	        }
	    	    }
	    	
	    	    tx.commit();
	    	   
	    	    	 Date end  = new Date();
	    	    	 System.out.println("All transactions commited sucessfully");                  // End of transaction and time of execution
	    	 	     System.out.println("start:"+startdt);
	    	 	     System.out.println(" End:"+end);
	    	 	
	    	    	
	    	    
	    	    session.close();
	    	   
		 
		
	    	    
	    }
	   
	
	 
	public static void main(String[] args) throws IOException, InterruptedException {  
	int interval =3;                                             // initialize number of threads or number of chunks in array

	
	
	
	
	
	
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		movie me = new movie();
		
for(int x=0;x<3;x++){
	
	executorService.execute( new StoreData(0,interval,("thread"+x+1)));
	//executorService.execute( new StoreData(0,interval,("thread"+x+1),me1));
}
		
	
executorService.shutdown();  
   
   
      
}  
}  