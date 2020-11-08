package engine.service;

import engine.entity.Account;

public interface AccountService {

    Account findByEmail(String email);

    Account save(Account account);
}