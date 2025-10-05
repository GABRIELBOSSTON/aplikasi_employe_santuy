package com.example.employee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "employee_db";
    private static final String TB_NAME = "employee";
    private static final String Col_ID = "id";
    private static final String Col_Name = "name";
    private static final String Col_Email = "email";

    private static final String CREATE_TABLE = "CREATE TABLE " + TB_NAME + "("
            + Col_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Col_Name + " TEXT NOT NULL, "
            + Col_Email + " TEXT NOT NULL);";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);  // Create table seperti slide
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_NAME);
        onCreate(db);
    }

    public void addEmployee(EmployeeModel employeeModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_Name, employeeModel.getName());
        contentValues.put(Col_Email, employeeModel.getEmail());
        SQLiteDatabase mySQLite = this.getWritableDatabase();  // Open/create DB seperti slide
        mySQLite.insert(TB_NAME, null, contentValues);
        mySQLite.close();
    }

    public void updateEmployee(EmployeeModel employeeModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_Name, employeeModel.getName());
        contentValues.put(Col_Email, employeeModel.getEmail());
        SQLiteDatabase mySQLite = this.getWritableDatabase();
        mySQLite.update(TB_NAME, contentValues, Col_ID + " = ?", new String[]{String.valueOf(employeeModel.getId())});
        mySQLite.close();
    }

    public void deleteEmployee(int id) {
        SQLiteDatabase mySQLite = this.getWritableDatabase();
        mySQLite.delete(TB_NAME, Col_ID + " = ?", new String[]{String.valueOf(id)});
        mySQLite.close();
    }

    public List<EmployeeModel> getEmployeeList() {
        String viewData = "SELECT * FROM " + TB_NAME;  // View data seperti slide
        SQLiteDatabase mySQLite = this.getReadableDatabase();
        List<EmployeeModel> storeEmployee = new ArrayList<>();
        Cursor cursor = mySQLite.rawQuery(viewData, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String email = cursor.getString(2);
                storeEmployee.add(new EmployeeModel(id, name, email));
            } while (cursor.moveToNext());
        }
        cursor.close();
        mySQLite.close();
        return storeEmployee;
    }

    public EmployeeModel getEmployeeById(int id) {
        SQLiteDatabase mySQLite = this.getReadableDatabase();
        Cursor cursor = mySQLite.query(TB_NAME, new String[]{Col_ID, Col_Name, Col_Email},
                Col_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            EmployeeModel employee = new EmployeeModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
            cursor.close();
            mySQLite.close();
            return employee;
        }
        if (cursor != null) cursor.close();
        mySQLite.close();
        return null;
    }
}