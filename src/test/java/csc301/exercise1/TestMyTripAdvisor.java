package csc301.exercise1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import csc301.exercise1.util.Constants;
import csc301.exercise1.util.Utils;

public class TestMyTripAdvisor {	
	
	private static TrainCompany fastTrain;
	private static TrainCompany swiftRail;

	private static TrainCompany tc1;
	private static TrainCompany tc2;
	private static TrainCompany tc3;
	
	private static TrainCompany spiderRail1;
	private static TrainCompany spiderRail2;
	private static TrainCompany spiderRail3;
	
	private static TrainCompany cyclicTrain;

	private MyTripAdvisor advisor;
	
	
	//=========================================================================
	// JUnit initialization/setup methods
	

	@BeforeClass
	public static void init() throws Exception {
		// Create TrainCompany instances from data files in the resources folder.
		fastTrain = Utils.createCopmanyFromDataFile("FastTrain.txt");
		swiftRail = Utils.createCopmanyFromDataFile("SwiftRail.txt");
		tc1 = Utils.createCopmanyFromDataFile("TC1.txt");
		tc2 = Utils.createCopmanyFromDataFile("TC2.txt");
		tc3 = Utils.createCopmanyFromDataFile("TC3.txt");
		spiderRail1 = Utils.createCopmanyFromDataFile("SpiderRail1.txt");
		spiderRail2 = Utils.createCopmanyFromDataFile("SpiderRail2.txt");
		spiderRail3 = Utils.createCopmanyFromDataFile("SpiderRail3.txt");
		cyclicTrain = Utils.createCopmanyFromDataFile("CyclicTrain.txt");
	}
	
	
	@Before
	public void setup(){
		advisor = new MyTripAdvisor();
	}
	
	
	
	//=========================================================================
	// Helper methods ...
	
	
	/**
	 * A little convenience helper, that allow us to add an arbitrary
	 * number of copmanies to our trip advisor in one line.
	 */
	private void addCopmanies(TrainCompany... trainCompanies){
		for (int i = 0; i < trainCompanies.length; i++) {
			advisor.addTrainCompany(trainCompanies[i]);
		}
	}
	
	
	/**
	 * Assert that a trip is as we expect.
	 * A little convenience helper function that allows us to specify an
	 * expected trip as an object array.
	 */
	private void assertTripEquals(List<DirectRoute> trip, Object[][] tripData){
		assertEquals(tripData.length, trip.size());
		for (int i = 0; i < tripData.length; i++) {
			// Check equality using a utility method, instead of equal(),
			// to avoid failing test here because of bad implementation of DirectRoute.equal
			DirectRoute expected = new DirectRoute((TrainCompany)tripData[i][0], 
					(String)tripData[i][1], (String)tripData[i][2], 
					((Number)tripData[i][3]).doubleValue());
			DirectRoute actual = trip.get(i);
			assertTrue(String.format("Expected %s, got %s.", expected, actual), 
					Utils.directRouteEqual(expected, actual));
		}
	}
	
	
	/**
	 * Assert that the given trip goes through the given stations.
	 */
	private void assertTripStationsEqual(List<DirectRoute> trip, String[] stations){
		assertEquals(stations.length - 1, trip.size());
		int i = 0;
		for(DirectRoute r : trip){
			assertEquals(r.getFromStation(), stations[i]);
			assertEquals(r.getToStation(), stations[i + 1]);
			i++;
		}
	}
	
	
	//=========================================================================

	
	@Test(timeout=3000)
	public void advisorWithSingleCopmanyDirectTrip() {
		addCopmanies(fastTrain);
		assertTripEquals(advisor.getCheapestTrip(Constants.OTTAWA, Constants.MONTREAL), new Object[][]{
				{fastTrain, Constants.OTTAWA, Constants.MONTREAL, 25}
		});
	}
	
	
	@Test(timeout=3000)
	public void advisorWithSingleCopmanyDirectTripPrice() {
		addCopmanies(fastTrain);
		assertEquals(25, advisor.getCheapestPrice(Constants.OTTAWA, Constants.MONTREAL), 0.001);
	}
	
	
	@Test(timeout=3000)
	public void advisorWithSingleCopmanyMultiRouteTrip() {
		addCopmanies(fastTrain);
		assertTripEquals(advisor.getCheapestTrip(Constants.TORONTO, Constants.MONTREAL), new Object[][]{
				{fastTrain, Constants.TORONTO, Constants.OTTAWA, 31},
				{fastTrain, Constants.OTTAWA, Constants.MONTREAL, 25}
		});
	}
	
	
	@Test(timeout=3000)
	public void advisorWithSingleCopmanyMultiRouteTripPrice() {
		addCopmanies(fastTrain);
		assertEquals(56, advisor.getCheapestPrice(Constants.TORONTO, Constants.MONTREAL), 0.001);
	}
	

