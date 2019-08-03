package com.volanty.controller;

import com.volanty.domain.document.Calendar;
import com.volanty.domain.document.Car;
import com.volanty.domain.document.Cav;
import com.volanty.exception.DateNotFoundException;
import com.volanty.infrastructure.CavType;
import com.volanty.infrastructure.ScheduleRequest;
import com.volanty.service.CavService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class CavController {

    @Autowired
    private CavService cavService;

    @GetMapping(value = "/cav", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Cav>> listAllCavs() {
        final List<Cav> cavs = cavService.listAllCavs();
        return ResponseEntity.ok(cavs);
    }

    @GetMapping(value = "/cav/{cavId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Calendar>> findCavById(@PathVariable("cavId") Long cavId,
                                                      @RequestParam(value = "type", required = false) CavType type,
                                                      @RequestParam(value = "date", required = false) String date)
            throws DateNotFoundException {
        final Optional<Cav> optionalCav = cavService.findCavById(cavId);

        if (optionalCav.isPresent()) {
            final List<Calendar> calendars = cavService.findCavSchedule(optionalCav.get(), type, date);
            return ResponseEntity.ok(calendars);
        }
        return ResponseEntity.notFound().build();

    }

    @GetMapping(value = "/car", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Car>> listAllCars() {
        final List<Car> cars = cavService.listAllCars();
        return ResponseEntity.ok(cars);
    }


    @PostMapping(value = "/cav/{cavId}/inspection", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity scheduleInspection(@PathVariable("cavId") Long cavId,
                                             @Valid @RequestBody ScheduleRequest request) throws Exception {

        final Optional<Cav> optionalCav = cavService.findCavById(cavId);
        if (!optionalCav.isPresent())
            return ResponseEntity.notFound().build();

        cavService.scheduleInspection(request, optionalCav.get());

        return ResponseEntity.created(new URI("")).build();
    }

    @PostMapping(value = "/cav/{cavId}/visit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity scheduleVisit(@PathVariable("cavId") Long cavId,
                                        @Valid @RequestBody ScheduleRequest request) throws Exception {

        final Optional<Cav> optionalCav = cavService.findCavById(cavId);
        if (!optionalCav.isPresent())
            return ResponseEntity.notFound().build();

        cavService.scheduleVisit(request, optionalCav.get());

        return ResponseEntity.created(new URI("")).build();
    }

}
