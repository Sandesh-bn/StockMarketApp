package apps.sandesh.stockmarketapp;

/**
 * Created by Sandesh on 1/18/2017.
 */

public class MultiDayWeather {
    private String multidayDay, multidayDate,  multiDayTemperature, multiDayCondition;
    int multiDayImageId;

    public MultiDayWeather(){

    }

    public String getMultidayDay() {
        return multidayDay;
    }

    public void setMultidayDay(String multidayDay) {
        this.multidayDay = multidayDay;
    }

    public String getMultidayDate() {
        return multidayDate;
    }

    public void setMultidayDate(String multidayDate) {
        this.multidayDate = multidayDate;
    }

    public String getMultiDayTemperature() {
        return multiDayTemperature;
    }

    public void setMultiDayTemperature(String multiDayTemperature) {
        this.multiDayTemperature = multiDayTemperature;
    }

    public String getMultiDayCondition() {
        return multiDayCondition;
    }

    public void setMultiDayCondition(String multiDayCondition) {
        this.multiDayCondition = multiDayCondition;
    }

    public int getMultiDayImageId() {
        return multiDayImageId;
    }

    public void setMultiDayImageId(int multiDayImageId) {
        this.multiDayImageId = multiDayImageId;
    }
}
