package sandbox.htmxpoc.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class IndexController {

    @GetMapping({"/", "/index"})
    public ModelAndView index(@RequestParam Map<String, String> params) {
        return new ModelAndView("index", Map.of(
                "states", StateUtils.states(params),
                "originalParams", StateUtils.toQuery(params))
        );
    }

}