	@Test(timeout=3000)
	public void testThatTheAlgorithmDoesNotGetStuckInALoop() {
		addCopmanies(cyclicTrain);
		assertTripStationsEqual(advisor.getCheapestTrip("A", "D"), new String[]{"A", "B", "C", "D"});
	}
	
	
	
	
	@Test(timeout=3000)
	public void twoRouteTripWhereRoutesAreFromDifferentCompanies() {
		addCopmanies(fastTrain, swiftRail);
		
		List<DirectRoute> trip = advisor.getCheapestTrip(Constants.TORONTO, Constants.MONTREAL);
		
		assertTripEquals(trip, new Object[][]{
				{swiftRail, Constants.TORONTO, Constants.OTTAWA, 30},
				{fastTrain, Constants.OTTAWA, Constants.MONTREAL, 25}
		});
	}
	
	
	@Test(timeout=3000)
	public void priceOfTwoRouteTripWhereRoutesAreFromDifferentCompanies() {
		addCopmanies(fastTrain, swiftRail);
		// Make sure that we got the total price we expect
		assertEquals(55, advisor.getCheapestPrice(Constants.TORONTO, Constants.MONTREAL), 0.001);
	}
	
	
	@Test(timeout=3000)
	public void priceOfNonExistingTrip() {
		addCopmanies(fastTrain);
		
		// Make sure that we got the total price we expect
		assertEquals(-1, advisor.getCheapestPrice(Constants.TORONTO, Constants.BOSTON), 0.001);
	}
	
	@Test(timeout=3000)
	public void nonExistingTrip() {
		addCopmanies(fastTrain, swiftRail);
		assertNull(advisor.getCheapestTrip(Constants.TORONTO, Constants.BOSTON));
	}
	
	
	
	
	@Test(timeout=3000)
	public void testLongTripWhereAllRoutesAreOfferedByTwoCopmaniesForTheSamePrice() {
		addCopmanies(tc1, tc2);
		
		assertTripStationsEqual(advisor.getCheapestTrip("A", "E"), 
				new String[]{"A", "B", "C", "D", "E"});
	}

	
	@Test(timeout=3000)
	public void testLongTripWhereAllRoutesAreOfferedByTwoCopmaniesForTheSamePriceExceptForOne() {
		addCopmanies(tc1, tc2);
		assertEquals(tc2, advisor.getCheapestTrip("A", "K").get(4).getTrainCompany());
	}
	
	
	@Test(timeout=3000)
	public void testLongTripThatIsCheaperThanADirectAlternative() {
		addCopmanies(tc1, tc3);
		assertEquals(10, advisor.getCheapestPrice("A", "K"), 0.001);
	}
	
	
	
	
	@Test(timeout=3000)
	public void testTripWhereEachRouteHasManyOptions() {
		addCopmanies(spiderRail1, spiderRail2, spiderRail3);
		// Each company offers routes 
		//   - From A to B1, B2, B3.
		//   - From each of B1, B2, B3 to each of C1, C2, C3.
		//   - From each of C1, C2, C3 to D.
		assertTripEquals(advisor.getCheapestTrip("A", "D"), new Object[][]{
				{spiderRail1, "A", "B3", 1},
				{spiderRail2, "B3", "C2", 1},
				{spiderRail3, "C2", "D", 1},
		});
	}
	
	
	
	@Test(timeout=3000)
	public void cyclicTripWithExactlyTwoStations() {
		addCopmanies(fastTrain, swiftRail);
		
		List<DirectRoute> trip = advisor.getCheapestTrip(Constants.NYC, Constants.NYC);
		
		assertTripEquals(trip, new Object[][]{
				{fastTrain, Constants.NYC, Constants.BOSTON, 34.2},
				{swiftRail, Constants.BOSTON, Constants.NYC, 29.3}
		});
	}
	
	
	@Test(timeout=3000)
	public void priceOfCyclicTripWithExactlyTwoStations() {
		addCopmanies(fastTrain, swiftRail);
		assertEquals(63.5, advisor.getCheapestPrice(Constants.NYC, Constants.NYC), 0.001);
	}
	
	
	@Test(timeout=3000)
	public void cyclicTripWithMoreThanTwoStations() {
		addCopmanies(cyclicTrain);
		assertTripStationsEqual(advisor.getCheapestTrip("A", "A"), new String[]{"A", "B", "C", "A"});
	}
}
