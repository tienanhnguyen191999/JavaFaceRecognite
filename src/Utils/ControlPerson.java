
package Utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ControlPerson {
    Dao conn = new Dao();
    public void insert(ModelPerson m) throws SQLException{
        String qr = "insert into user(fname,lname,dob) values(?,?,?)";
        PreparedStatement st = conn.conn.prepareStatement(qr);
        st.setString(1, m.getFname());
        st.setString(2, m.getLname());
        st.setString(3, m.getDob());
        st.executeUpdate();
        conn.destroy();
    }
}
