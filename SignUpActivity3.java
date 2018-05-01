package com.akseer.puzzlerz.akseer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SignUpActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up3);
    }
    public void openMainActivity(View view) {
        startActivity(new Intent(SignUpActivity3.this,SignUpActivity4.class));
        finish();



    }

}
