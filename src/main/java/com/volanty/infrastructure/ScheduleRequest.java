package com.volanty.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleRequest {

    @NotNull(message = "carId is required")
    private Long carId;
    @NotNull(message = "hour is required")
    private String hour;
    @NotNull(message = "date is required")
    private String date;
}
