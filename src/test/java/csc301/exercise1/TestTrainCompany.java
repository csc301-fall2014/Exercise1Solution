package csc301.exercise1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashSet;

import org.junit.Test;

import csc301.exercise1.util.Constants;
import csc301.exercise1.util.Utils;

public class TestTrainCompany {

	
	// A method used to bypass DirectRoute.equals (in case it is badly implemented)
	private void assertDirectRoutesEqual(DirectRoute expected, DirectRoute actual){
		assertTrue(String.format("Expected %s, got %s.", expected, actual), 
				Utils.directRouteEqual(expected, actual));
	}
	
	//-------------------------------------------------------------------------

	@Test(expected=IllegalArgumentException.class)
	public void createInstanceWithNullName() {
		new TrainCompany(null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void createInstanceWithEmptyName() {
		new TrainCompany("");
	}

	@Test(expected=IllegalArgumentException.class)
	public void createInstanceWithWhiteSpaceName() {
		new TrainCompany(" \t");
	}


	//-------------------------------------------------------------------------


	@Test(expected=IllegalArgumentException.class)
	public void setNullName() {
		TrainCompany c = Utils.createCompanyWithUniqueName();
		c.setName(null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void setEmptyName() {
		TrainCompany c = Utils.createCompanyWithUniqueName();
		c.setName("");
	}

	@Test(expected=IllegalArgumentException.class)
	public void setWhiteSpaceName() {
		TrainCompany c = Utils.createCompanyWithUniqueName();
		c.setName("\t ");
	}

	//-------------------------------------------------------------------------

	@Test
	public void equalsWithTheSameCompany(){
		TrainCompany c = Utils.createCompanyWithUniqueName();
		assertEquals(c, c);
	}

	@Test
	public void equalsWithDifferentCompanies(){
		TrainCompany c1 = Utils.createCompanyWithUniqueName();
		TrainCompany c2 = Utils.createCompanyWithUniqueName();
		assertNotEquals(c1, c2);
	}


	@Test
	public void equalsWithDifferentType(){
		TrainCompany c = Utils.createCompanyWithUniqueName();
		assertNotEquals(c, new Object());
	}

	//-------------------------------------------------------------------------


	@Test(expected=IllegalArgumentException.class)
	public void createInstanceWithExistingName() {
		TrainCompany c = Utils.createCompanyWithUniqueName();
		new TrainCompany(c.getName());
	}


	@Test(expected=IllegalArgumentException.class)
	public void createInstanceWithExistingNameWithTrailingSpaces1() {
		TrainCompany c = Utils.createCompanyWithUniqueName();
		new TrainCompany(c.getName() + " ");
	}

	@Test(expected=IllegalArgumentException.class)
	public void createInstanceWithExistingNameWithTrailingSpaces2() {
		TrainCompany c = Utils.createCompanyWithUniqueName();
		new TrainCompany("\t" + c.getName());
	}


	//-------------------------------------------------------------------------

	@Test(expected=IllegalArgumentException.class)
	public void setNameToAnExistingName() {
		TrainCompany c1 = Utils.createCompanyWithUniqueName();
		TrainCompany c2 = Utils.createCompanyWithUniqueName();
		c2.setName(c1.getName());
	}


	@Test(expected=IllegalArgumentException.class)
	public void setNameToAnExistingNameWithTrailingSpaces1() {
		TrainCompany c1 = Utils.createCompanyWithUniqueName();
		TrainCompany c2 = Utils.createCompanyWithUniqueName();
		c2.setName(c1.getName() + " ");
	}

	@Test(expected=IllegalArgumentException.class)
	public void setNameToAnExistingNameWithTrailingSpaces2() {
		TrainCompany c1 = Utils.createCompanyWithUniqueName();
		TrainCompany c2 = Utils.createCompanyWithUniqueName();
		c2.setName("\t" + c1.getName());
	}


	//-------------------------------------------------------------------------

	@Test(expected=IllegalArgumentException.class)
	public void createOrUpdateRouteWithNullFromStation() {
		TrainCompany c = Utils.createCompanyWithUniqueName();
		c.createOrUpdateDirectRoute(null, Constants.TORONTO, 2.50);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void createOrUpdateRouteWithEmptyFromStation() {
		TrainCompany c = Utils.createCompanyWithUniqueName();
		c.createOrUpdateDirectRoute("", Constants.TORONTO, 2.50);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void createOrUpdateRouteWithWhiteSpaceFromStation() {
		TrainCompany c = Utils.createCompanyWithUniqueName();
		c.createOrUpdateDirectRoute(" \t", Constants.TORONTO, 2.50);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void createOrUpdateRouteWithNullToStation() {
		TrainCompany c = Utils.createCompanyWithUniqueName();
		c.createOrUpdateDirectRoute(Constants.TORONTO, null, 2.50);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void createOrUpdateRouteWithEmptyToStation() {
		TrainCompany c = Utils.createCompanyWithUniqueName();
		c.createOrUpdateDirectRoute(Constants.TORONTO, "", 2.50);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void createOrUpdateRouteWithWhiteSpaceToStation() {
		TrainCompany c = Utils.createCompanyWithUniqueName();
		c.createOrUpdateDirectRoute(Constants.TORONTO, " \t", 2.50);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void createOrUpdateRouteWithNegativePrice() {
		TrainCompany c = Utils.createCompanyWithUniqueName();
		c.createOrUpdateDirectRoute(Constants.TORONTO, Constants.MONTREAL, -2.50);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void createRouteWithTheSameFromAndTostations() {
		TrainCompany c = Utils.createCompanyWithUniqueName();
		c.createOrUpdateDirectRoute(Constants.TORONTO, Constants.TORONTO, 7.2);
	}
	
	//-------------------------------------------------------------------------
	
	@Test
	public void createRoute() {
		TrainCompany c = Utils.createCompanyWithUniqueName();
		assertDirectRoutesEqual(new DirectRoute(c, Constants.TORONTO, Constants.MONTREAL, 22.50),
				c.createOrUpdateDirectRoute(Constants.TORONTO, Constants.MONTREAL, 22.50));
	}
	
	@Test
	public void updateExistingRoute() {
		TrainCompany c = Utils.createCompanyWithUniqueName();
		c.createOrUpdateDirectRoute(Constants.TORONTO, Constants.MONTREAL, 22.50);
		
		assertDirectRoutesEqual(new DirectRoute(c, Constants.TORONTO, Constants.MONTREAL, 24.50),
				c.createOrUpdateDirectRoute(Constants.TORONTO, Constants.MONTREAL, 24.50));
	}
	
	@Test
	public void updateRouteMultipleTimesWithoutError() {
		TrainCompany c = Utils.createCompanyWithUniqueName();
		for (int i = 0; i < 10; i++) {
			// Generate some semi-arbitrary prices
			double price = 20 + (0.2 * i * Math.pow(-1, i));
			assertDirectRoutesEqual(new DirectRoute(c, Constants.TORONTO, Constants.MONTREAL, price),
					c.createOrUpdateDirectRoute(Constants.TORONTO, Constants.MONTREAL, price));
		}
	}
	
	@Test
	public void createManyDifferentRoutes() {
		TrainCompany c = Utils.createCompanyWithUniqueName();
		String[] stations = {Constants.BOSTON, Constants.MONTREAL, Constants.NYC, Constants.OTTAWA, Constants.TORONTO};
		for (int i = 0; i < stations.length; i++) {
			for(int j = i+1; j < stations.length; j++){
				// Generate some semi-arbitrary prices
				double price1 = 20 + ((0.2 * i + 0.3 * j) * Math.pow(-1, i + j));
				double price2 = 22 + ((0.3 * i + 0.15 * j) * Math.pow(-1, i + j + 1));
				assertDirectRoutesEqual(new DirectRoute(c, stations[i], stations[j], price1),
						c.createOrUpdateDirectRoute(stations[i], stations[j], price1));
				assertDirectRoutesEqual(new DirectRoute(c, stations[j], stations[i], price2),
						c.createOrUpdateDirectRoute(stations[j], stations[i], price2));
			}
		}
	}
	
	
	@Test
	public void createAndGetRouteVerifyCompany() {
		double price = 22.50;
		TrainCompany c = Utils.createCompanyWithUniqueName();
		c.createOrUpdateDirectRoute(Constants.TORONTO, Constants.MONTREAL, price);
		assertEquals(c.getName(), c.getDirectRoute(Constants.TORONTO, Constants.MONTREAL).getTrainCompany().getName());
	}
	
	@Test
	public void createAndGetRouteVerifyFromStation() {
		double price = 22.50;
		TrainCompany c = Utils.createCompanyWithUniqueName();
		c.createOrUpdateDirectRoute(Constants.TORONTO, Constants.MONTREAL, price);
		assertEquals(Constants.TORONTO, c.getDirectRoute(Constants.TORONTO, Constants.MONTREAL).getFromStation());
	}
	
	@Test
	public void createAndGetRouteVerifyToStation() {
		double price = 22.50;
		TrainCompany c = Utils.createCompanyWithUniqueName();
		c.createOrUpdateDirectRoute(Constants.TORONTO, Constants.MONTREAL, price);
		assertEquals(Constants.MONTREAL, c.getDirectRoute(Constants.TORONTO, Constants.MONTREAL).getToStation());
	}
	
	@Test
	public void createAndGetRouteVerifyPrice() {
		double price = 22.50;
		TrainCompany c = Utils.createCompanyWithUniqueName();
		c.createOrUpdateDirectRoute(Constants.TORONTO, Constants.MONTREAL, price);
		assertEquals(price, c.getDirectRoute(Constants.TORONTO, Constants.MONTREAL).getPrice(), 0.001);
	}
	
	
	//-------------------------------------------------------------------------
	
	@Test
	public void deleteNonExistingRouteWithoutError() {
		TrainCompany c = Utils.createCompanyWithUniqueName();
		c.createOrUpdateDirectRoute(Constants.TORONTO, Constants.MONTREAL, 22.5);
		c.deleteDirectRoute(Constants.NYC, Constants.TORONTO);
	}
	
	
	@Test
	public void createDeleteAndGetRoute() {
		TrainCompany c = Utils.createCompanyWithUniqueName();
		c.createOrUpdateDirectRoute(Constants.TORONTO, Constants.MONTREAL, 22.5);
		c.deleteDirectRoute(Constants.TORONTO, Constants.MONTREAL);
		assertNull(c.getDirectRoute(Constants.TORONTO, Constants.MONTREAL));
	}
	
	
	@Test
	public void sanityCheckThatDeleteRouteDoesDeleteOtherRoutes() {
		TrainCompany c = Utils.createCompanyWithUniqueName();
		c.createOrUpdateDirectRoute(Constants.TORONTO, Constants.MONTREAL, 22.5);
		c.createOrUpdateDirectRoute(Constants.OTTAWA, Constants.MONTREAL, 22.5);
		c.deleteDirectRoute(Constants.TORONTO, Constants.MONTREAL);
		assertNotNull(c.getDirectRoute(Constants.OTTAWA, Constants.MONTREAL));
	}
	
	
	//-------------------------------------------------------------------------
	
	
	@Test
	public void testGetAllRoutesWithZeroRoutes(){
		TrainCompany c = Utils.createCompanyWithUniqueName();
		assertEquals(0, c.getAllDirectRoutes().size());
	}
	
	
	@Test
	public void testGetAllRoutesWithOneRoute(){
		TrainCompany c = Utils.createCompanyWithUniqueName();
		c.createOrUpdateDirectRoute(Constants.TORONTO, Constants.MONTREAL, 22.5);
		Collection<DirectRoute> allRoutes = c.getAllDirectRoutes();	
		assertTrue(allRoutes.size() == 1 && Utils.isIn(new DirectRoute(c, Constants.TORONTO, Constants.MONTREAL, 22.5), allRoutes));
	}
	
	
	@Test
	public void testGetAllRoutesWithMultipleRoutes(){
		TrainCompany c = Utils.createCompanyWithUniqueName();
		Collection<DirectRoute> createdRoutes = new HashSet<DirectRoute>(); 
		createdRoutes.add(c.createOrUpdateDirectRoute(Constants.TORONTO, Constants.MONTREAL, 22.5));
		createdRoutes.add(c.createOrUpdateDirectRoute(Constants.TORONTO, Constants.OTTAWA, 18.32));
		createdRoutes.add(c.createOrUpdateDirectRoute(Constants.BOSTON, Constants.MONTREAL, 24.7));
		createdRoutes.add(c.createOrUpdateDirectRoute(Constants.MONTREAL, Constants.BOSTON, 22.7));
		createdRoutes.add(c.createOrUpdateDirectRoute(Constants.NYC, Constants.BOSTON, 18.99));
		
		Collection<DirectRoute> allRoutes = c.getAllDirectRoutes();
		assertEquals(createdRoutes.size(), allRoutes.size());
		for(DirectRoute r : createdRoutes){
			assertTrue(Utils.isIn(r, allRoutes));
		}
	}
	
	
	
	
	@Test
	public void testDirectRoutesCountWithZeroRoutes(){
		TrainCompany c = Utils.createCompanyWithUniqueName();
		assertEquals(0, c.getDirectRoutesCount());
	}
	
	
	@Test
	public void testDirectRoutesCountWithOneRoute(){
		TrainCompany c = Utils.createCompanyWithUniqueName();
		c.createOrUpdateDirectRoute(Constants.TORONTO, Constants.OTTAWA, 30.2);
		assertEquals(1, c.getDirectRoutesCount());
	}
	
	
	@Test
	public void testDirectRoutesCountWithMultipleRoutes(){
		TrainCompany c = Utils.createCompanyWithUniqueName();
		c.createOrUpdateDirectRoute(Constants.TORONTO, Constants.OTTAWA, 30.2);
		c.createOrUpdateDirectRoute(Constants.TORONTO, Constants.MONTREAL, 30.2);
		c.createOrUpdateDirectRoute(Constants.TORONTO, Constants.NYC, 30.2);
		assertEquals(3, c.getDirectRoutesCount());
	}
	
	
	
	@Test
	public void testStationsCountWithZeroStations(){
		TrainCompany c = Utils.createCompanyWithUniqueName();
		assertEquals(0, c.getStationsCount());
	}
	
	
	@Test
	public void testStationsCountWithTwoStations(){
		TrainCompany c = Utils.createCompanyWithUniqueName();
		c.createOrUpdateDirectRoute(Constants.TORONTO, Constants.OTTAWA, 30.2);
		assertEquals(2, c.getStationsCount());
	}
	
	
	@Test
	public void testStationsCountWithMultipleStationsAndMultipleRoutesPerStation(){
		TrainCompany c = Utils.createCompanyWithUniqueName();
		c.createOrUpdateDirectRoute(Constants.TORONTO, Constants.OTTAWA, 30.2);
		c.createOrUpdateDirectRoute(Constants.TORONTO, Constants.MONTREAL, 30.2);
		c.createOrUpdateDirectRoute(Constants.TORONTO, Constants.NYC, 30.2);
		c.createOrUpdateDirectRoute(Constants.OTTAWA, Constants.TORONTO, 30.2);
		c.createOrUpdateDirectRoute(Constants.OTTAWA, Constants.MONTREAL, 30.2);
		c.createOrUpdateDirectRoute(Constants.OTTAWA, Constants.NYC, 30.2);
		c.createOrUpdateDirectRoute(Constants.MONTREAL, Constants.TORONTO, 30.2);
		c.createOrUpdateDirectRoute(Constants.MONTREAL, Constants.OTTAWA, 30.2);
		c.createOrUpdateDirectRoute(Constants.MONTREAL, Constants.NYC, 30.2);
		c.createOrUpdateDirectRoute(Constants.NYC, Constants.TORONTO, 30.2);
		c.createOrUpdateDirectRoute(Constants.NYC, Constants.OTTAWA, 30.2);
		c.createOrUpdateDirectRoute(Constants.NYC, Constants.MONTREAL, 30.2);
		
		assertEquals(4, c.getStationsCount());
	}
	
	
	
	
	
}
