package by.epam.grodno.uladzimir_stsiatsko.my_dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class SearchResult implements Serializable {
	
	private int tripId;
	private String routeName;
	private String routeType;
	private String train;
	private String fromStation;
	private int fromBlock;
	private Timestamp departureDate;
	private String toStation;
	private int toBlock;
	private Timestamp arrivalDate;
	private int places;
	private int sold;
	private double kmPrice;
	private double km;
	
	public int getTripId() {
		return tripId;
	}
	public void setTripId(int tripId) {
		this.tripId = tripId;
	}
	public String getRouteName() {
		return routeName;
	}
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	public String getRouteType() {
		return routeType;
	}
	public void setRouteType(String routeType) {
		this.routeType = routeType;
	}
	public String getTrain() {
		return train;
	}
	public void setTrain(String train) {
		this.train = train;
	}
	public String getFromStation() {
		return fromStation;
	}
	public void setFromStation(String fromStation) {
		this.fromStation = fromStation;
	}
	public int getFromBlock() {
		return fromBlock;
	}
	public void setFromBlock(int fromBlock) {
		this.fromBlock = fromBlock;
	}
	public Timestamp getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(Timestamp departureDate) {
		this.departureDate = departureDate;
	}	
	public String getToStation() {
		return toStation;
	}
	public void setToStation(String toStation) {
		this.toStation = toStation;
	}
	public int getToBlock() {
		return toBlock;
	}
	public void setToBlock(int toBlock) {
		this.toBlock = toBlock;
	}
	public Timestamp getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(Timestamp arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public int getPlaces() {
		return places;
	}
	public void setPlaces(int places) {
		this.places = places;
	}
	public int getSold() {
		return sold;
	}
	public void setSold(int sold) {
		this.sold = sold;
	}
	public double getKmPrice() {
		return kmPrice;
	}
	public void setKmPrice(double kmPrice) {
		this.kmPrice = kmPrice;
	}
	public double getKm() {
		return km;
	}
	public void setKm(double km) {
		this.km = km;
	}

}
