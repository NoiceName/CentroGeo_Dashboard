package extraction;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ZipExtractionTest {
	private Connection connection;

	@BeforeEach
	public void setUp(){
		try {
			Class.forName("org.postgresql.Driver");
		}
		catch (ClassNotFoundException cnfe) {
			System.err.println("Error loading driver: " + cnfe);
		}

		String url = "jdbc:postgresql://localhost:5433/centrogeo";
		String username = "postgres";
		String password = "1YIrISqSsLxYFI8Itig6";

		try {
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testInvalidFile() {
		try {
			ZipExtraction.extract("aFileThatDoesntExist.zip", connection);
			fail("Method should throw and IOException");
		} catch (IOException ignored) {

		}
	}

	@Test
	public void testValidFile(){
		try {
			ZipExtraction.extract("src/main/java/extraction/sumofiles.zip", connection);

			assertTrue(Files.exists(Paths.get("src/test/java/extraction/dump/")));
		} catch (IOException e) {
			fail("Files have not been extracted correctly");
		}
	}

}