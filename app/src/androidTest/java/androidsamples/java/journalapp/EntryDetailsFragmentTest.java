package androidsamples.java.journalapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.accessibility.AccessibilityChecks;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented tests for the {@link EntryDetailsFragment}.
 */
@RunWith(AndroidJUnit4.class)
public class EntryDetailsFragmentTest {

  @Rule
  public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

  @BeforeClass
  public static void enableAccessibilityChecks() {
    AccessibilityChecks.enable();
  }

  @Test
  public void btnAddEntryTest() {
    onView(withId(R.id.btn_add_entry)).perform(click());
    onView(withId(R.id.record_entry)).check(matches(withText("Record Entry")));
  }
  @Test
  public void btnInfoTest() {
    onView(withId(R.id.info)).perform(click());
    onView(withId(R.id.infoHeader)).check(matches(withText("Application Information")));
  }

  @Test
  public void defaultTitleTest() {
    //This test will pass only if their is not any previous entry
    onView(withId(R.id.btn_add_entry)).perform(click());
    onView(withId(R.id.btn_save)).perform(click());
    onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    onView(withId(R.id.edit_title)).check(matches(withText("")));
  }
  @Test
  public void updateTest() {
    //This test will pass only after running the above test
    onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    onView(withId(R.id.edit_title)).perform(typeText("All Work and No Play"));
    onView(withId(R.id.btn_save)).perform(click());
    onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    onView(withId(R.id.edit_title)).check(matches(withText("All Work and No Play")));
  }
  @Test
  public void btnShareTest() {
    //This test will pass only after running the above test
    onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    onView(withId(R.id.share)).perform(click());
  }
  @Test
  public void btnDeleteTest() {
    //This test will pass only after running the above test
    onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    onView(withId(R.id.delete)).perform(click());
  }
  @Test
  public void testNavigationToEntryListFragment() {

  }
}