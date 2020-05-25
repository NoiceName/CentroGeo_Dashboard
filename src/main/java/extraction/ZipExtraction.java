package extraction;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.*;
import java.io.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


public class ZipExtraction {
	/**
	 * Extracts file from zip file to target directory
	 *
	 * @param file The project relative path to the .zip file
	 * @param connection The SQL connection
	 * @throws IOException If the file does not exist
	 */
	public static void extract(String file, Connection connection) throws IOException {
		ZipFile zipFile = new ZipFile(file);

		// Generator holding the files
		Enumeration<? extends ZipEntry> entries = zipFile.entries();

		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();

			// Writes the file to disk
			InputStream in = zipFile.getInputStream(entry);

			if (entry.getName().contains("metadata.txt")){
				parseMetadata(in, connection);
			}

			if (entry.getName().contains("net.net.xml")){
				try {
					parseNet(in, connection);
				} catch (ParserConfigurationException | SAXException e) {
					e.printStackTrace();
				}
			}

			if (entry.getName().contains("routes.rou.xml")){

			}

			if (entry.getName().contains("simulation.sumocfg")){

			}

			if (entry.getName().contains("state.zip")){
				
			}
		}
	}

	public static void parseNet(InputStream in, Connection connection) throws ParserConfigurationException, IOException, SAXException {
		DefaultHandler handler = new NetHandler(connection);
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setValidating(true);
		SAXParser saxParser = factory.newSAXParser();
		saxParser.parse(in, handler);

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
					"VALUES (?, ?, ?, ?)";

			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, name);

			DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
			java.util.Date fixedDate = format.parse(date);
			statement.setDate(2, new java.sql.Date(fixedDate.getTime()));

			statement.setString(3, tags);
			statement.setString(4, description);

			statement.executeQuery();

		} catch (IOException | SQLException | ParseException e) {
			e.printStackTrace();
		}
	}

	private static class NetHandler extends DefaultHandler{
		Connection connection;

		public NetHandler(Connection connection){
			this.connection = connection;
		}

		public void startElement(String uri, String localName,
								 String qName, Attributes attributes) throws SAXException {
			if (qName.equals("lane")) {
				System.out.println(attributes.getValue(0));
				String id = attributes.getValue("id");
				int index = Integer.parseInt(attributes.getValue("index"));
				double speed = Double.parseDouble(attributes.getValue("speed"));
				double length = Double.parseDouble(attributes.getValue("length"));
				String shape = attributes.getValue("shape");

				String query = "INSERT INTO projectschema.lane (lane_id, index, length, shape) " +
						"VALUES (?, ?, ?, ?)";

				try {
					PreparedStatement statement = connection.prepareStatement(query);
					statement.setString(1, id);
					statement.setInt(2, index);
					statement.setDouble(3, length);
					statement.setString(4, shape);

					statement.executeQuery();
				} catch (SQLException e){
					e.getStackTrace();
				}

			}

		}

	}

}
