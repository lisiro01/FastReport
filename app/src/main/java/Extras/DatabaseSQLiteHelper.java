package Extras;

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
    private static final String TABLE_USER_ACCIDENT = "user_accident";


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
    private static final String KEY_HOUR = "hour";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_IMAGE1 = "image1";
    private static final String KEY_IMAGE2 = "image2";
    private static final String KEY_IMAGE3 = "image3";
    private static final String KEY_EMAIL_ADDRESSEE = "email_addressee";

    // USER_VEHICLE Table - column names
    private static final String KEY_USER_VEHICLE_ID = "user_vehicle_id";
    private static final String KEY_UV_USER_ID = "user_id";
    private static final String KEY_UV_VEHICLE_ID = "vehicle_id";

    // USER_ACCIDENT Table - column names
    private static final String KEY_USER_ACCIDENT_ID = "user_accident_id";
    private static final String KEY_UA_USER_ID = "user_id";
    private static final String KEY_UA_ACCIDENT_ID = "accident_id";

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
            + KEY_DATE + " TEXT,"
            + KEY_HOUR + " TEXT,"
            + KEY_LOCATION + " TEXT,"
            + KEY_IMAGE1 + " BLOB,"
            + KEY_IMAGE2 + " BLOB,"
            + KEY_IMAGE3 + " BLOB,"
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

    // User_Accident table create statement
    private static final String CREATE_TABLE_USER_ACCIDENT = "CREATE TABLE "
            + TABLE_USER_ACCIDENT +
            "("
            + KEY_USER_ACCIDENT_ID + " INTEGER PRIMARY KEY,"
            + KEY_UA_USER_ID + " INTEGER,"
            + KEY_UA_ACCIDENT_ID + " INTEGER,"
            + "FOREIGN KEY(" + KEY_UA_USER_ID + ") REFERENCES " + TABLE_USERS + ","
            + "FOREIGN KEY(" + KEY_UA_ACCIDENT_ID + ") REFERENCES " + TABLE_ACCIDENT
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
        db.execSQL(CREATE_TABLE_USER_ACCIDENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VEHICLES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCIDENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_VEHICLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_ACCIDENT);

        // create new tables
        onCreate(db);
    }

