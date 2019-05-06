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
public class UpdateNoteTest {
    @Rule
    public ActivityTestRule<Notepadv3> activityRule = new ActivityTestRule<>(Notepadv3.class);

    NotesDbAdapter mDbHelper_test;
    private long rowid;

    @Before
    public void setUp() {
        mDbHelper_test = activityRule.getActivity().getmDbHelper();
    }

    @Test
    public void test_UpdateNoteValida() {
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", new Date().getTime(), new Date().getTime());
        boolean upd = mDbHelper_test.updateNote(rowid, "tituloU", "cuerpoU", "catU", new Date().getTime(), new Date().getTime());
        assertEquals(true, upd);
    }

    @Test
    public void test_UpdateNoteInvalida1() {
        boolean upd = mDbHelper_test.updateNote(0, "tituloU", "cuerpoU", "catU", new Date().getTime(), new Date().getTime());
        assertEquals(false, upd);
    }

    @Test(expected = android.database.sqlite.SQLiteConstraintException.class)
    public void test_UpdateNoteInvalida2() {
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", new Date().getTime(), new Date().getTime());
        mDbHelper_test.updateNote(rowid, null, "cuerpoU", "catU", new Date().getTime(), new Date().getTime());
    }

    @Test(expected = android.database.sqlite.SQLiteConstraintException.class)
    public void test_UpdateNoteInvalida3() {
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", new Date().getTime(), new Date().getTime());
        mDbHelper_test.updateNote(rowid, "tituloU", null, "catU", new Date().getTime(), new Date().getTime());
    }

    @Test(expected = android.database.sqlite.SQLiteConstraintException.class)
    public void test_UpdateNoteInvalida4() {
        rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", new Date().getTime(), new Date().getTime());
        mDbHelper_test.updateNote(rowid, "tituloU", "cuerpoU", null, new Date().getTime(), new Date().getTime());
    }

    @After
    public void tearDown() {
        mDbHelper_test.deleteNote(rowid);
    }
}