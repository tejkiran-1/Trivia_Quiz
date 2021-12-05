package com.example.triviaquiz.data;

import com.example.triviaquiz.model.Question;

import java.util.ArrayList;

public interface AnswerListAsyncResponse {
    void processFinished(ArrayList<Question> questionArrayList);
}
