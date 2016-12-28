# PlacesAutoCompleteView
An AutocompleteTextView that interacts with the _Geocoding API_ of [LocationIQ.org](http://locationiq.org/) to provide places results (based on [OpenStreetMap](https://www.openstreetmap.org) data)

# Demo
<p align="center" >
<img src="https://github.com/sarweshkumar47/PlacesAutoCompleteView/blob/master/Images/demo.gif" alt="demo" width="220" align="left" />
<img src="https://github.com/sarweshkumar47/PlacesAutoCompleteView/blob/master/Images/mapdemo.gif" alt="map_demo" width="220"/>
<img src="https://github.com/sarweshkumar47/PlacesAutoCompleteView/blob/master/Images/toolbar_demo.gif" alt="toolbar_demo" width="220" align="right" /> </p>

# Setup
## Gradle
    dependencies {
        compile "com.github.sarweshkumar47:placesautocompleteview:1.0.0"
    }
  
## Maven
       <dependency>
        <groupId>com.github.sarweshkumar47</groupId>
        <artifactId>placesautocompleteview</artifactId>
        <version>1.0.0</version>
        <type>pom</type>
       </dependency>
        
# Usage
* You'll need a developer token to access the services of LocationIQ.org. Get the token [here](http://locationiq.org/#register)
* You must include the following permission in your AndroidManifest.xml, for the View to interact with the Geocoding API

         <uses-permission android:name="android.permission.INTERNET"/>

* Add the PlacesAutocompleteTextView to your layout xml:

        <com.github.sarweshkumar47.placesautocompleteview.PlacesAutoCompleteView
            android:id="@+id/placesSearch"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:inputType="textNoSuggestions"
            custom:geocoding_api_key="<LOCATIONIQ_API_KEY>"
            
* Finally, initialize the view in java code
 
        PlacesAutoCompleteView placesAutoCompleteView = (PlacesAutoCompleteView) findViewById(R.id.placesSearch);
        placesAutoCompleteView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                placesCustomInfo = (PlacesCustomInfo) parent.getItemAtPosition(position);
                placesAutoCompleteView.setText(placesCustomInfo.getPlaceName());
            }
        });
 
