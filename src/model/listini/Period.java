package model.listini;

import java.util.Calendar;
import java.util.Date;

public class Period {
	private Integer id;
	private Date startDate;
	private Date endDate;
	
	public Boolean includesDate(Date date){
		
		
		return false;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	

}
