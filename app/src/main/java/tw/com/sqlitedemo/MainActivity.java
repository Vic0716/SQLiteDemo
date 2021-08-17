package tw.com.sqlitedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private DBHelper dbHelper = null;
    private Button btnAdd,btnUpdate,btnDel,btnQuery,btnMySQL;
    private EditText edtProduct,edtPrice,edtAmount,edtId;
    private TextView sqlData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        findViews();
    }

    protected void onDestroy(){
        super.onDestroy();
        dbHelper.close();
    }

    public void findViews(){
        btnAdd = findViewById(R.id.btnAdd);
        btnDel = findViewById(R.id.btnDel);
        btnQuery = findViewById(R.id.btnQuery);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnMySQL = findViewById(R.id.btnMySQL);
        sqlData = findViewById(R.id.sqlData);
        edtAmount = findViewById(R.id.edtAmount);
        edtId = findViewById(R.id.edtId);
        edtPrice = findViewById(R.id.edtPrice);
        edtProduct = findViewById(R.id.edtProduct);

        btnMySQL.setOnClickListener(v -> {

            new Thread( () ->{

                ConnMySQL con = new ConnMySQL();
                con.connect();
                final String data = con.getData();

                sqlData.post(new Runnable() {
                    @Override
                    public void run() {
                        sqlData.setText(data);
                    }
                });


            }).start();

        });

        btnAdd.setOnClickListener(addData);

        btnDel.setOnClickListener(delData);

        btnUpdate.setOnClickListener(updateData);

        btnQuery.setOnClickListener(v -> {
            Cursor cursor = getAllData();
            StringBuffer sb = new StringBuffer("結果: \n");
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int price = cursor.getInt(2);
                int amount = cursor.getInt(3);

                sb.append(id).append("-");
                sb.append(name).append("-");
                sb.append(price).append("-");
                sb.append(amount).append("\n");
            }
            sqlData.setText(sb.toString());
        });

    }

    //刪除資料
    private View.OnClickListener delData = v -> {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String id = edtId.getText().toString();
        db.delete("product","_id="+id,null);
        clearEditText();

    };

    private View.OnClickListener updateData = v -> {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues(); //將值塞入此
        values.put("name",edtProduct.getText().toString());
        values.put("price",Integer.parseInt(edtPrice.getText().toString()));
        values.put("amount",Integer.parseInt(edtAmount.getText().toString()));
        String id = edtId.getText().toString();

        db.update("product",values,"id="+id,null);
        clearEditText();

    };

    private View.OnClickListener addData = v -> {
      //寫入資料到SQLite Database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues(); //將值塞入此
        values.put("name",edtProduct.getText().toString());
        values.put("price",Integer.parseInt(edtPrice.getText().toString()));
        values.put("amount",Integer.parseInt(edtAmount.getText().toString()));
        //新增
        db.insert("product",null,values);
        //自訂方法
        clearEditText();

    };

    //查詢資料
    private Cursor getAllData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //String[] columns = {"_id","name","price","amount"};
        //SQLite專用
        //Cursor cursor = db.query("product",columns,null,null,null,null,null);

        String sql = "select _id,name,price,amount  from product";
        Cursor cursor = db.rawQuery(sql,null);

        return cursor;
    }


    //清空輸入的空格
    private void clearEditText() {
        edtProduct.setText("");
        edtPrice.setText("");
        edtAmount.setText("");
        edtId.setText("");
    }

}