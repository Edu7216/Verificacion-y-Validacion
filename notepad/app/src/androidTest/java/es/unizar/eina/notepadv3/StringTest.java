package es.unizar.eina.notepadv3;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.database.Cursor;

import java.util.Date;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class StringTest {
    @Rule
    public ActivityTestRule<Notepadv3> activityRule = new ActivityTestRule<>(Notepadv3.class);

    NotesDbAdapter mDbHelper_test;
    long rowid;

    @Before
    public void setUp() {
        mDbHelper_test = activityRule.getActivity().getmDbHelper();
    }

    // Clases 1, 2, 3, 4, 5, 6, 7
    @Test
    public void test_StringValida() {
        char ascii_symbol = 33; // Clase 1 -> ! (0021 hexadecimal)
        char ascii_digit = 49; // Clase 2 -> 1 (0031 hexadecimal)
        char upper = 65; // Clase 3 -> A (0041 hexadecimal)
        char lower = 97; // Clase 4 -> a (0061 hexadecimal)
        char latin1_symbol = 169; // Clase 5 -> © (00A9 hexadecimal)
        char latin1_letter = 209; // Clase 6 -> Ñ (00D1 hexadecimal)
        char math_operator = 8721; // Clase 7 -> ∑ (2211 hexadecimal)
        String title = ascii_symbol + " / " + ascii_digit + " / " + upper + " / "
                + lower + " / " + latin1_symbol + " / " + latin1_letter + " / " + math_operator;
        mDbHelper_test.createNote(title, "cuerpo", "cat", new Date().getTime(), new Date().getTime());
        //COMPROBACIÓN MANUAL
        assertEquals(1, 1);
    }

    // Clases 8:75
    @Test
    public void test_StringInvalida1() {
        long startDate = new Date().getTime();
        long endDate = new Date().getTime() + 10;
        int j = 0;
        char ch;
        for(int i = 0; i <= 32; i++) {
            ch = (char) i;
            mDbHelper_test.createNote(j + " " + ch, "cuerpo",
                    "cat", startDate, endDate);
            j = i;
        }

        for(int i = 127; i <= 160; i++) {
            j++;
            ch = (char) i;
            mDbHelper_test.createNote(j + " " + ch, "cuerpo",
                    "cat", startDate, endDate);
        }
        j++;
        ch = 173;
        mDbHelper_test.createNote(j + " " + ch, "cuerpo",
                "cat", startDate, endDate);
        //COMPROBACION MANUAL
        assertEquals(1, 1);
    }

    // Clases 8:75
    @Test
    public void test_StringInvalida2() {
        long startDate = new Date().getTime();
        long endDate = new Date().getTime() + 10;
        int j = 0;
        char ch;
        for(int i = 0; i <= 32; i++) {
            ch = (char) i;
            mDbHelper_test.createNote("Titulo " + i + " - " + ch + " - Titulo", "cuerpo",
                    "cat", startDate, endDate);
            j = i;
        }

        for(int i = 127; i <= 160; i++) {
            j++;
            ch = (char) i;
            mDbHelper_test.createNote("Titulo " + j + " - " + ch + " - Titulo", "cuerpo",
                    "cat", startDate, endDate);
        }
        j++;
        ch = 173;
        mDbHelper_test.createNote("Titulo " + j + " - " + ch + " - Titulo", "cuerpo",
                "cat", startDate, endDate);
        //COMPROBACION MANUAL
        assertEquals(1, 1);
    }
}