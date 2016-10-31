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

        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(mResource,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.relativeLayout = (RelativeLayout)convertView.findViewById(R.id.stockItemListContainer);
            viewHolder.company = (TextView)convertView.findViewById(R.id.companyName);
            viewHolder.price = (TextView) convertView.findViewById(R.id.price);
            viewHolder.preClose = (TextView) convertView.findViewById(R.id.pre);
            viewHolder.open = (TextView) convertView.findViewById(R.id.open);
            viewHolder.perChange = (TextView) convertView.findViewById(R.id.perChange);
            viewHolder.high = (TextView) convertView.findViewById(R.id.dayHigh);
            viewHolder.low = (TextView) convertView.findViewById(R.id.dayLow);
            viewHolder.buyQty = (TextView) convertView.findViewById(R.id.buyQty);
            viewHolder.sellQty = (TextView) convertView.findViewById(R.id.sellQty);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        StockData stockData = getItem(position);
        Stock stock = stockData.getStock().get(0);

        if(stock != null) {
            viewHolder.company.setText(getCompanyNameTrimmed(stock.getCompanyName()));
            viewHolder.price.setText(stock.getLastPrice());
            viewHolder.preClose.setText(stock.getPreviousClose());
            viewHolder.open.setText(stock.getOpen());
            viewHolder.perChange.setText(stock.getPChange());
            viewHolder.high.setText(stock.getDayHigh());
            viewHolder.low.setText(stock.getDayLow());
            viewHolder.buyQty.setText(viewHolder.buyQty.getText().toString().replace("{0}",stock.getTotalBuyQuantity()));
            viewHolder.sellQty.setText(viewHolder.sellQty.getText().toString().replace("{0}",stock.getTotalSellQuantity()));
            viewHolder.relativeLayout.setBackgroundColor(setStockMoodColor(viewHolder.perChange.getText().toString()));
        }else{
            viewHolder.company.setText("No Data Available.....");
        }
        return convertView;
    }

    static class ViewHolder{
        TextView company;
        TextView price;
        TextView preClose;
        TextView open;
        TextView perChange;
        TextView high;
        TextView low;
        TextView buyQty;
        TextView sellQty;
        RelativeLayout relativeLayout;
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
