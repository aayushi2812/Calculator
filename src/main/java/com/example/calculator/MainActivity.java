package com.example.calculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView output;
    private TextView history;
    private Button advance;
    private String currentInput = "";
    private double firstNumber = 0;
    private String operator = "";
    private boolean isNewOperation = true;
    private StringBuilder operationHistory = new StringBuilder();
    StringBuilder currentOperation = new StringBuilder();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        output = findViewById(R.id.output);
        history = findViewById(R.id.history);
        advance = findViewById(R.id.advance);
        advance.setOnClickListener(v -> toggleHistory());


        // Number Buttons
        findViewById(R.id.one).setOnClickListener(v -> appendNumber("1"));
        findViewById(R.id.two).setOnClickListener(v -> appendNumber("2"));
        findViewById(R.id.three).setOnClickListener(v -> appendNumber("3"));
        findViewById(R.id.four).setOnClickListener(v -> appendNumber("4"));
        findViewById(R.id.five).setOnClickListener(v -> appendNumber("5"));
        findViewById(R.id.six).setOnClickListener(v -> appendNumber("6"));
        findViewById(R.id.seven).setOnClickListener(v -> appendNumber("7"));
        findViewById(R.id.eight).setOnClickListener(v -> appendNumber("8"));
        findViewById(R.id.nine).setOnClickListener(v -> appendNumber("9"));
        findViewById(R.id.zero).setOnClickListener(v -> appendNumber("0"));

        // Operation Buttons
        findViewById(R.id.plus).setOnClickListener(v -> handleOperator("+"));
        findViewById(R.id.minus).setOnClickListener(v -> handleOperator("-"));
        findViewById(R.id.multiply).setOnClickListener(v -> handleOperator("*"));
        findViewById(R.id.divide).setOnClickListener(v -> handleOperator("/"));

        // Clear Button
        findViewById(R.id.clear).setOnClickListener(v -> clearCalculator());

        // Equals Button
        findViewById(R.id.equals).setOnClickListener(v -> calculateFinalResult());

    }

    private void toggleHistory() {
        if (history.getVisibility() == View.GONE) {
            System.out.println("in show history");
            history.setVisibility(View.VISIBLE);
            advance.setText(R.string.standard);
        } else {
            System.out.println("in hide history");
            history.setVisibility(View.GONE);
            advance.setText(R.string.advance);
        }
    }

    private void appendNumber(String number) {
        if (isNewOperation) {
            currentInput = ""; // Reset the input if starting a new operation
            isNewOperation = false;
        }
        currentInput += number;
        output.setText(currentInput); // Update display
    }

    private void handleOperator(String selectedOperator) {
        if (!currentInput.isEmpty()) {
            if (!operator.isEmpty()) {
                // Perform the previous operation before setting the new operator
                calculateIntermediateResult();
            } else {
                firstNumber = Double.parseDouble(currentInput); // Set the first number
            }
            currentOperation.append(currentInput).append(" ").append(selectedOperator).append(" ");
            operationHistory.append(currentInput).append(" ").append(selectedOperator).append(" ");
            operator = selectedOperator; // Update the operator
            isNewOperation = true; // Prepare for the next number
        }
    }

    private void calculateIntermediateResult() {
        double secondNumber = Double.parseDouble(currentInput); // Parse the current input
        switch (operator) {
            case "+":
                firstNumber += secondNumber;
                break;
            case "-":
                firstNumber -= secondNumber;
                break;
            case "*":
                firstNumber *= secondNumber;
                break;
            case "/":
                if (secondNumber != 0) {
                    firstNumber /= secondNumber;
                } else {
                    output.setText("Error");
                    clearCalculator();
                    return;
                }
                break;
        }
        output.setText(String.valueOf(firstNumber)); // Display intermediate result
    }

    private void calculateFinalResult() {
        if (!operator.isEmpty() && !currentInput.isEmpty()) {
            calculateIntermediateResult(); // Calculate the result of the last operation
            operationHistory.append(currentInput).append(" = ").append(firstNumber).append("\n");
            currentOperation.append(currentInput).append(" = ").append(firstNumber).append("\n");
            output.setText(currentOperation);
            history.setText(operationHistory.toString());
            operator = ""; // Clear the operator
            isNewOperation = true; // Reset for new operations
        }
    }

    private void clearCalculator() {
        firstNumber = 0;
        currentInput = "";
        operator = "";
        isNewOperation = true;
        currentOperation = new StringBuilder("");
        output.setText("0"); // Reset display
    }
}