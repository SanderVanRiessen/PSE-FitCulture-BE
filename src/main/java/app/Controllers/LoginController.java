package app.Controllers;

import app.Dtos.LoginResponse;
import app.Security.JWTToken;
import app.Dtos.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JWTToken jwtToken;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, JWTToken jwtToken) {
        this.authenticationManager = authenticationManager;
        this.jwtToken = jwtToken;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtToken.generateToken(authentication);

        LoginResponse loginResponse = new LoginResponse(jwt);


        return ResponseEntity.ok(loginResponse);
    }
}
