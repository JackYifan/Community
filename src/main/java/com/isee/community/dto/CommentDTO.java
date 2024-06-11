package com.isee.community.dto;

import com.isee.community.model.User;
import lombok.Data;

import java.util.List;


@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private Integer commentCount;
    private String content;
    private User user;
    private Boolean isLike;
    List<CommentDTO> children;
}
