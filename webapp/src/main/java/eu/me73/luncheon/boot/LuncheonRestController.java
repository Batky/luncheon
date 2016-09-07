package eu.me73.luncheon.boot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LuncheonRestController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    String getIndex(){
        return "index.html";
    }

}
