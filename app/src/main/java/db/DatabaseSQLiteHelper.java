package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.lisis.charles.fastreport.DB_Accident;
import com.lisis.charles.fastreport.DB_User;
import com.lisis.charles.fastreport.DB_Vehicle;

import java.util.ArrayList;


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
    private static final String TABLE_ACCIDENT = "accident";
    private static final String TABLE_USER_VEHICLE = "user_vehicle";


    // USERS Table - column names
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_LAST_NAME = "last_name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASS = "pass";
    private static final String KEY_PHONE_NUMBER = "phone_number";
    private static final String KEY_DRIVERS_LICENSE = "drivers_license";
    private static final String KEY_EXPIRATION_DATE = "expiration_date";
    private static final String KEY_ADDRESS = "address";

    // VEHICLES Table - column names
    private static final String KEY_VEHICLE_ID = "vehicle_id";
    private static final String KEY_BRAND = "brand";
    private static final String KEY_MODEL = "model";
    private static final String KEY_REG_NUMBER = "registration_number";
    private static final String KEY_INSURANCE = "insurance";
    private static final String KEY_POLICY_NUMBER = "policy_number";

    // Accident Table - column names
    private static final String KEY_ACCIDENT_ID = "accident_id";
    private static final String KEY_A_USER_ID = "user_id";
    private static final String KEY_A_VEHICLE_ID = "vehicle_id";
    private static final String KEY_DATE = "date";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_EMAIL_ADDRESSEE = "email_addressee";

    // USER_VEHICLE Table - column names
    private static final String KEY_USER_VEHICLE_ID = "user_vehicle_id";
    private static final String KEY_UV_USER_ID = "user_id";
    private static final String KEY_UV_VEHICLE_ID = "vehicle_id";


    // Table Create Statements

    // Users table create statement

    private static final String CREATE_TABLE_USERS = "CREATE TABLE "
            + TABLE_USERS +
            "("
            + KEY_USER_ID + " INTEGER PRIMARY KEY,"
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
            + KEY_VEHICLE_ID + " INTEGER PRIMARY KEY,"
            + KEY_REG_NUMBER + " TEXT,"
            + KEY_BRAND + " TEXT,"
            + KEY_MODEL + " TEXT,"
            + KEY_INSURANCE + " TEXT,"
            + KEY_POLICY_NUMBER + " TEXT"
            + ")";


    // Accident table create statement
    private static final String CREATE_TABLE_ACCIDENT = "CREATE TABLE "
            + TABLE_ACCIDENT +
            "("
            + KEY_ACCIDENT_ID + " INTEGER PRIMARY KEY,"
            + KEY_A_USER_ID + " INTEGER,"
            + KEY_A_VEHICLE_ID + " INTEGER,"
            + KEY_DATE + " DATETIME,"
            + KEY_LOCATION + " TEXT,"
            + KEY_EMAIL_ADDRESSEE + " TEXT,"
            + "FOREIGN KEY(" + KEY_A_USER_ID + ") REFERENCES " + TABLE_USERS + ","
            + "FOREIGN KEY(" + KEY_A_VEHICLE_ID + ") REFERENCES " + TABLE_VEHICLES
            + ")";

    // User_Vehicle table create statement
    private static final String CREATE_TABLE_USER_VEHICLE = "CREATE TABLE "
            + TABLE_USER_VEHICLE +
            "("
            + KEY_USER_VEHICLE_ID + " INTEGER PRIMARY KEY,"
            + KEY_UV_USER_ID + " INTEGER,"
            + KEY_UV_VEHICLE_ID + " INTEGER,"
            + "FOREIGN KEY(" + KEY_UV_USER_ID + ") REFERENCES " + TABLE_USERS + ","
            + "FOREIGN KEY(" + KEY_UV_VEHICLE_ID + ") REFERENCES " + TABLE_VEHICLES
            + ")";

    public DatabaseSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_VEHICLES);
        db.execSQL(CREATE_TABLE_ACCIDENT);
        db.execSQL(CREATE_TABLE_USER_VEHICLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VEHICLES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCIDENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_VEHICLE);

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

        String str = "";
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

        Integer id = (int) (long) user_id;

        // updating row
        db.update(TABLE_USERS, values, KEY_USER_ID + " = " + id, null);
        db.close();
    }


    /*
 * Checking USER and PASS
 */


    public int checkUserPassBD(String username, String pass) {
        int success = -1;
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_USERS + " WHERE " + KEY_EMAIL + " = '" + username + "'";
        Log.e(LOG, selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor != null) {
            try {
                cursor.moveToFirst();
                String passSavedInDataBase = cursor.getString(cursor.getColumnIndex(KEY_PASS));
                if (pass.equals(passSavedInDataBase)) {
                    success = cursor.getInt(cursor.getColumnIndex(KEY_USER_ID));
                }
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }

        }

        return success;
    }



    /*
 * Find User
 */


    public int findUserDB(String email) {
        int success = -1;
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_USERS + " WHERE " + KEY_EMAIL + " = '" + email + "'";
        Log.e(LOG, selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor != null) {
            try {
                cursor.moveToFirst();
                success = cursor.getInt(cursor.getColumnIndex(KEY_USER_ID));
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }

        }

        return success;
    }


        /*
 * Resturn User
 */


    public DB_User getUserDB(long user_id) {
        DB_User user = new DB_User();
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_USERS + " WHERE " + KEY_USER_ID + " = '" + user_id + "'";
        Log.e(LOG, selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor != null) {
            try {
                cursor.moveToFirst();
                user.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
                user.setLastname(cursor.getString(cursor.getColumnIndex(KEY_LAST_NAME)));
                user.setAddress(cursor.getString(cursor.getColumnIndex(KEY_ADDRESS)));
                user.setDriverLicense(cursor.getString(cursor.getColumnIndex(KEY_DRIVERS_LICENSE)));
                user.setExpiration_date(cursor.getString(cursor.getColumnIndex(KEY_EXPIRATION_DATE)));
                user.setPhoneNumber(cursor.getString(cursor.getColumnIndex(KEY_PHONE_NUMBER)));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }

        return user;
    }


//******************************************************************************************
//******************************************************************************************


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

        Integer id = (int) (long) vehicle_id;

        // updating row
        db.update(TABLE_VEHICLES, values, KEY_VEHICLE_ID + " = " + id, null);
        db.close();
    }



    /*
 * Find a vehicle
 */


    public int findVehicleDB(String registrationNumber) {
        int success = -1;
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_VEHICLES + " WHERE " + KEY_REG_NUMBER + " = '" + registrationNumber + "'";
        Log.e(LOG, selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor != null) {
            try {
                cursor.moveToFirst();
                success = cursor.getInt(cursor.getColumnIndex(KEY_VEHICLE_ID));
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }

        }

        return success;
    }


            /*
 * Resturn User
 */


    public DB_Vehicle getVehicleDB(long vehicle_id) {
        DB_Vehicle vehicle = new DB_Vehicle();
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_VEHICLES + " WHERE " + KEY_USER_ID + " = '" + vehicle_id + "'";
        Log.e(LOG, selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor != null) {
            try {
                vehicle.setBrand(cursor.getString(cursor.getColumnIndex(KEY_BRAND)));
                vehicle.setModel(cursor.getString(cursor.getColumnIndex(KEY_MODEL)));
                vehicle.setRegistrationNumber(cursor.getString(cursor.getColumnIndex(KEY_REG_NUMBER)));
                vehicle.setInsurance(cursor.getString(cursor.getColumnIndex(KEY_INSURANCE)));
                vehicle.setPolicyNumber(cursor.getString(cursor.getColumnIndex(KEY_POLICY_NUMBER)));

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }

        return vehicle;
    }


//******************************************************************************************
//******************************************************************************************

    /*
 * Creating an Accident
 */


    //Generic method to create a complete DBAccident
    public long createAccidentDB(DB_Accident DBAccident) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_USER_ID, DBAccident.getUser_id());
        values.put(KEY_VEHICLE_ID, DBAccident.getVehicle_id());
        values.put(KEY_DATE, DBAccident.getDate());
        values.put(KEY_LOCATION, DBAccident.getLocation());
        values.put(KEY_EMAIL_ADDRESSEE, DBAccident.getEmail_addressee());

        // insert row
        long accident_id = db.insert(TABLE_ACCIDENT, null, values);

        db.close();
        return accident_id;
    }


    /*
 * Updating an ACCIDENT
 */

    //Updating an Accident with accident_id
    public void updateAccidentDB(DB_Accident DBAccident, long accident_id) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_USER_ID, DBAccident.getUser_id());
        values.put(KEY_VEHICLE_ID, DBAccident.getVehicle_id());
        values.put(KEY_DATE, DBAccident.getDate());
        values.put(KEY_LOCATION, DBAccident.getLocation());
        values.put(KEY_EMAIL_ADDRESSEE, DBAccident.getEmail_addressee());

        Integer id = (int) (long) accident_id;

        // updating row
        db.update(TABLE_ACCIDENT, values, KEY_ACCIDENT_ID + " = " + id, null);
        db.close();
    }

