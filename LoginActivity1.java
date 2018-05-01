package com.akseer.puzzlerz.akseer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity1 extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener  {
    public static final String PREFS_NAME = "MyPrefsFile";

    //private SignInButton SignIn;
    public static GoogleApiClient googleApiClient;
    private static final int REQ_CODE = 9001;

    Button button;
    Button button2;
    private String classtag = LoginActivity1.class.getSimpleName();
    private ProgressDialog progress;
    //private String url="https://raw.githubusercontent.com/iCodersLab/JSON-Parsing-in-Android-using-Android-Studio/master/index.html"; //passing url
    private String url = "http://videostreet.pk/tejori/tjApi/getLoggedIn"; //passing url
    ArrayList<HashMap<String, Object>> studentslist; //arraylist to save key value pair from json
    private EditText email, password;

    public static String id;

    public static int i;

    private OkHttpClient client = new OkHttpClient();

    String message = "";
    ImageButton googleButton;

    public static LoginButton loginButton ;
    ImageButton fbButton;
    CallbackManager callbackManager;
    private static final String EMAIL = "email";


    public String makePostRequest(String url, Map<String, String> headers) throws IOException {


        Headers headers1 = Headers.of(headers);

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("email", email.getText().toString())
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
        setContentView(R.layout.activity_login1);
        SharedPreferences settings = getSharedPreferences(LoginActivity1.PREFS_NAME, 0);
//Get "hasLoggedIn" value. If the value doesn't exist yet false is returned
        boolean hasLoggedIn = settings.getBoolean("hasLoggedIn", true);

        if(hasLoggedIn)
        {
            startActivity(new Intent(LoginActivity1.this, AkseerResearchActivity15.class));
            finishAffinity();
        }

      //  SignIn = (SignInButton)findViewById(R.id.glogin);
        googleButton = (ImageButton)findViewById(R.id.googleButton);
      //  SignIn.setOnClickListener(this);
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions).build();







        getSupportActionBar().hide();

        googleButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                signIn();
                //  new LoginActivity1.getStudents().execute();

            }


        });











        loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));
        fbButton = (ImageButton)findViewById(R.id.fbButton);
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                startActivity(new Intent(LoginActivity1.this, AkseerResearchActivity15.class));
                finishAffinity();
                finish();

            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(),
                        "Cancel",
                        Toast.LENGTH_LONG)
                        .show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(getApplicationContext(),
                        exception.getMessage().toString(),
                        Toast.LENGTH_LONG)
                        .show();
            }
        });
        fbButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
               if(v==fbButton)
               {  SharedPreferences settings = getSharedPreferences(LoginActivity1.PREFS_NAME, 0); // 0 - for private mode
                   SharedPreferences.Editor editor = settings.edit();

//Set "hasLoggedIn" to true
                   editor.putBoolean("hasLoggedIn", true);

// Commit the edits!
                   editor.commit();
                   loginButton.performClick();
               }

            }


        });
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        button = (Button) findViewById(R.id.loginButton);

        button.setOnClickListener(new View.OnClickListener() {

                                      @Override
                                      public void onClick(View v) {
                                          // TODO Auto-generated method stub
                                     //     startActivity(new Intent(LoginActivity1.this, NewsActivity4.class));
                                         new LoginActivity1.getStudents().execute();

                                      }


                                  }


        );

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_CODE)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }
        //  super.onActivityResult(requestCode, resultCode, data);
    }

    //  public void openHomeActivity(View view){
//       startActivity(new Intent(LoginActivity1.this, NewsActivity4.class));
//
//

    private void signIn(){



    Intent intent =Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
    startActivityForResult(intent,REQ_CODE);


    }

    public static void signOut()
    {
        Auth.GoogleSignInApi.signOut(googleApiClient);




    }

    private void handleResult(GoogleSignInResult result){

        if(result.isSuccess())
        {
            SharedPreferences settings = getSharedPreferences(LoginActivity1.PREFS_NAME, 0); // 0 - for private mode
            SharedPreferences.Editor editor = settings.edit();

//Set "hasLoggedIn" to true
            editor.putBoolean("hasLoggedIn", true);

// Commit the edits!
            editor.commit();
            startActivity(new Intent(LoginActivity1.this, AkseerResearchActivity15.class));
            finishAffinity();
        }

        else
            Toast.makeText(getApplicationContext(),
                    result.getStatus().toString(),
                    Toast.LENGTH_LONG)
                    .show();




    }
    private void updateUI(boolean isLogin){






    }








    public void openSignUpActivity(View view) {
        startActivity(new Intent(LoginActivity1.this, SignUpActivity2.class));
    }

    public void openForgetPassword(View view) {
        startActivity(new Intent(LoginActivity1.this, ForgetPassword.class));
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public class getStudents extends AsyncTask<Void, Void, Map<String, String>> {
        protected void onPreExecute() {
            super.onPreExecute(); //it will use pre defined preExecute method in async task
            progress = new ProgressDialog(LoginActivity1.this);
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
                    JSONArray stat = jObj.getJSONArray("message");

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
                                "Couldn't get data from server. Check internet connection!",
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
            if (message.equals("You have successfully signed in")) {

                SharedPreferences settings = getSharedPreferences(LoginActivity1.PREFS_NAME, 0); // 0 - for private mode
                SharedPreferences.Editor editor = settings.edit();

//Set "hasLoggedIn" to true
                editor.putBoolean("hasLoggedIn", true);

// Commit the edits!
                editor.commit();
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity1.this, AkseerResearchActivity15.class));
                finishAffinity();
            } else {


                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }

        }

    }
}