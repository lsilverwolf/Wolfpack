package com.mobpro.wolfpack;

/**
 * Created by rachel on 9/18/13.
 */
public class Score {
    private long id;
    private Integer score;
    private Integer timestamp;

    public Score(long id, Integer score, Integer timestamp) {
        this.id = id;
        this.score = score;
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", name='" + score  + '\'' +
                ", contents='" + timestamp + '\'' +
                '}';
    }
}
