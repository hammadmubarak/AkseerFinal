package com.akseer.puzzlerz.akseer;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.content.Intent;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.akseer.puzzlerz.akseer.LoginActivity1.loginButton;

public class NewsActivity4 extends AppCompatActivity {





    private String classtag= NewsActivity4.class.getSimpleName();  //return name of underlying class
    private ListView lv;
    private ProgressDialog progress;
    //private String url="https://raw.githubusercontent.com/iCodersLab/JSON-Parsing-in-Android-using-Android-Studio/master/index.html"; //passing url
    private String url="http://videostreet.pk/tejori/tjApi/getCategoryData"; //passing url
    ArrayList<HashMap<String,Object>> studentslist; //arraylist to save key value pair from json

    public static String id;

    public static int i;

 private JSONObject firstItem;











    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.News:

                    return true;
                case R.id.Market:

                    startActivity(new Intent(NewsActivity4.this, MarketsActivity6.class));finish();

                    return true;

                case R.id.Akseer:
                    startActivity(new Intent(NewsActivity4.this, AkseerResearchActivity15.class));finish();

                    return true;

               case R.id.Broker:

                   startActivity(new Intent(NewsActivity4.this, BrokerActivity19.class));finish();

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
                startActivity(new Intent(NewsActivity4.this, DisclaimerActivity16.class));
                return true;
         /*   case R.id.PerformanceReviews:
                startActivity(new Intent(NewsActivity4.this, PR18Activity.class));
                return true;
            case R.id.additiondeletion:
                startActivity(new Intent(NewsActivity4.this, AdditionDeletion17.class));
                return true;*/
            case R.id.logout:
                SharedPreferences settings = getSharedPreferences(LoginActivity1.PREFS_NAME, 0); // 0 - for private mode
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("hasLoggedIn", false);
                editor.commit();
                LoginManager.getInstance().logOut();

