package tw.com.sqlitedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    //決定資料庫名稱
    private final static String DATABASE_NAME = "store.db";
    //資料庫的版本
    private final static int VERSION = 1;

   public DBHelper(Context context){
       super(context,DATABASE_NAME,null,VERSION);
   }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //第一次執行時會建立資料表                            主鍵        流水編號(系統會自動加)
        String sql = "create  table  product(_id integer primary key autoincrement,name varchar(30),price int,amount int) ";
        db.execSQL(sql); //執行語法


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table product";  //刪除資料表
        db.execSQL(sql);

    }
}
