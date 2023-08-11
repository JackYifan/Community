package com.isee.community.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.isee.community.dto.FileDTO;
import com.isee.community.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author Yifan Wu
 * Date on 2022/4/15  22:49
 */



@Service
public class FileServiceImpl implements FileService {
    @Value("${aliyun.oss.file.endpoint}")
    String endpoint ;
    @Value("${aliyun.oss.file.keyid}")
    String accessKeyId ;
    @Value("${aliyun.oss.file.keysecret}")
    String accessKeySecret ;
    @Value("${aliyun.oss.file.bucketname}")
    String bucketName ;

    @Override
    public FileDTO upload(MultipartFile file){
        String fileName=file.getOriginalFilename();
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            ossClient.putObject(bucketName, fileName, file.getInputStream());
            ossClient.shutdown();
            FileDTO fileDTO = new FileDTO();
            fileDTO.setSuccess(1);
            String utl = "https://"+bucketName+"."+endpoint+"/"+fileName;
            fileDTO.setUrl(utl);
            return fileDTO;
        } catch (IOException e) {
            FileDTO fileDTO = new FileDTO();
            fileDTO.setSuccess(0);
            fileDTO.setMessage("上传失败");
            return fileDTO;
        }



    }
}
