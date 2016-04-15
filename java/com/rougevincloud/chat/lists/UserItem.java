package com.rougevincloud.chat.lists;

public class UserItem {
    private int id;
    private String pseudo;
    private String password;

    public UserItem(int id, String pseudo, String password) {
        this.id = id;
        this.pseudo = pseudo;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getPassword() {
        return password;
    }
}
