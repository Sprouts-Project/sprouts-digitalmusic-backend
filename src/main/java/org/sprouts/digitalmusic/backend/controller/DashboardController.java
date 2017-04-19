package org.sprouts.digitalmusic.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.sprouts.digitalmusic.backend.service.DashboardService;
import org.sprouts.digitalmusic.model.parser.customer.CustomerOverview;
import org.sprouts.digitalmusic.model.parser.finance.FinanceOverview;
import org.sprouts.digitalmusic.model.parser.stock.StockOverview;

import io.swagger.annotations.Api;

@RestController
@Api
@RequestMapping("/dashboard")
public class DashboardController extends AbstractController {

	@Autowired
	private DashboardService dashboardService;
	
	@RequestMapping(value = "/customer-overview", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomerOverview customerOverview() throws Exception {
		CustomerOverview customerOverview = dashboardService.getCustomerOverview();
		return customerOverview;
	}
	
	@RequestMapping(value = "/finance-overview", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public FinanceOverview financeOverview() throws Exception {
		FinanceOverview financeOverview = dashboardService.getFinanceOverview();
		return financeOverview;
	}
	
	@RequestMapping(value = "/operation-overview", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public StockOverview operationOverview() throws Exception {
		StockOverview stockOverview = dashboardService.getStockOverview();
		return stockOverview;
	}
	
}