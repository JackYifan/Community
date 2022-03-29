package com.isee.community.controller;

import com.isee.community.dto.NotificationDTO;
import com.isee.community.enums.NotificationTypeEnum;
import com.isee.community.model.Notification;
import com.isee.community.model.User;
import com.isee.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    /**
     * 点击连接阅读评论
     * @param request
     * @param id
     * @return
     */
    @GetMapping("/notification/{id}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "id")Long id){
        //检查是否登录
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/";
        }
        NotificationDTO notificationDTO = notificationService.read(id,user);
        if(NotificationTypeEnum.REPLY_COMMENT.getType()==notificationDTO.getType()
            ||NotificationTypeEnum.REPLY_QUESTION.getType()==notificationDTO.getType()
            ||NotificationTypeEnum.THUMB_COMMENT.getType()==notificationDTO.getType()){
            return "redirect:/question/"+notificationDTO.getOuterid();
        }else{
            return "redirect:/";
        }


    }



}
