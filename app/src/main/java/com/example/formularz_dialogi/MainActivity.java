package com.example.formularz_dialogi;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText etWeight, etHeight;
    private RadioGroup rgGender;
    private Button btnCalculate;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.etWeight);
        etHeight = findViewById(R.id.etHeight);
        rgGender = findViewById(R.id.rgGender);
        btnCalculate = findViewById(R.id.btnCalculate);
        tvResult = findViewById(R.id.tvResult);

        btnCalculate.setOnClickListener(v -> {
            if (validateForm()) {
                calculateBMI();
            }
        });
    }

    private boolean validateForm() {
        String weightStr = etWeight.getText().toString().trim();
        if (weightStr.isEmpty()) {
            etWeight.setError("Pole waga nie może być puste!");
            etWeight.requestFocus();
            return false;
        }

        double weight = Double.parseDouble(weightStr);
        if (weight == 0) {
            etWeight.setError("Waga nie może wynosić zero!");
            etWeight.requestFocus();
            return false;
        }

        String heightStr = etHeight.getText().toString().trim();
        if (heightStr.isEmpty()) {
            etHeight.setError("Pole wzrost nie może być puste!");
            etHeight.requestFocus();
            return false;
        }

        int height = Integer.parseInt(heightStr);
        if (height == 0) {
            etHeight.setError("Wzrost nie może wynosić zero!");
            etHeight.requestFocus();
            return false;
        }

        if (height > 300) {
            etHeight.setError("Wzrost nie może przekraczać 300 cm!");
            etHeight.requestFocus();
            return false;
        }

        if (rgGender.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Wybierz płeć!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void calculateBMI() {
        double weight = Double.parseDouble(etWeight.getText().toString().trim());
        double heightCm = Double.parseDouble(etHeight.getText().toString().trim());
        double heightM = heightCm / 100.0;

        double bmi = weight / (heightM * heightM);
        String category = getBMICategory(bmi);

        tvResult.setText(String.format(Locale.getDefault(), "Twój wynik BMI: %.2f\nKategoria: %s", bmi, category));
    }

    private String getBMICategory(double bmi) {
        if (bmi < 18.5) {
            return "Niedowaga";
        } else if (bmi < 25.0) {
            return "Waga prawidłowa";
        } else if (bmi < 30.0) {
            return "Nadwaga";
        } else {
            return "Otyłość";
        }
    }
}
