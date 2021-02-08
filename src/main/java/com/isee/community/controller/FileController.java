package com.isee.community.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.isee.community.dto.FileDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;

@Controller
public class FileController {

    @Value("${aliyun.oss.file.endpoint}")
    String endpoint ;
    @Value("${aliyun.oss.file.keyid}")
    String accessKeyId ;
    @Value("${aliyun.oss.file.keysecret}")
    String accessKeySecret ;
    @Value("${aliyun.oss.file.bucketname}")
    String bucketName ;


    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(MultipartHttpServletRequest request){
        MultipartFile file = request.getFile("editormd-image-file");
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
