package com.volanty.domain.repository;

import com.volanty.domain.document.Car;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CarRepository extends MongoRepository<Car, Long> {
}
