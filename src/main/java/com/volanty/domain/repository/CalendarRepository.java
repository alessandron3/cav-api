package com.volanty.domain.repository;

import com.volanty.domain.document.Calendar;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CalendarRepository extends MongoRepository<Calendar, Long> {

    Optional<Calendar> findByDate(String date);
}
