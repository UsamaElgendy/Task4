package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import model.Register;
import util.Constants;

public class DatabaseFaculty extends SQLiteOpenHelper {
    private Context context;

    public DatabaseFaculty(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_REGISTER_TABLE = "CREATE TABLE " + Constants.TABLE_FACULTY + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Constants.KEY_STUDENT_NAME + " TEXT,"
                + Constants.KEY_PASSWORD + " TEXT,"
                + Constants.KEY_PHONE + " TEXT,"
                + Constants.KEY_FACULTY_NAME + " INTEGER,"
                + Constants.KEY_DEPARTMENT_NAME + " INTEGER,"
                + Constants.KEY_EMAIL + " TEXT UNIQUE)";

        Log.d("TableCreated", "done");
        db.execSQL(CREATE_REGISTER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_FACULTY);
        onCreate(db);
    }

    public void addUser(Register register) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.KEY_STUDENT_NAME, register.getStudentName());
        values.put(Constants.KEY_PASSWORD, register.getPassword());
        values.put(Constants.KEY_PHONE, register.getPhone());
        values.put(Constants.KEY_EMAIL, register.getEmail());

        values.put(Constants.KEY_FACULTY_NAME, register.getFacultyName());
        values.put(Constants.KEY_DEPARTMENT_NAME, register.getDepartmentName());


        db.insert(Constants.TABLE_FACULTY, null, values);
        Log.d("Saved!", "saved to DB");

    }


    //return one user info
    public Register getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Constants.TABLE_FACULTY, new String[]{Constants.KEY_ID,
                        Constants.KEY_STUDENT_NAME, Constants.KEY_PASSWORD, Constants.KEY_EMAIL, Constants.KEY_PHONE, Constants.KEY_FACULTY_NAME, Constants.KEY_DEPARTMENT_NAME},
                Constants.KEY_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Register register = new Register();
        register.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
        register.setStudentName(cursor.getString(cursor.getColumnIndex(Constants.KEY_STUDENT_NAME)));
        register.setPassword(cursor.getString(cursor.getColumnIndex(Constants.KEY_PASSWORD)));
        register.setEmail(cursor.getString(cursor.getColumnIndex(Constants.KEY_EMAIL)));
        register.setPhone(cursor.getString(cursor.getColumnIndex(Constants.KEY_PHONE)));
        register.setFacultyName(cursor.getString(cursor.getColumnIndex(Constants.KEY_FACULTY_NAME)));
        register.setDepartmentName(cursor.getString(cursor.getColumnIndex(Constants.KEY_DEPARTMENT_NAME)));


        return register;
    }


    //get all user info
    public List<Register> getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Register> usersList = new ArrayList<>();
        //TELL THE SYSTEM IS EVERY THING ORDER BY THE DATA

        Cursor cursor = db.query(Constants.TABLE_FACULTY, new String[]{
                Constants.KEY_ID, Constants.KEY_STUDENT_NAME, Constants.KEY_PASSWORD,
                Constants.KEY_EMAIL, Constants.KEY_PHONE, Constants.KEY_FACULTY_NAME, Constants.KEY_DEPARTMENT_NAME}, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Register register = new Register();
                register.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
                register.setStudentName(cursor.getString(cursor.getColumnIndex(Constants.KEY_STUDENT_NAME)));
                register.setPassword(cursor.getString(cursor.getColumnIndex(Constants.KEY_PASSWORD)));
                register.setEmail(cursor.getString(cursor.getColumnIndex(Constants.KEY_EMAIL)));
                register.setPhone(cursor.getString(cursor.getColumnIndex(Constants.KEY_PHONE)));
                register.setFacultyName(cursor.getString(cursor.getColumnIndex(Constants.KEY_FACULTY_NAME)));
                register.setDepartmentName(cursor.getString(cursor.getColumnIndex(Constants.KEY_DEPARTMENT_NAME)));
                usersList.add(register);
            } while (cursor.moveToNext());
        }

        return usersList;
    }



    public int updateUser(Register register) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.KEY_STUDENT_NAME, register.getStudentName());
        values.put(Constants.KEY_PASSWORD, register.getPassword());
        values.put(Constants.KEY_EMAIL, register.getEmail());
        values.put(Constants.KEY_PHONE,register.getPhone());
        values.put(Constants.KEY_FACULTY_NAME,register.getFacultyName());
        values.put(Constants.KEY_DEPARTMENT_NAME,register.getDepartmentName());
        return db.update(Constants.TABLE_FACULTY, values, Constants.KEY_ID + "=?",
                new String[]{String.valueOf(register.getId())});
    }


    public void deleteUser(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_FACULTY, Constants.KEY_ID + "=?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public int getUsersCount() {
        String countQuery = "SELECT * FROM " + Constants.TABLE_FACULTY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }


    public Cursor getListContacts(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM "+Constants.TABLE_FACULTY,null);
        return data ;

    }
}
