package sandbox.htmxpoc.web.tab1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import sandbox.htmxpoc.web.StateUtils;

import java.util.Map;

@Controller
@RequestMapping("/tab1")
public class Tab1Controller {
    @GetMapping
    public ModelAndView content(@RequestParam Map<String, String> params) {
        return new ModelAndView("tab1", Map.of(
                "states", StateUtils.states(params),
                "originalParams", StateUtils.toQuery(params))
        );
    }

    @GetMapping("tab1-op1")
    public ModelAndView op1(@RequestParam Map<String, String> params) {
        return new ModelAndView("tab1-op1", Map.of(
                "states", StateUtils.states(params),
                "originalParams", StateUtils.toQuery(params))
        );
    }

    @GetMapping("tab1-op2")
    public String op2() {
        return "tab1-op2";
    }

}
