package rocketmq.subwaytrip;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public class BusTripInfo {

    private String tripId;

    private String userId;

    private String mobile;

    /**公交路线*/
    private String busLine;

    /**方向*/
    private String direction;

    /**费用*/
    private Double fee;

    /**出发时间*/
    private Date departTime;

    public BusTripInfo() {
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

    public String getBusLine() {
        return busLine;
    }

    public void setBusLine(String busLine) {
        this.busLine = busLine;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
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
                && StringUtils.isNotBlank(busLine)
                && StringUtils.isNotBlank(direction)
                && fee != null
                && departTime != null;
    }
}
