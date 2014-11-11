# Android PagerSlidingTabStrip (default Material Design)

Interactive paging indicator widget, compatible with the `ViewPager` from the
Android Support Library.

![PagerSlidingTabStrip Sample Material](https://raw.githubusercontent.com/jpardogo/PagerSlidingTabStrip/master/art/material_tabs.gif) ------
![PagerSlidingTabStrip Sample Material](https://raw.githubusercontent.com/jpardogo/PagerSlidingTabStrip/master/art/material_tabs_middle.gif)

# Usage

*For a working implementation of this project see the `sample/` folder.*

  1. Include the following dependency to your gradle file.

```groovy
    compile 'com.jpardogo.materialtabstrip:library:1.0.3'
```
  Or add the library as a project. I tried to send a pull request, but looks like the original
  developer doesn't maintain it anymore.

  2. Include the PagerSlidingTabStrip widget in your layout. This should usually be placed
     above the `ViewPager` it represents.

        <com.astuetz.PagerSlidingTabStrip
            android:id="@+id/tabs"
            android:layout_width="match_parent"
            android:layout_height="?attr/actionBarSize"
            android:background="?attr/colorPrimary" />

  3. In your `onCreate` method (or `onCreateView` for a fragment), bind the
     widget to the `ViewPager`.

         // Initialize the ViewPager and set an adapter
         ViewPager pager = (ViewPager) findViewById(R.id.pager);
         pager.setAdapter(new TestAdapter(getSupportFragmentManager()));

         // Bind the tabs to the ViewPager
         PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
         tabs.setViewPager(pager);

  5. If your adapter implements the interface `CustomTabProvider` you can past you custom tab view/s.
     In case the the view returned contains the id `R.id.tab_title`, this view should be a `Textview`  and
     will be used to placed the title.

     Otherwise the default tab will be use (That's a TextView with id `R.id.tab_title`)

  4. *(Optional)* If you use an `OnPageChangeListener` with your view pager
     you should set it in the widget rather than on the pager directly.

         // continued from above
         tabs.setOnPageChangeListener(mPageChangeListener);

# Customization

To not just look like another Play Store styled app, go and adjust these values to match
your brand:

 * `pstsIndicatorColor` Color of the sliding indicator
 * `pstsUnderlineColor` Color of the full-width line on the bottom of the view
 * `pstsDividerColor` Color of the dividers between tabs
 * `pstsIndicatorHeight`Height of the sliding indicator
 * `pstsUnderlineHeight` Height of the full-width line on the bottom of the view
 * `pstsDividerPadding` Top and bottom padding of the dividers
 * `pstsTabPaddingLeftRight` Left and right padding of each tab
 * `pstsScrollOffset` Scroll offset of the selected tab
 * `pstsTabBackground` Background drawable of each tab, should be a StateListDrawable
 * `pstsShouldExpand` If set to true, each tab is given the same weight, default false
 * `pstsTextAllCaps` If true, all tab titles will be upper case, default true
 * `pstsPaddingMiddle` If true, the tabs start at the middle of the view (Like Newsstand google app)
 * `pstsTextStyle` Set the text style, default bold
 * `pstsTextSelectedStyle` Set the text style of the selected tab, default bold
 * `pstsTextAlpha` Set the text alpha transparency, default 0.5
 * `pstsTextSelectedAlpha` Set the text alpha transparency of the selected tab, default 1

If you set any padding left/right to the `com.astuetz.PagerSlidingTabStrip`, the biggest will be apply to both sides.

*Almost all attributes but have their respective getters and setters to change them at runtime*

# Developed By

 * Andreas Stuetz - <andreas.stuetz@gmail.com>

### Credits

 * [Kirill Grouchnikov](https://plus.google.com/108761828584265913206/posts) - Author of [an explanation post on Google+](https://plus.google.com/108761828584265913206/posts/Cwk7joBV3AC)


# License

    Copyright 2013 Andreas Stuetz

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
