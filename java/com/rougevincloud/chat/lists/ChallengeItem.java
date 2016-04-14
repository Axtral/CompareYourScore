package com.rougevincloud.chat.lists;

public class ChallengeItem {
    private String img;
    private String title, desc;

    public ChallengeItem(String img, String title, String desc) {
        this.img = img;
        this.title = title;
        this.desc = desc;
    }

    public String getImg() {
        return img;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }
}
