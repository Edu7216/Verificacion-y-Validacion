package es.unizar.eina.notepadv3;

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
public class DeleteNoteTest {
    @Rule
    public ActivityTestRule<Notepadv3> activityRule = new ActivityTestRule<>(Notepadv3.class);

    private NotesDbAdapter mDbHelper_test;
    private long rowid;

    @Before
    public void setUp() {
        mDbHelper_test = activityRule.getActivity().getmDbHelper();
    }

    // Clase 1
    @Test
    public void test_DeleteNoteValida() {
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", new Date().getTime(), new Date().getTime());
        boolean del = mDbHelper_test.deleteNote(rowid);
        assertTrue(del);
    }

    // Clase 2
    @Test
    public void test_DeleteNoteInvalida1() {
        boolean del = mDbHelper_test.deleteNote(0);
        assertFalse(del);
    }

    // Clase 2
    @Test
    public void test_DeleteNoteInvalida2() {
        boolean del = mDbHelper_test.deleteNote(-5);
        assertFalse(del);
    }

    @After
    public void tearDown() {
        mDbHelper_test.deleteNote(rowid);
    }
}