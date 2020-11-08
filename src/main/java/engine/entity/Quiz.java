package engine.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import engine.model.Result;
import engine.model.SubmitedAnswer;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Quiz {

    @JsonProperty
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty
    @NotBlank(message = "Title is required.")
    private String title;

    @NotBlank(message = "Text is required.")
    private String text;

    @JsonProperty
    @NotNull(message = "Options is not null")
    @Size(min = 2, message = "Options should contain at least 2 items.")
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<Option> options;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<Answer> answer;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    private Account account;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.REMOVE)
    private List<Completion> completion;

    public Quiz() {
    }

    public Quiz(String title, String text, List<String> options, List<Integer> answer) {
        this.title = title;
        this.text = text;
        this.options = options.stream().map(Option::new).collect(Collectors.toList());
        this.answer = answer.stream().map(Answer::new).collect(Collectors.toList());
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String[] getOptions() {
        return options.stream().map(Option::getOption).toArray(String[]::new);
    }

    public void cascade() {
        options.forEach(o -> o.setQuiz(this));
        if (answer != null) {
            answer.forEach(a -> a.setQuiz(this));
        }
    }

    public Result getResult(SubmitedAnswer answer) {
        int[] correctAnswers = this.answer.stream().mapToInt(Answer::getAnswer).sorted().toArray();
        int[] submitAnswers = Arrays.stream(answer.getAnswer()).mapToInt(Integer::intValue).sorted().toArray();
        return Arrays.equals(correctAnswers, submitAnswers) ? Result.isCorrect() : Result.isWrong();
    }

    public boolean isAuthor(Account account) {
        return this.account.equals(account);
    }
}