package apps.sandesh.stockmarketapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;

public class AnotherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);

        // Make status bar transparent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        Intent intent = getIntent();
        FragmentB f2 = (FragmentB) getFragmentManager().findFragmentById(R.id.fragment2);
        ArrayList<String> todayInfo = new ArrayList<>();
        ArrayList<String> multiDayInfo  = new ArrayList<>();
        /*
        //int index = intent.getIntExtra("index", 0);
        ArrayList<String> indexAndWeather = intent.getStringArrayListExtra("index");
        FragmentB f2 = (FragmentB) getFragmentManager().findFragmentById(R.id.fragment2);
        Log.i("indexAndWeatherInfo", indexAndWeather.toString());
        if (f2 != null) {
            int index = Integer.parseInt(indexAndWeather.get(0));
            ArrayList<String> infoList = new ArrayList<>();
            for (int i = 1; i < indexAndWeather.size(); i++)
                infoList.add(indexAndWeather.get(i));
            f2.changeData(index, infoList);
        }
        */

        // Dummmy
        //String message = intent.getStringExtra("message");
        //if (f2 != null){
            //f2.updateView(message);
        //}

        //ArrayList<String> todayInfo = intent.getStringArrayListExtra("todayInfo");
        todayInfo = intent.getStringArrayListExtra("todayInfo");
        if (f2 != null && todayInfo != null){
            f2.showTodayInfo(todayInfo);
        }

        //ArrayList<String> multiDayInfo = intent.getStringArrayListExtra("multiDayInfo");
        multiDayInfo = intent.getStringArrayListExtra("multiDayInfo");
        //Log.i("error", multiDayInfo.toString());
        if (f2 != null && multiDayInfo != null){
            f2.showMultiDayInfo(multiDayInfo);
        }
    }


    public void displayToast2(View view) {
        String message = "";
        int x= view.getLeft(), y = view.getBottom() - view.getHeight();
        if (view.getId() == R.id.latitude_layout)
            message = "Latitude";
        else if (view.getId() == R.id.longitude_layout)
            message = "Longitude";

        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        //toast.setGravity(Gravity.TOP|Gravity.LEFT, x, y);
        toast.setGravity(Gravity.TOP, view.getLeft() - view.getWidth()/2 - toast.getView().getWidth()/2, view.getBottom());
        toast.show();

    }

    public  void displayToast(View view){
        String message = "";
        int x= view.getLeft(), y = view.getBottom() - view.getHeight();
        if (view.getId() == R.id.latitude_layout)
            message = "Lat";
        else if (view.getId() == R.id.longitude_layout)
            message = "Longitude";
        Toast toast = Toast.makeText(view.getContext(),
                message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.LEFT | Gravity.BOTTOM, view.getLeft(),
                view.getBottom()/2 );
        toast.show();
    }
}
