package com.example.trust_evaluation_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class askHelp extends AppCompatActivity {
    public static   String ops[] ={"Select","Traffic","Transport","Healthcare","Job"};
    public static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;
    public static final String LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String LOCATION_PREF = "locationPref";
    public static   String ops1[] ={"Jayadeva","JP Nagar","Jaya Nagar","BTM"};
    Context context;
    Activity activity;
    EditText et;
    public static  String msg="",user="",service="",area="";
    Button btnUpdateData;
    Handler handler;
    Spinner spinner,spLocation;
    TextView tv_pd;
    Intent next;

    Intent nextUpdateData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_help);
        tv_pd=(TextView)findViewById(R.id.tv);
        spinner = (Spinner) findViewById(R.id.spService);
        ArrayAdapter<String> adapterCategory = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,ops);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterCategory);
        context = askHelp.this;
        activity = askHelp.this;
        checkLocationPermission(activity,context,LOCATION_PERMISSION,LOCATION_PREF);
        spLocation = (Spinner) findViewById(R.id.spLocation);
        ArrayAdapter<String> adapterCategory1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,ops1);
        adapterCategory1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLocation.setAdapter(adapterCategory1);
        btnUpdateData=(Button)findViewById(R.id.btnUpdateData);

        btnUpdateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                service=spinner.getSelectedItem().toString();
                area=spLocation.getSelectedItem().toString();
                Log.d("service ",service);
                new get_data().execute();

            }
        });
    }
    private class get_data extends AsyncTask<Void,String,String>
    {
        @Override
        public String doInBackground(Void... Void)
        {
            JSONObject jsn = new JSONObject();
            String response = "";
            try {
                URL url = new URL(Global.url +"ask_help");
                jsn.put("user", user);
                jsn.put("query", service);
                jsn.put("area", area);


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

            //Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();
            String arr[]=s.split("@@");
            for(int i=0;i<arr.length;i++)
            {
                String a[]=arr[i].split("#");
                tv_pd.append(a[1]+"\n"+a[2]);
                ImageView imageView = findViewById(R.id.imageView);
                Picasso.with(getApplicationContext())
                        .load(Global.url+a[0]+".jpg")
                        .into(imageView);
            }


        }
    }
    private void checkLocationPermission(Activity activity, final Context context, final String Permission,final String prefName) {

        PermissionUtil.checkPermission(activity,context,Permission,prefName,
                new PermissionUtil.PermissionAskListener() {
                    @Override
                    public void onPermissionAsk() {


                        ActivityCompat.requestPermissions(askHelp.this,
                                new String[]{Permission},
                                MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

                    }
                    @Override
                    public void onPermissionPreviouslyDenied() {
                        //show a dialog explaining permission and then request permission

                        showToast("Permission previously Denied.");

                        ActivityCompat.requestPermissions(askHelp.this,
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