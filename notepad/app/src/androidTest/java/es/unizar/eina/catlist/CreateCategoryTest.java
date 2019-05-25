package es.unizar.eina.catlist;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.rule.ActivityTestRule;
import es.unizar.eina.notepadv3.Notepadv3;

import static org.junit.Assert.*;

public class CreateCategoryTest {
    @Rule
    public ActivityTestRule<CatList> activityRule = new ActivityTestRule<>(CatList.class);

    CatDbAdapter mDbHelper_test;
    private long rowid;

    @Before
    public void setUp() {
        mDbHelper_test = activityRule.getActivity().getmDbHelper();
    }

    @Test
    public void test_CreateCategoryValida() {
        int numCategorias = mDbHelper_test.getCategoriesNumber();
        rowid = mDbHelper_test.createCategory("titulo");
        assertEquals(numCategorias + 1, mDbHelper_test.getCategoriesNumber());
    }

    @Test
    public void test_CreateCategoryInvalida1() {
        rowid = mDbHelper_test.createCategory(null);
        assertEquals(-1, rowid);
    }

    @Test
    public void test_CreateCategoryInvalida2() {
        rowid = mDbHelper_test.createCategory("");
        assertEquals(-1, rowid);
    }
}