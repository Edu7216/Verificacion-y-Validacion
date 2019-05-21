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

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class Notepadv3Test {
    @Rule
    public ActivityTestRule<Notepadv3> activityRule = new ActivityTestRule<>(Notepadv3.class);

    NotesDbAdapter mDbHelper_test;
    private long rowid;

    @Before
    public void setUp() {
        mDbHelper_test = activityRule.getActivity().getmDbHelper();
    }

    @Test
    public void test_numNotas() {
        int numNotas = mDbHelper_test.getNotesNumber();
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", new Date().getTime(), new Date().getTime());
        assertEquals(numNotas + 1, mDbHelper_test.getNotesNumber());
    }

    @Test
    public void test_titulo() {
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", new Date().getTime(), new Date().getTime());
        String titulo_aux = mDbHelper_test.getTitle(rowid);
        assertEquals("titulo", titulo_aux);
    }

    @Test
    public void test_cuerpo() {
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", new Date().getTime(), new Date().getTime());
        String cuerpo_aux = mDbHelper_test.getBody(rowid);
        assertEquals("cuerpo", cuerpo_aux);
    }

    @Test
    public void test_categoria() {
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", new Date().getTime(), new Date().getTime());
        String cat_aux = mDbHelper_test.getCategory(rowid);
        assertEquals("cat", cat_aux);
    }

    @Test
    public void test_fechaAct() {
        long fAct = new Date().getTime();
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", fAct, new Date().getTime());
        long fAct_aux = mDbHelper_test.getStartDate(rowid);
        assertEquals(fAct, fAct_aux);
    }

    @Test
    public void test_fechaCad() {
        long fCad = new Date().getTime();
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", new Date().getTime(), fCad);
        long fCad_aux = mDbHelper_test.getEndDate(rowid);
        assertEquals(fCad, fCad_aux);
    }

    @After
    public void tearDown() {
        mDbHelper_test.deleteNote(rowid);
    }
}