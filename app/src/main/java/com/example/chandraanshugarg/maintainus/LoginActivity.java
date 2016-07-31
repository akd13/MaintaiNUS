package com.example.chandraanshugarg.maintainus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.HttpClient;
import org.apache.http.HttpResponse;
import java.io.IOException;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.ExecutionContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;


public class LoginActivity extends AppCompatActivity {

    String login, pass;
    JSONParser jsonParser = new JSONParser();
    private static String url_login = "http://104.155.213.115/check.php";
    private static final String TAG_SUCCESS = "success";
    int success=0;
    String result;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(!CheckConnection.isNetworkAvailable(LoginActivity.this)){
            Toast.makeText(getApplicationContext(), "Please Check Internet Connection",Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    public void DummyLogin(View v){
        EditText login_input =(EditText)findViewById(R.id.userid);
        EditText pass_input =(EditText)findViewById(R.id.password);

        login = login_input.getText().toString();
        pass  = pass_input.getText().toString();

        new CheckLoginDetails().execute();
        CheckLoginDetails task = new CheckLoginDetails();

       try {
             result = task.execute().get();
        }

        catch(InterruptedException ie) {}
        catch(ExecutionException ie1)  {}


        if(result.equals("1")) {
            SharedPreferences sharedpreferences = getSharedPreferences("usernamePreference", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putBoolean("loggedIn",true);
            editor.putString("id", login);
            editor.commit();
            Toast.makeText(getApplicationContext(), "Login Successful",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(getApplicationContext(), "Login Unsuccessful",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    public class CheckLoginDetails extends AsyncTask<String, String, String> {

        protected String doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("login", login));
            params.add(new BasicNameValuePair("pass", pass));
            JSONObject json = jsonParser.makeHttpRequest(url_login, "POST", params);
            Log.d("Check Login Response", json.toString());

            try {
                success = json.getInt(TAG_SUCCESS);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return Integer.toString(success);
        }
    }

}
