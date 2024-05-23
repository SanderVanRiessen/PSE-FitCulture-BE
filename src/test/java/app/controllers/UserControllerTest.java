package app.controllers;

import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.core.Is.is;

@Transactional
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void searchUserByEmail() throws Exception {
        mockMvc.perform(get("/public/user?email=admin@admin.nl").contentType("application/json")).andExpect(status().isOk()).andExpect(jsonPath("$.name", is("Admin")));
    }

    @Test
    void sendFriendRequest() throws Exception {
        JSONObject json = new JSONObject();
        json.put("email", "newuser@pse.nl");
        mockMvc.perform(post("/public/friendinvite").contentType("application/json").content(json.toJSONString())).andExpect(status().isOk());
    }

    @Test
    void cannotSendRequestTwice() throws Exception {
        JSONObject json = new JSONObject();
        json.put("email", "newuser@pse.nl");
        mockMvc.perform(post("/public/friendinvite").contentType("application/json").content(json.toJSONString())).andExpect(status().isOk()).andExpect(jsonPath("$.message", is("request already sent")));
    }

    @Test
    void acceptFriendRequest() throws Exception {
        JSONObject json = new JSONObject();
        json.put("id", "1");
        json.put("status", "accept");
        mockMvc.perform(post("/public/friendRequest").contentType("application/json").content(json.toJSONString())).andExpect(status().isOk());
    }

    @Test
    void declineFriendRequest() throws Exception {
        JSONObject json = new JSONObject();
        json.put("id", "1");
        json.put("status", "decline");
        mockMvc.perform(post("/public/friendRequest").contentType("application/json").content(json.toJSONString())).andExpect(status().isOk());
    }

    @Test
    void currentFriends() throws Exception {
        mockMvc.perform(get("/public/currentfriends").contentType("application/json")).andExpect(status().isOk());
    }

}
