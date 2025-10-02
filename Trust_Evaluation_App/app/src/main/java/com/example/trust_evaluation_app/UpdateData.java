package com.example.trust_evaluation_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UpdateData extends AppCompatActivity {
   // public static   String ops[] ={"Select","Traffic","Environment","Transport","Healthcare","Social Recommendation"};
    public static   String ops[] =null;
    public static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;
    public static final String LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String LOCATION_PREF = "locationPref";
    Context context;
    Activity activity;
    EditText et;
    public static  String msg="",user="",service="";
    Button btnUpdateData;
    Handler handler;
    Spinner spinner;
    TextView tv_pd;
    Intent next;

    Intent nextUpdateData;


    public static String imageFileName="",path="";
    Button btnClick,btnSave,btnUpload,b4;
    ProgressDialog dialog;
    public int CAMERA_REQUEST = 1;
    private static final int REQUEST_STORAGE_PERMISSION = 1;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    String catg="",old_contact="";
    SharedPreferences pref;
    ImageView iv;
    public static final String PREFS_NAME = "MyPrefs";
    private static final String PREF_contact = "contact";
    private ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1.0f;
    int serverResponseCode = 0;
    private String selectedFilePath;
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    String imgString;
    public static String res="";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap mResultsBitmap;
    private String mTempPhotoPath;
    private static final String FILE_PROVIDER_AUTHORITY = "com.mydomain.fileprovider";
    Activity a;
    Context c;

    private AppExecutor mAppExcutor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);

        et=(EditText)findViewById(R.id.etData);
        spinner = (Spinner) findViewById(R.id.spService);
        ArrayAdapter<String> adapterCategory = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,ops);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterCategory);
        context = UpdateData.this;
        activity = UpdateData.this;
        checkLocationPermission(activity,context,LOCATION_PERMISSION,LOCATION_PREF);

        btnClick = (Button)findViewById(R.id.b1);
        btnSave = (Button)findViewById(R.id.b2);
        btnUpload = (Button)findViewById(R.id.b3);

        iv = (ImageView)findViewById(R.id.imageView2);
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
        verifyStoragePermissions(this);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;





        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)iv.getLayoutParams();
        params.width = dpToPx(400,getApplicationContext());
        params.height = dpToPx(400,getApplicationContext());
        iv.setLayoutParams(params);

        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// Check for the external storage permission
                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // If you do not have permission, request it
                    ActivityCompat.requestPermissions(a,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_STORAGE_PERMISSION);
                } else {
                    // Launch the camera if the permission exists
                    if (ContextCompat.checkSelfPermission(c, Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(a,
                                new String[]{Manifest.permission.CAMERA},
                                REQUEST_CAMERA_PERMISSION);
                    } else {
                        // Permission already granted, proceed with camera access
                        launchCamera();
                    }

                }
            }
        });
        mAppExcutor = new AppExecutor();
        btnSave.setOnClickListener((View v) -> {
            mAppExcutor.diskIO().execute(() -> {
                // Delete the temporary image file
                BitmapUtils.deleteImageFile(this, mTempPhotoPath);
                Log.d("??",mTempPhotoPath);
                // Save the image
                WriteFileString("temp.png",mResultsBitmap);
                //   BitmapUtils.saveImage(this, mResultsBitmap);

            });

            Toast.makeText(this,"Image Save",Toast.LENGTH_LONG).show();

            String destPath = getApplicationContext().getExternalFilesDir(null).getAbsolutePath();
            // File FileDir = new File(destPath, "A");  // getDir();
            String path =destPath              + "/digiData/temp.png";
            Log.d("path",path);

        });



        btnUpload.setOnClickListener((View v) -> {
            mAppExcutor.diskIO().execute(() -> {


            });
            msg=et.getText().toString();
            service=spinner.getSelectedItem().toString();
//            new update_data().execute();


            String destPath = getApplicationContext().getExternalFilesDir(null).getAbsolutePath();
            // File FileDir = new File(destPath, "A");  // getDir();
            String path =destPath              + "/digiData/temp.png";
            Log.d("path",path);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //creating new thread to handle Http Operations
                    uploadFile(path);//path+imageFileName);
                }
            }).start();
        });
    }


    private class update_data extends AsyncTask<Void,String,String>
    {
        @Override
        public String doInBackground(Void... Void)
        {
            JSONObject jsn = new JSONObject();
            String response = "";
            try {
                URL url = new URL(Global.url +"update_data");
                jsn.put("user", user);
                jsn.put("service", service);
                jsn.put("data", msg);

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
            Log.d("response",s);
            if(s.equalsIgnoreCase("ok"))
            {
                Toast.makeText(getApplicationContext(),"Proper Context Data\nReputation Increased. Well Done", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(getApplicationContext(),"Please enter proper context data\n Reputation Decreased..", Toast.LENGTH_LONG).show();
            }




        }
    }
    private void checkLocationPermission(Activity activity, final Context context, final String Permission,final String prefName) {

        PermissionUtil.checkPermission(activity,context,Permission,prefName,
                new PermissionUtil.PermissionAskListener() {
                    @Override
                    public void onPermissionAsk() {


                        ActivityCompat.requestPermissions(UpdateData.this,
                                new String[]{Permission},
                                MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

                    }
                    @Override
                    public void onPermissionPreviouslyDenied() {
                        //show a dialog explaining permission and then request permission

                        showToast("Permission previously Denied.");

                        ActivityCompat.requestPermissions(UpdateData.this,
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



//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
//// If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    // permission was granted, yay! Do the task you need to do.
//                    getGpsLocation();
//
//
//                } else {
//
//
//                    showToast("Permission denied,without permission can't access maps.");
//                    // permission denied, boo! Disable the functionality that depends on this permission.
//                }
//                return;
//            }
//        }
//    }


    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }


    private void WriteFileString(String filename, Bitmap image) {
        try {

            String destPath = getApplicationContext().getExternalFilesDir(null).getAbsolutePath();
            // File FileDir = new File(destPath, "A");  // getDir();
            String path =destPath              + "/digiData";
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            path = path + "//" + filename;
            file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream stream = new FileOutputStream(path);
            OutputStreamWriter writer = new OutputStreamWriter(stream);
            image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            //writer.write(data);
            writer.flush();
            writer.close();
            stream.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }



    public static int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        scaleGestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // Called when you request permission to read and write to external storage
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case REQUEST_STORAGE_PERMISSION: {
        if (requestCode == REQUEST_STORAGE_PERMISSION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // If you get permission, launch the camera
                launchCamera();
            } else {
                // If you do not get permission, show a Toast
                Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launchCamera();
            } else {
                // Permission denied
                // Inform the user that permission is required
            }
        }
        if(requestCode == MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION){

            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // permission was granted, yay! Do the task you need to do.
                getGpsLocation();


            } else {


                showToast("Permission denied,without permission can't access maps.");
                // permission denied, boo! Disable the functionality that depends on this permission.
            }


        }



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If the image capture activity was called and was successful
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Process the image and set it to the TextView
            processAndSetImage();
        } else {

            // Otherwise, delete the temporary image file
            BitmapUtils.deleteImageFile(this, mTempPhotoPath);
        }
    }

    /**
     * Creates a temporary image file and captures a picture to store in it.
     */
    private void launchCamera() {

        // Create the capture image intent
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the temporary File where the photo should go
            File photoFile = null;
            try {
                photoFile = BitmapUtils.createTempImageFile(this);
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {

                // Get the path of the temporary file
                mTempPhotoPath = photoFile.getAbsolutePath();

                // Get the content URI for the image file
                Uri photoURI = FileProvider.getUriForFile(this,
                        FILE_PROVIDER_AUTHORITY,
                        photoFile);

                // Add the URI so the camera can store the image
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                // Launch the camera activity
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
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
    public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));
            iv.setScaleX(mScaleFactor);
            iv.setScaleY(mScaleFactor);
            return true;
        }
    }
    /**
     * Method for processing the captured image and setting it to the TextView.
     */
    private void processAndSetImage() {



        // Resample the saved image to fit the ImageView
        mResultsBitmap = BitmapUtils.resamplePic(this, mTempPhotoPath);


        // Set the new bitmap to the ImageView
        iv.setImageBitmap(mResultsBitmap);
    }


    public void uploadFile(String sourceFileUri) {
        String serverResponseMessage="";

//        location = GPSTracker.locate;
//        Log.d("Location : ",location);


        //String upLoadServerUri = Global.url+"UploadData1?location=" + lat;
        String upLoadServerUri = Global.url+"UploadData1?msg="+msg+"&service="+service;
        String fileName = sourceFileUri;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 10 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);
        if (!sourceFile.isFile()) {
            Log.e("uploadFile", "Source File Does not exist");
            // return 0;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(sourceFile);
            URL url = new URL(upLoadServerUri);
            conn = (HttpURLConnection) url.openConnection(); // Open a HTTP  connection to  the URL
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a Cached Copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            conn.setRequestProperty("uploaded_file", fileName);

            dos = new DataOutputStream(conn.getOutputStream());

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            //dos.writeUTF("Content-Disposition: form-data; name=\"lat\";lat=\""+ lat + "\"" + lineEnd);
            //  dos.writeUTF(lon);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""+ fileName + "\"" + lineEnd);

            dos.writeBytes(lineEnd);

            bytesAvailable = fileInputStream.available(); // create a buffer of  maximum size

            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // Responses from the server (code and message)
            serverResponseCode = conn.getResponseCode();
            serverResponseMessage = conn.getResponseMessage();



            //close the streams //
            fileInputStream.close();
            dos.flush();
            dos.close();

        } catch (MalformedURLException ex) {

            ex.printStackTrace();
            Toast.makeText(getApplicationContext(), "MalformedURLException", Toast.LENGTH_SHORT).show();
            Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
        } catch (Exception e) {

            e.printStackTrace();
            Log.d("Exception1: ",e.getMessage().toString());
            //Toast.makeText(getApplicationContext(), "Exception : " + e.getMessage(), Toast.LENGTH_SHORT).show();

        }





        Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);
        if(serverResponseCode == 200){
            runOnUiThread(new Runnable() {
                public void run() {
                    //  dialog.cancel();
                    Toast.makeText(getApplicationContext(), "File Upload Complete.", Toast.LENGTH_SHORT).show();
//                    new get_fname().execute();
//                    Intent next=new Intent(getApplicationContext(),ViewResult.class);
//                    startActivity(next);
                }
            });
        }


        //   return serverResponseCode;
    }

    public class get_fname extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
        }

        public String doInBackground(String... args0) {
            try {


                URL url = new URL(Global.url + "get_fname");
                JSONObject json = new JSONObject();



                String response = HttpClientConnection.getData(url, json);
                String status = response;

                return status;

            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }
        @Override
        protected void onPostExecute(String status) {

            Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();


        }
    }

}
