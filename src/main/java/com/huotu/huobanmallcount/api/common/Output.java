package com.huotu.huobanmallcount.api.common;

/**
 * 输出参数
 *
 * Created by CJ on 6/2/15.
 * @author CJ
 */
public interface Output<T> {

    <X extends T> void outputData(X data);
}
