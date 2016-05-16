package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.lisis.charles.fastreport.DB_User;
import com.lisis.charles.fastreport.DB_Vehicle;


public class DatabaseSQLiteHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "FastReportDB";

    // Table Names TODO: crear las demas tablas
    private static final String TABLE_USERS = "users";
    private static final String TABLE_VEHICLES = "vehicles";
    //private static final String TABLE_TAG = "tags";
    //private static final String TABLE_TODO_TAG = "todo_tags";

    // Common column names
    private static final String KEY_ID = "id";

    // USERS Table - column names
    private static final String KEY_NAME = "name";
    private static final String KEY_LAST_NAME = "last_name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASS = "pass";
    private static final String KEY_PHONE_NUMBER = "phone_number";
    private static final String KEY_DRIVERS_LICENSE = "drivers_license";
    private static final String KEY_EXPIRATION_DATE = "expiration_date";
    private static final String KEY_ADDRESS = "address";

    // VEHICLES Table - column names
    private static final String KEY_BRAND = "brand";
    private static final String KEY_MODEL = "model";
    private static final String KEY_REG_NUMBER = "registration_number";
    private static final String KEY_INSURANCE = "insurance";
    private static final String KEY_POLICY_NUMBER = "policy_number";



    /*// TAGS Table - column names
    private static final String KEY_TAG_NAME = "tag_name";

    // NOTE_TAGS Table - column names
    private static final String KEY_TODO_ID = "todo_id";
    private static final String KEY_TAG_ID = "tag_id";*/

    // Table Create Statements
    // Users table create statement

    private static final String CREATE_TABLE_USERS = "CREATE TABLE "
            + TABLE_USERS +
            "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_NAME + " TEXT,"
            + KEY_LAST_NAME + " TEXT,"
            + KEY_EMAIL + " TEXT,"
            + KEY_PASS + " TEXT,"
            + KEY_PHONE_NUMBER + " TEXT,"
            + KEY_DRIVERS_LICENSE + " TEXT,"
            + KEY_EXPIRATION_DATE + " TEXT,"
            + KEY_ADDRESS + " TEXT"
            + ")";

    // Vehicles table create statement

    private static final String CREATE_TABLE_VEHICLES = "CREATE TABLE "
            + TABLE_VEHICLES +
            "("
            + KEY_REG_NUMBER + " INTEGER PRIMARY KEY,"
            + KEY_BRAND + " TEXT,"
            + KEY_MODEL + " TEXT,"
            + KEY_INSURANCE + " TEXT,"
            + KEY_POLICY_NUMBER + " TEXT"
            + ")";


    /*// Tag table create statement
    private static final String CREATE_TABLE_TAG = "CREATE TABLE " + TABLE_TAG
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TAG_NAME + " TEXT,"
            + KEY_CREATED_AT + " DATETIME" + ")";

    // todo_tag table create statement
    private static final String CREATE_TABLE_TODO_TAG = "CREATE TABLE "
            + TABLE_TODO_TAG + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_TODO_ID + " INTEGER," + KEY_TAG_ID + " INTEGER,"
            + KEY_CREATED_AT + " DATETIME" + ")";*/

    public DatabaseSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_VEHICLES);
        /*db.execSQL(CREATE_TABLE_TAG);
        db.execSQL(CREATE_TABLE_TODO_TAG);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VEHICLES);
        /*db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO_TAG);*/

        // create new tables
        onCreate(db);
    }

    //******************************************************************************************
    //******************************************************************************************

    /*
 * Creating a USER
 */
    //Generic method to create a complete DBUser
    public long createUserDB(DB_User DBUser) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, DBUser.getName());
        values.put(KEY_LAST_NAME, DBUser.getLastname());
        values.put(KEY_EMAIL, DBUser.getEmail());
        values.put(KEY_PASS, DBUser.getPass());
        values.put(KEY_PHONE_NUMBER, DBUser.getPhoneNumber());
        values.put(KEY_DRIVERS_LICENSE, DBUser.getDriverLicense());
        values.put(KEY_EXPIRATION_DATE, DBUser.getExpiration_date());

        // insert row
        long user_id = db.insert(TABLE_USERS, null, values);

        db.close();
        return user_id;
    }

    //Create user with email/user and password
    public long createUserDB(String email, String pass) {

        SQLiteDatabase db = this.getWritableDatabase();

        String str = "null";
        ContentValues values = new ContentValues();
        values.put(KEY_PASS, pass);
        values.put(KEY_EMAIL, email);
        values.put(KEY_NAME, str);
        values.put(KEY_LAST_NAME, str);
        values.put(KEY_PHONE_NUMBER, str);
        values.put(KEY_DRIVERS_LICENSE, str);
        values.put(KEY_EXPIRATION_DATE, str);
        values.put(KEY_ADDRESS, str);


        // insert row
        long user_id = db.insert(TABLE_USERS, null, values);

        db.close();
        return user_id;
    }


    //******************************************************************************************
    //******************************************************************************************

    /*
 * Updating a USER
 */

    //Update DBUser with email/DBUser and password
    public void updateUserDB(DB_User DBUser, long user_id) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, DBUser.getName());
        values.put(KEY_LAST_NAME, DBUser.getLastname());
        values.put(KEY_PHONE_NUMBER, DBUser.getPhoneNumber());
        values.put(KEY_DRIVERS_LICENSE, DBUser.getDriverLicense());
        values.put(KEY_EXPIRATION_DATE, DBUser.getExpiration_date());
        values.put(KEY_ADDRESS, DBUser.getAddress());

        Integer id = (int)(long)user_id;

        // updating row
        db.update(TABLE_USERS, values, KEY_ID + " = " + id, null);
        db.close();
    }


    //******************************************************************************************
    //******************************************************************************************

    /*
 * Checking USER and PASS
 */


    public int checkUserPassBD(String username, String pass){
        int success = -1;
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_USERS + " WHERE " + KEY_EMAIL + " = '" + username + "'";
        Log.e(LOG,selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor != null) {
            try {
                cursor.moveToFirst();
                String passSavedInDataBase = cursor.getString(cursor.getColumnIndex(KEY_PASS));
                if(pass.equals(passSavedInDataBase)){
                    success = cursor.getInt(cursor.getColumnIndex(KEY_ID));
                }
            }
            catch (Exception e){
                e.printStackTrace();
                return -1;
            }

        }

        return success;
    }


    /* Creating a VEHICLE
    */
    //Generic method to create a complete vehicle
    public long createVehicleDB(DB_Vehicle DBVehicle) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_BRAND, DBVehicle.getBrand());
        values.put(KEY_MODEL, DBVehicle.getModel());
        values.put(KEY_REG_NUMBER, DBVehicle.getRegistrationNumber());
        values.put(KEY_INSURANCE, DBVehicle.getInsurance());
        values.put(KEY_POLICY_NUMBER, DBVehicle.getPolicyNumber());

        // insert row
        long vehicle_id = db.insert(TABLE_VEHICLES, null, values);

        db.close();
        return vehicle_id;
    }

        /*
 * Updating a VEHICLE
 */

    //Update DB_Vehicle with vehicle_id
    public void updateVehicleDB(DB_Vehicle DBVehicle, long vehicle_id) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_BRAND, DBVehicle.getBrand());
        values.put(KEY_MODEL, DBVehicle.getModel());
        values.put(KEY_REG_NUMBER, DBVehicle.getRegistrationNumber());
        values.put(KEY_INSURANCE, DBVehicle.getInsurance());
        values.put(KEY_POLICY_NUMBER, DBVehicle.getPolicyNumber());

        Integer id = (int)(long)vehicle_id;

        // updating row
        db.update(TABLE_VEHICLES, values, KEY_ID + " = " + id, null);
        db.close();
    }



}


//******************************************************************************************
//******************************************************************************************

