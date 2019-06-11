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
public class FetchNoteTest {
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
    public void test_BusquedaValida() {
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", new Date().getTime(), new Date().getTime());

        Cursor cr = mDbHelper_test.fetchNote(rowid);

        String cmpn1 = cr.getString(cr.getColumnIndex("title"));
        String cmpn2 = cr.getString(cr.getColumnIndex("body"));
        String cmpn3 = cr.getString(cr.getColumnIndex("category"));

        assertEquals("titulo",cmpn1);
        assertEquals("cuerpo",cmpn2);
        assertEquals("cat",cmpn3);
    }

    // Clase 2
    @Test(expected = SQLException.class)
    public void test_BusquedaInvalida1() {
        Cursor cr = mDbHelper_test.fetchNote(0);
    }

    // Clase 2
    @Test(expected = SQLException.class)
    public void test_BusquedaInvalida2() {
        Cursor cr = mDbHelper_test.fetchNote(-5);
    }

    @After
    public void tearDown() {
        mDbHelper_test.deleteNote(rowid);
    }
}