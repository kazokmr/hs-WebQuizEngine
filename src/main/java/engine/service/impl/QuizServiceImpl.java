package engine.service.impl;

import engine.entity.Account;
import engine.entity.Quiz;
import engine.model.Result;
import engine.model.SubmitedAnswer;
import engine.repository.QuizRepository;
import engine.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuizRepository repository;

    @Autowired
    public QuizServiceImpl(QuizRepository repository) {
        this.repository = repository;
    }

    @Override
    public Quiz saveQuiz(Quiz quiz) {
        quiz.cascade();
        return repository.save(quiz);
    }

    @Override
    public Quiz getQuizById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No quiz by id = " + id));
    }

    @Override
    public Page<Quiz> getQuizzesByPage(Integer page) {
        Pageable paging = PageRequest.of(page, 10);
        return repository.findAll(paging);
    }

    @Override
    public Result getResult(Long id, SubmitedAnswer answer) {
        return getQuizById(id).getResult(answer);
    }

    @Override
    public void remove(Long id, Account account) {
        Quiz quiz = getQuizById(id);
        if (!quiz.isAuthor(account)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    String.format("The quiz is not added by %s", account.getEmail()));
        }
        repository.delete(quiz);
    }
}