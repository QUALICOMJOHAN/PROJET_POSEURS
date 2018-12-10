package com.example.dev_qualicom.projet_poseurs;

public class Photo {

    String filePath;
    String titre;
    String prefix_photo;

    public Photo(String titre, String prefix_photo) {
        this.titre = titre;
        this.prefix_photo = prefix_photo;
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

    public String getPrefix_photo() {
        return prefix_photo;
    }

    public void setPrefix_photo(String prefix_photo) {
        this.prefix_photo = prefix_photo;
    }
}
