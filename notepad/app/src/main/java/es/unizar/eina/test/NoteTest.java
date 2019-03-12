package es.unizar.eina.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import es.unizar.eina.notepadv3.NotesDbAdapter;
import es.unizar.eina.notepadv3.R;

public class NoteTest extends AppCompatActivity {

    private NotesDbAdapter mDbHelper;
    private TextView mResult;

    private long idR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_test);
        setTitle(R.string.note_test);

        mDbHelper = new NotesDbAdapter(this);
        mDbHelper.open();

        mResult = (TextView) findViewById(R.id.notetest_result);

        Button cnButton = (Button) findViewById(R.id.notetest_cajanegra);
        cnButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                mResult.setText((""));
                test_cajaNegra();
            }

        });

        Button volButton = (Button) findViewById(R.id.notetest_volumen);
        volButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                test_volumen();
            }

        });

        Button scButton = (Button) findViewById(R.id.notetest_sobrecarga);
        scButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                mResult.setText((""));
                test_sobrecarga();
            }

        });

        Button rstButton = (Button) findViewById(R.id.notetest_reset);
        rstButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                mResult.setText(("OK"));
                test_reset();
            }

        });
    }

    private void test_reset() {
        idR = mDbHelper.createNote("delete", "all", "notes");
        for (long i=1; i<=idR; i++) {
            mDbHelper.deleteNote(i);
        }
    }

    private void test_cajaNegra() {
        mResult.setText(("createNote: "));
        test_createNote1234();
        test_createNote5();
        test_createNote6();
        test_createNote7();
        test_createNote8();

        mResult.setText((mResult.getText().toString() + "\nupdateNote: "));
        test_updateNote12345();
        test_updateNote6();
        test_updateNote7();
        test_updateNote8();
        test_updateNote8();
        test_updateNote9();
        test_updateNote10();

        mResult.setText((mResult.getText().toString() + "\ndeleteNote: "));
        test_deleteNote2();
        test_deleteNote1();
    }

    private void test_createNote1234() {
        try {
            idR = mDbHelper.createNote("Hola", "Soy yo", "Saludo");
            if (idR > 0) {
                mResult.setText((mResult.getText().toString() + "1, 2, 3, 4, "));
            }
        }
        catch (Exception e) { }
    }

    private void test_createNote5() {
        try {
            long r = mDbHelper.createNote(null, "Soy yo", "Saludo");
            if (r == -1) {
                mResult.setText((mResult.getText().toString() + "5, "));
            }
        }
        catch (Exception e) {
            mResult.setText((mResult.getText().toString() + "5, "));
        }
    }

    private void test_createNote6() {
        try {
            long r = mDbHelper.createNote("", "Soy yo", "Saludo");
            if (r == -1) {
                mResult.setText((mResult.getText().toString() + "6, "));
            }
        }
        catch (Exception e) {
            mResult.setText((mResult.getText().toString() + "6, "));
        }
    }

    private void test_createNote7() {
        try {
            long r = mDbHelper.createNote("Hola", null, "Saludo");
            if (r == -1) {
                mResult.setText((mResult.getText().toString() + "7, "));
            }
        }
        catch (Exception e) {
            mResult.setText((mResult.getText().toString() + "7, "));
        }
    }

    private void test_createNote8() {
        try {
            long r = mDbHelper.createNote("Hola", "Soy yo", null);
            if (r == -1) {
                mResult.setText((mResult.getText().toString() + "8, "));
            }
        }
        catch (Exception e) {
            mResult.setText((mResult.getText().toString() + "8, "));
        }
    }

    private void test_updateNote12345() {
        try {
            boolean r = mDbHelper.updateNote( idR, "Adios", "No soy yo", "Despedida");
            if (r) {
                mResult.setText((mResult.getText().toString() + "1, 2, 3, 4, 5, "));
            }
        }
        catch (Exception e) { }
    }

    private void test_updateNote6() {
        try {
            boolean r = mDbHelper.updateNote( 0, "Adios", "No soy yo", "Despedida");
            if (!r) {
                mResult.setText((mResult.getText().toString() + "6, "));
            }
        }
        catch (Exception e) {
            mResult.setText((mResult.getText().toString() + "6, "));
        }
    }

    private void test_updateNote7() {
        try {
            boolean r = mDbHelper.updateNote( idR, null, "No soy yo", "Despedida");
            if (!r) {
                mResult.setText((mResult.getText().toString() + "7, "));
            }
        }
        catch (Exception e) {
            mResult.setText((mResult.getText().toString() + "7, "));
        }
    }

    private void test_updateNote8() {
        try {
            boolean r = mDbHelper.updateNote( idR, "", "No soy yo", "Despedida");
            if (!r) {
                mResult.setText((mResult.getText().toString() + "8, "));
            }
        }
        catch (Exception e) {
            mResult.setText((mResult.getText().toString() + "8, "));
        }
    }

    private void test_updateNote9() {
        try {
            boolean r = mDbHelper.updateNote( idR, "Adios", null, "Despedida");
            if (!r) {
                mResult.setText((mResult.getText().toString() + "9, "));
            }
        }
        catch (Exception e) {
            mResult.setText((mResult.getText().toString() + "9, "));
        }
    }

    private void test_updateNote10() {
        try {
            boolean r = mDbHelper.updateNote( idR, "Adios", "No soy yo", null);
            if (!r) {
                mResult.setText((mResult.getText().toString() + "10, "));
            }
        }
        catch (Exception e) {
            mResult.setText((mResult.getText().toString() + "10, "));
        }
    }

    private void test_deleteNote1() {
        try {
            boolean r = mDbHelper.deleteNote( idR);
            if (r) {
                mResult.setText((mResult.getText().toString() + "1, "));
            }
        }
        catch (Exception e) { }
    }

    private void test_deleteNote2() {
        try {
            boolean r = mDbHelper.deleteNote( 0);
            if (!r) {
                mResult.setText((mResult.getText().toString() + "2, "));
            }
        }
        catch (Exception e) {
            mResult.setText((mResult.getText().toString() + "2, "));
        }
    }

    private void test_volumen() {
        for (Integer i=0; i<999; i++) {
            String title = "volumen" + i.toString();
            mDbHelper.createNote(title, "Cuerpo", "Test_volumen");
        }
        idR = mDbHelper.createNote("volumen999", "Cuerpo", "Test_volumen");
        if (idR > 0) {
            mResult.setText(("OK"));
        }
        else {
            mResult.setText(("FAIL"));
        }
    }

    private void test_sobrecarga() {
        String title;
        String body = "";
        String body_add = "";
        final int body_adder= 10000;
        for (int i=0; i<body_adder; i++) {
            body_add += "A";
        }

        idR = 1;
        Integer i = 0;

        try {
            while (idR > 0) {
                title = "sobrecarga" + i.toString();
                body += body_add;
                idR = mDbHelper.createNote(title, body, "Test_sobrecarga");
                Log.i("Test_sobrecarga", ((Integer) body.length()).toString());
                i++;
            }
        }
        catch (Exception e) { }

        mResult.setText(("Sobrecarga: " + i.toString() + ": " + ((Integer) body.length()).toString()));
    }
}
