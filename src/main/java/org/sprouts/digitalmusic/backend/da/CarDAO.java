package org.sprouts.digitalmusic.backend.da;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.sprouts.digitalmusic.model.Car;


@Transactional
public interface CarDAO extends CrudRepository<Car, Integer> {

}