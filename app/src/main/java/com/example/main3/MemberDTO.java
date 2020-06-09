package com.example.main3;

import java.io.Serializable;

//리뷰를 보여주기 위한 클래스
public class MemberDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String  Review_score, Review_time, Review_title, Review_contents,Review_user;



    public MemberDTO() {
    }

    public MemberDTO( String Review_score, String Review_time, String Review_title, String Review_contents, String Review_user) {
        this.Review_score = Review_score;
        this.Review_time = Review_time;
        this.Review_title = Review_title;
        this.Review_contents = Review_contents;
        this.Review_user = Review_user;
    }



    public float getReview_score() {
        return Float.parseFloat(Review_score);
    }

    public void setReview_score(String Review_score) {
        this.Review_score = Review_score;
    }

    public String getReview_time() {
        return Review_time;
    }

    public void setReview_time(String Review_time) {
        this.Review_time = Review_time;
    }

    public String getReview_title() {
        return Review_title;
    }

    public void setReview_title(String Review_title) {
        this.Review_title = Review_title;
    }

    public String getReview_contents() {
        return Review_contents;
    }

    public void setReview_contents(String Review_contents) { this.Review_contents = Review_contents; }

    public String getReview_user() {
        return Review_user;
    }

    public void setReview_user(String Review_user) { this.Review_user = Review_user; }



    @Override
    public String toString() {
        return "MemberDTO{" +
                ", Review_score='" + Review_score + '\'' +
                ", Review_time='" + Review_time + '\'' +
                ", Review_title='" + Review_title + '\'' +
                ", Review_contents=" + Review_contents +
                ", Review_user=" + Review_user +
                '}';
    }
}