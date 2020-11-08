package engine.service;

import engine.entity.Account;
import engine.entity.Completion;
import engine.entity.Quiz;
import org.springframework.data.domain.Page;

public interface CompletionService {

    void save(Quiz quiz, Account account);

    Page<Completion> findAllByAccountWithPagination(Account account, Integer page);
}