package org.sprouts.digitalmusic.backend.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.sprouts.digitalmusic.backend.service.OrderService;
import org.sprouts.digitalmusic.model.Order;
import org.sprouts.digitalmusic.pojo.ResponseObject;

@RestController
@Api
@RequestMapping("/order")
public class OrderController extends AbstractController{

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseObject create(@RequestBody Order order) throws Exception {
        return getResponseObject(orderService.save(order));
    }

}
