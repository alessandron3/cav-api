package com.volanty.infrastructure;

import com.volanty.domain.document.Calendar;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalendarWrapper {

    private List<Calendar> calendars;
}
