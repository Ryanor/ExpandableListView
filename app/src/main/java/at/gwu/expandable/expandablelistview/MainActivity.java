package at.gwu.expandable.expandablelistview;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ExpandableListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> serviceList;
    private HashMap<String, List<Characteristic>> characteristicMap;

    private ExpandableListView expandableListView;
    private ExpandableListViewAdapter expandableListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expandableListView = findViewById(R.id.expandableListView);

        serviceList = new ArrayList<>();
        characteristicMap = new HashMap<>();

        new RequestJSON().execute("http://192.168.0.3:3000/profile/json");

        expandableListViewAdapter = new ExpandableListViewAdapter(this, serviceList, characteristicMap);
    }


    private class RequestJSON extends AsyncTask<String, String, JSONArray> {

        @Override
        protected JSONArray doInBackground(String... strings) {
            HttpURLConnection urlConnection = null;
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader;

            try {
                URL url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
                reader = new BufferedReader(new InputStreamReader(stream));

                String line;

                while ((line = reader.readLine()) != null) {
                    buffer.append(line)
                            .append("\n");
                }

                return new JSONArray(buffer.toString());

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONArray result) {
            super.onPostExecute(result);

            if (result != null) {

                for (int i = 0; i < result.length(); i++) {
                    try {
                        JSONObject jsonService = result.getJSONObject(i);
                        Log.d("PARSE", ">" + jsonService.toString());
                        String serviceUUID = jsonService.getString("uuid");

                        List<Characteristic> characteristicList = new ArrayList<>();

                        JSONArray jsonCharacteristics = jsonService.getJSONArray("characteristics");

                        for (int j = 0; j < jsonCharacteristics.length(); j++) {
                            JSONObject jsonCharacteristic = jsonCharacteristics.getJSONObject(j);
                            String characteristicUUID = jsonCharacteristic.getString("uuid");

                            JSONArray characteristicProperties = jsonCharacteristic.getJSONArray("properties");
                            String[] properties = new String[characteristicProperties.length()];

                            for (int x = 0; x < characteristicProperties.length(); x++) {
                                properties[x] = (String) characteristicProperties.get(x);
                            }
                            String characteristicValue = jsonCharacteristic.getString("value");

                            characteristicList.add(new Characteristic(characteristicUUID, properties, characteristicValue));
                        }
                        serviceList.add(serviceUUID);
                        characteristicMap.put(serviceUUID, characteristicList);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Log.d("Response", "No response parsed!");
            }

            expandableListView.setAdapter(expandableListViewAdapter);
        }
    }
}
