package com.graphaware.meetup.web;

import com.graphaware.reco.generic.result.Score;

public class RecommendationVO {

    private String uuid;
    private String item;
    private Score score;

    public RecommendationVO() {
    }

    public RecommendationVO(String uuid, String item, Score score) {
        this.uuid = uuid;
        this.item = item;
        this.score = score;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }
}
