package com.isee.community.service;

import com.isee.community.dto.NotificationDTO;
import com.isee.community.dto.PaginationDTO;
import com.isee.community.enums.NotificationStatusEnum;
import com.isee.community.enums.NotificationTypeEnum;
import com.isee.community.exception.CustomizeErrorCode;
import com.isee.community.exception.CustomizeException;
import com.isee.community.mapper.NotificationMapper;
import com.isee.community.model.Notification;
import com.isee.community.model.NotificationExample;
import com.isee.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    /**
     * 根据id分页查询
     * @param userId
     * @param page
     * @param size
     * @return
     */
    public PaginationDTO<NotificationDTO> list(Long userId, Integer page, Integer size) {
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
        //根据userId 查询
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId);
        notificationExample.setOrderByClause("gms_create desc");
        Integer totalCount = (int)notificationMapper.countByExample(notificationExample);
        Integer totalPage;//总页数
        totalPage = totalCount/size+(totalCount%size==0?0:1);

        //限制page范围
        if(page>totalPage) page = totalPage;
        if(page<0) page = 1;
        paginationDTO.setPagination(totalPage,page,size);

        //计算offset并查询出分页数据
        Integer offset = size*(page-1);
        NotificationExample example = new NotificationExample();
        example.createCriteria()
                .andReceiverEqualTo(userId);
        example.setOrderByClause("gmt_create desc");
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));//分页查询

        //如果没有数据直接返回
        if(notifications.size()==0){
            return paginationDTO;
        }
        //有数据需要进行分装
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        paginationDTO.setData(notificationDTOS);

        for(Notification notification:notifications){
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }
        paginationDTO.setData(notificationDTOS);
        return paginationDTO;
    }

    public Long unreadCount(Long userId) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());

        return notificationMapper.countByExample(notificationExample);
    }

    public NotificationDTO read(Long id, User user) {
        NotificationExample example = new NotificationExample();
        example.createCriteria().andIdEqualTo(id);
        Notification notification = notificationMapper.selectByExample(example).get(0);
        //验证
        if(notification==null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if(!notification.getReceiver().equals(user.getId())){
            //如果该通知的接收者不是当前用户抛异常
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }

        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateStatus(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;

    }
}
