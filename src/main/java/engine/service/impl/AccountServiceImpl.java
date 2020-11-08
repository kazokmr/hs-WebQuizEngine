package engine.service.impl;

import engine.entity.Account;
import engine.repository.AccountRepository;
import engine.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    @Autowired
    public AccountServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public Account findByEmail(String email) {
        return repository.findByEmail(email).orElse(null);
    }

    @Override
    public Account save(Account account) {
        if (findByEmail(account.getEmail()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        account.encodePassword();
        return repository.save(account);
    }
}