package model.date;


import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Embeddable
public class DatePair {
	
		
	@Temporal(TemporalType.TIMESTAMP)
	private Date startAtDate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date endAtDate;
	
	public DatePair() {
	}
	
	public DatePair(Date start, Date end) throws EndBeforStartException {
		if(start.after(end)) 
			throw new EndBeforStartException();
		startAtDate=start;
		endAtDate=end;
	}

	public boolean isOverlapping(Date start, Date end) throws EndBeforStartException {
		if(start.after(end))
			throw new EndBeforStartException();
		
		if(((!start.after(endAtDate)) && (!start.before(startAtDate))) || ((!end.after(endAtDate)) && (!end.before(startAtDate))) 
				|| ((!start.after(startAtDate)) && (!end.before(endAtDate)))) 
			return true;
		else
			return false;
		
	}
	
	public boolean isOverlapping(DatePair p) {
		Date start=p.startAtDate;
		Date end=p.endAtDate;

		if(((!start.after(endAtDate)) && (!start.before(startAtDate))) || ((!end.after(endAtDate)) && (!end.before(startAtDate))) 
				|| ((!start.after(startAtDate)) && (!end.before(endAtDate)))) 
			return true;
		else
			return false;
	}
	

	public Date getStartAtDate() {
		return startAtDate;
	}

	public Date getEndAtDate() {
		return endAtDate;
	}

	public void setStartAtDate(Date startAtDate) {
		this.startAtDate = startAtDate;
	}

	public void setEndAtDate(Date endAtDate) {
		this.endAtDate = endAtDate;
	}
 
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof DatePair)) {
			return false;
		}
		DatePair d= (DatePair)obj;
		if(startAtDate.equals(d.startAtDate) && endAtDate.equals(d.endAtDate))
			return true;
		return false;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public String toString() {
		return startAtDate.toGMTString() + " to " + endAtDate.toGMTString();
	}
	
}
