package rocketmq.subwaytrip;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public class SubwayTripArriveInfo {

    private String userId;

    private String arriveSiteId;

    private String arriveSite;

    private Date arriveTime;

    public SubwayTripArriveInfo() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getArriveSiteId() {
        return arriveSiteId;
    }

    public void setArriveSiteId(String arriveSiteId) {
        this.arriveSiteId = arriveSiteId;
    }

    public String getArriveSite() {
        return arriveSite;
    }

    public void setArriveSite(String arriveSite) {
        this.arriveSite = arriveSite;
    }

    public Date getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Date arriveTime) {
        this.arriveTime = arriveTime;
    }

    public Boolean verify(){
        return StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(arriveSiteId) && StringUtils.isNotBlank(arriveSite) && arriveTime != null;
    }

}
