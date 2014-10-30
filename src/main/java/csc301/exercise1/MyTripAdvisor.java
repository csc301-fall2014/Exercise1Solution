package csc301.exercise1;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class MyTripAdvisor {

	private Set<TrainCompany> trainCompanies;
	
	
	public MyTripAdvisor() {
		trainCompanies = new HashSet<TrainCompany>();
	}
	
	
	public void addTrainCompany(TrainCompany trainCompany){
		trainCompanies.add(trainCompany);
	}
	
	
	/**
	 * Return the price of a cheapest trip from <code>fromStation</code>
	 * to <code>toStation</code>.
	 * Return -1, if there is no trip between the two specified stations.
	 */
	public double getCheapestPrice(String fromStation, String toStation){
		fromStation = Utils.normalizeName(fromStation);
		toStation = Utils.normalizeName(toStation);
		
		if(fromStation.equals(toStation)){
			return getCheapestCyclePrice(fromStation);
		} else {
			Map<String, Double> prices = computePrices(fromStation, toStation);
			return prices.containsKey(toStation) ? prices.get(toStation) : -1;
		}
	}
	
	
	private double getCheapestCyclePrice(String station){
		double cheapestPrice = -1;
		for(DirectRoute r : getRoutesFrom(station)){
			if(cheapestPrice != -1 && cheapestPrice < r.getPrice()){
				continue;
			}
			double price = r.getPrice() + getCheapestPrice(r.getToStation(), station);
			if(cheapestPrice == -1 || price < cheapestPrice){
				cheapestPrice = price;
			}
		}
		return cheapestPrice;
	}
	
	
	/**
	 * Return a cheapest trip from <code>fromStation</code> to <code>toStation</code>,
	 * as a list of DirectRoute objects.
	 * Return null, if there is no trip between the two specified stations.
	 */
	public List<DirectRoute> getCheapestTrip(String fromStation, String toStation){
		fromStation = Utils.normalizeName(fromStation);
		toStation = Utils.normalizeName(toStation);
		
		if(fromStation.equals(toStation)){
			return getCheapestCycle(fromStation);
		} else {
			return constructTripFromComputedPrices(fromStation, toStation, computePrices(fromStation, toStation));
		}
	}

	
	
	
	
	private List<DirectRoute> getCheapestCycle(String station) {
		List<DirectRoute> cheapestTrip = null;
		for(DirectRoute r : getRoutesFrom(station)){
			List<DirectRoute> t = getCheapestTrip(r.getToStation(), station);
			if(t == null){
				continue;
			}
			
			t.add(0, r);
			if(cheapestTrip == null || getTotalPrice(t) < getTotalPrice(cheapestTrip)){
				cheapestTrip = t;
			}
		}
		
		return cheapestTrip;
	}


	/**
	 * Pre-condition: fromStation and toStation are two different valid 
	 * station names without trailing spaces.
	 */
	private Map<String, Double> computePrices(String fromStation, String toStation){
		
		// Dijkstra's algorithm implementation ...
		
		// Set up data structures
		Map<String, Double> tentativePrices = new HashMap<String, Double>();
		tentativePrices.put(fromStation, 0.0);
		Set<String> visitedStations = new HashSet<String>();
		Set<String> unvisitedStations = new HashSet<String>();
		String currentStation = fromStation;
	    
		
		while(currentStation != null && (! visitedStations.contains(toStation))){
			
			// Go over all unvisited neighbours, and update the tentative prices
			for(DirectRoute r : getUnvisitedRoutesFrom(currentStation, visitedStations)){
				unvisitedStations.add(r.getToStation());
				updateTentativePrices(tentativePrices, r);
			}
			
			// Update the visited/unvisited sets
			visitedStations.add(currentStation);
			unvisitedStations.remove(currentStation);
			
			// Determine the next node we should visit
			currentStation = getNextUnvisitedStation(unvisitedStations, tentativePrices);
		}

		return tentativePrices;
	}
	
	

	private Collection<DirectRoute> getRoutesFrom(String fromStation) {
		return getUnvisitedRoutesFrom(fromStation, null);
	}
	
	
	private Collection<DirectRoute> getUnvisitedRoutesFrom(String fromStation, Set<String> visitedStations) {
		Collection<DirectRoute> routes = new HashSet<DirectRoute>();
		for(TrainCompany c : trainCompanies){
			for(DirectRoute r : c.getDirectRoutesFrom(fromStation)){
				if(visitedStations == null || (! visitedStations.contains(r.getToStation()))){
					routes.add(r);
				}
			}
		}
		return routes;
	}
	
	
	
	private void updateTentativePrices(Map<String, Double> tentativePrices, DirectRoute r){
		double tentativePrice = tentativePrices.get(r.getFromStation()) + r.getPrice();
		if((! tentativePrices.containsKey(r.getToStation())) || (tentativePrice < tentativePrices.get(r.getToStation()))){
			tentativePrices.put(r.getToStation(), tentativePrice);
		}
	}
	
	
	private String getNextUnvisitedStation(Set<String> unvisitedStations, Map<String, Double> prices){
		String candidate = null;
		for(String unvisitedNode : unvisitedStations){
			if(candidate == null || prices.get(unvisitedNode) < prices.get(candidate)){
				candidate = unvisitedNode;
			}
		}
		return candidate;
	}
	
	
	private List<DirectRoute> constructTripFromComputedPrices(String fromStation, String toStation, Map<String, Double> prices){
		if(! prices.containsKey(toStation)){
			return null;
		}
		
		List<DirectRoute> trip = new LinkedList<DirectRoute>();
		// Construct the trip by starting from the end ...
		String current = toStation;
		while(! current.equals(fromStation)){
			DirectRoute r = getRouteInCheapestTrip(current, prices); 
			trip.add(0,  r);
			current = r.getFromStation();
		}
		return trip;
	}
	
	
	private DirectRoute getRouteInCheapestTrip(String toStation, Map<String, Double> prices){
		double targetPrice = prices.get(toStation);
		for(DirectRoute r : getRoutesTo(toStation)){
			if(prices.containsKey(r.getFromStation()) && prices.get(r.getFromStation()) + r.getPrice() == targetPrice){
				return r;
			}
		}
		throw new InternalError("Oops, cannot find route to " + toStation + " from the computed cheapest prices.");
	}
	
	
	
	private Collection<DirectRoute> getRoutesTo(String toStation) {
		Collection<DirectRoute> routes = new HashSet<DirectRoute>();
		for(TrainCompany c : trainCompanies){
			routes.addAll(c.getRoutesTo(toStation));
		}
		return routes;
	}
	
	
	private double getTotalPrice(List<DirectRoute> trip){
		double price = 0;
		for(DirectRoute r : trip){
			price += r.getPrice();
		}
		return price;
	}
	
}
