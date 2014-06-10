package model.order;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import model.date.DatePair;
import model.date.EndBeforStartException;
import model.order.Order.Status;
import model.thing.Bike;
import model.thing.MTBike;
import model.thing.Thing;
import model.thing.TraditionalBike;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class OrderTest {
	private Order order;
	private Status status;
	private Float expectedResult;
	private Float price;
	private static DatePair datePair;

	@SuppressWarnings("deprecation")
	@BeforeClass
	public static void initializeDatePair() throws EndBeforStartException {
		datePair = new DatePair(
				new Date(Date.UTC(100 + 14, 11 - 1, 2, 3, 0, 0)), new Date(
						Date.UTC(100 + 14, 11 - 1, 5, 12, 0, 0)));
	}

	@Before
	public void initialize() {
		order = new Order();
		order.setDatePair(datePair);

		Thing thing1 = new TraditionalBike();
		thing1.setDailyRentalPrice(20);
		Bike bike2 = new MTBike();
		bike2.setDailyRentalPrice(50);
		price=(float) ((50+20)*(3*24+9))/24;
		
		
		order.addThing(thing1);
		order.addThing(bike2);
		
		order.setStatus(status);
	}

	public OrderTest(Status status, Float expectedResult) {
		this.status = status;
		this.expectedResult = expectedResult;
	}

	@Parameterized.Parameters
	public static Collection statusVsPrice() {
		return Arrays.asList(new Object[][] { 
				{ Status.CANCELED, 0f}, 
				{ Status.CREATING_ORDER, 0f},
				{ Status.CONFIRMED, (float) ((50+20)*(3*24+9))/24},
				{ Status.IN_REALISATION, 236.25f},
				{ Status.ENDED, 236.25f},
		});
	}
	
	@Test
	public void testCaclulatePrice() {
		System.out.println("Parametrize Status is: " + status );
		 assertEquals(expectedResult, order.calculatePrice(), 0.001);
	}
}
