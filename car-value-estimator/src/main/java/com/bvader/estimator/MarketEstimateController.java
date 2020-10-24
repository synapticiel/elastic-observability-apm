package com.bvader.estimator;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bvader.estimator.domain.MarketEstimate;

@RestController
public class MarketEstimateController {


    @RequestMapping("/api/estimateValue")
    public MarketEstimate estimateValue(@RequestParam(name = "brand") String make,
                                   @RequestParam(name = "model") String model,
                                   @RequestParam(name = "year") int year) {
        MarketEstimate estimate = new MarketEstimate(make, model, year);
        estimate.calculateEstimate();
        return estimate;
    }
}
