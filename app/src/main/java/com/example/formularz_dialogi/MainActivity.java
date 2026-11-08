package com.example.formularz_dialogi;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private EditText etAddress, etPhone;
    private Button btnSubmit;
    private Spinner spPizza;
    private RadioGroup rgSize;
    private SeekBar sbQuantity;
    private TextView tvQuantityValue, tvSummary;
    private MaterialSwitch swSauce;
    private CheckBox cbTerms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etAddress = findViewById(R.id.etAddress);
        etPhone = findViewById(R.id.etPhone);
        btnSubmit = findViewById(R.id.btnSubmit);
        spPizza = findViewById(R.id.spPizza);
        rgSize = findViewById(R.id.rgSize);
        sbQuantity = findViewById(R.id.sbQuantity);
        tvQuantityValue = findViewById(R.id.tvQuantityValue);
        tvSummary = findViewById(R.id.tvSummary);
        swSauce = findViewById(R.id.swSauce);
        cbTerms = findViewById(R.id.cbTerms);

        setUpPizzaSpinner();
        setUpListeners();
        tvQuantityValue.setText(String.valueOf(sbQuantity.getProgress()));
    }

    private void setUpPizzaSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.pizza_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPizza.setAdapter(adapter);
    }

    private void setUpListeners() {
        cbTerms.setOnCheckedChangeListener((buttonView, isChecked) ->
                btnSubmit.setEnabled(isChecked));

        btnSubmit.setOnClickListener(v -> {
            if (validateForm()) {
                askForConfirmation();
            }
        });

        sbQuantity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvQuantityValue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        spPizza.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    tvSummary.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private boolean validateForm() {
        String address = etAddress.getText().toString().trim();
        if (address.isEmpty()) {
            etAddress.setError(getString(R.string.error_address));
            etAddress.requestFocus();
            return false;
        }

        String phone = etPhone.getText().toString().trim();
        if (!Patterns.PHONE.matcher(phone).matches()) {
            etPhone.setError(getString(R.string.error_phone));
            etPhone.requestFocus();
            return false;
        }

        if (spPizza.getSelectedItemPosition() == 0) {
            Toast.makeText(this, R.string.error_pizza, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (rgSize.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, R.string.error_size, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (sbQuantity.getProgress() == 0) {
            Toast.makeText(this, R.string.error_quantity, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void askForConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.confirm_title)
                .setMessage(R.string.confirm_message)
                .setPositiveButton(R.string.yes, (dialog, which) -> submitForm())
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    private void submitForm() {
        tvSummary.setText(buildSummary());

        Snackbar.make(findViewById(R.id.main_scroll_view), R.string.sent, Snackbar.LENGTH_LONG)
                .setAction(R.string.undo, v -> tvSummary.setText(""))
                .show();
    }

    private String buildSummary() {
        String size = rgSize.getCheckedRadioButtonId() == R.id.rbSizeSmall
                ? getString(R.string.size_small)
                : getString(R.string.size_large);

        String sauce = swSauce.isChecked()
                ? getString(R.string.yes_short)
                : getString(R.string.no_short);

        return getString(R.string.summary_format,
                etAddress.getText().toString().trim(),
                etPhone.getText().toString().trim(),
                spPizza.getSelectedItem().toString(),
                size,
                sbQuantity.getProgress(),
                sauce);
    }
}
