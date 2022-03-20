package com.isee.community.model;

import com.alibaba.fastjson.JSONArray;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Yifan Wu
 * Date on 2022/3/20  15:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Weather {
    String weekIdx;
    String weather;
    Integer imgIdx;
    JSONArray futureWeather;
}
