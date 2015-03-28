package com.revyuk.bibi_request_class;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;


public class MainActivity extends ActionBarActivity implements BibiRequest.Callback {

    BibiRequest bibiRequest;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView) findViewById(R.id.bibitext);
        bibiRequest = new BibiRequest(this);

        JSONObject json = new JSONObject();
        try {
            json.put("first_name", URLEncoder.encode("Виталий", "UTF-8"));
            json.put("last_name", URLEncoder.encode("проверка русского текста", "UTF-8"));
            json.put("mobile", "0632580111");
            json.put("email", "vrevyuk2@gmail.com");
            json.put("state", "driver");
            json.put("gender", "male");
            json.put("password", "123");
            //bibiRequest.postCustomer(1, json.toString());
            bibiRequest.getCustomer(1000001, "5516b7d6811515f9278c34ba");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("XXX", "JSON ERROR");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void bibiResponse(int who, String responseMsg) {
        Log.d("XXX", "Who: "+who+" response: "+responseMsg);
        text.setText(URLDecoder.decode(responseMsg));
    }

    @Override
    public void bibiResponseFailure(int who, int responseCode, String responseMsg) {
        Log.d("XXX", "Error "+responseCode+" who: "+who+" response: "+responseMsg);
        text.setText(responseCode+" "+responseMsg);
    }
}
