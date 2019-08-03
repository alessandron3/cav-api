package com.volanty.service;


import com.volanty.domain.document.Calendar;
import com.volanty.domain.document.Car;
import com.volanty.domain.document.Cav;
import com.volanty.domain.document.CavCalendar;
import com.volanty.domain.repository.CalendarRepository;
import com.volanty.domain.repository.CarRepository;
import com.volanty.domain.repository.CavRepository;
import com.volanty.exception.DateNotFoundException;
import com.volanty.exception.TimeAlreadyScheduledException;
import com.volanty.infrastructure.CavType;
import com.volanty.infrastructure.ScheduleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CavService {

    private CarRepository carRepository;
    private CavRepository cavRepository;
    private CalendarRepository calendarRepository;

    @Autowired
    public CavService(CarRepository carRepository, CavRepository cavRepository, CalendarRepository calendarRepository) {
        this.carRepository = carRepository;
        this.cavRepository = cavRepository;
        this.calendarRepository = calendarRepository;
    }


    public List<Cav> listAllCavs() {
        return cavRepository.findAll();
    }

    public List<Calendar> listAllCalendars() {
        return calendarRepository.findAll();
    }

    public List<Car> listAllCars() {
        return carRepository.findAll();
    }

    public Optional<Cav> findCavById(Long cavId) {
        return cavRepository.findById(cavId);
    }


    public List<Calendar> findCavSchedule(Cav cav, CavType type, String date) throws DateNotFoundException {

        final List<Calendar> calendars = new ArrayList<>();
        if (date != null && !date.isEmpty()) {
            calendars.add(calendarRepository.findByDate(date).orElseThrow(DateNotFoundException::new));
        } else
            calendars.addAll(calendarRepository.findAll());

        calendars.stream()
                .forEach(c -> {
                    final List<CavCalendar> newCavList = c.getCavs().stream()
                            .filter(cv -> cv.getName().equalsIgnoreCase(cav.getName()))
                            .collect(Collectors.toList());


                    if(type != null && type.name().equalsIgnoreCase(CavType.INSPECTION.name()))
                        newCavList.stream().forEach(ncl -> ncl.setVisit(null));
                    else if(type != null && type.name().equalsIgnoreCase(CavType.VISIT.name()))
                        newCavList.stream().forEach(ncl -> ncl.setInspection(null));

                    calendars.stream().forEach(ca -> {
                        ca.getCavs().stream().forEach(cvs -> {
                            if(cvs.getVisit() != null)
                                cvs.setVisit(cvs.getVisit().stream().filter(v -> v.getCarId() == null).collect(Collectors.toList()));
                            if(cvs.getInspection() != null)
                                cvs.setInspection(cvs.getInspection().stream().filter(v -> v.getCarId() == null).collect(Collectors.toList()));
                        });
                    });

                    c.setCavs(newCavList);
                });

        return calendars;
    }


    public void scheduleVisit(ScheduleRequest request, Cav cav) throws Exception {

        final Optional<Calendar> optionalCalendar = calendarRepository.findByDate(request.getDate());
        if(optionalCalendar.isPresent()) {
            optionalCalendar.get()
                    .getCavs()
                    .stream()
                    .filter(cCalendar -> cCalendar.getName().equalsIgnoreCase(cav.getName()))
                    .findFirst().orElseThrow(Exception::new)
                    .getVisit()
                        .stream()
                        .filter(t -> t.getHour().equals(request.getHour()) && t.getCarId() == null)
                        .findFirst().orElseThrow(TimeAlreadyScheduledException::new)
                        .setCarId(request.getCarId());

            calendarRepository.save(optionalCalendar.get());
        } else
            throw new DateNotFoundException();
    }

    public void scheduleInspection(ScheduleRequest request, Cav cav) throws Exception {

        final Optional<Calendar> optionalCalendar = calendarRepository.findByDate(request.getDate());
        if(optionalCalendar.isPresent()) {
            optionalCalendar.get()
                    .getCavs()
                    .stream()
                    .filter(cCalendar -> cCalendar.getName().equalsIgnoreCase(cav.getName()))
                    .findFirst().orElseThrow(Exception::new)
                        .getInspection()
                        .stream()
                        .filter(t -> t.getHour().equals(request.getHour()) && t.getCarId() == null)
                        .findFirst().orElseThrow(TimeAlreadyScheduledException::new)
                        .setCarId(request.getCarId());

            calendarRepository.save(optionalCalendar.get());
        } else
            throw new DateNotFoundException();
    }

}
