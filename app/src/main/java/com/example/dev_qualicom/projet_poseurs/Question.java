package com.example.dev_qualicom.projet_poseurs;

public class Question {

    private int num;
    private String question;
    private boolean reponse;

    public Question(int num, String question) {
        this.num = num;
        this.question = question;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isReponse() {
        return reponse;
    }

    public void setReponse(boolean reponse) {
        this.reponse = reponse;
    }
}
