package com.isee.community.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String accountId;
    private String name;
    private String bio;
    private String avatarUrl;
}