//******************************************************************************************
//******************************************************************************************

    /*
 * Creating a User_Vehicle
 */


    //Generic method to create a complete User_Vehicle relation
    public long createUserVehicleDB(long user, long vehicle) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_UV_USER_ID, user);
        values.put(KEY_UV_VEHICLE_ID, vehicle);


        // insert row
        long user_vehicle_id = db.insert(TABLE_USER_VEHICLE, null, values);

        db.close();
        return user_vehicle_id;
    }


    /*
 * Updating a User_Vehicle row
 */

    //Updating a User_Vehicle relation with the id
    public void updateUserVehicleDB(int user, int vehicle, int user_vehicle_id) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_UV_USER_ID, user);
        values.put(KEY_UV_VEHICLE_ID, vehicle);

        Integer id = (int) (long) user_vehicle_id;

        // updating row
        db.update(TABLE_USER_VEHICLE, values, KEY_USER_VEHICLE_ID + " = " + id, null);
        db.close();
    }


    /*
* getting all vehicles under single user_id
* */
    public ArrayList<DB_Vehicle> getAllVehiclesByUserId(long user_id) {

        String selectQuery = "SELECT * FROM " + TABLE_USER_VEHICLE + " NATURAL JOIN " + TABLE_VEHICLES +
                " WHERE " + KEY_UV_USER_ID + " = " + user_id;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        ArrayList<DB_Vehicle> vehiclesList = new ArrayList<DB_Vehicle>();

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                DB_Vehicle v = new DB_Vehicle();
                v.setBrand(c.getString(c.getColumnIndex(KEY_BRAND)));
                v.setModel(c.getString(c.getColumnIndex(KEY_MODEL)));
                v.setRegistrationNumber(c.getString(c.getColumnIndex(KEY_REG_NUMBER)));

                vehiclesList.add(v);
            } while (c.moveToNext());
        }

        return vehiclesList;
    }


    /*
* getting all vehicles under single user_id
* */
    public ArrayList<String> getAllVehiclesByUserIdString(long user_id) {

        String selectQuery = "SELECT * FROM " + TABLE_USER_VEHICLE + " NATURAL JOIN " + TABLE_VEHICLES +
                " WHERE " + KEY_UV_USER_ID + " = " + user_id;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        ArrayList<String> vehiclesList = new ArrayList<String>();
        String matricula;

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                matricula = c.getString(c.getColumnIndex(KEY_REG_NUMBER));
                vehiclesList.add(matricula);
            } while (c.moveToNext());
        }

        return vehiclesList;
    }


}