package com.isee.community.model;

import lombok.Data;

@Data
public class Question {
    private Long id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private String tag;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;

}
