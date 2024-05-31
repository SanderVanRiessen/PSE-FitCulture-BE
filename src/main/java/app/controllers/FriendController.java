package app.controllers;

import app.dtos.MessageResponse;
import app.dtos.friend.payload.FriendInvite;
import app.dtos.friend.payload.UpdateFriendInvite;
import app.dtos.friend.response.GetUserWithFriends;
import app.dtos.friend.response.MapperFriend;
import app.models.Friend;
import app.models.FriendId;
import app.models.FriendRequestStatus;
import app.models.User;
import app.repository.FriendRepository;
import app.repository.UserRepository;
import app.services.CustomUserDetailsService;
import app.services.UserDetailsImpl;
import app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;

@RestController
public class FriendController {
    FriendRepository friendRepository;
    UserRepository userRepository;

    CustomUserDetailsService customUserDetailsService;
    MapperFriend mapper;

    @Autowired
    public FriendController (FriendRepository friendRepository, UserRepository userRepository, MapperFriend mapper, CustomUserDetailsService customUserDetailsService) {
        this.friendRepository = friendRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.customUserDetailsService = customUserDetailsService;
    }

    @GetMapping("/currentfriends")
    public ResponseEntity<?> currentFriends(Authentication auth) {
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        User currentUser = userRepository.getById(userDetails.getId());
        GetUserWithFriends serializedUser = mapper.toPlainUserWithFriends(currentUser);
        return ResponseEntity.ok(serializedUser);
    }

    @PostMapping("/friendinvite")
    public ResponseEntity<?> friendInvite(@RequestBody FriendInvite friendInvite) {
        User currentUser = customUserDetailsService.getCurrentUser();
        User friendUser = userRepository.findByEmail(friendInvite.getEmail()).orElseThrow(() -> new IllegalArgumentException("There is no user with this email"));

        Optional<Friend> friendExists = friendRepository.findByMyUserIdAndOtherUserId(currentUser, friendUser);
        if (friendExists.isPresent()){
            return ResponseEntity.badRequest().body(new MessageResponse("request already sent"));
        }

        Friend friendRequest = new Friend();
        friendRequest.setStatus(FriendRequestStatus.REQUESTED);
        friendRequest.setMyUserId(currentUser);
        friendRequest.setOtherUserId(friendUser);
        friendRepository.save(friendRequest);

        return ResponseEntity.ok(new MessageResponse("friend request is sent"));
    }

    @PutMapping("/friendinvite")
    public ResponseEntity<?> updateFriendInvite(@RequestBody UpdateFriendInvite updateFriendInvite) {
        User currentUser = customUserDetailsService.getCurrentUser();

        boolean statusDoesNotExists = Arrays.stream(FriendRequestStatus.values()).noneMatch((e) -> e.name().equals(updateFriendInvite.getStatus()));
        if (statusDoesNotExists) {
            return ResponseEntity.badRequest().body(new MessageResponse("status does not exists"));
        }

        User otherUser = userRepository.getById(updateFriendInvite.getFriendId());

        Friend friendRequest = friendRepository.findByMyUserIdAndOtherUserId(currentUser, otherUser).orElseThrow(() -> new IllegalArgumentException("Friend request does not exists"));

        FriendRequestStatus status = FriendRequestStatus.valueOf(updateFriendInvite.getStatus());
        friendRequest.setStatus(status);
        friendRepository.saveAndFlush(friendRequest);

        return ResponseEntity.ok(new MessageResponse("friend request status is changed to " + status));
    }
}
