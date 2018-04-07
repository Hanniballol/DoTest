package com.msymobile.dotest.entity;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * autour: hannibal
 * date: 2018/4/6
 * e-mail:404769122@qq.com
 * description: 汇率实体类
 */
public class FixerEntity {

    /**
     * base : USD
     * date : 2018-03-29
     * rates : {"AUD":1.3015,"BGN":1.5874,"BRL":3.3226,"CAD":1.2901,"CHF":0.95601,"CNY":6.2875,"CZK":20.636,"DKK":6.049,"EUR":0.81162,"GBP":0.71009,"HKD":7.8481,"HRK":6.0322,"HUF":253.33,"IDR":13744,"ILS":3.5112,"INR":65.17,"ISK":98.612,"JPY":106.44,"KRW":1064,"MXN":18.282,"MYR":3.868,"NOK":7.8541,"NZD":1.3877,"PHP":52.247,"PLN":3.4174,"RON":3.7793,"RUB":57.536,"SEK":8.3467,"SGD":1.3114,"THB":31.23,"TRY":3.975,"ZAR":11.867}
     */

    @SerializedName("base")
    private String base;
    @SerializedName("date")
    private String date;
    @SerializedName("rates")
    private Object rates;

    private List<ItemInfo> rateList = new ArrayList<>();

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public List<ItemInfo> getRateList() {
        if (rateList.size() > 0) return rateList;
        try {
            JSONObject jsonObject = new JSONObject(rates.toString());
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String next = keys.next();
                rateList.add(new ItemInfo(next, jsonObject.optDouble(next)));
            }
            return rateList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rateList;
    }

    public void setRateList(List<ItemInfo> rateList) {
        this.rateList = rateList;
    }

    public static class ItemInfo {
        private String fixerName;
        private double rate;

        public ItemInfo(String fixerName, double rate) {
            this.fixerName = fixerName;
            this.rate = rate;
        }

        public String getFixerName() {
            return fixerName;
        }

        public void setFixerName(String fixerName) {
            this.fixerName = fixerName;
        }

        public double getRate() {
            return rate;
        }

        public void setRate(double rate) {
            this.rate = rate;
        }
    }
}
