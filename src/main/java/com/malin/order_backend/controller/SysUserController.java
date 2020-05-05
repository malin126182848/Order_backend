package com.malin.order_backend.controller;

import com.malin.order_backend.constant.CookieConstant;
import com.malin.order_backend.dataobj.SellerInfo;
import com.malin.order_backend.enums.ResultEnum;
import com.malin.order_backend.service.SellerService;
import com.malin.order_backend.utils.CookieUtil;
import com.malin.order_backend.utils.JwtUtil;
import com.malin.order_backend.utils.ResultVOUtil;
import com.malin.order_backend.viewobj.ResultVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(tags = "用户相关接口")
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private SellerService sellerService;

    @PostMapping(value = "/login")
    public Map<String, Object> login(@RequestParam("username") String username,
                                        @RequestParam("password") String password){
        Map<String, Object> map = new HashMap<>();
        // 省略 账号密码验证
        String token = null;
        SellerInfo user = sellerService.findSellerInfoByUsername(username);
        boolean b = password.equals(user.getPassword());
        if (user != null && b)
            token = JwtUtil.sign(username,password);
        // 验证成功后发送token
        if (token != null){
            map.put("code", "200");
            map.put("message","认证成功");
            map.put("userType",user.getOpenid());
            map.put("token", token);
        } else {
            map.put("code", "403");
            map.put("message","认证失败");
        }
        return map;
    }

    @GetMapping("/logout")
    public ResultVO logout(HttpServletRequest request,
                           HttpServletResponse response,
                           Map<String, Object> map) {
        //1. 从cookie里查询
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie != null) {
            //2. 清除redis
//            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));

            //3. 清除cookie
            CookieUtil.set(response, CookieConstant.TOKEN, null, 0);
        }

        map.put("msg", ResultEnum.LOGOUT_SUCCESS.getMessage());
        return ResultVOUtil.success(map);
    }
}
