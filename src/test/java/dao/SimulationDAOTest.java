package dao;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;

class SimulationDAOTest {

	@Test
	void testEditMetadata() throws ParseException {
		int simulationId = 1;
		String name = "editor";
				
		String dateStr = "2020-06-09";
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		java.util.Date date = sdf.parse(dateStr);
		
		String tag = "tag";
		String description = "description";
		
		int result = SimulationDAO.instance.editMetadata(simulationId, name, date, tag, description);
	
		assertEquals(1, result);
	}

}
