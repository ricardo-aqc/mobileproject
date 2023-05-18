package edu.udem.examenfinalmovilesricardoquintanilla;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "topcarros.db";


    // columnas de la tabla pelicula
    public static final String CAR = "car";
    public static final String COLUMN_TOP_CAR_ID = "_id";
    public static final String COLUMN_TOP_CAR_NAME = "name";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_MOVIE_TABLE = "CREATE TABLE " + CAR + "("
                + COLUMN_TOP_CAR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TOP_CAR_NAME + " TEXT NOT NULL "
                + ")";


        sqLiteDatabase.execSQL(CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Borramos las tablas si existen
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CAR);

        // Por si las duda, volvemos a crear las tablas
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onOpen(SQLiteDatabase sqLiteDatabase) {
        super.onOpen(sqLiteDatabase);

        // Habilitamos las restricciones de llave externa como ON UPDATE CASCADE, ON DELETE CASCADE
        sqLiteDatabase.execSQL("PRAGMA foreign_keys=ON;");
    }


    //METODOS CARROS


    //METODO PARA INSERTAR DATOS en Top Car

    public boolean insertDataTopCar(String topcarid, String topcarname) {
        //Como estamos haciendo un insert utilizamos getWritableDatabase() si es para solo un SELECT se utilizaria
        //getReadableDatabase()
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TOP_CAR_ID, topcarid);
        contentValues.put(COLUMN_TOP_CAR_NAME, topcarname);

        //nullColumnHack puede ser null o no.
        long result = database.insert(CAR, null, contentValues);
        //Imprimir en el logcat para checar la realizacion correcta
        System.out.println(result);

        if (result == -1)
            return false;
        else
            return true;
    }


    //El cursor va a poder administrar los datos que puedes recuperar del query

    public Cursor retrieveData() {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + CAR, null);
        return cursor;
    }

    public Cursor retrieveID() {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT " + COLUMN_TOP_CAR_ID + " FROM "
                + CAR + " ORDER BY " + COLUMN_TOP_CAR_ID + " DESC LIMIT 1 ", null);
        return cursor;
    }



    public int deleteDataTopMovie(String deletecarnamestring) {
        SQLiteDatabase database = this.getWritableDatabase();
        return database.delete(CAR, "name = ?", new String[]{deletecarnamestring});
    }


}