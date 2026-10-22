package com.example.accessing_data_jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.net.URI;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class AddressBookWebTests {

    @Autowired
    private MockMvc mvc;

    private String path(String url) {
        return URI.create(url).getPath();
    }

    @Test
    void createBookAndBuddy_thenViewListsBuddy() throws Exception {
        String bookUrl = mvc.perform(post("/api/addressbooks")
                        .contentType(MediaType.APPLICATION_JSON).content("{}"))
                        .andExpect(status().isCreated())
                        .andReturn().getResponse().getHeader("Location");

        String buddyUrl = mvc.perform(post("/api/buddies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Max\",\"address\":\"23 Bank Street\",\"phoneNumber\":\"613-501-4444\",\"email\":\"max@example.ca\"}"))
                        .andExpect(status().isCreated())
                        .andReturn().getResponse().getHeader("Location");

        mvc.perform(post(path(bookUrl) + "/buddies")
                    .contentType("text/uri-list").content(buddyUrl))
                .andExpect(status().isNoContent());

        mvc.perform(get(path(bookUrl) + "/buddies"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("Max")));

        String id = path(bookUrl).substring(path(bookUrl).lastIndexOf('/') + 1);
        mvc.perform(get("/addressbooks/" + id))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("23 Bank Street")));
    }

    @Test
    void buddyEmail_isShownOnAddressBookPage() throws Exception {
        String bookUrl = mvc.perform(post("/api/addressbooks")
                        .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getHeader("Location");

        String buddyUrl = mvc.perform(post("/api/buddies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Aidan\",\"address\":\"24 Bank Street\",\"phoneNumber\":\"343-444-5555\",\"email\":\"aidan@example.ca\"}"))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getHeader("Location");

        mvc.perform(post(path(bookUrl) + "/buddies")
                    .contentType("text/uri-list").content(buddyUrl))
                    .andExpect(status().isNoContent());

        String id = path(bookUrl).substring(path(bookUrl).lastIndexOf('/') + 1);
        mvc.perform(get("/addressbooks/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("aidan@example.ca")));
    }

    @Test
    void unknownAddressBook_returns404() throws Exception {
        mvc.perform(get("/addressbooks/99999")).andExpect(status().isNotFound());
    }
}
