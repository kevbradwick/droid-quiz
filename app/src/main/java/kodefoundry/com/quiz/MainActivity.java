package kodefoundry.com.quiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Question[] questions = new Question[]{
            new Question(R.string.question_1, true),
            new Question(R.string.question_2, true),
            new Question(R.string.question_3, false),
            new Question(R.string.question_4, false),
            new Question(R.string.question_5, true),
            new Question(R.string.question_6, true),
            new Question(R.string.question_7, false),
            new Question(R.string.question_8, true),
            new Question(R.string.question_9, true),
            new Question(R.string.question_10, false),
    };

    private int currentQuestionIndex = 0;

    // first question is always at index 0
    private Question question = questions[currentQuestionIndex];

    private TextView textView;

    private void navigateQuestion(boolean previous) {
        // increment the index or return to zero if out of bounds
        currentQuestionIndex = previous ? currentQuestionIndex - 1 : currentQuestionIndex + 1;
        if (currentQuestionIndex == 10 || currentQuestionIndex < 0) {
            currentQuestionIndex = 0;
        }
        question = questions[currentQuestionIndex];
        textView.setText(question.getStringResourceId());

        Log.d(TAG, "Displaying question " + String.valueOf(currentQuestionIndex));
    }

    private void navigateQuestion() {
        navigateQuestion(false);
    }

    private void checkAnswer(boolean userAnswer) {
        int message = R.string.answer_correct;
        if (userAnswer != question.getAnswer()) {
            message = R.string.answer_incorrect;
        }
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // clicking on the text will navigate to the next question
        textView = (TextView) findViewById(R.id.question_text_field);
        textView.setText(question.getStringResourceId());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateQuestion();
            }
        });

        // answer buttons
        Button trueButton = (Button) findViewById(R.id.true_button);
        Button falseButton = (Button) findViewById(R.id.false_button);

        // navigation buttons
        ImageButton nextButton = (ImageButton) findViewById(R.id.button_next);
        ImageButton prevButton = (ImageButton) findViewById(R.id.button_previous);

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateQuestion();
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateQuestion(true);
            }
        });
    }
}
