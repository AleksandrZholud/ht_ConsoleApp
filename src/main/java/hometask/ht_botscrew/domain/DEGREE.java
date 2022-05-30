package hometask.ht_botscrew.domain;

import lombok.Getter;

@Getter
public enum DEGREE {

    ASSISTANT("assistant"), ASSOCIATE_PROFESSOR("associate_professor"), PROFESSOR("professor");

    private final String DEGREE;

    DEGREE(String degree) {
        DEGREE = degree;
    }

    @Override
    public String toString() {
        return DEGREE;
    }
}