package rocketmq.subwaytrip;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * 地铁出发MQ信息
 */
public class SubwayTripDepartInfo {

    private String tripId;

    private String userId;

    private String mobile;

    private String departSiteId;

    private String departSite;

    private Date departTime;

    public SubwayTripDepartInfo() {
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDepartSiteId() {
        return departSiteId;
    }

    public void setDepartSiteId(String departSiteId) {
        this.departSiteId = departSiteId;
    }

    public String getDepartSite() {
        return departSite;
    }

    public void setDepartSite(String departSite) {
        this.departSite = departSite;
    }

    public Date getDepartTime() {
        return departTime;
    }

    public void setDepartTime(Date departTime) {
        this.departTime = departTime;
    }

    public Boolean verify(){
        return StringUtils.isNotBlank(tripId)
                && StringUtils.isNotBlank(userId)
                && StringUtils.isNotBlank(mobile)
                && StringUtils.isNotBlank(departSiteId)
                && StringUtils.isNotBlank(departSite)
                && departTime != null;
    }


}
