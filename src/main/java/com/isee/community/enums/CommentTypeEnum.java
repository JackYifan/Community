package com.isee.community.enums;

public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);

    private Integer type;
    CommentTypeEnum(Integer type){
        this.type = type;
    }

    //注意枚举类的遍历方法
    public static boolean isExist(Integer type) {
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {
            if(commentTypeEnum.getType()== type){
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }
}
