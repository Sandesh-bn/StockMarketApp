package apps.sandesh.stockmarketapp;

/**
 * Created by Sandesh on 1/18/2017.
 */

public class HourlyWeather {
    private String day, time,  temperature, condition;
    int imageId;

    public HourlyWeather(){

    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getImage() {
        return imageId;
    }

    public void setImage(int image) {
        this.imageId = image;

    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}

