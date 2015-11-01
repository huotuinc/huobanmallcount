package com.huotu.huobanmallcount.controller;

import com.huotu.huobanmallcount.api.DreamSystem;
import com.huotu.huobanmallcount.api.common.ApiResult;
import com.huotu.huobanmallcount.api.common.Output;
import com.huotu.huobanmallcount.config.CommonEnum;
import com.huotu.huobanmallcount.model.*;
import com.huotu.huobanmallcount.repository.UserShareRebateSourceRepository;
import com.huotu.huobanmallcount.repository.UserShareSourceRepository;
import com.huotu.huobanmallcount.service.DreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by lgh on 2015/10/19.
 */

@Controller
@RequestMapping("/dream")
public class DreamController implements DreamSystem {

    @Autowired
    private DreamService dreamService;


    @Autowired
    private UserShareRebateSourceRepository userShareRebateSourceRepository;

    @Override
    @RequestMapping("/total")
    public ApiResult total(Output<DreamTotalModel> data, Integer userId) {
        data.outputData(dreamService.total(userId));
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @Override
    @RequestMapping("/top")
    public ApiResult top(Output<DreamTopModel[]> data, Integer merchantId) {
        List<DreamTopModel> dreamTopModels = dreamService.top(merchantId);
        data.outputData(dreamTopModels.toArray(new DreamTopModel[dreamTopModels.size()]));
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @Override
    @RequestMapping("/mytop")
    public ApiResult mytop(Output<Integer> top, Integer merchantId, Integer userId) {
        top.outputData(dreamService.myTop(merchantId, userId));
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @Override
    @RequestMapping("/fivesource")
    public ApiResult fivesource(Output<DreamSourceModel[]> data, Integer userId, Integer pageSize, Integer lastId) {
        List<DreamSourceModel> dreamSourceModels = dreamService.fivSource(userId, pageSize, lastId);
        data.outputData(dreamSourceModels.toArray(new DreamSourceModel[dreamSourceModels.size()]));
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @Override
    @RequestMapping("/twosource")
    public ApiResult twosource(Output<DreamSourceModel[]> data, Integer userId, Integer pageSize, Integer lastId) {
        List<DreamSourceModel> dreamSourceModels = dreamService.twoSource(userId, pageSize, lastId);
        data.outputData(dreamSourceModels.toArray(new DreamSourceModel[dreamSourceModels.size()]));
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @Override
    @RequestMapping("/rebatelist")
    public ApiResult rebatelist(Output<DreamRebateListModel[]> data, Integer merchantId, Integer pageSize, Integer page, String userName, Long startTime, Long endTime) {
        List<DreamRebateListModel> dreamListModels = dreamService.rebateList(merchantId, pageSize, page, userName, startTime, endTime);
        data.outputData(dreamListModels.toArray(new DreamRebateListModel[dreamListModels.size()]));
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @Override
    @RequestMapping("/rebatecount")
    public ApiResult rebatecount(Output<Integer> data, Integer merchantId, String userName, Long startTime, Long endTime) {
        data.outputData(dreamService.rebateCount(merchantId, userName, startTime, endTime));
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @Override
    @RequestMapping("/sharelist")
    public ApiResult sharelist(Output<DreamShareListModel[]> data, Integer merchantId, Integer pageSize, Integer page, String userName) {
        List<DreamShareListModel> dreamListModels = dreamService.shareList(merchantId, pageSize, page, userName);
        data.outputData(dreamListModels.toArray(new DreamShareListModel[dreamListModels.size()]));
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @Override
    @RequestMapping("/sharecount")
    public ApiResult sharecount(Output<Integer> data, Integer merchantId, String userName) {
        data.outputData(dreamService.shareCount(merchantId, userName));
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @Override
    @RequestMapping("/rebatedetail")
    public ApiResult rebatedetail(Output<DreamShareDetailModel[]> fiveData, Output<DreamShareDetailModel[]> twoData, Integer userId, Long time) {
        List<DreamShareDetailModel> five = dreamService.rebateFiveDetail(userId, time);
        List<DreamShareDetailModel> two = dreamService.rebateTwoDetail(userId, time);

        fiveData.outputData(five.toArray(new DreamShareDetailModel[five.size()]));
        twoData.outputData(two.toArray(new DreamShareDetailModel[two.size()]));
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @Override
    @RequestMapping("/sharedetail")
    public ApiResult sharedetail(Output<DreamShareDetailModel[]> fiveData, Output<DreamShareDetailModel[]> twoData, Integer userId) {
        List<DreamShareDetailModel> five = dreamService.shareFiveDetail(userId);
        List<DreamShareDetailModel> two = dreamService.shareTwoDetail(userId);

        fiveData.outputData(five.toArray(new DreamShareDetailModel[five.size()]));
        twoData.outputData(two.toArray(new DreamShareDetailModel[two.size()]));
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }
}
