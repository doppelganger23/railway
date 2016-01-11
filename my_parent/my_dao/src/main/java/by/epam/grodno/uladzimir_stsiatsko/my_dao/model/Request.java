package by.epam.grodno.uladzimir_stsiatsko.my_dao.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.stereotype.Repository;

@Repository
public class Request implements Serializable{

	private int id;
	private String departureStation;
	private String destinationStation;
	private Date departureDate;
	private Date arrivalDate;
	private String depCondition = ">=";
	private String arrCondition = "<=";
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDepartureStation() {
		return departureStation;
	}
	public void setDepartureStation(String departureStation) {
		this.departureStation = departureStation;
	}
	public String getDestinationStation() {
		return destinationStation;
	}
	public void setDestinationStation(String destinationStation) {
		this.destinationStation = destinationStation;
	}
	public Date getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}
	public Date getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public String getDepCondition() {
		return depCondition;
	}
	public void setDepCondition(String depCondition) {
		this.depCondition = depCondition;
	}
	public String getArrCondition() {
		return arrCondition;
	}
	public void setArrCondition(String arrCondition) {
		this.arrCondition = arrCondition;
	}
	
}
