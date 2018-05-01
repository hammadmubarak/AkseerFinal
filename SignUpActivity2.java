package com.akseer.puzzlerz.akseer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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

public class SignUpActivity2 extends AppCompatActivity {
    private String classtag = SignUpActivity2.class.getSimpleName();  //return name of underlying class
    private ListView lv;
    private ProgressDialog progress;
    //private String url="https://raw.githubusercontent.com/iCodersLab/JSON-Parsing-in-Android-using-Android-Studio/master/index.html"; //passing url
    private String url = "http://videostreet.pk/tejori/tjApi/getSignUp"; //passing url
    ArrayList<HashMap<String, Object>> studentslist; //arraylist to save key value pair from json
    private EditText firstName,lastName,email,code,contactNumber,password;

    public static String id;

    public static int i;

    private  OkHttpClient client = new OkHttpClient();

    String message="";


    public String makePostRequest(String url, Map<String,String> headers) throws IOException {





        Headers headers1 = Headers.of(headers);

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("firstname",firstName.getText().toString())
                .addFormDataPart("lastname", lastName.getText().toString())
                .addFormDataPart("email", email.getText().toString())

                .addFormDataPart("mobile", code.getText().toString() + contactNumber.getText().toString())

                .addFormDataPart("password", password.getText().toString())


                .build();

        Request request = new Request.Builder()
                .url(url)
                .headers(headers1)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);

        email = (EditText) findViewById(R.id.email);
        code = (EditText) findViewById(R.id.code);
        contactNumber = (EditText) findViewById(R.id.contactNumber);
        password = (EditText) findViewById(R.id.password);

        studentslist = new ArrayList<>();


