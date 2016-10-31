package com.bijesh.exchange.myapplication.utils;

import com.bijesh.exchange.myapplication.models.dbmodels.Share;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bijesh on 10/23/2016.
 */

public class StringUtil {

    private static final String NSE_SHARE_URL = "https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/ajaxGetQuoteJSON.jsp?symbol=";

//    public static List<String> getUrls(){
//        List<String> urls = new ArrayList<>();
////
//        urls.add( "https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/ajaxGetQuoteJSON.jsp?symbol=ASHOKLEY");
//        urls.add( "https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/ajaxGetQuoteJSON.jsp?symbol=INOXWIND");
//        urls.add( "https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/ajaxGetQuoteJSON.jsp?symbol=KMSUGAR");
//        urls.add( "https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/ajaxGetQuoteJSON.jsp?symbol=MAWANASUG");
//        urls.add( "https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/ajaxGetQuoteJSON.jsp?symbol=METALFORGE");
//        urls.add( "https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/ajaxGetQuoteJSON.jsp?symbol=RCOM");
//        urls.add( "https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/ajaxGetQuoteJSON.jsp?symbol=KCPSUGIND");
//        urls.add( "https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/ajaxGetQuoteJSON.jsp?symbol=MANDHANA");
//        urls.add( "https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/ajaxGetQuoteJSON.jsp?symbol=SHRENUJ");
//        return urls;
//    }

    public static List<String> getShareUrls(List<Share> shares){
        List<String> urls = new ArrayList<>();
        for(Share share:shares){
            urls.add(NSE_SHARE_URL+share.getShareSymbol());
        }
        return urls;
    }


}
