package sandbox.htmxpoc.web.tab1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import sandbox.htmxpoc.web.Option;
import sandbox.htmxpoc.web.StateUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/tab1/tab1-op1")
public class Op1Controller {

    @GetMapping("/f1")
    public ModelAndView filter1(@RequestParam(name = "f1", defaultValue = "") String selected) {
        return new ModelAndView("common/options", Map.of(
                "options", List.of(
                        new Option("", ""),
                        new Option("1", "1"),
                        new Option("2", "2"),
                        new Option("3", "3")
                ),
                "selected", selected
        ));
    }

    @GetMapping("/f2")
    public ModelAndView filter2(@RequestParam(name = "f1", required = false) String filter1,
                                @RequestParam(name = "f2", defaultValue = "") String selected) {
        List<Option> values;
        if (filter1 != null) {
            values = List.of(
                    new Option("", ""),
                    new Option("A" + filter1, "A" + filter1),
                    new Option("B" + filter1, "B" + filter1)
            );
        } else {
            values = List.of(new Option("", ""));
        }
        return new ModelAndView("common/options", Map.of(
                "options", values,
                "selected", selected
        ));
    }

    @GetMapping("/data")
    public ModelAndView data(HttpServletResponse response,
                             @RequestParam(name = "f1", required = false) String filter1,
                             @RequestParam(name = "f2", required = false) String filter2) {
        response.addHeader("HX-Push", StateUtils.toQuery(Map.of(
                        "state_0", "tab1",
                        "state_1", "tab1-op1",
                        "f1", filter1,
                        "f2", filter2
                )
        ));
        return new ModelAndView("common/rows", Map.of(
                "rows", List.of(
                        new String[]{new Date().toString(), ""},
                        new String[]{filter1 + "_1", filter2 + "_1"},
                        new String[]{filter1 + "_2", filter2 + "_2"},
                        new String[]{filter1 + "_3", filter2 + "_3"},
                        new String[]{filter1 + "_4", filter2 + "_4"}
                )
        ));
    }
}
