package com.bijesh.exchange.myapplication.adapters.listviewadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bijesh.exchange.myapplication.R;
import com.bijesh.exchange.myapplication.models.adaptermodels.StockChange;

import java.util.List;

/**
 * Created by Bijesh on 5/29/2016.
 */
public class HomeScreenAdapter extends ArrayAdapter<StockChange>{

    private List<StockChange> mListObjects;
    private int mResource;

    public HomeScreenAdapter(Context context, int resource, List<StockChange> objects) {
        super(context, resource, objects);
        this.mListObjects = objects;
        this.mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StockChange stockChange = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(mResource,parent,false);
        }

        TextView company = (TextView)convertView.findViewById(R.id.companyName);
        TextView price = (TextView) convertView.findViewById(R.id.price);
        TextView priceChange = (TextView) convertView.findViewById(R.id.priceChange);
        TextView percentageChange = (TextView) convertView.findViewById(R.id.percentageChange);

        company.setText(stockChange.getCompanyName());
        price.setText(stockChange.getPrice());
        priceChange.setText(stockChange.getPriceChange());
        percentageChange.setText(stockChange.getPercentageChange());



        return convertView;
    }



}
