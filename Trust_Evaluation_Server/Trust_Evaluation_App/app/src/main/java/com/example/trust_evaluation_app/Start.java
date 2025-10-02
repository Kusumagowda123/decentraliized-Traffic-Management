package com.example.trust_evaluation_app;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Start extends AppCompatActivity {
EditText et;
    Button btn;
    Intent next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        et=(EditText)findViewById(R.id.editText2);
        btn=(Button)findViewById(R.id.btnSet);
        next=new Intent(this,MainActivity.class);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usrl=et.getText().toString();
                Global.url=usrl;
                startActivity(next);

            }
        });

    }
}
