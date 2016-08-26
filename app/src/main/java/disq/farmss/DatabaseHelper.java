package disq.farmss;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vishal on 2/7/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    String TAG = "disq.farmss:";
    public static final int DATABASE_VERSION= 4;
    public static final String DATABASE_NAME = "farmss.db";
    //Customer_Table
    public static final String TABLE_NAME = "tbl_customer";
    public static final String CUST_MOBILE = "cust_mobile";
    public static final String CUST_NAME = "cust_name";
    public static final String CUST_PASSWORD= "cust_pass";
    public static final String CUST_DISTRICT ="cust_district";
    public static final String CUST_TEHSIL = "cust_tehsil";
    public static final String CUST_VILLAGE = "cust_village";
    public static final String CUST_PINCODE ="cust_pincode";

    //Soil_Test_Table
    public static final String TABLE_SOILTEST = "tbl_soiltest";
    public static final String ST_MOBILE = "st_mobile";
    public static final String ST_TRANSACTION_ID="st_tid";
    public static final String ST_AREATYPE ="st_areatype";
    public static final String ST_AREAVALUE = "st_areavalue";
    public static final String ST_PH_VALUE = "st_ph";
    public static final String ST_N_VALUE ="st_n";
    public static final String ST_P_VALUE ="st_p";
    public static final String ST_K_VALUE ="st_k";
    public static final String ST_SELECTED_CROP = "st_crop";
    public static final String ST_FN_VALUE ="st_fn";
    public static final String ST_FP_VALUE ="st_fp";
    public static final String ST_FK_VALUE ="st_fk";

    SQLiteDatabase db;
    long res = 0;

    private static final String TABLE_CUSTOMER_CREATE ="create table tbl_customer (cust_mobile text primary key not null , " +
            "cust_name text not null, cust_pass text not null, cust_district text, cust_tehsil text, cust_village text, cust_pincode text );";

    private static final String TABLE_SOILTEST_CREATE="create table tbl_soiltest (st_mobile text not null ,st_tid text not null , st_areatype text , st_areavalue text ,  "+
            "st_ph text ,st_n text , st_p text , st_k text , st_crop, st_fn text , st_fp text , st_fk text );";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CUSTOMER_CREATE);
        db.execSQL(TABLE_SOILTEST_CREATE);
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query  = "DROP TABLE IF EXISTS "+TABLE_NAME;
        String query2 = "DROP TABLE IF EXISTS "+TABLE_SOILTEST;
        db.execSQL(query);
        db.execSQL(query2);
        this.onCreate(db);
    }

    public boolean insertData(UserDataDBMethod c) {
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(CUST_MOBILE,c.getMobile());
        cv.put(CUST_NAME,c.getName());
        cv.put(CUST_PASSWORD,c.getPass());
        cv.put(CUST_DISTRICT,c.getDistrict());
        cv.put(CUST_TEHSIL,c.getTehsil());
        cv.put(CUST_VILLAGE,c.getVillage());
        cv.put(CUST_PINCODE,c.getPincode());
        db.insert(TABLE_NAME, null, cv);
        db.close();
        return true;
    }

    /*public String getUserData(UserDataDBMethod c){
        db = this.getReadableDatabase();
        String query = "select * from "+TABLE_NAME;

    }*/

    public String validateUser(String mobile) {
        db = this.getReadableDatabase();
        String query = "select cust_mobile, cust_pass from "+TABLE_NAME;
        Cursor c = db.rawQuery(query , null);
        String a ;
        String b = "Not found";
        if (c.moveToFirst()){
            do {
                a = c.getString(0);
                if (a.equals(mobile)){
                    b = c.getString(1);
                    break;
                }
            }while (c.moveToNext());
        }
        return b;
    }

    public boolean validateMobile(String new_mobile) {
        boolean v = true;
        db = this.getReadableDatabase();
        String q = "select cust_mobile from "+TABLE_NAME;
        Cursor cur = db.rawQuery(q , null);
        String m;
        if (cur.moveToFirst()){
            do {
              m =cur.getString(0);
                if (m.equals(new_mobile)){
                    v = false;
                    break;
                }
            }while (cur.moveToNext());
        }
        return v;
    }

    public long insertSoilTestData(SoilTestResultDBMethod str) {
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ST_MOBILE,str.getMobile());
        cv.put(ST_TRANSACTION_ID,str.getTID());
        cv.put(ST_AREATYPE,str.getAreaType());
        cv.put(ST_AREAVALUE,str.getAreaValue());
        cv.put(ST_PH_VALUE,str.getpH());
        cv.put(ST_N_VALUE,str.getN());
        cv.put(ST_P_VALUE,str.getP());
        cv.put(ST_K_VALUE,str.getK());
        cv.put(ST_SELECTED_CROP,str.getCrop());
        cv.put(ST_FN_VALUE,str.getFN());
        cv.put(ST_FP_VALUE,str.getFP());
        cv.put(ST_FK_VALUE,str.getFK());

        res=db.insert(TABLE_SOILTEST, null, cv);
        db.close();
        return res;

    }
}
