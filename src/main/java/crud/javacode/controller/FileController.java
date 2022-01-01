package crud.javacode.controller;

import crud.javacode.dto.CustomerDTO;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "")
public class FileController {
    @RequestMapping(value = "/file", method = RequestMethod.GET)
    public ModelAndView homePage() {
        CustomerDTO customerDTO = new CustomerDTO();
        ModelAndView mav = new ModelAndView("create-file");
//        customerDTO.setListResult(customerService.getCustomers());
//        mav.addObject("customerDTO", customerDTO);
        return mav;
    }
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ModelAndView uploadFile(BindingResult result,
                                   @RequestParam("file") MultipartFile photo,
                                   @RequestParam("type") String type) {
        ModelAndView mav = new ModelAndView("create-file");
//        customerDTO.setListResult(customerService.getCustomers());
//        mav.addObject("customerDTO", customerDTO);
        return mav;
    }
}
