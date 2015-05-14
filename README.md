# MaterialTabs

##Getting started

### Sample

You can find a sample app showing what this library can do on the Google Play Store.

<a href="https://play.google.com/store/apps/details?id=io.karim.materialtabs.sample">
  <img alt="Material Tabs Demo on Google Play"
         src="http://developer.android.com/images/brand/en_generic_rgb_wo_60.png" />
</a>

### Download

Download the [latest AAR](http://search.maven.org/remotecontent?filepath=io/karim/materialtabs/2.0.0/materialtabs-2.0.0.aar) or add the following dependency in your **build.gradle** file:

```groovy
	dependencies {
	    compile 'io.karim:materialtabs:2.0.0
	}
```

### Usage

1. Add the MaterialTabs widget (io.karim.MaterialTabs) in your `layout.xml` file:

	```
	<io.karim.MaterialTabs
    	   android:id="@+id/material_tabs"
    	   android:layout_width="match_parent"
    	   android:layout_height="48dp"
    	   android:background="?attr/colorPrimary"
    	   app:mtSameWeightTabs="true"
    	   app:mtPaddingMiddle="false" />
	```
Normally, this should go below a Toolbar (android.support.v7.widget.Toolbar) and above a ViewPager (android.support.v4.view.ViewPager).
Take a look at [this file] (https://github.com/pizza/MaterialTabs/blob/master/sample/res/layout/activity_tabs.xml) for a better understanding.

2.  In your onCreate method (or onCreateView for a fragment), bind the widget to the ViewPager:

	```
 // Initialize the ViewPager and set an adapter
 ViewPager pager = (ViewPager) findViewById(R.id.pager);
 pager.setAdapter(new SamplePagerAdapter(getSupportFragmentManager()));

 // Bind the tabs to the ViewPager
 MaterialTabs tabs = (MaterialTabs) findViewById(R.id.tabs);
 tabs.setViewPager(pager);
```

3. Create a new class extending FragmentPagerAdapter (I called it `SamplePagerAdapter`). Take a look at [this file] (https://github.com/pizza/MaterialTabs/blob/master/sample/src/io/karim/materialtabs/sample/TabsActivity.java#L126) for a better example.

## Credits
This library is based on the great [PagerSlidingTabStrip](https://github.com/jpardogo/PagerSlidingTabStrip) library by [jpardogo](https://github.com/jpardogo) and [astuetz](https://github.com/astuetz) and on the great [material-ripple](https://github.com/balysv/material-ripple) library by [balysv](https://github.com/balysv).
