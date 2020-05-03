package com.malin.order_backend.service.impl;

import com.malin.order_backend.dataobj.SellerInfo;
import com.malin.order_backend.repository.SellerInfoRepository;
import com.malin.order_backend.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository repository;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return repository.findByOpenid(openid);
    }

    @Override
    public SellerInfo findSellerInfoByUsername(String username){
        return repository.findByUsername(username);
    }

    @Override
    public String getPassword(){
        return "zszxz";
    }
}
