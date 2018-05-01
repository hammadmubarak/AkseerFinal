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
import android.widget.AdapterView;
import android.widget.EditText;
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
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HCP9 extends AppCompatActivity {


    private String classtag= HCP9.class.getSimpleName();  //return name of underlying class
    private ListView lv;
    private ProgressDialog progress;
    //private String url="https://raw.githubusercontent.com/iCodersLab/JSON-Parsing-in-Android-using-Android-Studio/master/index.html"; //passing url
    private String url="http://videostreet.pk/tejori/tjApi/getCategoryData"; //passing url
    ArrayList<HashMap<String,Object>> studentslist; //arraylist to save key value pair from json



    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.News:
                    startActivity(new Intent(HCP9.this, NewsActivity4.class));finish();
                    return true;
                case R.id.Market:

                    startActivity(new Intent(HCP9.this, MarketsActivity6.class));finish();

                    return true;

                case R.id.Akseer:
                    startActivity(new Intent(HCP9.this, AkseerResearchActivity15.class));finish();

                    return true;

                case R.id.Broker:

                    startActivity(new Intent(HCP9.this, BrokerActivity19.class));finish();

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
                startActivity(new Intent(HCP9.this, DisclaimerActivity16.class));
                return true;
      /*      case R.id.PerformanceReviews:
                startActivity(new Intent(HCP9.this, PR18Activity.class));
                return true;
            case R.id.additiondeletion:
                startActivity(new Intent(HCP9.this, AdditionDeletion17.class));
                return true;*/
            case R.id.logout:
                SharedPreferences settings = getSharedPreferences(LoginActivity1.PREFS_NAME, 0); // 0 - for private mode
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("hasLoggedIn", false);
                editor.commit();
                LoginManager.getInstance().logOut();
                startActivity(new Intent(HCP9.this, LoginActivity1.class));
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
        setContentView(R.layout.activity_hcp9);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setSelectedItemId(R.id.Akseer);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);





        studentslist=new ArrayList<>();
        lv= (ListView) findViewById(R.id.list); //from home screen list view
        new HCP9.getStudents().execute(); // it will execute your AsyncTask







        final String[] array = getResources().getStringArray(R.array.country_arrays2);
        Spinner spin2 = (Spinner) findViewById(R.id.spinner2);
        spin2.setSelection(0);
        //   spin.setAdapter(new ArrayAdapter<String>(MarketsActivity6.this, R.layout.a_layout, array));



        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
              /*  ((TextView) arg0.getChildAt(0)).setTextColor(Color.BLUE);
                ((TextView) arg0.getChildAt(0)).setTextSize(35);*/
                switch (position) {
                    case 0:
                        //   startActivity(new Intent(MarketsActivity6.this, MarketsActivity6.class));
                        break;
                    case 1:
                        startActivity(new Intent(HCP9.this, AdditionDeletion17.class));
                        break;
                    case 2:
                        startActivity(new Intent(HCP9.this, PR18Activity.class));
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
            progress=new ProgressDialog(HCP9.this);
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
                result= HttpRequestSomething3.makePostRequest(url,headers);
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
                    JSONObject students = jObj.getJSONObject("data"); //fetch array from studentsinfo object
                    JSONObject students2 =students.getJSONObject("high-conviction-portfolio");
                    JSONArray students3 =students2.getJSONArray("hcp");
                    Log.e(classtag, "Response from Uhop: " + students3);
                    for (int j = 0; j < students3.length(); j++) {

                        JSONObject student = students3.getJSONObject(j); //get object from i index
                        String id=student.getString("id");   //save string from variable 'id' to string
                        String title=student.getString("symbol");
                        String description=student.getString("entry_price");
                        String category_name=student.getString("last_price");
                        String post_datetime=student.getString("allocation");
                        String author=student.getString("return_to_date");











                        String post_image = student.getString("research_link");
                        // String urlImg = "http://es.opendomo.org/files/android-logo.png";
                        //String


/*
                        if(id != null){
                            Intent intent = new Intent(BrokerActivity19.this , BrokerInsightActivity20.class);
                            intent.putExtra("id" , id);    //do this for all data objects
                            startActivity(intent);
                        }*/


                        String impact=student.getString("upload_datetime");
                   //     String description_html=student.getString("description_html");


                        //   JSONObject phone=student.getJSONObject("data"); //get object from phone
                        //   String mobile=phone.getString("publish_status");  //save string from variable 'mobile' to string
                        //   String home=phone.getString("publish_status");
                        //   String office=phone.getString("publish_status");

                        HashMap<String, Object> studentdata = new HashMap<>(); //create a hash map to set key and value pair

                        studentdata.put("id", id); //hash map will save key and its value
                        studentdata.put("symbol", title);
                        studentdata.put("entry_price", description);
                        studentdata.put("last_price", category_name);
                        studentdata.put("allocation",post_datetime);
                        studentdata.put("return_to_date", author);



                        //    studentdata.put("flag", R.drawable.blank);
                        // studentdata.put("post_image", getBitmapFromURL(post_image));
                        studentdata.put("research_link", post_image);


                        studentdata.put("upload_datetime", impact);
                    //    studentdata.put("description_html", description_html);




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


                    HCP9.this,
                    studentslist,
                    R.layout.bucket_list_hcp9,
                    new String[]{"id","symbol","entry_price","last_price","allocation","return_to_date","research_link"},
                    new int[]{R.id.list_id,R.id.symbol,R.id.EntryPrice ,R.id.currentprice,R.id.allocation,R.id.returnText,R.id.buttonResearch});
            lv.setAdapter(adapter);


            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {

                    Intent intent = new Intent(HCP9.this , BrokerInsightActivity20.class);
                    intent.putExtra("stdData" , 1);    //do this for all data objects
                    startActivity(intent);

                }
            });





//            SimpleAdapter (Context context,                   //
//                    List<? extends Map<String, ?>> data,      //
//            int resource,                                     //
//            String[] from,                                    //
//            int[] to)                                         //
//this will pass your json values to the bucket_list xml and whatever it is stored of key 'name' will be
// displayed to text view list_Name
        }
    }


    public void openStockRating(View view) {
        startActivity(new Intent(HCP9.this, AdditionDeletion17.class));
    }
}
