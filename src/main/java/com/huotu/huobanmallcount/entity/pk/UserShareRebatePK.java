package com.huotu.huobanmallcount.entity.pk;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by lgh on 2015/10/27.
 */
public class UserShareRebatePK implements Serializable {

    private Integer userId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
