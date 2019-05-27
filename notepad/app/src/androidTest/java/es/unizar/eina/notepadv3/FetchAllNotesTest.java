package es.unizar.eina.notepadv3;

import android.database.Cursor;

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
public class FetchAllNotesTest {
    @Rule
    public ActivityTestRule<Notepadv3> activityRule = new ActivityTestRule<>(Notepadv3.class);

    NotesDbAdapter mDbHelper_test;
    private long rowid;

    @Before
    public void setUp() {
        mDbHelper_test = activityRule.getActivity().getmDbHelper();
    }

    @Test
    public void test_BusquedaValida1() {
        long std = new Date().getTime();
        long edd = new Date().getTime();
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", std, edd);
        Cursor cr = mDbHelper_test.fetchAllNotes(true,"","",0);
        cr.moveToFirst();
        String cmpn1 = cr.getString(cr.getColumnIndex("title"));
        String cmpn2 = cr.getString(cr.getColumnIndex("body"));
        String cmpn3 = cr.getString(cr.getColumnIndex("category"));
        String cmpn4 = cr.getString(cr.getColumnIndex("startDate"));
        String cmpn5 = cr.getString(cr.getColumnIndex("endDate"));
        assertEquals("titulo",cmpn1);
        assertEquals("cuerpo",cmpn2);
        assertEquals("cat",cmpn3);
        assertEquals(String.valueOf(std),cmpn4);
        assertEquals(String.valueOf(edd),cmpn5);
    }
}