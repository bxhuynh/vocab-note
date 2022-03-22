package me.bxhuynh.vocabnote;

public class WordModal {
    private String word;
    private String meaning;
    private String soundlike;
    private int id;
    private int isStudying;
    private  int createdDate;
    private int createdYear;


    public WordModal(int id, String word, String soundlike, String meaning, int isStudying, int createdDate, int createdYear) {
        this.id = id;
        this.word = word;
        this.soundlike = soundlike;
        this.meaning = meaning;
        this.isStudying = isStudying;
        this.createdDate = createdDate;
        this.createdYear = createdYear;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getSoundlike() {
        return soundlike;
    }

    public void setSoundlike(String soundlike) {
        this.soundlike = soundlike;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsStudying() {
        return isStudying;
    }

    public void setIsStudying(int isStudying) {
        this.isStudying = isStudying;
    }


    public int getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(int createdDate) {
        this.createdDate = createdDate;
    }

    public int getCreatedYear() {
        return createdYear;
    }

    public void setCreatedYear(int createdYear) {
        this.createdYear = createdYear;
    }
}
