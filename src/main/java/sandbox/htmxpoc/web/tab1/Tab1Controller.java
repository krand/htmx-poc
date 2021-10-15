package sandbox.htmxpoc.web.tab1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tab1")
public class Tab1Controller {
    @GetMapping
    String content() {
        return "tab1";
    }

    @GetMapping("tab1-op1")
    String op1() {
        return "tab1-op1";
    }

    @GetMapping("tab1-op2")
    String op2() {
        return "tab1-op2";
    }

}
