package com.example.chandraanshugarg.maintainus;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ComplaintForm extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String location, category, description, cause;
    boolean occupant=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_form);
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

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       // Toast.makeText(parent.getContext(), "OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
                //Toast.LENGTH_SHORT).show();
        if(parent.getItemAtPosition(0).toString()=="Tembusu College"){
            location=parent.getItemAtPosition(position).toString();
        }
        else{
            category=parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public  void checkboxClicked(View view){
        occupant=((CheckBox) view).isChecked();
    }
    public void Submit(View view){
        Toast.makeText(getApplicationContext(),location,Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),category,Toast.LENGTH_SHORT).show();
        EditText d=(EditText)findViewById(R.id.editText);
        EditText c=(EditText)findViewById(R.id.editText2);
        description=d.getText().toString();
        cause=c.getText().toString();
        Toast.makeText(getApplicationContext(),description,Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),cause,Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),"Occupant is "+occupant,Toast.LENGTH_SHORT).show();
    }
}
