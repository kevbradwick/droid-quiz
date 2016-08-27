package kodefoundry.com.quiz;

public class Question {

    private int stringResourceId;

    private boolean answer;

    public Question(int stringResourceId, boolean answer) {
        this.stringResourceId = stringResourceId;
        this.answer = answer;
    }

    public int getStringResourceId() {
        return stringResourceId;
    }

    public boolean getAnswer() {
        return answer;
    }
}
