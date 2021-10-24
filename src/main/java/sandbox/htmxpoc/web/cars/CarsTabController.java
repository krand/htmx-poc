package sandbox.htmxpoc.web.cars;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import sandbox.htmxpoc.web.StateUtils;

import java.util.Map;

@Controller
@RequestMapping("/cars-tab")
public class CarsTabController {
    @GetMapping
    public ModelAndView content(@RequestParam Map<String, String> params) {
        return new ModelAndView("cars/cars-tab", Map.of(
                "states", StateUtils.states(params),
                "originalParams", StateUtils.toQuery(params))
        );
    }

    @GetMapping("browse-cars")
    public ModelAndView browseCars(@RequestParam Map<String, String> params) {
        return new ModelAndView("cars/browse-cars", Map.of(
                "states", StateUtils.states(params),
                "originalParams", StateUtils.toQuery(params))
        );
    }

    @GetMapping("tab1-op2")
    public String createCar() {
        return "cars/create-car";
    }

}
