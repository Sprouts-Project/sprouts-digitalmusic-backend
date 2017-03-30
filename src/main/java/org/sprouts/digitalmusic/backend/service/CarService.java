package org.sprouts.digitalmusic.backend.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sprouts.digitalmusic.backend.da.CarDAO;
import org.sprouts.digitalmusic.model.Car;


@Service("carService")
public class CarService {

	// Managed Data Access Objects --------------------------------------------
	
	@Autowired
	private CarDAO carDAO;
	
	// Simple CRUD Methods ----------------------------------------------------
	
	public Car findOne(int id) {
		Car result;
		
		result = carDAO.findOne(id);
		
		return result;
	}
	
	public Collection<Car> findAll() {
		Collection<Car> result;
		
		result = (Collection<Car>) carDAO.findAll();
		
		return result;
	}
	
	public int save(Car car) {
		carDAO.save(car);
		
		return 1;
	}
	
	public int delete(Car car) {
		carDAO.delete(car);
		
		return 1;
	}
}