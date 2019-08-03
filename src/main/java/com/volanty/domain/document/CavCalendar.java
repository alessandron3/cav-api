package com.volanty.domain.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CavCalendar {

    private String name;
    private List<Time> visit;
    private List<Time> inspection;

}
