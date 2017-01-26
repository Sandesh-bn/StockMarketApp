package apps.sandesh.stockmarketapp;


import android.app.Fragment;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//import android.support.v4.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentB extends Fragment {
    TextView textView;
    RelativeLayout dayView;
    RelativeLayout weekView;
    TextView temperatureText, condition,
            latitudeText, longitudeText, windSpeedText, pressureText, humidityText,timeZoneOffsetText,
            dummy, conditionTodayText, locationTodayText, conditionMultiDay, multiDayLocation, multiDayTimezone;
    DecimalFormat twoDigits = new DecimalFormat("#.##");
    ListView hourlyListView;
    ListView multiDayListView;
    HourlyWeatherAdapter adapter;
    MultiDayWeatherAdapter multiDayAdapter;
    ArrayList<HourlyWeather> hourlyWeatherArrayList;
    ArrayList<MultiDayWeather> multiDayWeatherArrayList;
    ImageView hourlyImage;

    public FragmentB() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_b, container, false);

        hourlyImage = (ImageView)view.findViewById(R.id.today_hourly_imageView);
        textView = (TextView) view.findViewById(R.id.description_text);
        //main = (TextView) view.findViewById(R.id.latitudeTextView3);
        dayView = (RelativeLayout) view.findViewById(R.id.day_layout);
        weekView = (RelativeLayout) view.findViewById(R.id.week_layout);

        temperatureText = (TextView)view.findViewById(R.id.temperatureTextView);
        //condition = (TextView)view.findViewById(R.id.conditionTextView)).setText(conditionResult);
        multiDayLocation = (TextView)view.findViewById(R.id.multi_day_location_textView);
        multiDayTimezone = (TextView)view.findViewById(R.id.multi_day_timezone_textView);
        conditionTodayText = (TextView) view.findViewById(R.id.conditionTodayTextView);
        locationTodayText = (TextView) view.findViewById(R.id.locationTodayTextView);
        latitudeText = (TextView)view.findViewById(R.id.latitudeTextView);
        longitudeText = (TextView)view.findViewById(R.id.longitudeTextView);
        timeZoneOffsetText = (TextView) view.findViewById(R.id.timeZoneOffsetText);
        windSpeedText = (TextView)view.findViewById(R.id.windSpeedTextView);
        pressureText = (TextView)view.findViewById(R.id.pressureTextView);
        humidityText = (TextView)view.findViewById(R.id.humidityTextView);
        hourlyListView = (ListView) view.findViewById(R.id.today_hourly_listview);
        multiDayListView = (ListView) view.findViewById(R.id.multi_day_listView);
        conditionMultiDay = (TextView)view.findViewById(R.id.condition_multiday_textView);

        intializeFont(multiDayLocation, multiDayTimezone, conditionTodayText, locationTodayText,
                latitudeText, longitudeText, timeZoneOffsetText, windSpeedText, pressureText, humidityText
                , conditionMultiDay);


        return view;
    }


    public void intializeFont(View... views){
        Typeface customFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/oxygen.ttf");
        for (View view: views){
            if (view instanceof TextView)
                ((TextView)view).setTypeface(customFont);
            else if(view instanceof EditText)
                ((EditText)view).setTypeface(customFont);
        }
    }

    public void changeData(int index, ArrayList<String> moreInfoList){
    /*    String[] description = getResources().getStringArray(R.array.description);

        String conditionResult = moreInfoList.get(0);
        String cityCountry = moreInfoList.get(1) + ", " + moreInfoList.get(2);
        String kelvinResult = moreInfoList.get(3);
        Double celsiusValue = Double.parseDouble(kelvinResult) - 273.15;
        String latitudeResult = moreInfoList.get(4);
        String longResult = moreInfoList.get(5);
        String minMax = moreInfoList.get(6) + "/" + moreInfoList.get(7);
        String humidityResult = moreInfoList.get(9);
        String pressureResult = moreInfoList.get(8);

        temperatureText.setText(Html.fromHtml(twoDigits.format(celsiusValue) + "<sup><small>o</small></sup><small>c</small>"));
        latitudeText.setText(latitudeResult);
        longitudeText.setText(longResult);
        windSpeedText.setText(minMax);
        pressureText.setText(pressureResult);
        humidityText.setText(humidityResult);

        textView.setText(description[index]);
        if (index == 0){
            dayView.setVisibility(View.VISIBLE);
            weekView.setVisibility(View.GONE);
        }
        else if (index == 1){
            weekView.setVisibility(View.VISIBLE);
            dayView.setVisibility(View.GONE);
        }
        */
    }

    public void updateView(String message){
        dummy.setText(message);
    }
    /*todayInfo.add(addresses.get(0).getLocality());
    todayInfo.add(addresses.get(0).getCountryName());
    todayInfo.add(jsonObject.getString("latitude"));
    todayInfo.add(jsonObject.getString("longitude"));
    todayInfo.add(todayObject.getString("time"));
    todayInfo.add(todayObject.getString("summary"));
    todayInfo.add(todayObject.getString("temperature"));
    todayInfo.add(todayObject.getString("humidity"));
    todayInfo.add(todayObject.getString("windSpeed"));
    todayInfo.add(todayObject.getString("pressure"));
    todayInfo.add(todayObject.getString("ozone"));*/
    public void showTodayInfo(ArrayList<String> todayInfo){
        Log.i("showTodayInfo in B", todayInfo.toString());

        String cityCountry = todayInfo.get(0) + ", " + todayInfo.get(1);
        //String kelvinResult = todayInfo.get(6);
        //Double celsiusValue = Double.parseDouble(kelvinResult) - 273.15;


        String latitudeResult = todayInfo.get(2);
        String longResult = todayInfo.get(3);
        String conditionResult = todayInfo.get(5);
        String celsiusResult = todayInfo.get(6);
        Double celsiusValue = Double.parseDouble(celsiusResult);
        //String minMax = todayInfo.get(6) + "/" + todayInfo.get(7);
        String minMax = "dummyMin" + "/" + "dummyMax";
        String humidityResult = todayInfo.get(7);
        String windSpeedResult = todayInfo.get(8);
        String pressureResult = todayInfo.get(9);
        String offsetResult = todayInfo.get(10);

        locationTodayText.setText(cityCountry);
        conditionTodayText.setText(conditionResult);
        temperatureText.setText(Html.fromHtml(twoDigits.format(celsiusValue) + "<sup><small>o</small></sup><small>c</small>"));
        latitudeText.setText(twoDigits.format(Double.parseDouble(latitudeResult)));
        longitudeText.setText(twoDigits.format(Double.parseDouble(longResult)));
        windSpeedText.setText(windSpeedResult);
        pressureText.setText(pressureResult);
        humidityText.setText(humidityResult);
        timeZoneOffsetText.setText(offsetResult);


        if (weekView.getVisibility() == View.VISIBLE)
            weekView.setVisibility(View.GONE);
        dayView.setVisibility(View.VISIBLE);

        hourlyWeatherArrayList = new ArrayList<>();
        String hourlyUrl = "https://api.darksky.net/forecast/daca0825a289ec34f56c3dd8171776a2/" + latitudeResult + "," + longResult + "?units=si";
        new DarkSkyHourlyWeatherTask().execute(hourlyUrl);
        adapter = new HourlyWeatherAdapter(getActivity(), R.layout.today_hourly_row,
                hourlyWeatherArrayList);
        hourlyListView.setAdapter(adapter);

        /*
        if (dayView.getVisibility() == View.INVISIBLE){
            dayView.setVisibility(View.VISIBLE);
            weekView.setVisibility(View.GONE);
        }
        */


    }



    public void showMultiDayInfo(ArrayList<String> multiDayInfo){
        Log.i("showMultiDayInfo in B", multiDayInfo.toString());
        if (dayView.getVisibility() == View.VISIBLE)
            dayView.setVisibility(View.GONE);
        weekView.setVisibility(View.VISIBLE);
        // TODO: you need to get just the coordinates. you are actually getting all the hourly information
        //$$$
        //###

        String latitudeResult = multiDayInfo.get(0);
        String longResult = multiDayInfo.get(1);
        multiDayLocation.setText(multiDayInfo.get(2));
        multiDayTimezone.setText(multiDayInfo.get(3));
        multiDayWeatherArrayList = new ArrayList<>();
        String multiDayURL = "https://api.darksky.net/forecast/daca0825a289ec34f56c3dd8171776a2/" + latitudeResult + "," + longResult + "?units=si";
        new DarkSkyMultiDayWeatherTask().execute(multiDayURL);
        multiDayAdapter = new MultiDayWeatherAdapter(getActivity(), R.layout.multi_day_weather_row,
                multiDayWeatherArrayList);

        multiDayListView.setAdapter(multiDayAdapter);

    }

    public class DarkSkyHourlyWeatherTask extends AsyncTask<String, Void, String> {

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
                // USING hourly instead of daily gives hourly information
                JSONObject dailyObject = jsonObject.getJSONObject("hourly");
                JSONArray arr = dailyObject.getJSONArray("data");
                for (int i = 0; i < arr.length(); i++){
                    JSONObject jsonPart = arr.getJSONObject(i);
                    String localTime = getLocalDateString(jsonPart.getString("time"));
                    String description = jsonPart.getString("summary");
                    String condition = jsonPart.getString("icon");
                    String temperature = jsonPart.getString("temperature");
                    Log.i("data", localTime + "$" + condition + "$" + description );

                    JSONObject object = arr.getJSONObject(i);

                    HourlyWeather hourlyWeather = new HourlyWeather();
                    hourlyWeather.setCondition(description);
                    hourlyWeather.setDay(localTime.substring(6));
                    hourlyWeather.setTime(localTime.substring(0, 5));
                    hourlyWeather.setTemperature(temperature);

                    if (condition.equals("clear-day"))
                        hourlyWeather.setImage(R.drawable.clear_day);
                    else if (condition.equals("cloudy"))
                        hourlyWeather.setImage(R.drawable.cloudy);
                    else if (condition.equals("rain"))
                        hourlyWeather.setImage(R.drawable.rain);
                    else if (condition.equals("sleet"))
                        hourlyWeather.setImage(R.drawable.sleet);
                    else if (condition.equals("snow"))
                        hourlyWeather.setImage(R.drawable.snow);
                    else if (condition.equals("fog"))
                        hourlyWeather.setImage(R.drawable.fog);
                    else if (condition.equals("wind"))
                        hourlyWeather.setImage(R.drawable.wind);
                    else if (condition.equals("clear-night"))
                        hourlyWeather.setImage(R.drawable.clear_night);
                    else if (condition.equals("partly-cloudy-day"))
                        hourlyWeather.setImage(R.drawable.partly_cloudy_day);
                    else
                        hourlyWeather.setImage(R.drawable.rain);
                    hourlyWeatherArrayList.add(hourlyWeather);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            adapter.notifyDataSetChanged();

        }

        public String getLocalDateString(String epochTime){
            // for the detailed date
            //String strDateFormat = "dd/MM/yyyy HH:mm:ss EEEE";
            String strDateFormat = "HH:mm$EE";
            SimpleDateFormat sdf2 = new SimpleDateFormat(strDateFormat);
            String res = sdf2.format(new Date(Long.parseLong(epochTime) * 1000));
            return res;
        }
    }


    public class DarkSkyMultiDayWeatherTask extends AsyncTask<String, Void, String> {

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
                // USING hourly instead of daily gives hourly information
                JSONObject dailyObject = jsonObject.getJSONObject("daily");
                JSONArray arr = dailyObject.getJSONArray("data");
                for (int i = 0; i < arr.length(); i++){
                    JSONObject jsonPart = arr.getJSONObject(i);
                    String localTime = getLocalDateString(jsonPart.getString("time"));
                    String description = jsonPart.getString("summary");
                    String condition = jsonPart.getString("icon");
                    String temperature = jsonPart.getString("temperatureMin") + "/" +
                            jsonPart.getString("temperatureMax");
                    Log.i("data", localTime + "$" + condition + "$" + description );

                    JSONObject object = arr.getJSONObject(i);


                    MultiDayWeather multiDayWeather = new MultiDayWeather();
                    multiDayWeather.setMultiDayCondition(description);
                    multiDayWeather.setMultidayDay(localTime.substring(6));
                    multiDayWeather.setMultidayDate(localTime.substring(0, 5));
                    multiDayWeather.setMultiDayTemperature(temperature);

                    if (condition.equals("clear-day"))
                        multiDayWeather.setMultiDayImageId(R.drawable.clear_day);
                    else if (condition.equals("cloudy"))
                        multiDayWeather.setMultiDayImageId(R.drawable.cloudy);
                    else if (condition.equals("rain"))
                        multiDayWeather.setMultiDayImageId(R.drawable.rain);
                    else if (condition.equals("sleet"))
                        multiDayWeather.setMultiDayImageId(R.drawable.sleet);
                    else if (condition.equals("snow"))
                        multiDayWeather.setMultiDayImageId(R.drawable.snow);
                    else if (condition.equals("fog"))
                        multiDayWeather.setMultiDayImageId(R.drawable.fog);
                    else if (condition.equals("wind"))
                        multiDayWeather.setMultiDayImageId(R.drawable.wind);
                    else if (condition.equals("clear-night"))
                        multiDayWeather.setMultiDayImageId(R.drawable.clear_night);
                    else if (condition.equals("partly-cloudy-day"))
                        multiDayWeather.setMultiDayImageId(R.drawable.partly_cloudy_day);
                    else
                        multiDayWeather.setMultiDayImageId(R.drawable.rain);
                    multiDayWeatherArrayList.add(multiDayWeather);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            multiDayAdapter.notifyDataSetChanged();

        }

        public String getLocalDateString(String epochTime){
            // for the detailed date
            //String strDateFormat = "dd/MM/yyyy HH:mm:ss EEEE";
            String strDateFormat = "MM/dd$EE";
            SimpleDateFormat sdf2 = new SimpleDateFormat(strDateFormat);
            String res = sdf2.format(new Date(Long.parseLong(epochTime) * 1000));
            return res;
        }
    }
}
