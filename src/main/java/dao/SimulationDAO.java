package dao;

import java.util.ArrayList;

import model.Simulation;

public enum SimulationDAO {
	instance;
	
	public ArrayList<Simulation> getSimulations() {
		return new ArrayList<Simulation>();
	}

}
