package by.epam.grodno.uladzimir_stsiatsko.my_dao.model;

import java.io.Serializable;

public class StationToStationBlockInfo implements Serializable {
	
	private int id;
	private String departureStationName;
	private String destinationStationName;
	private double distanceInKilometres;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDepartureStationName() {
		return departureStationName;
	}
	public void setDepartureStationName(String departureStationName) {
		this.departureStationName = departureStationName;
	}
	public String getDestinationStationName() {
		return destinationStationName;
	}
	public void setDestinationStationName(String destinationStationName) {
		this.destinationStationName = destinationStationName;
	}
	public double getDistanceInKilometres() {
		return distanceInKilometres;
	}
	public void setDistanceInKilometres(double distanceInKilometres) {
		this.distanceInKilometres = distanceInKilometres;
	}

}
