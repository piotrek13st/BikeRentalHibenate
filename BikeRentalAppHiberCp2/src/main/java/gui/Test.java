package gui;

import java.util.List;

import logic.BikeDBManager;
import logic.RecordExistsException;
import logic.RecordNotFoundException;
import model.thing.Bike;
import model.thing.MountainBike;
import model.thing.TraditionalBike;

import org.hibernate.SessionFactory;

import db.util.HibernateUtil;

public class Test {
	
	public static void main(String [] args) throws Exception{
	SessionFactory factory = HibernateUtil.getSessionFactory();
	BikeDBManager manager=new BikeDBManager(factory);
	
	
	Bike b = new TraditionalBike();
	Bike b2 = new MountainBike();
	
	
	System.out.format("%-10s%-20s%9s%6s%11s%7s%5s%10s%n", "Id" , "Type", "s.dis[km]"
			,"cal", "a.dis[km]", "func.", "n.de", "model");
	try {
		List<Bike> bikes= manager.getRecords();
		if(bikes!=null)
		for(Bike bt: bikes) {
			System.out.println((Bike)bt);
		}
	} catch (RecordNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
	System.out.println("List of MountainBikes");
	
	try {
		List<Bike> bikes= manager.getMountainBikes();
		if(bikes!=null)
		for(Bike bt: bikes) {
			System.out.println((Bike)bt);
		}
	} catch (RecordNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
	System.out.println("Get BY ID");
	Bike bbb=manager.getRecordById(4);
	System.out.println(bbb);
	
//	Bike newB = new MountainBike();
//	newB.setAbsoluteDisctance(2000);
//	manager.updateRecord(6, newB);
	
	
//	bbb=manager.getRecordById(6);
//	System.out.println(bbb);
	
	
	//manager.removeRecord(5);
	
	b.setServicingDistance(11);
	b.setWheelDiameter(34);
	b.setDailyRentalPrice(12);
	b.setAbsoluteDisctance(100);
	b.setFunctional(true);
	b.setDistanceAfterService(12);
	

	try {
		System.out.println(manager.addRecord(b));
	} catch (RecordExistsException e) {
		e.printStackTrace();
	}
	
	}
	
	
}
