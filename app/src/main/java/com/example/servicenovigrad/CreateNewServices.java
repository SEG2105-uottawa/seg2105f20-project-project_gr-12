package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.HashMap;

public class CreateNewServices extends AppCompatActivity {
    //doc info
    public boolean firstname;
    public boolean lastname;
    public boolean address;
    public boolean dob;
    //form info
    public boolean status;
    public boolean photoID;
    public boolean resident;
    public boolean license;

    //to fix error where unwanted toast pops up
    public boolean serviceExistsCheck;
    //keeps track if entered price is in valid format
    public boolean priceValid;

    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_services);

        db = FirebaseDatabase.getInstance();
    }


    /*public void back(View view) {
        Intent intent = new Intent(this, CurrentService.class);
        startActivity(intent);
    }*/

    public void onClickDone(View view) {
        serviceExistsCheck = true;
        priceValid = true;

        CheckBox firstNameCheckBox = (CheckBox) findViewById(R.id.cbtnFirstName);
        CheckBox lastNameCheckBox = (CheckBox) findViewById(R.id.rbtnlastName);
        CheckBox addressCheckBox = (CheckBox) findViewById(R.id.rbtnaddress);
        CheckBox dobCheckBox = (CheckBox) findViewById(R.id.rbtnDob);

        CheckBox statusCheckbox = (CheckBox) findViewById(R.id.cbtnStatus);
        CheckBox photoIDCheckBox = (CheckBox) findViewById(R.id.cbtnPhoto);
        CheckBox residentCheckBox = (CheckBox) findViewById(R.id.cbtnRes);
        CheckBox licenseCheckBox = (CheckBox) findViewById(R.id.cbtnLicense);

        firstname = firstNameCheckBox.isChecked();
        lastname = lastNameCheckBox.isChecked();
        address = addressCheckBox.isChecked();
        dob = dobCheckBox.isChecked();
        status = statusCheckbox.isChecked();
        photoID = photoIDCheckBox.isChecked();
        resident = residentCheckBox.isChecked();
        license = licenseCheckBox.isChecked();


        // make sure the Name of Service and price is filled in
        EditText checkName = (EditText) findViewById(R.id.serviceName);
        EditText checkPrice = (EditText) findViewById(R.id.servicePrice);

        String emptyNameField = checkName.getText().toString();
        String priceField = checkPrice.getText().toString();

        emptyNameField = emptyNameField.replaceAll("\\s+","");

        if(!priceField.matches("^\\d+(.\\d{1,2})?$")){
            priceValid = false;
        }

        if (emptyNameField.equals("")){
            setContentView(R.layout.activity_create_new_services);
            Toast.makeText(getApplicationContext(),"Please Enter the Name of Service",Toast.LENGTH_LONG).show();
        }
        else if(priceField.equals("")) {
            setContentView(R.layout.activity_create_new_services);
            Toast.makeText(getApplicationContext(),"Please Enter a Price",Toast.LENGTH_LONG).show();
        }
        else    {
            //select at least one doc or form option
            if ((firstname== true || lastname==true) && (status== true || photoID==true || resident ==true) && (priceValid)) {


                // Add service to database
                EditText serviceNameEditText = (EditText) findViewById(R.id.serviceName);
                EditText servicePriceEditText = (EditText) findViewById(R.id.servicePrice);

                final String serviceNameField = serviceNameEditText.getText().toString();
                String servicePriceString = servicePriceEditText.getText().toString();

                if(servicePriceString.equals(".")){
                    servicePriceString = "0.00";
                }

                final double servicePriceField = Double.parseDouble(servicePriceString);

                HashMap<String, Boolean> fieldsEnableTemp = new HashMap<String, Boolean>();

                HashMap<String, Boolean> formsEnableTemp = new HashMap<String, Boolean>();

                fieldsEnableTemp.put("firstNameFieldEnable", firstname);
                fieldsEnableTemp.put("lastNameFieldEnable", lastname);
                fieldsEnableTemp.put("addressFieldEnable", address);
                fieldsEnableTemp.put("dobFieldEnable", dob);

                formsEnableTemp.put("statusFormEnable", status);
                formsEnableTemp.put("photoIDFormEnable", photoID);
                formsEnableTemp.put("residentFormEnable", resident);
                formsEnableTemp.put("licenseFormEnable", license);

                final HashMap<String, Boolean> fieldsEnable = fieldsEnableTemp;
                final HashMap<String, Boolean> formsEnable = formsEnableTemp;

                Log.e("Service:", serviceNameField);
                Log.e("Service:", servicePriceEditText.getText().toString());


                DatabaseReference serviceExists = db.getReference("services/" + serviceNameField);
                serviceExists.addValueEventListener(new ValueEventListener()
                {
                    public void onDataChange(DataSnapshot data)
                    {
                        Log.e("DATA FOR SERVICE REQUEST:", String.valueOf(data.exists()));
                        if (serviceExistsCheck){
                            if(data.exists())
                            {
                                Toast.makeText(getApplicationContext(),"Service already exists",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Processing New Service", Toast.LENGTH_SHORT).show();
                                DatabaseReference addService = db.getReference("services/" + serviceNameField);
                                addService.setValue(new Service(serviceNameField, fieldsEnable, formsEnable, servicePriceField));
                                serviceExistsCheck = false;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Intent intent = new Intent(this, CurrentService.class);
                startActivity(intent);
            }

            //none selected
            if((firstname==false && lastname==false && address==false && dob==false && license==false) && (status==false && photoID==false && resident==false)){
                setContentView(R.layout.activity_create_new_services);
                Toast.makeText(getApplicationContext(),"Document and Form Info Not Specified",Toast.LENGTH_LONG).show();
            }
            //nothing in forms selected
            else if (firstname==false && lastname==false && address==false && dob==false && license==false)   {
                setContentView(R.layout.activity_create_new_services);
                Toast.makeText(getApplicationContext(),"Select at least one Document Info",Toast.LENGTH_LONG).show();

            }
            //nothing in documents selected
            else if (status==false && photoID==false && resident==false)  {
                setContentView(R.layout.activity_create_new_services);
                Toast.makeText(getApplicationContext(),"Select at least one Form Info",Toast.LENGTH_LONG).show();

            }
            //Make sure not only address or dob field is checked
            else if(firstname == false && lastname == false && (address == true || dob == true || license==true))  {
                setContentView(R.layout.activity_create_new_services);
                Toast.makeText(getApplicationContext(),"Select at least one name field",Toast.LENGTH_LONG).show();

            }
            else if(!priceValid){
                setContentView(R.layout.activity_create_new_services);
                Toast.makeText(getApplicationContext(),"Maximum Two Decimals for Price",Toast.LENGTH_LONG).show();
            }
        }

    }

    public void onClickWelomePage(View view) {
        Intent intent = new Intent(this, AdminWelcome.class);
        startActivity(intent);
    }

}