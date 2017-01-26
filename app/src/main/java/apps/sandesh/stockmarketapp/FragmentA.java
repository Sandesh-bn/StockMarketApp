package apps.sandesh.stockmarketapp;


import android.app.Fragment;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static java.util.Locale.getDefault;

//import android.support.v4.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentA extends Fragment implements AdapterView.OnItemClickListener {

    Communicator comm;
    ArrayList<String> weatherInfo = new ArrayList<>();
    ArrayList<String> weeklyWeatherInfo = new ArrayList<>();
    EditText cityInput;
    JSONObject jsonPart;
    //TextView conditionText;
    // Weather related components
    //String url = "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=5c7c917f2eedbebf85086c5fab2569d2";

    public FragmentA() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_a, container, false);


        cityInput = (EditText) view.findViewById(R.id.cityInput);
        //DownloadTask downloadTask = new DownloadTask();
        //downloadTask.execute("http://api.openweathermap.org/data/2.5/weather?q=london&appid=5c7c917f2eedbebf85086c5fab2569d2");


        //weatherInfo = new ArrayList<>();
        //weeklyWeatherInfo = new ArrayList<>();
        ///conditionText = (TextView) view.findViewById(R.id.condition_text_main_screen);
        return view;
    }

    public void setCommunicator(Communicator c){
        this.comm = c;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       /*
        // refresh data and download the info again

        DownloadTask downloadTask = new DownloadTask();
        //String cityName = cityInput.getText().toString().trim();
        //String url =  "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=5c7c917f2eedbebf85086c5fab2569d2";
        String url =  "http://api.openweathermap.org/data/2.5/weather?q=london&appid=5c7c917f2eedbebf85086c5fab2569d2";
        downloadTask.execute(url);
        comm.respond(position, weatherInfo, weeklyWeatherInfo);
        */
    }

    public interface Communicator{
        //public void respond(int index, ArrayList<String> weatherInfo, ArrayList<String> weeklyInfo);
        public void setVariable(ArrayList<String> mainScreenInfo);
        public void setTodayInformation(ArrayList<String> todayWeatherInfo);
        public void setMultiDayInformation(ArrayList<String> todayWeatherInfo);
    }

    // JSON PROCESSING
    // THIS TASK ALL THE INFORMATION. i MADE COPY AND SEPARATED THIS CLASS
    // INTO 3 ASYNCTASK, 1 FOR MAINSCREEN, 2 FOR 24 HOUR AND 3 FOR WEEKLY
    /*
    public class DownloadTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder result = new StringBuilder();
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection)url.openConnection();

                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while(data != -1){
                    char current = (char)data;
                    result.append(current);
                    data = reader.read();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.i("insideAsync", "onPostExecuted");
            try {
                JSONObject jsonObject = new JSONObject(result);
                String weatherResponse = jsonObject.getString("weather");
                String condition = "";
                //Log.i("weather content", weatherInfo );

                JSONArray arr = new JSONArray(weatherResponse);

                for (int i = 0; i < arr.length(); i++){
                    //JSONObject jsonPart = arr.getJSONObject(i);
                    jsonPart = arr.getJSONObject(i);
                    Log.i("Main", jsonPart.getString("main"));
                    Log.i("Description", jsonPart.getString("description"));
                    condition = jsonPart.getString("description");
                }
                String city = jsonObject.getString("name");
                Log.i("City: ", city);
                String countryInfo = jsonObject.getString("sys");
                JSONObject countryObj = new JSONObject(countryInfo);
                String country = countryObj.getString("country");
                Log.i("Country: ", country );
                String coordInfo = jsonObject.getString("coord");
                JSONObject coordObj = new JSONObject(coordInfo);
                String latitude = coordObj.getString("lat");
                String longitude = coordObj.getString("lon");

                String temperatureInfo = jsonObject.getString("main");
                JSONObject tempObj = new JSONObject(temperatureInfo);
                String kelvinString = tempObj.getString("temp");
                String humidity = tempObj.getString("humidity");
                String pressure = tempObj.getString("pressure");
                String minTemperature = tempObj.getString("temp_min");
                String maxTemperature = tempObj.getString("temp_max");


                weatherInfo.add(city);
                weatherInfo.add(country);
                weatherInfo.add(kelvinString);
                weatherInfo.add(latitude);
                weatherInfo.add(longitude);
                weatherInfo.add(maxTemperature);
                weatherInfo.add(minTemperature);
                weatherInfo.add(pressure);
                weatherInfo.add(humidity);


                //celsiusValue = Double.parseDouble(kelvinString) - 273.15;

                String dateInfo = jsonObject.getString("dt");
                Log.i("insideAsync", weatherInfo.toString());



                //conditionText.setText(city + ", " + country);
                Log.i("city", city + ", " + country);
                //comm.setVariable(city + ", " + country);
                ArrayList<String> mainScreenInfo = new ArrayList<>();
                mainScreenInfo.add(city);
                mainScreenInfo.add(country);
                mainScreenInfo.add(condition);
                comm.setVariable(mainScreenInfo);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Log.i("Website content", result);
        }
        */
    // TODO: delete this old class
    public class DownloadTaskOld extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder result = new StringBuilder();
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection)url.openConnection();

                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while(data != -1){
                    char current = (char)data;
                    result.append(current);
                    data = reader.read();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.i("insideAsync", "onPostExecuted");
            try {
                JSONObject jsonObject = new JSONObject(result);
                String weatherResponse = jsonObject.getString("weather");
                String condition = "";
                //Log.i("weather content", weatherInfo );

                JSONArray arr = new JSONArray(weatherResponse);

                for (int i = 0; i < arr.length(); i++){
                    //JSONObject jsonPart = arr.getJSONObject(i);
                    jsonPart = arr.getJSONObject(i);
                    Log.i("Main", jsonPart.getString("main"));
                    Log.i("Description", jsonPart.getString("description"));
                    condition = jsonPart.getString("description");
                }
                String city = jsonObject.getString("name");
                Log.i("City: ", city);
                String countryInfo = jsonObject.getString("sys");
                JSONObject countryObj = new JSONObject(countryInfo);
                String country = countryObj.getString("country");
                Log.i("Country: ", country );

                String temperatureInfo = jsonObject.getString("main");
                JSONObject tempObj = new JSONObject(temperatureInfo);
                String kelvinString = tempObj.getString("temp");

                //celsiusValue = Double.parseDouble(kelvinString) - 273.15;

                String dateInfo = jsonObject.getString("dt");
                Log.i("insideAsync", weatherInfo.toString());



                //conditionText.setText(city + ", " + country);
                Log.i("city", city + ", " + country);
                //comm.setVariable(city + ", " + country);
                ArrayList<String> mainScreenInfo = new ArrayList<>();
                mainScreenInfo.add(city);
                mainScreenInfo.add(country);
                mainScreenInfo.add(condition);
                comm.setVariable(mainScreenInfo);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Log.i("Website content", result);
        }
    }

    public class DownloadTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder result = new StringBuilder();
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection)url.openConnection();

                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while(data != -1){
                    char current = (char)data;
                    result.append(current);
                    data = reader.read();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.i("insideMainScreen", "onPostExecuted");
            try {


                JSONObject jsonObject = new JSONObject(result);
                //String darkskyResponse = jsonObject.getString("currently");
                Log.i("quandl", jsonObject.toString());
               /*
                JSONObject todayObject = new JSONObject(darkskyResponse);

                //location information
                Double myLatitude = Double.parseDouble(jsonObject.getString("latitude"));
                Double myLongitude = Double.parseDouble(jsonObject.getString("longitude"));
                Geocoder geocoder = new Geocoder(getActivity(), getDefault());
                List<Address> addresses = geocoder.getFromLocation(myLatitude, myLongitude, 1);
                // MISC. Try printing addresses variable


                String temperature = todayObject.getString("temperature");
                String city =  addresses.get(0).getLocality();
                String country = addresses.get(0).getCountryName();
                String description = todayObject.getString("summary");
                String condition = todayObject.getString("icon");
                ArrayList<String> mainScreenInfo = new ArrayList<>();
                mainScreenInfo.add(temperature);
                mainScreenInfo.add(city);
                mainScreenInfo.add(country);
                mainScreenInfo.add(description);
                mainScreenInfo.add(condition);

                comm.setVariable(mainScreenInfo);
                */

                /*
                JSONObject jsonObject = new JSONObject(result);
                String weatherResponse = jsonObject.getString("weather");
                String condition = "";
                //Log.i("weather content", weatherInfo );
                JSONArray arr = new JSONArray(weatherResponse);
                for (int i = 0; i < arr.length(); i++){
                    //JSONObject jsonPart = arr.getJSONObject(i);
                    jsonPart = arr.getJSONObject(i);
                    Log.i("Main", jsonPart.getString("main"));
                    Log.i("Description", jsonPart.getString("description"));
                    condition = jsonPart.getString("description");
                }
                String city = jsonObject.getString("name");
                Log.i("City: ", city);
                String countryInfo = jsonObject.getString("sys");
                JSONObject countryObj = new JSONObject(countryInfo);
                String country = countryObj.getString("country");
                Log.i("Country: ", country );

                String temperatureInfo = jsonObject.getString("main");
                JSONObject tempObj = new JSONObject(temperatureInfo);
                String kelvinString = tempObj.getString("temp");

                //celsiusValue = Double.parseDouble(kelvinString) - 273.15;

                String dateInfo = jsonObject.getString("dt");
                Log.i("insideAsync", weatherInfo.toString());
                //conditionText.setText(city + ", " + country);
                Log.i("city", city + ", " + country);
                //comm.setVariable(city + ", " + country);
                */


            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Log.i("Website content", result);
        }
    }

    public class TodayWeatherTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder result = new StringBuilder();
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result.append(current);
                    data = reader.read();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.i("insideAsync", "onPostExecuted");
            try {
                JSONObject jsonObject = new JSONObject(result);
                String weatherResponse = jsonObject.getString("weather");
                String condition = "";
                //Log.i("weather content", weatherInfo );

                JSONArray arr = new JSONArray(weatherResponse);

                for (int i = 0; i < arr.length(); i++) {
                    //JSONObject jsonPart = arr.getJSONObject(i);
                    jsonPart = arr.getJSONObject(i);
                    Log.i("Main", jsonPart.getString("main"));
                    Log.i("Description", jsonPart.getString("description"));
                    condition = jsonPart.getString("description");
                }
                String city = jsonObject.getString("name");
                Log.i("City: ", city);
                String countryInfo = jsonObject.getString("sys");
                JSONObject countryObj = new JSONObject(countryInfo);
                String country = countryObj.getString("country");
                Log.i("Country: ", country);
                String coordInfo = jsonObject.getString("coord");
                JSONObject coordObj = new JSONObject(coordInfo);
                String latitude = coordObj.getString("lat");
                String longitude = coordObj.getString("lon");

                String temperatureInfo = jsonObject.getString("main");
                JSONObject tempObj = new JSONObject(temperatureInfo);
                String kelvinString = tempObj.getString("temp");
                String humidity = tempObj.getString("humidity");
                String pressure = tempObj.getString("pressure");
                String minTemperature = tempObj.getString("temp_min");
                String maxTemperature = tempObj.getString("temp_max");

                //celsiusValue = Double.parseDouble(kelvinString) - 273.15;

                String dateInfo = jsonObject.getString("dt");
                Log.i("insideAsync", weatherInfo.toString());


                //conditionText.setText(city + ", " + country);
                Log.i("city", city + ", " + country);
                //comm.setVariable(city + ", " + country);
                ArrayList<String> todayWeatherInfo = new ArrayList<>();
                todayWeatherInfo.add(condition);
                todayWeatherInfo.add(city);
                todayWeatherInfo.add(country);
                todayWeatherInfo.add(kelvinString);
                todayWeatherInfo.add(latitude);
                todayWeatherInfo.add(longitude);
                todayWeatherInfo.add(maxTemperature);
                todayWeatherInfo.add(minTemperature);
                todayWeatherInfo.add(pressure);
                todayWeatherInfo.add(humidity);
                comm.setTodayInformation(todayWeatherInfo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Log.i("Website content", result);


        }
    }


    public class MultiDayWeatherTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder result = new StringBuilder();
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result.append(current);
                    data = reader.read();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.i("insideAsync", "onPostExecuted");
            try {
                JSONObject jsonObject = new JSONObject(result);
                String weatherResponse = jsonObject.getString("weather");
                String condition = "";
                //Log.i("weather content", weatherInfo );

                JSONArray arr = new JSONArray(weatherResponse);

                for (int i = 0; i < arr.length(); i++) {
                    //JSONObject jsonPart = arr.getJSONObject(i);
                    jsonPart = arr.getJSONObject(i);
                    Log.i("Main", jsonPart.getString("main"));
                    Log.i("Description", jsonPart.getString("description"));
                    condition = jsonPart.getString("description");
                }
                String city = jsonObject.getString("name");
                Log.i("City: ", city);
                String countryInfo = jsonObject.getString("sys");
                JSONObject countryObj = new JSONObject(countryInfo);
                String country = countryObj.getString("country");
                Log.i("Country: ", country);
                String coordInfo = jsonObject.getString("coord");
                JSONObject coordObj = new JSONObject(coordInfo);
                String latitude = coordObj.getString("lat");
                String longitude = coordObj.getString("lon");

                String temperatureInfo = jsonObject.getString("main");
                JSONObject tempObj = new JSONObject(temperatureInfo);
                String kelvinString = tempObj.getString("temp");
                String humidity = tempObj.getString("humidity");
                String pressure = tempObj.getString("pressure");
                String minTemperature = tempObj.getString("temp_min");
                String maxTemperature = tempObj.getString("temp_max");

                //celsiusValue = Double.parseDouble(kelvinString) - 273.15;

                String dateInfo = jsonObject.getString("dt");
                Log.i("insideAsync", weatherInfo.toString());


                //conditionText.setText(city + ", " + country);
                Log.i("city", city + ", " + country);
                //comm.setVariable(city + ", " + country);
                ArrayList<String> todayWeatherInfo = new ArrayList<>();
                todayWeatherInfo.add(condition);

                ArrayList<String> multiDayWeatherInfo = new ArrayList<>();
                multiDayWeatherInfo.add("multi day weather info from api request");
                comm.setMultiDayInformation(multiDayWeatherInfo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Log.i("Website content", result);


        }
    }



    public class DarkSkyWeatherTodayTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder result = new StringBuilder();
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result.append(current);
                    data = reader.read();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.i("darkSky", "onPostExecuted");
            try {
                JSONObject jsonObject = new JSONObject(result);
                String darkskyResponse = jsonObject.getString("currently");
                Log.i("darkySkyResponse", darkskyResponse);
                String condition = "";
                JSONObject todayObject = new JSONObject(darkskyResponse);
                Log.i("time", todayObject.getString("time").toString());
                Log.i("summary", todayObject.getString("summary"));


                //location information
                Double myLatitude = Double.parseDouble(jsonObject.getString("latitude"));
                Double myLongitude = Double.parseDouble(jsonObject.getString("longitude"));
                Geocoder geocoder = new Geocoder(getActivity(), getDefault());
                List<Address> addresses = geocoder.getFromLocation(myLatitude, myLongitude, 1);
                // MISC. Try printing addresses variable
                String cityName = addresses.get(0).getAddressLine(0);
                String stateName = addresses.get(0).getAddressLine(1);
                String countryName = addresses.get(0).getAddressLine(2);
                Log.i("location", cityName + " " + stateName + " " + countryName);
                Log.i("whole address", addresses.toString());
                Log.i("address", addresses.get(0).getLocality() + " " + addresses.get(0).getCountryName());


                ArrayList<String> todayInfo = new ArrayList<>();
                todayInfo.add(addresses.get(0).getLocality());
                todayInfo.add(addresses.get(0).getCountryName());
                todayInfo.add(jsonObject.getString("latitude"));
                todayInfo.add(jsonObject.getString("longitude"));
                todayInfo.add(todayObject.getString("time"));
                todayInfo.add(todayObject.getString("summary"));
                todayInfo.add(todayObject.getString("temperature"));
                todayInfo.add(todayObject.getString("humidity"));
                todayInfo.add(todayObject.getString("windSpeed"));
                todayInfo.add(todayObject.getString("pressure"));
                todayInfo.add(jsonObject.getString("offset"));
                Log.i("todayInfo", todayInfo.toString());
                Log.i("local time", getLocalDate(todayObject.getString("time")));


                // LOCAL TIME
                String time = todayObject.getString("time");

                Date updatedate = new Date(Long.parseLong(time) * 1000);
                SimpleDateFormat formatted = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss z", Locale.getDefault());
                Log.i("locale", formatted.format(updatedate));


                comm.setTodayInformation(todayInfo);

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Log.i("Website content", result);


        }

        public String getLocalDate(String epochTime){
            Date UTCDate = new Date(Long.parseLong(epochTime));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy h:mm:ss a");
            String result = "";
            try {
                //System.out.println("local format: " + simpleDateFormat.format(UTCDate));
                //System.out.println("local Date: " + simpleDateFormat.parse(simpleDateFormat.format(UTCDate)));
                result = String.valueOf(simpleDateFormat.parse(simpleDateFormat.format(UTCDate)));
            } catch (Exception ex) {
                result = "Date could not be calculated.";
                //Log.i("INVALID DATE", "Date could not be calculated.");
            }
            return result;
        }
    }

    public class DarkSkyWeatherMultidayTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder result = new StringBuilder();
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result.append(current);
                    data = reader.read();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ArrayList<String> multiDayInfoArrayList = new ArrayList<>();
            Log.i("darkSky", "onPostExecuted");
            try {
                //$$$
                ///###
                JSONObject jsonObject = new JSONObject(result);
                String latitude = jsonObject.getString("latitude");
                String longitude = jsonObject.getString("longitude");
                multiDayInfoArrayList.add(latitude);
                multiDayInfoArrayList.add(longitude);
                Double myLatitude = Double.parseDouble(jsonObject.getString("latitude"));
                Double myLongitude = Double.parseDouble(jsonObject.getString("longitude"));
                Geocoder geocoder = new Geocoder(getActivity(), getDefault());
                List<Address> addresses = geocoder.getFromLocation(myLatitude, myLongitude, 1);
                Log.i("addresses", addresses.toString());
                multiDayInfoArrayList.add( addresses.get(0).getLocality() + ", " + addresses.get(0).getCountryName());
                multiDayInfoArrayList.add(jsonObject.getString("timezone"));

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Log.i("Website content", result);

            comm.setMultiDayInformation(multiDayInfoArrayList);
        }

        public String getLocalDateString(String epochTime){
            // for the detailed date
            //String strDateFormat = "dd/MM/yyyy HH:mm:ss EEEE";
            String strDateFormat = "dd/MM$EEEE";
            SimpleDateFormat sdf2 = new SimpleDateFormat(strDateFormat);
            String res = sdf2.format(new Date(Long.parseLong(epochTime) * 1000));
            return res;
        }
    }
}
