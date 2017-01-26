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

public class MultiDayWeatherAdapter extends ArrayAdapter<MultiDayWeather> {
    ArrayList<MultiDayWeather> multiDayWeatherItems;
    int resource;
    Context context;
    LayoutInflater vi;
    ViewHolder viewHolder;
    Typeface customFont;
    public MultiDayWeatherAdapter(Context context, int resource, ArrayList<MultiDayWeather> objects) {
        super(context, resource, objects);
        multiDayWeatherItems = objects;
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
            viewHolder = new MultiDayWeatherAdapter.ViewHolder();
            viewHolder.multiDayImage = (ImageView) convertView.findViewById(R.id.multiday_imageView);
            viewHolder.multiDayTemperature = (TextView)convertView.findViewById(R.id.temperature_multiday_textView);
            viewHolder.multiDayDay = (TextView)convertView.findViewById(R.id.day_multiday_textView);
            viewHolder.multiDayDate = (TextView)convertView.findViewById(R.id.date_multiday_textView);
            viewHolder.multiDayCondition = (TextView)convertView.findViewById(R.id.condition_multiday_textView);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.multiDayTemperature.setText(multiDayWeatherItems.get(position).getMultiDayTemperature());
        viewHolder.multiDayDay.setText(multiDayWeatherItems.get(position).getMultidayDay());
        viewHolder.multiDayDate.setText(multiDayWeatherItems.get(position).getMultidayDate());
        viewHolder.multiDayCondition.setText(multiDayWeatherItems.get(position).getMultiDayCondition());
        viewHolder.multiDayImage.setImageResource(multiDayWeatherItems.get(position).getMultiDayImageId());
        viewHolder.multiDayTemperature.setTypeface(customFont, Typeface.BOLD);
        viewHolder.multiDayDay.setTypeface(customFont);
        viewHolder.multiDayDate.setTypeface(customFont, Typeface.BOLD);
        viewHolder.multiDayCondition.setTypeface(customFont);
        return convertView;
    }



    static class ViewHolder {

        public TextView multiDayCondition;
        public TextView multiDayDate;
        public TextView multiDayDay;
        public ImageView multiDayImage;
        public TextView multiDayTemperature;
    }


}

