package com.github.sarweshkumar47.placesautocompleteview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;


import com.github.sarweshkumar47.placesautocompleteview.Models.AllPlacesDetails;
import com.github.sarweshkumar47.placesautocompleteview.Models.PlacesCustomInfo;
import com.github.sarweshkumar47.placesautocompleteview.Utils.PlacesSearchAsync;
import com.github.sarweshkumar47.placesautocompleteview.View.ViewHolder;

import java.util.ArrayList;

import static com.github.sarweshkumar47.placesautocompleteview.Utils.Constants.BUILD_DEBUG;

public class PlacesAutoCompleteAdapter extends ArrayAdapter implements Filterable {

    private String TAG = "PlacesAutoComAdapter";
    private ArrayList<AllPlacesDetails> mAllPlacesInfo;
    private ArrayList<PlacesCustomInfo> mPlacesCustomInfo;
    private int LAYOUT_RESOURCE;
    private String url = null;

    PlacesAutoCompleteAdapter(Context context, int resource) {
        super(context, resource);
        LAYOUT_RESOURCE = resource;
        mPlacesCustomInfo = new ArrayList<>();
        if(BUILD_DEBUG) Log.d(TAG, "PlacesAutoCompleteView, PlacesAutoCompleteAdapter constructor");
    }

    @Override
    public int getCount() {
        return mPlacesCustomInfo.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return mPlacesCustomInfo.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        if (view == null) {
            view = inflater.inflate(LAYOUT_RESOURCE, null);
            holder = new ViewHolder();
            holder.placesName  = (TextView) view.findViewById(R.id.myLibPlaceName);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        /*LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.places_auto_complete_layout, parent, false);*/
        view.setBackgroundColor(PlacesAutoCompleteView.getTextSuggestViewBackground());
        String places = mPlacesCustomInfo.get(position).getPlaceName();
        holder.placesName.setTextSize(TypedValue.COMPLEX_UNIT_PX, PlacesAutoCompleteView.getTextSuggestViewTextSize());
        if (PlacesAutoCompleteView.isSingleLineEnabled()) {
            holder.placesName.setMaxLines(1);
            holder.placesName.setEllipsize(TextUtils.TruncateAt.END);
        }
        holder.placesName.setTextColor(PlacesAutoCompleteView.getTextSuggestViewFontColor());
        holder.placesName.setText(places);
        return view;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        Filter myPlacesFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                if(BUILD_DEBUG) Log.d(TAG, "PlacesAutoCompleteView, AutocompleteAdapter performFiltering");
                url = PlacesAutoCompleteView.getGeoCodingUrl();
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    try {
                        String term = constraint.toString();
                        if(BUILD_DEBUG) Log.d(TAG, "PlacesAutoCompleteView, text entered: " + term);
                        mPlacesCustomInfo = new PlacesSearchAsync(url + term).check();

                        filterResults.values = mPlacesCustomInfo;
                        filterResults.count = mPlacesCustomInfo.size();
                    } catch (Exception e) {
                        if(BUILD_DEBUG) Log.e(TAG, "PlacesAutoCompleteView Exception " + e);
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if(BUILD_DEBUG) Log.d(TAG, "PlacesAutoCompleteView, AutocompleteAdapter publishResults");
                if (results != null && results.count > 0) {
                    if(BUILD_DEBUG) Log.d(TAG, "PlacesAutoCompleteView, AutocompleteAdapter publishResults notifyDataSetChanged");
                    notifyDataSetChanged();
                } else {
                    if(BUILD_DEBUG) Log.d(TAG, "PlacesAutoCompleteView, AutocompleteAdapter publishResults notifyDataSetInvalidated");
                    notifyDataSetInvalidated();
                }
            }
        };

        return myPlacesFilter;
    }
}