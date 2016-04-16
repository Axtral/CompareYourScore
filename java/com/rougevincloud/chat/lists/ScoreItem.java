package com.rougevincloud.chat.lists;

public class ScoreItem {
    private int id;
    private ChallengeItem challenge;
    private UserItem user;
    private int score;

    public ScoreItem(int id, ChallengeItem challenge, UserItem user, int score) {
        this.id = id;
        this.challenge = challenge;
        this.user = user;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public ChallengeItem getChallenge() {
        return challenge;
    }

    public UserItem getUser() {
        return user;
    }

    public int getScore() {
        return score;
    }
}
