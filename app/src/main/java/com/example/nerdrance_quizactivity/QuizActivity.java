package com.example.nerdrance_quizactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * The type Quiz activity.
 */
public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;


    /**
     * @Question - Is a question object from the constructor in Question.java
     * @mQuestionBank Is an array of Question objects
     * @mCurrentIndex will keep count of how many times the accessor methods in Question
     */
    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    private int mCurrentIndex = 0;
    private boolean mIsCheater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "OnCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);


        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkAnswer(true);
            }
        });
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(QuizActivity.this, R.string.incorrect_toast,
                        Toast.LENGTH_SHORT).show();
                checkAnswer(false);
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                mIsCheater = false;
                updateQuestion();
            }
        });

        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener(){
            /**
             * @intent
             * @answerIsTrue
             * @onClick Creates an explicit intent to start CheatActivity by QuizActivity
             * @startActivity method call via ActivityManager to start CheatActivity
             * @startActivityForResult sends and receives back requestCode to CheatActivity
             */
            @Override
            public void onClick(View v){
                    boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                    Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
//                    startActivity(intent);
                    startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });

        updateQuestion();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode != Activity.RESULT_OK){
            return ;
        }

        if (requestCode == REQUEST_CODE_CHEAT){
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }




    /**
     * @mQuestionBank assigned to the int question that will be the mCurrentIndex
     *                in the array, and will display the text id in strings.xml
     * @mQuestionTextView is the text view object that will display the current question
     */
    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }



    /**
     * @param userPressedTrue checkAnswers accepts this variable to identify if user passed TRUE or FALSE
     *                        It is checked against the answer in current Question object, then it makes
     *                        a Toast that displays appropriate message.
     */
    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;

        if (mIsCheater) {
            messageResId = R.string.judgment_toast;
        } else{
            if(userPressedTrue == answerIsTrue){
                messageResId = R.string.correct_toast;
            } else{
                messageResId = R.string.incorrect_toast;
            }
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }
}


