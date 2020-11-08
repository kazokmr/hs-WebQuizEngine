package engine.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {
    @JsonProperty
    private final boolean success;
    @JsonProperty
    private final String feedback;

    public Result(boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;
    }

    public static Result isCorrect() {
        return new Result(true, "Congratulations, you're right!");
    }

    public static Result isWrong() {
        return new Result(false, "Wrong answer! Please, try again.");
    }

    public boolean isSuccess() {
        return success;
    }

    public String getFeedback() {
        return feedback;
    }
}