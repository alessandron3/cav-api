package com.volanty.domain.repository;

import com.volanty.domain.document.Cav;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CavRepository extends MongoRepository<Cav, Long> {
}
