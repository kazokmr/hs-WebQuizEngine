package engine.service.impl;

import engine.entity.Account;
import engine.entity.Completion;
import engine.entity.Quiz;
import engine.repository.CompletionRepository;
import engine.service.CompletionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CompletionServiceImpl implements CompletionService {

    private final CompletionRepository repository;

    @Autowired
    public CompletionServiceImpl(CompletionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Quiz quiz, Account account) {
        Completion completion = new Completion(quiz, account);
        repository.save(completion);
    }

    @Override
    public Page<Completion> findAllByAccountWithPagination(Account account, Integer page) {
        Pageable paging = PageRequest.of(page, 10, Sort.by("completedAt").descending());
        return repository.findAllByAccount(account, paging);
    }
}