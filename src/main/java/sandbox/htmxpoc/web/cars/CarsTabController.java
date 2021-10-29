package sandbox.htmxpoc.web.cars;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sandbox.htmxpoc.model.Manufacturer;
import sandbox.htmxpoc.model.Model;
import sandbox.htmxpoc.model.Vehicle;
import sandbox.htmxpoc.repository.ManufacturerRepository;
import sandbox.htmxpoc.repository.ModelRepository;
import sandbox.htmxpoc.repository.VehicleRepository;
import sandbox.htmxpoc.web.Option;
import sandbox.htmxpoc.web.StateUtils;
import sandbox.htmxpoc.web.cars.dto.CarDTO;
import sandbox.htmxpoc.web.cars.dto.EditCarDTO;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("/cars-tab")
public class CarsTabController {

    @Autowired
    private ManufacturerRepository manufacturerRepository;
    @Autowired
    private ModelRepository modelRepository;
    @Autowired
    private VehicleRepository vehicleRepository;

    @GetMapping
    public ModelAndView tabContent(@RequestParam Map<String, String> params) {
        return new ModelAndView("cars/cars-tab", Map.of(
                "states", StateUtils.states(params),
                "originalParams", StateUtils.toQuery(params))
        );
    }

    @GetMapping("/browse-cars")
    public ModelAndView browseCars(@RequestParam Map<String, String> params) {
        return new ModelAndView("cars/browse-cars", Map.of(
                "states", StateUtils.states(params),
                "originalParams", StateUtils.toQuery(params))
        );
    }

    @GetMapping("/browse-cars/data")
    public ModelAndView getCars(HttpServletResponse response,
                                @RequestParam(name = "manufacturerId", required = false) Integer manufacturerId,
                                @RequestParam(name = "modelId", required = false) Integer modelId) {
        response.addHeader("HX-Push", StateUtils.toQuery(Map.of(
                        "state_0", "cars-tab",
                        "state_1", "browse-cars",
                        "manufacturerId", Objects.toString(manufacturerId, ""),
                        "modelId", Objects.toString(modelId, "")
                )
        ));
        List<Object[]> rows = vehicleRepository.findAll(manufacturerId, modelId).stream().map(v -> new Object[]{
                v.getId(),
                Optional.of(v).map(Vehicle::getManufacturer).map(Manufacturer::getName).orElse(""),
                Optional.of(v).map(Vehicle::getModel).map(Model::getName).orElse(""),
                v.getMileage(),
                v.getPrice()
        }).toList();
        return new ModelAndView("common/rows", Map.of(
                "rows", rows
        ));
    }

    @GetMapping("/new")
    public ModelAndView addCar() {
        return new ModelAndView("cars/edit-car", "car", new EditCarDTO());
    }

    @GetMapping("/{id}")
    public ModelAndView viewCar(HttpServletResponse response, @PathVariable("id") Integer id) {
        Vehicle car = vehicleRepository.getById(id);
        CarDTO carDto = new CarDTO(car.getId(),
                car.getManufacturer().getName(),
                car.getModel().getName(),
                car.getMileage(),
                car.getPrice()
        );
        response.addHeader("HX-Push", StateUtils.toQuery(Map.of(
                        "state_0", "cars-tab",
                        "state_1", car.getId().toString()
                )
        ));
        return new ModelAndView("cars/view-car", "car", carDto);
    }

    @PostMapping(consumes = {"application/x-www-form-urlencoded"})
    public ModelAndView saveCar(HttpServletResponse response, EditCarDTO newCar) {

        Vehicle car = new Vehicle();
        car.setManufacturer(manufacturerRepository.getById(newCar.getManufacturerId()));
        car.setModel(modelRepository.getById(newCar.getModelId()));
        car.setMileage(newCar.getMileage());
        car.setPrice(newCar.getPrice());
        car = vehicleRepository.save(car);

        response.addHeader("HX-Push", StateUtils.toQuery(Map.of(
                        "state_0", "cars-tab",
                        "state_1", car.getId().toString()
                )
        ));
        return viewCar(response, car.getId());
    }

    @GetMapping("/manufacturers")
    public ModelAndView getManufacturers(@RequestParam(name = "manufacturerId", required = false) Integer selected) {
        List<Option> options = manufacturerRepository.findAll().stream()
                .map(m -> new Option(m.getId(), m.getName()))
                .toList();
        return new ModelAndView("common/options", Map.of(
                "options", options,
                "emptyOption", true,
                "selected", selected != null ? selected : "-"
        ));
    }

    @GetMapping("/models")
    public ModelAndView getModels(@RequestParam(name = "manufacturerId", required = false) Integer manufacturerId,
                                  @RequestParam(name = "modelId", required = false) Integer selected) {
        List<Option> options;
        if (manufacturerId == null) {
            options = Collections.emptyList();
        } else {
            options = modelRepository.findByManufacturerId(manufacturerId).stream()
                    .map(m -> new Option(m.getId(), m.getName()))
                    .toList();
        }
        return new ModelAndView("common/options", Map.of(
                "options", options,
                "emptyOption", true,
                "selected", selected != null ? selected : "-"
        ));
    }
}
