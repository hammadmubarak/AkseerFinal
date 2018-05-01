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
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TabHost;
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

public class KiborActivity11 extends AppCompatActivity {




    private String classtag= BrokerActivity19.class.getSimpleName();  //return tenor of underlying class
    private ListView listViewKibor;
    private ListView listViewLibor;
    private ListView listViewPKRV;
    private ProgressDialog progress;
    //private String url="https://raw.githubusercontent.com/iCodersLab/JSON-Parsing-in-Android-using-Android-Studio/master/index.html"; //passing url
    private String url="http://videostreet.pk/tejori/tjApi/getCategoryData"; //passing url
    ArrayList<HashMap<String,String>> listTab1;
    ArrayList<HashMap<String,String>> listTab2; //arraylist to save key value pair from json
    ArrayList<HashMap<String,String>> listTab3; //arraylist to save key value pair from json
    ArrayList<HashMap<String,String>> listTab4; //arraylist to save key value pair from json
    //arraylist to save key value pair from json
    private   TabHost host;


    private TextView mTextMessage;





    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.News:
                    startActivity(new Intent(KiborActivity11.this, NewsActivity4.class));finish();
                    return true;
                case R.id.Market:

                    startActivity(new Intent(KiborActivity11.this, KiborActivity11.class));finish();

                    return true;

                case R.id.Akseer:
                    startActivity(new Intent(KiborActivity11.this, AkseerResearchActivity15.class));finish();

                    return true;

                case R.id.Broker:

                    startActivity(new Intent(KiborActivity11.this, BrokerActivity19.class));finish();

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
                startActivity(new Intent(KiborActivity11.this, DisclaimerActivity16.class));
                return true;
        /*    case R.id.PerformanceReviews:
                startActivity(new Intent(KiborActivity11.this, PR18Activity.class));
                return true;
            case R.id.additiondeletion:
                startActivity(new Intent(KiborActivity11.this, AdditionDeletion17.class));
                return true;*/
            case R.id.logout:
                SharedPreferences settings = getSharedPreferences(LoginActivity1.PREFS_NAME, 0); // 0 - for private mode
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("hasLoggedIn", false);
                editor.commit();
                LoginManager.getInstance().logOut();
                startActivity(new Intent(KiborActivity11.this, LoginActivity1.class));
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
        setContentView(R.layout.activity_kibor11);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setSelectedItemId(R.id.Market);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        host = (TabHost)findViewById(R.id.TabHost);
        host.setup();



        listTab1=new ArrayList<>();
        listTab2=new ArrayList<>();
        listTab3=new ArrayList<>();
      //  listTab4=new ArrayList<>();
        listViewKibor= (ListView) findViewById(R.id.list2_Kibor);
        listViewLibor= (ListView) findViewById(R.id.list_Libor);
        listViewPKRV= (ListView) findViewById(R.id.list2_PKRV);//from home screen list view
        new KiborActivity11.getStudents().execute(); // it will execute your AsyncTask

        TabHost.TabSpec spec = host.newTabSpec("KIBOR");
        spec.setContent(R.id.Kibor);
        spec.setIndicator("Kibor");
        host.addTab(spec);


        //Tab 2
        spec = host.newTabSpec("LIBOR");
        spec.setContent(R.id.Libor);
        spec.setIndicator("Libor");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("PKRV");
        spec.setContent(R.id.PKRV);
        spec.setIndicator("PKRV");
        host.addTab(spec);
        final String[] array = getResources().getStringArray(R.array.country_arrays);
        Spinner spin = (Spinner) findViewById(R.id.spinner);
        spin.setSelection(1);







