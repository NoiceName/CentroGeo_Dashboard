package extraction;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.ReaderInputStream;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.*;
import java.io.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.zip.ZipEntry;

import static org.apache.commons.io.IOUtils.toInputStream;


public class ZipExtraction {
	private static int simulationID;

	public static void getZipData(InputStream stream, Connection connection) throws EOFException {
		WontCloseZipInputStream zipStream = new WontCloseZipInputStream(stream);

		try {
			for (ZipEntry e; (e = zipStream.getNextEntry()) != null; ) {
				// System.out.println(e.getName());
				if (e.getName().contains("metadata.txt")) {
					parseMetadata(zipStream, connection);
				}
				if (e.getName().contains("net.net.xml")) {
					parseNet(zipStream, connection);
				}
				if (e.getName().contains("routes.rou.xml")) {
					parseRoutes(zipStream, connection);
				}
				if (e.getName().contains("simulation.sumocfg")) {
					parseEmpty(zipStream, connection);
				}
				if (e.getName().contains("state.zip")) {
					System.out.println("Opening state.zip");
					getZipData(zipStream, connection);
					System.out.println("Closing state.zip");
				}
				if (e.getName().contains("state_")) {
					parseState(zipStream, connection);
				}
			}
			zipStream.reallyClose();
		} catch (IOException | ParserConfigurationException | SAXException | SQLException e) {
			e.printStackTrace();
		}
	}

	// /**
	//  * Extracts file from zip file to target directory
	//  *
	//  * @param in The project relative path to the .zip file
	//  * @param connection The SQL connection
	//  * @throws IOException If the file does not exist
	//  */
	// public static void extract(InputStream in, Connection connection) throws IOException, ParserConfigurationException, SAXException {
	// 	// parseMetadata(getZipData(in, "metadata.txt"), connection);
	// 	parseNet(getZipData(in, "net.net.xml"), connection);
	// 	parseEmpty(getZipData(in, "routes.rou.xml"), connection);
	// 	parseEmpty(getZipData(in, "simulation.sumocfg"), connection);
	// 	parseEmpty(getZipData(in, "state.zip"), connection);
	// }

	public static void parseNet(InputStream in, Connection connection) throws ParserConfigurationException, IOException, SAXException {
		DefaultHandler handler = new NetHandler(connection, simulationID);
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setValidating(true);
		SAXParser saxParser = factory.newSAXParser();
		saxParser.parse(in, handler);
	}

	public static void parseRoutes(InputStream in, Connection connection) throws ParserConfigurationException, IOException, SAXException {
		DefaultHandler handler = new RoutesHandler(connection);
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setValidating(true);
		SAXParser saxParser = factory.newSAXParser();
		saxParser.parse(in, handler);
	}

	public static void parseState(InputStream in, Connection connection) throws ParserConfigurationException, IOException, SAXException, SQLException {
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

	public static void parseMetadata(InputStream in, Connection connection) {
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

			String query = "INSERT INTO projectschema.simulation (name, date, tags, description) " +
					"VALUES (?, ?, ?, ?) RETURNING simulation_id";

			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, name);

			DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
			java.util.Date fixedDate = format.parse(date);
			statement.setDate(2, new java.sql.Date(fixedDate.getTime()));

			statement.setString(3, tags);
			statement.setString(4, description);

			statement.execute();

			ResultSet returning = statement.getResultSet();
			returning.next();
			simulationID = returning.getInt(1);

		} catch (IOException | SQLException | ParseException e) {
			e.printStackTrace();
		}
	}

	private static class NetHandler extends DefaultHandler{
		Connection connection;
		int simulationID;

		public NetHandler(Connection connection, int simulationID){
			this.connection = connection;
			this.simulationID = simulationID;
		}

		public void startElement(String uri, String localName,
								 String qName, Attributes attributes) throws SAXException {
			if (qName.equals("lane")) {
				String id = attributes.getValue("id");
				int index = Integer.parseInt(attributes.getValue("index"));
				double speed = Double.parseDouble(attributes.getValue("speed"));
				double length = Double.parseDouble(attributes.getValue("length"));
				String shape = attributes.getValue("shape");

				String query = "INSERT INTO projectschema.lane (lane_id, simulation, index, length, shape) " +
						"VALUES (?, ?, ?, ?, ?)";

				try {
					PreparedStatement statement = connection.prepareStatement(query);
					statement.setString(1, id);
					statement.setInt(2, simulationID);
					statement.setInt(3, index);
					statement.setDouble(4, length);
					statement.setString(5, shape);

					statement.executeUpdate();
				} catch (SQLException e){
					e.getStackTrace();
				}

			}

		}

	}

	private static class RoutesHandler extends DefaultHandler{
		Connection connection;

		public RoutesHandler(Connection connection){
			this.connection = connection;
		}

		public void startElement(String uri, String localName,
								 String qName, Attributes attributes) throws SAXException {
			if (qName.equals("vehicle")) {
				String id = attributes.getValue("id");
				float depart = Float.parseFloat(attributes.getValue("depart"));

				String query = "INSERT INTO projectschema.vehicle (vehicle_id, depart) " +
						"VALUES (?, ?)";

				try {
					PreparedStatement statement = connection.prepareStatement(query);
					statement.setString(1, id);
					statement.setFloat(2, depart);

					statement.executeUpdate();
				} catch (SQLException e){
					e.getStackTrace();
				}

			}

		}

	}

	private static class StateHandler extends DefaultHandler{
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
			String query = "INSERT INTO projectschema.snapshot (simulation, time, data) " +
					"VALUES (?, ?, ?)";

			PreparedStatement statement = null;
			try {
				statement = connection.prepareStatement(query);
				statement.setInt(1, simulationID);
				statement.setDouble(2, time);
				statement.setSQLXML(3, xmlVal);

				statement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void parseEmpty(InputStream in, Connection connection) {

	}

	static class WontCloseZipInputStream extends java.util.zip.ZipInputStream {
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
