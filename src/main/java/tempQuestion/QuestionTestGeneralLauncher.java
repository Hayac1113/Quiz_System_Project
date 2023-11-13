package tempQuestion;

import model.Question;

public class QuestionTestGeneralLauncher {

    public static void main(String[] args) {
        Question q = new Question();
        String f = q.toString();
        System.out.println(f);
    }
}
