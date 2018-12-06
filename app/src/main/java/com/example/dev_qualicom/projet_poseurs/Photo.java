package com.example.dev_qualicom.projet_poseurs;

public class Photo {

    String filePath;
    String titre;

    public Photo(String titre) {
        this.titre = titre;
        this.filePath = "";
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }
}
