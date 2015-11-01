package com.huotu.huobanmallcount.service.impl;


import com.huotu.huobanmallcount.entity.*;
import com.huotu.huobanmallcount.model.*;
import com.huotu.huobanmallcount.repository.UserRepository;
import com.huotu.huobanmallcount.repository.UserShareRebateRepository;
import com.huotu.huobanmallcount.repository.UserShareRepository;
import com.huotu.huobanmallcount.repository.UserShareSourceRepository;
import com.huotu.huobanmallcount.service.CommonConfigService;
import com.huotu.huobanmallcount.service.DreamService;
import com.huotu.huobanmallcount.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lgh on 2015/10/19.
 */

@Service
public class DreamServiceImpl implements DreamService {

    @Autowired
    private UserShareRepository userShareRepository;

    @Autowired
    private UserShareSourceRepository userShareSourceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CommonConfigService commonConfigService;

    @Autowired
    private UserShareRebateRepository userShareRebateRepository;


    @Override
    public DreamTotalModel total(Integer userId) {
//        Date time = DateHelper.getYesterdayBegin();
        UserShare userShare = userShareRepository.findByUserId(userId);
        if (userShare != null) {
            DreamTotalModel result = new DreamTotalModel();
            result.setFiveIncome(userShare.getPercentFiveAmount());
            result.setFiveNum(userShare.getPercentFiveNum());
            result.setFiveTotal(result.getFiveIncome() * 20);
            result.setTwoIncome(userShare.getPercentTwoAmount());
            result.setTwoNum(userShare.getPercentTwoNum());
            result.setTwoTotal(result.getTwoIncome() * 50);
            return result;
        }
        return null;
    }

    @Override
    public List<DreamTopModel> top(Integer merchantId) {
//        Date time = DateHelper.getYesterdayBegin();
        StringBuffer hql = new StringBuffer();
        hql.append("select share.userId,sum(share.percentFiveAmount+share.percentTwoAmount) num from UserShareRebate share " +
                " where share.merchantId=:merchantId" +
                " group by share.userId order by num desc");
        List list = userShareRebateRepository.queryHql(hql.toString(), query -> {
            query.setParameter("merchantId", merchantId);
            query.setMaxResults(10);
        });


        String resoureServerUrl = commonConfigService.getResoureServerUrl();
        List<DreamTopModel> dreamTopModels = new ArrayList<>();
        list.forEach(data -> {
            Object[] objects = (Object[]) data;
            User user = userRepository.findOne(Integer.parseInt(objects[0].toString()));
            dreamTopModels.add(new DreamTopModel(StringUtils.isEmpty(user.getUserFace()) ? "" : resoureServerUrl + user.getUserFace()
                    , userService.getViewUserName(user)
                    , Float.parseFloat(objects[1].toString())));
        });

        return dreamTopModels;
    }

    @Override
    public Integer myTop(Integer merchantId, Integer userId) {
//        Date time = DateHelper.getYesterdayBegin();
        StringBuffer hql = new StringBuffer();
//        hql.append("select t.rowid from (select share.userId,row_number() over(order by sum(share.percentFiveAmount+share.percentTwoAmount) desc) as rowid from UserShare share " +
//                " where share.merchantId=:merchantId and share.time=:time) as t where t.userId=:userId");
        hql.append("select share.userId from UserShareRebate share " +
                " where share.merchantId=:merchantId " +
                " group by share.userId " +
                " order by sum(share.percentFiveAmount+share.percentTwoAmount) desc");
        List list = userShareRebateRepository.queryHql(hql.toString(), query -> {
            query.setParameter("merchantId", merchantId);
//            query.setParameter("userId", userId);
        });

        boolean exist = false;
        int count = 0;
        for (Object data : list) {
            if (data.toString().equals(userId.toString())) {
                exist = true;
                break;
            }
            count += 1;
        }
        if (exist)
            return count + 1;
        else
            return -1;
    }

//    @Override
//    public List<DreamSourceModel> fivSource(Integer userId) {
//        Date time = DateHelper.getYesterdayBegin();
//
//
//        StringBuilder hql = new StringBuilder();
//        hql.append("select source,user from UserShareSource source" +
//                " left join User user on source.sourceUserId=user.id" +
//                " where source.userId=:userId and source.time=:time and source.type=:type");
//        List list = userShareSourceRepository.queryHql(hql.toString(), query -> {
//            query.setParameter("userId", userId);
//            query.setParameter("time", time);
//            query.setParameter("type", 1);
//        });
//
//        String resoureServerUrl = commonConfigService.getResoureServerUrl();
//        List<DreamSourceModel> result = new ArrayList<>();
//        list.forEach(data -> {
//            Object[] objects = (Object[]) data;
//            UserShareSource userShareSource = (UserShareSource) objects[0];
//            User user = (User) objects[1];
//            result.add(new DreamSourceModel(StringUtils.isEmpty(user.getUserFace()) ? "" : resoureServerUrl + user.getUserFace()
//                    , userService.getViewUserName(user)
//                    , userShareSource.getAmount()));
//        });
//        return fivSource(userId, time.getTime());
//    }

//    @Override
//    public List<DreamSourceModel> twoSource(Integer userId) {
//        Date time = DateHelper.getYesterdayBegin();


//        StringBuilder hql = new StringBuilder();
//        hql.append("select source,user from UserShareSource source" +
//                " left join User user on source.sourceUserId=user.id" +
//                " where source.userId=:userId and source.time=:time and source.type=:type");
//        List list = userShareSourceRepository.queryHql(hql.toString(), query -> {
//            query.setParameter("userId", userId);
//            query.setParameter("time", time);
//            query.setParameter("type", 2);
//        });
//
//        String resoureServerUrl = commonConfigService.getResoureServerUrl();
//        List<DreamSourceModel> result = new ArrayList<>();
//        list.forEach(data -> {
//            Object[] objects = (Object[]) data;
//            UserShareSource userShareSource = (UserShareSource) objects[0];
//            User user = (User) objects[1];
//            result.add(new DreamSourceModel(StringUtils.isEmpty(user.getUserFace()) ? "" : resoureServerUrl + user.getUserFace()
//                    , userService.getViewUserName(user)
//                    , userShareSource.getAmount()));
//        });
//        return twoSource(userId, time.getTime());
//    }


