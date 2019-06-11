package es.unizar.eina.catlist;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class DeleteCategoryTest {
    @Rule
    public ActivityTestRule<CatList> activityRule = new ActivityTestRule<>(CatList.class);

    CatDbAdapter mDbHelper_test;
    private long rowid;

    @Before
    public void setUp() {
        mDbHelper_test = activityRule.getActivity().getmDbHelper();
    }

    // Clase 1
    @Test
    public void test_DeleteCategoryValida() {
        rowid = mDbHelper_test.createCategory("titulo");
        boolean res = mDbHelper_test.deleteCategory(rowid);
        assertTrue(res);
    }

    // Clase 2
    @Test
    public void test_DeleteCategoryInvalida1() {
        boolean res = mDbHelper_test.deleteCategory(0);
        assertFalse(res);
    }

    // Clase 2
    @Test
    public void test_DeleteCategoryInvalida2() {
        boolean res = mDbHelper_test.deleteCategory(-5);
        assertFalse(res);
    }

    @After
    public void tearDown() {
        mDbHelper_test.deleteCategory(rowid);
    }
}