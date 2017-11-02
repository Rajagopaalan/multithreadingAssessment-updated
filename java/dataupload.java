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

import com.mysql.jdbc.Connection;

public class dataupload {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub

		String csvFile = "C:\\Users\\Rajagopaalan\\workspace4\\Multithreadingproject\\src\\ratings.dat";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = "::";
       

        try {
            Class.forName("com.mysql.jdbc.Driver");
    		Connection conn = null;
    		conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/db","root", "root");
    	
            br = new BufferedReader(new FileReader(csvFile));
            int x=1;
            String query = "INSERT INTO movie VALUES (?, ?, ?,?)";
            PreparedStatement ps = conn.prepareStatement(query);            
           
            while ((line = br.readLine()) != null && x<10000) {          
                String[]  movie= line.split(cvsSplitBy);            
                      
             
                    ps.setString(1, movie[0]);
                    ps.setString(2, movie[1]);
                    ps.setString(3,movie[2]);
                    ps.setString(4,movie[3]);
                    ps.addBatch();
                
            //    ps.executeBatch();
        		System.out.println(x);
        		x++;
            }
            ps.executeBatch();
            ps.clearBatch();

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

}
