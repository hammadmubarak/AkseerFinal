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
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
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

import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StockRating10 extends AppCompatActivity {


    private String classtag= BrokerActivity19.class.getSimpleName();  //return name of underlying class
    private ListView lv;
    private ProgressDialog progress;
    private static String searchValue ="";
    //private String url="https://raw.githubusercontent.com/iCodersLab/JSON-Parsing-in-Android-using-Android-Studio/master/index.html"; //passing url
    private String url="http://videostreet.pk/tejori/tjApi/getCategoryData"; //passing url
    private String url2 = "http://videostreet.pk/tejori/tjApi/getStockRatingSuggestion";
    ArrayList<HashMap<String,Object>> studentslist; //arraylist to save key value pair from json

    String symbol;
    String year_end;
    String price;
    String target;
    String upside;
    ArrayList<String> eps;
    ArrayList<String> dps;
    ArrayList<String> per;
    ArrayList<String> div_yield;
    ArrayList<String> bvps;





    private TextView mTextMessage;

    private static OkHttpClient client = new OkHttpClient();

    public static String makePostRequest(String url, Map<String, String> headers) throws IOException {

        Headers headers1 = Headers.of(headers);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("symbol", searchValue)

                .build();

        Request request = new Request.Builder()
                .url(url)
                .headers(headers1)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.News:
                    startActivity(new Intent(StockRating10.this, NewsActivity4.class));finish();
                    return true;
                case R.id.Market:

                    startActivity(new Intent(StockRating10.this, MarketsActivity6.class));finish();

                    return true;

                case R.id.Akseer:
                    startActivity(new Intent(StockRating10.this, AkseerResearchActivity15.class));finish();

                    return true;

                case R.id.Broker:

                    startActivity(new Intent(StockRating10.this, BrokerActivity19.class));finish();

                    return true;
            }
            return false;
        }

    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu)   {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Disclaimer:
                startActivity(new Intent(StockRating10.this, DisclaimerActivity16.class));
                return true;
         /*   case R.id.PerformanceReviews:
                startActivity(new Intent(StockRating10.this, PR18Activity.class));
                return true;
            case R.id.additiondeletion:
                startActivity(new Intent(StockRating10.this, AdditionDeletion17.class));
                return true;*/
            case R.id.logout:
                SharedPreferences settings = getSharedPreferences(LoginActivity1.PREFS_NAME, 0); // 0 - for private mode
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("hasLoggedIn", false);
                editor.commit();
                LoginManager.getInstance().logOut();
                startActivity(new Intent(StockRating10.this, LoginActivity1.class));
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
        setContentView(R.layout.activity_stock_rating10);




        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setSelectedItemId(R.id.Market);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);








        studentslist=new ArrayList<>();
        eps = new ArrayList<>();
        dps = new ArrayList<>();
        per = new ArrayList<>();
        div_yield = new ArrayList<>();
        bvps = new ArrayList<>();
        lv= (ListView) findViewById(R.id.list); //from home screen list view
        new StockRating10.getStudents().execute(); // it will execute your AsyncTask




    }

    public class getStudents extends AsyncTask<Void,Void,Void> {
        protected void onPreExecute(){
            super.onPreExecute(); //it will use pre defined preExecute method in async task
            progress=new ProgressDialog(StockRating10.this);
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
                    JSONObject students = jObj.getJSONObject("data"); //fetch array from studentsinfo
                    JSONObject students2 = students.getJSONObject("stock-rating"); //fetch array from studentsinfo object


                    symbol=students2.getString("symbol");   //save string from variable 'id' to string
                    year_end=students2.getString("year_end");
                    price=students2.getString("price");
                    target=students2.getString("target");
                    upside=students2.getString("upside");

                    JSONObject students3 = students2.getJSONObject("category_data"); //fetch array from studentsinfo object
                    JSONArray epsArray =students3.getJSONArray("eps");
                    eps.add(epsArray.getJSONObject(0).getString("2016"));
                    eps.add(epsArray.getJSONObject(1).getString("2017"));
                    eps.add(epsArray.getJSONObject(2).getString("2018"));

                    JSONArray dpsArray =students3.getJSONArray("dps");
                    dps.add(dpsArray.getJSONObject(0).getString("2016"));
                    dps.add(dpsArray.getJSONObject(1).getString("2017"));
                    dps.add(dpsArray.getJSONObject(2).getString("2018"));

                    JSONArray perArray =students3.getJSONArray("per");
                    per.add(perArray.getJSONObject(0).getString("2016"));
                    per.add(perArray.getJSONObject(1).getString("2017"));
                    per.add(perArray.getJSONObject(2).getString("2018"));

                    JSONArray div_yieldArray =students3.getJSONArray("div yield");
                    div_yield.add(div_yieldArray.getJSONObject(0).getString("2016"));
                    div_yield.add(div_yieldArray.getJSONObject(1).getString("2017"));
                    div_yield.add(div_yieldArray.getJSONObject(2).getString("2018"));

                    JSONArray bvpsArray =students3.getJSONArray("bvps");
                    bvps.add(bvpsArray.getJSONObject(0).getString("2016"));
                    bvps.add(bvpsArray.getJSONObject(1).getString("2017"));
                    bvps.add(bvpsArray.getJSONObject(2).getString("2018"));

                    HashMap<String, Object> studentdata = new HashMap<>();

                    studentdata.put("symbol",symbol);
                    studentdata.put("year_end",year_end);
                    studentdata.put("price",price);
                    studentdata.put("target",target);
                    studentdata.put("upside",upside);

                    studentdata.put("eps2016",eps.get(0));
                    studentdata.put("eps2017",eps.get(1));
                    studentdata.put("eps2018",eps.get(2));

                    studentdata.put("per2016",per.get(0));
                    studentdata.put("per2017",per.get(1));
                    studentdata.put("per2018",per.get(2));

                    studentdata.put("dps2016",dps.get(0));
                    studentdata.put("dps2017",dps.get(1));
                    studentdata.put("dps2018",dps.get(2));

                    studentdata.put("div_yield2016",div_yield.get(0));
                    studentdata.put("div_yield2017",div_yield.get(1));
                    studentdata.put("div_yield2018",div_yield.get(2));

                    studentdata.put("bvps2016",bvps.get(0));
                    studentdata.put("bvps2017",bvps.get(1));
                    studentdata.put("bvps2018",bvps.get(2));


                    studentslist.add(studentdata);



                    //                  }

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


                    StockRating10.this,
                    studentslist,
                    R.layout.bucket_list_stockrating10,
                    new String[]{"symbol","year_end","price","target","upside","eps2016","eps2017","eps2018","dps2016","dps2017","dps2018","per2016","per2017","per2018","div_yield2016","div_yield2017","div_yield2018","bvps2016","bvps2017","bvps2018"},
                    new int[]{R.id.symbolxml,R.id.year_end,R.id.CurrentPrice,R.id.TargetPrice,R.id.upside,R.id.eps_twenty16,R.id.eps_twenty17,R.id.eps_twenty18,R.id.dps_twenty16,R.id.dps_twenty17,R.id.dps_twenty18,R.id.per_twenty16,R.id.per_twenty17,R.id.per_twenty18,R.id.divYield_twenty16,R.id.divYield_twenty17,R.id.divYield_twenty18,R.id.bvps_twenty16,R.id.bvps_twenty17,R.id.bvps_twenty18});
            lv.setAdapter(adapter);


            SearchView sv = (SearchView)findViewById(R.id.search);

            sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    searchValue = query;
                    studentslist.clear();
                    new StockRating10.getStock().execute();
                   // Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
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


    public class getStock extends AsyncTask<Void,Void,Void> {
        protected void onPreExecute(){
            super.onPreExecute(); //it will use pre defined preExecute method in async task
            progress=new ProgressDialog(StockRating10.this);
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
                result= makePostRequest(url2,headers);
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
                    JSONArray students = jObj.getJSONArray("data"); //fetch array from studentsinfo

                    for(int count=0; count <students.length();count++) {
                        JSONObject students2 = students.getJSONObject(count);//fetch array from studentsinfo object


                        symbol = students2.getString("symbol");   //save string from variable 'id' to string
                        year_end = students2.getString("year_end");
                        price = students2.getString("price");
                        target = students2.getString("target");
                        upside = students2.getString("upside");

                        JSONObject students3 = students2.getJSONObject("category_data"); //fetch array from studentsinfo object
                        JSONArray epsArray = students3.getJSONArray("eps");
                        eps.add(epsArray.getJSONObject(0).getString("2016"));
                        eps.add(epsArray.getJSONObject(1).getString("2017"));
                        eps.add(epsArray.getJSONObject(2).getString("2018"));

                        JSONArray dpsArray = students3.getJSONArray("dps");
                        dps.add(dpsArray.getJSONObject(0).getString("2016"));
                        dps.add(dpsArray.getJSONObject(1).getString("2017"));
                        dps.add(dpsArray.getJSONObject(2).getString("2018"));

                        JSONArray perArray = students3.getJSONArray("per");
                        per.add(perArray.getJSONObject(0).getString("2016"));
                        per.add(perArray.getJSONObject(1).getString("2017"));
                        per.add(perArray.getJSONObject(2).getString("2018"));

                        JSONArray div_yieldArray = students3.getJSONArray("div yield");
                        div_yield.add(div_yieldArray.getJSONObject(0).getString("2016"));
                        div_yield.add(div_yieldArray.getJSONObject(1).getString("2017"));
                        div_yield.add(div_yieldArray.getJSONObject(2).getString("2018"));

                        JSONArray bvpsArray = students3.getJSONArray("bvps");
                        bvps.add(bvpsArray.getJSONObject(0).getString("2016"));
                        bvps.add(bvpsArray.getJSONObject(1).getString("2017"));
                        bvps.add(bvpsArray.getJSONObject(2).getString("2018"));

                        HashMap<String, Object> studentdata = new HashMap<>();

                        studentdata.put("symbol", symbol);
                        studentdata.put("year_end", year_end);
                        studentdata.put("price", price);
                        studentdata.put("target", target);
                        studentdata.put("upside", upside);

                        studentdata.put("eps2016", eps.get(0));
                        studentdata.put("eps2017", eps.get(1));
                        studentdata.put("eps2018", eps.get(2));

                        studentdata.put("per2016", per.get(0));
                        studentdata.put("per2017", per.get(1));
                        studentdata.put("per2018", per.get(2));

                        studentdata.put("dps2016", dps.get(0));
                        studentdata.put("dps2017", dps.get(1));
                        studentdata.put("dps2018", dps.get(2));

                        studentdata.put("div_yield2016", div_yield.get(0));
                        studentdata.put("div_yield2017", div_yield.get(1));
                        studentdata.put("div_yield2018", div_yield.get(2));

                        studentdata.put("bvps2016", bvps.get(0));
                        studentdata.put("bvps2017", bvps.get(1));
                        studentdata.put("bvps2018", bvps.get(2));


                        studentslist.add(studentdata);


                        //                  }
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


                    StockRating10.this,
                    studentslist,
                    R.layout.bucket_list_stockrating10,
                    new String[]{"symbol","year_end","price","target","upside","eps2016","eps2017","eps2018","dps2016","dps2017","dps2018","per2016","per2017","per2018","div_yield2016","div_yield2017","div_yield2018","bvps2016","bvps2017","bvps2018"},
                    new int[]{R.id.symbolxml,R.id.year_end,R.id.CurrentPrice,R.id.TargetPrice,R.id.upside,R.id.eps_twenty16,R.id.eps_twenty17,R.id.eps_twenty18,R.id.dps_twenty16,R.id.dps_twenty17,R.id.dps_twenty18,R.id.per_twenty16,R.id.per_twenty17,R.id.per_twenty18,R.id.divYield_twenty16,R.id.divYield_twenty17,R.id.divYield_twenty18,R.id.bvps_twenty16,R.id.bvps_twenty17,R.id.bvps_twenty18});
            lv.setAdapter(adapter);


            SearchView sv = (SearchView)findViewById(R.id.search);

            sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    searchValue = query;
                    studentslist.clear();
                    new StockRating10.getStock().execute();
                    Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }


            });
//
        }
    }


}