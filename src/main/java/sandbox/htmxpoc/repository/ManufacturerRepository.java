package sandbox.htmxpoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sandbox.htmxpoc.repository.model.Manufacturer;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer> {
}
