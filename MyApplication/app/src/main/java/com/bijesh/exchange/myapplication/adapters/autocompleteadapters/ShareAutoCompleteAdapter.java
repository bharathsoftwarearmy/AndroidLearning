package com.bijesh.exchange.myapplication.adapters.autocompleteadapters;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.Filterable;

import com.bijesh.exchange.myapplication.models.parsers.ShareSymbol;

import java.util.List;

/**
 * Created by Bijesh on 4/8/2017.
 */

public class ShareAutoCompleteAdapter  extends ArrayAdapter<ShareSymbol> implements Filterable {

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
    }

    public ShareAutoCompleteAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull List<ShareSymbol> objects) {
        super(context, resource, textViewResourceId, objects);
    }


    

}
