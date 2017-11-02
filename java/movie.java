import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;





@Table(name="movie")  

@Entity 
public class movie {                 // Model represents table movie
  String ratings;
  String id;
  String wid;
  String vid;
  int primaryid;

     @Id
	 @GeneratedValue(strategy=GenerationType.AUTO)
     @Column(name="primaryid")
	public int getPrimaryid() {
	return primaryid;
}


public void setPrimaryid(int primaryid) {
	this.primaryid = primaryid;
}


	public movie() {
		// TODO Auto-generated constructor stub
	}
	  
	 
	@Column(name="ratings")  
	public String getRatings() {
		return ratings;
	}


	public void setRatings(String ratings) {
		this.ratings = ratings;
	}
	
	//@Column(name="id")  
	
	 @Column(name="id")
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}

	@Column(name="wid")  
	public String getWid() {
		return wid;
	}


	public void setWid(String wid) {
		this.wid = wid;
	}

	@Column(name="vid")  
	public String getVid() {
		return vid;
	}


	public void setVid(String vid) {
		this.vid = vid;
	}


	@Override
	public String toString() {
		return "movie [ratings=" + ratings + ", id=" + id + ", wid=" + wid + ", vid=" + vid + ", primaryid=" + primaryid
				+ "]";
	}




	
	

}
