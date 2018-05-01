package com.akseer.puzzlerz.akseer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MarketsActivity6 extends AppCompatActivity {




    private String classtag= BrokerActivity19.class.getSimpleName();  //return name of underlying class
    private ListView lv;
    private ProgressDialog progress;
    //private String url="https://raw.githubusercontent.com/iCodersLab/JSON-Parsing-in-Android-using-Android-Studio/master/index.html"; //passing url
    private String url="http://videostreet.pk/tejori/tjApi/getCategoryData"; //passing url
   ArrayList<HashMap<String,String>> listTab1;
    ArrayList<HashMap<String,String>> listTab2; //arraylist to save key value pair from json
    ArrayList<HashMap<String,String>> listTab3; //arraylist to save key value pair from json
    ArrayList<HashMap<String,String>> listTab4;
    ArrayList<HashMap<String,String>> listTab5;//arraylist to save key value pair from json
    //arraylist to save key value pair from json
   private TabLayout tabLayout;



    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.News:
                    startActivity(new Intent(MarketsActivity6.this, NewsActivity4.class));
                    finish();
                    return true;
                case R.id.Market:


                    return true;

                case R.id.Akseer:
                    startActivity(new Intent(MarketsActivity6.this, AkseerResearchActivity15.class));
                    finish();
                    return true;

                case R.id.Broker:

                    startActivity(new Intent(MarketsActivity6.this, BrokerActivity19.class));
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
                startActivity(new Intent(MarketsActivity6.this, DisclaimerActivity16.class));
                return true;
           /* case R.id.PerformanceReviews:
                startActivity(new Intent(MarketsActivity6.this, PR18Activity.class));
                return true;
            case R.id.additiondeletion:
                startActivity(new Intent(MarketsActivity6.this, AdditionDeletion17.class));
                return true;*/
            case R.id.logout:
                SharedPreferences settings = getSharedPreferences(LoginActivity1.PREFS_NAME, 0); // 0 - for private mode
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("hasLoggedIn", false);
                editor.commit();
                LoginManager.getInstance().logOut();
                startActivity(new Intent(MarketsActivity6.this, LoginActivity1.class));
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
        setContentView(R.layout.activity_markets6);



        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setSelectedItemId(R.id.Market);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        tabLayout= (TabLayout) findViewById(R.id.tabHost);
        

        listTab1=new ArrayList<>();
        listTab2=new ArrayList<>();
        listTab3=new ArrayList<>();
        listTab4=new ArrayList<>();
        listTab5=new ArrayList<>();
        lv= (ListView) findViewById(R.id.list); //from home screen list view
        new getStudents().execute(); // it will execute your AsyncTask





































        final String[] array = getResources().getStringArray(R.array.country_arrays);
        Spinner spin = (Spinner) findViewById(R.id.spinner);
        spin.setSelection(0);
     //   spin.setAdapter(new ArrayAdapter<String>(MarketsActivity6.this, R.layout.a_layout, array));



        spin.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
              /*  ((TextView) arg0.getChildAt(0)).setTextColor(Color.BLUE);
                ((TextView) arg0.getChildAt(0)).setTextSize(35);*/
                switch (position) {
                    case 0:
                     //   startActivity(new Intent(MarketsActivity6.this, MarketsActivity6.class));
                        break;
                    case 1:
                        startActivity(new Intent(MarketsActivity6.this, KiborActivity11.class));
                        break;
                    case 2:
                        startActivity(new Intent(MarketsActivity6.this, ForexRatesActivity14.class));
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
            progress=new ProgressDialog(MarketsActivity6.this);
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
            if (result != null) try {
                JSONObject jObj = new JSONObject(result);
                JSONObject categoryObject = jObj.getJSONObject("data");
                JSONArray categoryObject2 = categoryObject.getJSONArray("commodities");
                for (int j = 0; j < categoryObject2.length(); j++) {

                    JSONObject categoryObject3 = categoryObject2.getJSONObject(j);
                    // Log.e(classtag, "Response 2 from URL: " + categoryObject2);
                    //our json data starts with the object
                    JSONArray students = categoryObject3.getJSONArray("data"); //fetch array from studentsinfo object

                    for (int i = 0; i < students.length(); i++) {

                        JSONObject student = students.getJSONObject(i); //get object from i index
                        String name = student.getString("name");   //save string from variable 'id' to string
                        String long_name = student.getString("long_name");
                        String data_value = student.getString("data_value");
                        String date = student.getString("date");
                        String todays_change = student.getString("todays_change");
                        String imapact = student.getString("imapact");
                        if(imapact.equals("+"))
                          imapact = String.valueOf(R.drawable.arrowup);
                        else
                            imapact = String.valueOf(R.drawable.arrowdown);

//                        String post_image = student.getString("post_image");
///*
//                        if(id != null){
//                            Intent intent = new Intent(BrokerActivity19.this , BrokerInsightActivity20.class);
//                            intent.putExtra("id" , id);    //do this for all data objects
//                            startActivity(intent);
//                        }*/
//
//
//                        String impact=student.getString("impact");
//                        String description_html=student.getString("description_html");


                        //   JSONObject phone=student.getJSONObject("data"); //get object from phone
                        //   String mobile=phone.getString("publish_status");  //save string from variable 'mobile' to string
                        //   String home=phone.getString("publish_status");
                        //   String office=phone.getString("publish_status");

                        HashMap<String, String> studentdata = new HashMap<>(); //create a hash map to set key and value pair

                        studentdata.put("name", name); //hash map will save key and its value
                        studentdata.put("long_name", long_name);
                        studentdata.put("data_value", data_value);
                        studentdata.put("date", date);
                        studentdata.put("todays_change", todays_change);
                        studentdata.put("imapact", imapact);
                        // studentdata.put("post_image", post_image);


                        // studentdata.put("impact", impact);
                        // studentdata.put("description_html", description_html);

                        if(j==0)
                            listTab1.add(studentdata); //now save all of the key value pairs to arraylist
                        else if( j==1)
                            listTab2.add(studentdata);
                        else if( j==2)
                            listTab3.add(studentdata);
                        else if( j==3)
                            listTab4.add(studentdata);
                        else if( j==4)
                            listTab5.add(studentdata);

                    }
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
            else {
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
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    int position = tab.getPosition();

                    if(position == 0)
                    {
                        ListAdapter adapter=new SimpleAdapter(


                                MarketsActivity6.this,
                                listTab1,
                                R.layout.bucket_list3,
                                new String[]{"name","long_name","data_value","date","todays_change","imapact"},
                                new int[]{R.id.list_id,R.id.list_title,R.id.list_description ,R.id.category,R.id.list_post_datetime,R.id.list_author});
                        lv.setAdapter(adapter);
                    }

                    else if(position == 1)
                    {
                        ListAdapter adapter=new SimpleAdapter(


                                MarketsActivity6.this,
                                listTab2,
                                R.layout.bucket_list3,
                                new String[]{"name","long_name","data_value","date","todays_change","imapact"},
                                new int[]{R.id.list_id,R.id.list_title,R.id.list_description ,R.id.category,R.id.list_post_datetime,R.id.list_author});
                        lv.setAdapter(adapter);
                    }

                    else if(position == 2)
                    {
                        ListAdapter adapter=new SimpleAdapter(


                                MarketsActivity6.this,
                                listTab3,
                                R.layout.bucket_list3,
                                new String[]{"name","long_name","data_value","date","todays_change","imapact"},
                                new int[]{R.id.list_id,R.id.list_title,R.id.list_description ,R.id.category,R.id.list_post_datetime,R.id.list_author});
                        lv.setAdapter(adapter);
                    }

                    else if(position == 3)
                    {
                        ListAdapter adapter=new SimpleAdapter(


                                MarketsActivity6.this,
                                listTab4,
                                R.layout.bucket_list3,
                                new String[]{"name","long_name","data_value","date","todays_change","imapact"},
                                new int[]{R.id.list_id,R.id.list_title,R.id.list_description ,R.id.category,R.id.list_post_datetime,R.id.list_author});
                        lv.setAdapter(adapter);
                    }

                    else if(position == 4)
                    {
                        ListAdapter adapter=new SimpleAdapter(


                                MarketsActivity6.this,
                                listTab5,
                                R.layout.bucket_list3,
                                new String[]{"name","long_name","data_value","date","todays_change","imapact"},
                                new int[]{R.id.list_id,R.id.list_title,R.id.list_description ,R.id.category,R.id.list_post_datetime,R.id.list_author});
                        lv.setAdapter(adapter);
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

            ListAdapter adapter=new SimpleAdapter(


                    MarketsActivity6.this,
                    listTab1,
                    R.layout.bucket_list3,
                    new String[]{"name","long_name","data_value","date","todays_change","imapact"},
                    new int[]{R.id.list_id,R.id.list_title,R.id.list_description ,R.id.category,R.id.list_post_datetime,R.id.list_author});
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















    public void openHCP(View view) {

    startActivity(new Intent(MarketsActivity6.this, HCP9.class));}
























//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//            switch (item.getItemId()) {
//
//
//               case R.id.News:
//                    startActivity(new Intent(MarketsActivity6.this, NewsActivity4.class));
//
//                   return true;
//               case R.id.Market:
//
//                   return true;
//               case R.id.Akseer:
//                   startActivity(new Intent(MarketsActivity6.this, AkseerActivity7.class));
//
//                   return true;
//
//               case R.id.Broker:
//                  startActivity(new Intent(MarketsActivity6.this, BrokerActivity19.class));
//                   return true;
//           }
//           return false;
//        }
//
//    };
//    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//
//
//    private TextView mTextMessage;
//
//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//
//            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//
//
//            switch (item.getItemId()) {
//
//
//                case R.id.News:
//                    startActivity(new Intent(MarketsActivity6.this, NewsActivity4.class));
//
//                    return true;
//                case R.id.Market:
//
//                    return true;
//                case R.id.Akseer:
//                    startActivity(new Intent(MarketsActivity6.this, AkseerActivity7.class));
//
//                    return true;
//
//                case R.id.Broker:
//                    startActivity(new Intent(MarketsActivity6.this, BrokerActivity19.class));
//
//                    return true;
//            }
//            return false;
//        }
//
//    };
//



/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markets6);


        TabHost TabHost;


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);





    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }*/
}
