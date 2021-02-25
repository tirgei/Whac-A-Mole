package com.kokonetworks.theapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private Field field;
    private TextView tvLevel;
    private TextView tvScore;

    private Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        field = findViewById(R.id.field);
        tvLevel = findViewById(R.id.tvLevel);
        btnStart = findViewById(R.id.btnStart);
        tvScore = findViewById(R.id.tvScore);

        setEventListeners();
    }

    void setEventListeners(){
        btnStart.setOnClickListener(view -> {
            btnStart.setVisibility(View.GONE);
            tvScore.setVisibility(View.GONE);
            field.startGame();
        });

        field.setListener(listener);
    }

    private final Field.Listener listener = new Field.Listener() {

        @Override
        public void onGameEnded(int score) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    btnStart.setVisibility(View.VISIBLE);
                    showScore(score);
                }
            });
        }

        @Override
        public void updateScore(int score) {
            showScore(score);
        }

        @Override
        public void onLevelChange(int level) {
            tvLevel.setText(String.format(getString(R.string.level), level));
        }
    };

    private void showScore(int score) {
        if (tvScore.getVisibility() != View.VISIBLE) {
            tvScore.setVisibility(View.VISIBLE);
        }
        tvScore.setText(String.format(getString(R.string.your_score), score));
    }
}