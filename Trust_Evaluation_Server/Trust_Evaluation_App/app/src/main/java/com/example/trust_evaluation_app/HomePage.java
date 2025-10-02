package com.example.trust_evaluation_app;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.Settings;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class HomePage extends AppCompatActivity implements SensorEventListener {
    public static   String ops[] ={"Select","Traffic","Transport","Healthcare","Job"};//Social Recommendation  Environment
    public static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;
    public static final String LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String LOCATION_PREF = "locationPref";
    Context context;Activity activity;
    public static boolean flag=false;
    EditText et;
    TextView tv;
    Intent pa;
    public static  String msg="",user="",crsu="",pub_key="",priv_key="",pk="",key_status="";
    Button btnuloc,btnUpdateService,btnRemoveService,btnUpdateInfo,btnAskHelp,btnCheckRep;
    Handler handler;
    Spinner spinner;
    TextView tv_wel;
    Intent next;
    String mid="",puser="",pmsg="",wit="",pt="",status="",sp="";
    Intent nextUpdateData,ask_help;

    long lastUpdate;
    double threshold = 30;


    Sensor accelerometer;
    SensorManager sm;
    private SensorManager sensorManager;
    // private Sensor accelerometer;

    private float deltaXMax = 0;
    private float deltaYMax = 0;
    private float deltaZMax = 0;

    private float deltaX = 0;
    private float deltaY = 0;
    private float deltaZ = 0;

    private float vibrateThreshold = 0;
    public Vibrator v;
    private float lastX, lastY, lastZ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        spinner = (Spinner) findViewById(R.id.spService);


        ArrayAdapter<String> adapterCategory = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,ops);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterCategory);
        //nextUpdateData=new Intent(this,UpdateData.class);
        nextUpdateData=new Intent(this,Capture_photo.class);
        ask_help=new Intent(this,askHelp.class);
        pa=new Intent(this,Process_Alert.class);

        context = HomePage.this;
        activity = HomePage.this;
        checkLocationPermission(activity,context,LOCATION_PERMISSION,LOCATION_PREF);
        tv_wel=(TextView)findViewById(R.id.textView2);
        tv_wel.setText("Welcome "+user);
        btnuloc=(Button)findViewById(R.id.btnLoc);
        btnUpdateService=(Button)findViewById(R.id.btnUpdateService);
        btnRemoveService=(Button)findViewById(R.id.btnRemoveService);
        btnUpdateInfo=(Button)findViewById(R.id.btnUpdateInfo);
        btnAskHelp=(Button)findViewById(R.id.btnAskHelp);
        btnCheckRep=(Button)findViewById(R.id.btnCheckRep);
tv=findViewById(R.id.tv);

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            // success! we have an accelerometer

            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            vibrateThreshold = accelerometer.getMaximumRange() / 2;
        } else {
            // fai! we dont have an accelerometer!
        }

        //initialize vibration
        v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);





        btnuloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                handler = new Handler();
                Timer timer = new Timer();
                TimerTask doAsynchronousTask = new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            public void run() {
                                try {

                                    new update_loc().execute();

                                } catch (Exception e) {

                                }
                            }
                        });
                    }
                };
                timer.schedule(doAsynchronousTask, 0, 10000); //execute in every 5s*



            }
        });
        btnUpdateService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp=spinner.getSelectedItem().toString();
                status="1";
                new update_service().execute();
            }
        });

        btnRemoveService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp=spinner.getSelectedItem().toString();
                status="0";
                new update_service().execute();
            }
        });
        btnUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  UpdateData.user=user;
                Capture_photo.user=user;
                new get_interests().execute();

            }
        });

        btnAskHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askHelp.user=user;
                startActivity(ask_help);

            }
        });
        btnCheckRep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
