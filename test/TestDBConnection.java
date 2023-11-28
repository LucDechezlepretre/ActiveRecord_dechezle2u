import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestDBConnection {
    @Test
    public void testDBConnection()throws SQLException {
        DBConnection.setNomDB("testpersonne");
        Connection c1 = DBConnection.getConnection();
        Connection c2 = DBConnection.getConnection();
        assertEquals(c1, c2, "Les connections devrait être les mêmes");
    }
    @Test
    public void testSetDBName() throws SQLException{
        DBConnection.setNomDB("testpersonne");
        Connection c1 = DBConnection.getConnection();
        DBConnection.setNomDB("touiter");
        Connection c2 = DBConnection.getConnection();
        assertNotEquals(c1, c2, "Les connections devraient être différentes");
    }
}
