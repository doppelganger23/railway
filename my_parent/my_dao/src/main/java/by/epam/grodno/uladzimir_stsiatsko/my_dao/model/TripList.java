package by.epam.grodno.uladzimir_stsiatsko.my_dao.model;

import java.io.Serializable;
import java.util.Date;

public class TripList implements Serializable{

	private int id;
	private int trainId;
	private int routeId;
	private Date departureDate;
	private int ticketsSold;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTrainId() {
		return trainId;
	}
	public void setTrainId(int trainId) {
		this.trainId = trainId;
	}
	public int getRouteId() {
		return routeId;
	}
	public void setRouteId(int routeId) {
		this.routeId = routeId;
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
