
package Utils;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Dao {
    public PreparedStatement st;
    public ResultSet rs;
    private  String url;
    private  String user;
    private  String password;
    public Connection conn;
    public Dao(){
        try {
            // New Update . Use com.mysql.cj.jdbc.Driver
            // Instead of       com.mysql.jdbc.Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            url = "jdbc:mysql://localhost:3306/faceRecognition";
            user = "root";
            password = "mrcool99";
            conn = DriverManager.getConnection(url,user,password);
            System.out.println("OKE");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void executeSQL(String SQL){
        try{
            st = conn.prepareStatement(SQL);
            rs = st.executeQuery();
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
    
    public void destroy() throws SQLException{
        conn.close();
    }
    
    public static void main(String[] args) throws SQLException {
        String qr = "insert into user(fname) values(?)";
        PreparedStatement st = new Dao().conn.prepareStatement(qr);
        st.setString(1, "TienAnh");
        st.executeUpdate();
        st.close();
    }
}
