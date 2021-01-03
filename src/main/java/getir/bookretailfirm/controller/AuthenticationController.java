package getir.bookretailfirm.controller;

import getir.bookretailfirm.request.AuthenticationRequest;
import getir.bookretailfirm.response.AuthenticationResponse;
import getir.bookretailfirm.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping
    public AuthenticationResponse getToken(@RequestBody @Valid AuthenticationRequest authenticationRequest) {
        return authenticationService.getToken(authenticationRequest);
    }
}
