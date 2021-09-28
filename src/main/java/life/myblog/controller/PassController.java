package life.myblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PassController {
    @GetMapping("/pass.html")
    public String pass(){
        return "pass";
    }
}
