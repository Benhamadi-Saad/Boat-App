package com.example.boatApp.boat.application.rest;

import com.example.boatApp.boat.application.dto.BoatRequest;
import com.example.boatApp.boat.application.dto.BoatResponse;
import com.example.boatApp.boat.application.dto.ErrorResponse;
import com.example.boatApp.boat.domain.Boat;
import com.example.boatApp.boat.domain.exception.BoatDomainException;
import com.example.boatApp.boat.domain.exception.ResourceNotFoundException;
import com.example.boatApp.boat.domain.service.BoatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BoatController.class)
@WithMockUser
public class BoatControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BoatService boatService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldCreateBoat_thenReturn201() throws Exception {
        ArgumentCaptor<Boat> boatArgumentCaptor = ArgumentCaptor.forClass(Boat.class);
        when(boatService.createBoat(any())).thenReturn(123L);

        final ResultActions mvcResult = mvc.perform(post("/api/boats")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new BoatRequest("name", "description of boat"))))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        final var actualResponseBody = mvcResult
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(actualResponseBody)
                .isEqualTo("123");
        verify(boatService, times(1)).createBoat(boatArgumentCaptor.capture());
        assertThat(boatArgumentCaptor.getValue().getName()).isEqualTo("name");
        assertThat(boatArgumentCaptor.getValue().getDescription()).isEqualTo("description of boat");
    }

    @Test
    public void shouldCreateBoat_thenReturn404() throws Exception {
        when(boatService.createBoat(any()))
                .thenThrow(new BoatDomainException("error validation"));

        final ResultActions mvcResult = mvc.perform(post("/api/boats")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new BoatRequest("name", "description of boat"))))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        final var actualResponseBodyAsString = mvcResult
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(actualResponseBodyAsString)
                .isNotEmpty()
                .isEqualToIgnoringWhitespace(
                        objectMapper.writeValueAsString(new ErrorResponse("error validation"))
                );
    }

    @Test
    public void shouldDeleteBoat_thenReturn200() throws Exception {

        mvc.perform(delete("/api/boats/{id}", 1L))
                .andExpect(status().isOk());

        verify(boatService, times(1)).deleteBoat(eq(1L));
    }

    @Test
    public void shouldGetAllBoats_ThenReturn200() throws Exception {
        when(boatService.getAllBoats())
                .thenReturn(singletonList(factoryBoatDomain(1L)));

        final ResultActions resultPerform = mvc.perform(get("/api/boats")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        final var actualResponseBodyAsString = resultPerform
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(actualResponseBodyAsString)
                .isNotEmpty()
                .isEqualToIgnoringWhitespace(
                        objectMapper.writeValueAsString(singletonList(new BoatResponse(1L, "Name")))
                );
    }

    @Test
    public void shouldGetBoat_thenReturn200() throws Exception {
        long id = 1L;
        when(boatService.getBoatById(id))
                .thenReturn(factoryBoatDomain(id));

        final ResultActions resultPerform = mvc.perform(get("/api/boats/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        final var actualResponseBodyAsString = resultPerform
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(actualResponseBodyAsString)
                .isNotEmpty()
                .isEqualToIgnoringWhitespace(
                        objectMapper.writeValueAsString(factoryBoatDomain(id))
                );
    }

    @Test
    public void shouldGetBoat_thenReturn400() throws Exception {
        when(boatService.getBoatById(any()))
                .thenThrow(new ResourceNotFoundException("Not found boat"));

        final ResultActions resultPerform = mvc.perform(get("/api/boats/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        final var actualResponseBodyAsString = resultPerform
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(actualResponseBodyAsString)
                .isNotEmpty()
                .isEqualToIgnoringWhitespace(
                        objectMapper.writeValueAsString(new ErrorResponse("Not found boat"))
                );
    }

    @Test
    public void shouldUpdateBoat_thenReturn200() throws Exception {
        long id = 1L;
        when(boatService.updateBoat(any(), any()))
                .thenReturn(factoryBoatDomain(id));

        final ResultActions resultPerform = mvc.perform(put("/api/boats/{id}", 1L)
                .content(objectMapper.writeValueAsString(new BoatRequest("name", "description of boat")))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        final var actualResponseBodyAsString = resultPerform
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(actualResponseBodyAsString)
                .isNotEmpty()
                .isEqualToIgnoringWhitespace(
                        objectMapper.writeValueAsString(factoryBoatDomain(id))
                );
    }

    @Test
    public void shouldUpdateBoat_thenReturn500() throws Exception {
        when(boatService.updateBoat(any(), any()))
                .thenThrow(new RuntimeException("Error connection BD"));

        final ResultActions resultPerform = mvc.perform(put("/api/boats/{id}", 1L)
                .content(objectMapper.writeValueAsString(new BoatRequest("name", "description of boat")))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        final var actualResponseBodyAsString = resultPerform
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(actualResponseBodyAsString)
                .isNotEmpty()
                .isEqualToIgnoringWhitespace(
                        objectMapper.writeValueAsString(new ErrorResponse("Error connection BD"))
                );
    }

    @Test
    public void shouldUpdateBoatEmpty_thenReturn400() throws Exception {

        final ResultActions resultPerform = mvc.perform(put("/api/boats/{id}", 1L)
                .content(objectMapper.writeValueAsString(new BoatRequest("", "")))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        final var actualResponseBodyAsString = resultPerform
                .andReturn()
                .getResponse()
                .getContentAsString();

        final var errorResponse = objectMapper.readValue(actualResponseBodyAsString, ErrorResponse.class);
        assertThat(errorResponse.getMessages())
                .isNotEmpty()
                .containsExactlyInAnyOrder("Description may not be blank", "Name may not be blank");
    }


    private Boat factoryBoatDomain(Long id) {
        Boat boat = new Boat();
        boat.setId(id);
        boat.setName("Name");
        boat.setDescription("The characteristics of boat");
        return boat;
    }

}

