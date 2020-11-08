package engine.controller;

import engine.entity.Account;
import engine.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

@RestController
public class UserController {

    private final AccountService service;

    @Autowired
    public UserController(AccountService service) {
        this.service = service;
    }

    @PostMapping("/api/register")
    public Account registerUser(@Valid @RequestBody Account account) {
        try {
            return service.save(account);
        } catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}