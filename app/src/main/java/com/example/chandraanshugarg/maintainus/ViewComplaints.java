package com.example.chandraanshugarg.maintainus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
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
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;
import org.apache.http.ParseException;

/**
 * Created by Chandraanshu Garg on 28-07-2016.
 */
public class ViewComplaints extends ListActivity{
    private ProgressDialog pDialog;
    String username, time_of_complaint;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> complaintsList;

    private static String url_all_complaints = "http://192.168.1.7/maintaiNUS/read_complaint.php";
    private static String url_delete_complaints = "http://192.168.1.7/maintaiNUS/delete_complaint.php";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_COMPLAINTS = "complaints";
    private static final String TAG_USER = "name_of_user";
    private static final String TAG_TIME = "time_of_complaint";
    private static final String TAG_LOCATION = "location";
    private static final String TAG_CATEGORY = "category";
    private static final String TAG_CAUSE = "cause";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_REPAIR = "repair_time";


    JSONArray complaints = null;
    public void onCreate(Bundle savedInstanceState) {
        username=getIntent().getStringExtra("username");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_complaints);
        complaintsList = new ArrayList<HashMap<String, String>>();

        // Loading products in Background Thread
        new LoadAllComplaints().execute();

        // Get listview
        ListView lv = getListView();

//        lv.findViewById(R.id.update).setOnItemClickListener(new OnItemClickListener(){
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String desc, cause;
//                desc=((TextView) view.findViewById(R.id.description)).getText().toString();
//                cause=((TextView) view.findViewById(R.id.cause)).getText().toString();
//                Log.d("cause and desc", desc+cause);
//            }
//        });
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
                        String repairs = c.getString(TAG_REPAIR);

                        if(repairs.equals("null"))
                        { repairs = "COMPLAINT REGISTERED"; }

                        DateFormat formatter = new SimpleDateFormat("yyyy-mm-DD");
                        try {Date date = (Date)formatter.parse(repairs);
                            SimpleDateFormat newFormat = new SimpleDateFormat("DD/mm/yyyy");
                           repairs = newFormat.format(date);}
                        catch(java.text.ParseException e) {}


                        HashMap<String, String> map = new HashMap<String, String>();

                        map.put(TAG_USER, user);
                        map.put(TAG_TIME, time);
                        map.put(TAG_CATEGORY, category);
                        map.put(TAG_LOCATION, location);
                        map.put(TAG_CAUSE, cause);
                        map.put(TAG_DESCRIPTION, description);
                        map.put(TAG_REPAIR, repairs);


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
                            R.layout.complaint_item, new String[] { TAG_CATEGORY,TAG_CAUSE, TAG_DESCRIPTION, TAG_LOCATION, TAG_REPAIR, TAG_TIME, TAG_USER, TAG_DESCRIPTION, TAG_CAUSE, TAG_TIME},
                            new int[] { R.id.category, R.id.cause, R.id.description, R.id.location, R.id.repair_time, R.id.time_of_complaint, R.id.user, R.id.dummyDesc, R.id.dummyCause, R.id.dummyTime });
                    // updating listview
                    setListAdapter(adapter);
                }
            });

        }

    }
    public void updateHandler(View v){
        LinearLayout vwParentRow = (LinearLayout)v.getParent();
        TextView childDesc = (TextView)vwParentRow.getChildAt(2);
        TextView childCause = (TextView)vwParentRow.getChildAt(3);
        TextView childTime = (TextView)vwParentRow.getChildAt(4);
        Log.d("cause and desc", childCause.getText().toString()+ "   " +childDesc.getText().toString()+ childTime.getText().toString());
        Intent intent = new Intent(ViewComplaints.this,UpdateForm.class);
        intent.putExtra("cause",childCause.getText().toString());
        intent.putExtra("desc",childDesc.getText().toString());
        intent.putExtra("username",username);
        intent.putExtra("time", childTime.getText().toString());
        startActivity(intent);
    }



}