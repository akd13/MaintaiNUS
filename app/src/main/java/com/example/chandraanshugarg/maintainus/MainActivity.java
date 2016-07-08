package com.example.chandraanshugarg.maintainus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        TextView t1=(TextView)findViewById(R.id.username);
        SharedPreferences pref = getSharedPreferences("usernamePreference", MODE_PRIVATE);
        String temp=pref.getString("id","Guest");
        t1.setText(temp);
    }
    public void logout(View v){
        SharedPreferences pref = getSharedPreferences("usernamePreference", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("loggedIn");
        editor.putBoolean("loggedIn",false);
        editor.remove("id");
        editor.commit();
        Toast.makeText(getApplicationContext(), "Successfully Logged Out",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
    public void openForm(View v){
        Intent intent=new Intent(this,ComplaintForm.class);
        startActivity(intent);
    }

}
