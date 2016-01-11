package by.epam.grodno.uladzimir_stsiatsko.my_dao.model;

import java.io.Serializable;

public class Station implements Serializable {
	
	private int id;
	private String name;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
