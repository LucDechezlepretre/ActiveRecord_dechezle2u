import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class Personne {
    private int id;
    private String nom;
    private String prenom;

    public Personne(String nom, String prenom) {
        this.id = -1;
        this.nom = nom;
        this.prenom = prenom;
    }


    public int getId() {
        return id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static ArrayList<Personne> findAll() throws SQLException {
        DBConnection.setNomDB("testpersonne");
        Connection connection = DBConnection.getConnection();
        ArrayList<Personne> personnes = new ArrayList<Personne>();
        PreparedStatement preparedStatement = connection.prepareStatement("select id, nom, prenom from Personne");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            Personne p = new Personne(resultSet.getString("nom"), resultSet.getString("prenom"));
            p.id = Integer.valueOf(resultSet.getString("id"));
            personnes.add(p);
        }
        return personnes;
    }
    public static Personne findById(int id) throws SQLException{
        DBConnection.setNomDB("testpersonne");
        Connection connection = DBConnection.getConnection();
        PreparedStatement prepareStatement = connection.prepareStatement("select id, nom, prenom from Personne where id = ?");
        prepareStatement.setString(1, id+"");
        ResultSet res = prepareStatement.executeQuery();
        Personne p = null;
        if(res.next()){
            p = new Personne(res.getString("nom"), res.getString("prenom"));
            p.id = Integer.valueOf(res.getString("id"));
        }
        return p;
    }
    public static ArrayList<Personne> findByName(String name) throws SQLException{
        DBConnection.setNomDB("testpersonne");
        Connection connection = DBConnection.getConnection();
        PreparedStatement prepareStatement = connection.prepareStatement("select id, nom, prenom from Personne where nom like ?");
        prepareStatement.setString(1, name);
        ResultSet res = prepareStatement.executeQuery();
        ArrayList<Personne> listPersonne = new ArrayList<Personne>();
        while(res.next()){
            Personne p = new Personne(res.getString("nom"), res.getString("prenom"));
            p.id = Integer.valueOf(res.getString("id"));
            listPersonne.add(p);
        }
        return listPersonne;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public static void createTable() throws SQLException{
        DBConnection.setNomDB("testpersonne");
        Connection connection = DBConnection.getConnection();
        String createString = "CREATE TABLE Personne ( " + "ID INTEGER  AUTO_INCREMENT, "
                + "NOM varchar(40) NOT NULL, " + "PRENOM varchar(40) NOT NULL, " + "PRIMARY KEY (ID))";
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(createString);
    }

    public static void deleteTable() throws SQLException{
        DBConnection.setNomDB("testpersonne");
        Connection connection = DBConnection.getConnection();
        String drop = "DROP TABLE Personne";
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(drop);
    }

    @Override
    public String toString() {
        return "Personne{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Personne personne = (Personne) o;
        return id == personne.id && Objects.equals(nom, personne.nom) && Objects.equals(prenom, personne.prenom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, prenom);
    }

    public void save() throws SQLException{
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
        String SQLprep = "update Personne set nom=?, prenom=? where id=?;";
        PreparedStatement prep = connection.prepareStatement(SQLprep);
        prep.setString(1, this.nom);
        prep.setString(2, this.prenom);
        prep.setInt(3, this.id);
        prep.execute();
    }

    private void saveNew() throws SQLException{
        DBConnection.setNomDB("testpersonne");
        Connection connection = DBConnection.getConnection();
        String SQLPrep = "INSERT INTO Personne (nom, prenom) VALUES (?,?);";
        PreparedStatement prep;
        // l'option RETURN_GENERATED_KEYS permet de recuperer l'id (car
        // auto-increment)
        prep = connection.prepareStatement(SQLPrep, Statement.RETURN_GENERATED_KEYS);
        prep.setString(1, this.nom);
        prep.setString(2, this.prenom);
        prep.executeUpdate();
        ResultSet res = prep.getGeneratedKeys();
        if(res.next()) {
            this.id = (res.getInt(1));
        }
    }

    public void delete() throws SQLException{
        DBConnection.setNomDB("testpersonne");
        Connection connection = DBConnection.getConnection();
        String sql = "DELETE FROM Personne WHERE id = " + this.id;
        PreparedStatement prep;
        // l'option RETURN_GENERATED_KEYS permet de recuperer l'id (car
        // auto-increment)
        prep = connection.prepareStatement(sql);
        prep.executeUpdate();
        this.id = -1;
    }
}
