package kodefoundry.com.quiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String TAG = CheatActivity.class.getSimpleName();
    private static final String PACKAGE_NAME = CheatActivity.class.getPackage().toString();
    private static final String EXTRA_ANSWER_IS_TRUE = PACKAGE_NAME + "answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = PACKAGE_NAME + "answer_shown";

    private boolean answerIsTrue;

    /**
     * Convenience method to create a new intent for this activity with the
     * required extras.
     *
     * @param packageContext the activity context
     * @param answerIsTrue the answer to the question
     * @return a new intent
     */
    static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        // obtain the answer to the question from the intent extras.
        answerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        // the ui elements on the layout
        final TextView textView = (TextView) findViewById(R.id.answer_text_view);
        final Button showAnswer = (Button) findViewById(R.id.show_answer_button);

        showAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Show answer button clicked");

                // display the answer
                if (answerIsTrue) {
                    textView.setText(R.string.true_button);
                } else {
                    textView.setText(R.string.false_button);
                }
                setAnswerShownResult(true);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, intent);
    }

    /**
     * Provide a static method to determine if the user cheated. This is placed
     * in this activity so that any other activity needing the result can use
     * this method rather than implementing their own.
     *
     * @param result the intent
     * @return true|false
     */
    static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }
}
