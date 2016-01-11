package by.epam.grodno.uladzimir_stsiatsko.my_dao.model;

import java.io.Serializable;

public class StationToStationBlock implements Serializable {
	
	private int id;
	private int departureStationId;
	private int destinationStationId;
	private double distanceInKilometres;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDepartureStationId() {
		return departureStationId;
	}
	public void setDepartureStationId(int departureStationId) {
		this.departureStationId = departureStationId;
	}
	public int getDestinationStationId() {
		return destinationStationId;
	}
	public void setDestinationStationId(int destinationStationId) {
		this.destinationStationId = destinationStationId;
	}	
	public double getDistanceInKilometres() {
		return distanceInKilometres;
	}
	public void setDistanceInKilometres(double distanceInKilometres) {
		this.distanceInKilometres = distanceInKilometres;
	}
	
}
