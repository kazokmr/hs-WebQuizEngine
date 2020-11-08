package engine.entity;

import javax.persistence.*;

@Entity
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String option;

    @ManyToOne
    private Quiz quiz;

    public Option() {
    }

    public Option(String option) {
        this.option = option;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public String getOption() {
        return option;
    }
}