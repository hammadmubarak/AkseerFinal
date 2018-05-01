package com.akseer.puzzlerz.akseer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AkseerInsightActivity8 extends AppCompatActivity {
    private String classtag= AkseerInsightActivity8.class.getSimpleName();
    private ListView lv;
    private ProgressDialog progress;
    //private String url="https://raw.githubusercontent.com/iCodersLab/JSON-Parsing-in-Android-using-Android-Studio/master/index.html"; //passing url
    private String url="http://videostreet.pk/tejori/tjApi/getCategoryData"; //passing url
    ArrayList<HashMap<String,String>> studentslist;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.News:
                    startActivity(new Intent(AkseerInsightActivity8.this, NewsActivity4.class));finish();
                    return true;
                case R.id.Market:

                    startActivity(new Intent(AkseerInsightActivity8.this, ForexRatesActivity14.class));
                    finish();

                    return true;

                case R.id.Akseer:
                    startActivity(new Intent(AkseerInsightActivity8.this, AkseerResearchActivity15.class));

                    return true;

                case R.id.Broker:

                    startActivity(new Intent(AkseerInsightActivity8.this, BrokerActivity19.class));
                    finish();
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
                startActivity(new Intent(AkseerInsightActivity8.this, DisclaimerActivity16.class));
                return true;
        /*    case R.id.PerformanceReviews:
                startActivity(new Intent(AkseerInsightActivity8.this, PR18Activity.class));
                return true;
            case R.id.additiondeletion:
                startActivity(new Intent(AkseerInsightActivity8.this, AdditionDeletion17.class));
                return true;*/
            case R.id.logout:
                SharedPreferences settings = getSharedPreferences(LoginActivity1.PREFS_NAME, 0); // 0 - for private mode
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("hasLoggedIn", false);
                editor.commit();
                LoginManager.getInstance().logOut();
                startActivity(new Intent(AkseerInsightActivity8.this, LoginActivity1.class));
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
        setContentView(R.layout.activity_akseer_insight8);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setSelectedItemId(R.id.Akseer);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        TextView title = (TextView)findViewById(R.id.title);
        TextView description = (TextView)findViewById(R.id.description);
        TextView category = (TextView)findViewById(R.id.category);
        ImageView img = (ImageView)findViewById(R.id.postImage);
        TextView date = (TextView)findViewById(R.id.date);
        TextView author = (TextView)findViewById(R.id.author);
        title.setText(getIntent().getStringExtra("list_title"));
        description.setText(getIntent().getStringExtra("list_description"));
        category.setText(getIntent().getStringExtra("list_category_name"));
        date.setText(getIntent().getStringExtra("list_post_datetime"));
        author.setText(getIntent().getStringExtra("list_author"));
     /*   if(impact.getText().equals("POSITIVE"))
        {
            //   impact.setText("POSITIVE");
            impact.setTextColor(Color.parseColor("#008000"));
        }

        else{
            impact.setText("NEGATIVE");
            impact.setTextColor(Color.parseColor("#e82e25"));
        }*/
        Picasso.with(this).load(getIntent().getStringExtra("list_post_image")).into(img);


    }

  /*  public class getStudents extends AsyncTask<Void,Void,Void> {
        protected void onPreExecute(){
            super.onPreExecute(); //it will use pre defined preExecute method in async task
            progress=new ProgressDialog(AkseerInsightActivity8.this);
            progress.setMessage("Loading data..."); // show what you want in the progress dialog
            progress.setCancelable(false); //progress dialog is not cancellable here
            progress.show();





        }
        protected Void doInBackground(Void...arg0){

            Map<String, String> headers = new HashMap<>();
            headers.put("X-TJ-APIKEY", "876564123");
            headers.put("Content-Type","application/x-www-form-urlencoded");
            String result="";
            try {
                result= HttpRequestSomething.makePostRequest(url,headers);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.e("Result",result);
            //HTTP_Handler hh = new HTTP_Handler(); // object of HTTP_Handler
            // String jString = hh.makeHTTPCall(url); //calling makeHTTPCall method and string its response in a string
            Log.e(classtag, "Response from URL: " + result);
            if (result != null) {
                try {
                    JSONObject jObj = new JSONObject(result); //our json data starts with the object
                    JSONArray students = jObj.getJSONArray("data"); //fetch array from studentsinfo object


                    JSONObject student = students.getJSONObject(1); //get object from i index


                    String id=student.getString("id");   //save string from variable 'id' to string
                    String title=student.getString("title");
                    String description=student.getString("description");
                    String category_name=student.getString("category_name");
                    String post_datetime=student.getString("post_datetime");
                    String author=student.getString("author");

                    String post_image = student.getString("post_image");




                    String impact=student.getString("impact");
                    String description_html=student.getString("description_html");


                    //   JSONObject phone=student.getJSONObject("data"); //get object from phone
                    //   String mobile=phone.getString("publish_status");  //save string from variable 'mobile' to string
                    //   String home=phone.getString("publish_status");
                    //   String office=phone.getString("publish_status");

                    HashMap<String, String> studentdata = new HashMap<>(); //create a hash map to set key and value pair

                    studentdata.put("id", id); //hash map will save key and its value
                    studentdata.put("list_title",  getIntent().getStringExtra("list_title"));
                    studentdata.put("list_description",getIntent().getStringExtra("list_description"));
                    studentdata.put("list_category_name", getIntent().getStringExtra("list_category_name"));
                    studentdata.put("list_post_datetime",getIntent().getStringExtra("list_post_datetime"));
                    studentdata.put("list_author", getIntent().getStringExtra("list_author"));
                    studentdata.put("list_post_image", getIntent().getStringExtra("list_post_image"));



                    studentdata.put("impact", impact);
                    studentdata.put("list_description_html", getIntent().getStringExtra("list_description_html"));


                    studentslist.add(studentdata); //now save all of the key value pairs to arraylist





                } catch (final JSONException e) {
                    Log.e(classtag, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show(); //show if you catch any exception with data
                        }
                    });
                }
            } else {
                Log.e(classtag, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {Toast.makeText(getApplicationContext(),
                            "Couldn't get json from server. Check internet connection!",
                            Toast.LENGTH_LONG).show();//show if you are unable to connect with the internet or if jString is null
                    }
                });
            }
            return null;
        }






        protected void onPostExecute(Void Result){

            super.onPostExecute(Result);
            if(progress.isShowing()){
                progress.dismiss();
            }


            ListAdapter adapter=new SimpleAdapter(
                    AkseerInsightActivity8.this,
                    studentslist,
                    R.layout.bucket_list2,
                    new String[]{"id","list_title","list_description","list_category_name","list_post_datetime","list_author","list_post_image","impact","list_description_html"},
                    new int[]{R.id.list_id,R.id.list_title,R.id.list_description ,R.id.category,R.id.list_post_datetime,R.id.list_author,R.id.list_post_image,R.id.list_description_html}){
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View v = super.getView(position, convertView, parent);

                    ImageView img = (ImageView) v.getTag();
                    if(img == null){
                        img = (ImageView) v.findViewById(R.id.list_post_image);
                        v.setTag(img); // <<< THIS LINE !!!!
                    }
                    String url = String.valueOf(((Map)getItem(position)).get("list_post_image"));
                    Picasso.with(v.getContext()).load(url).into(img);
                    return v;
                }


            };
            lv.setAdapter(adapter);


//            SimpleAdapter (Context context,                   //
//                    List<? extends Map<String, ?>> data,      //
//            int resource,                                     //
//            String[] from,                                    //
//            int[] to)                                         //
//this will pass your json values to the bucket_list xml and whatever it is stored of key 'name' will be
// displayed to text view list_Name
        }
    }*/

}

