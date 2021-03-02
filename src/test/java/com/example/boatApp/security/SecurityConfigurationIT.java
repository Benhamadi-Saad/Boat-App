package com.example.boatApp.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class SecurityConfigurationIT {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithAnonymousUser
    @DisplayName("When anonymous user try to call api side, should unauthorize, then return HttpStatus:401 ")
    @Test
    public void shouldUnauthorize_thenReturn401() throws Exception {
        mvc.perform(get("/api"))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(roles = "ADMIN")
    @DisplayName("When admin try to call api side, should forbid, then return HttpStatus:403 ")
    @Test
    public void shouldForbid_thenReturn403() throws Exception {
        mvc.perform(get("/api"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(roles = "USER")
    @DisplayName("When user try to call api, should permit, then return HttpStatus:200 ")
    @Test
    public void shouldPermit_thenReturn200() throws Exception {
        mvc.perform(get("/api/boats"))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @DisplayName("When anonymous user try to logout app side, should permit , then return HttpStatus:204 ")
    @Test
    public void shouldLogin_thenReturn200() throws Exception {
       mvc.perform(get("/logout"))
                .andExpect(status().isNoContent())
                .andReturn();
    }
}

