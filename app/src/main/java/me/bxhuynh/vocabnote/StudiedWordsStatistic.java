package me.bxhuynh.vocabnote;

public class StudiedWordsStatistic {
    private int month;
    private int year;
    private  int numberOfWords;

    public StudiedWordsStatistic(int month,int year, int numberOfWords) {
        this.month = month;
        this.year = year;
        this.numberOfWords = numberOfWords;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getNumberOfWords() {
        return numberOfWords;
    }

    public void setNumberOfWords(int numberOfWords) {
        this.numberOfWords = numberOfWords;
    }
}
