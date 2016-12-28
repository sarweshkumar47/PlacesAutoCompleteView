# PlacesAutoCompleteView
An AutocompleteTextView that interacts with the _Geocoding API_ of [LocationIQ.org](http://locationiq.org/) to provide places results (based on [OpenStreetMap](https://www.openstreetmap.org) data)

# Demo
<p align="center" >
<img src="https://github.com/sarweshkumar47/PlacesAutoCompleteView/blob/master/Images/demo.gif" alt="demo" width="220" align="left" />
<img src="https://github.com/sarweshkumar47/PlacesAutoCompleteView/blob/master/Images/mapdemo.gif" alt="map_demo" width="220"/>
<img src="https://github.com/sarweshkumar47/PlacesAutoCompleteView/blob/master/Images/toolbar_demo.gif" alt="toolbar_demo" width="220" align="right" /> </p>

# Setup
## Gradle
Add the following to your project's build.gradle:

    repositories {
        maven { url 'http://dl.bintray.com/sarweshkumar/Maven' }
    }
    
Add the following to your module's build.gradle:

    dependencies {
        compile "com.github.sarweshkumar47:placesautocompleteview:1.0.0"
    }
