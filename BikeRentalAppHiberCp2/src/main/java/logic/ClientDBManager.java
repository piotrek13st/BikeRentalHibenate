package logic;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import model.check.Checkable;
import model.client.Client;


public class ClientDBManager extends DBManager<Client> {

	public ClientDBManager(SessionFactory factory) {
		super(factory);
	}

	@Override
	public void sortRecords() {
		// TODO Auto-generated method stub

	}

	@Override
	public Client getRecordById(long id) throws RecordNotFoundException {
		Session session = factory.openSession();
		Transaction tx = null;
		List result;
		try {
			tx = session.beginTransaction();
			Query query=session.createQuery("FROM Client C WHERE C.id = :client_id ");
			query.setParameter("client_id", id);
			result= query.list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			logger.debug("addRecord() error: " + e.getMessage());
			return null;
		} finally {
			session.close();
		}
		return (Client)result.get(0);
	}
	
	
	
	@Override
	public List<Client> getRecords() throws RecordNotFoundException {	
		return (List<Client>)getRecords("FROM Client");
	}
	


	@Override
	public void setRecords(Collection<Client> col) {
		
	}

	@Override
	public boolean removeRecord(Client obj) {
		return removeRecord(obj.getId());
	}
	
	

	@Override
	public boolean removeRecord(long id) {
		  Session session = factory.openSession();
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
	         Client client = 
	                   (Client)session.get(Client.class, id); 
	         session.delete(client); 
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
	public List<Client> findAll(Checkable<Client> chk) {
		return null;
	}

	@Override
	public Collection<Client> findAll(Checkable<Client> chk, int max) {
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
	public boolean updateRecord(long id, Client newRecord) {
	      Session session = factory.openSession();
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
	         Client client = 
	                    (Client)session.get(Client.class, id); 

	         if(newRecord.getEmail()!=null) {
	        	 client.setEmail(newRecord.getEmail());
	         }
	         if(newRecord.getPhoneNumber()!=null) {
	        	 client.setPhoneNumber(newRecord.getPhoneNumber());
	         }
	        	 
			 session.update(client); 
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
