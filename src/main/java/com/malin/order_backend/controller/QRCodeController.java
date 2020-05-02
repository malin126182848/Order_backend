package com.malin.order_backend.controller;


import com.malin.order_backend.utils.QRCodeUtil;
import com.malin.order_backend.utils.ResultVOUtil;
import com.malin.order_backend.viewobj.ResultVO;
import com.malin.order_backend.service.FileService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Api(tags = "二维码相关接口")
@RequestMapping("/seller/QRcode")
public class QRCodeController {

    @Autowired
    private FileService fileService;

    @PostMapping("/create")
    @ApiOperation("生成座位二维码")
    @ApiImplicitParam(name = "address", value = "座位号")
    public ResultVO create(@RequestParam("address") String str) throws Exception {
        String result = QRCodeUtil.encode(str,"","./Image/QRCodePath",str,false);
        return ResultVOUtil.success(result);
    }

    @PostMapping("/createLogo")
    @ApiOperation("生成带logo座位二维码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "address", value = "座位号"),
            @ApiImplicitParam(name = "file", value = "Logo文件")
    })
    public ResultVO createLogo(@RequestParam("address") String str, @RequestParam("file") MultipartFile logofile)
            throws Exception {
        String fileName = fileService.storeFile(logofile);
        String result = QRCodeUtil.encode(str,"./image/"+fileName,"./Image/QRCodePath",str,true);
        return ResultVOUtil.success(result);
    }
}
