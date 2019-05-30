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
public class UpdateNoteTest {
    @Rule
    public ActivityTestRule<Notepadv3> activityRule = new ActivityTestRule<>(Notepadv3.class);

    private NotesDbAdapter mDbHelper_test;
    private long rowid;

    @Before
    public void setUp() {
        mDbHelper_test = activityRule.getActivity().getmDbHelper();
    }

    // Clases 1, 3, 4, 7, 9, 11, 12, 15, 16
    @Test
    public void test_UpdateNoteValida1() {
        long startDate = new Date().getTime();
        long endDate = new Date().getTime() + 10;
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", new Date().getTime(), new Date().getTime());
        boolean upd = mDbHelper_test.updateNote(rowid, "tituloU", "cuerpoU", "catU", startDate, endDate);
        assertTrue(upd);
    }

    // Clases 1, 3, 4, 7, 9, 11, 12, 15, 16
    @Test
    public void test_UpdateNoteValida2() {
        long startDate = new Date().getTime();
        long endDate = startDate;
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", new Date().getTime(), new Date().getTime());
        boolean upd = mDbHelper_test.updateNote(rowid, "tituloU", "cuerpoU", "catU", startDate, endDate);
        assertTrue(upd);
    }

    // Clase 2
    @Test
    public void test_UpdateNoteInvalida1() {
        boolean upd = mDbHelper_test.updateNote(0, "tituloU", "cuerpoU", "catU", new Date().getTime(), new Date().getTime());
        assertFalse(upd);
    }

    // Clase 5
    @Test
    public void test_UpdateNoteInvalida2() {
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", new Date().getTime(), new Date().getTime());
        boolean upd = mDbHelper_test.updateNote(rowid, null, "cuerpoU", "catU", new Date().getTime(), new Date().getTime());
        assertFalse(upd);
    }

    // Clase 8
    @Test
    public void test_UpdateNoteInvalida3() {
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", new Date().getTime(), new Date().getTime());
        boolean upd = mDbHelper_test.updateNote(rowid, "tituloU", null, "catU", new Date().getTime(), new Date().getTime());
        assertFalse(upd);
    }

    // Clase 10
    @Test
    public void test_UpdateNoteInvalida4() {
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", new Date().getTime(), new Date().getTime());
        boolean upd = mDbHelper_test.updateNote(rowid, "tituloU", "cuerpoU", null, new Date().getTime(), new Date().getTime());
        assertFalse(upd);
    }

    // Clase 2
    @Test
    public void test_UpdateNoteInvalida5() {
        boolean upd = mDbHelper_test.updateNote(-8, "tituloU", "cuerpoU", "catU", new Date().getTime(), new Date().getTime());
        assertFalse(upd);
    }

    // Clase 6
    @Test
    public void test_UpdateNoteInvalida6() {
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", new Date().getTime(), new Date().getTime());
        boolean upd = mDbHelper_test.updateNote(rowid, "", "cuerpoU", "catU", new Date().getTime(), new Date().getTime());
        assertFalse(upd);
    }

    // Clase 13
    @Test
    public void test_UpdateNoteInvalida8() {
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", new Date().getTime(), new Date().getTime());
        boolean upd = mDbHelper_test.updateNote(rowid, "tituloU", "cuerpoU", "catU", 0, new Date().getTime());
        assertFalse(upd);
    }

    // Clase 13
    @Test
    public void test_UpdateNoteInvalida9() {
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", new Date().getTime(), new Date().getTime());
        boolean upd = mDbHelper_test.updateNote(rowid, "tituloU", "cuerpoU", "catU", -8, new Date().getTime());
        assertFalse(upd);
    }

    // Clase 17
    @Test
    public void test_UpdateNoteInvalida10() {
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", new Date().getTime(), new Date().getTime());
        boolean upd = mDbHelper_test.updateNote(rowid, "tituloU", "cuerpoU", "catU", new Date().getTime(), 0);
        assertFalse(upd);
    }

    // Clase 17
    @Test
    public void test_UpdateNoteInvalida11() {
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", new Date().getTime(), new Date().getTime());
        boolean upd = mDbHelper_test.updateNote(rowid, "tituloU", "cuerpoU", "catU", new Date().getTime(), -8);
        assertFalse(upd);
    }

    // Clase 14, 18
    @Test
    public void test_UpdateNoteInvalida12() {
        long endDate = new Date().getTime();
        long startDate = new Date().getTime() + 10;
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", new Date().getTime(), new Date().getTime());
        boolean upd = mDbHelper_test.updateNote(rowid, "tituloU", "cuerpoU", "catU", startDate, endDate);
    }

    @After
    public void tearDown() {
        mDbHelper_test.deleteNote(rowid);
    }
}