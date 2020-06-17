package dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ChartDAOTest {

	@Test
	void test() {
		ChartDAO.instance.getEdgeAppereance(1, "e1");
	}

}
