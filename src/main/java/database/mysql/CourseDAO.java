package database.mysql;

import model.Cursus;
import testController.CursusTekstIOTemp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CursusDAO extends AbstractDAO implements GenericDAO<Cursus> {

    public CursusDAO(DBAccess dbAccess) {
        super(dbAccess);
    }

    @Override
    public List<Cursus> getAll() {
        String sql = "SELECT cursusNaam, coordinator, niveau FROM CursusTable"; // Adjust table name accordingly
        List<Cursus> cursusList = new ArrayList<>();
        try {
            setupPreparedStatement(sql);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                Cursus cursus = new Cursus(
                        resultSet.getString("cursusNaam"),
                        resultSet.getString("coordinator"),
                        resultSet.getString("niveau")
                );
                cursusList.add(cursus);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cursusList;
    }

    @Override
    public Cursus getOneByPK(Cursus cursus) {
        String sql = "SELECT cursusNaam, coordinator, niveau FROM CursusTable WHERE cursusNaam = ?"; // Adjust table name and primary key accordingly
        try {
            setupPreparedStatement(sql);
            preparedStatement.setString(1, cursus.getCursusNaam());
            ResultSet resultSet = executeSelectStatement();
            if (resultSet.next()) {
                return new Cursus(
                        resultSet.getString("cursusNaam"),
                        resultSet.getString("coordinator"),
                        resultSet.getString("niveau")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void storeOne(Cursus cursus) {
        String sql = "INSERT INTO CursusTable (cursusNaam, coordinator, niveau) VALUES (?, ?, ?)"; // Adjust table name accordingly
        try {
            setupPreparedStatementWithKey(sql);
            preparedStatement.setString(1, cursus.getCursusNaam());
            preparedStatement.setString(2, cursus.getCoordinator());
            preparedStatement.setString(3, cursus.getNiveau());
            executeInsertStatementWithKey();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertCursusFromCSVToDB() {
        List<Cursus> cursusList = CursusTekstIOTemp.loadCSV("yourCSVFileName.csv");  // Provide the appropriate file name
        for (Cursus cursus : cursusList) {
            storeOne(cursus);
        }
    }
}
