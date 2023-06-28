package dev.kpucha.racms.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * @author Arturo Alcala - kpucha.dev
 *
 * @param <T>
 * @param <ID>
 */
@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Example Repository tests")
@SuppressWarnings("unchecked")
@TestMethodOrder(MethodOrderer.DisplayName.class)
abstract class GenericRepositoryTests<T, ID extends Serializable> {

	protected abstract GenericRepository<T, ID> createRepository();

	protected abstract Class<T> testClass();

	@Autowired
	GenericRepository<T, ID> repository;

	private PodamFactory factory = new PodamFactoryImpl();

	@Test
	@DisplayName("save(T) Saves a given entity")
	void test_save() {
		saveEntity();
	}

	@Test
	@DisplayName("saveAll(Iterable<T>) Saves all given entities.")
	void test_saveAll() {
		saveList();
	}

	@Test
	@DisplayName("findById(ID) Retrieves an entity by its id")
	void test_findById() {
		T entity = saveEntity();
		Optional<T> foundById = repository.findById(repository.getEntityID(entity));
		assertEquals(entity, foundById.get());
	}

	@Test
	@DisplayName("existsById(ID) Returns whether an entity with the given id exists")
	void test_existsById() {
		T entity = saveEntity();
		Boolean exists = repository.existsById(repository.getEntityID(entity));
		assertTrue(exists);
		repository.deleteAll();
		Boolean existsBeforDeleteAll = repository.existsById(repository.getEntityID(entity));
		assertFalse(existsBeforDeleteAll);
	}

	@Test
	@DisplayName("findAll() Returns all instances of the type")
	void test_findAll() {
		repository.deleteAll();
		assertEquals(0, repository.count());
		List<T> list = saveList();
		List<T> found = repository.findAll();
		assertEquals(list, found);
	}

	@Test
	@DisplayName("findAllById(Iterable<ID>) Returns all instances of the type with the given IDs")
	void test_findAllById() {
		List<T> list = saveList();
		List<ID> idList = list.stream().map(entity -> repository.getEntityID(entity)).collect(Collectors.toList());
		List<T> foundById = repository.findAllById(idList);
		assertEquals(list, foundById);
	}

	@Test
	@DisplayName("count() Returns the number of entities available")
	void test_count() {
		Long initCount = repository.count();
		T entity = saveEntity();
		Long afterSaveCount = repository.count();
		assertEquals(afterSaveCount, initCount + 1);
		repository.delete(entity);
		Long afterDeleteCount = repository.count();
		assertEquals(afterDeleteCount, afterSaveCount - 1);
	}

	@Test
	@DisplayName("deleteById(ID) Deletes the entity with the given id")
	void test_deleteById() {
		T entity = saveEntity();
		repository.deleteById(repository.getEntityID(entity));
		assertNoneMatch(entity);
	}

	@Test
	@DisplayName("delete(T) Deletes a given entity")
	void test_delete() {
		T entity = saveEntity();
		repository.delete(entity);
		assertNoneMatch(entity);
	}

	@Test
	@DisplayName("deleteAllById(Iterable<ID>) Deletes all instances of the type with the given IDs.")
	void test_deleteAllById() {
		List<T> list = saveList();
		List<ID> idList = list.stream().map(entity -> repository.getEntityID(entity)).collect(Collectors.toList());
		repository.deleteAllById(idList);
		assertNoneMatch(list);
	}

	@Test
	@DisplayName("deleteAll(Iterable<T>) Deletes the given entities")
	void test_deleteAll() {
		List<T> exampleList = saveList();
		repository.deleteAll(exampleList);
		assertNoneMatch(exampleList);
	}

	@Test
	@DisplayName("deleteAll() Deletes all entities managed by the repository")
	void test_deleteAll2() {
		saveList();
		repository.deleteAll();
		assertEquals(0, repository.count());
	}

	/**
	 * Generate a new entity. Asserts that the entity doesn't exist in the
	 * repository. Save the entity. Asserts that the entity exists in the
	 * repository.
	 * 
	 * @return T The saved entity
	 */
	private T saveEntity() {
		T entity = factory.manufacturePojo(testClass());
		assertNoneMatch(entity);
		T saved = (T) repository.save(entity);
		assertEquals(entity, saved);
		List<T> found = repository.findAll();
		assertTrue(found.contains(saved));
		return saved;
	}

	/**
	 * Generate a new entity list. Asserts that the entities don't exist in the
	 * repository. Save the list. Asserts that all entities exist in the repository.
	 * 
	 * @return List<T> The saved list
	 */
	private List<T> saveList() {
		List<T> list = factory.manufacturePojo(ArrayList.class, testClass());
		assertNoneMatch(list);
		List<T> savedList = repository.saveAll(list);
		assertEquals(list, savedList);
		List<T> found = repository.findAll();
		assertTrue(found.containsAll(savedList));
		return savedList;
	}

	/**
	 * Asserts that none of the records in the received list exist in the repository
	 * 
	 * @param list List<T>
	 */
	private void assertNoneMatch(List<T> list) {
		List<T> found = repository.findAll();
		Boolean noneMatch = list.stream().noneMatch(found::contains);
		assertTrue(noneMatch);
	}

	/**
	 * Asserts that the record received exists in the repository
	 * 
	 * @param entity T
	 */
	private void assertNoneMatch(T entity) {
		List<T> found = repository.findAll();
		Boolean noneMatch = found.stream().noneMatch(e -> e.equals(entity));
		assertTrue(noneMatch);
	}

}
