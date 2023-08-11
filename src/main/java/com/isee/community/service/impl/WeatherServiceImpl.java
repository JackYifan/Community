package com.isee.community.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.isee.community.model.Weather;
import com.isee.community.vo.WeatherVO;
import okhttp3.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @Author Yifan Wu
 * Date on 2022/3/20  22:22
 */
@Service
public class WeatherServiceImpl {
    //http://47.103.125.27:8081/weather
    public WeatherVO getWeather(){
        WeatherVO weatherVO = new WeatherVO();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://47.103.125.27:8081/weather")
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            JSONObject body = JSONObject.parseObject(responseBody);
            JSONObject data = body.getJSONObject("data");
            Weather weather = data.toJavaObject(Weather.class);
            BeanUtils.copyProperties(weather,weatherVO);
            JSONArray jsonArray = weather.getFutureWeather();
            ArrayList<Weather> weatherList = new ArrayList<>();
            for(int i=0;i<2;i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Weather fweather = new Weather();
                fweather.setImgIdx(jsonObject.getInteger("imgIdx"));
                fweather.setWeather(jsonObject.getString("weather"));
                fweather.setWeekIdx(jsonObject.getString("weekIdx"));
                weatherList.add(fweather);
            }
            weatherVO.setFutureWeather(weatherList);

//            List<Weather> weathers = futureWeather.toJavaList(Weather.class);
//            Integer imgIdx = data.getInteger("imgIdx");
//            String weekIdx = data.getString("weekIdx");
//            String weather = data.getString("weather");
//            System.out.println(weather);
            System.out.println(weatherList);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return weatherVO;

    }

    // @Test
    // public void test(){
    //     getWeather();
    // }
}
