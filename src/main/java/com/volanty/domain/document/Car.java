package com.volanty.domain.document;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document
public class Car {

    @Id
    private Long id;
    private String brand;
    private String model;
    private String cav;
}
