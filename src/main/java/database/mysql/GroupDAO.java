//package database.mysql;
//
//import model.User;
//import model.Group;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class GroupDAO extends AbstractDAO implements GenericDAO<Group, String> {
//    public GroupDAO(DBAccess dbAccess) {
//        super(dbAccess);
//    }
//
//    @Override
//    public List<Group> getAll() {
//        List<Group> questionList = new ArrayList<>();
//        String sql = "SELECT * FROM group;";
//        Group group = null;
//        UserDAO userDAO = new UserDAO(dbAccess);
//        try {
//            setupPreparedStatement(sql);
//            ResultSet resultSet = executeSelectStatement();
//            while (resultSet.next()) {
//                String courseName = resultSet.getString("courseName");
//                String groupName = resultSet.getString("groupName");
//                int maxNumberStudents = resultSet.getInt("maxNumberStudents");
//                String teacher = resultSet.getString("teacher");
//                User user = userDAO.getOneByPK(teacher);
//                group = new Group(courseName, groupName, maxNumberStudents, user);
//                questionList.add(group);
//            }
//        } catch (SQLException sqlFout){
//            System.out.println("SQL fout: " + sqlFout.getMessage());
//        }
//        return questionList;
//    }
//
//    @Override
//    public Group getOneByPK(String groupName) {
//        Group group = null;
//        UserDAO userDAO = new UserDAO(dbAccess);
//        CourseDAO courseDAO = new CourseDAO(dbAccess);
//        String sql = "SELECT * FROM group WHERE courseName = ?;";
//        try {
//            setupPreparedStatement(sql);
//            preparedStatement.setString(1, courseName);
//            ResultSet resultSet = executeSelectStatement();
//            while (resultSet.next()) {
//                String groupName = resultSet.getString("groupName");
//                int maxNumberStudents = resultSet.getInt("maxNumberStudents");
//                String teacher = resultSet.getString("teacher");
//                User user = userDAO.getOneByPK(teacher);
//                group = new Group(courseName, groupName, maxNumberStudents, user);
//            }
//        } catch (SQLException sqlFout) {
//            System.out.println("SQL fout: " + sqlFout.getMessage());
//        }
//        return group;
//    }
//
//    @Override
//    public void storeOne(Group group) {
//        String sql = "INSERT INTO group(courseName, groupName, maxNumberStudents, teacher)" +
//                "VALUES (?, ?, ?, ?);";
//        try {
//            setupPreparedStatement(sql);
//            preparedStatement.setString(1, group.getCourseName().getCourseName());
//            preparedStatement.setString(2, group.getGroupName());
//            preparedStatement.setInt(3, group.getMaxNumberStudents());
//            preparedStatement.setString(4, group.getTeacher().getUsername());
//            executeManipulateStatement();
//        } catch (SQLException sqlFout){
//            System.out.println("SQL fout: " + sqlFout.getMessage());
//        }
//
//    }
//}
