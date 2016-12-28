package com.github.sarweshkumar47.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

import com.github.sarweshkumar47.placesautocompleteview.Models.PlacesCustomInfo;
import com.github.sarweshkumar47.placesautocompleteview.PlacesAutoCompleteView;

public class ToolbarDemoActivity extends AppCompatActivity {

    private PlacesAutoCompleteView placesAutoCompleteView;
    private PlacesCustomInfo placesCustomInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toolbar_demo_activity);

        Toolbar tb = (Toolbar) findViewById(R.id.toolbar_search);
        setSupportActionBar(tb);

        placesAutoCompleteView = (PlacesAutoCompleteView) findViewById(R.id.toolbarPlacesSearch);

        placesAutoCompleteView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                placesCustomInfo = (PlacesCustomInfo) parent.getItemAtPosition(position);
                placesAutoCompleteView.setText(placesCustomInfo.getPlaceName());
            }
        });

    }

}
