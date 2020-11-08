package engine.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Completion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Quiz quiz;

    @ManyToOne
    private Account account;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime completedAt;

    public Completion() {
    }

    public Completion(Quiz quiz, Account account) {
        this.quiz = quiz;
        this.account = account;
    }

    @JsonProperty("id")
    public Long getQuizId() {
        return quiz.getId();
    }

    @JsonProperty("completedAt")
    public LocalDateTime getCompletedAt() {
        return completedAt;
    }
}