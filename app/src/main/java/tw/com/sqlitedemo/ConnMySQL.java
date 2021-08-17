package tw.com.sqlitedemo;

import android.location.LocationManager;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnMySQL {

    String dbip = "db4free.net";
    int port = 3306;
    String dbName = "wtdemo";
    String url ="jdbc:mysql://"+dbip+":"+port+"/"+dbName;
    String user = "watson";
    String pwd ="123456789";

    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Log.i("LCC","驅動程式成功");
        }
        catch (Exception e){
            Log.i("LCC","驅動程式失敗");
            return;
        }
    }

    public String getData(){
        StringBuffer sb = new StringBuffer();
        try{
            Connection conn = DriverManager.getConnection(url,user,pwd);
            String sql = "select  * from member";

            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery(sql);

            while(rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                sb.append(id).append("-");
                sb.append(name).append("/n");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }

}
