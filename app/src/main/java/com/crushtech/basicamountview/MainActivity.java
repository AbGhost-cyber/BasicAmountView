package com.crushtech.basicamountview;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.crushtech.basicamountview.view.AmountView;
import com.google.android.material.button.MaterialButton;

import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {

    AtomicBoolean vi = new AtomicBoolean(true);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AmountView amountView = findViewById(R.id.amountView);
        amountView.setCount(20);
        MaterialButton mb = findViewById(R.id.mb);
        mb.setOnClickListener(v -> {
            vi.set(!vi.get());
            Log.d("TAG", "onCreate: " + vi);
            if (vi.get()) {
                mb.setText("HIDE");
            } else {
                mb.setText("TOGGLE");
            }
            amountView.enableButtons(vi.get());
        });
    }
}