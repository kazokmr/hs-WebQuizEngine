package engine.repository;

import engine.entity.Account;
import engine.entity.Completion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CompletionRepository extends PagingAndSortingRepository<Completion, Long> {
    Page<Completion> findAllByAccount(Account account, Pageable pageable);
}