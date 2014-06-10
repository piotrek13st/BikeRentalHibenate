package logic;


import model.thing.Bike;
import model.thing.MountainBike;
import model.thing.TraditionalBike;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class RecordMenagerTest {
	RecordManager<Bike> records;
	
	@Before
	public void initialize() {
		records = new RecordManager<Bike>();
	}
	
	
	@Test(expected=RecordExistsException.class)
	public void addRecordTest1() throws RecordExistsException {
		Bike bike1 = new TraditionalBike(10);
		records.addRecord(bike1);
		records.addRecord(bike1);
	}
	
	@Test
	public void addRecordTest2() throws RecordExistsException {
		Bike bike1 = new TraditionalBike(11);
		Bike bike2 = new TraditionalBike(12);
		records.addRecord(bike1);
		records.addRecord(bike2);
	}
	
	@Test(expected=RecordNotFoundException.class)
	public void getRecordTest1() throws RecordNotFoundException, RecordExistsException {
		Bike bike1 = new TraditionalBike(11);
		records.addRecord(bike1);
		records.getRecordById(13);
	}
	
	
	
}
