package org.sprouts.digitalmusic.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.sprouts.digitalmusic.backend.service.OrderService;
import org.sprouts.digitalmusic.model.Order;
import org.sprouts.digitalmusic.pojo.PaginationInfo;
import org.sprouts.digitalmusic.pojo.ResponseObject;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/order")
public class OrderController extends AbstractController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseObject create(@RequestBody Order order) throws Exception {
        return getResponseObject(orderService.save(order));
    }

    @RequestMapping(value = "/admin/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object[] list(@RequestParam int pageNumber) throws Exception {
        Object[] result = new Object[2];

        Page<Order> page = orderService.findAll(pageNumber);

        result[0] = page.getContent();
        result[1] = new PaginationInfo(pageNumber, page.getTotalPages(), page.getNumberOfElements(), 10);

        return result;
    }

    @RequestMapping(value = "/admin/markAsDelivered", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseObject markAsDelivered(@RequestParam int orderId) throws Exception {
        return getResponseObject(orderService.markAsDelivered(orderId));
    }


}
