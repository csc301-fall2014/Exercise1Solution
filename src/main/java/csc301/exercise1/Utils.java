package csc301.exercise1;

public class Utils {

	
	public static String normalizeName(String name){
		if(name == null){
			throw new IllegalArgumentException("Names must not be null.");
		}
		name = name.trim();
		if(name.isEmpty()){
			throw new IllegalArgumentException("Names must contain at least one non-white-space character.");
		}
		return name;
	}
	
	
}
