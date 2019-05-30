package es.unizar.eina.catlist;

import android.database.Cursor;
import android.database.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class FetchCategoryTest {
    @Rule
    public ActivityTestRule<CatList> activityRule = new ActivityTestRule<>(CatList.class);

    CatDbAdapter mDbHelper_test;
    private long rowid;

    @Before
    public void setUp() {
        mDbHelper_test = activityRule.getActivity().getmDbHelper();
    }

    @Test
    public void test_BusquedaValida() {
        rowid = mDbHelper_test.createCategory("titulo");

        Cursor cr = mDbHelper_test.fetchCategory(rowid);

        String cmpn1 = cr.getString(cr.getColumnIndex("title"));

        assertEquals("titulo",cmpn1);
    }

    @Test(expected = SQLException.class)
    public void test_BusquedaInvalida1() {
        Cursor cr = mDbHelper_test.fetchCategory(0);
    }

    @Test(expected = SQLException.class)
    public void test_BusquedaInvalida2() {
        Cursor cr = mDbHelper_test.fetchCategory(-1);
    }

    @After
    public void tearDown() {
        mDbHelper_test.deleteCategory(rowid);
    }
}