package sandbox.htmxpoc.web.tab1;

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
@RequestMapping("/tab1/tab1-op1")
public class Op1Controller {

    @Autowired
    private ManufacturerRepository manufacturerRepository;
    @Autowired
    private ModelRepository modelRepository;
    @Autowired
    private VehicleRepository vehicleRepository;

    @GetMapping("/f1")
    public ModelAndView filter1(@RequestParam(name = "f1", required = false) Integer selected) {
        List<Option> options = manufacturerRepository.findAll().stream()
                .map(m -> new Option(m.getId(), m.getName()))
                .toList();
        return new ModelAndView("common/options", Map.of(
                "options", options,
                "emptyOption", true,
                "selected", selected != null ? selected : "-"
        ));
    }

    @GetMapping("/f2")
    public ModelAndView filter2(@RequestParam(name = "f1", required = false) Integer filter1,
                                @RequestParam(name = "f2", required = false) Integer selected) {
        List<Option> options;
        if (filter1 == null) {
            options = Collections.emptyList();
        } else {
            options = modelRepository.findByManufacturerId(filter1).stream()
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
                             @RequestParam(name = "f1", required = false) Integer filter1,
                             @RequestParam(name = "f2", required = false) Integer filter2) {
        response.addHeader("HX-Push", StateUtils.toQuery(Map.of(
                        "state_0", "tab1",
                        "state_1", "tab1-op1",
                        "f1", Objects.toString(filter1, ""),
                        "f2", Objects.toString(filter2, "")
                )
        ));
        List<Object[]> rows = vehicleRepository.findAll(filter1, filter2).stream().map(v -> new Object[]{
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
