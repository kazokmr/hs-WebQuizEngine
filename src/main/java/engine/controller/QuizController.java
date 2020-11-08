package engine.controller;

import engine.entity.Account;
import engine.entity.Completion;
import engine.entity.Quiz;
import engine.model.Result;
import engine.model.SubmitedAnswer;
import engine.service.AccountService;
import engine.service.CompletionService;
import engine.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

@RestController
public class QuizController {

    private final QuizService quizService;
    private final AccountService accountService;
    private final CompletionService completionService;

    @Autowired
    public QuizController(QuizService quizService, AccountService accountService, CompletionService completionService) {
        this.quizService = quizService;
        this.accountService = accountService;
        this.completionService = completionService;
    }

    @PostMapping("/api/quizzes")
    public Quiz createNewQuiz(@Valid @RequestBody Quiz quiz,
                              @AuthenticationPrincipal User user) {
        try {
            Account account = getUserAccount(user);
            quiz.setAccount(account);
            return quizService.saveQuiz(quiz);
        } catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/api/quizzes/{id}")
    public Quiz getQuizById(@PathVariable Long id) {
        return quizService.getQuizById(id);
    }


    @GetMapping("/api/quizzes")
    public Page<Quiz> getAllQuizzes(@RequestParam(defaultValue = "0") Integer page) {
        return quizService.getQuizzesByPage(page);
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public Result postSolve(@PathVariable Long id,
                            @RequestBody(required = false) SubmitedAnswer answer,
                            @AuthenticationPrincipal User user) {
        Result result = quizService.getResult(id, answer);
        if (result.isSuccess()) {
            Quiz quiz = quizService.getQuizById(id);
            Account account = getUserAccount(user);
            completionService.save(quiz, account);
        }
        return result;
    }

    @DeleteMapping("/api/quizzes/{id}")
    public ResponseEntity<String> deleteQuiz(@PathVariable Long id,
                                             @AuthenticationPrincipal User user) {
        Account account = getUserAccount(user);
        quizService.remove(id, account);
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/api/quizzes/completed")
    public Page<Completion> getCompletedQuiz(@RequestParam(defaultValue = "0") Integer page,
                                             @AuthenticationPrincipal User user) {
        Account account = getUserAccount(user);
        return completionService.findAllByAccountWithPagination(account, page);
    }

    private Account getUserAccount(User user) {
        return accountService.findByEmail(user.getUsername());
    }
}