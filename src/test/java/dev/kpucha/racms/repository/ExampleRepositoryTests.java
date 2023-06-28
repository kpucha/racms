package dev.kpucha.racms.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import dev.kpucha.racms.model.entity.ExampleEntity;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Example Repository tests")
@TestMethodOrder(MethodOrderer.DisplayName.class)
class ExampleRepositoryTests extends GenericRepositoryTests<ExampleEntity, Long> {

	@Autowired
	private ExampleRepository exampleRepository;

	@Override
	protected GenericRepository<ExampleEntity, Long> createRepository() {
		return exampleRepository;
	}

	@Override
	protected Class<ExampleEntity> testClass() {
		return ExampleEntity.class;
	}

}