    @Override
    public List<DreamSourceModel> fivSource(Integer userId, Integer pageSize, Integer lastId) {
        return source(userId, pageSize, lastId, 1);
    }

    @Override
    public List<DreamSourceModel> twoSource(Integer userId, Integer pageSize, Integer lastId) {
        return source(userId, pageSize, lastId, 2);
    }

    private List<DreamSourceModel> source(Integer userId, Integer pageSize, Integer lastId, Integer type) {
        StringBuilder hql = new StringBuilder();
        hql.append("select source,user from UserShareSource source" +
                " left join User user on source.sourceUserId=user.id" +
                " where source.userId=:userId and source.type=:type");
        if (lastId > 0) hql.append(" and source.id>:lastId");
        hql.append(" order by source.id");

        List list = userShareSourceRepository.queryHql(hql.toString(), query -> {
            query.setParameter("userId", userId);
            query.setParameter("type", type);
            if (lastId > 0) query.setParameter("lastId", lastId);
            query.setMaxResults(pageSize);
        });

        String resoureServerUrl = commonConfigService.getResoureServerUrl();
        List<DreamSourceModel> result = new ArrayList<>();
        list.forEach(data -> {
            Object[] objects = (Object[]) data;
            UserShareSource userShareSource = (UserShareSource) objects[0];
            User user = (User) objects[1];
            result.add(new DreamSourceModel(userShareSource.getId()
                    , StringUtils.isEmpty(user.getUserFace()) ? "" : resoureServerUrl + user.getUserFace()
                    , userService.getViewUserName(user)
                    , userShareSource.getAmount()));
        });
        return result;
    }


    @Override
    public List<DreamRebateListModel> rebateList(Integer merchantId, Integer pageSize, Integer page
            , String userName, Long startTime, Long endTime) {

        StringBuilder hql = new StringBuilder();
        hql.append("select share,user from UserShareRebate share" +
                " left join User user on user.id=share.userId" +
                " where share.merchantId=:merchantId");
        if (!StringUtils.isEmpty(userName)) hql.append(" and user.username=:username");
        if (startTime != null && startTime > 0) hql.append(" and share.time>=:starttime");
        if (endTime != null && endTime > 0) hql.append(" and share.time<=:endtime");

        hql.append(" order by share.time desc,share.userId");

        List list = userShareRebateRepository.queryHql(hql.toString(), query -> {
            query.setParameter("merchantId", merchantId);
            if (!StringUtils.isEmpty(userName)) query.setParameter("username", userName);
            if (startTime != null && startTime > 0) query.setParameter("starttime", new Date(startTime));
            if (endTime != null && endTime > 0) query.setParameter("endtime", new Date(endTime));
            query.setMaxResults(pageSize);
            query.setFirstResult((page - 1) * pageSize);
        });

        List<DreamRebateListModel> result = new ArrayList<>();
        list.forEach(data -> {
            Object[] objects = (Object[]) data;
            UserShareRebate userShareRebate = (UserShareRebate) objects[0];
            User user = (User) objects[1];
            result.add(new DreamRebateListModel(userShareRebate.getUserId()
                    , user.getUsername()
                    , user.getWxNickName()
                    , userShareRebate.getTime()
                    , userShareRebate.getPercentFiveNum(), userShareRebate.getPercentFiveAmount()
                    , userShareRebate.getPercentTwoNum(), userShareRebate.getPercentTwoAmount()));
        });

        return result;
    }

