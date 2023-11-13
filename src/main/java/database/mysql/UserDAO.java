package database.mysql;

import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends AbstractDAO implements GenericDAO<User> {
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
                int userId = resultSet.getInt("userId");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String firstName = resultSet.getString("firstName");
                String infix = resultSet.getString("infix");
                String lastName = resultSet.getString("lastName");
                String role = resultSet.getString("role");
                User user = new User(userId, username, password, firstName, infix, lastName, role);
                listOfUsers.add(user);
            }
        } catch (SQLException foutmelding) {
            System.out.println(foutmelding.getMessage());
        }
        return listOfUsers;
    }

    @Override
    public User getOneById(int userId) {
        User user = null;
        String sql = "SELECT * FROM User WHERE userId = ?;";
        try {
            setupPreparedStatement(sql);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String firstName = resultSet.getString("firstName");
                String infix = resultSet.getString("infix");
                String lastName = resultSet.getString("lastName");
                String role = resultSet.getString("role");
                user = new User(userId, username, password, firstName, infix, lastName, role);
            }
        } catch (SQLException sqlFout) {
            System.out.println(sqlFout);
        }
        return user;
    }

    @Override
    public void storeOne(User user) {
        String sql = "INSERT INTO User(userName, password, firstName, infix, lastName, role) VALUES(?, ?, ?, ?, ?, ?);";
        try {
            setupPreparedStatement(sql);
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getInfix());
            preparedStatement.setString(5, user.getLastName());
            preparedStatement.setString(6, user.getRole());
            preparedStatement.executeUpdate();
        } catch (SQLException sqlFout) {
            System.out.println(sqlFout);
        }
    }

    @Override
    public void updateOne(User user) {
        String sql = "UPDATE User SET userName = ?, password = ?, firstName = ?, infix = ?, lastName = ?, role = ? WHERE userId = ?;";
        try {
            setupPreparedStatement(sql);
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getInfix());
            preparedStatement.setString(5, user.getLastName());
            preparedStatement.setString(6, user.getRole());
            preparedStatement.setInt(7, user.getUserId());
            preparedStatement.executeUpdate();
        } catch (SQLException sqlFout) {
            System.out.println(sqlFout);
        }
    }

    public void deleteOne(User user){
        String sql = "DELETE FROM user WHERE userId = ?;";
        try {
            setupPreparedStatement(sql);
            preparedStatement.setInt(1, user.getUserId());
            executeManipulateStatement();
        } catch (SQLException sqlerror) {
            System.out.println(sqlerror.getMessage());
        }
    }

    public int countByRole(String userRole) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM User WHERE role = ?;";
        try {
            setupPreparedStatement(sql);
            preparedStatement.setString(1, userRole);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                count = resultSet.getInt("COUNT(*)");
            }
        } catch (SQLException sqlFout) {
            System.out.println(sqlFout);
        }
        return count;
    }

    public User getUserByName(String userName) {
        User user = null;
        String sql = "SELECT * FROM User WHERE userName = ?";
        try {
            setupPreparedStatement(sql);
            preparedStatement.setString(1, userName);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                int userId = resultSet.getInt("userId");
                String password = resultSet.getString("password");
                String firstName = resultSet.getString("firstName");
                String infix = resultSet.getString("infix");
                String lastName = resultSet.getString("lastName");
                String role = resultSet.getString("role");
                user = new User(userId, userName, password, firstName, infix, lastName, role);
            }
        } catch (SQLException sqlerror) {
            System.out.println(sqlerror.getMessage());
        }
        return user;
    }
}
