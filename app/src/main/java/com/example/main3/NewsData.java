package com.example.main3;




//package com.imaec.forblog;


public class NewsData {

    private String title;
    private String content;
    private String resId;

    public NewsData(String my_title, String my_body, String my_imgUrl) {
        this.title = my_title;
        this.content = my_body;
        this.resId = my_imgUrl;
    }

    public String getNewsTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNewsContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNewsResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }


}