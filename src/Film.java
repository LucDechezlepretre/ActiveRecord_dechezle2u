import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Film {
    private String titre;
    private int id;
    private int id_real;
    public Film(String titre){
        this.titre = titre;
        this.id = -1;
        this.id_real = -1;
    }
    private Film(String titre, int id, int id_real){
        this.titre = titre;
        this.id = id;
        this.id_real = id_real;
    }

    public static void createTable() throws SQLException {
        DBConnection.setNomDB("testpersonne");
        Connection connection = DBConnection.getConnection();
        String createString = 
                "CREATE TABLE `Film` (\n" +
                "  `id` int(11) NOT NULL,\n" +
                "  `titre` varchar(40) NOT NULL,\n" +
                "  `id_rea` int(11) DEFAULT NULL\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=latin1;"+
                "ALTER TABLE `Film`\n" +
                "  ADD PRIMARY KEY (`id`),\n" +
                "  ADD KEY `id_rea` (`id_rea`);" +
                "ALTER TABLE `Film`\n" +
                "  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;"+
                "ALTER TABLE `Film`\n" +
                "  ADD CONSTRAINT `film_ibfk_1` FOREIGN KEY (`id_rea`) REFERENCES `Personne` (`id`);";
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(createString);
    }
}