        // it will execute your AsyncTask

    }




    public void openMainActivity(View view) {
        new SignUpActivity2.getStudents().execute();

    }








    public class getStudents extends AsyncTask<Void, Void, Map<String, String>> {
        protected void onPreExecute() {
            super.onPreExecute(); //it will use pre defined preExecute method in async task
            progress = new ProgressDialog(SignUpActivity2.this);
            progress.setMessage("Loading data..."); // show what you want in the progress dialog
            progress.setCancelable(false); //progress dialog is not cancellable here



        }

        protected Map<String, String> doInBackground(Void... arg0) {

            Map<String, String> headers = new HashMap<>();
            headers.put("X-TJ-APIKEY", "876564123");
            headers.put("Content-Type", "application/x-www-form-urlencoded");
            String result = "";


            try {
                result = makePostRequest(url, headers);
//                Map<String, String> headers1 = new HashMap<>();
//
//                return headers1;

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
                    //JSONObject students = jObj.getJSONObject("success"); //fetch array from studentsinfo object
                    JSONArray stat  = jObj.getJSONArray("message");

                    message = stat.get(0).toString();









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


        protected void onPostExecute(Map<String, String> Result) {

            super.onPostExecute(Result);
            if (progress.isShowing()) {
                progress.dismiss();
            }
            if(message.equals("You have successfully signed up")){


                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT ).show();
                startActivity(new Intent(SignUpActivity2.this,NewsActivity4.class));
            }
            else {


                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT ).show();
            }

        }





////            SimpleAdapter (Context context,                   //
////                    List<? extends Map<String, ?>> data,      //
////            int resource,                                     //
////            String[] from,                                    //
////            int[] to)                                         //
////this will pass your json values to the bucket_list xml and whatever it is stored of key 'name' will be
//// displayed to text view list_Name
//        }
//    }

     /*   public void openMainActivity(View view) {

            Map<String, String> headers = new HashMap<>();
            headers.put("X-TJ-APIKEY", "876564123");
            headers.put("Content-Type", "application/x-www-form-urlencoded");
            String result = "";


            try {
                result = HttpRequestSomething_signup.makePostRequest(url, headers);
//                Map<String, String> headers1 = new HashMap<>();
//
//                return headers1;

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
                    //JSONObject students = jObj.getJSONObject("success"); //fetch array from studentsinfo object
                    JSONArray stat  = jObj.getJSONArray("message");
                    if(stat.get(0).equals("You have successfully signed up")){


                        Toast.makeText(getApplicationContext(),"false",Toast.LENGTH_SHORT ).show();
                    }
                    else {


                        Toast.makeText(getApplicationContext(),"FAILED",Toast.LENGTH_SHORT ).show();
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
            //return null;

            startActivity(new Intent(SignUpActivity2.this,SignUpActivity3.class));
            finish();



        }*/

    }





   /* private String classtag = SignUpActivity2.class.getSimpleName();  //return name of underlying class
    private ListView lv;
    private ProgressDialog progress;
    //private String url="https://raw.githubusercontent.com/iCodersLab/JSON-Parsing-in-Android-using-Android-Studio/master/index.html"; //passing url
    private String url = "http://videostreet.pk/tejori/tjApi/getSignUp"; //passing url
    ArrayList<HashMap<String, Object>> studentslist; //arraylist to save key value pair from json
    private EditText firstName,lastName,email;

    public static String id;

    public static int i;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);

        email = (EditText) findViewById(R.id.email);


        studentslist = new ArrayList<>();


        new SignUpActivity2.getStudents().execute(); // it will execute your AsyncTask

    }












    public class getStudents extends AsyncTask<Void, Void, Map<String, String>> {
        protected void onPreExecute() {
            super.onPreExecute(); //it will use pre defined preExecute method in async task
            progress = new ProgressDialog(SignUpActivity2.this);
            progress.setMessage("Loading data..."); // show what you want in the progress dialog
            progress.setCancelable(false); //progress dialog is not cancellable here



        }

        protected Map<String, String> doInBackground(Void... arg0) {

            Map<String, String> headers = new HashMap<>();
            headers.put("X-TJ-APIKEY", "876564123");
            headers.put("Content-Type", "application/x-www-form-urlencoded");
            String result = "";


            try {
                result = HttpRequestSomething_signup.makePostRequest(url, headers);
//                Map<String, String> headers1 = new HashMap<>();
//
//                return headers1;

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
                    //JSONObject students = jObj.getJSONObject("success"); //fetch array from studentsinfo object
                    JSONArray stat  = jObj.getJSONArray("message");
                    if(stat.get(0)=="You have successfully signed up"){


                        Toast.makeText(getApplicationContext(),"false",Toast.LENGTH_SHORT ).show();
                    }
                    else {


                        Toast.makeText(getApplicationContext(),"FAILED",Toast.LENGTH_SHORT ).show();
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


        protected void onPostExecute(Map<String, String> Result) {

            super.onPostExecute(Result);
            if (progress.isShowing()) {
                progress.dismiss();
            }}





////            SimpleAdapter (Context context,                   //
////                    List<? extends Map<String, ?>> data,      //
////            int resource,                                     //
////            String[] from,                                    //
////            int[] to)                                         //
////this will pass your json values to the bucket_list xml and whatever it is stored of key 'name' will be
//// displayed to text view list_Name
//        }
//    }

        public void openMainActivity(View view) {
            startActivity(new Intent(SignUpActivity2.this,SignUpActivity3.class));
            finish();



        }

    }




  *//*  private String classtag = BrokerActivity19.class.getSimpleName();  //return name of underlying class
    private ListView lv;
    private ProgressDialog progress;
    //private String url="https://raw.githubusercontent.com/iCodersLab/JSON-Parsing-in-Android-using-Android-Studio/master/index.html"; //passing url
    private String url = "http://videostreet.pk/tejori/tjApi/getSignUp"; //passing url
    ArrayList<HashMap<String, Object>> studentslist; //arraylist to save key value pair from json
    private EditText firstName,lastName,email;

    public static String id;

    public static int i;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
//        email = (EditText) findViewById(R.id.email);
//
//
//        studentslist = new ArrayList<>();
//
//        lv = (ListView) findViewById(R.id.list); //from home screen list view
     //
        //   new SignUpActivity2.getStudents().execute(); // it will execute your AsyncTask

    }
*//*











//    public class getStudents extends AsyncTask<Void, Void, Map<String, String>> {
//        protected void onPreExecute() {
//            super.onPreExecute(); //it will use pre defined preExecute method in async task
//            progress = new ProgressDialog(SignUpActivity2.this);
//            progress.setMessage("Loading data..."); // show what you want in the progress dialog
//            progress.setCancelable(false); //progress dialog is not cancellable here
//            progress.show();
//
//
//        }
//
//        protected Map<String, String> doInBackground(Void... arg0) {
//
//            Map<String, String> headers = new HashMap<>();
//            headers.put("X-TJ-APIKEY", "876564123");
//            headers.put("Content-Type", "application/x-www-form-urlencoded");
//            String result = "";
//
//
//            try {
//                result = HttpRequestSomething_signup.makePostRequest(url, headers);
//                Map<String, String> headers1 = new HashMap<>();
//                headers1.put("firstname",firstName.getText().toString());
//                headers1.put("lastName",lastName.getText().toString());
//                return headers1;
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            Log.e("Result", result);
//            //HTTP_Handler hh = new HTTP_Handler(); // object of HTTP_Handler
//            // String jString = hh.makeHTTPCall(url); //calling makeHTTPCall method and string its response in a string
//            Log.e(classtag, "Response from URL: " + result);
//            if (result != null) {
//                try {
//
//
//
//                    JSONObject jObj = new JSONObject(result); //our json data starts with the object
//
//                    if(jObj.names().get(0).equals("success")){
//
//
//                        Toast.makeText(getApplicationContext(),"SUCCESS"+jObj.getString("success"),Toast.LENGTH_SHORT ).show();
//                    }
//                    JSONArray students = jObj.getJSONArray("data"); //fetch array from studentsinfo object
//
//
//                        JSONObject student = students.getJSONObject(i); //get object from i index
//                        id = student.getString("id");   //save string from variable 'id' to string
//
//
//
//
//
//                } catch (final JSONException e) {
//                    Log.e(classtag, "Json parsing error: " + e.getMessage());
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(getApplicationContext(),
//                                    "Json parsing error: " + e.getMessage(),
//                                    Toast.LENGTH_LONG)
//                                    .show(); //show if you catch any exception with data
//                        }
//                    });
//                }
//            } else {
//                Log.e(classtag, "Couldn't get json from server.");
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getApplicationContext(),
//                                "Couldn't get json from server. Check internet connection!",
//                                Toast.LENGTH_LONG).show();//show if you are unable to connect with the internet or if jString is null
//                    }
//                });
//            }
//            return null;
//        }
//
//
//        protected void onPostExecute(Map<String, String> Result) {
//
//            super.onPostExecute(Result);
//            if (progress.isShowing()) {
//                progress.dismiss();
//            }
//
//
//
//
//
////            SimpleAdapter (Context context,                   //
////                    List<? extends Map<String, ?>> data,      //
////            int resource,                                     //
////            String[] from,                                    //
////            int[] to)                                         //
////this will pass your json values to the bucket_list xml and whatever it is stored of key 'name' will be
//// displayed to text view list_Name
//        }
//    }
*/

}
