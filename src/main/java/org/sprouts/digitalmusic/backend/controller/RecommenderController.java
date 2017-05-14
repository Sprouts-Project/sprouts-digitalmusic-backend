package org.sprouts.digitalmusic.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.sprouts.digitalmusic.backend.service.RecommenderService;
import org.sprouts.digitalmusic.model.Item;
import org.sprouts.digitalmusic.model.parser.recommender.AlsoBoughtRecommender;
import org.sprouts.digitalmusic.model.parser.recommender.BestReviewedDuringLastSixMonths;
import org.sprouts.digitalmusic.model.parser.recommender.ItemProfileRecommender;
import org.sprouts.digitalmusic.model.parser.recommender.ItemRecommendation;
import org.sprouts.digitalmusic.model.parser.recommender.MostSoldDuringLastSixMonths;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/recommender")
public class RecommenderController extends AbstractController {

	@Autowired
	private RecommenderService recommenderService;
	
	@RequestMapping(value = "/also-bought-recommender", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public AlsoBoughtRecommender alsoBoughtRecommender(@RequestParam int itemId) throws Exception {
		AlsoBoughtRecommender alsoBoughtRecommender = recommenderService.getAlsoBoughtRecommender(itemId);
		return alsoBoughtRecommender;
	}
	
	@RequestMapping(value = "/best-reviewed-during-last-six-months", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<BestReviewedDuringLastSixMonths> bestReviewedDuringLastSixMonths() throws Exception {
		List<BestReviewedDuringLastSixMonths> bestReviewedDuringLastSixMonths = recommenderService.getBestReviewedDuringLastSixMonths();
		return bestReviewedDuringLastSixMonths;
	}
	
	@RequestMapping(value = "/most-sold-during-last-six-months", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<MostSoldDuringLastSixMonths> mostSoldDuringLastSixMonths() throws Exception {
		List<MostSoldDuringLastSixMonths> mostSoldDuringLastSixMonths = recommenderService.getMostSoldDuringLastSixMonths();
		return mostSoldDuringLastSixMonths;
	}

	@RequestMapping(value = "/collaborative-filtering-recommender", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ItemRecommendation> collaborativeFilteringRecommender() throws Exception {
		List<ItemRecommendation> result = recommenderService.getCollaborativeFilteringRecommends();
		return result;
	}
	
	@RequestMapping(value = "/item-profile-recommender", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ItemProfileRecommender itemProfileRecommender(@RequestParam int itemId) throws Exception {
		ItemProfileRecommender itemProfileRecommender = recommenderService.getItemProfileRecommeender(itemId);
		return itemProfileRecommender;
	}
	
}