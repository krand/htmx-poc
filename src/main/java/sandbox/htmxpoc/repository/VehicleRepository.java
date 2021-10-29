package sandbox.htmxpoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sandbox.htmxpoc.model.Vehicle;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    @Query("SELECT v FROM Vehicle v WHERE (:manufacturerId is null OR v.manufacturer.id = :manufacturerId) AND (:modelId is null OR v.model.id = :modelId)")
    List<Vehicle> findAll(@Param("manufacturerId") Integer manufacturerId, @Param("modelId") Integer modelId);

}
