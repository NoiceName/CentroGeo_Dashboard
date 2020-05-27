package dao;

import java.util.ArrayList;

import model.Metadata;

public enum MetadataDAO {
	instance;
	
	public ArrayList<Metadata> getSimulations() {
		return new ArrayList<Metadata>();
	}

}
