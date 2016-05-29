package com.bijesh.exchange.myapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bijesh.exchange.myapplication.R;
import com.bijesh.exchange.myapplication.adapters.listviewadapters.HomeScreenAdapter;
import com.bijesh.exchange.myapplication.models.adaptermodels.StockChange;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bijesh on 5/21/2016.
 */
public class HomeFragment extends BaseFragment{

    private View mRootView;
    private ListView mStockListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mRootView == null){
            mRootView = inflater.inflate(R.layout.home_fragment,container,false);
            initComponents(mRootView);
        }
        return mRootView;
    }

    private void initComponents(View mRootView){
        mStockListView = (ListView) mRootView.findViewById(R.id.stockList);
        attachAdapter(mStockListView);
    }

    private void attachAdapter(ListView listView){
        HomeScreenAdapter homeScreenAdapter = new HomeScreenAdapter(getContext(),R.layout.stock_list_item,initStockChangeDetails());
        listView.setAdapter(homeScreenAdapter);
    }


    private List<StockChange> initStockChangeDetails(){
        List<StockChange> changes = new ArrayList<>();
        StockChange change1 = new StockChange();
        change1.setCompanyName("Crompton");
        change1.setExchange("NSE");
        change1.setPrice("60");
        change1.setPercentageChange("1.2%");
        change1.setPriceChange("0.13");

        StockChange change2 = new StockChange();
        change2.setCompanyName("Jindal");
        change2.setExchange("NSE");
        change2.setPrice("61");
        change2.setPercentageChange("1.2%");
        change2.setPriceChange("0.13");

        StockChange change3 = new StockChange();
        change3.setCompanyName("Mangalam Timbers");
        change3.setExchange("NSE");
        change3.setPrice("23");
        change3.setPercentageChange("1.2%");
        change3.setPriceChange("0.13");

        StockChange change4 = new StockChange();
        change4.setCompanyName("PNB");
        change4.setExchange("NSE");
        change4.setPrice("75");
        change4.setPercentageChange("1.2%");
        change4.setPriceChange("0.13");

        StockChange change5 = new StockChange();
        change5.setCompanyName("Marsons");
        change5.setExchange("NSE");
        change5.setPrice("13");
        change5.setPercentageChange("1.2%");
        change5.setPriceChange("0.13");

        StockChange change6 = new StockChange();
        change6.setCompanyName("TVS Motors");
        change6.setExchange("NSE");
        change6.setPrice("295");
        change6.setPercentageChange("1.2%");
        change6.setPriceChange("0.13");

        StockChange change7 = new StockChange();
        change7.setCompanyName("Mahindra CIE");
        change7.setExchange("NSE");
        change7.setPrice("195");
        change7.setPercentageChange("1.2%");
        change7.setPriceChange("0.13");

        changes.add(change1);
        changes.add(change2);
        changes.add(change3);
        changes.add(change4);
        changes.add(change5);
        changes.add(change6);
        changes.add(change7);
        changes.add(change1);
        changes.add(change2);
        changes.add(change3);
        changes.add(change4);
        changes.add(change5);
        changes.add(change6);
        changes.add(change7);
        changes.add(change1);
        changes.add(change2);
        changes.add(change3);
        changes.add(change4);
        changes.add(change5);
        changes.add(change6);
        changes.add(change7);
        changes.add(change1);
        changes.add(change2);
        changes.add(change3);
        changes.add(change4);
        changes.add(change5);
        changes.add(change6);
        changes.add(change7);
        return changes;
    }


}
