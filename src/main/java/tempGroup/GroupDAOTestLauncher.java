//package tempGroup;
//
//import database.mysql.DBAccess;
//import database.mysql.GroupDAO;
//import database.mysql.QuizDAO;
//import model.Group;
//import model.Question;
//import model.Quiz;
//import model.User;
//
//public class GroupDAOTestLauncher {
//    public static void main(String[] args) {
//        DBAccess dBaccess = new DBAccess("QuizMaster", "userQuizMaster", "userQuizMasterPW");
//        dBaccess.openConnection();
//        GroupDAO groupDAO = new GroupDAO(dBaccess);
//
//        User testTeacher = new User("TestTeacher", "TestPassword", "TestFirst",
//                "TestInfix", "TestLastName", "Tester");
//
//        groupDAO.storeOne(new Group("TestCourse", "TestGroup", ""));
//
//    }
//}
