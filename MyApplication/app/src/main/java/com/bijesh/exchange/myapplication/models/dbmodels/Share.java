package com.bijesh.exchange.myapplication.models.dbmodels;

/**
 * Created by Bijesh on 10/28/2016.
 */

public class Share {

    private String shareName;
    private String shareSymbol;
    private long previousNotificationTime;
    private double triggerPrice;

    public double getTriggerPrice() {
        return triggerPrice;
    }

    public void setTriggerPrice(double triggerPrice) {
        this.triggerPrice = triggerPrice;
    }

    public long getPreviousNotificationTime() {
        return previousNotificationTime;
    }

    public void setPreviousNotificationTime(long previousNotificationTime) {
        this.previousNotificationTime = previousNotificationTime;
    }

    public String getShareSymbol() {
        return shareSymbol;
    }

    public void setShareSymbol(String shareSymbol) {
        this.shareSymbol = shareSymbol;
    }

    public String getShareName() {
        return shareName;
    }

    public void setShareName(String shareName) {
        this.shareName = shareName;
    }


    @Override
    public String toString() {
        return shareName+" :: "+shareSymbol;
    }
}
