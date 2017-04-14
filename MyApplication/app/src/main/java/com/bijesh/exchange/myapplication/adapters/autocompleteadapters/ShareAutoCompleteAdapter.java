package com.bijesh.exchange.myapplication.adapters.autocompleteadapters;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.bijesh.exchange.myapplication.BaseApplication;
import com.bijesh.exchange.myapplication.models.dbmodels.Share;
import com.bijesh.exchange.myapplication.models.parsers.ShareSymbol;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bijesh on 4/8/2017.
 * reference : http://android.foxykeep.com/dev/how-to-add-autocompletion-to-an-edittext
 */

public class ShareAutoCompleteAdapter  extends ArrayAdapter<ShareSymbol> implements Filterable {

    private LayoutInflater mInflater = null;
    private int resId;
    private List<ShareSymbol> mListShareSymbols;
    private StringBuilder mStringBuiler = new StringBuilder();
    private BaseApplication mBaseApplication;

    public ShareAutoCompleteAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    public ShareAutoCompleteAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public ShareAutoCompleteAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ShareSymbol[] objects) {
        super(context, resource, objects);
    }

    public ShareAutoCompleteAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull ShareSymbol[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public ShareAutoCompleteAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ShareSymbol> objects) {
        super(context, resource, objects);
        this.resId = resource;
        this.mListShareSymbols = objects;
        mInflater = LayoutInflater.from(context);
        mBaseApplication = (BaseApplication)context.getApplicationContext();
    }

    public ShareAutoCompleteAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull List<ShareSymbol> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final TextView tv;
        if (convertView != null) {
            tv = (TextView) convertView;
        } else {
            tv = (TextView) mInflater.inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
        }

        tv.setText(getFormattedShareSymbol(getItem(position)));
        return tv;
    }

    @Override
    public int getCount() {
        return mListShareSymbols.size();
    }

    private String getFormattedShareSymbol(ShareSymbol shareSymbol){
        mStringBuiler.setLength(0);
        mStringBuiler.append(shareSymbol.getSYMBCOMPANYNAME());
        return mStringBuiler.toString();
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<ShareSymbol> shareSymbol = null;
                if(constraint != null){
                    if(mListShareSymbols != null && mListShareSymbols.size() > 0) {
                        shareSymbol = getFilteredShareSymbols(mListShareSymbols,constraint.toString());
                    }else{
                        shareSymbol = getFilteredShareSymbols(mBaseApplication.getShareSymbol(),constraint.toString());
                    }
                }
                if(shareSymbol == null){
                    shareSymbol = new ArrayList<>();
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = shareSymbol;
                filterResults.count = shareSymbol.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                clear();
                for(ShareSymbol shareSymbol:(List<ShareSymbol>)results.values){
                    add(shareSymbol);
                }
                if(results.count > 0){
                    notifyDataSetChanged();
                }else{
                    notifyDataSetInvalidated();
                }
            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                return resultValue == null ? "":((ShareSymbol)resultValue).getSYMBCOMPANYNAME();
            }
        };
    }

    private List<ShareSymbol> getFilteredShareSymbols(List<ShareSymbol> allShareSymbols,String constraints){
        List<ShareSymbol> returnList = new ArrayList<>();
        for(ShareSymbol shareSymbol:allShareSymbols){
            if(shareSymbol.getSYMBCOMPANYNAME().contains(constraints)){
                returnList.add(shareSymbol);
            }
        }
        return returnList;
    }

}
