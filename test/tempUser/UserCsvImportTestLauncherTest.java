package tempUser;

import model.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static tempUser.UserCsvImportTestLauncher.importCsvUsers;

class UserCsvImportTestLauncherTest {

    @Test
    void testImportCsvUsers() {
        // Arrange
        int expected = 205;
        // Act
        List<User> listOfUsers = importCsvUsers("Gebruikers.csv");
        try {
            int actual = listOfUsers.size();
            // Assert
            assertEquals(expected, actual);
        } catch (NullPointerException exception) {
            fail();
        }
    }
}