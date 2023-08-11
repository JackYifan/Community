package com.isee.community.service;

import com.isee.community.dto.FileDTO;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    FileDTO upload(MultipartFile file);
}
