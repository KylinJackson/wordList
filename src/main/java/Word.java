public class Word {
    private String word;
    private String trans;
    private String phonetic;

    Word(){

    }
    public Word(String word, String trans, String phonetic) {
        this.word = word;
        this.trans = trans;
        this.phonetic = phonetic;
    }

    void setWord(String word) {
        this.word = word;
    }

    void setTrans(String trans) {
        this.trans = trans;
    }

    void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    String getWord() {
        return word;
    }

    String getTrans() {
        return trans;
    }

    String getPhonetic() {
        return phonetic;
    }
}
