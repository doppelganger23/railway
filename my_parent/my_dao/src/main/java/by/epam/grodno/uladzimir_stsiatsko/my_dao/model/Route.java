package by.epam.grodno.uladzimir_stsiatsko.my_dao.model;

import java.io.Serializable;

public class Route implements Serializable{
	
	private int id;
	private String routeName;
	private String routeType;
	private double priceForKilometer;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public double getPriceForKilometer() {
		return priceForKilometer;
	}
	public void setPriceForKilometer(double priceForKilometer) {
		this.priceForKilometer = priceForKilometer;
	}
	

}
