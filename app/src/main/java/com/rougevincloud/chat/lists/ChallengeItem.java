package com.rougevincloud.chat.lists;

public class ChallengeItem {
    private int id;
    private String img;
    private String title, desc;

    public ChallengeItem(int id, String img, String title, String desc) {
        this.id = id;
        this.img = img;
        this.title = title;
        this.desc = desc;
    }

    public int getId() {
        return id;
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
