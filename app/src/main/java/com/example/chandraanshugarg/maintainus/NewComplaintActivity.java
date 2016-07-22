//package com.example.chandraanshugarg.maintainus;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.http.NameValuePair;
//import org.apache.http.message.BasicNameValuePair;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import android.app.Activity;
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import static com.example.chandraanshugarg.maintainus.ComplaintForm.*;
//
///**
// * Created by Chandraanshu Garg on 20-07-2016.
// */
//public class NewComplaintActivity extends Activity {
//    // Progress Dialog
//    private ProgressDialog pDialog;
//    SharedPreferences pref = getSharedPreferences("usernamePreference", MODE_PRIVATE);
//    String name_of_user = pref.getString("id", "Guest");
//
//    JSONParser jsonParser = new JSONParser();
//    private static String url_create_complaint = "http://localhost/maintaiNUS/create_complaint.php";
//    private static final String TAG_SUCCESS = "success";
//
//    @Override
//    public void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        Button btnCreateComplaint = (Button) findViewById(R.id.SubmitButtonClick);
//        btnCreateComplaint.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
////                // creating new product in background thread
////                if (!CheckConnection.isNetworkAvailable(NewComplaintActivity.this)) {
////                    Toast.makeText(getApplicationContext(), "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
////                } else {
////                    Toast.makeText(getApplicationContext(), "Check1", Toast.LENGTH_SHORT).show();
////                    new CreateNewComplaint().execute();
////                }
//                new CreateNewComplaint().execute();
//            }
//        });
//    }
//
//    class CreateNewComplaint extends AsyncTask<String, String, String> {
//
//        /**
//         * Before starting background thread Show Progress Dialog
//         */
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            pDialog = new ProgressDialog(NewComplaintActivity.this);
//            pDialog.setMessage("Submitting Complaint");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(true);
//            pDialog.show();
//        }
//
//        protected String doInBackground(String... args) {
//            List<NameValuePair> params = new ArrayList<NameValuePair>();
//            params.add(new BasicNameValuePair("name_of_user", name_of_user));
//            params.add(new BasicNameValuePair("location", location));
//            params.add(new BasicNameValuePair("category", category));
//            params.add(new BasicNameValuePair("description", description));
//            params.add(new BasicNameValuePair("cause", cause));
//            params.add(new BasicNameValuePair("occupant", occupant));
//
//            JSONObject json = jsonParser.makeHttpRequest(url_create_complaint, "POST", params);
//
//            Log.d("Create Response", json.toString());
//
//            // check for success tag
//            try {
//                int success = json.getInt(TAG_SUCCESS);
//
//                if (success == 1) {
//                    // successfully created product
//                    Toast.makeText(getApplicationContext(), "Complaint Successfully Registered", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(NewComplaintActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    // failed to create product
//                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//        protected void onPostExecute(String file_url) {
//            // dismiss the dialog once done
//            pDialog.dismiss();
//        }
//    }
//}
//
