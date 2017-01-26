package apps.sandesh.stockmarketapp;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static apps.sandesh.stockmarketapp.R.id.cityInput;

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
        todayButton = (TextView) findViewById(R.id.today_button);
        weeklyButton = (TextView) findViewById(R.id.multi_day_button);
        cityUserInput = (EditText) findViewById(cityInput);
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

        locationText.setText(city + ", " + country);
        int toColor = R.drawable.clear_day_gradient;

        if (condition.equals("clear-day")) {
            mainScreenImage.setImageResource(R.drawable.clear_day);
            toColor = R.drawable.clear_day_gradient;
        }
        else if (condition.equals("cloudy")) {
            mainScreenImage.setImageResource(R.drawable.cloudy);
            toColor = R.drawable.cloud_partly_cloudy_gradient;
        }
        else if (condition.equals("rain")) {
            mainScreenImage.setImageResource(R.drawable.rain);
            toColor = R.drawable.rain_gradient;
        }
        else if (condition.equals("sleet")) {
            mainScreenImage.setImageResource(R.drawable.sleet);
            toColor = R.drawable.sleet_snow_gradient;
        }
        else if (condition.equals("snow")) {
            mainScreenImage.setImageResource(R.drawable.snow);
            toColor = R.drawable.sleet_snow_gradient;
        }
        else if (condition.equals("fog")) {
            mainScreenImage.setImageResource(R.drawable.fog);
            toColor = R.drawable.wind_fog_gradient;
        }
        else if (condition.equals("wind")) {
            mainScreenImage.setImageResource(R.drawable.wind);
            toColor = R.drawable.wind_fog_gradient;
        }
        else if (condition.equals("clear-night")) {
            mainScreenImage.setImageResource(R.drawable.clear_night);
            toColor = R.drawable.clear_night_gradient;
        }
        else if (condition.equals("partly-cloudy-day")) {
            mainScreenImage.setImageResource(R.drawable.partly_cloudy_day);
            toColor = R.drawable.cloud_partly_cloudy_gradient;
        }
        else {
            mainScreenImage.setImageResource(R.drawable.rain);
        }
        //animateBackGround(mainPortraitLayout.getBackground(), getApplicationContext().getResources().getDrawable(toColor) );
        //animateBackGround();
        mainPortraitLayout.setBackground(getResources().getDrawable(toColor));

        /*
        ObjectAnimator animator = ObjectAnimator.ofInt(mainPortraitLayout, "background", R.drawable.light_blue_gradient, R.drawable.wind_fog_gradient).setDuration(3000);
        animator.setEvaluator(new ArgbEvaluator());
        animator.start();
        */

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
            f2.updateView(message);
        }
        else {
            Intent intent = new Intent(this, AnotherActivity.class);
            intent.putExtra("message", message);
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
            f2.showTodayInfo(todayInfo);
        }
        else {
            Intent intent = new Intent(this, AnotherActivity.class);
            intent.putStringArrayListExtra("todayInfo", todayInfo);
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
            f2.showMultiDayInfo(multiDayInfo);
        }
        else {
            Intent intent = new Intent(this, AnotherActivity.class);
            intent.putStringArrayListExtra("multiDayInfo", multiDayInfo);
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

    public void showMap(View view) {
        if (!isOnline()){
            showDialog(view);
        }
        else {
            Intent intent = null, chooser = null;
            String location = cityUserInput.getText().toString();
            if (location == null) location = "San Francisco";
            ArrayList<String> coordinates = getLatitudeAndLongitudeFromGoogleMapForAddress(location);
            if (coordinates.size() == 0) {
                Toast.makeText(this, "Using default city: San Francisco", Toast.LENGTH_SHORT).show();
                coordinates.add("37.7749295");//latitude for san francisco
                coordinates.add("-122.419415");//longitude for san francisco
            }
            String latitude = coordinates.get(0), longitude = coordinates.get(1);
            intent = new Intent(Intent.ACTION_VIEW);

            String locationUri = "geo:" + latitude + ", " + longitude +
                    "?q=" + latitude + ", " + longitude;// + "(" + location + ")";
            intent.setData(Uri.parse(locationUri));
            chooser = Intent.createChooser(intent, "Hey there choose one");
            startActivity(chooser);
        }

    }
}
