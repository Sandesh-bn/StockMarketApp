package apps.sandesh.stockmarketapp;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sandesh on 1/18/2017.
 */

public class HourlyWeatherAdapter extends ArrayAdapter<HourlyWeather> {
    ArrayList<HourlyWeather> hourlyWeatherItems;
    int resource;
    Context context;
    LayoutInflater vi;
    ViewHolder viewHolder;
    Typeface customFont;
    public HourlyWeatherAdapter(Context context, int resource, ArrayList<HourlyWeather> objects) {
        super(context, resource, objects);
        hourlyWeatherItems = objects;
        this.resource = resource;
        this.context = context;
        this.customFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/oxygen.ttf");
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = vi.inflate(resource, null );
            viewHolder = new HourlyWeatherAdapter.ViewHolder();
            viewHolder.hourlyImage= (ImageView) convertView.findViewById(R.id.today_hourly_imageView);
            viewHolder.hourlyTemperature = (TextView)convertView.findViewById(R.id.today_hourly_temperature);
            viewHolder.hourlyDay = (TextView)convertView.findViewById(R.id.today_hourly_day);
            viewHolder.hourlyTime = (TextView)convertView.findViewById(R.id.today_hourly_time);
            viewHolder.hourlyCondition = (TextView)convertView.findViewById(R.id.today_hourly_condition);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.hourlyTemperature.setText(hourlyWeatherItems.get(position).getTemperature());
        viewHolder.hourlyDay.setText(hourlyWeatherItems.get(position).getDay());
        viewHolder.hourlyTime.setText(hourlyWeatherItems.get(position).getTime());
        viewHolder.hourlyCondition.setText(hourlyWeatherItems.get(position).getCondition());
        viewHolder.hourlyImage.setImageResource(hourlyWeatherItems.get(position).getImage());
        viewHolder.hourlyCondition.setTypeface(customFont);
        viewHolder.hourlyTemperature.setTypeface(customFont);
        viewHolder.hourlyDay.setTypeface(customFont);
        viewHolder.hourlyTime.setTypeface(customFont);


        return convertView;
    }



    static class ViewHolder {

        public TextView hourlyCondition;
        public TextView hourlyTime;
        public TextView hourlyDay;
        public ImageView hourlyImage;
        public TextView hourlyTemperature;
    }


}

