package crud.javacode.controller;


import crud.javacode.dto.CustomerDTO;

import crud.javacode.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller(value = "customerControl")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView homePage() {
        CustomerDTO customerDTO = new CustomerDTO();
        ModelAndView mav = new ModelAndView("home");
        customerDTO.setListResult(customerService.getCustomers());
        mav.addObject("customerDTO", customerDTO);
        return mav;
    }

    @RequestMapping(value = "/createCustomer", method = RequestMethod.GET)
    public ModelAndView createPage(CustomerDTO customerDTO) {
        ModelAndView mav = new ModelAndView("createCustomer");
        mav.addObject("customer", customerDTO);
        return mav;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String newCustomer(CustomerDTO customerDTO) {
        customerService.saveCustomer(customerDTO);
        return "redirect:/";
    }

    @RequestMapping(value = "/delete")
    public String deleteCutomer(long id) {
        customerService.delete(id);
        return "redirect:/";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView editCustomer(long id) {
        ModelAndView mav = new ModelAndView("editCustomer");
        CustomerDTO customerDTO = customerService.getCustomer(id);
        mav.addObject("customer", customerDTO);
        return mav;
    }

    @RequestMapping("/search")
    public ModelAndView search(@RequestParam String keyword) {
        ModelAndView mav = new ModelAndView("search");
        List<CustomerDTO> resultList = customerService.search(keyword);
        mav.addObject("result", resultList);
        return mav;
    }
}
