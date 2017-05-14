package org.sprouts.digitalmusic.backend.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.sprouts.digitalmusic.backend.service.CustomerService;
import org.sprouts.digitalmusic.forms.CustomerForm;
import org.sprouts.digitalmusic.model.Customer;
import org.sprouts.digitalmusic.pojo.ResponseObject;

@RestController
@RequestMapping("/customer")
public class CustomerController extends AbstractController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseObject registerCustomer(@RequestBody Customer customer) {
        return getResponseObject(customerService.save(customer));
    }

    @RequestMapping(value = "/show", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer showCustomer() {
        return customerService.getCustomerInfo();
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseObject editCustomer(@RequestBody CustomerForm customerForm) {
        return getResponseObject(customerService.edit(customerForm));
    }



}
