package apps.sandesh.stockmarketapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class AnotherActivity extends AppCompatActivity {

    LineChart stockChart;
    LineDataSet stockLineDataset;
    ArrayList<Double> xStockVal = new ArrayList<>();
    ArrayList<Double> yStockVal = new ArrayList<>();
    ArrayList<Entry> stockEntries = new ArrayList<>();
    TextView oneStockValue, twoStockValue, threeStockValue, fourStockValue,
            fiveStockValue, sixStockValue, companyText;



    //autocomplete
    AutoCompleteTextView stockAutoComplete;
    String[] stockSymbols;
    String[] companyNames;
    String companyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);

        stockChart = (LineChart) findViewById(R.id.stock_chart);//$

        stockAutoComplete = (AutoCompleteTextView) findViewById(R.id.stock_auto_complete);
        stockSymbols = getResources().getStringArray(R.array.company_symbol);

        oneStockValue = (TextView) findViewById(R.id.one_stock_value);
        twoStockValue = (TextView) findViewById(R.id.two_stock_value);
        threeStockValue = (TextView) findViewById(R.id.three_stock_value);
        fourStockValue = (TextView) findViewById(R.id.four_stock_value);
        fiveStockValue = (TextView) findViewById(R.id.five_stock_value);
        sixStockValue = (TextView) findViewById(R.id.six_stock_value);
        companyText = (TextView) findViewById(R.id.company_textView);


        ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stockSymbols);
        stockAutoComplete.setAdapter(autoCompleteAdapter);
        stockAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position,
                                    long id) {
                Log.i("your selected item", parent.getItemAtPosition(position).toString());
                String tokens[] = parent.getItemAtPosition(position).toString().split(", ");
                if (tokens != null && tokens.length > 1) {
                    Log.i("symbol", tokens[0]);
                    Log.i("company", tokens[1]);
                    companyName = tokens[1];
                    getStockInfo(tokens[0]);
                }
                else
                    Toast.makeText(getBaseContext(), "Stock information not available at this time.", Toast.LENGTH_SHORT);
            }
        });


        // Make status bar transparent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        Intent intent = getIntent();

        FragmentB f2 = (FragmentB) getFragmentManager().findFragmentById(R.id.fragment2);
        getStockInfo("fb");

    }


    public void getStockInfo(View view) {
        /*
        FragmentB fb = new FragmentB();
        FragmentB.DownloadStockDataTask downloadStockDataTask = fb.new DownloadStockDataTask();
        String urlFirst = "https://www.quandl.com/api/v3/datasets/WIKI/goog/data.json?&exclude_column_names=true&start_date=2017-01-20&end_date=2017-01-25&order=asc&transform=rdif?api_key=D_2ozLMLXJJ-rTKs3qm2";
        String url = "https://www.quandl.com/api/v3/datasets/WIKI/aapl.json?limit=5?column_index=4&api_key=D_2ozLMLXJJ-rTKs3qm2";
        downloadStockDataTask.execute(url);
        */
        String url = "https://www.quandl.com/api/v3/datasets/WIKI/fb.json?limit=30?column_index=4&api_key=D_2ozLMLXJJ-rTKs3qm2";
        DownloadStockDataTaskActivity d = new DownloadStockDataTaskActivity();
        d.execute(url);
    }

    public void getStockInfo(String symbol){
        String url = "https://www.quandl.com/api/v3/datasets/WIKI/" + symbol + ".json?limit=5?column_index=4&api_key=D_2ozLMLXJJ-rTKs3qm2";
        DownloadStockDataTaskActivity d = new DownloadStockDataTaskActivity();
        d.execute(url);
    }


    public class DownloadStockDataTaskActivity extends AsyncTask<String, Void, String> {

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
            try {
                Log.i("hello", "inside activity");
                companyText.setText(companyName);
                if (stockLineDataset != null) {
                    stockLineDataset.clear();
                    stockLineDataset.notifyDataSetChanged();
                }

                Log.i("stock info", result.toString());
                JSONObject jsonObject = new JSONObject(result);
                JSONObject obj = jsonObject.getJSONObject("dataset");
                JSONArray array = obj.getJSONArray("data");
                JSONArray columnLabels = obj.getJSONArray("column_names");
                Log.i("column labels", columnLabels.toString());
                Log.i("data", array.toString());
                String dateString = obj.get("newest_available_date").toString();
                String values[] = array.getJSONArray(0).toString().split(",");
                String previousDayValues[] = array.getJSONArray(1).toString().split(",");
                Double difference =
                        Double.parseDouble(values[4]) - Double.parseDouble(previousDayValues[4]);
                Double percentage = Math.abs(difference) * 100 / Double.parseDouble(values[4]);

                String percentageText = getDecimal(difference.toString()) + "(" +
                                        getDecimal(percentage.toString()) + "%)";
                Log.i("values", Arrays.toString(values));

                oneStockValue.setText(getDate(dateString));
                twoStockValue.setText(values[4]);

                threeStockValue.setText(percentageText);
                if (difference > 0) {
                    threeStockValue.setTextColor(getResources().getColor(R.color.colorGreenArrow));
                }
                else
                    threeStockValue.setTextColor(getResources().getColor(R.color.colorRedArrow));


                fourStockValue.setText(values[1]);
                fiveStockValue.setText(values[2]);
                sixStockValue.setText(values[5]);

                yStockVal.clear();
                for (int i = 0; i < array.length(); i++) {
                    //Log.i("quandl", array[i][4] + "");
                    //JSONObject o = new JSONObject(array[i]);
                    //Log.i("closing", array.getJSONArray(i).get(4).toString());
                    yStockVal.add((Double) array.getJSONArray(i).get(4));
                }
                Log.i("closing", yStockVal.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < yStockVal.size(); i++) {
                //float xf = (float) (xVal.get(i) * 1.0f);
                float yf = (float) (yStockVal.get(i) * 1.0f);
                stockEntries.add(new Entry(i  * 1.0f, yf));

            }
            Log.i("stockEntries", stockEntries.toString());
            //LineDataSet stockLineDataset = new LineDataSet(stockEntries, "Stock Information");
            stockLineDataset = new LineDataSet(stockEntries, companyName);
            stockLineDataset.notifyDataSetChanged();
            Log.i("linedataset in stock", stockLineDataset.toString());
            stockLineDataset.setColor(getResources().getColor(R.color.colorGreen));
            stockLineDataset.setDrawFilled(true);
            LineData stockLineData = new LineData(stockLineDataset);
            if (stockLineData == null)
                Log.i("NULL", "stockLineData is null");
            stockChart.setData(stockLineData);
            stockChart.notifyDataSetChanged();
            //lineChart.clear();
            stockChart.invalidate();


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
}
