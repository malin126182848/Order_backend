package com.malin.order_backend.controller;

import com.malin.order_backend.viewobj.ResultVO;
//import com.malin.order_backend.config.UpYunConfig;
import com.malin.order_backend.utils.ResultVOUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RestController
@Api(tags = "文件上传下载相关接口")
@RequestMapping("/image")
public class ImageController {

    @PostMapping("/upload")
    //TODO 上传图片文件
    public ResultVO upload(@RequestParam("file_data") MultipartFile multipartFile) throws IOException{
/*        UpYun upyun = new UpYun(upYunConfig.getBucketName(), upYunConfig.getUsername(), upYunConfig.getPassword());
        String fileName = String.format("%s.%s", UUID.randomUUID().toString(), multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1));
        upyun.writeFile(fileName, multipartFile.getInputStream(), true, new HashMap<>());

        Map map = new HashMap<>();
        map.put("fileName", fileName);
        return ResultVOUtil.success(map);*/
        return null;
    }

    //TODO 文件下载
}
