package com.huotu.huobanmallcount.interceptor;

/**
 * 更低级别的api响应
 * @author CJ
 */
public class PhysicalApiResult {

    /**
     *
     系统状态返回：1，成功;0，失败
     */
    private int systemResultCode;
    /**
     *
     成功/失败描述
     */
    private String systemResultDescription;
    /**
     *
     逻辑状态返回 ：1成功,0 失败
     */
    private int resultCode;

    /**
     * 逻辑状态描述
     */

    private String resultDescription;
    /**
     * 返回具体数据
     */
    private Object resultData;


    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public Object getResultData() {
        return resultData;
    }

    public void setResultData(Object resultData) {
        this.resultData = resultData;
    }

    public String getResultDescription() {
        return resultDescription;
    }

    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
    }

    public int getSystemResultCode() {
        return systemResultCode;
    }

    public void setSystemResultCode(int systemResultCode) {
        this.systemResultCode = systemResultCode;
    }

    public String getSystemResultDescription() {
        return systemResultDescription;
    }

    public void setSystemResultDescription(String systemResultDescription) {
        this.systemResultDescription = systemResultDescription;
    }
}