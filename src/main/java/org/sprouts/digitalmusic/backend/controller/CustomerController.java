package org.sprouts.digitalmusic.backend.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.sprouts.digitalmusic.backend.service.CustomerService;
import org.sprouts.digitalmusic.model.Customer;
import org.sprouts.digitalmusic.pojo.ResponseObject;

@RestController
@Api
@RequestMapping("/customer")
public class CustomerController extends AbstractController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseObject registerCustomer(@RequestBody Customer customer) {
        System.out.println(customer);
        return getResponseObject(customerService.save(customer));
    }

}
