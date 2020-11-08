package engine.entity;

import javax.persistence.*;

@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer answer;

    @ManyToOne
    private Quiz quiz;

    public Answer() {
    }

    public Answer(Integer answer) {
        this.answer = answer;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public Integer getAnswer() {
        return answer;
    }
}