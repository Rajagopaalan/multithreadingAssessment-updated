
import org.springframework.context.ApplicationContext;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class Springdboperation {
   public static void main(String[] args) throws IOException {
      ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
      

   //  SpringDao sdao = 
    //     (SpringDao)context.getBean("SpringDao"); 
    // sdao.insert();
     /*
      * public void insert() throws IOException{
		 String csvFile = "C:\\Users\\Rajagopaalan\\workspace4\\Multithreadingproject\\src\\ratings.dat";
	 	  JdbcTemplate jdbcTemplateObject = new  JdbcTemplate() ;
			List<String> st = new ArrayList<String>();
		    BufferedReader	 br = new BufferedReader(new FileReader(csvFile));
		        
			    movie e1=new movie(); 
			    String line;
			    while ((line = br.readLine()) != null) {
			    	st.add(line);      	  			 
			     
			    	  }   
			    
			    for(int x=0;x<st.size();x++){
			    	String SQL = "insert into movie  values (?,?, ?, ?,?)";	 
			    	String[] records = st.get(x).split("::");
			     jdbcTemplateObject.update(SQL, x, records[0], records[1],records[2],records[3]);
			    }
	}
      * 
      * *
      */
     
   }

}