package com.example.triviaquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.triviaquiz.controller.AppController;
import com.example.triviaquiz.data.AnswerListAsyncResponse;
import com.example.triviaquiz.data.QuestionBank;
import com.example.triviaquiz.model.Question;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView questTxt, counterTxt;
    private ImageButton prevBtn, nextBtn;
    private Button falseBtn, trueBtn;
    private int currentQuestIndex = 0;
    private List<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questTxt = (TextView)findViewById(R.id.questionText);
        counterTxt = findViewById(R.id.counter_text);
        prevBtn = findViewById(R.id.prev_btn);
        nextBtn = findViewById(R.id.next_btn);
        falseBtn = findViewById(R.id.false_btn);
        trueBtn = findViewById(R.id.true_btn);


        nextBtn.setOnClickListener(this);
        prevBtn.setOnClickListener(this);
        falseBtn.setOnClickListener(this);
        trueBtn.setOnClickListener(this);




        questionList = new QuestionBank().getQuestions(new AnswerListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {
                questTxt.setText(questionArrayList.get(currentQuestIndex).getAnswer());
                counterTxt.setText(currentQuestIndex+" /"+questionArrayList.size());
                Log.d("Inside","processFinished: "+questionArrayList);
            }
        });
        //Log.d("Main","onCreate: "+questionList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.prev_btn:
                if (currentQuestIndex > 0){
                    currentQuestIndex--;
                    updateQuestion();
                }
                break;
            case R.id.next_btn:
                currentQuestIndex = (currentQuestIndex+1) % questionList.size();
                updateQuestion();
                break;
            case R.id.true_btn:
                updateQuestion();
                checkAnswer(true);
                break;
            case R.id.false_btn:
                updateQuestion();
                checkAnswer(false);
                break;
        }
    }

    private void fadeView(){
        CardView cardViiew = findViewById(R.id.cardView);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.f);
        alphaAnimation.setDuration(350);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        cardViiew.setAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardViiew.setCardBackgroundColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardViiew.setCardBackgroundColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }



    private void shakeAnime(){
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake_anime);
        CardView cardViiew = findViewById(R.id.cardView);
        cardViiew.setVisibility(View.VISIBLE);
        cardViiew.setAnimation(shake);
        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardViiew.setCardBackgroundColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardViiew.setCardBackgroundColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    private void updateQuestion() {
        questTxt.setText(questionList.get(currentQuestIndex).getAnswer());
        counterTxt.setText(currentQuestIndex+" /"+questionList.size());
    }

    private void checkAnswer(boolean choice) {
        boolean answerIsTrue = questionList.get(currentQuestIndex).isAnswerTrue();
        if (choice == answerIsTrue){
            fadeView();
            Toast.makeText(this,"Correct Answer",Toast.LENGTH_SHORT).show();
        }
        else {
            shakeAnime();
            Toast.makeText(this, "Incorrect Answer", Toast.LENGTH_SHORT).show();
        }
    }





}