package com.example.chandraanshugarg.maintainus;

import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateForm extends AppCompatActivity {
    public String c, d, name_of_user, time_of_complaint;
    boolean successful;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private static String url_update_complaint = "http://192.168.1.7/maintaiNUS/update_complaint.php";///////////////////IMPORTANT/////////
    private static final String TAG_SUCCESS = "success";
    EditText newCause;
    EditText newDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String description = getIntent().getStringExtra("desc");
        String cause = getIntent().getStringExtra("cause");

        EditText EditCause=(EditText)findViewById(R.id.UpdateCause);
        EditText EditDesc=(EditText)findViewById(R.id.UpdateDesc);
        EditCause.setText(cause);
        EditDesc.setText(description);
        newCause = (EditText) findViewById(R.id.UpdateCause);
        newDesc =  (EditText) findViewById(R.id.UpdateDesc);

        c = newCause.getText().toString();
        d = newDesc.getText().toString();

        name_of_user=getIntent().getStringExtra("username");
        time_of_complaint=getIntent().getStringExtra("time");

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }
    public void UpdateComplaint(View v){
        if (!CheckConnection.isNetworkAvailable(UpdateForm.this)) {
            Toast.makeText(getApplicationContext(), "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
        } else {
            c = newCause.getText().toString();
            d = newDesc.getText().toString();
            new UpdateComplaint().execute();
        }
    }
    class UpdateComplaint extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(UpdateForm.this);
            pDialog.setMessage("Updating Complaint");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            // pDialog.show();
        }

        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name_of_user", name_of_user));
            Log.d("Description updated",d);
            Log.d("Cause updated",c);
            params.add(new BasicNameValuePair("description", d));
            params.add(new BasicNameValuePair("cause", c));
            params.add(new BasicNameValuePair("time_of_complaint", time_of_complaint));
            JSONObject json1 = jsonParser.makeHttpRequest(url_update_complaint, "POST", params);
            Log.d("Update Response", json1.toString());

            try {
                int success = json1.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    successful = true;
                    finish();
                } else {
                    // failed to create product
                    //Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    successful = false;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            if (successful) {
                Toast.makeText(getApplicationContext(), "Complaint Successfully Updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "An Error Occurred", Toast.LENGTH_SHORT).show();
            }
            pDialog.dismiss();
        }
    }


    public void Cancel(View v){
        finish();
    }



}
