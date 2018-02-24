/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.android.teatime;

import android.support.annotation.StringRes;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.teatime.model.Tea;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * This test demos a user clicking on a GridView item in MenuActivity which opens up the
 * corresponding OrderActivity.
 * <p>
 * This test does not utilize Idling Resources yet. If idling is set in the MenuActivity,
 * then this test will fail. See the IdlingResourcesTest for an identical test that
 * takes into account Idling Resources.
 */


@RunWith(AndroidJUnit4.class)
public class MenuActivityScreenTest {

    @StringRes
    final int TEA_NAME = R.string.chamomile_tea_name;

    @Rule
    public ActivityTestRule mRule = new ActivityTestRule(MenuActivity.class);

    @Test
    public void clickGridViewItem_OpensOrderActivity() {
        final String teaName = mRule.getActivity().getApplicationContext().getString(TEA_NAME);
        onData(withTeaName(teaName))
                .inAdapterView(withId(R.id.tea_grid_view))
                .perform(click());
        onView(withId(R.id.tea_name_text_view)).check(matches(withText(TEA_NAME)));
    }

    public static Matcher<Object> withTeaName(final String name) {
        return new BoundedMatcher<Object, Tea>(Tea.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has name " + name);
            }

            @Override
            public boolean matchesSafely(Tea item) {
                return name.equals(item.getTeaName());
            }
        };
    }
}
