package com.malin.order_backend.controller;


import com.malin.order_backend.utils.JsonUtil;
import com.malin.order_backend.utils.QRCodeUtil;
import com.malin.order_backend.utils.ResultVOUtil;
import com.malin.order_backend.viewobj.ProductVO;
import com.malin.order_backend.viewobj.ResultVO;
import com.malin.order_backend.service.FileService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.spring.web.json.Json;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(tags = "二维码相关接口")
@RequestMapping("/seller/QRcode")
public class QRCodeController {

    @GetMapping("/list")
    @ApiOperation("二维码列表的接口")
    public ResultVO<ArrayList<Map<String, String>>> list() throws Exception {

        ArrayList<Map<String, String>> files = new ArrayList<Map<String, String>>();
        try{
            File file = new File("Image/QRCodePath");
            File[] tempList = file.listFiles();
            for (File value : tempList) {
                if (value.isFile()) {
                    String fileName = value.getName();
                    String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
                    if (suffix.equals("jpg"))
                    {
                        String json = QRCodeUtil.decode(value);
                        Map<String, String> tMap = JsonUtil.toMap(json);
                        tMap.put("fileName", value.getName());
                        files.add(tMap);
                    }

                }
            }
        }catch (Exception e){
            return ResultVOUtil.error(133,e.getMessage());
        }
        return ResultVOUtil.success(files);
    }
        @PostMapping("/create")
    @ApiOperation("生成座位二维码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "address", value = "座位号"),
            @ApiImplicitParam(name = "name", value = "店铺名")
    })
    public ResultVO create(@RequestParam("address") String address,
                           @RequestParam("name") String name) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("address", address);
        map.put("name", name);

        String str = JsonUtil.toJson(map);
        String result = QRCodeUtil.encode(str,"","./Image/QRCodePath",address,false);

        map.put("fileName", result);
        return ResultVOUtil.success(map);
    }

    @PostMapping("/createLogo")
    @ApiOperation("生成带logo座位二维码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "address", value = "座位号"),
            @ApiImplicitParam(name = "file", value = "Logo文件"),
            @ApiImplicitParam(name = "name", value = "店铺名")
    })
    public ResultVO createLogo(@RequestParam("address") String address,
                               @RequestParam("name") String name,
                               @RequestParam("fileName") String logofile)
            throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("address", address);
        map.put("name", name);
        String str = JsonUtil.toJson(map);
        String result = QRCodeUtil.encode(str,"./image/"+logofile,"./Image/QRCodePath",address,true);
        map.put("fileName", result);
        return ResultVOUtil.success(map);
    }
}
