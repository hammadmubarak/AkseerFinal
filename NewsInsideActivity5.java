package com.akseer.puzzlerz.akseer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

public class NewsInsideActivity5 extends AppCompatActivity {



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.News:
                    startActivity(new Intent(NewsInsideActivity5.this, NewsActivity4.class));finish();
                    return true;
                case R.id.Market:

                    startActivity(new Intent(NewsInsideActivity5.this, MarketsActivity6.class));finish();

                    return true;

                case R.id.Akseer:
                    startActivity(new Intent(NewsInsideActivity5.this, AkseerResearchActivity15.class));finish();

                    return true;

                case R.id.Broker:

                    startActivity(new Intent(NewsInsideActivity5.this, BrokerActivity19.class));finish();

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
                startActivity(new Intent(NewsInsideActivity5.this, DisclaimerActivity16.class));
                return true;
         /*   case R.id.PerformanceReviews:
                startActivity(new Intent(NewsInsideActivity5.this, PR18Activity.class));
                return true;
            case R.id.additiondeletion:
                startActivity(new Intent(NewsInsideActivity5.this, AdditionDeletion17.class));
                return true;*/
            case R.id.logout:
                SharedPreferences settings = getSharedPreferences(LoginActivity1.PREFS_NAME, 0); // 0 - for private mode
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("hasLoggedIn", false);
                editor.commit();
                LoginManager.getInstance().logOut();
                startActivity(new Intent(NewsInsideActivity5.this, LoginActivity1.class));
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
        setContentView(R.layout.activity_news_inside5);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setSelectedItemId(R.id.News);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        TextView title = (TextView)findViewById(R.id.title);
        TextView description = (TextView)findViewById(R.id.description);
        TextView category = (TextView)findViewById(R.id.category);
        ImageView img = (ImageView)findViewById(R.id.postImage);
        TextView date = (TextView)findViewById(R.id.date);
        TextView impact = (TextView)findViewById(R.id.impact);
        title.setText(getIntent().getStringExtra("list_title"));
        description.setText(getIntent().getStringExtra("list_description"));
        category.setText(getIntent().getStringExtra("list_category_name"));
        date.setText(getIntent().getStringExtra("list_post_datetime"));
       impact.setText(getIntent().getStringExtra("impact"));
        if(impact.getText().equals("POSITIVE"))
        {
         //   impact.setText("POSITIVE");
            impact.setTextColor(Color.parseColor("#008000"));
        }

        else{
            impact.setText("NEGATIVE");
            impact.setTextColor(Color.parseColor("#e82e25"));
        }
        Picasso.with(this).load(getIntent().getStringExtra("list_post_image")).into(img);
       /* studentdata.put("list_description",getIntent().getStringExtra("list_description"));
        studentdata.put("list_category_name", getIntent().getStringExtra("list_category_name"));
        studentdata.put("list_post_datetime",getIntent().getStringExtra("list_post_datetime"));
       // studentdata.put("list_author", getIntent().getStringExtra("list_author"));
        studentdata.put("list_post_image", getIntent().getStringExtra("list_post_image"));



        studentdata.put("impact", impact);
        studentdata.put("list_description_html", getIntent().getStringExtra("list_description_html"));*/
    }


}
