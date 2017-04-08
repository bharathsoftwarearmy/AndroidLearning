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

import com.bijesh.exchange.myapplication.models.parsers.ShareSymbol;

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
                List<ShareSymbol> shareSymbol;
                if(constraint != null){

                }
                return null;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

            }
        };
    }
}
