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
public class CreateCategoryTest {
    @Rule
    public ActivityTestRule<CatList> activityRule = new ActivityTestRule<>(CatList.class);

    CatDbAdapter mDbHelper_test;
    private long rowid;

    @Before
    public void setUp() {
        mDbHelper_test = activityRule.getActivity().getmDbHelper();
    }

    // Clases 1, 2
    @Test
    public void test_CreateCategoryValida() {
        int numCategorias = mDbHelper_test.getCategoriesNumber();
        rowid = mDbHelper_test.createCategory("titulo");
        assertEquals(numCategorias + 1, mDbHelper_test.getCategoriesNumber());
    }

    // Clase 3
    @Test
    public void test_CreateCategoryInvalida1() {
        rowid = mDbHelper_test.createCategory(null);
        assertEquals(-1, rowid);
    }

    // Clase 4
    @Test
    public void test_CreateCategoryInvalida2() {
        rowid = mDbHelper_test.createCategory("");
        assertEquals(-1, rowid);
    }

    @After
    public void tearDown() {
        mDbHelper_test.deleteCategory(rowid);
    }
}