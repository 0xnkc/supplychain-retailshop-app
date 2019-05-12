package com.qrcodescanner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //View Objects
    private Button buttonScan,buttonbatch;
    private TextView textViewName, textViewAddress,textExp,textbatch,textmfd,textquantity;
    //qr code scanner object
    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //View objects
        buttonScan = (Button) findViewById(R.id.buttonScan);
        buttonbatch =(Button)findViewById(R.id.buttonbatch);
        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewAddress = (TextView) findViewById(R.id.textViewAddress);
        textExp =(TextView)findViewById(R.id.textexp);
        textbatch =(TextView)findViewById(R.id.textbatch);
        textmfd=(TextView)findViewById(R.id.textmfd);
        textquantity=(TextView)findViewById(R.id.textquantity);
        //intializing scan object
        qrScan = new IntentIntegrator(this);

        //attaching onclick listener
        buttonScan.setOnClickListener(this);
        buttonbatch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sp = getSharedPreferences("batch", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                String s1= sp.getString("batchid",null);
                // webView.loadUrl("http://35.200.186.171/supplychain-frontend/view-batch.php?batchNo="+s1);

                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://35.200.190.111/supplychain-frontend/view-batch.php?batchNo="+s1));
                startActivity(i);
            }
        });
    }

    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());
                    //setting values to textviews
                    textViewName.setText(obj.getString("name"));
                    textmfd.setText(obj.getString("mfd"));
                    textExp.setText(obj.getString("exp"));
                    textquantity.setText(obj.getString("quantity"));
                    textViewAddress.setText(obj.getString("price"));
                    textbatch.setText(obj.getString("batch"));
                    SharedPreferences sp = getSharedPreferences("batch", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("batchid", obj.getString("batch"));
                    editor.commit();

                } catch (JSONException e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    @Override
    public void onClick(View view) {
        //initiating the qr code scan
        qrScan.initiateScan();
    }
}
