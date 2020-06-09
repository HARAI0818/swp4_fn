package com.example.main3;




//package com.imaec.forblog;


public class Youtube_Data {

    private String title;
    private String content;
    private String resId;

    public Youtube_Data(String my_title, String my_link, String my_imgUrl) {
        this.title = my_title;
        this.content = my_link;
        this.resId = my_imgUrl;
    }

    public String getYoutubeTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYoutubeContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getYoutubeResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }


}
