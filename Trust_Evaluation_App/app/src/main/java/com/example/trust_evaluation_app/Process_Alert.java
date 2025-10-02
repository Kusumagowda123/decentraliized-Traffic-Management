package com.example.trust_evaluation_app;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Process_Alert extends AppCompatActivity {
public static String det="";
    RadioButton accept;
    RadioButton deny;
    Intent n;
    Button b1;
    String opinion;
    String id="";

TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_process_alert);
        tv=findViewById(R.id.tv);
        accept = (RadioButton) findViewById(R.id.accept);
        deny = (RadioButton) findViewById(R.id.deny);
        n = new Intent(this,HomePage.class);
        b1 = (Button) findViewById(R.id.b1);
        String a[]=det.split("#");
        id=a[0];
        tv.setText("Information ID: "+a[0]+"\nCategory: "+a[2]+"\n"+a[3]);

        ImageView imageView = findViewById(R.id.imageView);
        Picasso.with(this)
                .load(Global.url+a[0]+".jpg")
                .into(imageView);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accept.isChecked())
                    opinion = "Agree";
                else
                    opinion = "Deny";
                new voting().execute();
            }
        });


    }


    private class voting extends AsyncTask<Void,String,String>
    {
        @Override
        public String doInBackground(Void... Void)
        {
            JSONObject jsn = new JSONObject();
            String response = "";
            try {
                URL url = new URL(Global.url +"voting");
                jsn.put("user", HomePage.user);


                jsn.put("id",id);
                jsn.put("opinion",opinion);
                jsn.put("lat",GPSTracker.latitude);
                jsn.put("lon",GPSTracker.longitude);


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

          HomePage.flag=true;
                    startActivity(n);




            }




    }
}