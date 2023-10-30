package database.mysql;

import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends AbstractDAO implements GenericDAO<User, String> {
    public UserDAO(DBAccess dBaccess) {
        super(dBaccess);
    }

    @Override
    public List<User> getAll() {
        List<User> listOfUsers = new ArrayList<>();
        String sql = "SELECT * FROM User;";
        try {
            setupPreparedStatement(sql);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String firstName = resultSet.getString("firstName");
                String infix = resultSet.getString("infix");
                String lastName = resultSet.getString("lastName");
                String role = resultSet.getString("role");
                User user = new User(username, password, firstName, infix, lastName, role);
                listOfUsers.add(user);
            }
        } catch (SQLException foutmelding) {
            System.out.println(foutmelding.getMessage());
        }
        return listOfUsers;
    }

    @Override
    public User getOneByPK(String PK) {
        User user = null;
        String sql = "SELECT * FROM User WHERE userName = ?;";
        try {
            setupPreparedStatement(sql);
            preparedStatement.setString(1, PK);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String firstName = resultSet.getString("firstName");
                String infix = resultSet.getString("infix");
                String lastName = resultSet.getString("lastName");
                String role = resultSet.getString("role");
                user = new User(username, password, firstName, infix, lastName, role);
            }
        } catch (SQLException sqlFout) {
            System.out.println(sqlFout);
        }
        return user;
    }

    @Override
    public void storeOne(User user) {
        String sql = "INSERT INTO User VALUES(?, ?, ?, ?, ?, ?);";
        try {
            setupPreparedStatement(sql);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getInfix());
            preparedStatement.setString(5, user.getLastName());
            preparedStatement.setString(6, user.getRole());
            executeManipulateStatement();
        } catch (SQLException sqlFout) {
            System.out.println(sqlFout);
        }
    }
}
