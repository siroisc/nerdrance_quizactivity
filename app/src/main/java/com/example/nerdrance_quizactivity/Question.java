package com.example.nerdrance_quizactivity;

public class Question {
    private int mTextRestID;
    private boolean mAnswerTrue;

    public Question(int textResId, boolean answerTrue){
        mTextRestID = textResId;
        mAnswerTrue = answerTrue;
    }

    public void setTextRestID(int textRestID) {
        mTextRestID = textRestID;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public int getTextResId() {
        return mTextRestID;
    }
}

