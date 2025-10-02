package com.example.trust_evaluation_app;



import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class click extends AppCompatActivity {
    public static  String msg="",service="",area="";
    private static final int CAMERA_REQUEST = 1888;
    //private static final int STORAGE_PERMISSION_REQUEST = 1;
    private static final int CAMERA_PERMISSION_REQUEST = 1;
    private static final int STORAGE_PERMISSION_REQUEST = 2;
    private static final String UPLOAD_URL = Global.url+"upload"; // Replace with your servlet URL

    private Button captureButton;
    private ImageView imageView;
    private Bitmap photo;
    EditText et;
    Spinner spinner,spLocation;
    public static   String ops[] =null;
    public static   String ops1[] ={"Jayadeva","JP Nagar","Jaya Nagar","BTM"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.click);

        captureButton = findViewById(R.id.captureButton);
        imageView = findViewById(R.id.imageView);
        et=(EditText)findViewById(R.id.etData);
        spinner = (Spinner) findViewById(R.id.spService);
        ArrayAdapter<String> adapterCategory = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,ops);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterCategory);


        spLocation = (Spinner) findViewById(R.id.spLocation);
        ArrayAdapter<String> adapterCategory1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,ops1);
        adapterCategory1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLocation.setAdapter(adapterCategory1);

        captureButton.setOnClickListener(v -> {
            // Check camera permission
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, STORAGE_PERMISSION_REQUEST);
            } else {

                msg=et.getText().toString();
                service=spinner.getSelectedItem().toString();
                area=spLocation.getSelectedItem().toString();

                captureImage();
            }
        });
    }

    private void captureImage() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            // Check if the data contains an image URI or bitmap data
            if (data != null) {
                Uri imageUri = data.getData();
                if (imageUri != null) {
                    // Handle the image URI case
                    try {
                        // You can retrieve the bitmap using the URI if needed
                        Bitmap photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                        imageView.setImageBitmap(photo);

                        // Optionally, you can convert the Bitmap to a Base64 string
                        String encodedImage = encodeImageToBase64(photo);
                        encodedImage = encodedImage.replaceAll("\n", "").replaceAll("\r", "");  // Remove line breaks
                        uploadImageToServlet(encodedImage);


                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Failed to get image from URI", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Fallback: if no URI, it could be stored as a Bitmap in extras
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    imageView.setImageBitmap(photo);

                    // Optionally, you can convert the Bitmap to a Base64 string
                    String encodedImage = encodeImageToBase64(photo);

                    // Upload the image to the servlet
                    uploadImageToServlet(encodedImage);
                }
            }
        }
    }



    private String encodeImageToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        // Encode the image to Base64
        String base64String = Base64.encodeToString(byteArray, Base64.NO_WRAP);  // NO_WRAP avoids newlines or extra spaces

        return base64String;
    }

    private void uploadImageToServlet(String encodedImage) {
        // In a real app, use a background thread or AsyncTask to upload the image
        new Thread(() -> {
            try {
                // Ensure the Base64 string is URL-encoded
                String encodedData = URLEncoder.encode(encodedImage, "UTF-8");
                String upLoadServerUri = Global.url+"upload?data="+msg+"&service="+service+"&user="+HomePage.user+"&lat="+GPSTracker.latitude+"&lon="+GPSTracker.longitude+"&area="+area;

                // Prepare the URL for the servlet
                //URL url = new URL(UPLOAD_URL + "?a=1&b=2");
                URL url = new URL(upLoadServerUri);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                // Prepare the data to send (URL-encoded format)
                String data = "image=" + encodedData;

                // Send the data
                connection.getOutputStream().write(data.getBytes());

                // Get the response from the servlet
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Success - handle the response if needed
                    runOnUiThread(() -> Toast.makeText(click.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show());
                } else {
                    runOnUiThread(() -> Toast.makeText(click.this, "Upload failed", Toast.LENGTH_SHORT).show());
                }
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(click.this, "Error uploading image", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                captureImage();
            } else {
                Toast.makeText(this, "Camera permission is required", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
