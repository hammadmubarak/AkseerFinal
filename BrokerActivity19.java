package com.akseer.puzzlerz.akseer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class BrokerActivity19 extends AppCompatActivity {

    private String classtag = BrokerActivity19.class.getSimpleName();  //return name of underlying class
    private ListView lv;
    private ProgressDialog progress;
    //private String url="https://raw.githubusercontent.com/iCodersLab/JSON-Parsing-in-Android-using-Android-Studio/master/index.html"; //passing url
    private String url = "http://videostreet.pk/tejori/tjApi/getCategoryData"; //passing url
    ArrayList<HashMap<String, Object>> studentslist; //arraylist to save key value pair from json

    public static String id;

    public static int i;


    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.News:
                    startActivity(new Intent(BrokerActivity19.this, NewsActivity4.class));
                    finish();
                    return true;
                case R.id.Market:

                    startActivity(new Intent(BrokerActivity19.this, MarketsActivity6.class));
                    finish();
                    return true;
                case R.id.Akseer:
                    startActivity(new Intent(BrokerActivity19.this, AkseerResearchActivity15.class));

                    finish();
                    return true;

                case R.id.Broker:


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
                startActivity(new Intent(BrokerActivity19.this, DisclaimerActivity16.class));
                return true;
         /*   case R.id.PerformanceReviews:
                startActivity(new Intent(BrokerActivity19.this, PR18Activity.class));
                return true;
            case R.id.additiondeletion:
                startActivity(new Intent(BrokerActivity19.this, AdditionDeletion17.class));
                return true;*/
            case R.id.logout:
                SharedPreferences settings = getSharedPreferences(LoginActivity1.PREFS_NAME, 0); // 0 - for private mode
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("hasLoggedIn", false);
                editor.commit();
                LoginManager.getInstance().logOut();
                startActivity(new Intent(BrokerActivity19.this, LoginActivity1.class));
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
        setContentView(R.layout.activity_broker19);
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setSelectedItemId(R.id.Broker);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        studentslist = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list); //from home screen list view
        new getStudents().execute(); // it will execute your AsyncTask


    }


  /*  public void onClick(View view) {
        startActivity(new Intent(BrokerActivity19.this, BrokerInsightActivity20.class));
    }
*/


    public class getStudents extends AsyncTask<Void, Void, Void> {
        protected void onPreExecute() {
            super.onPreExecute(); //it will use pre defined preExecute method in async task
            progress = new ProgressDialog(BrokerActivity19.this);
            progress.setMessage("Loading data..."); // show what you want in the progress dialog
            progress.setCancelable(false); //progress dialog is not cancellable here
            progress.show();


        }

        protected Void doInBackground(Void... arg0) {

            Map<String, String> headers = new HashMap<>();
            headers.put("X-TJ-APIKEY", "876564123");
            headers.put("Content-Type", "application/x-www-form-urlencoded");
            String result = "";
            try {
                result = HttpRequestBroker.makePostRequest(url, headers);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.e("Result", result);
            //HTTP_Handler hh = new HTTP_Handler(); // object of HTTP_Handler
            // String jString = hh.makeHTTPCall(url); //calling makeHTTPCall method and string its response in a string
            Log.e(classtag, "Response from URL: " + result);
            if (result != null) {
                try {
                    JSONObject jObj = new JSONObject(result); //our json data starts with the object
                    JSONArray students = jObj.getJSONArray("data"); //fetch array from studentsinfo object

                    for (i = 0; i < students.length(); i++) {

                        JSONObject student = students.getJSONObject(i); //get object from i index
                        id = student.getString("id");   //save string from variable 'id' to string
                        String title = student.getString("title");
                        String description = student.getString("description");
                        String category_name = student.getString("category_name");
                        String post_datetime = student.getString("post_datetime");
                        String author = student.getString("author");

                        String post_image = student.getString("post_image");
                        // String urlImg = "http://es.opendomo.org/files/android-logo.png";
                        //String


/*
                        if(id != null){
                            Intent intent = new Intent(BrokerActivity19.this , BrokerInsightActivity20.class);
                            intent.putExtra("id" , id);    //do this for all data objects
                            startActivity(intent);
                        }*/


                        String impact = student.getString("impact");
                        String description_html = student.getString("description_html");


                        //   JSONObject phone=student.getJSONObject("data"); //get object from phone
                        //   String mobile=phone.getString("publish_status");  //save string from variable 'mobile' to string
                        //   String home=phone.getString("publish_status");
                        //   String office=phone.getString("publish_status");

                        HashMap<String, Object> studentdata = new HashMap<>(); //create a hash map to set key and value pair

                        studentdata.put("id", id); //hash map will save key and its value
                        studentdata.put("title", title);
                        studentdata.put("description", description);
                        studentdata.put("category_name", category_name);
                        studentdata.put("post_datetime", post_datetime);
                        studentdata.put("author", author);
                        //    studentdata.put("flag", R.drawable.blank);
                        // studentdata.put("post_image", getBitmapFromURL(post_image));
                        studentdata.put("post_image", post_image);


                        studentdata.put("impact", impact);
                        studentdata.put("description_html", description_html);


                        studentslist.add(studentdata); //now save all of the key value pairs to arraylist


                    }

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
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check internet connection!",
                                Toast.LENGTH_LONG).show();//show if you are unable to connect with the internet or if jString is null
                    }
                });
            }
            return null;
        }


        protected void onPostExecute(Void Result) {

            super.onPostExecute(Result);
            if (progress.isShowing()) {
                progress.dismiss();
            }


            ListAdapter adapter = new SimpleAdapter(


                    BrokerActivity19.this,
                    studentslist,
                    R.layout.bucket_list,
                    new String[]{"id", "title", "description", "category_name", "post_datetime", "author", "post_image", "impact", "description_html"},
                    new int[]{R.id.list_id, R.id.list_title, R.id.list_description, R.id.category, R.id.list_post_datetime, R.id.list_author, R.id.list_post_image, R.id.list_description_html}) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View v = super.getView(position, convertView, parent);

                    ImageView img = (ImageView) v.getTag();
                    if(img == null){
                        img = (ImageView) v.findViewById(R.id.list_post_image);
                        v.setTag(img); // <<< THIS LINE !!!!
                    }
                    String url = String.valueOf(((Map)getItem(position)).get("post_image"));
                    Picasso.with(v.getContext()).load(url).into(img);


                    TextView list_title = (TextView) v.findViewById(R.id.list_title);
                    TextView list_description = (TextView) v.findViewById(R.id.list_description);
                    TextView list_category_name = (TextView) v.findViewById(R.id.category);
                    TextView list_author = (TextView) v.findViewById(R.id.list_author);
                    TextView list_post_datetime = (TextView) v.findViewById(R.id.list_post_datetime);
                    ImageView list_post_image = (ImageView) v.findViewById(R.id.list_post_image);
                    TextView list_description_html = (TextView) v.findViewById(R.id.list_description_html);
                    list_title.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            // TODO Auto-generated method stub
                            Intent intent = new Intent(BrokerActivity19.this, BrokerInsightActivity20.class);
                            intent.putExtra("list_title", list_title.getText());    //do this for all data objects
                            intent.putExtra("list_description", list_description.getText());
                            intent.putExtra("list_category_name", list_category_name.getText());
                            intent.putExtra("list_author", list_author.getText());
                            intent.putExtra("list_post_datetime", list_post_datetime.getText());
                            intent.putExtra("list_post_image", url);
                            intent.putExtra("list_description_html", list_description_html.getText());
                            startActivity(intent);
                        }
                    });

                    list_description.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            // TODO Auto-generated method stub
                            Intent intent = new Intent(BrokerActivity19.this, BrokerInsightActivity20.class);
                            intent.putExtra("list_title", list_title.getText());    //do this for all data objects
                            intent.putExtra("list_description", list_description.getText());
                            intent.putExtra("list_category_name", list_category_name.getText());
                            intent.putExtra("list_author", list_author.getText());
                            intent.putExtra("list_post_datetime", list_post_datetime.getText());
                            intent.putExtra("list_post_image", url);
                            intent.putExtra("list_description_html", list_description_html.getText());
                            startActivity(intent);
                        }
                    });

                    list_category_name.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            // TODO Auto-generated method stub
                            Intent intent = new Intent(BrokerActivity19.this, BrokerInsightActivity20.class);
                            intent.putExtra("list_title", list_title.getText());    //do this for all data objects
                            intent.putExtra("list_description", list_description.getText());
                            intent.putExtra("list_category_name", list_category_name.getText());
                            intent.putExtra("list_author", list_author.getText());
                            intent.putExtra("list_post_datetime", list_post_datetime.getText());
                            intent.putExtra("list_post_image", url);
                            intent.putExtra("list_description_html", list_description_html.getText());
                            startActivity(intent);
                        }
                    });

                    list_author.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            // TODO Auto-generated method stub
                            Intent intent = new Intent(BrokerActivity19.this, BrokerInsightActivity20.class);
                            intent.putExtra("list_title", list_title.getText());    //do this for all data objects
                            intent.putExtra("list_description", list_description.getText());
                            intent.putExtra("list_category_name", list_category_name.getText());
                            intent.putExtra("list_author", list_author.getText());
                            intent.putExtra("list_post_datetime", list_post_datetime.getText());
                            intent.putExtra("list_post_image", url);
                            intent.putExtra("list_description_html", list_description_html.getText());
                            startActivity(intent);
                        }
                    });

                    list_post_datetime.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            // TODO Auto-generated method stub
                            Intent intent = new Intent(BrokerActivity19.this, BrokerInsightActivity20.class);
                            intent.putExtra("list_title", list_title.getText());    //do this for all data objects
                            intent.putExtra("list_description", list_description.getText());
                            intent.putExtra("list_category_name", list_category_name.getText());
                            intent.putExtra("list_author", list_author.getText());
                            intent.putExtra("list_post_datetime", list_post_datetime.getText());
                            intent.putExtra("list_post_image", url);
                            intent.putExtra("list_description_html", list_description_html.getText());
                            startActivity(intent);
                        }
                    });

                    list_post_image.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            // TODO Auto-generated method stub
                            Intent intent = new Intent(BrokerActivity19.this, BrokerInsightActivity20.class);
                            intent.putExtra("list_title", list_title.getText());    //do this for all data objects
                            intent.putExtra("list_description", list_description.getText());
                            intent.putExtra("list_category_name", list_category_name.getText());
                            intent.putExtra("list_author", list_author.getText());
                            intent.putExtra("list_post_datetime", list_post_datetime.getText());
                            intent.putExtra("list_post_image", url);
                            intent.putExtra("list_description_html", list_description_html.getText());
                            startActivity(intent);
                        }
                    });

                    list_description_html.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            // TODO Auto-generated method stub
                            Intent intent = new Intent(BrokerActivity19.this, BrokerInsightActivity20.class);
                            intent.putExtra("list_title", list_title.getText());    //do this for all data objects
                            intent.putExtra("list_description", list_description.getText());
                            intent.putExtra("list_category_name", list_category_name.getText());
                            intent.putExtra("list_author", list_author.getText());
                            intent.putExtra("list_post_datetime", list_post_datetime.getText());
                            intent.putExtra("list_post_image", url);
                            intent.putExtra("list_description_html", list_description_html.getText());
                            startActivity(intent);
                        }
                    });

                    v.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            // TODO Auto-generated method stub
                            Intent intent = new Intent(BrokerActivity19.this, BrokerInsightActivity20.class);
                            intent.putExtra("list_title", list_title.getText());    //do this for all data objects
                            intent.putExtra("list_description", list_description.getText());
                            intent.putExtra("list_category_name", list_category_name.getText());
                            intent.putExtra("list_author", list_author.getText());
                            intent.putExtra("list_post_datetime", list_post_datetime.getText());
                            intent.putExtra("list_post_image", url);
                            intent.putExtra("list_description_html", list_description_html.getText());
                            startActivity(intent);
                        }
                    });
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
    }
}

