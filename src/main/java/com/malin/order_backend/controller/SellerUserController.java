package com.malin.order_backend.controller;

import com.malin.order_backend.config.ProjectUrlConfig;
import com.malin.order_backend.constant.CookieConstant;
import com.malin.order_backend.constant.DataConstant;
import com.malin.order_backend.dataobj.SellerInfo;
import com.malin.order_backend.enums.ResultEnum;
import com.malin.order_backend.service.SellerService;
import com.malin.order_backend.utils.CookieUtil;
import com.malin.order_backend.utils.ResultVOUtil;
import com.malin.order_backend.viewobj.ResultVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;

/**
 * 用户
 */
@Controller
@Api(tags = "用户相关接口")
@RequestMapping("/User")
public class SellerUserController {

    @Autowired
    private SellerService sellerService;

//    @Autowired
//    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @GetMapping("/login")
    public ResultVO login(@RequestParam("openid") String openid,
                          HttpServletResponse response,
                          Map<String, Object> map) {

        //1. openid去和数据库里的数据匹配
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);
        if (sellerInfo == null) {
            map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
            map.put("url", "/sell/seller/order/list");
            return ResultVOUtil.success(map);
        }

        //2. 设置token//至redis
        String token = UUID.randomUUID().toString();
        Integer expire = DataConstant.EXPIRE;

//        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), openid, expire, TimeUnit.SECONDS);

        //3. 设置token至cookie
        CookieUtil.set(response, CookieConstant.TOKEN, token, expire);

        return ResultVOUtil.success("redirect:" + projectUrlConfig.getSell() + "/sell/seller/order/list");

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
        map.put("url", "/sell/seller/order/list");
        return ResultVOUtil.success(map);
    }
}
