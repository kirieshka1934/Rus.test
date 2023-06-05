package com.example.myapplication;

import java.util.List;

public class Question {
        public String text;
        public List<String> answers;
        public List<Integer> right;

    public String getText() {
        return text;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public List<Integer> getRight() {
        return right;
    }
}


