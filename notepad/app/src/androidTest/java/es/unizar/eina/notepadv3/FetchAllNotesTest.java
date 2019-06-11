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

    // Clase 1
    @Test
    public void test_BusquedaValida1() {
        mDbHelper_test.limpiarBD();

        long std = new Date().getTime();
        long edd = new Date().getTime();
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", std, edd);

        Cursor cr = mDbHelper_test.fetchAllNotes(true,"","",0);

        cr.moveToFirst();
        String cmpn1 = cr.getString(cr.getColumnIndex("title"));
        String cmpn2 = cr.getString(cr.getColumnIndex("body"));
        String cmpn3 = cr.getString(cr.getColumnIndex("category"));

        assertEquals("titulo",cmpn1);
        assertEquals("cuerpo",cmpn2);
        assertEquals("cat",cmpn3);
    }

    // Clase 2
    @Test
    public void test_BusquedaValida2() {
        mDbHelper_test.limpiarBD();

        long std = new Date().getTime();
        long edd = new Date().getTime();
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", std, edd);

        Cursor cr = mDbHelper_test.fetchAllNotes(false,"","",0);

        cr.moveToFirst();
        String cmpn1 = cr.getString(cr.getColumnIndex("title"));
        String cmpn2 = cr.getString(cr.getColumnIndex("body"));
        String cmpn3 = cr.getString(cr.getColumnIndex("category"));

        assertEquals("titulo",cmpn1);
        assertEquals("cuerpo",cmpn2);
        assertEquals("cat",cmpn3);
    }

    // Clase 3, 4
    @Test
    public void test_BusquedaValida3() {
        mDbHelper_test.limpiarBD();

        long std = new Date().getTime();
        long edd = new Date().getTime();
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", std, edd);

        Cursor cr = mDbHelper_test.fetchAllNotes(false,"cat","",0);

        cr.moveToFirst();
        String cmpn1 = cr.getString(cr.getColumnIndex("title"));
        String cmpn2 = cr.getString(cr.getColumnIndex("body"));
        String cmpn3 = cr.getString(cr.getColumnIndex("category"));

        assertEquals("titulo",cmpn1);
        assertEquals("cuerpo",cmpn2);
        assertEquals("cat",cmpn3);
    }

    // Clase 2, 5
    @Test
    public void test_BusquedaValida4() {
        mDbHelper_test.limpiarBD();

        long crnt = new Date().getTime();
        long std = new Date().getTime() + 10;
        long edd = new Date().getTime() + 20;
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", std, edd);

        Cursor cr = mDbHelper_test.fetchAllNotes(false,"","Planned Notes",crnt);

        cr.moveToFirst();
        String cmpn1 = cr.getString(cr.getColumnIndex("title"));
        String cmpn2 = cr.getString(cr.getColumnIndex("body"));
        String cmpn3 = cr.getString(cr.getColumnIndex("category"));

        assertEquals("titulo",cmpn1);
        assertEquals("cuerpo",cmpn2);
        assertEquals("cat",cmpn3);
    }

    // Clase 1, 5
    @Test
    public void test_BusquedaValida5() {
        mDbHelper_test.limpiarBD();

        long crnt = new Date().getTime();
        long std = new Date().getTime() + 10;
        long edd = new Date().getTime() + 20;
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", std, edd);

        Cursor cr = mDbHelper_test.fetchAllNotes(true,"","Planned Notes",crnt);

        cr.moveToFirst();
        String cmpn1 = cr.getString(cr.getColumnIndex("title"));
        String cmpn2 = cr.getString(cr.getColumnIndex("body"));
        String cmpn3 = cr.getString(cr.getColumnIndex("category"));

        assertEquals("titulo",cmpn1);
        assertEquals("cuerpo",cmpn2);
        assertEquals("cat",cmpn3);
    }

    // Clase 3, 4, 5
    @Test
    public void test_BusquedaValida6() {
        mDbHelper_test.limpiarBD();

        long crnt = new Date().getTime();
        long std = new Date().getTime() + 10;
        long edd = new Date().getTime() + 20;
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", std, edd);

        Cursor cr = mDbHelper_test.fetchAllNotes(false,"cat","Planned Notes",crnt);

        cr.moveToFirst();
        String cmpn1 = cr.getString(cr.getColumnIndex("title"));
        String cmpn2 = cr.getString(cr.getColumnIndex("body"));
        String cmpn3 = cr.getString(cr.getColumnIndex("category"));

        assertEquals("titulo",cmpn1);
        assertEquals("cuerpo",cmpn2);
        assertEquals("cat",cmpn3);
    }

    // Clase 1, 6
    @Test
    public void test_BusquedaValida7() {
        mDbHelper_test.limpiarBD();

        long std = new Date().getTime();
        long crnt = new Date().getTime() + 10;
        long edd = new Date().getTime() + 20;
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", std, edd);

        Cursor cr = mDbHelper_test.fetchAllNotes(true,"","Active Notes",crnt);

        cr.moveToFirst();
        String cmpn1 = cr.getString(cr.getColumnIndex("title"));
        String cmpn2 = cr.getString(cr.getColumnIndex("body"));
        String cmpn3 = cr.getString(cr.getColumnIndex("category"));

        assertEquals("titulo",cmpn1);
        assertEquals("cuerpo",cmpn2);
        assertEquals("cat",cmpn3);
    }

    // Clase 2, 6
    @Test
    public void test_BusquedaValida8() {
        mDbHelper_test.limpiarBD();

        long std = new Date().getTime();
        long crnt = new Date().getTime() + 10;
        long edd = new Date().getTime() + 20;
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", std, edd);

        Cursor cr = mDbHelper_test.fetchAllNotes(false,"","Active Notes",crnt);

        cr.moveToFirst();
        String cmpn1 = cr.getString(cr.getColumnIndex("title"));
        String cmpn2 = cr.getString(cr.getColumnIndex("body"));
        String cmpn3 = cr.getString(cr.getColumnIndex("category"));

        assertEquals("titulo",cmpn1);
        assertEquals("cuerpo",cmpn2);
        assertEquals("cat",cmpn3);
    }

    // Clase 3, 4, 6
    @Test
    public void test_BusquedaValida9() {
        mDbHelper_test.limpiarBD();

        long std = new Date().getTime();
        long crnt = new Date().getTime() + 10;
        long edd = new Date().getTime() + 20;
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", std, edd);

        Cursor cr = mDbHelper_test.fetchAllNotes(false,"cat","Active Notes",crnt);

        cr.moveToFirst();
        String cmpn1 = cr.getString(cr.getColumnIndex("title"));
        String cmpn2 = cr.getString(cr.getColumnIndex("body"));
        String cmpn3 = cr.getString(cr.getColumnIndex("category"));

        assertEquals("titulo",cmpn1);
        assertEquals("cuerpo",cmpn2);
        assertEquals("cat",cmpn3);
    }

    // Clase 1, 7
    @Test
    public void test_BusquedaValida10() {
        mDbHelper_test.limpiarBD();

        long std = new Date().getTime();
        long edd = new Date().getTime() + 10;
        long crnt = new Date().getTime() + 20;
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", std, edd);

        Cursor cr = mDbHelper_test.fetchAllNotes(true,"","Expired Notes",crnt);

        cr.moveToFirst();
        String cmpn1 = cr.getString(cr.getColumnIndex("title"));
        String cmpn2 = cr.getString(cr.getColumnIndex("body"));
        String cmpn3 = cr.getString(cr.getColumnIndex("category"));

        assertEquals("titulo",cmpn1);
        assertEquals("cuerpo",cmpn2);
        assertEquals("cat",cmpn3);
    }

    // Clase 2, 7
    @Test
    public void test_BusquedaValida11() {
        mDbHelper_test.limpiarBD();

        long std = new Date().getTime();
        long edd = new Date().getTime() + 10;
        long crnt = new Date().getTime() + 20;
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", std, edd);

        Cursor cr = mDbHelper_test.fetchAllNotes(false,"","Expired Notes",crnt);

        cr.moveToFirst();
        String cmpn1 = cr.getString(cr.getColumnIndex("title"));
        String cmpn2 = cr.getString(cr.getColumnIndex("body"));
        String cmpn3 = cr.getString(cr.getColumnIndex("category"));

        assertEquals("titulo",cmpn1);
        assertEquals("cuerpo",cmpn2);
        assertEquals("cat",cmpn3);
    }

    // Clase 3, 4, 7
    @Test
    public void test_BusquedaValida12() {
        mDbHelper_test.limpiarBD();

        long std = new Date().getTime();
        long edd = new Date().getTime() + 10;
        long crnt = new Date().getTime() + 20;
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", std, edd);

        Cursor cr = mDbHelper_test.fetchAllNotes(false,"cat","Expired Notes",crnt);

        cr.moveToFirst();
        String cmpn1 = cr.getString(cr.getColumnIndex("title"));
        String cmpn2 = cr.getString(cr.getColumnIndex("body"));
        String cmpn3 = cr.getString(cr.getColumnIndex("category"));

        assertEquals("titulo",cmpn1);
        assertEquals("cuerpo",cmpn2);
        assertEquals("cat",cmpn3);
    }

    @After
    public void tearDown() {
        mDbHelper_test.limpiarBD();
    }
}