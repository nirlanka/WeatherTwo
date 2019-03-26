package com.evildino.weathertwo;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getActionBar();
        actionBar.hide();

        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#293251"));

        requestQueue = Volley.newRequestQueue(this);

        loadApiData();
    }

    private void loadApiData() {
        String url = "https://api.darksky.net/forecast/d56f8cb05558b141e61a50d32b3bb8b0/37.8267,-122.4233";

        JsonObjectRequest request = new JsonObjectRequest(
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            JSONObject currently = jsonObject.getJSONObject("currently");
                            double temperature = currently.getDouble("temperature");
                            double windSpeed = currently.getDouble("windSpeed");
                            double pressure = currently.getDouble("pressure");
                            double humidity = currently.getDouble("humidity");

                            final TextView txtTemperature = (TextView) findViewById(R.id.txtTemperature);
                            txtTemperature.setText(Double.toString(temperature) + "℃");

                            final TextView txtWind = (TextView) findViewById(R.id.txtWind);
                            txtWind.setText(Double.toString(windSpeed));

                            final TextView txtPressure = (TextView) findViewById(R.id.txtPressure);
                            txtPressure.setText(Double.toString(pressure));

                            final TextView txtHumidity = (TextView) findViewById(R.id.txtHumidity);
                            txtHumidity.setText(Double.toString(humidity));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // TODO: Handle error
                    }
                });

        requestQueue.add(request);
    }
}
