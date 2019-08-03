package com.volanty.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class LoadProperties {

    @Value("classpath:cars.json")
    private Resource carResourceFile;

    @Value("classpath:cav.json")
    private Resource cavResourceFile;

    @Value("classpath:calendar.json")
    private Resource calendarResourceFile;
}
