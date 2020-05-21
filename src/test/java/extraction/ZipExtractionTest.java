package extraction;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class ZipExtractionTest {

	@Test
	public void testInvalidFile() {
		try {
			ZipExtraction.Extract("aFileThatDoesntExist.zip", "dump");
			fail("Method should throw and IOException");
		} catch (IOException ignored) {

		}
	}

	@Test
	public void testValidFile(){
		try {
			ZipExtraction.Extract("src/test/java/extraction/sumofiles.zip",
					"src/test/java/extraction/dump");

			assertTrue(Files.exists(Paths.get("src/test/java/extraction/dump")));
		} catch (IOException e) {
			fail("Files have not been extracted correctly");
		}
	}

}