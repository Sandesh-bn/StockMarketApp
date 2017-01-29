package apps.sandesh.stockmarketapp;


import android.app.Fragment;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//import android.support.v4.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentB extends Fragment {

    //$$
    /*
    LineChart stockChart;
    LineDataSet stockLineDataset;
    ArrayList<Double> xStockVal = new ArrayList<>();
    ArrayList<Double> yStockVal = new ArrayList<>();
    ArrayList<Entry> stockEntries = new ArrayList<>();
    */
    public FragmentB() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_b, container, false);

        //stockChart = (LineChart) view.findViewById(R.id.stock_chart);

        //intializeFont(multiDayLocation, multiDayTimezone, conditionTodayText, locationTodayText,
         //       latitudeText, longitudeText, timeZoneOffsetText, windSpeedText, pressureText, humidityText
         //       , conditionMultiDay);


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

    public class DownloadStockDataTask extends AsyncTask<String, Void, String> {

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
                /*
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
                Log.i("latest", dateString);
                String values[] = array.getJSONArray(0).toString().split(",");
                Log.i("values", Arrays.toString(values));

                yStockVal.clear();
                for (int i = 0; i < array.length(); i++) {
                    //Log.i("quandl", array[i][4] + "");
                    //JSONObject o = new JSONObject(array[i]);
                    //Log.i("closing", array.getJSONArray(i).get(4).toString());
                    yStockVal.add((Double) array.getJSONArray(i).get(4));
                }
                Log.i("closing", yStockVal.toString());
                /*
                //Log.i("nasdaq", jsonObject.toString());

                JSONObject obj = jsonObject.getJSONObject("dataset");
                //Log.i("date", obj.getJSONObject("newest_available_date").toString());

                String dateString = obj.get("newest_available_date").toString();
                JSONArray array = obj.getJSONArray("data");
                Log.i("nasdaq", array.toString());



                Log.i("delete", values[0] + "");
                Log.i("delete", values[1] + "");

                yVal.clear();

                for (int i = 0; i < array.length(); i++) {
                    //Log.i("quandl", array[i][4] + "");
                    //JSONObject o = new JSONObject(array[i]);
                    //Log.i("qua", array.getJSONArray(i).get(3).toString());
                    yVal.add((Double) array.getJSONArray(i).get(1) / 100);
                }
                Log.i("yVal in nasdaq", yVal.toString());
                */

            } catch (Exception e) {
                e.printStackTrace();
            }

            /*
            for (int i = 0; i < yStockVal.size(); i++) {
                //float xf = (float) (xVal.get(i) * 1.0f);
                float yf = (float) (yStockVal.get(i) * 1.0f);
                stockEntries.add(new Entry(i  * 1.0f, yf));

            }
            Log.i("stockEntries", stockEntries.toString());
            //LineDataSet stockLineDataset = new LineDataSet(stockEntries, "Stock Information");
            stockLineDataset = new LineDataSet(stockEntries, "stock information");
            stockLineDataset.notifyDataSetChanged();
            Log.i("linedataset in stock", stockLineDataset.toString());
           // stockLineDataset.setColor(getResources().getColor(R.color.colorGreen));
            //stockLineDataset.setDrawFilled(true);
            LineData stockLineData = new LineData(stockLineDataset);
            if (stockLineData == null)
                Log.i("NULL", "stockLineData is null");
            stockChart.setData(stockLineData);
            stockChart.notifyDataSetChanged();
            //lineChart.clear();
            stockChart.invalidate();
            */
        }

    }








}