    @Override
    public Integer rebateCount(Integer merchantId, String userName, Long startTime, Long endTime) {
        StringBuilder hql = new StringBuilder();
        hql.append("select count(share) from UserShareRebate share" +
                " left join User user on user.id=share.userId" +
                " where share.merchantId=:merchantId");
        if (!StringUtils.isEmpty(userName)) hql.append(" and user.username=:username");
        if (startTime != null && startTime > 0) hql.append(" and share.time>=:starttime");
        if (endTime != null && endTime > 0) hql.append(" and share.time<=:endtime");

        List list = userShareRebateRepository.queryHql(hql.toString(), query -> {
            query.setParameter("merchantId", merchantId);
            if (!StringUtils.isEmpty(userName)) query.setParameter("username", userName);
            if (startTime != null && startTime > 0) query.setParameter("starttime", new Date(startTime));
            if (endTime != null && endTime > 0) query.setParameter("endtime", new Date(endTime));
        });

        if (list != null) return Integer.parseInt(list.get(0).toString());
        return 0;
//        return userShareRebateRepository.countByMerchantId(merchantId);
    }


    @Override
    public List<DreamShareListModel> shareList(Integer merchantId, Integer pageSize, Integer page
            , String userName) {

        StringBuilder hql = new StringBuilder();
        hql.append("select share,user from UserShare share" +
                " left join User user on user.id=share.userId" +
                " where share.merchantId=:merchantId");
        if (!StringUtils.isEmpty(userName)) hql.append(" and user.username=:username");


        hql.append(" order by share.userId");

        List list = userShareRepository.queryHql(hql.toString(), query -> {
            query.setParameter("merchantId", merchantId);
            if (!StringUtils.isEmpty(userName)) query.setParameter("username", userName);
            query.setMaxResults(pageSize);
            query.setFirstResult((page - 1) * pageSize);
        });

        List<DreamShareListModel> result = new ArrayList<>();
        list.forEach(data -> {
            Object[] objects = (Object[]) data;
            UserShare userShare = (UserShare) objects[0];
            User user = (User) objects[1];
            result.add(new DreamShareListModel(userShare.getUserId()
                    , user.getUsername()
                    , user.getWxNickName()
                    , userShare.getPercentFiveNum(), userShare.getPercentFiveAmount()
                    , userShare.getPercentTwoNum(), userShare.getPercentTwoAmount()));
        });

        return result;
    }

    @Override
    public Integer shareCount(Integer merchantId, String userName) {

        StringBuilder hql = new StringBuilder();
        hql.append("select  count(share) from UserShare share" +
                " left join User user on user.id=share.userId" +
                " where share.merchantId=:merchantId");
        if (!StringUtils.isEmpty(userName)) hql.append(" and user.username=:username");

        List list = userShareRepository.queryHql(hql.toString(), query -> {
            query.setParameter("merchantId", merchantId);
            if (!StringUtils.isEmpty(userName)) query.setParameter("username", userName);
        });
        if (list != null) return Integer.parseInt(list.get(0).toString());
        return 0;
//        return userShareRepository.countByMerchantId(merchantId);
    }

    @Override
    public List<DreamShareDetailModel> rebateFiveDetail(Integer userId, Long time) {
        return rebateDetail(userId, time, 1);
    }

    @Override
    public List<DreamShareDetailModel> rebateTwoDetail(Integer userId, Long time) {
        return rebateDetail(userId, time, 2);
    }

    private List<DreamShareDetailModel> rebateDetail(Integer userId, Long time, Integer type) {
        StringBuilder hql = new StringBuilder();
        hql.append("select share,user from UserShareRebateSource share" +
                " left join User user on share.sourceUserId=user.id" +
                " where share.userId=:userId and share.type=:type and share.time=:time");
        List list = userShareSourceRepository.queryHql(hql.toString(), query -> {
            query.setParameter("userId", userId);
            query.setParameter("type", type);
            query.setParameter("time", new Date(time));
        });

        List<DreamShareDetailModel> result = new ArrayList<>();
        list.forEach(data -> {
            Object[] objects = (Object[]) data;
            UserShareRebateSource userShareRebateSource = (UserShareRebateSource) objects[0];
            User user = (User) objects[1];
            result.add(new DreamShareDetailModel(user.getUsername(), user.getWxNickName(), userShareRebateSource.getAmount()));
        });

        return result;
    }


    @Override
    public List<DreamShareDetailModel> shareFiveDetail(Integer userId) {
        return shareDetail(userId, 1);
    }

    @Override
    public List<DreamShareDetailModel> shareTwoDetail(Integer userId) {
        return shareDetail(userId, 2);
    }


    private List<DreamShareDetailModel> shareDetail(Integer userId, Integer type) {
        StringBuilder hql = new StringBuilder();
        hql.append("select share,user from UserShareSource share" +
                " left join User user on share.sourceUserId=user.id" +
                " where share.userId=:userId and share.type=:type");
        List list = userShareSourceRepository.queryHql(hql.toString(), query -> {
            query.setParameter("userId", userId);
            query.setParameter("type", type);
        });

        List<DreamShareDetailModel> result = new ArrayList<>();
        list.forEach(data -> {
            Object[] objects = (Object[]) data;
            UserShareSource userShareSource = (UserShareSource) objects[0];
            User user = (User) objects[1];
            result.add(new DreamShareDetailModel(user.getUsername(), user.getWxNickName(), userShareSource.getAmount()));
        });

        return result;
    }
}
