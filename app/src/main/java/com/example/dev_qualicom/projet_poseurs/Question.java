package com.example.dev_qualicom.projet_poseurs;

import java.io.Serializable;

public class Question implements Serializable {

    private int num;
    private String question;
    private boolean reponse;
    private boolean attendu;

    public Question(int num, String question, boolean attendu) {
        this.num = num;
        this.question = question;
        this.attendu = attendu;
    }

    public boolean isEqual(){
        if(attendu == reponse){
            return true;
        }else{
            return false;
        }
    }

    public boolean isAttendu() {
        return attendu;
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
