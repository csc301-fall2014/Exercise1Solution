package csc301.exercise1.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.UUID;

import csc301.exercise1.DirectRoute;
import csc301.exercise1.TrainCompany;

public class Utils {

	
	/**
	 * A utility method that creates and returns a TrainCompany isntance based
	 * on data in a text file.
	 * 
	 * Pre-condition: There is a file called <code>dataFileName</code> in the src/test/resources folder.
	 * 
	 * See existing files in src/test/resources for an example of the format. 
	 */
	public static TrainCompany createCopmanyFromDataFile(String dataFileName) throws IOException{
		InputStream in = ClassLoader.getSystemResourceAsStream(dataFileName);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
		// The first line of the file should contain the company's name
		String line = br.readLine();
		TrainCompany company = new TrainCompany(line.trim());
		
		// The next lines are expected to be of the format fromStation,toStation,price
		line = br.readLine();
		while(line != null){
			line = line.trim();
			// Skip blank lines
			if(line.isEmpty()){
				continue;
			}
			
			// Parse the line, and add the route to the company 
			String[] parts = line.split(",");
			company.createOrUpdateDirectRoute(parts[0].trim(), parts[1].trim(), Double.parseDouble(parts[2].trim()));
			
			line = br.readLine();
		}
		
		br.close();
		return company;
	}
	
	
	
	
	public static TrainCompany createCompanyWithUniqueName(){
		return new TrainCompany(UUID.randomUUID().toString());
	}
	
	
	/**
	 * A Collection's might use hashCode() instead of equals() to determine
	 * whether it contains an item.
	 * Therefore, we need to have a way to check if a collection contains a route
	 * that depends only on DirectRoute's equals method.   
	 */
	public static boolean isIn(DirectRoute r, Collection<DirectRoute> routes){
		Iterator<DirectRoute> itr = routes.iterator();
		while (itr.hasNext()) {
			DirectRoute r2 = itr.next();
			if(directRouteEqual(r, r2)){
				return true;
			}
		}
		return false;
	}
	
	
	// NOTE: In a real codebase, this method wouldn't exist.
	// I just don't want students to fail tests in TestMyTripAdvisor, because
	// they didn't implement DirectRoute.equals properly
	public static boolean directRouteEqual(DirectRoute r1, DirectRoute r2){
		return Objects.equals(r1.getTrainCompany().getName(), r2.getTrainCompany().getName()) &&
				Objects.equals(r1.getFromStation(), r2.getFromStation()) &&
				Objects.equals(r1.getToStation(), r2.getToStation()) &&
				r1.getPrice() == r2.getPrice();
	}
}
