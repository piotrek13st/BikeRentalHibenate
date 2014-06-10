package model.thing;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Transient;

import model.date.DatePair;
import model.date.EndBeforStartException;
import model.id.Idenifiable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Thing implements Idenifiable, Serializable {

	private static final long serialVersionUID = 1166863320651410201L;
	
	@Id
	@GeneratedValue
	private long id;
	private Float dailyRentalPrice=null;		//pln/day
	private Boolean isFunctional=null;	
	
//	@OneToMany(cascade={CascadeType.ALL})
//	@JoinTable(
//			name="dates",
//			joinColumns = @JoinColumn ( name="date_id"),
//			inverseJoinColumns = @JoinColumn(name="thing_id")
//			)
	//@ManyToMany(cascade = CascadeType.PERSIST)
	 @ElementCollection(fetch=FetchType.EAGER)
	 @CollectionTable(name="thing_reservations", joinColumns=@JoinColumn(name="client_id"))
	 @Column(name="data_pair")
	protected List<DatePair> datesOfReservations = new LinkedList<>();
	
	protected static Logger logger=LogManager.getLogger(Thing.class.getName());
	
	public Thing(long id) {
		this.id=id;
		logger.info("Creating Thing: id="+ id);
	}
	
	public Thing() {
		this(0);
	}
	
	abstract public String getName();
	
	
	@Override
	public long getId() {
		return id;
	}
	
	@Override
	public void setId(long id) {
		this.id=id;
	}

	public Float getDailyRentalPrice() {
		return dailyRentalPrice;
	}
	public void setDailyRentalPrice(float dailyRentalPrice) {
		this.dailyRentalPrice = dailyRentalPrice;
	}
	
	/**
	 * 
	 * @return false if thing needs a service
	 */
	public Boolean isFunctional() {
		return isFunctional;
	}
	
	public void setFunctional(boolean isFunctional) {
		this.isFunctional = isFunctional;
	}
	
	/**
	 * 
	 * @param from Date that represents time that reservation starts
	 * @param to Date that represents time that reservation end
	 * @return	true if Reservation date is correctly added or false while dates are overlapping with other reservations
	 */
	public boolean addReservation(Date from, Date to) {
		boolean isAdded=isAvailable(from, to);
		if(isAdded==true)
			try {
				isAdded=datesOfReservations.add(new DatePair(from, to));
			} catch (EndBeforStartException e) {
				return false;
			}
		return isAdded;
	}
	
		public boolean addReservation(DatePair p) {
			return addReservation(p.getStartAtDate(), p.getEndAtDate());
		}
	
/*	public boolean addReservation(DatePair p) {
		return datesOfReservations.add(p);
	}*/
	
	public boolean releaseReservation(DatePair pair) {
		return datesOfReservations.remove(pair);
	}
	
	@Transient
	public boolean isAvailable(Date from, Date to) {
		try{
			
			for(DatePair p: datesOfReservations) {
				if(p.isOverlapping(from, to))
					return false;
			}
		} catch (EndBeforStartException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	public List<DatePair> getDatesOfReservations() {
		return datesOfReservations;
	}

	public void setDatesOfReservations(List<DatePair> datesOfReservations) {
		this.datesOfReservations = datesOfReservations;
	}

	
	
}
