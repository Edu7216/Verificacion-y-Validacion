package es.unizar.eina.notepadv3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Simple notes database access helper class. Defines the basic CRUD operations
 * for the notepad example, and gives the ability to list all notes as well as
 * retrieve or modify a specific note.
 *
 * This has been improved from the first version of this tutorial through the
 * addition of better error handling and also using returning a Cursor instead
 * of using a collection of inner classes (which is less scalable and not
 * recommended).
 */
public class NotesDbAdapter {

    static final String KEY_TITLE = "title";
    static final String KEY_BODY = "body";
    static final String KEY_CATEGORY = "category";
    static final String KEY_ROWID = "_id";
    static final String KEY_STARTDATE = "startDate";
    static final String KEY_ENDDATE = "endDate";

    private static final String TAG = "NotesDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    /**
     * Database creation sql statement
     */
    private static final String DATABASE_CREATE =
            "create table notes (_id integer primary key autoincrement, "
                    + "title text not null, body text not null, category text not null, "
                    + "startDate integer, endDate integer);";

    private static final String DATABASE_NAME = "data";
    private static final String DATABASE_TABLE = "notes";
    private static final int DATABASE_VERSION = 2;

    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS notes");
            onCreate(db);
        }
    }

    /**
     * Comprueba los valores de los parametros para ver si son válidos o no
     *
     * @param title titulo
     * @param body cuerpo
     * @param category categoria
     * @param startDate fecha de activacion
     * @param endDate fecha de caducidad
     * @param op crear/actualizar
     * @return true si son correctos, false en caso contrario
     */
    private boolean parametrosValidos(String title, String body, String category, long startDate, long endDate, String op) {
        int counter = 0;
        if(title != null && title.length() > 0) {
            char[] titleArray = title.toCharArray();
            for (char aTitleArray : titleArray) {
                if (Character.isLetter(aTitleArray) || Character.isDigit(aTitleArray)) {
                    counter++;
                }
            }
        }
        switch (op) {
            case "CN":
                return title != null && category != null && !title.isEmpty() && startDate > 0 && startDate <= endDate &&  counter != 0;
            case "UN":
                return title != null && category != null && body != null && !title.isEmpty() && startDate > 0 && startDate <= endDate && counter != 0;
            default:
                return false;
        }
    }

    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     *
     * @param ctx the Context within which to work
     */
    public NotesDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    /**
     * Open the notes database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     *
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws SQLException if the database could be neither opened or created
     */
    public NotesDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    /**
     * Create a new note using the title and body provided. If the note is
     * successfully created return the new rowId for that note, otherwise return
     * a -1 to indicate failure.
     *
     * @param title the title of the note
     * @param body the body of the note
     * @return rowId or -1 if failed
     */
    long createNote(String title, String body, String category, long startDate, long endDate) {
        if (parametrosValidos(title,body,category,startDate,endDate,"CN")) {
            ContentValues initialValues = new ContentValues();
            initialValues.put(KEY_TITLE, title);
            initialValues.put(KEY_BODY, body);
            initialValues.put(KEY_CATEGORY, category);
            initialValues.put(KEY_STARTDATE, startDate);
            initialValues.put(KEY_ENDDATE, endDate);

            return mDb.insert(DATABASE_TABLE, null, initialValues);
        }
        else {
            return -1;
        }
    }

    /**
     * Delete the note with the given rowId
     *
     * @param rowId id of note to delete
     * @return true if deleted, false otherwise
     */
    boolean deleteNote(long rowId) {
        return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    /**
     * Delete the category
     *
     * @param category category to delete
     */
    public void deleteCategory(String category) throws SQLException {
        if (category == null) {
            throw new SQLException();
        }
        else {
            mDb.execSQL("UPDATE " + DATABASE_TABLE + " SET " + KEY_CATEGORY + " = '' " +
                    " WHERE " + KEY_CATEGORY + " = '"+ category + "'");
        }
    }

    /**
     * Return a Cursor over the list of all notes selected in the database
     *
     * @param byCategory if it's true, notes will be ordered by its category name, if not, they will
     *                   be ordered by its title
     * @param Category category of notes to retrieve, null for all notes
     * @return Cursor over all notes
     */
    Cursor fetchAllNotes(boolean byCategory, String Category, String typeDate,
                         long currentDate) {
        String where;
        switch (typeDate) {
            case "Planned Notes":
                where = " " + KEY_STARTDATE + " > " + currentDate + " ";
                break;
            case "Active Notes":
                where = " " + KEY_STARTDATE + " < " + currentDate
                        + " AND " + KEY_ENDDATE + " > " + currentDate + " ";
                break;
            case "Expired Notes":
                where = " " + KEY_ENDDATE + " < " + currentDate + " ";
                break;
            default:
                where = " 1 = 1 ";
                break;
        }
        if (Category.isEmpty()) {
            if (byCategory) {
                return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_TITLE,
                                KEY_BODY, KEY_CATEGORY}, where, null,
                        null, null, KEY_CATEGORY);
            }
            else {
                return mDb.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_TITLE,
                                KEY_BODY, KEY_CATEGORY}, where, null,
                        null, null, KEY_TITLE);
            }
        }
        else {
            where = where + " AND " + KEY_CATEGORY + " = '" + Category + "'";
            return mDb.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_TITLE,
                            KEY_BODY, KEY_CATEGORY}, where, null, null,
                    null, KEY_TITLE);
        }
    }

    /**
     * Return a Cursor positioned at the note that matches the given rowId
     *
     * @param rowId id of note to retrieve
     * @return Cursor positioned to matching note, if found
     * @throws SQLException if note could not be found/retrieved
     */
    Cursor fetchNote(long rowId) throws SQLException {
        if (rowId <= 0) {
            throw new SQLException();
        }
        else {
            Cursor mCursor =

                    mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                                    KEY_TITLE, KEY_BODY, KEY_CATEGORY, KEY_STARTDATE, KEY_ENDDATE},
                            KEY_ROWID + "=" + rowId, null, null,
                            null, null, null);
            if (mCursor != null) {
                mCursor.moveToFirst();
            }
            return mCursor;
        }
    }

    Cursor fetchNote(String name) throws SQLException {
        Cursor mCursor =

                mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                                KEY_TITLE, KEY_BODY, KEY_CATEGORY, KEY_STARTDATE, KEY_ENDDATE},
                        KEY_TITLE + "=\"" + name + "\"", null, null,
                        null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    /**
     * Update the note using the details provided. The note to be updated is
     * specified using the rowId, and it is altered to use the title and body
     * values passed in
     *
     * @param rowId id of note to update
     * @param title value to set note title to
     * @param body value to set note body to
     * @return true if updated, false otherwise
     */
    boolean updateNote(long rowId, String title, String body, String category, long startDate, long endDate) {
        if (parametrosValidos(title,body,category,startDate,endDate,"UN")) {
            ContentValues args = new ContentValues();
            args.put(KEY_TITLE, title);
            args.put(KEY_BODY, body);
            args.put(KEY_CATEGORY, category);
            args.put(KEY_STARTDATE, startDate);
            args.put(KEY_ENDDATE, endDate);

            return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
        }
        else {
            return false;
        }
    }

    /**
     * Update the category
     *
     * @param old_category category to update
     * @param new_category category to set
     */
    public void updateCategory(String old_category, String new_category) throws SQLException {
        if (old_category == null || new_category == null) {
            throw new SQLException();
        }
        else {
            mDb.execSQL("UPDATE " + DATABASE_TABLE + " SET " + KEY_CATEGORY + " = '" + new_category +
                    "' WHERE " + KEY_CATEGORY + " = '"+ old_category + "'");
        }
    }

    /**
     * Operaciones para testing
     *
     */
    int getNotesNumber() {
        Cursor aux = this.fetchAllNotes(false,"","ALL",0);
        int numNotas = aux.getCount();
        aux.close();
        return numNotas;
    }

    String getTitle(long rowId) {
        Cursor aux = this.fetchNote(rowId);
        String titulo_aux = aux.getString(aux.getColumnIndex("title"));
        aux.close();
        return titulo_aux;
    }

    String getBody(long rowId) {
        Cursor aux = this.fetchNote(rowId);
        String cuerpo_aux = aux.getString(aux.getColumnIndex("body"));
        aux.close();
        return cuerpo_aux;
    }

    String getCategory(long rowId) {
        Cursor aux = this.fetchNote(rowId);
        String cat_aux = aux.getString(aux.getColumnIndex("category"));
        aux.close();
        return cat_aux;
    }

    long getStartDate(long rowId) {
        Cursor aux = this.fetchNote(rowId);
        long fAct_aux = aux.getLong(aux.getColumnIndex("startDate"));
        aux.close();
        return fAct_aux;
    }

    long getEndDate(long rowId) {
        Cursor aux = this.fetchNote(rowId);
        long fCad_aux = aux.getLong(aux.getColumnIndex("endDate"));
        aux.close();
        return fCad_aux;
    }

    void limpiarBD() {
        Cursor cr = fetchAllNotes(false,"","",0);
        cr.moveToFirst();

        long rowid;
        String sri;

        if (cr.moveToFirst()) {
            sri = cr.getString(cr.getColumnIndex(NotesDbAdapter.KEY_ROWID));
            rowid = Long.parseLong(sri);
            deleteNote(rowid);
        }

        while (cr.moveToNext()) {
            sri = cr.getString(cr.getColumnIndex(NotesDbAdapter.KEY_ROWID));
            rowid = Long.parseLong(sri);
            deleteNote(rowid);
        }
    }
}