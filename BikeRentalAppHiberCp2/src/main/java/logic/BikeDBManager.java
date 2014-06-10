package logic;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import model.check.Checkable;
import model.date.DatePair;
import model.thing.Bike;
import model.thing.TraditionalBike;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class BikeDBManager extends DBManager<Bike> {
	
	public BikeDBManager(SessionFactory factory) {
		super(factory);
	}

	@Override
	public void sortRecords() {
		// TODO Auto-generated method stub

	}

	@Override
	public Bike getRecordById(long id) throws RecordNotFoundException {
		Session session = factory.openSession();
		Transaction tx = null;
		List result;
		try {
			tx = session.beginTransaction();
			Query query=session.createQuery("FROM "+ Bike.class.getSimpleName() +" B WHERE B.id = :bike_id ");
			query.setParameter("bike_id", id);
			result= query.list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			logger.debug("addRecord() error: " + e.getMessage());
			return null;
		} finally {
			session.close();
		}
		return (Bike)result.get(0);
	}
	
	
	
	@Override
	public List<Bike> getRecords() throws RecordNotFoundException {	
		return (List<Bike>)getRecords("FROM Bike");
	}
	
	public List<Bike> getTraditionalBikes() throws RecordNotFoundException {
		return (List<Bike>)getRecords("FROM TraditionalBike");	
	}
	
	public List<Bike> getMountainBikes() throws RecordNotFoundException {
		return (List<Bike>)getRecords("FROM MountainBike");	
	}
	
	public List<Bike> getMTBikes() throws RecordNotFoundException {
		return (List<Bike>)getRecords("FROM MTBike");	
	}

	@Override
	public void setRecords(Collection<Bike> col) {
		
	}

	@Override
	public boolean removeRecord(Bike obj) {
		return removeRecord(obj.getId());
	}
	
	

	@Override
	public boolean removeRecord(long id) {
		  Session session = factory.openSession();
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
	         Bike employee = 
	                   (Bike)session.get(Bike.class, id); 
	         session.delete(employee); 
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         logger.debug("removeRecord() throw: " + e.getMessage());
	         return false;
	      }finally {
	         session.close(); 
	      }
		return true;
	}

	@Override
	public List<Bike> findAll(Checkable<Bike> chk) {
		return null;
	}

	@Override
	public Collection<Bike> findAll(Checkable<Bike> chk, int max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Deprecated
	public boolean saveRecordsToFile(String filename) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@Deprecated
	public boolean loadRecordsFromFile(String filename) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateRecord(long id, Bike newRecord) {
	      Session session = factory.openSession();
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
	         Bike bike = 
	                    (Bike)session.get(Bike.class, id); 
	         if(newRecord.getAbsoluteDisctance()!=null) {
	        	 bike.setAbsoluteDisctance(newRecord.getAbsoluteDisctance());
	         }
	         if(newRecord.getDailyRentalPrice()!=null) {
	        	 bike.setDailyRentalPrice(newRecord.getDailyRentalPrice());
	         }
	         if(newRecord.getDistanceAfterService()!=null) {
	        	 bike.setDistanceAfterService(newRecord.getDistanceAfterService());
	         }
	         if(newRecord.getServicingDistance()!=null) {
	        	 bike.setServicingDistance(newRecord.getServicingDistance());
	         }
	         if(newRecord.getWheelDiameter()!=null) {
	        	 bike.setWheelDiameter(newRecord.getWheelDiameter());
	         }
	         if(newRecord.isFunctional()!=null) {
	        	 bike.setFunctional(newRecord.isFunctional());
	         }
	         if(newRecord.getDatesOfReservations().size()>0) {
	        	 bike.setDatesOfReservations(newRecord.getDatesOfReservations());
	         }
	        	 
			 session.update(bike); 
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
		return true;
	}
	
//	public boolean addReservation(long id, DatePair dp){
//		Session session = factory.openSession();
//	      Transaction tx = null;
//	      try{
//	         tx = session.beginTransaction();
//	         Bike bike = 
//	                    (Bike)session.get(Bike.class, id); 
//	         
//	         
//	        	 
//			 session.update(bike); 
//	         tx.commit();
//	      }catch (HibernateException e) {
//	         if (tx!=null) tx.rollback();
//	         e.printStackTrace(); 
//	      }finally {
//	         session.close(); 
//	      }
//		return true;
//	}

}
