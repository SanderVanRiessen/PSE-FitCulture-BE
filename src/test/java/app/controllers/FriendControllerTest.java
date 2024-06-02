package app.controllers;

import app.models.Friend;
import app.repository.FriendRepository;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FriendControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    FriendRepository friendRepository;

    @BeforeEach
    void setup() throws Exception {
        JSONObject json = new JSONObject();
        json.put("email", "newuser@pse.nl");
        json.put("name", "newUser");
        json.put("password", "newuser@pse.nl");
        mockMvc.perform(post("/public/register").contentType("application/json").content(json.toJSONString()));
        JSONObject secondJson = new JSONObject();
        secondJson.put("email", "second@pse.nl");
        secondJson.put("name", "newUser");
        secondJson.put("password", "newuser@pse.nl");
        mockMvc.perform(post("/public/register").contentType("application/json").content(secondJson.toJSONString()));
    }

    @Test
    @WithUserDetails("admin@admin.nl")
    void sendFriendRequest() throws Exception {
        JSONObject json = new JSONObject();
        json.put("email", "second@pse.nl");
        mockMvc.perform(post("/friendinvite").contentType("application/json").content(json.toJSONString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("friend request is sent")));
    }

    @Test
    @WithUserDetails("admin@admin.nl")
    void cannotSendRequestTwice() throws Exception {
        JSONObject json = new JSONObject();
        json.put("email", "newuser@pse.nl");
        mockMvc.perform(post("/friendinvite").contentType("application/json").content(json.toJSONString()));
        mockMvc.perform(post("/friendinvite").contentType("application/json").content(json.toJSONString()))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message", is("request already sent")));
    }

    @Test
    @WithUserDetails("admin@admin.nl")
    void acceptFriendRequest() throws Exception {
        JSONObject thirdJson = new JSONObject();
        thirdJson.put("email", "newuser@pse.nl");
        mockMvc.perform(post("/friendinvite").contentType("application/json").content(thirdJson.toJSONString()));
        Friend friend = friendRepository.findByOtherUserId_Email("newuser@pse.nl");
        JSONObject json = new JSONObject();
        json.put("friendId", friend.getOtherUserId().getId());
        json.put("status", "ACCEPTED");
        mockMvc.perform(put("/friendinvite").contentType("application/json").content(json.toJSONString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("friend request status is changed to ACCEPTED")));
    }

    @Test
    @WithUserDetails("admin@admin.nl")
    void declineFriendRequest() throws Exception {
        JSONObject thirdJson = new JSONObject();
        thirdJson.put("email", "newuser@pse.nl");
        mockMvc.perform(post("/friendinvite").contentType("application/json").content(thirdJson.toJSONString()));
        Friend friend = friendRepository.findByOtherUserId_Email("newuser@pse.nl");
        JSONObject json = new JSONObject();
        json.put("friendId", friend.getOtherUserId().getId());
        json.put("status", "DECLINED");
        mockMvc.perform(put("/friendinvite").contentType("application/json").content(json.toJSONString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("friend request status is changed to DECLINED")));
    }

    @Test
    @WithUserDetails("admin@admin.nl")
    void currentFriends() throws Exception {
        mockMvc.perform(get("/currentfriends").contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Admin")));
    }
}
