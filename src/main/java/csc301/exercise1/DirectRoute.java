package csc301.exercise1;



public class DirectRoute {
	
	
	
	private TrainCompany trainCompany;
	private String fromStation;
	private String toStation; 
	private double price;
	
	
	
	public DirectRoute(TrainCompany trainCompany, String fromStation, String toStation, double price) {
		setTrainCompany(trainCompany);
		setFromStation(fromStation);
		setToStation(toStation);
		setPrice(price);
	}


	
	public TrainCompany getTrainCompany() {
		return trainCompany;
	}
	
	
	public void setTrainCompany(TrainCompany trainCompany) {
		if(trainCompany == null){
			throw new IllegalArgumentException("Train company must not be null.");
		}
		this.trainCompany = trainCompany;
	}
	
	
	public String getFromStation() {
		return fromStation;
	}
	
	
	public void setFromStation(String fromStation) {
		fromStation = Utils.normalizeName(fromStation);
		if(fromStation.equals(toStation)){
			throw new IllegalArgumentException("Cannot create a direct route from the a station to itself");
		}
		this.fromStation = fromStation;
	}


	public String getToStation() {
		return toStation;
	}
	
	
	public void setToStation(String toStation) {
		toStation = Utils.normalizeName(toStation);
		if(toStation.equals(fromStation)){
			throw new IllegalArgumentException("Cannot create a direct route from the a station to itself");
		}
		this.toStation = toStation;
	}


	public double getPrice() {
		return price;
	}
	
	
	public void setPrice(double price) {
		if(price < 0){
			throw new IllegalArgumentException("Price must be non-negative.");
		}
		
		// NOTE: 
		// * We are not checking if the price contains a fraction of a cent.
		// * Performing this check reliably is actually a harder problem than it sounds.
		// * It is related to the fact that doubles are stored in the computer as 64 bits, which
		//   means that they have limited precision. You will learn more about these issues
		//   in your numerical analysis course(s).
		// * In case you are curious, the proper way to deal with this issue would be to 
		//   define price as BigDecimal, instead of a double.
		// * This is a very technical subtlety, and we wouldn't expect you to know about it.
		
		this.price = price;
	}
	
	
	
	@Override
	public boolean equals(Object obj) {
		if(! (obj instanceof DirectRoute)){
			return false;
		}
		DirectRoute r = (DirectRoute) obj;
		return r.trainCompany.equals(trainCompany) && r.fromStation.equals(fromStation) &&
				r.toStation.equals(toStation) && r.price == price;
	}
	
	
	@Override
	public String toString() {
		return String.format("%s from %s to %s, %.2f$", getTrainCompany().getName(), 
				getFromStation(), getToStation(), getPrice());
	}
}
