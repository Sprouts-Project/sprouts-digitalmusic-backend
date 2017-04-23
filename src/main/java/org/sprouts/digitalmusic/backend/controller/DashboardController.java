package org.sprouts.digitalmusic.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.sprouts.digitalmusic.backend.service.DashboardService;
import org.sprouts.digitalmusic.model.parser.customer.BestReviewers;
import org.sprouts.digitalmusic.model.parser.customer.CustomerOverview;
import org.sprouts.digitalmusic.model.parser.customer.CustomerSegmentationAgeAndBrand;
import org.sprouts.digitalmusic.model.parser.customer.CustomerSegmentationAgeAndItemProfile;
import org.sprouts.digitalmusic.model.parser.finance.FinanceMonthlySalesPredictions;
import org.sprouts.digitalmusic.model.parser.finance.FinanceMonthlySalesPredictionsByState;
import org.sprouts.digitalmusic.model.parser.finance.FinanceOverview;
import org.sprouts.digitalmusic.model.parser.finance.SalesPredictionsByItemProfiles;
import org.sprouts.digitalmusic.model.parser.items.ItemProfile;
import org.sprouts.digitalmusic.model.parser.stock.MonthlySalesPredictions;
import org.sprouts.digitalmusic.model.parser.stock.MonthlySalesPredictionsByState;
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

	@RequestMapping(value = "/sales-predictions-by-item-profiles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<SalesPredictionsByItemProfiles> salesPredictionsByItemProfiles() throws Exception {
		List<SalesPredictionsByItemProfiles> salesPredictionsByItemProfiles = dashboardService.getSalesPredictionsByItemProfiles();
		return salesPredictionsByItemProfiles;
	}
	
	@RequestMapping(value = "/sales-predictions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<MonthlySalesPredictions> monthlySalesPredictions() throws Exception {
		List<MonthlySalesPredictions> monthlySalesPredictions = dashboardService.getMonthlySalesPredictions();
		return monthlySalesPredictions;
	}
	
	@RequestMapping(value = "/best-reviewers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<BestReviewers> bestReviewers() throws Exception {
		List<BestReviewers> bestReviewers = dashboardService.getBestReviewers();
		return bestReviewers;
	}
	
	@RequestMapping(value = "/customer-segmentation-age-and-brand", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CustomerSegmentationAgeAndBrand> customerSegmentationAgeAndBrand() throws Exception {
		List<CustomerSegmentationAgeAndBrand> customerSegmentationAgeAndBrand = dashboardService.getCustomerSegmentationAgeAndBrand();
		return customerSegmentationAgeAndBrand;
	}

	@RequestMapping(value = "/customer-segmentation-age-and-item-profile", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CustomerSegmentationAgeAndItemProfile> customerSegmentationAgeAndItemProfile() throws Exception {
		List<CustomerSegmentationAgeAndItemProfile> customerSegmentationAgeAndItemProfile = dashboardService.getCustomerSegmentationAgeAndItemProfile();
		return customerSegmentationAgeAndItemProfile;
	}
	
	@RequestMapping(value = "/item-profiles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ItemProfile> itemProfile() throws Exception {
		List<ItemProfile> itemProfile = dashboardService.getItemProfile();
		return itemProfile;
	}
	
	@RequestMapping(value = "/sales-predictions-by-state", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<MonthlySalesPredictionsByState> monthlySalesPredictionsByStates() throws Exception {
		List<MonthlySalesPredictionsByState> monthlySalesPredictionsByStates = dashboardService.getMonthlySalesPredictionsByStates();
		return monthlySalesPredictionsByStates;
	}
	
	@RequestMapping(value = "/sales-value-predictions-by-state", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<FinanceMonthlySalesPredictionsByState> financeMonthlySalesPredictionsByStates() throws Exception {
		List<FinanceMonthlySalesPredictionsByState> financeMonthlySalesPredictionsByStates = dashboardService.getFinanceMonthlySalesPredictionsByStates();
		return financeMonthlySalesPredictionsByStates;
	}
	
	@RequestMapping(value = "/sales-value-predictions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<FinanceMonthlySalesPredictions> financeMonthlySalesPredictions() throws Exception {
		List<FinanceMonthlySalesPredictions> financeMonthlySalesPredictions = dashboardService.getFinanceMonthlySalesPredictions();
		return financeMonthlySalesPredictions;
	}
	
}