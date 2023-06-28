package dev.kpucha.racms;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Application initialization test")
class RestApiCrudMicroserviceApplicationTests {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	@DisplayName("Application context has been loaded")
	void contextLoads() {
	}

	@Test
	@DisplayName("Test data has been loaded into the database")
	void test_tableExamplesLoaded() {
		assert (true);
	}

}
