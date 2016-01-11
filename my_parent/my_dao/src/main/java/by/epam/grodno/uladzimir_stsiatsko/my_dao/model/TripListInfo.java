package by.epam.grodno.uladzimir_stsiatsko.my_dao.model;

import java.io.Serializable;
import java.util.Date;

public class TripListInfo implements Serializable {
	
	private int id;
	private String trainNumber;
	private String routeType;
	private String routeName;
	private Date departureDate;
	private int ticketsSold;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTrainNumber() {
		return trainNumber;
	}
	public void setTrainNumber(String trainNumber) {
		this.trainNumber = trainNumber;
	}
	public String getRouteType() {
		return routeType;
	}
	public void setRouteType(String routeType) {
		this.routeType = routeType;
	}
	public String getRouteName() {
		return routeName;
	}
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	public Date getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}
	public int getTicketsSold() {
		return ticketsSold;
	}
	public void setTicketsSold(int ticketsSold) {
		this.ticketsSold = ticketsSold;
	}

}
