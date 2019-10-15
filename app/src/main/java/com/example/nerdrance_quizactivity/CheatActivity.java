package com.example.nerdrance_quizactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * @EXTRA_ANSWER_IS_TRUE the key value for extra intents, used by Quiz Activity
 */
public class CheatActivity extends AppCompatActivity {

    private boolean mAnswerIsTrue;
    private static final String EXTRA_ANSWER_IS_TRUE = "com.example.nerdrance_quizactivity.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.example.nerdrance_quizactivity.answer_shown";
    private TextView mAnswerTextView;
    private Button mShowAnswerButton;

    /**
     *  newIntent()- static method to create Intent with extras CheatActivity
     *
     * @param packageContext
     * @param answerIsTrue put into the intent with private name using EXTRA_IS_TRUE constant.
     * @return
     */
    public static Intent newIntent(Context packageContext, boolean answerIsTrue){
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    public static boolean wasAnswerShown(Intent result){
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }


    /**
     * @param savedInstanceState
     * @getIntent() always returns the Intent that started the activity(QuizActivity)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);

        mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (mAnswerIsTrue){
                    mAnswerTextView.setText(R.string.true_button);
                } else{
                    mAnswerTextView.setText(R.string.false_button);
                }
                setAnswerShownResult(true);
            }
        });
    }

    /**
     * @param isAnswerShown
     * @setResult(int,Intent) CheatActivity sends result code and intent when user presses SHOW_ANSWER button.
     *
     **/
    private void setAnswerShownResult(boolean isAnswerShown){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

}
