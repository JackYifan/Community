package com.isee.community.vo;

import com.alibaba.fastjson.JSONArray;
import com.isee.community.model.Weather;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author Yifan Wu
 * Date on 2022/3/20  23:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherVO {
    String weekIdx;
    String weather;
    Integer imgIdx;
    List<Weather> futureWeather;
}
