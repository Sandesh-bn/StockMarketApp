package apps.sandesh.stockmarketapp;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

//import android.support.v4.app.FragmentManager;

public class MainActivity extends AppCompatActivity implements FragmentA.Communicator{
    FragmentA f1;
    FragmentB f2;
    FragmentManager fm;
    String info;

    TextView searchButton, showMapButton,
           todayButton, weeklyButton;
    EditText cityUserInput;
    TextView conditionText;
    TextView locationText;
    ArrayList<String> todayInfo;
    ArrayList<String> multiDayInfo;
    ImageView mainScreenImage;
    ViewGroup mainPortraitLayout;

    // Stock market app variables from here.
    private LineChart lineChart;
    LineDataSet set;
    ArrayList<Double> xVal = new ArrayList<>();
    ArrayList<Double> yVal = new ArrayList<>();
    ArrayList<Entry> entries = new ArrayList<>();
    TextView oneLabel, twoLabel, threeLabel, fourLabel, fiveLabel, sixLabel;
    TextView oneValue, twoValue, threeValue, fourValue, fiveValue, sixValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainPortraitLayout = (ViewGroup) findViewById(R.id.activity_main);
        fm = getFragmentManager();
        f1 = (FragmentA)fm.findFragmentById(R.id.fragment);
        f1.setCommunicator(this);
        searchButton = (Button) findViewById(R.id.search_button);
        showMapButton = (TextView)findViewById(R.id.show_map_button);


        info = "";
        conditionText = (TextView) findViewById(R.id.condition_text_main_screen);
        mainScreenImage = (ImageView) findViewById(R.id.resultImage);
        locationText = (TextView) findViewById(R.id.location_main_screen);
        intializeFont(conditionText, locationText, cityUserInput, searchButton, todayButton, weeklyButton, showMapButton);



