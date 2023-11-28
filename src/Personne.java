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
        if(res.next()){
            Personne p = new Personne(res.getString("nom"), res.getString("prenom"));
            p.id = Integer.valueOf(res.getString("id"));
            listPersonne.add(p);
        }
        return listPersonne;
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
}
