package com.serafinebot.dint.game_2048.data;

public class Score {
    public long id;
    public int score;
    public String player;
    public String date;

    public Score() {
    }

    public Score(int score, String player) {
        this.score = score;
        this.player = player;
    }
}
