package com.example.chandraanshugarg.maintainus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Chandraanshu Garg on 28-07-2016.
 */
public class ViewComplaints extends ListActivity{
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> complaintsList;

    private static String url_all_complaints = "http://192.168.1.106/maintaiNUS/read_complaint.php";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_COMPLAINTS = "complaints";
    private static final String TAG_USER = "name_of_user";
    private static final String TAG_TIME = "time_of_complaint";
    private static final String TAG_LOCATION = "location";
    private static final String TAG_CATEGORY = "category";
    private static final String TAG_CAUSE = "cause";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_OCCUPANT = "occupant";

    JSONArray complaints = null;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_complaints);
        complaintsList = new ArrayList<HashMap<String, String>>();

        // Loading products in Background Thread
        new LoadAllComplaints().execute();

        // Get listview
        ListView lv = getListView();
    }
    class LoadAllComplaints extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ViewComplaints.this);
            pDialog.setMessage("Loading complaints. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_complaints, "GET", params);
            Log.d("All Products: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    complaints = json.getJSONArray(TAG_COMPLAINTS);
                    for (int i = 0; i < complaints.length(); i++) {
                        JSONObject c = complaints.getJSONObject(i);
                        String user = c.getString(TAG_USER);
                        String time = c.getString(TAG_TIME);
                        String category = c.getString(TAG_CATEGORY);
                        String location = c.getString(TAG_LOCATION);
                        String cause = c.getString(TAG_CAUSE);
                        String description = c.getString(TAG_DESCRIPTION);
                        String occupant = c.getString(TAG_OCCUPANT);

                        HashMap<String, String> map = new HashMap<String, String>();

                        map.put(TAG_USER, user);
                        map.put(TAG_TIME, time);
                        map.put(TAG_CATEGORY, category);
                        map.put(TAG_LOCATION, location);
                        map.put(TAG_CAUSE, cause);
                        map.put(TAG_DESCRIPTION, description);
                        map.put(TAG_OCCUPANT, occupant);

                        complaintsList.add(map);
                    }
                } else {
                    //Toast.makeText(getApplicationContext(), "No Complaints Found!", Toast.LENGTH_SHORT).show();
                }
            }
            catch (JSONException e){
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            ViewComplaints.this, complaintsList,
                            R.layout.complaint_item, new String[] { TAG_CATEGORY,TAG_CAUSE, TAG_DESCRIPTION, TAG_LOCATION, TAG_OCCUPANT, TAG_TIME, TAG_USER},
                            new int[] { R.id.category, R.id.cause, R.id.description, R.id.location, R.id.occupant, R.id.time_of_complaint, R.id.user });
                    // updating listview
                    setListAdapter(adapter);
                }
            });

        }

    }
}