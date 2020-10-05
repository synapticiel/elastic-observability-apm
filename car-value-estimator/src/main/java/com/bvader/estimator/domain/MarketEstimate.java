package com.bvader.estimator.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketEstimate {

    final static Logger logger = LoggerFactory.getLogger(MarketEstimate.class);
    private  int estimate = 0;
    private  String brand;
    private  String model;
    private  int year;

    public MarketEstimate(String brand, String model, int year) {
        this.brand = brand;
        this.model = model;
        this.year = year;
    }

    public void calculateEstimate() {

        int basePrice = 0;
        switch(brand)
        {
            case "Toyota":
                basePrice = 25000;
                logger.debug("Value Estimation of Model : Toyota");
                break;
            case "Lexus":
                basePrice = 35000;
                logger.debug("Value Estimation of Model : Lexus");
                break;
            case "Ford":
                basePrice = 20000;
                logger.debug("Value Estimation of Model : Ford");
                break;
            case "Nissan":
                basePrice = 20000;
                logger.debug("Value Estimation of Model : Nissan");
                break;
            case "Tesla":
                basePrice = 60000;
                logger.debug("Value Estimation of Model : Tesla");
                break;
            case "Ferrari":
                basePrice = 250000;
                calculateExoticPrice();
                logger.debug("Value Estimation of Model : Ferrari");
                break;
            default:
                basePrice = 30000;
                logger.debug("Value Estimation of Model : Other");
        }

        int est = (int) ((((Math.random()) - .5) / 10 + 1) * basePrice);
        setEstimate(est);
    }

    private void calculateExoticPrice() {
        // Hmmm lets think about this for a while
        logger.debug("Exotic car not in database, calculating ...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public int getEstimate() {
        return estimate;
    }

    public void setEstimate(int estimate) {
        this.estimate = estimate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}
