package com.example.grehelper;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {
 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "WordManager";
 
    // Contacts table name
    private static final String TABLE_CONTACTS = "wordtable";
 
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_WORD = "word";
    private static final String KEY_MEANING = "meaning";
    private static final String KEY_ANTONIM = "antonim";
    private static final String KEY_SYNONIM = "synonim";
 
    public DatabaseHandler(Context context) 
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) 
    {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_WORD + " TEXT,"
                + KEY_MEANING + " TEXT," +  KEY_ANTONIM + " TEXT,"+ KEY_SYNONIM + " TEXT" +")";
        
        //db.execSQL("delete from" + DATABASE_NAME);
        Log.e("redoy", "trunk hoise");
        
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
 
        // Create tables again
        onCreate(db);
    }
 
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
 
    // Adding new contact
    void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_WORD, contact.get_word()); // Contact word
        values.put(KEY_MEANING, contact.get_meaning()); // Contact meaning
        values.put(KEY_ANTONIM, contact.get_antonim()); // Contact antonim
        values.put(KEY_SYNONIM, contact.get_synonim()); // Contact synonim
 
        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }
 
    // Getting single contact
    Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                KEY_WORD, KEY_MEANING ,KEY_ANTONIM, KEY_SYNONIM}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        // return contact
        return contact;
    }
 
    // Getting All Contacts
    public List<Contact> getAllContacts() 
    {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS + " ORDER BY word ASC ";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.set_id(Integer.parseInt(cursor.getString(0)));
                contact.set_word(cursor.getString(1));
                contact.set_meaning(cursor.getString(2));
                contact.set_antonim(cursor.getString(3));
                contact.set_synonim(cursor.getString(4));
                
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return contactList;
    }
 
    // Updating single contact
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_WORD, contact.get_word());
        values.put(KEY_MEANING, contact.get_meaning());
        values.put(KEY_ANTONIM, contact.get_antonim());
        values.put(KEY_SYNONIM, contact.get_synonim());
 
        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.get_id()) });
    }
 
    // Deleting single contact
    public void deleteContact(Contact contact) 
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",new String[] { String.valueOf(contact.get_id()) });
        db.close();
    }
 
    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }
 
}