                startActivity(new Intent(NewsActivity4.this, LoginActivity1.class));
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
        setContentView(R.layout.activity_home4);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.News);



        studentslist=new ArrayList<>();
        lv= (ListView) findViewById(R.id.list); //from home screen list view
        new NewsActivity4.getStudents().execute(); // it will execute your AsyncTask


    }
    public class getStudents extends AsyncTask<Void,Void,Void> {
        protected void onPreExecute(){
            super.onPreExecute(); //it will use pre defined preExecute method in async task
            progress=new ProgressDialog(NewsActivity4.this);
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
                    firstItem = students.getJSONObject(0);
                    for ( i = 1; i < students.length(); i++) {


                        JSONObject student = students.getJSONObject(i); //get object from i index
                        id = student.getString("id");   //save string from variable 'id' to string
                        String title = student.getString("title");
                        String description = student.getString("description");
                        String category_name = student.getString("category_name");
                        String post_datetime = student.getString("post_datetime");
                        String author = student.getString("author");
                        String readMore = student.getString("readmore_link");

                        String post_image = student.getString("post_image");
                        String post_image2 = student.getString("post_image2");
                        Log.e(classtag, "Response: " + post_image);
                        if(post_image.equals("null")) {
                            post_image = "https://www.google.com.pk/search?q=default+image&source=lnms&tbm=isch&sa=X&ved=0ahUKEwjz25Lr4pTaAhXDhqQKHdCDD1MQ_AUICigB&biw=1536&bih=734#imgrc=D9qNVeBP9D2X_M:";
                            Log.e(classtag, "Response from URL: " + post_image);
                        }// String urlImg = "http://es.opendomo.org/files/android-logo.png";
                        //String


/*
                        if(id != null){
                            Intent intent = new Intent(NewsActivity4.this , NewsInsideActivity5.class);
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
                        studentdata.put("readMore", readMore);
                        //    studentdata.put("flag", R.drawable.blank);
                        // studentdata.put("post_image", getBitmapFromURL(post_image));
                        studentdata.put("post_image", post_image);
                        studentdata.put("post_image2", post_image2);


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


                    NewsActivity4.this,
                    studentslist,
                    R.layout.bucket_list_news4,
                    new String[]{"id","title","description","category_name","post_datetime","author","post_image","impact","description_html","readMore","post_image2"},
                    new int[]{R.id.list_id,R.id.list_title,R.id.list_description ,R.id.category,R.id.list_post_datetime,R.id.list_author,R.id.list_post_image,R.id.impact,R.id.list_description_html,R.id.readMoreLink,R.id.list_post_image2}){
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View v = super.getView(position, convertView, parent);

                    ImageView img = (ImageView) v.getTag();
                    if(img == null){
                        img = (ImageView) v.findViewById(R.id.list_post_image);
                        v.setTag(img); // <<< THIS LINE !!
                    }
                    String url = String.valueOf(((Map)getItem(position)).get("post_image"));


                    if(url.equals("null")) {
                        img.setImageResource(R.drawable.newsprofile);
                        Log.e(classtag, "Res: " + url);
                    }
                    else
                    Picasso.with(v.getContext()).load(url).into(img);

                    TextView impact = (TextView) v.findViewById(R.id.impact);
                    if(impact.getText().equals("+"))
                    {
                        impact.setText("POSITIVE");
                        impact.setTextColor(Color.parseColor("#008000"));
                    }

                    else if(impact.getText().equals("-")){
                        impact.setText("NEGATIVE");
                        impact.setTextColor(Color.parseColor("#e82e25"));
                    }

                    else {
                        impact.setText("NEUTRAL");
                        impact.setTextColor(Color.parseColor("#F0E68C"));
                    }

                    TextView list_title = (TextView) v.findViewById(R.id.list_title);
                    TextView list_description = (TextView) v.findViewById(R.id.list_description);
                    TextView list_category_name = (TextView) v.findViewById(R.id.category);
                    TextView list_post_image2 = (TextView) v.findViewById(R.id.list_post_image2);
                    TextView readMoreLink = (TextView) v.findViewById(R.id.readMoreLink);
                    TextView list_post_datetime = (TextView) v.findViewById(R.id.list_post_datetime);
                    ImageView list_post_image = (ImageView) v.findViewById(R.id.list_post_image);
                 //   TextView list_description_html = (TextView) v.findViewById(R.id.list_description_html);
                    list_title.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            // TODO Auto-generated method stub
                            Intent intent = new Intent(NewsActivity4.this, NewsInsideActivity5.class);
                            intent.putExtra("list_title", list_title.getText());    //do this for all data objects
                            intent.putExtra("list_description", list_description.getText());
                            intent.putExtra("list_category_name", list_category_name.getText());
                           // intent.putExtra("list_author", list_author.getText());
                            intent.putExtra("list_post_datetime", list_post_datetime.getText());
                            intent.putExtra("list_post_image", url);
                            intent.putExtra("impact", impact.getText());
                            intent.putExtra("list_post_image2", list_post_image2.getText());
                            intent.putExtra("readMoreLink", readMoreLink.getText());
                            //intent.putExtra("list_description_html", list_description_html.getText());
                            startActivity(intent);
                        }
                    });

                    list_description.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            // TODO Auto-generated method stub
                            Intent intent = new Intent(NewsActivity4.this, NewsInsideActivity5.class);
                            intent.putExtra("list_title", list_title.getText());    //do this for all data objects
                            intent.putExtra("list_description", list_description.getText());
                            intent.putExtra("list_category_name", list_category_name.getText());
                            //intent.putExtra("list_author", list_author.getText());
                            intent.putExtra("list_post_datetime", list_post_datetime.getText());
                            intent.putExtra("list_post_image", url);
                            intent.putExtra("impact", impact.getText());
                            intent.putExtra("list_post_image2", list_post_image2.getText());
                            intent.putExtra("readMoreLink", readMoreLink.getText());
                           // intent.putExtra("list_description_html", list_description_html.getText());
                            startActivity(intent);
                        }
                    });

                    list_category_name.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            // TODO Auto-generated method stub
                            Intent intent = new Intent(NewsActivity4.this, NewsInsideActivity5.class);
                            intent.putExtra("list_title", list_title.getText());    //do this for all data objects
                            intent.putExtra("list_description", list_description.getText());
                            intent.putExtra("list_category_name", list_category_name.getText());
                            //intent.putExtra("list_author", list_author.getText());
                            intent.putExtra("list_post_datetime", list_post_datetime.getText());
                            intent.putExtra("list_post_image", url);
                            intent.putExtra("impact", impact.getText());
                            intent.putExtra("list_post_image2", list_post_image2.getText());
                            intent.putExtra("readMoreLink", readMoreLink.getText());
                            //intent.putExtra("list_description_html", list_description_html.getText());
                            startActivity(intent);
                        }
                    });
/*
                    list_author.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            // TODO Auto-generated method stub
                            Intent intent = new Intent(NewsActivity4.this, NewsInsideActivity5.class);
                            intent.putExtra("list_title", list_title.getText());    //do this for all data objects
                            intent.putExtra("list_description", list_description.getText());
                            intent.putExtra("list_category_name", list_category_name.getText());
                            intent.putExtra("list_author", list_author.getText());
                            intent.putExtra("list_post_datetime", list_post_datetime.getText());
                            intent.putExtra("list_post_image", url);
                            intent.putExtra("list_description_html", list_description_html.getText());
                            startActivity(intent);
                        }
                    });*/

                    list_post_datetime.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            // TODO Auto-generated method stub
                            Intent intent = new Intent(NewsActivity4.this, NewsInsideActivity5.class);
                            intent.putExtra("list_title", list_title.getText());    //do this for all data objects
                            intent.putExtra("list_description", list_description.getText());
                            intent.putExtra("list_category_name", list_category_name.getText());
  //                          intent.putExtra("list_author", list_author.getText());
                            intent.putExtra("list_post_datetime", list_post_datetime.getText());
                            intent.putExtra("list_post_image", url);
                            intent.putExtra("list_post_image2", list_post_image2.getText());
                            intent.putExtra("readMoreLink", readMoreLink.getText());
    //                        intent.putExtra("list_description_html", list_description_html.getText());
                            intent.putExtra("impact", impact.getText());
                            startActivity(intent);
                        }
                    });

                    list_post_image.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            // TODO Auto-generated method stub
                            Intent intent = new Intent(NewsActivity4.this, NewsInsideActivity5.class);
                            intent.putExtra("list_title", list_title.getText());    //do this for all data objects
                            intent.putExtra("list_description", list_description.getText());
                            intent.putExtra("list_category_name", list_category_name.getText());
                            //intent.putExtra("list_author", list_author.getText());
                            intent.putExtra("list_post_datetime", list_post_datetime.getText());
                            intent.putExtra("list_post_image", url);
                            //intent.putExtra("list_description_html", list_description_html.getText());
                            intent.putExtra("impact", impact.getText());
                            intent.putExtra("list_post_image2", list_post_image2.getText());
                            intent.putExtra("readMoreLink", readMoreLink.getText());
                            startActivity(intent);
                        }
                    });

                   /* list_description_html.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            // TODO Auto-generated method stub
                            Intent intent = new Intent(NewsActivity4.this, NewsInsideActivity5.class);
                            intent.putExtra("list_title", list_title.getText());    //do this for all data objects
                            intent.putExtra("list_description", list_description.getText());
                            intent.putExtra("list_category_name", list_category_name.getText());
                            intent.putExtra("list_author", list_author.getText());
                            intent.putExtra("list_post_datetime", list_post_datetime.getText());
                            intent.putExtra("list_post_image", url);
                            intent.putExtra("list_description_html", list_description_html.getText());
                            startActivity(intent);
                        }
                    });*/

                    v.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            // TODO Auto-generated method stub
                            Intent intent = new Intent(NewsActivity4.this, NewsInsideActivity5.class);
                            intent.putExtra("list_title", list_title.getText());    //do this for all data objects
                            intent.putExtra("list_description", list_description.getText());
                            intent.putExtra("list_category_name", list_category_name.getText());
                          //  intent.putExtra("list_author", list_author.getText());
                            intent.putExtra("list_post_datetime", list_post_datetime.getText());
                            intent.putExtra("list_post_image", url);
                            //intent.putExtra("list_description_html", list_description_html.getText());
                            intent.putExtra("impact", impact.getText());
                            intent.putExtra("list_post_image2", list_post_image2.getText());
                            intent.putExtra("readMoreLink", readMoreLink.getText());
                            startActivity(intent);
                        }
                    });
                    return v;
                }


            };






        lv.setAdapter(adapter);

            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View head = inflater.inflate(R.layout.bucket_list_news4_2, lv, false);
            TextView title = head.findViewById(R.id.list_title2);
            TextView description = head.findViewById(R.id.list_description2);
            ImageView post_image = head.findViewById(R.id.list_post_image2);
            TextView category = head.findViewById(R.id.category);
            TextView impact = head.findViewById(R.id.impact);
            TextView post_image2 = head.findViewById(R.id.list_post_image3);
            TextView readMoreLink = head.findViewById(R.id.readMoreLink);

            TextView datetime = head.findViewById(R.id.list_post_datetime2);

            //title.setText(firstItem.getString("title"));
          //  id = firstItem.getString("id");   //save string from variable 'id' to string
            //String title = null;
            try {
                title.setText(firstItem.getString("title"));
                description.setText(firstItem.getString("description"));
                category.setText(firstItem.getString("category_name"));
                if(firstItem.getString("impact").equals("+")) {
                    impact.setText("POSITIVE");
                    impact.setTextColor(Color.parseColor("#008000"));
                }
                datetime.setText(firstItem.getString("post_datetime"));
                url=firstItem.getString("post_image");
                Picasso.with(head.getContext()).load(firstItem.getString("post_image")).into(post_image);

            } catch (JSONException e) {
                e.printStackTrace();
            }
           /* String description = student.getString("description");
            String category_name = student.getString("category_name");
            String post_datetime = student.getString("post_datetime");
            String author = student.getString("author");

            String post_image = student.getString("post_image");
            // String urlImg = "http://es.opendomo.org/files/android-logo.png";
            //String


*//*
                        if(id != null){
                            Intent intent = new Intent(NewsActivity4.this , NewsInsideActivity5.class);
                            intent.putExtra("id" , id);    //do this for all data objects
                            startActivity(intent);
                        }*//*


            String impact = student.getString("impact");
            String description_html = student.getString("description_html");*/

            //title.setText("hammad");
            lv.addHeaderView(head);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {

                    Intent intent = new Intent(NewsActivity4.this , NewsInsideActivity5.class);
                    TextView title = head.findViewById(R.id.list_title2);
                    TextView description = head.findViewById(R.id.list_description2);
                    ImageView post_image = head.findViewById(R.id.list_post_image2);
                    TextView category = head.findViewById(R.id.category);
                    TextView impact = head.findViewById(R.id.impact);
                    TextView datetime = head.findViewById(R.id.list_post_datetime2);
                    intent.putExtra("list_title", title.getText());    //do this for all data objects
                            intent.putExtra("list_description", description.getText());
                            intent.putExtra("list_category_name", category.getText());
                           //intent.putExtra("list_author", author.getText());
                           intent.putExtra("list_post_datetime", datetime.getText());
                            intent.putExtra("list_post_image", url);
                    try {
                        intent.putExtra("list_post_image2", firstItem.getString("post_image2"));
                        intent.putExtra("readMoreLink", firstItem.getString("readmore_link"));
                       // intent.putExtra("readMoreLink", "null");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    intent.putExtra("impact", impact.getText());   //do this for all data objects
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


    public static String getBitmapFromURL(String src) {
        try {
            Log.e("src", src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap", String.valueOf(myBitmap));
            return String.valueOf(myBitmap);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
            return null;
        }
    }
    public void onClick(View view) {
        startActivity(new Intent(NewsActivity4.this, NewsInsideActivity5.class));
    }
//    public void openNews(View v){
//        startActivity(new Intent(NewsActivity4.this, NewsInsideActivity5.class));
//    }
}

