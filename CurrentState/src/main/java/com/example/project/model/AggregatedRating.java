package com.example.project.model;

public class AggregatedRating {

    private String oneStar;
    private String twoStar;
    private String threeStar;
    private String fourStar;
    private String fiveStar;
    private String aggregatingRating;

    public AggregatedRating(String oneStar, String twoStar, String threeStar, String fourStar, String fiveStar, String aggregatingRating) {
        this.oneStar = oneStar;
        this.twoStar = twoStar;
        this.threeStar = threeStar;
        this.fourStar = fourStar;
        this.fiveStar = fiveStar;
        this.aggregatingRating = aggregatingRating;
    }


    public String getOneStar() {
        return oneStar;
    }

    public void setOneStar(String oneStar) {
        this.oneStar = oneStar;
    }

    public String getTwoStar() {
        return twoStar;
    }

    public void setTwoStar(String twoStar) {
        this.twoStar = twoStar;
    }

    public String getThreeStar() {
        return threeStar;
    }

    public void setThreeStar(String threeStar) {
        this.threeStar = threeStar;
    }

    public String getFourStar() {
        return fourStar;
    }

    public void setFourStar(String fourStar) {
        this.fourStar = fourStar;
    }

    public String getFiveStar() {
        return fiveStar;
    }

    public void setFiveStar(String fiveStar) {
        this.fiveStar = fiveStar;
    }

    public String getAggregatingRating() {
        return aggregatingRating;
    }

    public void setAggregatingRating(String media) {
        this.aggregatingRating = media;
    }


}
