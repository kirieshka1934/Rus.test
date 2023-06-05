package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TestingActivity extends AppCompatActivity {

    public static final String TEST_NAME_ARG = "Testname";
    private TextView text;
    List<String> answerButtons = new ArrayList<String>();
    OneTest root;
    int index;
    int countRight=0;


    private CheckBox check1;

    private CheckBox check2;

    private CheckBox check3;

    private CheckBox check4;

    private CheckBox check5;

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testing);
        findElements();

        String str = getIntent().getStringExtra(TEST_NAME_ARG);
        loadQuestion(str);
        index = 0;
    }

    public void findElements() {
        text = (TextView) findViewById(R.id.textView);
        check1 = (CheckBox) findViewById(R.id.checkBox);
        check2 = (CheckBox) findViewById(R.id.checkBox2);
        check3 = (CheckBox) findViewById(R.id.checkBox3);
        check4 = (CheckBox) findViewById(R.id.checkBox4);
        check5 = (CheckBox) findViewById(R.id.checkBox5);
    }

    private void loadQuestion(String a) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/kirieshka1934/rus-test-info/main/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TestService service = retrofit.create(TestService.class);
        Call<OneTest> call = service.fetchOneTest(a);
        call.enqueue(new Callback<OneTest>() {
            @Override
            public void onResponse(Call<OneTest> call, Response<OneTest> response) {
                root = response.body();
                if (root == null){
                    Toast.makeText(TestingActivity.this, "Не получилось загрузить тест", Toast.LENGTH_LONG).show();
                    finish();
                    return;
                }
                showQuestion();
            }

            @Override
            public void onFailure(Call<OneTest> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(TestingActivity.this, "Не получилось загрузить тест", Toast.LENGTH_LONG).show();
                finish();
            }
        });


/*        OkHttpClient client = new OkHttpClient();
        String url = "https://raw.githubusercontent.com/kirieshka1934/rus-test-info/main/postanovkaudar.json";

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonData = response.body().string();


                Gson gson = new Gson();
                root = gson.fromJson(jsonData, OneTest.class);

                runOnUiThread(() -> {
                    showQuestion();
                });
            }
        });
*/

    }


    private boolean checkAnswer(boolean userAnswer, int number, List<Integer> rightAnswers) {
        if (userAnswer == true) {
            if (rightAnswers.contains(number))
                return true;
            else
                return false;
        } else {
            if (rightAnswers.contains(number))
                return false;
            else
                return true;
        }
    }

    public void nextQuestion(boolean isSkip) {
        List<Integer> rightAnswers = root.getQuestions().get(index).getRight();
        boolean isOk1 = checkAnswer(check1.isChecked(), 1, rightAnswers);
        boolean isOk2 = checkAnswer(check2.isChecked(), 2, rightAnswers);
        boolean isOk3 = checkAnswer(check3.isChecked(), 3, rightAnswers);
        boolean isOk4 = checkAnswer(check4.isChecked(), 4, rightAnswers);
        boolean isOk5 = checkAnswer(check5.isChecked(), 5, rightAnswers);

        if (!isSkip && isOk1 && isOk2 && isOk3 && isOk4 && isOk5) {
            countRight++;
        }


        index++;
        if (root.questions.size() == index) {
            new AlertDialog.Builder(this)
                    .setTitle("Результаты теста")
                    .setMessage(String.format("Правильных ответов %d из %d", countRight, root.questions.size()))
                    .setNeutralButton("Понятно", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            finish();
                        }
                    }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            finish();
                        }
                    }).create().show();
        } else showQuestion();

    }

    public void next(View view) {
        nextQuestion(false);
    }
    public void skip(View view) {
        nextQuestion(true);
    }

    public void exit(View view) {
        finish();
    }

    public void showQuestion(){
        Question question = root.getQuestions().get(index);
        check1.setText(question.getAnswers().get(0));
        check1.setChecked(false);
        if (question.getAnswers().size() > 1) {
            check2.setText(question.getAnswers().get(1));
            check2.setVisibility(View.VISIBLE);
        } else {
            check2.setVisibility(View.INVISIBLE);
        }
        check2.setChecked(false);
        if (question.getAnswers().size() > 2) {
            check3.setText(question.getAnswers().get(2));
            check3.setVisibility(View.VISIBLE);
        } else {
            check3.setVisibility(View.INVISIBLE);
        }
        check3.setChecked(false);
        if (question.getAnswers().size() > 3) {
            check4.setText(question.getAnswers().get(3));
            check4.setVisibility(View.VISIBLE);
        } else {
            check4.setVisibility(View.INVISIBLE);
        }
        check4.setChecked(false);
        if (question.getAnswers().size() > 4) {
            check5.setText(question.getAnswers().get(4));
            check5.setVisibility(View.VISIBLE);
        } else {
            check5.setVisibility(View.INVISIBLE);
        }
        check5.setChecked(false);
        text.setText(question.getText());
    }
}