package crud.javacode.controller;

import crud.javacode.dto.CustomerDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller(value = "fileController")
public class FileController {
    @RequestMapping(value = "/file", method = RequestMethod.GET)
    public ModelAndView homePage() {
        CustomerDTO customerDTO = new CustomerDTO();
        ModelAndView mav = new ModelAndView("create-file");
//        customerDTO.setListResult(customerService.getCustomers());
//        mav.addObject("customerDTO", customerDTO);
        return mav;
    }
}
