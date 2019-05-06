package es.unizar.eina.notepadv3;

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
public class CreateNoteTest {
    @Rule
    public ActivityTestRule<Notepadv3> activityRule = new ActivityTestRule<>(Notepadv3.class);

    NotesDbAdapter mDbHelper_test;
    private long rowid;

    @Before
    public void setUp() {
        mDbHelper_test = activityRule.getActivity().getmDbHelper();
    }

    @Test
    public void test_CreateNoteValida() {
        int numNotas = mDbHelper_test.getNotesNumber();
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", new Date().getTime(), new Date().getTime());
        assertEquals(numNotas + 1, mDbHelper_test.getNotesNumber());
    }

    @Test
    public void test_CreateNoteInvalida1() {
        rowid = mDbHelper_test.createNote(null, "cuerpo", "cat", new Date().getTime(), new Date().getTime());
        assertEquals(-1, rowid);
    }

    @Test
    public void test_CreateNoteInvalida2() {
        rowid = mDbHelper_test.createNote("titulo", null, "cat", new Date().getTime(), new Date().getTime());
        assertEquals(-1, rowid);
    }

    @Test
    public void test_CreateNoteInvalida3() {
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", null, new Date().getTime(), new Date().getTime());
        assertEquals(-1, rowid);
    }

    @After
    public void tearDown() {
        mDbHelper_test.deleteNote(rowid);
    }
}