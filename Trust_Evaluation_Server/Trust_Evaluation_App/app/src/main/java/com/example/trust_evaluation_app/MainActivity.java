package com.example.trust_evaluation_app;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    String USER="";
    String PASSWORD="";
    EditText username;
    EditText password;
    Button submit;
    TextView register;
    JSONObject jsn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        register = (TextView) findViewById(R.id.txtRegister);
        username = (EditText) findViewById(R.id.editUser);
        Log.d("Name : "+username,"pass : "+password);
        password = (EditText) findViewById(R.id.editPass);
        submit = (Button) findViewById(R.id.buttonLogin);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                USER = username.getText().toString();
                PASSWORD = password.getText().toString();
                Log.d("Nameeee : "+USER,"password : "+PASSWORD);

                if (USER.equals("")) {
                    Toast.makeText(MainActivity.this, "USERNAME should not be empty", Toast.LENGTH_SHORT).show();
                } else if (PASSWORD.equals("")) {
                    Toast.makeText(MainActivity.this, "PASSWORD should'nt be empty", Toast.LENGTH_SHORT).show();
                } else {


                    new DataTransfer().execute();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerText = new Intent(MainActivity.this, NewActivity.class);
                startActivity(registerText);
            }
        });

    }

    private class DataTransfer extends AsyncTask<Void,String,String>
    {
        @Override
        public String doInBackground(Void... Void)
        {
            jsn = new JSONObject();
            String response = "";
            try {
                URL url = new URL(Global.url +"Login");
                jsn.put("username", USER);
                jsn.put("password",PASSWORD);
                Log.d("Nameeee : "+USER,"password http: "+PASSWORD);
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
            if(s.equals("ok")) {



                Intent intent = new Intent(MainActivity.this, HomePage.class);
                intent.putExtra("username", USER);
                HomePage.user=USER;

                startActivity(intent);
            }

            else
            {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }
        }
    }



}