        //   spin.setAdapter(new ArrayAdapter<String>(MarketsActivity6.this, R.layout.a_layout, array));
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {

                switch (position) {
                    case 0:
                        startActivity(new Intent(KiborActivity11.this, MarketsActivity6.class));
                        break;
                    case 1:
                       // startActivity(new Intent(KiborActivity11.this, KiborActivity11.class));
                        break;
                    case 2:
                        startActivity(new Intent(KiborActivity11.this, ForexRatesActivity14.class));
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
            progress=new ProgressDialog(KiborActivity11.this);
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
                JSONObject categoryObject1 = categoryObject.getJSONObject("money-market");
                //JSONArray categoryObject2 = categoryObject1.getJSONArray("data");
                //for (int j = 0; j < categoryObject2.length(); j++) {

                    JSONObject kiborObject = categoryObject1.getJSONObject("kibor");
                JSONObject liborObject = categoryObject1.getJSONObject("libor");
                JSONObject pkrvObject = categoryObject1.getJSONObject("pkrv");
                Log.e(classtag, "Response from kibor: " + kiborObject);
                Log.e(classtag, "Response from libor: " + liborObject);
                Log.e(classtag, "Response from pkrv: " + pkrvObject);
                    // Log.e(classtag, "Response 2 from URL: " + categoryObject2);
                    //our json data starts with the object
                    JSONArray resultArray = kiborObject.getJSONArray("data"); //fetch array from studentsinfo object

                    for (int i = 0; i < resultArray.length(); i++) {

                        JSONObject resultElement = resultArray.getJSONObject(i); //get object from i index
                        String tenor = resultElement.getString("tenor");   //save string from variable 'id' to string
                        String bid = resultElement.getString("bid");
                        String offer = resultElement.getString("offer");
                        String frequency = resultElement.getString("frequency");
                        if(frequency.equals("w"))
                            frequency = " Week";
                        else if(frequency.equals("m"))
                            frequency = " Month";
                        else if(frequency.equals("y"))
                            frequency = " Year";
                        else if(frequency.equals("d"))
                            frequency = " Day";
                        else if(frequency.equals("on"))
                        {
                            frequency = "Today";
                            tenor ="";

                        }

                        HashMap<String, String> resultRow = new HashMap<>(); //create a hash map to set key and value pair

                        resultRow.put("tenor", tenor); //hash map will save key and its value
                        resultRow.put("bid", bid);
                        resultRow.put("offer", offer);
                        resultRow.put("frequency", frequency);
                        listTab1.add(resultRow); //now save all of the key value pairs to arraylist

                    }
                        resultArray = liborObject.getJSONArray("data"); //fetch array from studentsinfo object

                        for (int i = 0; i < resultArray.length(); i++) {

                            JSONObject resultElement = resultArray.getJSONObject(i); //get object from i index
                            String tenor = resultElement.getString("tenor");   //save string from variable 'id' to string
                         //   String bid = resultElement.getString("bid");
                            String rate = resultElement.getString("rate");
                            String frequency = resultElement.getString("frequency");
                            if(frequency.equals("w"))
                                frequency = " Week";
                            else if(frequency.equals("m"))
                                frequency = " Month";
                            else if(frequency.equals("y"))
                                frequency = " Year";
                            else if(frequency.equals("d"))
                                frequency = " Day";
                            else if(frequency.equals("on"))
                            {
                                frequency = "Today";
                                tenor ="";

                            }

                            HashMap<String, String> resultRow = new HashMap<>(); //create a hash map to set key and value pair

                            resultRow.put("tenor", tenor); //hash map will save key and its value
                           // resultRow.put("bid", bid);
                            resultRow.put("rate", rate);
                            resultRow.put("frequency", frequency);
                            listTab2.add(resultRow); //now save all of the key value pairs to arraylist


                        }
                JSONObject pkrvObjectData = pkrvObject.getJSONObject("data");
                Log.e(classtag, "Response from pkrv Data1: " + pkrvObjectData);
                JSONArray dateArray = pkrvObjectData.getJSONArray("dates");
                resultArray = pkrvObjectData.getJSONArray("data"); //fetch array from studentsinfo object
                Log.e(classtag, "Response from pkrv Data2: " + dateArray.get(0));
                TextView dateNew = (TextView)findViewById(R.id.dateNew);
                TextView dateOld = (TextView)findViewById(R.id.dateOld);

                String month=dateArray.get(1).toString().substring(5,7);
                String newDate=dateArray.get(1).toString().substring(8,10);
                if(month.equals("01"))
                    month = " JAN";
                else if(month.equals("02"))
                    month = " FEB";
                else if(month.equals("03"))
                    month = " MAR";
                else if(month.equals("04"))
                    month = " APR";
                else if(month.equals("05"))
                    month = " MAY";
                else if(month.equals("06"))
                    month = " JUN";
                else if(month.equals("07"))
                    month = " JUL";
                else if(month.equals("08"))
                    month = " AUG";
                else if(month.equals("09"))
                    month = " SEP";
                else if(month.equals("10"))
                    month = " OCT";
                else if(month.equals("11"))
                    month = " NOV";
                else if(month.equals("12"))
                    month = " DEC";

                dateNew.setText(dateArray.get(1).toString().substring(8,10) + month);

                month = dateArray.get(0).toString().substring(5,7);

                if(month.equals("01"))
                    month = " JAN";
                else if(month.equals("02"))
                    month = " FEB";
                else if(month.equals("03"))
                    month = " MAR";
                else if(month.equals("04"))
                    month = " APR";
                else if(month.equals("05"))
                    month = " MAY";
                else if(month.equals("06"))
                    month = " JUN";
                else if(month.equals("07"))
                    month = " JUL";
                else if(month.equals("08"))
                    month = " AUG";
                else if(month.equals("09"))
                    month = " SEP";
                else if(month.equals("10"))
                    month = " OCT";
                else if(month.equals("11"))
                    month = " NOV";
                else if(month.equals("12"))
                    month = " DEC";

                dateOld.setText(dateArray.get(0).toString().substring(8,10) + month);

                for (int i = 0; i < resultArray.length(); i++) {

                    JSONObject resultElement = resultArray.getJSONObject(i); //get object from i index
                    String tenor = resultElement.getString("new_tenor");   //save string from variable 'id' to string
                   // String frequency = tenor.substring(tenor.length()-2,tenor.length()-1);
                    if(tenor.contains("w") || tenor.contains("W"))
                        tenor =tenor.substring(0,tenor.length()-1) + " Week";
                    else if(tenor.contains("m") || tenor.contains("M"))
                        tenor =tenor.substring(0,tenor.length()-1) + " Month";
                    else if(tenor.contains("y") || tenor.contains("Y"))
                        tenor =tenor.substring(0,tenor.length()-1) + " Year";
                    else if(tenor.contains("d") || tenor.contains("D"))
                        tenor =tenor.substring(0,tenor.length()-1) + " Day";
                    else if(tenor.toLowerCase().equals("on"))
                    {
                        tenor = "Today";


                    }
                    String old_midrate = resultElement.getString("old_midrate");
                    String new_midrate = resultElement.getString("new_midrate");
                    String change = resultElement.getString("new_change");
                    String arrow="";
                    float impact = Float.parseFloat(change);
                    if(impact < 0)
                    {
                        arrow = String.valueOf(R.drawable.arrowdown);
                    }
                    else if (impact > 0)
                    {
                        arrow = String.valueOf(R.drawable.arrowup);
                    }



                    HashMap<String, String> resultRow = new HashMap<>(); //create a hash map to set key and value pair

                    resultRow.put("tenor", tenor); //hash map will save key and its value
                    resultRow.put("old_midrate", old_midrate);
                    resultRow.put("new_midrate", new_midrate);
                    resultRow.put("change", change);
                    resultRow.put("arrow", arrow);
                    //resultRow.put("frequency", frequency);
                    listTab3.add(resultRow); //now save all of the key value pairs to arraylist


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
            host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

                public void onTabChanged(String tabId) {
                    //.setCurrentItem(Integer.parseInt(tabId));
                   /* Toast.makeText(getApplicationContext(),
                            tabId,
                            Toast.LENGTH_LONG)
                            .show();*/

                    if(tabId == "PKRV")
                    {
                        ListAdapter adapter=new SimpleAdapter(


                                KiborActivity11.this,
                                listTab3,
                                R.layout.bucket_list4,
                                new String[]{"tenor","old_midrate","new_midrate","change","arrow"},
                                new int[]{R.id.tenor,R.id.midrate1,R.id.midrate2 ,R.id.change,R.id.arrow});
                        listViewPKRV.setAdapter(adapter);
                    }

                    else if(tabId == "LIBOR")
                    {
                        ListAdapter adapter=new SimpleAdapter(


                                KiborActivity11.this,
                                listTab2,
                                R.layout.bucketlist_liborkibor,
                                new String[]{"tenor","rate","frequency"},
                                new int[]{R.id.textViewDays,R.id.offer ,R.id.textViewMonth});
                        listViewLibor.setAdapter(adapter);
                    }

                    else
                    {
                        ListAdapter adapter=new SimpleAdapter(


                                KiborActivity11.this,
                                listTab1,
                                R.layout.bucketlist_liborkibor,
                                new String[]{"tenor","bid","offer","frequency"},
                                new int[]{R.id.textViewDays,R.id.bid,R.id.offer ,R.id.textViewMonth});
                        listViewKibor.setAdapter(adapter);
                    }
                    }

            });
                 ListAdapter adapter=new SimpleAdapter(


                                KiborActivity11.this,
                                listTab1,
                                R.layout.bucketlist_liborkibor,
                                new String[]{"tenor","bid","offer","frequency"},
                                new int[]{R.id.textViewDays,R.id.bid,R.id.offer ,R.id.textViewMonth});
                        listViewKibor.setAdapter(adapter);
                    }

