package com.example.katwapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView info_txt;
    EditText city_txt;
    ImageView icon;

    String city = "";
    String key = "24d2bec763b426d6865ef99f1f8c7697";

    int temperature;

    int[] icons = new int[]{R.drawable.ic__sun,R.drawable.ic_cloud,R.drawable.ic_cloud_rain};
    String[] iconsd = {"Sunny","Clouds","Rain"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        info_txt = findViewById(R.id.info_txt);
        city_txt = findViewById(R.id.city_txt);
        icon = findViewById(R.id.icon);

       // DownloadContent downloadContent = new DownloadContent();
       // downloadContent.execute("https://api.openweathermap.org/data/2.5/weather?q=Athens&appid=24d2bec763b426d6865ef99f1f8c7697");
    }

    public class DownloadContent extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(strings[0]);

                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();
                while (data != -1){
                    char current = (char)data;
                    result += current;
                    data = reader.read();
                }

                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }catch (Exception e){
                Toast.makeText(MainActivity.this, "d", Toast.LENGTH_SHORT).show();
            }

            return null;
        }//end of doInBackground

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);

               String weather =  jsonObject.getString("weather");

                JSONArray jsonArray = new JSONArray(weather);

                JSONObject weatherObject = jsonArray.getJSONObject(0);
                String mainw = weatherObject.getString("main");
                String description = weatherObject.getString("description");


                JSONObject wind =  jsonObject.getJSONObject("wind");
                String speed_wind =  wind.getString("speed");
                String deg_wind =  wind.getString("deg");

                JSONObject main =  jsonObject.getJSONObject("main");
                String temp =  main.getString("temp");

                temperature = (int) Float.parseFloat(temp) - 273;


                info_txt.setText( String.valueOf(temperature)+"\u2103");

                for (int i=0;i<iconsd.length;i++){
                    if(iconsd[i].equals(mainw)){
                        icon.setImageResource(icons[i]);
                        break;
                    }
                }


                System.out.println("Result: "+mainw);

            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
                Toast.makeText(MainActivity.this, "Wrong City Name", Toast.LENGTH_SHORT).show();
            }



        }

    }

    public void searchCity(View v){

        city = city_txt.getText().toString().trim();

        if(city.isEmpty()){
            Toast.makeText(this, "Enter city", Toast.LENGTH_SHORT).show();
            return;
        }

         DownloadContent downloadContent = new DownloadContent();
         downloadContent.execute("https://api.openweathermap.org/data/2.5/weather?q="+city
                 +"&appid="+key);


    }
}

