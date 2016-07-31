
package com.example.chandraanshugarg.maintainus;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.StrictMode;
import android.widget.Toast;

public class EditComplaintForm extends AppCompatActivity {

    public String name_of_user,location, category, cause, description, occupant,time_of_complaint;
    boolean successful;

    EditText txtLocation;
    EditText txtCategory;
    EditText txtDesc;
    EditText txtCause;

    Button btnSave;
    Button btnDelete;

    String pid;

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    // single complaint url
    private static final String url_complaint_details = "http://192.168.1.7/maintaiNUS/read_complaint.php";

    // url to update complaint
    private static final String url_update_complaint = "http://192.168.1.7/maintaiNUS/update_complaint.php";

    // url to delete complaint
    private static final String url_delete_complaint = "http://192.168.1.7/maintaiNUS/delete_complaint.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_COMPLAINT_TABLE = "complaint_table";
    private static final String TAG_PID = "pid";
    private static final String TAG_LOCATION = "location";
    private static final String TAG_CATEGORY = "category";
    private static final String TAG_CAUSE = "cause";
    private static final String TAG_DESCRIPTION = "description";


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_complaint);

        // save button
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        // getting complaint details from intent
        Intent i = getIntent();

        // getting complaint id (pid) from intent
        pid = i.getStringExtra(TAG_PID);


        // Getting complete complaint details in background thread
        new GetComplaintDetails().execute();

        // save button click event
        btnSave.setOnClickListener(new View.OnClickListener() {

           // @Override
            public void onClick(View arg0) {
                // starting background task to update complaint
                new SaveComplaintDetails().execute();
            }
        });

        // Delete button click event
        btnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // deleting complaint in background thread
                new DeleteComplaintTemp().execute();
           }
        });

    }

    /**
     * Background Async Task to Get complete complaint details
     * */
    class GetComplaintDetails extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditComplaintForm.this);
            pDialog.setMessage("Loading complaint details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }




        /**
         * Getting complaint details in background thread
         * */
        protected String doInBackground(String... params) {

            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    // Check for success tag
                    int success;
                    try {
                        // Building Parameters
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                       // params.add(new BasicNameValuePair("pid", pid));

                        // getting complaint details by making HTTP request
                        // Note that complaint details url will use GET request

                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);

                        JSONObject json = jsonParser.makeHttpRequest(url_complaint_details, "GET", params);

                        // check your log for json response
                        Log.d("Complaint Details", json.toString());

                        // json success tag
                        success = json.getInt(TAG_SUCCESS);
                        Log.d("Complaint number", String.valueOf(success));

                        if (success == 1) {
                            // successfully received complaint details
                            successful=true;
                            JSONArray complaintObj = json.getJSONArray(TAG_COMPLAINT_TABLE); // JSON Array

                          //  get first complaint object from JSON Array
                            JSONObject complaint_table = complaintObj.getJSONObject(0);
                            Log.d("Location",String.valueOf(complaintObj.getJSONObject(0)));

                            // complaint with this pid found
                            // Edit Text
                            txtLocation = (EditText) findViewById(R.id.inputLocation);
                            txtCategory = (EditText) findViewById(R.id.inputCategory);
                            txtCause = (EditText) findViewById(R.id.inputCause);
                            txtDesc = (EditText) findViewById(R.id.inputDesc);


                            // display complaint data in EditText
                            txtLocation.setText(complaint_table.getString(TAG_LOCATION));
                            txtCategory.setText(complaint_table.getString(TAG_CATEGORY));
                            txtCause.setText(complaint_table.getString(TAG_CAUSE));
                            txtDesc.setText(complaint_table.getString(TAG_DESCRIPTION));

                        }else{
                            successful=false;// product with pid not found
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            if(successful){
                Toast.makeText(getApplicationContext(), "Complaint Loaded", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
            pDialog.dismiss();
        }
    }
    /**
     * Background Async Task to  Save product Details
     * */
    class SaveComplaintDetails extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditComplaintForm.this);
            pDialog.setMessage("Saving complaint ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Saving product
         * */
        protected String doInBackground(String... args) {


            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_PID, pid));
            params.add(new BasicNameValuePair(TAG_LOCATION, location));
            params.add(new BasicNameValuePair(TAG_CATEGORY, category));
            params.add(new BasicNameValuePair(TAG_CAUSE, cause));
            params.add(new BasicNameValuePair(TAG_DESCRIPTION, description));

            // sending modified data through http request
            // Notice that update product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_update_complaint,
                    "POST", params);

            // check json success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
                Log.d("Update successful", String.valueOf(success));

                if (success == 1) {
                    // successfully updated
                    Intent i = getIntent();
                    // send result code 100 to notify about product update
                    setResult(100, i);
                    finish();
                } else {
                    // failed to update product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product updated
            pDialog.dismiss();
        }
    }

    /*****************************************************************
     * Background Async Task to Delete Product
     * */
    class DeleteComplaintTemp extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditComplaintForm.this);
            pDialog.setMessage("Deleting Complaint...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Deleting product
         * */
        protected String doInBackground(String... args) {

            // Check for success tag
            int success;
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("name_of_user", name_of_user));
                params.add(new BasicNameValuePair("time_of_complaint", time_of_complaint));

                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(url_delete_complaint, "POST", params);

                // check your log for json response
                Log.d("Delete Complaint", json.toString());

                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    // product successfully deleted
                    // notify previous activity by sending code 100
                    Intent i = getIntent();
                    // send result code 100 to notify about product deletion
                    setResult(100, i);
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();

        }

    }

}