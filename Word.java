public class Word {
    String kor;
    String eng;
    int presentCount;
    int incorrectAnswerCount;

    public Word(String eng, String kor) {
        super();
        this.eng = eng;
        this.kor = kor;
        this.presentCount = 0;
        this.incorrectAnswerCount = 0;
    }

    @Override
    public String toString() {
        return this.eng+" : "+this.kor;
    }

    @Override
    public int hashCode() {
        return this.eng.hashCode()+this.kor.hashCode();
    }

    public void increasePresentCount() {
        this.presentCount++;
    }

    public void increaseIncorrectAnswerCount() {
        this.incorrectAnswerCount++;
    }

    public int getPresentCount() {
        return presentCount;
    }

    public int getIncorrectAnswerCount() {
        return incorrectAnswerCount;
    }

    public boolean equals(Object object){
        if(object instanceof Word){
            Word w = (Word)object;
            return (this.eng.equals(w.eng)&& this.kor.equals(w.kor));
        }else {
            return super.equals(object);
        }
    }
    public void setKor(String kor){
        this.kor = kor;
    }


}