package co.edu.cue.GestorRecetas.repositories;

import co.edu.cue.GestorRecetas.domain.entities.Menu;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends CrudRepository<Menu, Integer> {
}