new check_reputation().execute();
            }
        });

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        //This is done to prevent multiple values above threshold being registered as multiple potholes
        long actualTime = event.timestamp;
        try{
            if(actualTime - lastUpdate > 100000000) {
                lastUpdate = actualTime;
                if (event.values[2] > threshold) {
                  //  if(flag==true) {
                        Toast.makeText(getApplicationContext(), "Pothole Detected", Toast.LENGTH_SHORT).show();



                        new update_pothole_loc().execute();


                   // }

                }
            }
        }
        catch (Exception e){
            lastUpdate = actualTime;
        }



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    //onPause() unregister the accelerometer for stop listening the events
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    private class update_service extends AsyncTask<Void,String,String>
    {
        @Override
        public String doInBackground(Void... Void)
        {
            JSONObject  jsn = new JSONObject();
            String response = "";
            try {
                URL url = new URL(Global.url +"update_service");
                jsn.put("user", user);

                jsn.put("service", sp);
                jsn.put("status", status);


                response = HttpClientConnection.getData(url,jsn);
                Log.d("Response",""+response);
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
            return response;
        }
        @Override
        protected void onPostExecute(String s) {

            Toast.makeText(getApplicationContext(),"Interest Updated", Toast.LENGTH_LONG).show();



        }
    }
    private class check_reputation extends AsyncTask<Void,String,String>
    {
        @Override
        public String doInBackground(Void... Void)
        {
            JSONObject  jsn = new JSONObject();
            String response = "";
            try {
                URL url = new URL(Global.url +"check_reputation");
                jsn.put("user", user);




                response = HttpClientConnection.getData(url,jsn);
                Log.d("Response",""+response);
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
            return response;
        }
        @Override
        protected void onPostExecute(String s) {
            if(s.endsWith("null"))
            {

                s=s.substring(0,s.length()-4);
            }
            Toast.makeText(getApplicationContext(),"Your Reputation Score is "+s, Toast.LENGTH_LONG).show();
                String a[]=s.split("#");
                tv.setText("Experience Score: "+a[0]+"\nPositive Reputation: "+a[1]
                        +"\nNegative Reputation: "+a[2]
                        +"\nTrust Count: "+a[3]);


        }
    }
    private class get_interests extends AsyncTask<Void,String,String>
    {
        @Override
        public String doInBackground(Void... Void)
        {
            JSONObject  jsn = new JSONObject();
            String response = "";
            try {
                URL url = new URL(Global.url +"get_interests");
                jsn.put("user", user);



                response = HttpClientConnection.getData(url,jsn);
                Log.d("Response",""+response);
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
            return response;
        }
        @Override
        protected void onPostExecute(String s) {
         if(s.endsWith("null"))
         {

             s=s.substring(0,s.length()-4);
         }

          String arr[]=s.split("#");
          //  UpdateData.ops=arr;
            Capture_photo.ops=arr;
            startActivity(nextUpdateData);


        }
    }
    private class update_loc extends AsyncTask<Void,String,String>
    {
        @Override
        public String doInBackground(Void... Void)
        {
            JSONObject  jsn = new JSONObject();
            String response = "";
            try {
                URL url = new URL(Global.url +"update_location");
                jsn.put("user", user);


                jsn.put("lat",GPSTracker.latitude+"");
                jsn.put("lon",GPSTracker.longitude+"");

                response = HttpClientConnection.getData(url,jsn);
                Log.d("Response",""+response);
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
            return response;
        }
        @Override
        protected void onPostExecute(String s) {
            if(s.endsWith("null"))

            {
                s=s.substring(0,s.length()-4);
            }
            Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();

            if(s.equals("ok"))
            {
                Toast.makeText(getApplicationContext(),"Location Updated", Toast.LENGTH_LONG).show();
            }
            else if(s.equals("no alert"))
            {
                Toast.makeText(getApplicationContext(),"Location Updated", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(getApplicationContext(),flag+"", Toast.LENGTH_LONG).show();

                if(flag==false)
                {

                    Process_Alert.det=s;
                    startActivity(pa);
                }



            }



        }
    }
    private class update_pothole_loc extends AsyncTask<Void,String,String>
    {
        @Override
        public String doInBackground(Void... Void)
        {
            JSONObject  jsn = new JSONObject();
            String response = "";
            try {
                URL url = new URL(Global.url +"update_pothole_location");
                jsn.put("user", user);


                jsn.put("lat",GPSTracker.latitude+"");

                jsn.put("lon",GPSTracker.longitude+"");

                response = HttpClientConnection.getData(url,jsn);
                Log.d("Response",""+response);
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
            return response;
        }
        @Override
        protected void onPostExecute(String s) {
            if(s.endsWith("null"))
            {

                s=s.substring(0,s.length()-4);
            }
            Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();



        }
    }
    private void checkLocationPermission(Activity activity, final Context context, final String Permission,final String prefName) {

        PermissionUtil.checkPermission(activity,context,Permission,prefName,
                new PermissionUtil.PermissionAskListener() {
                    @Override
                    public void onPermissionAsk() {


                        ActivityCompat.requestPermissions(HomePage.this,
                                new String[]{Permission},
                                MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

                    }
                    @Override
                    public void onPermissionPreviouslyDenied() {
                        //show a dialog explaining permission and then request permission

                        showToast("Permission previously Denied.");

                        ActivityCompat.requestPermissions(HomePage.this,
                                new String[]{Permission},
                                MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

                    }
                    @Override
                    public void onPermissionDisabled() {

                        askUserToAllowPermissionFromSetting();

                    }
                    @Override
                    public void onPermissionGranted() {

                        showToast("Permission Granted.");
                        getGpsLocation();
                    }
                });
    }

    private void askUserToAllowPermissionFromSetting() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle("Permission Required:");

        // set dialog message
        alertDialogBuilder
                .setMessage("Kindly allow Permission from App Setting, without this permission app could not show maps.")
                .setCancelable(false)
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                        showToast("Permission forever Disabled.");
                    }
                })
                .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    private void getGpsLocation() {
        // check if GPS enabled
        GPSTracker gpsTracker = new GPSTracker(context);
        if (gpsTracker.getIsGPSTrackingEnabled())
        {
            showToast("Gps Values are:"+GPSTracker.latitude+" , "+GPSTracker.longitude);
        }
        else
        {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gpsTracker.showSettingsAlert();
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
// If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the task you need to do.
                    getGpsLocation();


                } else {


                    showToast("Permission denied,without permission can't access maps.");
                    // permission denied, boo! Disable the functionality that depends on this permission.
                }
                return;
            }
        }
    }


    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}