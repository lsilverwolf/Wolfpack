package com.mobpro.wolfpack;

/**
 * Created by rachel on 9/18/13.
 */
public class Score {
    private long id;
    private String score;
    private String timestamp;

    public Score(long id, String score, String timestamp) {
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

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
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
