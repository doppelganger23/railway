package by.epam.grodno.uladzimir_stsiatsko.my_dao.model;

import java.io.Serializable;

public class Train implements Serializable{

	private int id;
	private String trainNumber;
	private int passengersCapacity;
	
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
	public int getPassengersCapacity() {
		return passengersCapacity;
	}
	public void setPassengersCapacity(int passengersCapacity) {
		this.passengersCapacity = passengersCapacity;
	}	
	
}
