package model.thing;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import model.date.DatePair;
import model.date.EndBeforStartException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class BikeAddReservationTest {
	Bike bike;
	DatePair datePair;
	Boolean expectedResult;
	
	@Before
	public void initialize() throws EndBeforStartException {
		bike = new TraditionalBike();
		bike.addReservation(new DatePair(new Date(100000L), new Date(150000L)));
	}
	
	public BikeAddReservationTest(DatePair datePair, Boolean expectedResult){
		this.datePair=datePair;
		this.expectedResult=expectedResult;
	}
	
	@Parameterized.Parameters
	public static Collection isOverlapping() throws EndBeforStartException {
		return Arrays.asList(new Object[][]  {
				{new DatePair(new Date(160000L), new Date(200000L)), true},
				{new DatePair(new Date(80000L) , new Date(90000L)), true},
				{new DatePair(new Date(120000L), new Date(160000L)), false},
				{new DatePair(new Date(90000L), new Date(120000L)), false},
				{new DatePair(new Date(110000L), new Date(130000L)), false},
				{new DatePair(new Date(90000L) , new Date(200000L)), false}
		});
	}
	
	
	@Test
	public void checkTest() {
		assertEquals(expectedResult, bike.addReservation(datePair));
	}
}
