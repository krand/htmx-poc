package sandbox.htmxpoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sandbox.htmxpoc.model.Model;

import java.util.List;

public interface ModelRepository extends JpaRepository<Model, Integer> {
    List<Model> findByManufacturerId(Integer manufacturerId);
}
