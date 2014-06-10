package logic;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import model.check.Checkable;
import model.order.Order;
import model.thing.Bike;
import model.thing.Thing;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class OrderDBManager extends DBManager<Order> {
	
	
	public OrderDBManager(SessionFactory factory) {
		super(factory);
	}
	
	@Override
	public void sortRecords() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public long addRecord(Order obj) throws RecordExistsException {
		Session session = factory.openSession();
		Transaction tx = null;
		Long id=-1L;
		try {
			List<Thing> lt=obj.getThingList();
			
			tx = session.beginTransaction();
			id= (Long)session.save(obj);
			Bike bike;
			for(Thing b : lt) {
	                    bike=(Bike)session.get(Bike.class, b.getId()); 
	                    if(bike.addReservation(obj.getDatePair()))
	                    	session.update(bike); 
			}
			logger.debug("addRecord(): record with id=" +id+ " added");
			
			tx.commit();
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			logger.debug("addRecord() error: " + e.getMessage());
			return -1L;
		} finally {
			session.close();
		}
		return id;
	}
	

	
	public void releaseReservations(long orderId) {
		Session session = factory.openSession();
		Transaction tx = null;
		Long id=-1L;
		try {
			
			tx = session.beginTransaction();
			Order ord=(Order)session.get(Order.class, orderId);
			
			Bike bike;
			for(Thing b : ord.getThingList()) {
	                    bike=(Bike)session.get(Bike.class, b.getId()); 
	                    bike.releaseReservation(ord.getDatePair());
			}
			logger.debug("releaseReservations()");
			
			tx.commit();
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			logger.debug("releaseReservation() error: " + e.getMessage());
			return;
		} finally {
			session.close();
		}
		
	}
	

	@Override
	public Order getRecordById(long id) throws RecordNotFoundException {
		Session session = factory.openSession();
		Transaction tx = null;
		List result;
		try {
			tx = session.beginTransaction();
			Query query=session.createQuery("FROM "+ Order.class.getSimpleName() +" B WHERE B.id = :order_id ");
			query.setParameter("order_id", id);
			result= query.list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			logger.debug("addRecord() error: " + e.getMessage());
			return null;
		} finally {
			session.close();
		}
		return (Order)result.get(0);
	}
	
	
	
	@Override
	public List<Order> getRecords() throws RecordNotFoundException {	
		return (List<Order>)getRecords("FROM Order");
	}
	


	@Override
	public void setRecords(Collection<Order> col) {
		
	}

	@Override
	public boolean removeRecord(Order obj) {
		return removeRecord(obj.getId());
	}
	
	

	@Override
	public boolean removeRecord(long id) {
		  Session session = factory.openSession();
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
	         Order order = 
	                   (Order)session.get(Order.class, id); 
	         session.delete(order); 
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
	public List<Order> findAll(Checkable<Order> chk) {
		return null;
	}

	@Override
	public Collection<Order> findAll(Checkable<Order> chk, int max) {
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
	public boolean updateRecord(long id, Order newRecord) {
	      Session session = factory.openSession();
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
	         Order order = 
	                    (Order)session.get(Order.class, id); 
	         if(newRecord.getClient()!=null) {
	        	 order.setClient(newRecord.getClient());
	         }
	         if(newRecord.getMadedOrderDate()!=null) {
	        	order.setMadedOrderDate(newRecord.getMadedOrderDate());
	         }
	         if(newRecord.getStatus()!=null) {
	        	 order.setStatus(newRecord.getStatus());
	         }
	         if(newRecord.getThingList()!=null) {
	        	 order.setThingList(newRecord.getThingList());
	         }
	         if(newRecord.getDatePair()!=null) {
	        	order.setDatePair(newRecord.getDatePair());
	         }
	        	 
			 session.update(order); 
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
		return true;
	}
}
