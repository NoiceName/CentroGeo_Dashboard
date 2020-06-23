package extraction;

import dao.NetDAO;
import dao.RoutesDAO;
import dao.SimulationDAO;
import dao.SnapshotDAO;
import model.Database;
import org.apache.commons.io.IOUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.inject.Singleton;
import javax.xml.parsers.*;
import java.io.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.zip.ZipEntry;

import static org.apache.commons.io.IOUtils.toInputStream;

public enum ZipExtraction {
	instance;

	// Keeps track of which simulation is currently being added
	public int simulationID;

	public void getZipData(InputStream stream) throws Exception {
		// Stops the stream from being closed by other methods
		WontCloseZipInputStream zipStream = new WontCloseZipInputStream(stream);

		Database db = new Database();
		Database.loadPGSQL();
		db.connectPGSQL();

		Connection connection = db.getConnection();

		boolean containsMetadata = false;

		simulationID = SimulationDAO.instance.addEmptyMetadata();

		try {
			for (ZipEntry e; (e = zipStream.getNextEntry()) != null; ) {
				if (e.getName().contains("state_")) {
					parseState(zipStream, connection);
					containsMetadata = true;
				} else if (e.getName().contains("metadata.txt")) {
					parseMetadata(zipStream, connection);
					containsMetadata = true;
				} else if (e.getName().contains("net.net.xml")) {
					parseNet(zipStream, connection);
				} else if (e.getName().contains("routes.rou.xml")) {
					parseRoutes(zipStream, connection);
				} else if (e.getName().contains("simulation.sumocfg")) {
					parseEmpty(zipStream, connection);
				} else if (e.getName().contains("state.zip")) {
					System.out.println("Opening state.zip");
					getZipData(zipStream);
					System.out.println("Closing state.zip");
				}
			}
			// Manually close stream, because the default was overwritten
			zipStream.reallyClose();
			if (!containsMetadata){throw new Exception("Invalid zip file");}
		} catch (IOException | ParserConfigurationException | SAXException e) {
			e.printStackTrace();
			throw new Exception("Invalid zip file");
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new Exception("SQL Database Error");
		}
	}

	public void parseNet(InputStream in, Connection connection) throws ParserConfigurationException, IOException, SAXException {
		DefaultHandler handler = new NetHandler(connection, simulationID);
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setValidating(true);
		SAXParser saxParser = factory.newSAXParser();
		saxParser.parse(in, handler);
	}

	public void parseRoutes(InputStream in, Connection connection) throws ParserConfigurationException, IOException, SAXException {
		DefaultHandler handler = new RoutesHandler(connection);
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setValidating(true);
		SAXParser saxParser = factory.newSAXParser();
		saxParser.parse(in, handler);
	}

	public void parseState(InputStream in, Connection connection) throws ParserConfigurationException, IOException, SAXException, SQLException {
		StringWriter writer = new StringWriter();
		IOUtils.copy(in, writer, "UTF-8");
		SQLXML xmlVal = connection.createSQLXML();
		xmlVal.setString(writer.toString());

		DefaultHandler handler = new StateHandler(connection, simulationID, xmlVal);

		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setValidating(true);
		SAXParser saxParser = factory.newSAXParser();
		saxParser.parse(toInputStream(writer.toString(), "UTF-8"), handler);
	}

	// Metadata is a text file
	public void parseMetadata(InputStream in, Connection connection) {
		try {
			// Turns InputStream into a readable string
			StringWriter writer = new StringWriter();
			IOUtils.copy(in, writer, "UTF-8");

			// Splits the text file into lines
			String[] lines = writer.toString().split("\\r?\\n");

			String name = null, date = null, tags = null, description = null;

			// Associates the variables with their values
			for (String line : lines){
				if (line.contains("Name: ")){
					name = line.split("Name: ")[1];
				}

				if (line.contains("Date: ")){
					date = line.split("Date: ")[1];
				}

				if (line.contains("Tags: ")){
					tags = line.split("Tags: ")[1];
				}

				if (line.contains("Description: ")){
					description = line.split("Description: ")[1];
				}
			}

			SimulationDAO.instance.addMetadata(simulationID, name, date, tags, description);

		} catch (IOException | SQLException | ParseException e) {
			e.printStackTrace();
		}
	}

	private class NetHandler extends DefaultHandler{
		Connection connection;
		int simulationID;

		String edge_id;

		public NetHandler(Connection connection, int simulationID){
			this.connection = connection;
			this.simulationID = simulationID;
		}

		public void startElement(String uri, String localName,
								 String qName, Attributes attributes) throws SAXException {
			if (qName.equals("edge")) {
				edge_id = attributes.getValue("id");
			}

			if (qName.equals("lane")) {
				String id = attributes.getValue("id");
				int index = Integer.parseInt(attributes.getValue("index"));
				double speed = Double.parseDouble(attributes.getValue("speed"));
				double length = Double.parseDouble(attributes.getValue("length"));
				String shape = attributes.getValue("shape");

				try {
					NetDAO.instance.addNet(id, edge_id, simulationID, index, length, shape);
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}

		}

	}

	private class RoutesHandler extends DefaultHandler{
		Connection connection;

		public RoutesHandler(Connection connection){
			this.connection = connection;
		}

		public void startElement(String uri, String localName,
								 String qName, Attributes attributes) throws SAXException {
			if (qName.equals("vehicle")) {
				String id = attributes.getValue("id");
				float depart = Float.parseFloat(attributes.getValue("depart"));

				try {
					RoutesDAO.instance.addRoute(simulationID, id, depart);
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}

		}

	}

	private class StateHandler extends DefaultHandler{
		Connection connection;
		private double time;
		private int simulationID;
		private SQLXML xmlVal;

		public StateHandler(Connection connection, int simulationID, SQLXML xmlVal){
			this.connection = connection;
			this.simulationID = simulationID;
			this.xmlVal = xmlVal;
		}

		public void startElement(String uri, String localName,
								 String qName, Attributes attributes) throws SAXException {
			if (qName.equals("snapshot")) {
				time = Double.parseDouble(attributes.getValue("time"));
			}
		}

		public void endDocument() throws SAXException {
			try {
				SnapshotDAO.instance.addSnapshot(simulationID, time, xmlVal);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void parseEmpty(InputStream in, Connection connection) {

	}

	class WontCloseZipInputStream extends java.util.zip.ZipInputStream {
		public WontCloseZipInputStream(InputStream in) {
			super(in);
		}

		public void close () {
			// Do nothing.
		}

		public void reallyClose() throws IOException {
			super.close();
		}
	}

}
