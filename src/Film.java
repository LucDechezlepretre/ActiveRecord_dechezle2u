import java.sql.*;

public class Film {
    private String titre;
    private int id;
    private int id_real;
    public Film(String titre, Personne personne){
        this.titre = titre;
        this.id = -1;
        this.id_real = personne.getId();
    }
    private Film(String titre, int id, int id_real){
        this.titre = titre;
        this.id = id;
        this.id_real = id_real;
    }

    public static void createTable() throws SQLException {
        DBConnection.setNomDB("testpersonne");
        Connection connection = DBConnection.getConnection();
        String createString = "CREATE TABLE Film (ID INTEGER AUTO_INCREMENT, TITRE varchar(40) NOT NULL, ID_REAL INT(11) NOT NULL, PRIMARY KEY (ID), FOREIGN KEY (ID_REAL) REFERENCES Personne(id)); ";
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(createString);
    }

    public static void deleteTable() throws SQLException{
        DBConnection.setNomDB("testpersonne");
        Connection connection = DBConnection.getConnection();
        String drop = "DROP TABLE Film";
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(drop);
    }

    public static Film findById(int i) throws SQLException{
        DBConnection.setNomDB("testpersonne");
        Connection connection = DBConnection.getConnection();
        PreparedStatement prepareStatement = connection.prepareStatement("select id, titre, id_real from Film where id = ?");
        prepareStatement.setInt(1, i);
        ResultSet res = prepareStatement.executeQuery();
        Film film = null;
        if(res.next()){
            film = new Film(res.getString("titre"), res.getInt("id"), res.getInt("id_real"));
        }
        return film;
    }

    public void save() throws SQLException, RealisateurAbsentException{
        if(this.id_real == -1){
            throw new RealisateurAbsentException();
        }
        if(this.id == -1){
            this.saveNew();
        }
        else{
            this.update();
        }
    }

    private void update() throws SQLException{
        DBConnection.setNomDB("testpersonne");
        Connection connection = DBConnection.getConnection();
        String SQLprep = "update Film set titre=?, id_real=? where id=?;";
        PreparedStatement prep = connection.prepareStatement(SQLprep);
        prep.setString(1, this.titre);
        prep.setInt(2, this.id_real);
        prep.setInt(3, this.id);
        prep.execute();
    }

    private void saveNew() throws SQLException{
        DBConnection.setNomDB("testpersonne");
        Connection connection = DBConnection.getConnection();
        String SQLPrep = "INSERT INTO Film (titre, id_real) VALUES (?,?);";
        PreparedStatement prep;
        // l'option RETURN_GENERATED_KEYS permet de recuperer l'id (car
        // auto-increment)
        prep = connection.prepareStatement(SQLPrep, Statement.RETURN_GENERATED_KEYS);
        prep.setString(1, this.titre);
        prep.setInt(2, this.id_real);
        prep.executeUpdate();
        ResultSet res = prep.getGeneratedKeys();
        if(res.next()) {
            this.id = (res.getInt(1));
        }
    }

    public String getTitre() {
        return titre;
    }

    public int getId() {
        return id;
    }

    public int getId_real() {
        return id_real;
    }

    public Personne getRealisateur() throws SQLException{
        DBConnection.setNomDB("testpersonne");
        Connection connection = DBConnection.getConnection();
        PreparedStatement prepareStatement = connection.prepareStatement("select id, nom, prenom from Personne where id = ?");
        prepareStatement.setString(1, this.id_real+"");
        ResultSet res = prepareStatement.executeQuery();
        Personne p = null;
        if(res.next()){
            p = new Personne(res.getString("nom"), res.getString("prenom"));
            p.setId(Integer.valueOf(res.getString("id")));
        }
        return p;
    }
}
