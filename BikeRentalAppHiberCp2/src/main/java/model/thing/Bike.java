package model.thing;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import model.check.Checkable;
import model.date.DatePair;

@Entity
abstract public class Bike extends Thing implements Comparable<Bike>, Checkable<Bike> {

	private static final long serialVersionUID = -4215264803172552939L;
	private Integer wheelDiameter;
	protected Integer servicingDistance;	//in km
	private Integer absoluteDisctance;		//in km
	private Integer distanceAfterService; 	//in km
	
	public Bike(long id) {
		super(id);
	}
	
	public Bike() {
		super(0L);
	}
	@Override
	public int compareTo(Bike o) {
		return (this.servicingDistance-this.distanceAfterService)<(o.servicingDistance-o.distanceAfterService) ? 1:-1;
	}
	
	/**
	 * If this has a dates of reservations not empty then check() returns true if reservations won't overlap
	 * otherwise 
	 */
	@Override
	public boolean check(Bike obj) {
		if(!this.getName().equals(obj.getName()))
			return false;
		
		if(!this.datesOfReservations.isEmpty()) {
			for(DatePair d:datesOfReservations) {
				if(!obj.isAvailable(d.getStartAtDate(), d.getEndAtDate()))
					return false;
			}
			return true;
		}
		return false;
		
	}
	
	public boolean isServiceNeeded() {
		if(distanceAfterService>servicingDistance)
			return true;
		return false;
	}
	
	public void clearDistanceAfterService() {
		distanceAfterService=0;
	}
	
	//getters & setters
	public Integer getWheelDiameter() {
		return wheelDiameter;
	}
	public void setWheelDiameter(int wheelDiameter) {
		this.wheelDiameter = wheelDiameter;
	}

	public Integer getServicingDistance() {
		return servicingDistance;
	}

	public void setServicingDistance(int servicingDistance) {
		this.servicingDistance = servicingDistance;
	}

	public Integer getAbsoluteDisctance() {
		return absoluteDisctance;
	}

	public void setAbsoluteDisctance(int absoluteDisctance) {
		this.absoluteDisctance = absoluteDisctance;
	}

	public Integer getDistanceAfterService() {
		return distanceAfterService;
	}

	public void setDistanceAfterService(int distanceAfterServices) {
		this.distanceAfterService = distanceAfterServices;
	}

	@Override
	public String toString() {
		return String.format("%-10s%-20s%9d%6d%11d%7b", new Long(getId()).toString() ,getName(),  
				(getServicingDistance()-getDistanceAfterService()), wheelDiameter, absoluteDisctance, isFunctional());
	}
	
	
	

}
