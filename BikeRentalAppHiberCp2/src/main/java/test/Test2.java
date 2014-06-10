package test;

import java.util.Date;
import java.util.List;

import logic.BikeDBManager;
import logic.RecordExistsException;
import logic.RecordNotFoundException;
import model.thing.Bike;

import model.thing.TraditionalBike;

import org.hibernate.SessionFactory;

import db.util.HibernateUtil;

public class Test2 {

	public static void main(String[] args) throws RecordNotFoundException, RecordExistsException {
		SessionFactory factory= HibernateUtil.getSessionFactory();
		
		BikeDBManager bdb = new BikeDBManager(factory);
//		List<Bike> lb= bdb.getTraditionalBikes();
//		Bike b= lb.get(0);
//		System.out.println(b);
//		
		Bike b2	= new TraditionalBike();	
		b2.setAbsoluteDisctance(122);
		b2.setDailyRentalPrice(123);
		b2.setDistanceAfterService(413);
		b2.setFunctional(true);
		b2.setServicingDistance(332);
		b2.setWheelDiameter(322);
		b2.addReservation(new Date(1000000L), new Date(2000000L));
		b2.addReservation(new Date(3000000L), new Date(4000000L));
		System.out.println(b2.isAvailable(new Date(1234L), new Date(2345L)));
		
		//bdb.addRecord(1);
		
		//Bike b=bdb.getRecordById(1);
		//System.out.println(b);
		//System.out.println(b.isAvailable(new Date(3500000L),new Date(5000000L)));
		
	
		//System.out.println(b.isAvailable(new Date(2222L), new Date(54333L) ));
		
	}

}
