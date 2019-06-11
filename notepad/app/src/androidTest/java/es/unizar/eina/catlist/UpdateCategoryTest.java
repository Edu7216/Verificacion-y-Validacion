package es.unizar.eina.catlist;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class UpdateCategoryTest {
    @Rule
    public ActivityTestRule<CatList> activityRule = new ActivityTestRule<>(CatList.class);

    CatDbAdapter mDbHelper_test;
    private long rowid;

    @Before
    public void setUp() {
        mDbHelper_test = activityRule.getActivity().getmDbHelper();
    }

    // Clases 1, 3, 4, 5
    @Test
    public void test_UpdateCategoryValida() {
        rowid = mDbHelper_test.createCategory("titulo");
        boolean res = mDbHelper_test.updateCategory(rowid, "tituloU");
        assertTrue(res);
    }

    // Clase 2
    @Test
    public void test_UpdateCategoryInvalida4() {
        boolean res = mDbHelper_test.updateCategory(-8, "tituloU");
        assertFalse(res);
    }

    // Clase 2
    @Test
    public void test_UpdateCategoryInvalida1() {
        boolean res = mDbHelper_test.updateCategory(0, "tituloU");
        assertFalse(res);
    }

    // Clase 6
    @Test
    public void test_UpdateCategoryInvalida2() {
        rowid = mDbHelper_test.createCategory("titulo");
        boolean res = mDbHelper_test.updateCategory(rowid, null);
        assertFalse(res);
    }

    // Clase 7
    @Test
    public void test_UpdateCategoryInvalida3() {
        rowid = mDbHelper_test.createCategory("titulo");
        boolean res = mDbHelper_test.updateCategory(rowid, "");
        assertFalse(res);
    }

    // Clase 8
    @Test
    public void test_UpdateCategoryInvalida5() {
        rowid = mDbHelper_test.createCategory("titulo");
        boolean res = mDbHelper_test.updateCategory(rowid, "'"
        );
        assertFalse(res);
    }

    @After
    public void tearDown() {
        mDbHelper_test.deleteCategory(rowid);
    }
}