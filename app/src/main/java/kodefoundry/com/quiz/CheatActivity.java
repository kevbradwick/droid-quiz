package kodefoundry.com.quiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String TAG = CheatActivity.class.getSimpleName();
    private static final String PACKAGE_NAME = CheatActivity.class.getPackage().toString();
    private static final String EXTRA_ANSWER_IS_TRUE = PACKAGE_NAME + "answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = PACKAGE_NAME + "answer_shown";
    private static final String ANSWER_TEXT = PACKAGE_NAME + "answer_text";

    private boolean answerIsTrue;

    private int answerText;

    /**
     * Convenience method to create a new intent for this activity with the
     * required extras.
     *
     * @param packageContext the activity context
     * @param answerIsTrue   the answer to the question
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

        TextView apiLevel = (TextView) findViewById(R.id.api_version_text);
        if (apiLevel != null) {
            String versionLine = getString(R.string.api_version, Build.VERSION.SDK_INT);
            apiLevel.setText(versionLine);
        }

        // the ui elements on the layout
        final TextView textView = (TextView) findViewById(R.id.answer_text_view);
        final Button showAnswer = (Button) findViewById(R.id.show_answer_button);

        if (savedInstanceState != null) {
            int resourceText = savedInstanceState.getInt(ANSWER_TEXT);
            if (resourceText != 0) {
                textView.setText(resourceText);
                setAnswerShownResult(true);
            }
        }

        showAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Show answer button clicked");
                answerText = answerIsTrue ? R.string.true_button : R.string.false_button;
                textView.setText(answerText);
                setAnswerShownResult(true);

                int cx = showAnswer.getWidth() / 2;
                int cy = showAnswer.getHeight() / 2;
                float radius = showAnswer.getWidth();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Animator anim = ViewAnimationUtils.createCircularReveal(showAnswer, cx, cy, radius, 0);
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            showAnswer.setVisibility(View.INVISIBLE);
                        }
                    });
                    anim.start();
                } else {
                    showAnswer.setVisibility(View.INVISIBLE);
                }
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (answerText != 0) {
            outState.putInt(ANSWER_TEXT, answerText);
        }
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
