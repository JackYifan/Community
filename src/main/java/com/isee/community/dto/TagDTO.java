package com.isee.community.dto;

import lombok.Data;

import java.util.List;

@Data
public class TagDTO {
    //tag类别
    private String categoryName;
    //该类别下所有的tag
    private List<String> tags;
}