                  /*  else if(position == 1)
                    {
                        ListAdapter adapter=new SimpleAdapter(


                                KiborActivity11.this,
                                listTab2,
                                R.layout.bucket_list3,
                                new String[]{"tenor","bid","offer","frequency","todays_change","imapact"},
                                new int[]{R.id.list_id,R.id.list_title,R.id.list_description ,R.id.list_category_tenor,R.id.list_post_frequencytime,R.id.list_author});
                        lv.setAdapter(adapter);
                    }

                    else if(position == 2)
                    {
                        ListAdapter adapter=new SimpleAdapter(


                                KiborActivity11.this,
                                listTab3,
                                R.layout.bucket_list3,
                                new String[]{"tenor","bid","offer","frequency","todays_change","imapact"},
                                new int[]{R.id.list_id,R.id.list_title,R.id.list_description ,R.id.list_category_tenor,R.id.list_post_frequencytime,R.id.list_author});
                        lv.setAdapter(adapter);
                    }

                    if(position == 3)
                    {
                        ListAdapter adapter=new SimpleAdapter(


                                KiborActivity11.this,
                                listTab4,
                                R.layout.bucket_list3,
                                new String[]{"tenor","bid","offer","frequency","todays_change","imapact"},
                                new int[]{R.id.list_id,R.id.list_title,R.id.list_description ,R.id.list_category_tenor,R.id.list_post_frequencytime,R.id.list_author});
                        lv.setAdapter(adapter);
                    } }*/


//
//            ListAdapter adapter=new SimpleAdapter(
//
//
//                    KiborActivity11.this,
//                    listTab1,
//                    R.layout.bucket_list4,
//                    new String[]{"tenor","bid","offer","frequency"},
//                    new int[]{R.id.textViewDays,R.id.bid,R.id.offer ,R.id.textViewMonth});
//            lv.setAdapter(adapter);







//            SimpleAdapter (Context context,                   //
//                    List<? extends Map<String, ?>> data,      //
//            int resource,                                     //
//            String[] from,                                    //
//            int[] to)                                         //
//this will pass your json values to the bucket_list xml and whatever it is stored of key 'tenor' will be
// displayed to text view list_tenor
        }
    }


//A}
