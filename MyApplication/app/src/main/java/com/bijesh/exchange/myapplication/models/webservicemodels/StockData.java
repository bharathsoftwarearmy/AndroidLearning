
package com.bijesh.exchange.myapplication.models.webservicemodels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class StockData implements Serializable{

    @SerializedName("futLink")
    @Expose
    private String futLink;
    @SerializedName("otherSeries")
    @Expose
    private List<String> otherSeries = new ArrayList<String>();
    @SerializedName("lastUpdateTime")
    @Expose
    private String lastUpdateTime;
    @SerializedName("tradedDate")
    @Expose
    private String tradedDate;
    @SerializedName("data")
    @Expose
    private List<Stock> data = new ArrayList<Stock>();
    @SerializedName("optLink")
    @Expose
    private String optLink;

    /**
     * 
     * @return
     *     The futLink
     */
    public String getFutLink() {
        return futLink;
    }

    /**
     * 
     * @param futLink
     *     The futLink
     */
    public void setFutLink(String futLink) {
        this.futLink = futLink;
    }

    /**
     * 
     * @return
     *     The otherSeries
     */
    public List<String> getOtherSeries() {
        return otherSeries;
    }

    /**
     * 
     * @param otherSeries
     *     The otherSeries
     */
    public void setOtherSeries(List<String> otherSeries) {
        this.otherSeries = otherSeries;
    }

    /**
     * 
     * @return
     *     The lastUpdateTime
     */
    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * 
     * @param lastUpdateTime
     *     The lastUpdateTime
     */
    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * 
     * @return
     *     The tradedDate
     */
    public String getTradedDate() {
        return tradedDate;
    }

    /**
     * 
     * @param tradedDate
     *     The tradedDate
     */
    public void setTradedDate(String tradedDate) {
        this.tradedDate = tradedDate;
    }

    /**
     * 
     * @return
     *     The data
     */
    public List<Stock> getStock() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setStock(List<Stock> data) {
        this.data = data;
    }

    /**
     * 
     * @return
     *     The optLink
     */
    public String getOptLink() {
        return optLink;
    }

    /**
     * 
     * @param optLink
     *     The optLink
     */
    public void setOptLink(String optLink) {
        this.optLink = optLink;
    }


    @Override
    public String toString() {
        if(this.getStock() != null && this.getStock().size() > 0) {
            return this.getStock().get(0).getSymbol();
        }else{
            return null;
        }
    }
}
