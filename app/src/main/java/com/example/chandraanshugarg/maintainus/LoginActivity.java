package com.example.chandraanshugarg.maintainus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

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
        EditText login=(EditText)findViewById(R.id.userid);
        EditText pass=(EditText)findViewById(R.id.password);
        if(login.getText().toString().equals("chanda")&&pass.getText().toString().equals("chanda")){
            SharedPreferences sharedpreferences = getSharedPreferences("usernamePreference", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putBoolean("loggedIn",true);
            editor.putString("id", login.getText().toString());
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
}
