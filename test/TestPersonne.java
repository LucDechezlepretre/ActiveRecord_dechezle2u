import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPersonne {
    @Test
    public void testFindById() throws SQLException {
        Personne p1 = new Personne("Spielberg", "Steven");
        p1.setId(1);
        Personne p2 = Personne.findById(1);
        assertEquals(p1, p2, "Les deux personnes devraient être identique");
    }
}
