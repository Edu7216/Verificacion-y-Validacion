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

    // Clases 1, 2, 3, 4, 5, 6, 7, 8, 9
    @Test
    public void test_CreateNoteValida1() {
        long startDate = new Date().getTime();
        long endDate = new Date().getTime() + 10;
        int numNotas = mDbHelper_test.getNotesNumber();
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", startDate, endDate);
        assertEquals(numNotas + 1, mDbHelper_test.getNotesNumber());
    }

    // Clases 1, 2, 3, 4, 5, 6, 7, 8, 9
    @Test
    public void test_CreateNoteValida2() {
        long startDate = new Date().getTime();
        long endDate = startDate;
        int numNotas = mDbHelper_test.getNotesNumber();
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", startDate, endDate);
        assertEquals(numNotas + 1, mDbHelper_test.getNotesNumber());
    }

    // Clase 10
    @Test
    public void test_CreateNoteInvalida1() {
        rowid = mDbHelper_test.createNote(null, "cuerpo", "cat", new Date().getTime(), new Date().getTime());
        assertEquals(-1, rowid);
    }

    // Clase 13
    @Test
    public void test_CreateNoteInvalida2() {
        rowid = mDbHelper_test.createNote("titulo", null, "cat", new Date().getTime(), new Date().getTime());
        assertEquals(-1, rowid);
    }

    // Clase 14
    @Test
    public void test_CreateNoteInvalida3() {
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", null, new Date().getTime(), new Date().getTime());
        assertEquals(-1, rowid);
    }

    // Clase 11
    @Test
    public void test_CreateNoteInvalida4() {
        rowid = mDbHelper_test.createNote("", "cuerpo", "cat", new Date().getTime(), new Date().getTime());
        assertEquals(-1, rowid);
    }

    // Clase 15
    @Test
    public void test_CreateNoteInvalida6() {
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", 0, new Date().getTime());
        assertEquals(-1, rowid);
    }

    // Clase 15
    @Test
    public void test_CreateNoteInvalida7() {
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", -10, new Date().getTime());
        assertEquals(-1, rowid);
    }

    // Clase 17
    @Test
    public void test_CreateNoteInvalida8() {
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", new Date().getTime(), 0);
        assertEquals(-1, rowid);
    }

    // Clase 17
    @Test
    public void test_CreateNoteInvalida9() {
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", new Date().getTime(), -10);
        assertEquals(-1, rowid);
    }

    // Clase 16, 18
    @Test
    public void test_CreateNoteInvalida10() {
        long endDate = new Date().getTime();
        long startDate = new Date().getTime() + 10;
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", startDate, endDate);
        assertEquals(-1, rowid);
    }

    // Clase 12
    @Test
    public void test_CreateNoteInvalida11() {
        rowid = mDbHelper_test.createNote("'", "cuerpo", "cat", 1, 10);
        assertEquals(-1, rowid);
    }

    @After
    public void tearDown() {
        mDbHelper_test.deleteNote(rowid);
    }
}