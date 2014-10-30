package csc301.exercise1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashSet;

import org.junit.Test;

import csc301.exercise1.util.Constants;
import csc301.exercise1.util.Utils;


public class TestDirectRoute {


	@Test
	public void testCreateInstance(){
		new DirectRoute(Utils.createCompanyWithUniqueName(), Constants.TORONTO, Constants.OTTAWA, 37.5);
	}

	@Test
	public void testCreateInstanceWithPriceZero(){
		new DirectRoute(Utils.createCompanyWithUniqueName(), Constants.TORONTO, Constants.OTTAWA, 0);
	}



	// BEGIN: Constructor with invalid arguments

	@Test(expected=IllegalArgumentException.class)
	public void testCreateInstanceWithNullCompany(){
		new DirectRoute(null, Constants.TORONTO, Constants.OTTAWA, 37.5);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCreateInstanceWithNullFromStation(){
		new DirectRoute(Utils.createCompanyWithUniqueName(), null, Constants.OTTAWA, 37.5);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCreateInstanceWithNullToStation(){
		new DirectRoute(Utils.createCompanyWithUniqueName(), Constants.TORONTO, null, 37.5);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCreateInstanceWithEmptyFromStation(){
		new DirectRoute(Utils.createCompanyWithUniqueName(), "", Constants.OTTAWA, 37.5);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCreateInstanceWithEmptyToStation(){
		new DirectRoute(Utils.createCompanyWithUniqueName(), Constants.TORONTO, "", 37.5);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCreateInstanceWithWhiteSpaceOnlyFromStation(){
		new DirectRoute(Utils.createCompanyWithUniqueName(), "  ", Constants.OTTAWA, 37.5);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCreateInstanceWithWhiteSpaceOnlyToStation(){
		new DirectRoute(Utils.createCompanyWithUniqueName(), Constants.TORONTO, "\t", 37.5);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCreateInstanceWithNegativePrice(){
		new DirectRoute(Utils.createCompanyWithUniqueName(), Constants.TORONTO, Constants.OTTAWA, -0.2);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateInstanceWithTheSameToAndFromStations(){
		new DirectRoute(Utils.createCompanyWithUniqueName(), Constants.TORONTO, Constants.TORONTO, 3.2);
	}


	// END: Constructor with invalid arguments


	// BEGIN: Setters with invalid arguments

	@Test(expected=IllegalArgumentException.class)
	public void testSetNullCompany(){
		DirectRoute r = new DirectRoute(Utils.createCompanyWithUniqueName(), Constants.TORONTO, Constants.OTTAWA, 37.5);
		r.setTrainCompany(null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSetNullFromStation(){
		DirectRoute r = new DirectRoute(Utils.createCompanyWithUniqueName(), Constants.TORONTO, Constants.OTTAWA, 37.5);
		r.setFromStation(null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSetNullToStation(){
		DirectRoute r = new DirectRoute(Utils.createCompanyWithUniqueName(), Constants.TORONTO, Constants.OTTAWA, 37.5);
		r.setToStation(null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSetEmptyFromStation(){
		DirectRoute r = new DirectRoute(Utils.createCompanyWithUniqueName(), Constants.TORONTO, Constants.OTTAWA, 37.5);
		r.setFromStation("");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSetEmptyToStation(){
		DirectRoute r = new DirectRoute(Utils.createCompanyWithUniqueName(), Constants.TORONTO, Constants.OTTAWA, 37.5);
		r.setToStation("");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSetWhiteSpaceOnlyFromStation(){
		DirectRoute r = new DirectRoute(Utils.createCompanyWithUniqueName(), Constants.TORONTO, Constants.OTTAWA, 37.5);
		r.setFromStation("\t");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSetWhiteSpaceOnlyToStation(){
		DirectRoute r = new DirectRoute(Utils.createCompanyWithUniqueName(), Constants.TORONTO, Constants.OTTAWA, 37.5);
		r.setToStation(" ");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSetNegativePrice(){
		DirectRoute r = new DirectRoute(Utils.createCompanyWithUniqueName(), Constants.TORONTO, Constants.OTTAWA, 37.5);
		r.setPrice(-1.7);
	}

	// END: Setters with invalid arguments
	
	
	// BEGIN: Test equality
	
	@Test
	public void testEqualsOnEqualInstances(){
		TrainCompany c = Utils.createCompanyWithUniqueName();
		DirectRoute r1 = new DirectRoute(c, Constants.TORONTO, Constants.MONTREAL, 32.3);
		DirectRoute r2 = new DirectRoute(c, Constants.TORONTO, Constants.MONTREAL, 32.3);
		assertEquals(r1, r2);
	}
	
	@Test
	public void testEqualsFromStationDifferInTrailingSpaces(){
		TrainCompany c = Utils.createCompanyWithUniqueName();
		DirectRoute r1 = new DirectRoute(c, Constants.TORONTO, Constants.MONTREAL, 32.3);
		DirectRoute r2 = new DirectRoute(c, Constants.TORONTO + "  ", Constants.MONTREAL, 32.3);
		assertEquals(r1, r2);
	}
	
	@Test
	public void testEqualsToStationDifferInTrailingSpaces(){
		TrainCompany c = Utils.createCompanyWithUniqueName();
		DirectRoute r1 = new DirectRoute(c, Constants.TORONTO, Constants.MONTREAL, 32.3);
		DirectRoute r2 = new DirectRoute(c, Constants.TORONTO, "\t" + Constants.MONTREAL, 32.3);
		assertEquals(r1, r2);
	}
	
	
	@Test
	public void testEqualsDifferentCopmanies(){
		TrainCompany c1 = Utils.createCompanyWithUniqueName();
		TrainCompany c2 = Utils.createCompanyWithUniqueName();
		DirectRoute r1 = new DirectRoute(c1, Constants.TORONTO, Constants.MONTREAL, 32.3);
		DirectRoute r2 = new DirectRoute(c2, Constants.TORONTO, Constants.MONTREAL, 32.3);
		assertNotEquals(r1, r2);
	}
	
	@Test
	public void testEqualsDifferentFromStation(){
		TrainCompany c = Utils.createCompanyWithUniqueName();
		DirectRoute r1 = new DirectRoute(c, Constants.TORONTO, Constants.MONTREAL, 32.3);
		DirectRoute r2 = new DirectRoute(c, Constants.TORONTO + "X", Constants.MONTREAL, 32.3);
		assertNotEquals(r1, r2);
	}
	
	@Test
	public void testEqualsDifferentToStation(){
		TrainCompany c = Utils.createCompanyWithUniqueName();
		DirectRoute r1 = new DirectRoute(c, Constants.TORONTO, Constants.MONTREAL, 32.3);
		DirectRoute r2 = new DirectRoute(c, Constants.TORONTO, "x" + Constants.MONTREAL, 32.3);
		assertNotEquals(r1, r2);
	}
	
	@Test
	public void testEqualsDifferentPrice(){
		TrainCompany c = Utils.createCompanyWithUniqueName();
		DirectRoute r1 = new DirectRoute(c, Constants.TORONTO, Constants.MONTREAL, 32.3);
		DirectRoute r2 = new DirectRoute(c, Constants.TORONTO, Constants.MONTREAL, 32.4);
		assertNotEquals(r1, r2);
	}
	
	@Test
	public void testEqualsWithNonDirectRouteObject(){
		DirectRoute r1 = new DirectRoute(Utils.createCompanyWithUniqueName(), Constants.TORONTO, Constants.MONTREAL, 32.3);
		assertNotEquals(r1, new Object());
	}
	
	@Test
	public void testContainedInACollection(){
		DirectRoute r1 = new DirectRoute(Utils.createCompanyWithUniqueName(), Constants.TORONTO, Constants.MONTREAL, 32.3);
		Collection<DirectRoute> routes = new HashSet<DirectRoute>();
		routes.add(r1);
		assertTrue(routes.contains(r1));
	}
	
	@Test
	public void testNotContainedInACollection(){
		DirectRoute r1 = new DirectRoute(Utils.createCompanyWithUniqueName(), Constants.TORONTO, Constants.MONTREAL, 32.3);
		DirectRoute r2 = new DirectRoute(Utils.createCompanyWithUniqueName(), Constants.MONTREAL, Constants.OTTAWA, 25.1);
		Collection<DirectRoute> routes = new HashSet<DirectRoute>();
		routes.add(r1);
		assertFalse(routes.contains(r2));
	}
	
	// END: Test equality

	
	// BEGIN: Test getters
	
	@Test
	public void testGetCopmany(){
		TrainCompany c = Utils.createCompanyWithUniqueName();
		DirectRoute r = new DirectRoute(c, Constants.TORONTO, Constants.MONTREAL, 32.3);
		// Notice: We don't want to depend on TrainCopmany's equals method.
		//         We're testing if two things are the exact same instance.
		//         That is, we're checking whether c == r.getTrainCompany(), and not whether c.equals(r.getTrainCompany())
		assertSame(c, r.getTrainCompany());
	}
	
	@Test
	public void testGetFromStation(){
		DirectRoute r = new DirectRoute(Utils.createCompanyWithUniqueName(), Constants.TORONTO, Constants.MONTREAL, 32.3);
		assertEquals(Constants.TORONTO, r.getFromStation());
	}
	
	@Test
	public void testGetToStation(){
		DirectRoute r = new DirectRoute(Utils.createCompanyWithUniqueName(), Constants.TORONTO, Constants.MONTREAL, 32.3);
		assertEquals(Constants.MONTREAL, r.getToStation());
	}
	
	@Test
	public void testGetPrice(){
		double price = 32.1;
		DirectRoute r = new DirectRoute(Utils.createCompanyWithUniqueName(), Constants.TORONTO, Constants.MONTREAL, price);
		assertTrue(price == r.getPrice());
	}
	
	// END: Test getters
	
}
