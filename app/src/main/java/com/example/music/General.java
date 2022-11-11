package com.example.music;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class General extends AppCompatActivity {
    private Adapter pAdapter;
    private List<Mask> listProduct = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);

        ListView ivProducts = findViewById(R.id.Music);
        pAdapter = new Adapter(General.this, listProduct);
        ivProducts.setAdapter(pAdapter);

        new GetMusic().execute();
    }
    private class GetMusic extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
               
                URL url = new URL("https://ngknn.ru:5101/NGKNN/ПетуненковаАП/api/Musics");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                StringBuilder result = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                return result.toString();

            } catch (Exception exception) {
                return null;
            }
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try
            {
                JSONArray tempArray = new JSONArray(s);
                for (int i = 0;i<tempArray.length();i++)
                {

                    JSONObject musicJson = tempArray.getJSONObject(i);
                    Mask tempProduct = new Mask(
                            musicJson.getInt("ID_Music"),
                            musicJson.getString("Name"),
                            musicJson.getString("Executor"),
                            musicJson.getString("Genre"),
                            musicJson.getString("Duration"),
                            musicJson.getString("Image")
                    );
                    listProduct.add(tempProduct);
                    pAdapter.notifyDataSetInvalidated();
                }
            } catch (Exception ignored) {


            }
        }


    }

}