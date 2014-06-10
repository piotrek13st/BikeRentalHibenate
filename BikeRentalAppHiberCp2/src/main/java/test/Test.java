package test;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import logic.BikeDBManager;
import logic.ClientDBManager;
import logic.OrderDBManager;
import logic.RecordExistsException;
import logic.RecordNotFoundException;
import model.client.Client;
import model.client.Person;
import model.date.DatePair;
import model.date.EndBeforStartException;
import model.order.Order;
import model.order.Order.Status;
import model.thing.Bike;
import model.thing.Thing;

import org.hibernate.SessionFactory;

import db.util.HibernateUtil;

public class Test {
	public static void main(String [] args) throws EndBeforStartException, RecordNotFoundException, RecordExistsException {
		SessionFactory factory= HibernateUtil.getSessionFactory();
		BikeDBManager bdb= new BikeDBManager(factory);
		
		//Session ses = factory.openSession();
		
//		Bike b= new TraditionalBike();	
//		b.setAbsoluteDisctance(12);
//		b.setDailyRentalPrice(12);
//		b.setDistanceAfterService(43);
//		b.setFunctional(true);
//		b.setServicingDistance(32);
//		b.setWheelDiameter(32);
//		b.addReservation(new Date(543L), new Date(4343));
//		b.addReservation(new Date(5555L), new Date(6666L));
//		
//		Bike b2	= new TraditionalBike();	
//		b2.setAbsoluteDisctance(122);
//		b2.setDailyRentalPrice(123);
//		b2.setDistanceAfterService(413);
//		b2.setFunctional(true);
//		b2.setServicingDistance(332);
//		b2.setWheelDiameter(322);
//		b2.addReservation(new Date(543L), new Date(4343));
//		b2.addReservation(new Date(5555L), new Date(6666L));
		
		Client c = new Person() ;
		c.setEmail("asdasd");
		c.setPhoneNumber("wefsdf");
		((Person)c).setName("dasd");
		((Person)c).setSurname("Heheh");
		
		
		Order ord = new Order();
		//ord.setClient(c);
		ord.setDatePair(new DatePair(new Date(4324000L), new Date(5436000L)));
		ord.setMadedOrderDate(new Date());
		ord.setPrice(234L);
		ord.setStatus(Status.CONFIRMED);

		
		//Transaction tx;
		
		ClientDBManager cm = new ClientDBManager(factory);
		OrderDBManager om = new OrderDBManager(factory);
		BikeDBManager bm = new BikeDBManager(factory);
		
		Client client1= cm.getRecordById(1L);
		Bike bike1= bm.getRecordById(3L);
		Bike bike2= bm.getRecordById(4L);
		
		List<Thing> lt = new LinkedList<>();
		lt.add(bike1);
		lt.add(bike2);
		
		ord.setClient(client1);
		ord.setThingList(lt);
		
		om.addRecord(ord);
		
//		tx=ses.beginTransaction();
////		
////		ses.save(b);
////		ses.save(b2);
//		ses.save(c);
//		ses.save(ord);
//		tx.commit();
		
		//ses.close();
		
		System.out.println("The end");
	}
	
}
