package com.example.calculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {

    private TextView history;
    StringBuilder historyText = new StringBuilder();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        Log.d("App", "Second Activity -  onCreate");
        history = findViewById(R.id.history2);
        for(int i =0;i< CalculatorHistory.list.size();i++){
            historyText.append(CalculatorHistory.list.get(i)).append("\n");
        }
        history.setText(historyText.toString());
    }
}