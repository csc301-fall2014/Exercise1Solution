package csc301.exercise1;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TrainCompany {

	
	
	// Maintain a set of names of all train companies
	private static Set<String> trainCompanyNames = new HashSet<String>();
	
	
	
	private String name;
	private Map<String, Map<String, DirectRoute>> from2to2route;
	
	
	
	public TrainCompany(String name) {
		setName(name);
		from2to2route = new HashMap<String, Map<String,DirectRoute>>();
	}
	
	
	
	@Override
	public String toString() {
		return String.format("%s, offering %d routes between %d stations", 
				getName(), getDirectRoutesCount(), getStationsCount());
	}
	
	
	
	public String getName() {
		return name;
	}
	
	
	public void setName(String name) {
		name = Utils.normalizeName(name);
		if(name.equals(this.name)){
			return;
		}
		if(trainCompanyNames.contains(name)){
			throw new IllegalArgumentException("Name is already taken - " + name);
		}
		
		// If we're changing the name, remove the old name from the set
		if(this.name != null){
			trainCompanyNames.remove(this.name);
		}
		this.name = name;
		trainCompanyNames.add(this.name);
	}
	
	
	
	/**
	 * @return The DirectRoute object that was created/updated.
	 */
	public DirectRoute createOrUpdateDirectRoute(String fromStation, String toStation, double price){
		fromStation = Utils.normalizeName(fromStation);
		toStation = Utils.normalizeName(toStation);
		DirectRoute r = getDirectRoute(fromStation, toStation);
		
		if(r != null){
			r.setPrice(price);
			return r;
		}
		
		r = new DirectRoute(this, fromStation, toStation, price);
		if(! from2to2route.containsKey(fromStation)){
			from2to2route.put(fromStation, new HashMap<String, DirectRoute>());
		}
		from2to2route.get(fromStation).put(toStation, r);
		return r;
	}
	
	
	/**
	 * Delete the specified route, if it exists.
	 */
	public void deleteDirectRoute(String fromStation, String toStation){
		if(from2to2route.containsKey(fromStation) && from2to2route.get(fromStation).containsKey(toStation)){
			from2to2route.get(fromStation).remove(toStation);
		}
	}
	
	
	/**
	 * @return null if there is no route from <code>fromStation</code> to
	 * 			<code>toStation</code> with this TrainCompany.
	 */
	public DirectRoute getDirectRoute(String fromStation, String toStation){
		if(from2to2route.containsKey(fromStation) && from2to2route.get(fromStation).containsKey(toStation)){
			return from2to2route.get(fromStation).get(toStation);
		} else {
			return null;
		}
	}
	
	
	public Collection<DirectRoute> getDirectRoutesFrom(String fromStation){
		Collection<DirectRoute> routes = new HashSet<DirectRoute>();
		if(! from2to2route.containsKey(fromStation)){
			return routes;
		}
		routes.addAll(from2to2route.get(fromStation).values());
		return routes;
	}
	
	
	public Collection<DirectRoute> getRoutesTo(String toStation){
		Collection<DirectRoute> routes = new HashSet<DirectRoute>();
		for(Map<String,DirectRoute> to2route : from2to2route.values()){
			if(to2route.containsKey(toStation)){
				routes.add(to2route.get(toStation));
			}
		}
		return routes;
	}
	
	
	public Collection<DirectRoute> getAllDirectRoutes(){
		Collection<DirectRoute> routes = new HashSet<DirectRoute>();
		for(Map<String,DirectRoute> to2route : from2to2route.values()){
			routes.addAll(to2route.values());
		}
		return routes;
	}
	
	
	public int getDirectRoutesCount(){
		return getAllDirectRoutes().size();
	}
	
	
	
	/**
	 * @return The number of stations with service by this TrainCompany.
	 * To be clearer:
	 * - Take the union of all stations (from and to) from this.getAllDirectRoutes()
	 * - Count the unique number of stations (i.e. You only count a station
	 *   once, even if there are multiple routes from/to this station) 
	 */
	public int getStationsCount(){
		Set<String> uniqueStationNames = new HashSet<String>();
		for(String fromStation : from2to2route.keySet()){
			uniqueStationNames.add(fromStation);
			uniqueStationNames.addAll(from2to2route.get(fromStation).keySet());
		}
		return uniqueStationNames.size();
	}
	
	
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof TrainCompany && ((TrainCompany) obj).name.equals(this.name);
	}
	
}
