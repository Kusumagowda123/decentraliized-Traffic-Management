package com.example.trust_evaluation_app;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

public class NewActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {

    EditText name,etmob;
    EditText password,cp;
    EditText email;
    Button submittt;

    RadioButton male;
    RadioButton female;
    String Name = "";
    String vnum;
    String Email = "";
    String Gender = "";
    String Password = "",mob="",cpass="";

    JSONObject jsn = new JSONObject();
    String type="",profession="";
    Spinner sp;
    private Calendar mcalendar;
    private int day,month,year;
    String msg="",  private_key="";
    final public static int SEND_SMS = 101;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    public static   String arr[] ={"Profession","Teacher","Tourist Guide","Businessman","IT Professional","Doctor"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);


        verifyStoragePermissions(this);
        name = (EditText) findViewById(R.id.editName);
        email = (EditText) findViewById(R.id.editEmail);
        etmob= (EditText) findViewById(R.id.etmob);
        male = (RadioButton) findViewById(R.id.rdMale);
        female = (RadioButton) findViewById(R.id.rdFemale);
        password = (EditText) findViewById(R.id.editPassword);
        cp = (EditText) findViewById(R.id.editCPassword);
        submittt = (Button) findViewById(R.id.buttonn);
        sp=(Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> adapterCategory = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arr);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapterCategory);



        submittt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                Name = name.getText().toString();
                Password = password.getText().toString();
                cpass = cp.getText().toString();
                Email = email.getText().toString();
                mob=etmob.getText().toString();
                profession=sp.getSelectedItem().toString();

                if (Name.equals("")) {
                    Toast.makeText(NewActivity.this, "Name should'nt be blank", Toast.LENGTH_SHORT).show();
                } else if (Password.length() < 6) {
                    Toast.makeText(NewActivity.this, "Atleast 6 characters should be their in password", Toast.LENGTH_SHORT).show();
                } else if (Email.equals("")) {
                    Toast.makeText(NewActivity.this, "email is invalid", Toast.LENGTH_SHORT).show();
                } else if (!Password.equals(cpass)) {
                    Toast.makeText(NewActivity.this, "Passwords are not matching", Toast.LENGTH_SHORT).show();

                }
                else if (profession.equals("Profession")) {
                    Toast.makeText(NewActivity.this, "Please select your profession", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (male.isChecked())
                        Gender = "Male";
                    else
                        Gender = "Female";

                    new Transfer().execute();
                }

            }


        });




    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        type = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + type, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }

    private class Transfer extends AsyncTask<Void, String, String> {
        @Override
        public String doInBackground(Void... Void)
        {
            jsn = new JSONObject();
            String response = "";

            Log.d("Values",Name + "-" + vnum);

            try {
                URL url = new URL(Global.url +"Registration");
                jsn.put("name",Name);
                jsn.put("password",Password);
                jsn.put("gender",Gender);
                jsn.put("email",Email);

                jsn.put("mob",mob);
                jsn.put("profession",profession);
                response = HttpClientConnection.getData(url,jsn);
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }

            //  if (response.equals())
            return response;
        }
        @Override
        protected void onPostExecute(String s) {

            if(s.endsWith("null"))
            {

                s=s.substring(0,s.length()-4);
            }
            Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();
            if(!s.equalsIgnoreCase("notok")) {
                msg="Registration Successful";
                checkAndroidVersion(mob);
                Intent intent = new Intent(NewActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }


    public void checkAndroidVersion(String mobile){

        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(NewActivity.this, Manifest.permission.SEND_SMS);
            if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(NewActivity.this,new String[]{Manifest.permission.SEND_SMS},SEND_SMS);
                return;
            }else{
                sendSms(mobile);
            }
        } else {
            sendSms(mobile);
        }
    }
    public void sendSms(String edt_phoneNo)
    {


        try {

            SmsManager sm = SmsManager.getDefault();
//            String url="https://www.google.co.in/maps/@"+GPSTracker.latitude+","+GPSTracker.longitude+",15z?hl";
//            String msg="https://www.google.co.in/?gfe_rd=cr&ei=SE4EWentI-_I8AexhZ_QCg&gws_rd=ssl#q="+GPSTracker.latitude+""+GPSTracker.longitude+"";
//
            sm.sendTextMessage(edt_phoneNo, null, msg, null, null);



        } catch (Exception ex) {
            Toast.makeText(NewActivity.this, "Your sms has failed...",
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();

        }


    }

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}


