package com.bijesh.exchange.myapplication.models.dbmodels;

/**
 * Created by Bijesh on 10/28/2016.
 */

public class Share {

    private String shareName;
    private String shareSymbol;
    private long previousNotificationTime;
    private double triggerPrice;
    private String comments;
    private int numberOfShares;

    public int getNumberOfShares() {
        return numberOfShares;
    }

    public void setNumberOfShares(int numberOfShares) {
        this.numberOfShares = numberOfShares;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

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
