# PlacesAutoCompleteView
An AutocompleteTextView that interacts with the _Geocoding API_ of [LocationIQ.org](http://locationiq.org/) to provide places results (based on [OpenStreetMap](https://www.openstreetmap.org) data)

# Demo
<p align="center" >
<img src="https://github.com/sarweshkumar47/PlacesAutoCompleteView/blob/master/Images/demo.gif" alt="demo" width="220" align="left" />
<img src="https://github.com/sarweshkumar47/PlacesAutoCompleteView/blob/master/Images/mapdemo.gif" alt="map_demo" width="220"/>
<img src="https://github.com/sarweshkumar47/PlacesAutoCompleteView/blob/master/Images/toolbar_demo.gif" alt="toolbar_demo" width="220" align="right" /> </p>

# Setup
### Gradle
    dependencies {
        compile "com.github.sarweshkumar47:placesautocompleteview:1.0.0"
    }
  
### Maven
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
 
# Advanced usage

__XML Properties__

Customize some of the functionalities of the autocomplete view

| XML Property | Description |
|--------|---------|
| language  | Preferred language order for showing search results, either uses standard rfc2616 accept-language string or a simple comma separated list of language codes (click here for [more info](http://locationiq.org/#docs)). Default: en-US |
| geocoding_api_key | Developer token to access the services |
| autoTextThreshold | After entering these many characters request should be sent to the server. Default: 3 |
| autoTextDelay | Delay after which request should be sent to the server (in milliseconds). Default: 250 millisec |
| textSuggestViewTextSize | Text size of textview in the dropdown adapter (in sp). Default: 16sp |
| textSuggestSingleLineEnabled | Constrains the text to a single horizontally scrolling line instead of letting it wrap onto multiple lines. Default: false |
| textSuggestViewFontColor | Color for textview in the dropdown adapter. Default: Black |
| textSuggestViewBackground | Color for background in the dropdown adapter. Default: White |
| clearButtonDrawable | Drawable image for clear icon 'X' (Useful if you want to add the autocompleteview to ToolBar)|

# Places details
Library provides these information about the place you select in the view
* Display name
* Latitude and longitude of the place
* Bounding box (Minimum and maximum coordinates) of the place

To get above details, define an object of type **PlacesCustomInfo** and apply the following methods

    * getPlaceName() // Returns String
    * getLatitude()  // Returns double
    * getLongitude() // Returns double
    * getGeoBoundingBox() // Returns ArrayList<Double> - minLatitude, maxLatitude, minLongitude, maxLongitude
    
For more info, please check the sample apps in the repo

## Limitations

Structured requests are not supported yet

_**Note:** issues can be submitted via [github issues](https://github.com/sarweshkumar47/PlacesAutoCompleteView/issues/new)_

# License
    MIT License

    Copyright (c) 2016 Sarweshkumar C R

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.


