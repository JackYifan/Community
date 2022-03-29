package com.isee.community.enums;



public enum NotificationTypeEnum {
    REPLY_QUESTION(1,"回复了问题"),
    REPLY_COMMENT(2,"回复了评论"),
    THUMB_COMMENT(3, "赞了评论");

    private int type;
    private String name ;

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }
    NotificationTypeEnum(int type, String name){
        this.type = type;
        this.name = name;
    }

    /**
     *
     * @param type
     * @return 根据type返回通知的名字
     */
    public static String nameOfType(int type){
        //遍历枚举的所有value
        for(NotificationTypeEnum notificationTypeEnum:NotificationTypeEnum.values()){
            if(notificationTypeEnum.getType()==type){
                return notificationTypeEnum.getName();
            }
        }
        return "";
    }
}
