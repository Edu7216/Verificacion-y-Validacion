package es.unizar.eina.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import es.unizar.eina.catlist.CatDbAdapter;
import es.unizar.eina.notepadv3.R;

public class CategoryTest extends AppCompatActivity {

    private CatDbAdapter mDbHelper;
    private TextView mResult;

    private long idR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_test);
        setTitle(R.string.cat_test);

        mDbHelper = new CatDbAdapter(this);
        mDbHelper.open();

        mResult = (TextView) findViewById(R.id.cattest_result);

        Button cnButton = (Button) findViewById(R.id.cattest_cajanegra);
        cnButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                mResult.setText((""));
                test_cajaNegra();
            }

        });

        Button volButton = (Button) findViewById(R.id.cattest_volumen);
        volButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                test_volumen();
            }

        });

        Button scButton = (Button) findViewById(R.id.cattest_sobrecarga);
        scButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                mResult.setText((""));
                test_sobrecarga();
            }

        });

        Button rstButton = (Button) findViewById(R.id.cattest_reset);
        rstButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                mResult.setText(("OK"));
                test_reset();
            }

        });
    }

    private void test_reset() {
        idR = mDbHelper.createCategory("delete");
        for (long i=1; i<=idR; i++) {
            mDbHelper.deleteCategory(i);
        }
    }

    private void test_cajaNegra() {
        mResult.setText(("createCategory: "));
        test_createCategory12();
        test_createCategory3();
        test_createCategory4();

        mResult.setText((mResult.getText().toString() + "\nupdateCategory: "));
        test_updateCategory123();
        test_updateCategory4();
        test_updateCategory5();
        test_updateCategory6();

        mResult.setText((mResult.getText().toString() + "\ndeleteCategory: "));
        test_deleteCategory2();
        test_deleteCategory1();
    }

    private void test_createCategory12() {
        try {
            idR = mDbHelper.createCategory("Saludo");
            if (idR > 0) {
                mResult.setText((mResult.getText().toString() + "1, 2, "));
            }
        }
        catch (Exception e) { }
    }

    private void test_createCategory3() {
        try {
            long r = mDbHelper.createCategory(null);
            if (r == -1) {
                mResult.setText((mResult.getText().toString() + "3, "));
            }
        }
        catch (Exception e) {
            mResult.setText((mResult.getText().toString() + "3, "));
        }
    }

    private void test_createCategory4() {
        try {
            long r = mDbHelper.createCategory("");
            if (r == -1) {
                mResult.setText((mResult.getText().toString() + "4, "));
            }
        }
        catch (Exception e) {
            mResult.setText((mResult.getText().toString() + "4, "));
        }
    }

    private void test_updateCategory123() {
        try {
            boolean r = mDbHelper.updateCategory( idR, "Despedida");
            if (r) {
                mResult.setText((mResult.getText().toString() + "1, 2, 3, "));
            }
        }
        catch (Exception e) { }
    }

    private void test_updateCategory4() {
        try {
            boolean r = mDbHelper.updateCategory( 0, "Despedida");
            if (!r) {
                mResult.setText((mResult.getText().toString() + "4, "));
            }
        }
        catch (Exception e) {
            mResult.setText((mResult.getText().toString() + "4, "));
        }
    }

    private void test_updateCategory5() {
        try {
            boolean r = mDbHelper.updateCategory( idR, null);
            if (!r) {
                mResult.setText((mResult.getText().toString() + "5, "));
            }
        }
        catch (Exception e) {
            mResult.setText((mResult.getText().toString() + "5, "));
        }
    }

    private void test_updateCategory6() {
        try {
            boolean r = mDbHelper.updateCategory( idR, "");
            if (!r) {
                mResult.setText((mResult.getText().toString() + "6, "));
            }
        }
        catch (Exception e) {
            mResult.setText((mResult.getText().toString() + "6, "));
        }
    }

    private void test_deleteCategory1() {
        try {
            boolean r = mDbHelper.deleteCategory( idR);
            if (r) {
                mResult.setText((mResult.getText().toString() + "1, "));
            }
        }
        catch (Exception e) { }
    }

    private void test_deleteCategory2() {
        try {
            boolean r = mDbHelper.deleteCategory( 0);
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
            mDbHelper.createCategory(title);
        }
        idR = mDbHelper.createCategory("volumen999");
        if (idR > 0) {
            mResult.setText(("OK"));
        }
        else {
            mResult.setText(("FAIL"));
        }
    }

    private void test_sobrecarga() {
        String title = "";
        String title_add = "";
        final int title_adder= 10000;
        for (int i=0; i<title_adder; i++) {
            title_add += "A";
        }

        idR = 1;
        Integer i = 0;

        try {
            while (idR > 0) {
                title += title_add;
                idR = mDbHelper.createCategory(title);
                Log.i("Test_sobrecarga", ((Integer) title.length()).toString());
                i++;
            }
        }
        catch (Exception e) { }

        mResult.setText(("Sobrecarga: " + i.toString() + ": " + ((Integer) title.length()).toString()));
    }
}
