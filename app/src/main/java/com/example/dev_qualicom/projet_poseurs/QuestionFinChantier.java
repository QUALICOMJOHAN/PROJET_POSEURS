package com.example.dev_qualicom.projet_poseurs;

public class QuestionFinChantier {

    private int num;
    private String question;
    private String reponse;

    public QuestionFinChantier(int num, String question) {
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

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }
}
