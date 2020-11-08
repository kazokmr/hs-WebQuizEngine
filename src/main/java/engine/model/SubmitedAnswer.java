package engine.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubmitedAnswer {

    @JsonProperty
    private Integer[] answer;

    public Integer[] getAnswer() {
        return answer;
    }

    public void setAnswer(Integer[] answer) {
        this.answer = answer == null ? new Integer[0] : answer;
    }
}