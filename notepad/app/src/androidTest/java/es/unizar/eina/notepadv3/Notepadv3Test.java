package es.unizar.eina.notepadv3;

import android.database.Cursor;

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

    Notepadv3 mNotepad;

    @Before
    public void setUp() {
        mNotepad = activityRule.getActivity();
    }

    @Test
    public void test_1() {
        NotesDbAdapter mDbHelper_test = mNotepad.getmDbHelper();
        Cursor aux = mDbHelper_test.fetchAllNotes(false,"","ALL",0);
        int numNotas = aux.getCount();
        mDbHelper_test.createNote("titulo", "cuerpo", "cat", new Date().getTime(), new Date().getTime());
        aux = mDbHelper_test.fetchAllNotes(false,"","ALL",0);
        int numNotas_nuevo = aux.getCount();
        assertEquals(numNotas + 1, numNotas_nuevo);
    }


}