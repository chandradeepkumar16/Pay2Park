package com.example.pay2park;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class TestActivity extends AppCompatActivity {

    TextView text;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        text= findViewById(R.id.textfortest);

        Bundle extras1 = getIntent().getExtras();
        if (extras1 != null) {
            id= extras1.getString("key");
            //The key argument here must match that used in the other activity
        }
        Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();
        text.setText(id);
    }
}