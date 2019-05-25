package es.unizar.eina.notepadv3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.Date;

import es.unizar.eina.catlist.CatList;

public class NoteEdit extends AppCompatActivity {

    private static final int ACTIVITY_CATEGORY = 1;
    private static final int ACTIVITY_DATE = 2;

    private EditText mTitleText;
    private EditText mBodyText;
    private EditText mCatText;
    private Date currentDate;
    private EditText mStartDate;
    private Date startDate;
    private EditText mEndDate;
    private Date endDate;
    private Long mRowId;
    private EditText error;

    private NotesDbAdapter mDbHelper;

    private EditText mIdText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDbHelper = new NotesDbAdapter(this);
        mDbHelper.open();

        setContentView(R.layout.note_edit);
        setTitle(R.string.edit_note);

        mTitleText = (EditText) findViewById(R.id.title);
        mBodyText = (EditText) findViewById(R.id.body);

        mCatText = (EditText) findViewById(R.id.category);
        mCatText.setEnabled(false);

        mStartDate = (EditText) findViewById(R.id.startDate);
        mStartDate.setEnabled(false);

        mEndDate = (EditText) findViewById(R.id.endDate);
        mEndDate.setEnabled(false);

        error = (EditText) findViewById(R.id.error);
        error.setEnabled(false);

        currentDate = new Date();
        currentDate.setYear(currentDate.getYear() + 1900);
        currentDate.setMonth(currentDate.getMonth() + 1);
        currentDate = new Date(currentDate.getYear(), currentDate.getMonth(), currentDate.getDate());

        if(startDate == null){
            startDate = new Date(currentDate.getYear(), currentDate.getMonth(), currentDate.getDate());
        }
        if (endDate == null){
            endDate = new Date(currentDate.getYear(), currentDate.getMonth() + 1, currentDate.getDate());
        }


        mIdText = (EditText) findViewById(R.id.id);
        mIdText.setEnabled(false);

        Button confirmButton = (Button) findViewById(R.id.confirm);

        mRowId = (savedInstanceState == null) ? null :
                (Long) savedInstanceState.getSerializable(NotesDbAdapter.KEY_ROWID);
        if (mRowId == null) {
            Bundle extras = getIntent().getExtras();
            mRowId = (extras != null) ? extras.getLong(NotesDbAdapter.KEY_ROWID)
                    : null;
        }

        confirmButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                setResult(RESULT_OK);
                finish();
            }

        });

        Button modifyButton = (Button) findViewById(R.id.cat_modify);
        modifyButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), CatList.class);
                startActivityForResult(i, ACTIVITY_CATEGORY);
            }

        });

        Button modifyStartDateButton = (Button) findViewById(R.id.startDate_modify);
        modifyStartDateButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), NoteDatePicker.class);
                i.putExtra("info","startDate");
                i.putExtra("startDate", startDate);
                startActivityForResult(i, ACTIVITY_DATE);
            }

        });

        Button modifyEndDateButton = (Button) findViewById(R.id.endDate_modify);
        modifyEndDateButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), NoteDatePicker.class);
                i.putExtra("info","endDate");
                i.putExtra("startDate", startDate);
                i.putExtra("endDate", endDate);
                startActivityForResult(i, ACTIVITY_DATE);
            }

        });

        Button resetButton = (Button) findViewById(R.id.cat_reset);
        resetButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                mCatText.setText("");
                saveState();
            }

        });
    }

    @SuppressLint("SetTextI18n")
    private void populateFields() {
        if (mRowId != null) {
            Cursor note = mDbHelper.fetchNote(mRowId);
            startManagingCursor(note);
            mTitleText.setText(note.getString(
                    note.getColumnIndexOrThrow(NotesDbAdapter.KEY_TITLE)));
            mBodyText.setText(note.getString(
                    note.getColumnIndexOrThrow(NotesDbAdapter.KEY_BODY)));
            mCatText.setText(note.getString(
                    note.getColumnIndexOrThrow(NotesDbAdapter.KEY_CATEGORY)));
            startDate = new Date(note.getLong(
                    note.getColumnIndexOrThrow(NotesDbAdapter.KEY_STARTDATE)));
            endDate = new Date(note.getLong(
                    note.getColumnIndexOrThrow(NotesDbAdapter.KEY_ENDDATE)));

            mStartDate.setText(startDate.getMonth() + "/" + startDate.getDate()
                    + "/" + startDate.getYear());
            mEndDate.setText(endDate.getMonth() + "/" + endDate.getDate()
                    + "/" + endDate.getYear());

            mIdText.setText(mRowId.toString());
        }
        else {
            mIdText.setText("***");
            mStartDate.setText(startDate.getMonth() + "/" + startDate.getDate()
                    + "/" + startDate.getYear());
            mEndDate.setText(endDate.getMonth() + "/" + endDate.getDate()
                    + "/" + endDate.getYear());
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState();
        outState.putSerializable(NotesDbAdapter.KEY_ROWID, mRowId);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateFields();
    }

    private void saveState() {
        String title = mTitleText.getText().toString();
        String body = mBodyText.getText().toString();
        String category = mCatText.getText().toString();

        if (title.isEmpty()) {
            title = "Note";
        }

        if (mRowId == null) {
            long id = mDbHelper.createNote(title, body, category, startDate.getTime(), endDate.getTime());
            if (id > 0) {
                mRowId = id;
            }
        } else {
            mDbHelper.updateNote(mRowId, title, body, category, startDate.getTime(), endDate.getTime());
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == ACTIVITY_CATEGORY) {
            if (resultCode == RESULT_OK) {
                mCatText.setText(intent.getStringExtra("cat_result"));
                saveState();
            }
        }
        if (requestCode == ACTIVITY_DATE) {
            if (resultCode == RESULT_OK) {
                if (intent.getStringExtra("info").equals("startDate")) {
                    Date d = (Date) intent.getSerializableExtra("startDate");
                    if(d.compareTo(currentDate) < 0) {
                        error.setText("Start date can't be before the current date");
                        startDate = currentDate;
                    } else if (d.compareTo(endDate) > 0){
                        error.setText("Start date can't be after the end date");
                        startDate = currentDate;
                    } else {
                        error.setText("");
                        startDate = d;
                    }
                }
                if (intent.getStringExtra("info").equals("endDate")){
                    Date d = (Date) intent.getSerializableExtra("endDate");
                    if (d.compareTo(startDate) < 0){
                        error.setText("End date can't be before the start date");
                        endDate = startDate;
                        endDate.setMonth(endDate.getMonth());
                    } else {
                        error.setText("");
                        endDate = d;
                    }
                }
                saveState();
            }
        }
    }

}
