package rocketmq.subwaytrip;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public class DoorGuardTripInfo {

    private String tripId;

    private String userId;

    private String mobile;

    private Date enterTime;

    private String enterType;

    private String guardName;

    private String community;

    private String city;

    private String manager;

    private Double longitude;

    private Double latitude;

    public DoorGuardTripInfo() {
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

    public Date getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(Date enterTime) {
        this.enterTime = enterTime;
    }

    public String getEnterType() {
        return enterType;
    }

    public void setEnterType(String enterType) {
        this.enterType = enterType;
    }

    public String getGuardName() {
        return guardName;
    }

    public void setGuardName(String guardName) {
        this.guardName = guardName;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Boolean verify(){
        return StringUtils.isNotBlank(tripId)
                && StringUtils.isNotBlank(userId)
                && StringUtils.isNotBlank(mobile)
                && StringUtils.isNotBlank(enterType)
                && StringUtils.isNotBlank(guardName)
                && StringUtils.isNotBlank(community)
                && StringUtils.isNotBlank(city)
                && StringUtils.isNotBlank(manager)
                && enterTime != null
                && longitude != null
                && latitude != null;
    }
}
