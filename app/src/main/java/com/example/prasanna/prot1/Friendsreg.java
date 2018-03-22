package com.example.prasanna.prot1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;

public class Friendsreg extends AppCompatActivity {

    public String user5,pass5,ride5;
    public ProgressDialog progressDialog;
    EditText name,num;
    String name1,num1;
    Button regi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendsreg);
        user5 = this.getIntent().getExtras().getString("username");
        pass5 = this.getIntent().getExtras().getString("password");
        ride5 = this.getIntent().getExtras().getString("ridetype");
        name=(EditText)findViewById(R.id.tfname);
        num=(EditText)findViewById(R.id.tfmob);
        regi=(Button)findViewById(R.id.breg);
        regi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name1 = name.getText().toString();
                num1 = num.getText().toString();
                new MyTask().execute();

            }
        });
    }
    private class MyTask extends AsyncTask<String, Integer, String> {

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(Friendsreg.this, "Message", "Creating Account...");

        }

        // This is run in a background thread
        @Override
        protected String doInBackground(String... params) {
            // get the string from params, which is an array
            try {
                CloudantClient client = ClientBuilder.account("b3428247-aaf2-464e-b539-b9f8a2950dcd-bluemix")
                        .username("b3428247-aaf2-464e-b539-b9f8a2950dcd-bluemix")
                        .password("36aa0871b8baf6708eda9c902d4af3b516eb85ede26b89af16fceb4348659e0e")
                        .build();
                Database db = client.database("pickmybus", false);

                db.save(new Schedule(user5,pass5,ride5,"","","",name1,num1,"","","","","","","","","","",0.0,0.0));

                return "SUCCESS";

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Network Busy!!", Toast.LENGTH_LONG).show();
                //System.out.println("Error SMS "+e);

            }

            return "FAILED";
        }



        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result.equals("SUCCESS")){
                System.out.println("SUCCESS");
                Intent n = new Intent(Friendsreg.this,LoginActivityDriver.class);

                startActivity(n);
            }
            else{
                Toast.makeText(getApplicationContext(),"Failed..",Toast.LENGTH_LONG).show();
            }

            progressDialog.dismiss();
            // Do things like hide the progress bar or change a TextView
        }
    }
}