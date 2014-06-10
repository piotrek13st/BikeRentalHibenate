package model.date;

import java.sql.Date;

import org.junit.Test;

import model.date.EndBeforStartException;

public class DatePairTest {
	
	@Test(expected=EndBeforStartException.class)
	public void creatingDataPair2Test() throws EndBeforStartException {
		DatePair datePair = new DatePair(new Date(20000L), new Date(10000L));
	}
	
	@Test
	public void creatingDataPair1Test() throws EndBeforStartException {
		DatePair datePair = new DatePair(new Date(10000L), new Date(20000L));
	}
	

	
}
