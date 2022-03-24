package com.codegym.controller;

import com.codegym.model.Customer;
import com.codegym.service.customer.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CustomerController {
    @Autowired
    ICustomerService customerService;

    @GetMapping ("/customers")
    public ModelAndView ListCustomer (){
        List<Customer> customers = customerService.findAll();
        ModelAndView modelAndView = new ModelAndView("/customer/list");
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }

    @GetMapping ("/customers/create")
    public ModelAndView showCreateForm (){
        ModelAndView modelAndView = new ModelAndView("/customer/create");
        modelAndView.addObject("customer", new Customer());
        return modelAndView;
    }

    @PostMapping ("/customers/create")
    public ModelAndView saveCustomer (@ModelAttribute Customer customer){
        customerService.save(customer);
        ModelAndView modelAndView = new ModelAndView("/customer/create");
        modelAndView.addObject("customer", new Customer());//sau mỗi lần submit sẽ xóa trắng form
        modelAndView.addObject("message", "New customer created successfully");
        return modelAndView;
//        return new ModelAndView("redirect:/products/list");
    }

    @GetMapping ("/customers/edit/{id}")

    public ModelAndView showEditForm (@PathVariable Long id){
        Customer customer = customerService.findById(id);
        if (customer!= null){
            ModelAndView modelAndView = new ModelAndView("/customer/edit");
            modelAndView.addObject("customer", customer);
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView("/error.404");
        return modelAndView;
    }
    @PostMapping("/customers/edit")
    public ModelAndView updateCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.save(customer);
        ModelAndView modelAndView = new ModelAndView("/customer/edit");
        modelAndView.addObject("customer", customer);
        modelAndView.addObject("message", "Customer updated successfully");
        return modelAndView;
    }
    @GetMapping ("/customers/delete/{id}")
    public ModelAndView showDeleteForm (@PathVariable Long id){
        Customer customer = customerService.findById(id);
        if (customer!= null){
            ModelAndView modelAndView = new ModelAndView("/customer/delete");
            modelAndView.addObject("customer", customer);
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView("/error.404");
        return modelAndView;
    }
    @PostMapping("/customers/delete")
    public String deleteCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.remove(customer.getId());
        return "redirect:/customers";
    }
}
