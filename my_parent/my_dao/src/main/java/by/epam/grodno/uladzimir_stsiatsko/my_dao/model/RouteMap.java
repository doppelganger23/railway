package by.epam.grodno.uladzimir_stsiatsko.my_dao.model;

import java.io.Serializable;
import java.sql.Time;

public class RouteMap implements Serializable {
	
	private int id;
	private int routeId;
	private int stationToStationBlockId;
	private int blockNumberInRoute;
	private Time blockLeaveTime;
	private Time blockEnterTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRouteId() {
		return routeId;
	}
	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}
	public int getBlockNumberInRoute() {
		return blockNumberInRoute;
	}
	public void setBlockNumberInRoute(int blockNumberInRoute) {
		this.blockNumberInRoute = blockNumberInRoute;
	}
	public int getStationToStationBlockId() {
		return stationToStationBlockId;
	}
	public void setStationToStationBlockId(int stationToStationBlockId) {
		this.stationToStationBlockId = stationToStationBlockId;
	}
	public Time getBlockLeaveTime() {
		return blockLeaveTime;
	}
	public void setBlockLeaveTime(Time blockLeaveTime) {
		this.blockLeaveTime = blockLeaveTime;
	}
	public Time getBlockEnterTime() {
		return blockEnterTime;
	}
	public void setBlockEnterTime(Time blockEnterTime) {
		this.blockEnterTime = blockEnterTime;
	}
	
}
