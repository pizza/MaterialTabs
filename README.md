# MaterialTabs

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-pizza%2FMaterialTabs-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/1874)

An easy-to-integrate tab bar for Android that completely respects the [Material Design guidelines] (http://www.google.com/design/spec/components/tabs.html) and **supports all versions of Android since API 9**!

If you think that this library does not fully respect the Material Design guidelines, file an issue, send a pull request or reach out to me! The goal of this library is to be 100% MaterialDesign-compliant.

<img alt="Animated screenshot" src="http://imgur.com/1CnH2U1.gif" />

## Sample

You can find a sample app showing what this library can do on the Google Play Store.
**This sample can also generate the XML code you need instantly and export it!** You can then copy paste it in your corresponding layout XML file. Done!

<a href="https://play.google.com/store/apps/details?id=io.karim.materialtabs.sample">
  <img alt="Material Tabs Demo on Google Play"
         src="http://developer.android.com/images/brand/en_generic_rgb_wo_60.png" />
</a>

## Download

Download the [latest AAR](http://search.maven.org/remotecontent?filepath=io/karim/materialtabs/2.0.3/materialtabs-2.0.3.aar) or add the following dependency in your **build.gradle** file:

```groovy
	dependencies {
	    compile 'io.karim:materialtabs:2.0.3'
	}
```

## Usage

1. Add the MaterialTabs widget (io.karim.MaterialTabs) in your `layout.xml` file:

	```xml
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

	```java
 // Initialize the ViewPager and set an adapter
 ViewPager pager = (ViewPager) findViewById(R.id.pager);
 pager.setAdapter(new SamplePagerAdapter(getSupportFragmentManager()));

 // Bind the tabs to the ViewPager
 MaterialTabs tabs = (MaterialTabs) findViewById(R.id.tabs);
 tabs.setViewPager(pager);
```

3. Create a new class extending FragmentPagerAdapter.

	If you're looking for tabs with text, take a look at [this file] (https://github.com/pizza/MaterialTabs/blob/master/sample/src/io/karim/materialtabs/sample/TabsActivity.java#L126) for a better example.

	If you're looking for tabs with icons instead of text, take a look at [this example](https://github.com/pizza/MaterialTabs/blob/master/sample/src/io/karim/materialtabs/sample/MainActivity.java#L397) instead. Specifically, make sure that your class implements `MaterialTabs.CustomtabProvider` and override `getCustomtabView(...)`.

	If you're looking for tabs with more than just icons or just text, create your own custom views in `getCustomtabView(...)`.

## Customization
There are many attributes that you can override in the XML layout.
Here is a table of these attributes, their descriptions and their default value:

| Attribute  | Description |
| ------------- | ------------- | 
| android:textColor | Color of text in non-selected tabs | 
| app:mtIndicatorColor  | Color of the sliding indicator  | 
| app:mtUnderlineColor | Color of the full-width line on the bottom of the view  | 
| app:mtIndicatorHeight | Height of the sliding indicator  | 
| app:mtUnderlineHeight | Height of the full-width line on the bottom of the view  | 
| app:mtTabPaddingLeftRight | Left and right padding of each tab  | 
| app:mtSameWeightTabs | If set to true, each tab is given the same weight  | 
| app:mtTextAllCaps | If true, all tab titles will be upper case  | 
| app:mtPaddingMiddle | If true, the tabs start at the middle of the view  | 
| app:mtTextColorSelected | Color of text in selected tab  | 
| app:mtTextUnselectedStyle | Style of text in unselected tab  | 
| app:mtTextSelectedStyle | Style of text in selected tab  | 
| app:mtMrlRippleColor | Color of the ripple  | 
| app:mtMrlRippleHighlightColor | Color of the background while the ripple is undergoing an animation  | 
| app:mtMrlRippleDiameter | Radius of starting ripple  | 
| app:mtMrlRippleOverlay | If true, ripple is drawn in foreground of view. Otherwise, it will drawn in the background  | 
| app:mtMrlRippleAlpha | Level of transparency (alpha) of the ripple  | 
| app:mtMrlRippleDuration | Duration of the ripple animation  | 
| app:mtMrlRippleFadeDuration | Duration of fade out effect on ripple  | 
| app:mtMrlRippleDelayClick | If true, delays calls to OnClickListeners until ripple effect ends. In that case, the indicator line's  move to the clicked tab will also be delayed  | 
| app:mtMrlRipplePersistent | If true, the ripple background color persists after animation, until setRadius(0) is called  | 
| app:mtMrlRippleInAdapter | if true, MaterialRippleLayout will be optimized for use in AdapterViews  | 
| app:mtMrlRippleRoundedCorners | Radius of corners of the ripple. Note: it uses software rendering pipeline for API 17 and  below  | 

Don't forget to add `xmlns:app="http://schemas.android.com/apk/res-auto"` to the root item in your layout.

If you would like to use a custom font in the tabs' title text, you can do so in your Java code by adding the last two lines just after binding the tabs to the ViewPager:

```java
 // Initialize the ViewPager and set an adapter
 ViewPager pager = (ViewPager) findViewById(R.id.pager);
 pager.setAdapter(new SamplePagerAdapter(getSupportFragmentManager()));

 // Bind the tabs to the ViewPager
 MaterialTabs tabs = (MaterialTabs) findViewById(R.id.tabs);
 tabs.setViewPager(pager);
 
 // Set custom font/typeface to text in tabs' title
 Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
 tabs.setTypeface(typeface, Typeface.BOLD);
```

For this to work, make sure to add your font file (in this case, `Roboto-Thin.ttf`) in the `fonts` folder under `assets`.

## Contribution
If you would like to contribute code you can do so through GitHub by forking the repository and sending a pull request.

## Credits
This library is based on the great [PagerSlidingTabStrip](https://github.com/jpardogo/PagerSlidingTabStrip) library by [jpardogo](https://github.com/jpardogo) and [astuetz](https://github.com/astuetz) and on the great [material-ripple](https://github.com/balysv/material-ripple) library by [balysv](https://github.com/balysv).
