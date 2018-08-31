package rocketmq.attendance;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

public class EntranceGuardPassMessage implements Serializable {

    /**ID*/
    private String id;

    /**用户ID*/
    private String userId;

    /**手机号*/
    private String mobile;

    /**用户姓名*/
    private String name;

    /**用户IMSI*/
    private String imsi;

    /**基站ID*/
    private String cellTowerCode;

    /**基站名*/
    private String cellTowerName;

    /**通行时间*/
    private Date passTime;

    /**状态*/
    private Integer status;

    /**客户ID*/
    private String customerId;

    /**客户*/
    private String customerName;

    public EntranceGuardPassMessage() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getCellTowerCode() {
        return cellTowerCode;
    }

    public void setCellTowerCode(String cellTowerCode) {
        this.cellTowerCode = cellTowerCode;
    }

    public String getCellTowerName() {
        return cellTowerName;
    }

    public void setCellTowerName(String cellTowerName) {
        this.cellTowerName = cellTowerName;
    }

    public Date getPassTime() {
        return passTime;
    }

    public void setPassTime(Date passTime) {
        this.passTime = passTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Boolean verify(){
        return StringUtils.isNotBlank(id)
                && StringUtils.isNotBlank(imsi)
                && StringUtils.isNotBlank(cellTowerCode)
                && StringUtils.isNotBlank(cellTowerName)
                && StringUtils.isNotBlank(customerId)
                && StringUtils.isNotBlank(customerName)
                && passTime != null
                && status != null;
    }

    @Override
    public String toString() {
        return "EntranceGuardPassMessage{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", mobile='" + mobile + '\'' +
                ", name='" + name + '\'' +
                ", imsi='" + imsi + '\'' +
                ", cellTowerId='" + cellTowerCode + '\'' +
                ", cellTowerName='" + cellTowerName + '\'' +
                ", passTime=" + passTime +
                ", status=" + status +
                ", customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                '}';
    }
}