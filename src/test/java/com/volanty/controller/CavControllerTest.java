package com.volanty.controller;

import com.google.gson.Gson;
import com.volanty.domain.document.Car;
import com.volanty.domain.document.Cav;
import com.volanty.infrastructure.ScheduleRequest;
import com.volanty.service.CavService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CavControllerTest {

    @Mock
    private CavService cavService;
    private MockMvc mockMvc;
    @InjectMocks
    private CavController cavController;
    private Cav cav;
    private ScheduleRequest request;
    private Gson gson = new Gson();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(cavController).build();
        cav = new Cav(1L, "Botafogo");
        request = new ScheduleRequest(1L, "10", "2019-08-01");
    }

    @Test
    public void listAllCavsTest() throws Exception {
        this.mockMvc.perform(get("/cav")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void findCavByIdWithSuccessTest() throws Exception {
        when(cavService.findCavById(1L)).thenReturn(Optional.of(cav));
        this.mockMvc.perform(get("/cav/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void findCavByIdWithNotFoundTest() throws Exception {
        when(cavService.findCavById(1L)).thenReturn(Optional.empty());
        this.mockMvc.perform(get("/cav/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void listAllCarsTest() throws Exception {
        when(cavService.listAllCars()).thenReturn(new ArrayList<Car>());
        this.mockMvc.perform(get("/car")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void scheduleInspectionCavNotFoundTest() throws Exception {
        when(cavService.findCavById(11L)).thenReturn(Optional.empty());
        this.mockMvc.perform(post("/cav/11/inspection")
                .content(gson.toJson(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void scheduleInspectionSuccessTest() throws Exception {
        when(cavService.findCavById(1L)).thenReturn(Optional.of(cav));
        this.mockMvc.perform(post("/cav/1/inspection")
                .content(gson.toJson(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void scheduleVisitCavNotFoundTest() throws Exception {
        when(cavService.findCavById(11L)).thenReturn(Optional.empty());
        this.mockMvc.perform(post("/cav/11/visit")
                .content(gson.toJson(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void scheduleVisitSuccessTest() throws Exception {
        when(cavService.findCavById(1L)).thenReturn(Optional.of(cav));
        this.mockMvc.perform(post("/cav/1/visit")
                .content(gson.toJson(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}