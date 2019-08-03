package com.volanty.service;

import com.volanty.domain.document.Calendar;
import com.volanty.domain.document.Cav;
import com.volanty.domain.document.CavCalendar;
import com.volanty.domain.document.Time;
import com.volanty.domain.repository.CalendarRepository;
import com.volanty.domain.repository.CarRepository;
import com.volanty.domain.repository.CavRepository;
import com.volanty.exception.DateNotFoundException;
import com.volanty.exception.TimeAlreadyScheduledException;
import com.volanty.infrastructure.CavType;
import com.volanty.infrastructure.ScheduleRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class CavServiceTest {

    @Mock
    private CarRepository carRepository;
    @Mock
    private CavRepository cavRepository;
    @Mock
    private CalendarRepository calendarRepository;
    private CavService cavService;
    @Mock
    private Calendar mockCalendar;
    @Mock
    private ScheduleRequest request;
    private Cav cav;
    private List<CavCalendar> cavCalendars = new ArrayList<>();
    private Calendar calendar;


    @Before
    public void setup() {
        cavService = new CavService(carRepository, cavRepository, calendarRepository);
        //request = new ScheduleRequest(1L, "10", "2019-08-01");
        cav = new Cav(1L , "Botafogo");

        final List<Time> times = new ArrayList<>();
        times.add(new Time("10", null));
        times.add(new Time("11", null));
        times.add(new Time("12", 1L));
        times.add(new Time("13", null));
        times.add(new Time("14", 1L));


        cavCalendars.add(new CavCalendar("Botafogo", times, times));
        cavCalendars.add(new CavCalendar("Barra da Tijuca", times, times));
        cavCalendars.add(new CavCalendar("Norte Shopping", times, times));
        calendar = new Calendar();
        calendar.setCavs(cavCalendars);
        calendar.setDate("2019-08-01");
    }

    @Test
    public void scheduleVisitSuccessTest() throws Exception {
        when(request.getDate()).thenReturn("2019-08-01");
        when(request.getHour()).thenReturn("10");
        when(calendarRepository.findByDate("2019-08-01")).thenReturn(Optional.of(mockCalendar));
        when(mockCalendar.getCavs()).thenReturn(cavCalendars);
        cavService.scheduleVisit(request, cav);
    }

    @Test(expected = TimeAlreadyScheduledException.class)
    public void scheduleVisitTimeNotEmptyTest() throws Exception {
        when(request.getDate()).thenReturn("2019-08-01");
        when(request.getHour()).thenReturn("12");
        when(calendarRepository.findByDate("2019-08-01")).thenReturn(Optional.of(mockCalendar));
        when(mockCalendar.getCavs()).thenReturn(cavCalendars);
        cavService.scheduleVisit(request, cav);
    }

    @Test(expected = DateNotFoundException.class)
    public void scheduleVisitDateNotFoundTest() throws Exception {
        when(request.getDate()).thenReturn("2019-13-01");
        when(calendarRepository.findByDate("2019-13-01")).thenReturn(Optional.empty());
        when(mockCalendar.getCavs()).thenReturn(cavCalendars);
        cavService.scheduleVisit(request, cav);
    }

    @Test
    public void scheduleInspectionSuccessTest() throws Exception {
        when(request.getDate()).thenReturn("2019-08-01");
        when(request.getHour()).thenReturn("10");
        when(calendarRepository.findByDate("2019-08-01")).thenReturn(Optional.of(mockCalendar));
        when(mockCalendar.getCavs()).thenReturn(cavCalendars);
        cavService.scheduleInspection(request, cav);
    }

    @Test(expected = TimeAlreadyScheduledException.class)
    public void scheduleInspectionTimeNotEmptyTest() throws Exception {
        when(request.getDate()).thenReturn("2019-08-01");
        when(request.getHour()).thenReturn("12");
        when(calendarRepository.findByDate("2019-08-01")).thenReturn(Optional.of(mockCalendar));
        when(mockCalendar.getCavs()).thenReturn(cavCalendars);
        cavService.scheduleInspection(request, cav);
    }

    @Test(expected = DateNotFoundException.class)
    public void scheduleInspectionDateNotFoundTest() throws Exception {
        when(request.getDate()).thenReturn("2019-13-01");
        when(calendarRepository.findByDate("2019-13-01")).thenReturn(Optional.empty());
        when(mockCalendar.getCavs()).thenReturn(cavCalendars);
        cavService.scheduleInspection(request, cav);
    }

    @Test
    public void findCavScheduleTest() throws DateNotFoundException {

        when(calendarRepository.findAll()).thenReturn(new ArrayList<Calendar>(Arrays.asList(calendar)));
        final List<Calendar> calendars = cavService.findCavSchedule(cav, null, null);
        verify(calendarRepository, times(1)).findAll();
        verify(calendarRepository,times(0)).findByDate(any());

        for (Calendar calendar: calendars) {
            for(CavCalendar cavCalendar: calendar.getCavs()) {
                Assert.assertEquals(cav.getName(), cavCalendar.getName());
            }
        }
    }


    @Test
    public void findCavScheduleWithTypeInspectionTest() throws DateNotFoundException {

        when(calendarRepository.findAll()).thenReturn(new ArrayList<Calendar>(Arrays.asList(calendar)));
        final List<Calendar> calendars = cavService.findCavSchedule(cav, CavType.INSPECTION, null);
        verify(calendarRepository, times(1)).findAll();
        verify(calendarRepository,times(0)).findByDate(any());

        for (Calendar calendar: calendars) {
            for(CavCalendar cavCalendar: calendar.getCavs()) {
                Assert.assertEquals(cav.getName(), cavCalendar.getName());
                assertNull(cavCalendar.getVisit());
                assertNotNull(cavCalendar.getInspection());
            }
        }
    }

    @Test
    public void findCavScheduleWithTypeVisitTest() throws DateNotFoundException {

        when(calendarRepository.findAll()).thenReturn(new ArrayList<Calendar>(Arrays.asList(calendar)));
        final List<Calendar> calendars = cavService.findCavSchedule(cav, CavType.VISIT, null);
        verify(calendarRepository, times(1)).findAll();
        verify(calendarRepository,times(0)).findByDate(any());

        for (Calendar calendar: calendars) {
            for(CavCalendar cavCalendar: calendar.getCavs()) {
                Assert.assertEquals(cav.getName(), cavCalendar.getName());
                assertNotNull(cavCalendar.getVisit());
                assertNull(cavCalendar.getInspection());
            }
        }
    }

    @Test(expected = DateNotFoundException.class)
    public void findCavScheduleWithInvalidDateTest() throws DateNotFoundException {
        when(calendarRepository.findByDate("2018-10-10")).thenReturn(Optional.empty());
        final List<Calendar> calendars = cavService.findCavSchedule(cav, null, "2018-10-10");
    }

    @Test
    public void findCavScheduleWithValidDateTest() throws DateNotFoundException {
        when(calendarRepository.findByDate("2019-08-01")).thenReturn(Optional.of(calendar));
        final List<Calendar> calendars = cavService.findCavSchedule(cav, null, "2019-08-01");

        assertEquals(1, calendars.size());

    }
}