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

public class ComplaintForm extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    boolean successful;
    EditText causeInput;
    EditText descInput;
    //myDBHandler dbHandler;
    public String location, category, cause, description;
    CheckBox occupantCheck;
    public String occupant;
    private ProgressDialog pDialog;

    String name_of_user;

    JSONParser jsonParser = new JSONParser();
    private static String url_create_complaint = "http://104.155.213.115/create_complaint.php";///////////////////IMPORTANT/////////
    private static final String TAG_SUCCESS = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_form);
        causeInput = (EditText) findViewById(R.id.CauseInput);
       //causeText  = (TextView) findViewById(R.id.DescInput);
       descInput = (EditText) findViewById(R.id.DescInput);
        // descText  = (TextView) findViewById(R.id.descText);
//        dbHandler = new myDBHandler(this, null, null, 1);
//        printDatabase();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(this);
        List<String> categories1 = new ArrayList<>();
        categories1.add("Tembusu College");
        categories1.add("Cinnamon College");
        categories1.add("CAPT");
        categories1.add("RC4");
        categories1.add("PGPR");
        categories1.add("UTown Residences");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,categories1);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter1);



        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner2.setOnItemSelectedListener(this);
        List<String> categories2 = new ArrayList<>();
        categories2.add("Air Conditioning");
        categories2.add("Fan");
        categories2.add("Lighting");
        categories2.add("Electricity");
        categories2.add("Furniture");
        categories2.add("Plumbing");
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,categories2);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter2);

        name_of_user=getIntent().getExtras().getString("username");


        Button btnCreateComplaint = (Button) findViewById(R.id.SubmitButtonClick);
        btnCreateComplaint.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // creating new product in background thread
                if (!CheckConnection.isNetworkAvailable(ComplaintForm.this)) {
                    Toast.makeText(getApplicationContext(), "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                } else {
                    description=descInput.getText().toString();
                    cause=causeInput.getText().toString();

                    occupantCheck=(CheckBox)findViewById(R.id.checkBox);
                    if(occupantCheck.isChecked()){
                        occupant="true";
                    }
                    else{
                        occupant="false";
                    }
                    new CreateNewComplaint().execute();
                }

            }
        });
    }
    class CreateNewComplaint extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ComplaintForm.this);
            pDialog.setMessage("Submitting Complaint");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
           // pDialog.show();
        }

        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name_of_user", name_of_user));
            params.add(new BasicNameValuePair("location", location));
            params.add(new BasicNameValuePair("category", category));
            params.add(new BasicNameValuePair("description", description));
            params.add(new BasicNameValuePair("cause", cause));
            params.add(new BasicNameValuePair("occupant", occupant));

            JSONObject json = jsonParser.makeHttpRequest(url_create_complaint, "POST", params);

           Log.d("Create Response", json.toString()); //IMPORTANT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    //Toast.makeText(getApplicationContext(), "Complaint Successfully Registered", Toast.LENGTH_SHORT).show();
                    successful=true;
                    Intent intent = new Intent(ComplaintForm.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    // failed to create product
                    //Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    successful = false;
                    Intent intent = new Intent(ComplaintForm.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            if(successful){
                Toast.makeText(getApplicationContext(), "Complaint Successfully Registered", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
            pDialog.dismiss();
        }
    }


//    public void printDatabase() {
//        String dbString = dbHandler.databasetoString();
//        causeInput.setText(dbString);
//        causeInput.setText("");
//        descInput.setText(dbString);
//        descInput.setText("");
//    }

//    public void SubmitButtonClick(View view) {
//        if(!CheckConnection.isNetworkAvailable(ComplaintForm.this)){
//            Toast.makeText(getApplicationContext(), "Please Check Internet Connection",Toast.LENGTH_SHORT).show();
//        }
//        else {
////            CauseDescription causedescription = new CauseDescription(causeInput.getText().toString(), descInput.getText().toString());
////            dbHandler.addCauseDesc(causedescription);
////            printDatabase();
//        }
//    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getItemAtPosition(0)=="Tembusu College"){
            location=parent.getItemAtPosition(position).toString();
        }
        else{
            category=parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
