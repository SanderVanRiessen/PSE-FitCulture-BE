package app.controllers;

import app.dtos.challenge.payload.AcceptChallengeRequestDTO;
import app.dtos.challenge.payload.CompleteChallengeRequestDTO;
import app.dtos.challenge.payload.CreateChallengeRequestDTO;
import app.dtos.challenge.response.ChallengeResponseDTO;
import app.services.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/challenges")
public class ChallengeController {

    @Autowired
    private ChallengeService challengeService;

    @Transactional
    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ChallengeResponseDTO> createChallenge(@RequestBody CreateChallengeRequestDTO request) {
        ChallengeResponseDTO response = challengeService.createChallenge(request);
        return ResponseEntity.ok(response);
    }

    @Transactional
    @PostMapping("/accept")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ChallengeResponseDTO> acceptChallenge(@RequestBody AcceptChallengeRequestDTO request) {
        ChallengeResponseDTO response = challengeService.acceptChallenge(request);
        return ResponseEntity.ok(response);
    }

    @Transactional
    @PostMapping("/complete")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ChallengeResponseDTO> completeChallenge(@RequestBody CompleteChallengeRequestDTO request) {
        ChallengeResponseDTO response = challengeService.completeChallenge(request);
        return ResponseEntity.ok(response);
    }

    @Transactional
    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<ChallengeResponseDTO>> getAllChallenges() {
        List<ChallengeResponseDTO> response = challengeService.getAllChallenges();
        return ResponseEntity.ok(response);
    }

    @Transactional
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ChallengeResponseDTO> getChallengeById(@PathVariable Long id) {
        ChallengeResponseDTO response = challengeService.getChallengeById(id);
        return ResponseEntity.ok(response);
    }
}
