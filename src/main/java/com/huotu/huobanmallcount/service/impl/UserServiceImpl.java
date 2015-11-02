/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmallcount.service.impl;

import com.huotu.huobanmallcount.entity.User;
import com.huotu.huobanmallcount.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by lgh on 2015/10/19.
 */

@Service
public class UserServiceImpl implements UserService {
    public String getViewUserName(User user) {
        if (user == null) {
            return "";
        }
        String realName = user.getRealName();
        String wxNickName = user.getWxNickName();
        String mobile = user.getMobile();
        String userName = user.getUsername();
        if (StringUtils.isEmpty(realName)) {
            if (StringUtils.isEmpty(wxNickName)) {
                if (StringUtils.isEmpty(mobile)) {
                    return userName;
                } else {
                    return mobile;
                }
            } else {
                return wxNickName;
            }
        } else {
            return realName;
        }
    }
}
