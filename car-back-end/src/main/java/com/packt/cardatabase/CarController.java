package com.packt.cardatabase;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.packt.cardatabase.domain.Car;
import com.packt.cardatabase.domain.CarRepository;
import com.packt.cardatabase.domain.MarketEstimate;


@RestController
public class CarController {

    final static Logger logger = LoggerFactory.getLogger(CarController.class);

    @Value("${estimator.uri}")
    private String estimatorUri;

    @Autowired
    CarRepository carRepository;

    @Autowired
    public CarController(CarRepository repo) {
        carRepository = repo;
    }

    @RequestMapping(method= RequestMethod.GET, value="/api/cars")
    public Iterable<Car> Car() {
        logger.debug("GET all cars from endpoint /api/cars");
        return carRepository.findAll();
    }

    @RequestMapping(method=RequestMethod.POST, value="/api/cars")
    public Car save(@RequestBody Car car) {
        logger.debug("Adding new car using POST endpoint /api/cars");

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(estimatorUri)
                // Add query parameter
                .queryParam("brand", car.getBrand())
                .queryParam("model", car.getModel())
                .queryParam("year", car.getYear());


        logger.debug(builder.build().toString());
        RestTemplate restTemplate = new RestTemplate();
        MarketEstimate carEstimate = restTemplate.getForObject(builder.build().toString(), MarketEstimate.class);
        logger.debug(carEstimate.toString());
        car.setMarketEstimate(carEstimate.getEstimate());
        carRepository.save(car);

        return car;
    }

    @RequestMapping(method=RequestMethod.GET, value="/api/cars/{id}")
    public Optional<Car> show(@PathVariable Long id) {
        logger.debug("Getting car by Id using GET endpoint /api/cars/{id}");
        return carRepository.findById(id);
    }

    @RequestMapping(method=RequestMethod.PUT, value="/api/cars/{id}")
    public Car update(@PathVariable Long id, @RequestBody Car Car) {
        logger.debug("Updating car by Id using PUT endpoint /api/cars/{id}");
        Optional<Car> optCar = carRepository.findById(id);
        Car car = optCar.get();
        if(Car.getBrand() != null)
            car.setBrand(Car.getBrand());
        if(Car.getModel() != null)
            car.setModel(Car.getModel());
        if(Car.getColor() != null)
            car.setColor(Car.getColor());
        if(Car.getRegisterNumber() != null)
            car.setRegisterNumber(Car.getRegisterNumber());
        if(Car.getYear() != 0)
            car.setYear(Car.getYear());
        if(Car.getPrice() != 0)
            car.setPrice(Car.getPrice());

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(estimatorUri)
                // Add query parameter
                .queryParam("brand", car.getBrand())
                .queryParam("model", car.getModel())
                .queryParam("year", car.getYear());

        logger.debug(builder.build().toString());
        RestTemplate restTemplate = new RestTemplate();
        MarketEstimate carEstimate = restTemplate.getForObject(builder.build().toString(), MarketEstimate.class);
        logger.debug(carEstimate.toString());
        car.setMarketEstimate(carEstimate.getEstimate());
        carRepository.save(car);
        return car;

    }

    @RequestMapping(method=RequestMethod.DELETE, value="/api/cars/{id}")
    public String delete(@PathVariable long id) {
    	logger.debug("Delinting car by Id using endpoint /api/cars/{id}");
        Optional<Car> optCar = carRepository.findById(id);
        Car Car = optCar.get();
        carRepository.delete(Car);
        return "";
    }
}