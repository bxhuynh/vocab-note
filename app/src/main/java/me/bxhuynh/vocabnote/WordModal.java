package me.bxhuynh.vocabnote;

public class WordModal {
    private String word;
    private String meaning;
    private String soundlike;
    private int id;
    private int isStudying;

    public WordModal(int id, String word, String soundlike, String meaning, int isStudying) {
        this.id = id;
        this.word = word;
        this.soundlike = soundlike;
        this.meaning = meaning;
        this.isStudying = isStudying;
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
}
