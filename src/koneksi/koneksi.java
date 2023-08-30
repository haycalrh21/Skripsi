
package koneksi;
//jdbc = plugin
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource ;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.Connection;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class koneksi {
    
    private static Connection Koneksi;
    public Connection connect;
  
    public static Connection koneksiDatabase(){
        if(Koneksi==null){
            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setURL("jdbc:mysql://localhost/cvagajayacenter");//konek database
            dataSource.setUser("root"); //user sbg  adm di php
            dataSource.setPassword("");// pw dikosongkan
            try{//memulai koneksi
                Koneksi = dataSource.getConnection();
            }catch (SQLException ex){
                JOptionPane.showMessageDialog(null,"Erorr koneksi :"+ ex.getMessage());
            }
         }
        return Koneksi;
        }
    public Connection getConnection(){
        throw new UnsupportedOperationException("not supported yet");
    }


 }

