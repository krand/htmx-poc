package sandbox.htmxpoc.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class IndexController {

    @GetMapping({"/", "/index"})
    public ModelAndView index(
            @RequestParam(name = "L1", required = false) String l1,
            @RequestParam(name = "L2", required = false) String l2
    ) {
        Map<String, String> map = new HashMap<>();
        if (l1 != null) map.put("stateL1", l1);
        if (l2 != null) map.put("stateL2", l2);
        return new ModelAndView("index", map);
    }
}