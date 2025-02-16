package es.unizar.eina.notepadv3;

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
public class DeleteCategoryTest {
    @Rule
    public ActivityTestRule<Notepadv3> activityRule = new ActivityTestRule<>(Notepadv3.class);

    NotesDbAdapter mDbHelper_test;
    private long rowid;

    @Before
    public void setUp() {
        mDbHelper_test = activityRule.getActivity().getmDbHelper();
    }

    // Clase 1
    @Test
    public void test_DeleteCategoryValida() {
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", new Date().getTime(), new Date().getTime());
        mDbHelper_test.deleteCategory("cat");
        Cursor cr = mDbHelper_test.fetchNote(rowid);
        String cat = cr.getString(cr.getColumnIndex("category"));
        assertEquals("",cat);
    }

    // Clase 2
    @Test(expected = SQLException.class)
    public void test_DeleteCategoryInvalida() {
        mDbHelper_test.deleteCategory(null);
    }
}