package com.volanty.infrastructure;

import com.google.common.io.CharStreams;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.volanty.domain.document.Car;
import com.volanty.domain.document.Cav;
import com.volanty.domain.repository.CalendarRepository;
import com.volanty.domain.repository.CarRepository;
import com.volanty.domain.repository.CavRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataListener {

    private CarRepository carRepository;
    private CavRepository cavRepository;
    private CalendarRepository calendarRepository;
    private LoadProperties loadProperties;
    private Gson gson;

    @Autowired
    public DataListener(CarRepository carRepository, CavRepository cavRepository,
                        CalendarRepository calendarRepository,
                        LoadProperties loadProperties, Gson gson) {
        this.carRepository = carRepository;
        this.cavRepository = cavRepository;
        this.calendarRepository = calendarRepository;
        this.loadProperties = loadProperties;
        this.gson = gson;
        loadCars(loadProperties.getCarResourceFile());
        loadCavs(loadProperties.getCavResourceFile());
        loadCalendars(loadProperties.getCalendarResourceFile());
    }


    private void loadCars(Resource resource) {
        try {
            InputStream resourcee = resource.getInputStream();
            String text = null;
            try (final Reader reader = new InputStreamReader(resourcee)) {
                text = CharStreams.toString(reader);
            }

            Type listType = new TypeToken<ArrayList<Car>>(){}.getType();
            List<Car> cars = new Gson().fromJson(text, listType);
            carRepository.deleteAll();
            carRepository.saveAll(cars);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadCavs(Resource resource) {
        try {
            InputStream resourcee = resource.getInputStream();
            String text = null;
            try (final Reader reader = new InputStreamReader(resourcee)) {
                text = CharStreams.toString(reader);
            }

            Type listType = new TypeToken<ArrayList<Cav>>(){}.getType();
            List<Cav> cavs = new Gson().fromJson(text, listType);
            cavRepository.deleteAll();
            cavRepository.saveAll(cavs);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCalendars(Resource resource) {
        try {
            InputStream resourcee = resource.getInputStream();
            String text = null;
            try (final Reader reader = new InputStreamReader(resourcee)) {
                text = CharStreams.toString(reader);
            }

            final CalendarWrapper calendarWrapper = new Gson().fromJson(text, CalendarWrapper.class);
            calendarRepository.deleteAll();
            calendarRepository.saveAll(calendarWrapper.getCalendars());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
