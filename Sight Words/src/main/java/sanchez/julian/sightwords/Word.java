package sanchez.julian.sightwords;

public class Word {
    public String getWord() {
        return word;
    }

    private String word;

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    private boolean isComplete;
    public Word(String word, boolean isComplete){
        this.word =word;
        this.isComplete =isComplete;
    }
}
