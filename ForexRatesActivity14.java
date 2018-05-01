package com.akseer.puzzlerz.akseer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ForexRatesActivity14 extends AppCompatActivity {

    private String classtag= BrokerActivity19.class.getSimpleName();  //return name of underlying class
    private ListView lv;
    private ProgressDialog progress;
    //private String url="https://raw.githubusercontent.com/iCodersLab/JSON-Parsing-in-Android-using-Android-Studio/master/index.html"; //passing url
    private String url="http://videostreet.pk/tejori/tjApi/getCategoryData"; //passing url
    ArrayList<HashMap<String,Object>> studentslist; //arraylist to save key value pair from json

    public static String id;

    public static int i;


    private TextView mTextMessage;





















    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.News:
                    startActivity(new Intent(ForexRatesActivity14.this, NewsActivity4.class));finish();
                    return true;
                case R.id.Market:

                    startActivity(new Intent(ForexRatesActivity14.this, ForexRatesActivity14.class));finish();

                    return true;

                case R.id.Akseer:
                    startActivity(new Intent(ForexRatesActivity14.this, AkseerResearchActivity15.class));finish();

                    return true;

                case R.id.Broker:

                    startActivity(new Intent(ForexRatesActivity14.this, BrokerActivity19.class));finish();

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
                startActivity(new Intent(ForexRatesActivity14.this, DisclaimerActivity16.class));
                return true;
         /*   case R.id.PerformanceReviews:
                startActivity(new Intent(ForexRatesActivity14.this, PR18Activity.class));
                return true;
            case R.id.additiondeletion:
                startActivity(new Intent(ForexRatesActivity14.this, AdditionDeletion17.class));
                return true;*/
            case R.id.logout:
                SharedPreferences settings = getSharedPreferences(LoginActivity1.PREFS_NAME, 0); // 0 - for private mode
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("hasLoggedIn", false);
                editor.commit();
                LoginManager.getInstance().logOut();
                startActivity(new Intent(ForexRatesActivity14.this, LoginActivity1.class));
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
        setContentView(R.layout.activity_forex_rates14);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setSelectedItemId(R.id.Market);





        studentslist=new ArrayList<>();
        lv= (ListView) findViewById(R.id.list); //from home screen list view
        new ForexRatesActivity14.getStudents().execute(); // it will execute your AsyncTask




        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Spinner spin = (Spinner) findViewById(R.id.spinner);
        spin.setSelection(2);
        //   spin.setAdapter(new ArrayAdapter<String>(MarketsActivity6.this, R.layout.a_layout, array));
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {

                switch (position) {
                    case 0:
                        startActivity(new Intent(ForexRatesActivity14.this, MarketsActivity6.class));
                        break;
                    case 1:
                        startActivity(new Intent(ForexRatesActivity14.this,KiborActivity11 .class));
                        break;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }







    public class getStudents extends AsyncTask<Void,Void,Void> {
        protected void onPreExecute(){
            super.onPreExecute(); //it will use pre defined preExecute method in async task
            progress=new ProgressDialog(ForexRatesActivity14.this);
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
                result= HttpRequestSomething2.makePostRequest(url,headers);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.e("Result",result);
            //HTTP_Handler hh = new HTTP_Handler(); // object of HTTP_Handler
            // String jString = hh.makeHTTPCall(url); //calling makeHTTPCall method and string its response in a string
            Log.e(classtag, "Response from URL: " + result);
            if (result != null) {
                try {
                    JSONObject jObj = new JSONObject(result);
                    JSONObject categoryObject = jObj.getJSONObject("data");
                    JSONArray categoryObject2 = categoryObject.getJSONArray("forex-rates");
                    for (int i = 0; i < categoryObject2.length(); i++) {

                        JSONObject student = categoryObject2.getJSONObject(i); //get object from i index
                       //  String id=student.getString("id");   //save string from variable 'id' to string
                        String name=student.getString("name");
                        String long_name=student.getString("long_name");
                        String data_value=student.getString("data_value");
                        String date=student.getString("date");
                        String todays_change = student.getString("todays_change");
                        String imapact=student.getString("imapact");
                        if(imapact.equals("+"))
                            imapact = String.valueOf(R.drawable.arrowup);
                        else
                            imapact = String.valueOf(R.drawable.arrowdown);

                        // String urlImg = "http://es.opendomo.org/files/android-logo.png";
                        //String


/*
                        if(id != null){
                            Intent intent = new Intent(BrokerActivity19.this , BrokerInsightActivity20.class);
                            intent.putExtra("id" , id);    //do this for all data objects
                            startActivity(intent);
                        }*/


                     //   String impact=student.getString("impact");
                        //String description_html=student.getString("description_html");


                        //   JSONObject phone=student.getJSONObject("data"); //get object from phone
                        //   String mobile=phone.getString("publish_status");  //save string from variable 'mobile' to string
                        //   String home=phone.getString("publish_status");
                        //   String office=phone.getString("publish_status");

                        HashMap<String, Object> studentdata = new HashMap<>(); //create a hash map to set key and value pair

                        studentdata.put("name", name); //hash map will save key and its value
                        studentdata.put("long_name", long_name);
                        studentdata.put("data_value", data_value);
                        studentdata.put("date", date);
                        studentdata.put("todays_change",todays_change);
                        studentdata.put("imapact", imapact);
                        //    studentdata.put("flag", R.drawable.blank);
                 //   studentdata.put("post_image", getBitmapFromURL(post_image));
                     //   studentdata.put("post_image", post_image);


                     //   studentdata.put("impact", impact);
                     //   studentdata.put("description_html", description_html);




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


                    ForexRatesActivity14.this,
                    studentslist,
                    R.layout.bucket_list5,
                    new String[]{"name","long_name","data_value","date","todays_change","imapact"},
                    new int[]{R.id.name,R.id.longname,R.id.data_value ,R.id.todays_change,R.id.todays_change,R.id.imapact}) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View v = super.getView(position, convertView, parent);

                    /*ImageView img = (ImageView) v.getTag();
                    if (img == null) {
                        img = (ImageView) v.findViewById(R.id.list_post_image);
                        v.setTag(img); // <<< THIS LINE !!
                    }
                    String url = String.valueOf(((Map) getItem(position)).get("post_image"));
                    Picasso.with(v.getContext()).load(url).into(img);*/

                  //  TextView impact = (TextView) v.findViewById(R.id.imapact);
                    TextView todays_change = (TextView) v.findViewById(R.id.todays_change);
                    if (todays_change.getText().charAt(0) == '-') {
                        //impact.setText("POSITIVE");

                        todays_change.setTextColor(Color.parseColor("#e82e25"));
                    } else {
                        //impact.setText("NEGATIVE");
                        todays_change.setTextColor(Color.parseColor("#008000"));
                    }
                     return  v;
                }
            };
            lv.setAdapter(adapter);


//            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position,
//                                        long id) {
//
//                    Intent intent = new Intent(ForexRatesActivity14.this , BrokerInsightActivity20.class);
//                    intent.putExtra("stdData" , 1);    //do this for all data objects
//                    startActivity(intent);
//
//                }
//            });





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
