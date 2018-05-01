package com.akseer.puzzlerz.akseer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.facebook.login.LoginManager;

public class DisclaimerActivity16 extends AppCompatActivity {
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.News:
                    startActivity(new Intent(DisclaimerActivity16.this, NewsActivity4.class));finish();
                    return true;
                case R.id.Market:

                    startActivity(new Intent(DisclaimerActivity16.this, ForexRatesActivity14.class));finish();

                    return true;

                case R.id.Akseer:
                    startActivity(new Intent(DisclaimerActivity16.this, AkseerResearchActivity15.class));finish();

                    return true;

                case R.id.Broker:

                    startActivity(new Intent(DisclaimerActivity16.this, BrokerActivity19.class));finish();

                    return true;
            }
            return false;
        }

    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Disclaimer:
                startActivity(new Intent(DisclaimerActivity16.this, DisclaimerActivity16.class));
                return true;
           /* case R.id.PerformanceReviews:
                startActivity(new Intent(DisclaimerActivity16.this, PR18Activity.class));
                return true;
            case R.id.additiondeletion:
                startActivity(new Intent(DisclaimerActivity16.this, AdditionDeletion17.class));
                return true;*/
            case R.id.logout:
                SharedPreferences settings = getSharedPreferences(LoginActivity1.PREFS_NAME, 0); // 0 - for private mode
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("hasLoggedIn", false);
                editor.commit();
                LoginManager.getInstance().logOut();
                startActivity(new Intent(DisclaimerActivity16.this, LoginActivity1.class));
                finishAffinity();
                finish();
                return true;
            case R.id.Share:
//                startActivity(new Intent(NewsInsideActivity5.this, AdditionDeletion17.class));
                return true;
            case R.id.aboutus:
//                startActivity(new Intent(NewsInsideActivity5.this, AdditionDeletion17.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disclaimer16);
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            BottomNavigationViewHelper.disableShiftMode(navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    public void openAddDel(View view) {
        startActivity(new Intent(DisclaimerActivity16.this,NewsActivity4.class));
    }
}
