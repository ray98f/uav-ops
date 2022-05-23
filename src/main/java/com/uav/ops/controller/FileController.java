package com.uav.ops.controller;

import com.uav.ops.config.MinioConfig;
import com.uav.ops.dto.DataResponse;
import com.uav.ops.entity.File;
import com.uav.ops.enums.ErrorCode;
import com.uav.ops.exception.CommonException;
import com.uav.ops.utils.*;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/file")
@Api(tags = "文件接口")
@Validated
public class FileController {

    @Autowired
    private MinioConfig minioConfig;

    @Autowired
    private MinioClient client;

    @Autowired
    private MinioUtils minioUtils;

    @PostMapping("/upload")
    @ApiOperation(value = "文件上传")
    public DataResponse<Map<String, Object>> fileUpload(@RequestParam MultipartFile file, String bizCode) throws Exception {
        String fileUrl = uploadFile(file, bizCode);
        if (Objects.isNull(fileUrl)) {
            throw new CommonException(ErrorCode.FILE_UPLOAD_FAIL);
        }
        return DataResponse.of(new HashMap<String, Object>(1) {{
            put("url", fileUrl);
        }});
    }

    public String uploadFile(MultipartFile file, String bizCode) throws Exception {
        if (!minioUtils.bucketExists(bizCode)) {
            minioUtils.makeBucket(bizCode);
        }
        String oldName = file.getOriginalFilename();
        String fileName = FileUploadUtils.extractFilename(file);
        PutObjectArgs args = PutObjectArgs.builder()
                .bucket(bizCode)
                .object(fileName)
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType())
                .build();
        client.putObject(args);
        return minioConfig.getUrl() + "/" + bizCode + "/" + fileName;
    }
}