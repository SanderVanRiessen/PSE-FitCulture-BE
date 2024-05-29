package app.controllers;

import app.models.User;
import app.repository.UserRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class MessageControllerTest {

    private String message;
    private boolean actual;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Given("message is {string}")
    public void messageIs(String message) {
        this.message = message;
    }

    @When("I enter the message \"THIS STRING IS 256 CHARACTERS xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\"")
    @WithUserDetails("admin@admin.nl")
    public void I_enter_the_message_a() throws Exception {
        JSONObject json = new JSONObject();
        json.put("message", message);
        json.put("receiverId", 1);
        MvcResult mvcResult =  this.mockMvc.perform(post("/message").contentType("application/json").content(json.toJSONString())).andReturn();
        actual = Boolean.parseBoolean(mvcResult.getResponse().getContentAsString());
    }

    @Then("The api returns {string}")
    public void theApiReturns(String answer) {
        Assertions.assertEquals(Boolean.parseBoolean(answer), actual);
    }

    private Long userId;

    @Given("user is {long}")
    public void userIs(Long id) {
        this.userId = id;
    }

    @When("A user sent a message to a other user which is not in the friendslist")
    @WithUserDetails("admin@admin.nl")
    public void aUserSentAMessageToAOtherUserWhichIsNotInTheFriendslist() throws Exception {
        JSONObject json = new JSONObject();
        json.put("message", "new");
        json.put("receiverId", userId);
        MvcResult mvcResult =  this.mockMvc.perform(post("/message").contentType("application/json").content(json.toJSONString())).andReturn();
        actual = Boolean.parseBoolean(mvcResult.getResponse().getContentAsString());
    }

    @Then("The api returns message {string}")
    public void theApiReturnsMessage(String message) {
        Assertions.assertEquals(Boolean.parseBoolean(message), actual);
    }


    @Given("valid message is {string}")
    public void validMessageIs(String message) {
        this.message = message;
    }

    @When("A user sent a valid message to other user")
    @WithUserDetails("admin@admin.nl")
    public void aUserSentAValidMessageToOtherUser() throws Exception {
        JSONObject json = new JSONObject();
        json.put("email", "newuser@pse.nl");
        json.put("name", "newUser");
        json.put("password", "newuser@pse.nl");
        mockMvc.perform(post("/public/register").contentType("application/json").content(json.toJSONString()));
        User user = userRepository.findByEmail("newuser@pse.nl").orElseThrow(() -> new IllegalArgumentException("no user found"));

        JSONObject jsonFriend = new JSONObject();
        jsonFriend.put("email", "newuser@pse.nl");
        mockMvc.perform(post("/friendinvite").contentType("application/json").content(jsonFriend.toJSONString()));

        JSONObject jsonMessage = new JSONObject();
        jsonMessage.put("message", message);
        jsonMessage.put("receiverId", user.getId());
        MvcResult mvcResult =  this.mockMvc.perform(post("/message").contentType("application/json").content(jsonMessage.toJSONString())).andReturn();
        actual = Boolean.parseBoolean(mvcResult.getResponse().getContentAsString());
    }

    @Then("The api returns the message object {string}")
    public void theApiReturnsTheMessageObject(String result) {
        Assertions.assertEquals(Boolean.parseBoolean(result), actual);
    }
}
