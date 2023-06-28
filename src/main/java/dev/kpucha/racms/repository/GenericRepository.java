package dev.kpucha.racms.repository;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Interface for generic List CRUD operations on a repository for a specific
 * type. This an extension to {@link ListCrudRepository} adding a default option
 * to get an entity ID.
 *
 * @author Arturo Alcala - kpucha.dev
 */
@NoRepositoryBean
public interface GenericRepository<T, ID extends Serializable> extends ListCrudRepository<T, ID> {

	/**
	 * Obtiene el ID de la entidad proporcionada.
	 *
	 * @param entity the entity from which to obtain the ID
	 * @return the ID of the entity
	 * @throws RuntimeException if the ID cannot be obtained from the entity
	 */
	@SuppressWarnings("unchecked")
	default ID getEntityID(T entity) throws RuntimeException {
		try {
			Field idField = entity.getClass().getDeclaredField("id");
			idField.setAccessible(true);
			return (ID) idField.get(entity);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new RuntimeException("Error al obtener el ID de la entidad", e);
		}
	}

}
