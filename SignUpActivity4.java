package com.akseer.puzzlerz.akseer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SignUpActivity4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up4);
    }
    public void openMainActivity(View view) {
        startActivity(new Intent(SignUpActivity4.this,DisclaimerActivity16.class));
        finish();



    }

}
