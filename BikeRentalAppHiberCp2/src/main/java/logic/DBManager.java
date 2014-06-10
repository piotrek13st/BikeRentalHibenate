package logic;

import java.util.List;

import model.thing.Bike;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

abstract public class DBManager<T> implements RecordManagerInterface<T>   {
	
	
	protected SessionFactory factory;
	protected static Logger logger = LogManager.getLogger(RecordManager.class
			.getName());
	
	public DBManager(SessionFactory factory) {
		this.factory=factory;
	}
	
	/**
	 * 
	 * @param id		- id of the record in DB to be updated
	 * @param newRecord - record which not null field will update DB element; 
	 * @return			- true if successul
	 */
	abstract public boolean updateRecord(long id, T newRecord);
	
	public SessionFactory getFactory() {
		return factory;
	}

	public void setFactory(SessionFactory factory) {
		this.factory = factory;
	}
	
	
	
	@Override
	public long addRecord(T obj) throws RecordExistsException {
		Session session = factory.openSession();
		Transaction tx = null;
		Long id=-1L;
		try {
			tx = session.beginTransaction();
			id= (Long)session.save(obj);
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
	

	
	public List getRecords(String queryString) throws RecordNotFoundException {
		Session session=factory.openSession();
		Transaction tx= null;
		List recordList = null;
		try {
			tx=session.beginTransaction();
			recordList= session.createQuery(queryString).list();
			tx.commit();
		} catch(HibernateException e) {
			if( tx != null) 
				tx.rollback();
			logger.debug("getRecords() error: " + e.getMessage());
			return null;
		} finally {
			session.close();
		}
		logger.debug("getRecords(): "+recordList.size() +" elements founded");
		return recordList;
	}
	
}
