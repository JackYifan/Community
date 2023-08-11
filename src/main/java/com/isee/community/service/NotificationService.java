package com.isee.community.service;

import com.isee.community.dto.NotificationDTO;
import com.isee.community.dto.PaginationDTO;
import com.isee.community.model.User;

public interface NotificationService {
    PaginationDTO<NotificationDTO> list(Long userId, Integer page, Integer size);

    Long unreadCount(Long userId);

    NotificationDTO read(Long id, User user);
}
