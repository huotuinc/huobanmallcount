package com.huotu.huobanmallcount.concurrency;

import com.huotu.huobanmallcount.entity.UserShareAmounted;
import com.huotu.huobanmallcount.model.TreeNode;

import java.util.List;

/**
 * Created by lgh on 2015/10/9.
 */

public interface SystemCounting {

    List<UserShareAmounted> countFromLeaf(TreeNode node);

    void countAll();

    void countMerchant(int merchantId);
}