//*************************************************************************************************************************************************
//*************************************************************************************************************************************************

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

        PasswordHashHelper passwordHashHelper = new PasswordHashHelper();
        String hashed_password = passwordHashHelper.hashPassword(pass);

        SQLiteDatabase db = this.getWritableDatabase();

        String str = "";
        ContentValues values = new ContentValues();
        values.put(KEY_PASS, hashed_password);
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
        PasswordHashHelper passwordHashHelper = new PasswordHashHelper();

        String selectQuery = "SELECT * FROM " + TABLE_USERS + " WHERE " + KEY_EMAIL + " = '" + username + "'";
        Log.e(LOG, selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor != null) {
            try {
                cursor.moveToFirst();
                String passSavedInDataBase = cursor.getString(cursor.getColumnIndex(KEY_PASS));
                if (passwordHashHelper.checkPassword(pass, passSavedInDataBase))
                    //if (pass.equals(passSavedInDataBase)) {
                    success = cursor.getInt(cursor.getColumnIndex(KEY_USER_ID));
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
 * Return User
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
        else {
            user.setLastname("apellidooo");
        }

        return user;
    }


//*************************************************************************************************************************************************
//*************************************************************************************************************************************************


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

        String selectQuery = "SELECT * FROM " + TABLE_VEHICLES + " WHERE " + KEY_VEHICLE_ID + " = '" + vehicle_id + "'";
        Log.e(LOG, selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor != null) {
            try {
                cursor.moveToFirst();
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





//*************************************************************************************************************************************************
//*************************************************************************************************************************************************

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
        values.put(KEY_HOUR, DBAccident.getHour());
        values.put(KEY_LOCATION, DBAccident.getLocation());
        values.put(KEY_IMAGE1, DBAccident.getImage1());
        values.put(KEY_IMAGE2, DBAccident.getImage2());
        values.put(KEY_IMAGE3, DBAccident.getImage3());
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
        values.put(KEY_HOUR, DBAccident.getHour());
        values.put(KEY_LOCATION, DBAccident.getLocation());
        values.put(KEY_IMAGE1, DBAccident.getImage1());
        values.put(KEY_IMAGE2, DBAccident.getImage2());
        values.put(KEY_IMAGE3, DBAccident.getImage3());
        values.put(KEY_EMAIL_ADDRESSEE, DBAccident.getEmail_addressee());

        Integer id = (int) (long) accident_id;

        // updating row
        db.update(TABLE_ACCIDENT, values, KEY_ACCIDENT_ID + " = " + id, null);
        db.close();
    }



    public int findAccidentDB(String date, String hour) {
        int success = -1;
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_ACCIDENT + " WHERE " + KEY_DATE + " = '" + date + "' and " + KEY_HOUR + " = '" + hour + "'";
        Log.e(LOG, selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor != null) {
            try {
                cursor.moveToFirst();
                success = cursor.getInt(cursor.getColumnIndex(KEY_ACCIDENT_ID));
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


    public DB_Accident getAccidentDB(long accident_id) {
        DB_Accident accident = new DB_Accident();
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_ACCIDENT + " WHERE " + KEY_ACCIDENT_ID + " = '" + accident_id + "'";
        Log.e(LOG, selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor != null) {
            try {
                cursor.moveToFirst();
                accident.setUser_id(cursor.getInt(cursor.getColumnIndex(KEY_USER_ID)));
                accident.setVehicle_id(cursor.getInt(cursor.getColumnIndex(KEY_VEHICLE_ID)));
                accident.setDate(cursor.getString(cursor.getColumnIndex(KEY_DATE)));
                accident.setHour(cursor.getString(cursor.getColumnIndex(KEY_HOUR)));
                accident.setLocation(cursor.getString(cursor.getColumnIndex(KEY_LOCATION)));
                accident.setImage1(cursor.getBlob(cursor.getColumnIndex(KEY_IMAGE1)));
                accident.setImage2(cursor.getBlob(cursor.getColumnIndex(KEY_IMAGE2)));
                accident.setImage3(cursor.getBlob(cursor.getColumnIndex(KEY_IMAGE3)));
                accident.setEmail_addressee(cursor.getString(cursor.getColumnIndex(KEY_EMAIL_ADDRESSEE)));


            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }

        return accident;
    }


//*************************************************************************************************************************************************
//*************************************************************************************************************************************************

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




//*************************************************************************************************************************************************
//*************************************************************************************************************************************************

    //Generic method to create a complete User_Vehicle relation
    public long createUserAccidentDB(long user, long accident) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_UA_USER_ID, user);
        values.put(KEY_UA_ACCIDENT_ID, accident);


        // insert row
        long user_accident_id = db.insert(TABLE_USER_ACCIDENT, null, values);

        db.close();
        return user_accident_id;
    }


//*************************************************************************************************************************************************
//*************************************************************************************************************************************************


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

//*************************************************************************************************************************************************
//*************************************************************************************************************************************************


    public ArrayList<String> getAllAccidentsByUserIdString(long user_id) {

        ArrayList<String> accidentList = new ArrayList<String>();

        String selectQuery = "SELECT * FROM " + TABLE_USER_ACCIDENT + " NATURAL JOIN " + TABLE_ACCIDENT +
        " WHERE " + KEY_UA_USER_ID + " = " + user_id;

        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        String date, hour;


        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                date = c.getString(c.getColumnIndex(KEY_DATE));
                hour = c.getString(c.getColumnIndex(KEY_HOUR));
                accidentList.add(date + " " + hour);
            } while (c.moveToNext());
        }

        ArrayList<String> accidentListInverted = new ArrayList<String>();

        for (int i = accidentList.size(); i > 0; i--){
            accidentListInverted.add(accidentList.get(i-1)); //para devolver los accidentes con el más reciente el primero
        }

        return accidentListInverted;
    }

/*

    public ArrayList<DB_Accident> getAllAccidentsByUserId(long user_id) {

        String selectQuery = "SELECT * FROM " + TABLE_USER_ACCIDENT + " NATURAL JOIN " + TABLE_ACCIDENT +
                " WHERE " + KEY_UA_USER_ID + " = " + user_id;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        ArrayList<DB_Accident> accidentList = new ArrayList<DB_Accident>();

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                DB_Accident a = new DB_Accident();

                a.setVehicle_id(c.getLong(c.getColumnIndex(KEY_BRAND)));
                a.setDate(tvFecha.toString());
                a.setLocation("localizacion" + vehicle_id);
                a.setImage1(image1);
                a.setImage2(image2);
                a.setImage3(image3);
                a.setEmail_addressee(email.getText().toString());

                a.setBrand(c.getString(c.getColumnIndex(KEY_BRAND)));
                a.setModel(c.getString(c.getColumnIndex(KEY_MODEL)));
                a.setRegistrationNumber(c.getString(c.getColumnIndex(KEY_REG_NUMBER)));

                vehiclesList.add(v);
            } while (c.moveToNext());
        }

        return vehiclesList;
    }
*/
}