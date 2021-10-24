package sandbox.htmxpoc.web.cars;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import sandbox.htmxpoc.repository.ManufacturerRepository;
import sandbox.htmxpoc.repository.ModelRepository;
import sandbox.htmxpoc.repository.VehicleRepository;
import sandbox.htmxpoc.repository.model.Manufacturer;
import sandbox.htmxpoc.repository.model.Model;
import sandbox.htmxpoc.repository.model.Vehicle;
import sandbox.htmxpoc.web.Option;
import sandbox.htmxpoc.web.StateUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("/cars-tab/browse-cars")
public class BrowseCarsController {

    @Autowired
    private ManufacturerRepository manufacturerRepository;
    @Autowired
    private ModelRepository modelRepository;
    @Autowired
    private VehicleRepository vehicleRepository;

    @GetMapping("/manufacturers")
    public ModelAndView filter1(@RequestParam(name = "manufacturers", required = false) Integer selected) {
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
    public ModelAndView filter2(@RequestParam(name = "manufacturers", required = false) Integer manufacturerId,
                                @RequestParam(name = "models", required = false) Integer selected) {
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

    @GetMapping("/data")
    public ModelAndView data(HttpServletResponse response,
                             @RequestParam(name = "manufacturers", required = false) Integer manufacturerId,
                             @RequestParam(name = "models", required = false) Integer modelId) {
        response.addHeader("HX-Push", StateUtils.toQuery(Map.of(
                        "state_0", "cars-tab",
                        "state_1", "browse-cars",
                        "manufacturers", Objects.toString(manufacturerId, ""),
                        "models", Objects.toString(modelId, "")
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
}
