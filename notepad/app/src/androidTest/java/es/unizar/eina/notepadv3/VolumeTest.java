package es.unizar.eina.notepadv3;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class VolumeTest {
    @Rule
    public ActivityTestRule<Notepadv3> activityRule = new ActivityTestRule<>(Notepadv3.class);

    NotesDbAdapter mDbHelper_test;
    private long rowid;
    ArrayList<Long> ids = new ArrayList<Long>();

    @Before
    public void setUp() {
        mDbHelper_test = activityRule.getActivity().getmDbHelper();
    }

    @Test
    public void test_numNotas() {
        int numNotas = mDbHelper_test.getNotesNumber();
        for (int i = 0; i < 1000; i++){
            rowid = mDbHelper_test.createNote("titulo", "cuerpo", "cat", new Date().getTime(), new Date().getTime());
            ids.add(rowid);
        }
        assertEquals(numNotas + 1000, mDbHelper_test.getNotesNumber());
    }

    @Test
    public void test_lengthTitle() {
        int numNotas = mDbHelper_test.getNotesNumber();
        int length = 100000;
        StringBuffer outputBuffer = new StringBuffer(length);
        for (int i = 0; i < length; i++){
            outputBuffer.append("a");
        }
        rowid = mDbHelper_test.createNote(outputBuffer.toString(), "cuerpo", "cat", new Date().getTime(), new Date().getTime());
        ids.add(rowid);
        assertEquals(numNotas + 1, mDbHelper_test.getNotesNumber());
    }

    @Test
    public void test_lengthBody() {
        int numNotas = mDbHelper_test.getNotesNumber();
        int length = 100000;
        StringBuffer outputBuffer = new StringBuffer(length);
        for (int i = 0; i < length; i++){
            outputBuffer.append("a");
        }
        rowid = mDbHelper_test.createNote("titulo", outputBuffer.toString(), "cat", new Date().getTime(), new Date().getTime());
        ids.add(rowid);
        assertEquals(numNotas + 1, mDbHelper_test.getNotesNumber());
    }

    @After
    public void tearDown() {
        for(Long id: ids){
            mDbHelper_test.deleteNote(id);
        }
    }
}