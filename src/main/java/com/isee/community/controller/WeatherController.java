package com.isee.community.controller;

import com.isee.community.model.Weather;
import com.isee.community.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author Yifan Wu
 * Date on 2022/3/20  22:42
 */
public class WeatherController {

    @Autowired
    private WeatherService weatherService;


}