        // Make status bar transparent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        // stock market app variables
        lineChart = (LineChart) findViewById(R.id.mainScreenChart);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0);
        xAxis.setAxisLineWidth(1f);
        oneLabel = (TextView) findViewById(R.id.one_label);
        twoLabel = (TextView) findViewById(R.id.two_label);
        threeLabel = (TextView) findViewById(R.id.three_label);
        fourLabel = (TextView) findViewById(R.id.four_label);
        fiveLabel = (TextView) findViewById(R.id.five_label);
        sixLabel = (TextView) findViewById(R.id.six_label);
        oneValue = (TextView) findViewById(R.id.one_value);
        twoValue = (TextView) findViewById(R.id.two_value);
        threeValue = (TextView) findViewById(R.id.three_value);
        fourValue = (TextView) findViewById(R.id.four_value);
        fiveValue = (TextView) findViewById(R.id.five_value);
        sixValue = (TextView) findViewById(R.id.six_value);

    }

    public void showDialog(View v){
        NoInternetDialog noInternetDialog = new NoInternetDialog();
        noInternetDialog.show(getSupportFragmentManager(), "no_internet_tag");
    }



    public boolean isOnline(){
        // Test internet connection
        boolean connected = false;
        ConnectivityManager connectivityManager =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
            connected = true;

        return connected;
    }

    public void intializeFont(View... views){
        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/oxygen.ttf");
        for (View view: views){
            if (view instanceof TextView)
                ((TextView)view).setTypeface(customFont);
            else if(view instanceof EditText)
                ((EditText)view).setTypeface(customFont);
            else if(view instanceof Button)
                ((Button)view).setTypeface(customFont);
        }
    }






    public void setVariable(ArrayList<String> mainScreenInfo){
        String city = mainScreenInfo.get(1);
        String country = mainScreenInfo.get(2);
        String description = mainScreenInfo.get(3);
        String condition = mainScreenInfo.get(4);
       // conditionText.setText(this.info);
        conditionText.setText(description);

        if (city == null || city.equals("null")){
            city = cityUserInput.getText().toString().trim();
            Toast.makeText(this, "Choosing nearest location: " + city , Toast.LENGTH_SHORT).show();
        }


    }

    //public void animateBackGround(Drawable fromColor, Drawable toColor) {
    public void animateBackGround() {

        /*


        mainPortraitLayout.setBackgroundResource(R.drawable.translate);
        //mainPortraitLayout.setBackgroundResource(R.drawable.clear_day);
        TransitionDrawable transition = (TransitionDrawable) mainPortraitLayout.getBackground();
        transition.startTransition(3000);
        */
        /*
        Drawable backgrounds[] = {R.drawable.wind_fog_gradient, R.drawable.clear_night};
        TransitionDrawable crossfader = new TransitionDrawable(backgrounds);
        mainPortraitLayout.setBackgroundDrawable(crossfader);
        TransitionDrawable tr = (TransitionDrawable)mainPortraitLayout.getBackground();
        tr.startTransition(3000);
        */
    }




    public void showWeatherOnMainScreen2(View view){
        conditionText = (TextView) findViewById(R.id.condition_text_main_screen);
        locationText = (TextView) findViewById(R.id.location_main_screen);
        Log.i("User input", cityUserInput.getText().toString());
        //String cityName = cityUserInput.getText().toString().trim();
        String cityName = null;
        try {
            cityName = URLEncoder.encode(cityUserInput.getText().toString(), "UTF-8");
            if (cityName.equals("")){
                Toast.makeText(getApplicationContext(), "Using default city: london", Toast.LENGTH_SHORT).show();
                cityName = "london";
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=5c7c917f2eedbebf85086c5fab2569d2";
        //FragmentA fragmentA = new FragmentA();
        //FragmentA.DownloadTask downloadTask = fragmentA.new DownloadTask();
        FragmentA.DownloadTask downloadTask = f1.new DownloadTask();
        downloadTask.execute(url);
    }

    public void showWeatherOnMainScreen(View view){
        if (!isOnline()){
            showDialog(view);
        }
        else{
            String url = "https://www.quandl.com/api/v3/datasets/NASDAQOMX/COMP.json?api_key=D_2ozLMLXJJ-rTKs3qm2&start_date=2017-01-20&end_date=2017-01-25";
            /*
            String location = cityUserInput.getText().toString();
            if (location == null) location = "san francisco";
            ArrayList<String> coordinates = getLatitudeAndLongitudeFromGoogleMapForAddress(location);
            if (coordinates.size() == 0) {
                Toast.makeText(this, "Using default city: San Francisco", Toast.LENGTH_SHORT).show();
                coordinates.add("37.7749295");//latitude for san francisco
                coordinates.add("-122.419415");//longitude for san francisco
            }
            String latitude = coordinates.get(0), longitude = coordinates.get(1);
            String url = "https://api.darksky.net/forecast/daca0825a289ec34f56c3dd8171776a2/" + latitude
                    + "," + longitude + "?units=si";
            */
            FragmentA.DownloadTask downloadTask = f1.new DownloadTask();
            downloadTask.execute(url);
        }
    }





    public void showDummyInfo(View view) {
        launchDetails("Hello from main screen");
    }

    public void launchDetails(String message) {
        f2 = (FragmentB) fm.findFragmentById(R.id.fragment2);
        if (f2 != null && f2.isVisible()){
            //f2.changeData(index, weatherInfo);

        }
        else {
            Intent intent = new Intent(this, AnotherActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void setTodayInformation(ArrayList<String> todayWeatherInfo) {
        todayInfo = todayWeatherInfo;
        Log.i("setTodayInformaion", todayInfo.toString());
        launchTodayFragment(todayInfo);
    }
    public void showTodayForecast(View view) {
        //conditionText = (TextView) findViewById(R.id.condition_text_main_screen);
        //locationText = (TextView) findViewById(R.id.location_main_screen);
        Log.i("User input", cityUserInput.getText().toString());
        //String cityName = cityUserInput.getText().toString().trim();
        String cityName = null;
        todayInfo = new ArrayList<>();
        try {
            cityName = URLEncoder.encode(cityUserInput.getText().toString(), "UTF-8");
            if (cityName.equals("")){
                Toast.makeText(getApplicationContext(), "Using default city: london", Toast.LENGTH_SHORT).show();
                cityName = "london";
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=5c7c917f2eedbebf85086c5fab2569d2";
        //FragmentA fragmentA = new FragmentA();
        //FragmentA.DownloadTask downloadTask = fragmentA.new DownloadTask();
        FragmentA.TodayWeatherTask downloadTask = f1.new TodayWeatherTask();
        downloadTask.execute(url);




        //TODO: get arraylist containing today's information
        //launchTodayFragment(todayInfo);
    }

    public void launchTodayFragment(ArrayList<String> todayInfo){

        Log.i("launchTodayFragment", todayInfo.toString());

        f2 = (FragmentB) fm.findFragmentById(R.id.fragment2);
        if (f2 != null && f2.isVisible()){
            //TODO: implement this

        }
        else {
            Intent intent = new Intent(this, AnotherActivity.class);

            startActivity(intent);
        }

    }

    // MultidayTask
    @Override
    public void setMultiDayInformation(ArrayList<String> multiDayWeatherInfo) {
        multiDayInfo = multiDayWeatherInfo;

        launchMultidayFragment(multiDayInfo);
    }


    public void launchMultidayFragment(ArrayList<String> multiDayInfo){

        Log.i("Success multiDayInfo", multiDayInfo.toString());

        f2 = (FragmentB) fm.findFragmentById(R.id.fragment2);
        if (f2 != null && f2.isVisible()){
            //TODO: implement this

        }
        else {
            Intent intent = new Intent(this, AnotherActivity.class);

            startActivity(intent);
        }

    }


    public void darkWeatherToday(View view){
        if (!isOnline()){
            showDialog(view);
        }
        else {
            // TODO: add an if condition to execute the below only when it is landscape
            showWeatherOnMainScreen(view);
            String location = cityUserInput.getText().toString();
            if (location == null) location = "london";
            ArrayList<String> coordinates = getLatitudeAndLongitudeFromGoogleMapForAddress(location);
            if (coordinates.size() == 0) {
                Toast.makeText(this, "Using default city: San Francisco", Toast.LENGTH_SHORT).show();
                coordinates.add("37.7749295");//latitude for san francisco
                coordinates.add("-122.419415");//longitude for san francisco
            }
            String latitude = coordinates.get(0), longitude = coordinates.get(1);
            String url = "https://api.darksky.net/forecast/daca0825a289ec34f56c3dd8171776a2/" + latitude
                    + "," + longitude + "?units=si";


            FragmentA.DarkSkyWeatherTodayTask downloadTask = f1.new DarkSkyWeatherTodayTask();
            downloadTask.execute(url);
        }
    }


    public ArrayList<String> getLatitudeAndLongitudeFromGoogleMapForAddress(String searchedAddress){
        ArrayList<String> coordinates = new ArrayList<>();
        Geocoder coder = new Geocoder(this);
        List<Address> address;
        try {

            address = coder.getFromLocationName(searchedAddress,5);
            if (address == null) {
                Log.d("gecoder", "############Address not correct #########");
            }
            Address location = address.get(0);

            Log.d("gecoder", "Address Latitude : "+ location.getLatitude()+ "Address Longitude : "+ location.getLongitude());
            coordinates.add(location.getLatitude() + "");
            coordinates.add(location.getLongitude() + "");

        }catch(Exception e){
            Log.d("gecoder", "MY_ERROR : ############Address Not Found");
        }
        return coordinates;
    }

    public void darkWeatherMultiday(View view) {

        if (!isOnline()){
            showDialog(view);
        }
        else {
            // TODO: add an if condition to execute the below only when it is landscape
            showWeatherOnMainScreen(view);
            String location = cityUserInput.getText().toString();
            if (location == null) location = "london";
            ArrayList<String> coordinates = getLatitudeAndLongitudeFromGoogleMapForAddress(location);
            if (coordinates.size() == 0) {
                Toast.makeText(this, "Using default city: San Francisco", Toast.LENGTH_SHORT).show();
                coordinates.add("37.7749295");//latitude for san francisco
                coordinates.add("-122.419415");//longitude for san francisco
            }
            String latitude = coordinates.get(0), longitude = coordinates.get(1);
            String url = "https://api.darksky.net/forecast/daca0825a289ec34f56c3dd8171776a2/" + latitude
                    + "," + longitude + "?units=si";
            Log.i("url", url);
            FragmentA.DarkSkyWeatherMultidayTask downloadTask = f1.new DarkSkyWeatherMultidayTask();
            downloadTask.execute(url);
        }

    }



    public void getSandPData(View view) {

        if (!isOnline())
            showDialog(view);
        else {
            String url = "https://www.quandl.com/api/v3/datasets/YAHOO/INDEX_GSPC.json?api_key=D_2ozLMLXJJ-rTKs3qm2&start_date=2017-01-20";

            DownloadTask downloadTask = new DownloadTask();
            downloadTask.execute(url);
        }
    }

    public void showStockInformation(View view) {
        if (!isOnline()){
            showDialog(view);
        }
        else {

            f2 = (FragmentB) fm.findFragmentById(R.id.fragment2);
            if (f2 != null && f2.isVisible()) {

            } else {
                Intent intent = new Intent(this, AnotherActivity.class);
                startActivity(intent);
            }
        }
    }

    // TODO: move it to fragment class
    public class DownloadTask extends AsyncTask<String, Void, String> {

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
            Log.i("insideMainScreen", "onPostExecuted");
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONObject obj = jsonObject.getJSONObject("dataset");
                JSONArray array = obj.getJSONArray("data");
                String dateString = obj.get("newest_available_date").toString();
                if (set != null) {
                    set.clear();
                    set.notifyDataSetChanged();
                }
                //Log.i("label", columnNames.toString());
                JSONArray columnNames = obj.getJSONArray("column_names");
                oneLabel.setText(columnNames.getString(0));
                twoLabel.setText(columnNames.getString(1));
                threeLabel.setText(columnNames.getString(2));
                fourLabel.setText(columnNames.getString(3));
                fiveLabel.setText(columnNames.getString(4));
                sixLabel.setText(columnNames.getString(5).substring(0,3) + "(in Mil.)" );
                yVal.clear();
                String values[] = array.getJSONArray(0).toString().split(",");
                Log.i("values", Arrays.toString(values));
                Log.i("delete", values[0] + "");
                Log.i("delete", values[1] + "");
                oneValue.setText(getDate(dateString));

                Double volume = Double.parseDouble(values[5])/1000000000;
                twoValue.setText(getDecimal(values[1]));
                threeValue.setText(getDecimal(values[2]));
                fourValue.setText(getDecimal(values[3]));
                fiveValue.setText(getDecimal(values[4]));
                sixValue.setText(getDecimal(volume.toString()) + " B." );


                for (int i = 0; i < array.length(); i++) {
                    //Log.i("quandl", array[i][4] + "");
                    //JSONObject o = new JSONObject(array[i]);
                    //Log.i("qua", array.getJSONArray(i).get(3).toString());
                    yVal.add((Double) array.getJSONArray(i).get(3));
                }
                Log.i("yVal in s and p", yVal.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }


            for (int i = 0; i < yVal.size(); i++) {
                //float xf = (float) (xVal.get(i) * 1.0f);
                float yf = (float) (yVal.get(i) * 1.0f);
                entries.add(new Entry(i  * 1.0f, yf));

            }

            set = new LineDataSet(entries, "S and P 500");
            set.notifyDataSetChanged();
            Log.i("linedataset in s and p", set.toString());
            set.setColor(getResources().getColor(R.color.colorGreen));
            set.setDrawFilled(true);
            LineData lineData = new LineData(set);
            lineChart.setData(lineData);
            lineChart.notifyDataSetChanged();
            //lineChart.clear();
            lineChart.invalidate();

        }

        private String getDecimal(String value) {
            BigDecimal bd = new BigDecimal(value);
            bd = bd.setScale(2, RoundingMode.HALF_UP);
            return bd.doubleValue() + "";
        }
        private String getDate(String value){
            Date date = null;
            String formattedDate = "";
            try {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(value);
                formattedDate = new SimpleDateFormat("E, MMM d").format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return formattedDate;
        }



    }


    public void getNasdaqData(View view) {
        if (!isOnline())
            showDialog(view);
        else {
            String url = "https://www.quandl.com/api/v3/datasets/NASDAQOMX/COMP.json?&start_date=2017-01-15&end_date=2017-01-25?api_key=D_2ozLMLXJJ-rTKs3qm2";

            DownloadNasdaqTask downloadTask = new DownloadNasdaqTask();
            downloadTask.execute(url);
        }
    }

    // TODO: move it to fragment class
    public class DownloadNasdaqTask extends AsyncTask<String, Void, String> {

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
            Log.i("insideMainScreen", "onPostExecuted");
            try {

                JSONObject jsonObject = new JSONObject(result);
                if (set != null) {
                    set.clear();
                    set.notifyDataSetChanged();
                }
                //Log.i("nasdaq", jsonObject.toString());

                JSONObject obj = jsonObject.getJSONObject("dataset");
                //Log.i("date", obj.getJSONObject("newest_available_date").toString());

                String dateString = obj.get("newest_available_date").toString();
                JSONArray array = obj.getJSONArray("data");
                Log.i("nasdaq", array.toString());

                JSONArray columnNames = obj.getJSONArray("column_names");
                Log.i("label", columnNames.toString());
                oneLabel.setText(columnNames.getString(0));
                twoLabel.setText(columnNames.getString(1));
                threeLabel.setText(columnNames.getString(2));
                fourLabel.setText(columnNames.getString(3));
                fiveLabel.setText(columnNames.getString(4));
                sixLabel.setText(columnNames.getString(5));

                String values[] = array.getJSONArray(0).toString().split(",");
                Log.i("values", Arrays.toString(values));
                Double dividend = Double.parseDouble(values[5].substring(0,values[5].length()-1))/1000000000;
                Double marketVal = Double.parseDouble(values[4])/1000000000;
                Log.i("delete", values[0] + "");
                Log.i("delete", values[1] + "");
                oneValue.setText(getDate(dateString));
                twoValue.setText(values[1]);
                threeValue.setText(values[2]);
                fourValue.setText(values[3]);
                fiveValue.setText(getDecimal(marketVal.toString()) + " B.");
                sixValue.setText(getDecimal(dividend.toString()) + " B.");
                yVal.clear();

                for (int i = 0; i < array.length(); i++) {
                    //Log.i("quandl", array[i][4] + "");
                    //JSONObject o = new JSONObject(array[i]);
                    //Log.i("qua", array.getJSONArray(i).get(3).toString());
                    yVal.add((Double) array.getJSONArray(i).get(1) / 100);
                }
               Log.i("yVal in nasdaq", yVal.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }


            for (int i = 0; i < yVal.size(); i++) {
                //float xf = (float) (xVal.get(i) * 1.0f);
                float yf = (float) (yVal.get(i) * 1.0f);
                entries.add(new Entry(i  * 1.0f, yf));

            }

            //LineDataSet set = new LineDataSet(entries, "Legendary legend");
            set = new LineDataSet(entries, "Nasdaq");
            set.notifyDataSetChanged();
            Log.i("linedataset in nasdaq", set.toString());
            set.setColor(getResources().getColor(R.color.colorGreen));
            set.setDrawFilled(true);
            LineData lineData = new LineData(set);
            lineChart.setData(lineData);
            lineChart.notifyDataSetChanged();
            //lineChart.clear();
            lineChart.invalidate();

        }
        private String getDate(String value){
            Date date = null;
            String formattedDate = "";
            try {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(value);
                formattedDate = new SimpleDateFormat("E, MMM d").format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return formattedDate;
        }
        private String getDecimal(String value) {
            BigDecimal bd = new BigDecimal(value);
            bd = bd.setScale(2, RoundingMode.HALF_UP);
            return bd.doubleValue() + "";
        }
    }
}
