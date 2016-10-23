package com.bijesh.exchange.myapplication.adapters.listviewadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bijesh.exchange.myapplication.R;
import com.bijesh.exchange.myapplication.models.adaptermodels.StockChange;
import com.bijesh.exchange.myapplication.models.webservicemodels.Stock;
import com.bijesh.exchange.myapplication.models.webservicemodels.StockData;

import java.util.List;

/**
 * Created by Bijesh on 5/29/2016.
 */
public class HomeScreenAdapter extends ArrayAdapter<StockData>{

    private List<StockData> mListObjects;
    private int mResource;
    private Context mContext;

    public HomeScreenAdapter(Context context, int resource, List<StockData> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mListObjects = objects;
        this.mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // TODO: 10/23/2016 use view holder pattern 
        
        StockData stockData = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(mResource,parent,false);
        }

        RelativeLayout relativeLayout = (RelativeLayout)convertView.findViewById(R.id.stockItemListContainer);
        TextView company = (TextView)convertView.findViewById(R.id.companyName);
        TextView price = (TextView) convertView.findViewById(R.id.price);
        TextView preClose = (TextView) convertView.findViewById(R.id.pre);
        TextView open = (TextView) convertView.findViewById(R.id.open);
        TextView perChange = (TextView) convertView.findViewById(R.id.perChange);
        TextView high = (TextView) convertView.findViewById(R.id.dayHigh);
        TextView low = (TextView) convertView.findViewById(R.id.dayLow);


        Stock stock = stockData.getStock().get(0);

        if(stock != null) {
            company.setText(getCompanyNameTrimmed(stock.getCompanyName()));
            price.setText(stock.getLastPrice());
            preClose.setText(stock.getPreviousClose());
            open.setText(stock.getOpen());
            perChange.setText(stock.getPChange());
            high.setText(stock.getDayHigh());
            low.setText(stock.getDayLow());
            relativeLayout.setBackgroundColor(setStockMoodColor(perChange.getText().toString()));
        }else{
            company.setText("No Data Available.....");
        }


        return convertView;
    }

    private String getCompanyNameTrimmed(String companyName){
        if(companyName != null){
            String[] names = companyName.split(" ");
            if(names != null && names.length > 2){
                return names[0]+" "+names[1];
            }else{
                return companyName;
            }
        }
        return companyName;
    }

    private int setStockMoodColor(String perChange){
        double percentageChange = Double.parseDouble(perChange);
        if(percentageChange >= 0){
            if(percentageChange < 3){
                return mContext.getResources().getColor(R.color.greaterThan1Percentage);
            }else if ((percentageChange > 3) && (percentageChange < 5)){
                return mContext.getResources().getColor(R.color.greaterThanLessThan3Percentage);
            }else{
                return mContext.getResources().getColor(R.color.greaterThanLessThan5Percentage);
            }
        }else{
            if(percentageChange > -3){
                return mContext.getResources().getColor(R.color.lessThan1Percentage);
            }else if ((percentageChange < -3) && (percentageChange > -5)){
                return mContext.getResources().getColor(R.color.lessThan3Percentage);
            }else{
                return mContext.getResources().getColor(R.color.lessThan5Percentage);
            }
        }
    }



}
