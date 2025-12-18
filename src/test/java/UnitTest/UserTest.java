package UnitTest;

import InOut.FileReader;
import Models.User;
import Services.InputValidator;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class UserTest {

    // All the following testing is for testing the input validator part of userName, userId and ID uniqueness
    // Note: for testing of input file for FileReaderService Class go to line 526 in this file

    //Valid UserName:
    // 1) Uppercase Name
    @Test
    public void validateUserName_validName1() {
        InputValidator validator = new InputValidator();
        Set<String> ids = new HashSet<>();
        User user = new User("Ahmed Ali", "12345678" , null);

        try{
            validator.validateUser(user, ids);
        } catch (Exception e){
            fail();
        }
    }

    //Valid UserName:
    // 2) Lowercase Name
    @Test
    public void validateUserName_validName2() {
        InputValidator validator = new InputValidator();
        Set<String> ids = new HashSet<>();
        User user = new User("sara mohamed", "12345678" , null);

        try{
            validator.validateUser(user, ids);
        } catch (Exception e){
            fail();
        }
    }

    //Valid name:
    // 3) Only one name
    @Test
    public void validateUserName_HasOnlyOneName() {
        InputValidator validator = new InputValidator();
        Set<String> ids = new HashSet<>();
        User user = new User("sara", "12345678" , null);

        try{
            validator.validateUser(user, ids);
        } catch (Exception e){
            fail();
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    //Invalid name:
    // 1) Space at first
    @Test
    public void validateUserName_SpaceAtFirst() {
        InputValidator validator = new InputValidator();
        Set<String> ids = new HashSet<>();
        User user = new User(" sara mohamed", "12345678" , null);

        assertThrows(RuntimeException.class , () -> validator.validateUser(user, ids));
    }

    //Invalid name:
    // 2) Space at last
    @Test
    public void validateUserName_SpaceAtLast() {
        InputValidator validator = new InputValidator();
        Set<String> ids = new HashSet<>();
        User user = new User("sara mohamed ", "12345678" , null);

        assertThrows(RuntimeException.class , () -> validator.validateUser(user, ids));
    }

    //Invalid name:
    // 4) Has numbers at first
    @Test
    public void validateUserName_HasNumbersAtFirst1() {
        InputValidator validator = new InputValidator();
        Set<String> ids = new HashSet<>();
        User user = new User("132sara Mohamed", "12345678" , null);

        assertThrows(RuntimeException.class , () -> validator.validateUser(user, ids));
    }

    //Invalid name:
    // 5) Has numbers at first
    @Test
    public void validateUserName_HasNumbersAtFirst2() {
        InputValidator validator = new InputValidator();
        Set<String> ids = new HashSet<>();
        User user = new User("-1sara Mohamed", "12345678" , null);

        assertThrows(RuntimeException.class , () -> validator.validateUser(user, ids));
    }

    //Invalid name:
    // 6) Has numbers at first
    @Test
    public void validateUserName_HasNumbersAtFirst3() {
        InputValidator validator = new InputValidator();
        Set<String> ids = new HashSet<>();
        User user = new User("0sara Mohamed", "12345678" , null);

        assertThrows(RuntimeException.class , () -> validator.validateUser(user, ids));
    }

    //Invalid name:
    // 7) Has numbers at middle
    @Test
    public void validateUserName_HasNumbersAtMiddle() {
        InputValidator validator = new InputValidator();
        Set<String> ids = new HashSet<>();
        User user = new User("sara12 -9Mohamed", "12345678" , null);

        assertThrows(RuntimeException.class , () -> validator.validateUser(user, ids));
    }

    //Invalid name:
    // 8) Has numbers at last
    @Test
    public void validateUserName_HasNumbersAtLast1() {
        InputValidator validator = new InputValidator();
        Set<String> ids = new HashSet<>();
        User user = new User("sara Mohamed123", "12345678" , null);

        assertThrows(RuntimeException.class , () -> validator.validateUser(user, ids));
    }

    //Invalid name:
    // 9) Has numbers at last
    @Test
    public void validateUserName_HasNumbersAtLast2() {
        InputValidator validator = new InputValidator();
        Set<String> ids = new HashSet<>();
        User user = new User("sara Mohamed-0", "12345678" , null);

        assertThrows(RuntimeException.class , () -> validator.validateUser(user, ids));
    }

    //Invalid name:
    // 10) Has more than one space at middle
    @Test
    public void validateUserName_HasMoreSpacesAtMiddle() {
        InputValidator validator = new InputValidator();
        Set<String> ids = new HashSet<>();
        User user = new User("sara  Mohamed", "12345678" , null);

        assertThrows(RuntimeException.class , () -> validator.validateUser(user, ids));
    }

    //Invalid name:
    // 11) Has Special characters at first
    @Test
    public void validateUserName_HasSpecialCharactersAtFirst1() {
        InputValidator validator = new InputValidator();
        Set<String> ids = new HashSet<>();
        User user = new User("@#sara Mohamed", "12345678" , null);

        assertThrows(RuntimeException.class , () -> validator.validateUser(user, ids));
    }

    //Invalid name:
    // 12) Has Special characters at first
    @Test
    public void validateUserName_HasSpecialCharactersAtFirst2() {
        InputValidator validator = new InputValidator();
        Set<String> ids = new HashSet<>();
        User user = new User("*()*&^%!sara Mohamed", "12345678" , null);

        assertThrows(RuntimeException.class , () -> validator.validateUser(user, ids));
    }

    //Invalid name:
    // 13) Has Special characters at middle
    @Test
    public void validateUserName_HasSpecialCharactersAtMiddle1() {
        InputValidator validator = new InputValidator();
        Set<String> ids = new HashSet<>();
        User user = new User("sara! Mohamed", "12345678" , null);

        assertThrows(RuntimeException.class , () -> validator.validateUser(user, ids));
    }

    //Invalid name:
    // 14) Has Special characters at middle
    @Test
    public void validateUserName_HasSpecialCharactersAtMiddle2() {
        InputValidator validator = new InputValidator();
        Set<String> ids = new HashSet<>();
        User user = new User("sara =Mohamed", "12345678" , null);

        assertThrows(RuntimeException.class , () -> validator.validateUser(user, ids));
    }

    //Invalid name:
    // 15) Has Special characters at last
    @Test
    public void validateUserName_HasSpecialCharactersAtLast() {
        InputValidator validator = new InputValidator();
        Set<String> ids = new HashSet<>();
        User user = new User("sara Mohamed+", "12345678" , null);

        assertThrows(RuntimeException.class , () -> validator.validateUser(user, ids));
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    //Valid ID:
    // 1) 9 Numbers only
    @Test
    public void validateUserID_ValidId9NumbersOnly() {
        InputValidator validator = new InputValidator();
        Set<String> ids = new HashSet<>();
        User user = new User("sara Mohamed", "12345678" , null);

        try{
            validator.validateUser(user, ids);
        } catch (Exception e){
            fail();
        }
    }

    //Valid ID:
    // 2) 8 Numbers with one lowercase alphabet
    @Test
    public void validateUserID_ValidId_8NumbersWith_1AlphabetLowercase() {
        InputValidator validator = new InputValidator();
        Set<String> ids = new HashSet<>();
        User user = new User("sara Mohamed", "12345678a" , null);

        try{
            validator.validateUser(user, ids);
        } catch (Exception e){
            fail();
        }
    }

    //Valid ID:
    // 3) 8 Numbers with one uppercase alphabet
    @Test
    public void validateUserID_ValidId_8NumbersWith_1AlphabetUppercase() {
        InputValidator validator = new InputValidator();
        Set<String> ids = new HashSet<>();
        User user = new User("sara Mohamed", "12345678P" , null);

        try{
            validator.validateUser(user, ids);
        } catch (Exception e){
            fail();
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    //Invalid ID:
    // 1) 8 numbers only
    @Test
    public void validateUserID_InValidId_8NumbersOnly() {
        InputValidator validator = new InputValidator();
        Set<String> ids = new HashSet<>();
        User user = new User("sara Mohamed", "1234567891" , null);

        assertThrows(RuntimeException.class , () -> validator.validateUser(user, ids));
    }

    //Invalid ID:
    // 2) 3 numbers only
    @Test
    public void validateUserID_InValidId_3NumbersOnly() {
        InputValidator validator = new InputValidator();
        Set<String> ids = new HashSet<>();
        User user = new User("sara Mohamed", "123" , null);

        assertThrows(RuntimeException.class , () -> validator.validateUser(user, ids));
    }

    //Invalid ID:
    // 3) 10 numbers only
    @Test
    public void validateUserID_InValidId_10NumbersOnly() {
        InputValidator validator = new InputValidator();
        Set<String> ids = new HashSet<>();
        User user = new User("sara Mohamed", "1234567891" , null);

        assertThrows(RuntimeException.class , () -> validator.validateUser(user, ids));
    }

    //Invalid ID:
    // 4) 15 numbers only
    @Test
    public void validateUserID_InValidId_15NumbersOnly() {
        InputValidator validator = new InputValidator();
        Set<String> ids = new HashSet<>();
        User user = new User("sara Mohamed", "123456789123456" , null);

        assertThrows(RuntimeException.class , () -> validator.validateUser(user, ids));
    }

    //Invalid ID:
    // 5) 7 numbers with one alphabet at last
    @Test
    public void validateUserID_InValidId_7Numbers_oneAlphabetAtLast() {
        InputValidator validator = new InputValidator();
        Set<String> ids = new HashSet<>();
        User user = new User("sara Mohamed", "1234567A" , null);

        assertThrows(RuntimeException.class , () -> validator.validateUser(user, ids));
    }

    //Invalid ID:
    // 6) 9 numbers with one alphabet at last
    @Test
    public void validateUserID_InValidId_9Numbers_oneAlphabetAtLast() {
        InputValidator validator = new InputValidator();
        Set<String> ids = new HashSet<>();
        User user = new User("sara Mohamed", "1234567891A" , null);

        assertThrows(RuntimeException.class , () -> validator.validateUser(user, ids));
    }

    //Invalid ID:
    // 7) 8 numbers with one alphabet at first
    @Test
    public void validateUserID_InValidId_8Numbers_oneAlphabetAtFirst() {
        InputValidator validator = new InputValidator();
        Set<String> ids = new HashSet<>();
        User user = new User("sara Mohamed", "A12345678" , null);

        assertThrows(RuntimeException.class , () -> validator.validateUser(user, ids));
    }
    @Test
    public void validateUserID_InValidId_7Numbers_oneAlphabetAtFirst() {
        InputValidator validator = new InputValidator();
        Set<String> ids = new HashSet<>();
        User user = new User("sara Mohamed", "1234567" , null);

        assertThrows(RuntimeException.class , () -> validator.validateUser(user, ids));
    }

    //Invalid ID:
    // 7) 9 alphabet characters
    @Test
    public void validateUserID_InValidId_9AlphabetCharacters() {
        InputValidator validator = new InputValidator();
        Set<String> ids = new HashSet<>();
        User user = new User("sara Mohamed", "AbncDjklp" , null);

        assertThrows(RuntimeException.class , () -> validator.validateUser(user, ids));
    }


    /// //////////////////////////////////////////////////////////////////////////////////////////////////////////////


    //Valid Unique IDs:
    // 1) only first user
    @Test
    public void validateUniqueId_FirstUse() {
        InputValidator validator = new InputValidator();
        Set<String> ids = new HashSet<>();

        User user = new User("sara mohamed", "12345678A", null);

        try{
            validator.validateUser(user, ids);
        } catch (Exception e){
            fail();
        }
    }

    //Valid Unique IDs:
    // 2) 2 Users
    @Test
    public void validateUniqueId_2Users() {
        InputValidator validator = new InputValidator();
        Set<String> ids = new HashSet<>();

        User user1 = new User("Sara Mohamed", "12345678A", null);
        User user2 = new User("Omar Salah", "11345678A", null);

        try{
            validator.validateUser(user1, ids);
            validator.validateUser(user2, ids);
        } catch (Exception e){
            fail();
        }
    }

    //Valid Unique IDs:
    // 3) 5 Users
    @Test
    public void validateUniqueId_5Users() {
        InputValidator validator = new InputValidator();
        Set<String> ids = new HashSet<>();

        User user1 = new User("sara Mohamed", "12345678A", null);
        User user2 = new User("Omar Salah", "11445678A", null);
        User user3 = new User("Ali Mosa", "11346678A", null);
        User user4 = new User("Mohamed Ahmed", "11345078A", null);
        User user5 = new User("Amr Salah", "11345688A", null);

        try{
            validator.validateUser(user1, ids);
            validator.validateUser(user2, ids);
            validator.validateUser(user3, ids);
            validator.validateUser(user4, ids);
            validator.validateUser(user5, ids);
        } catch (Exception e){
            fail();
        }
    }


    /// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////



    //InValid Unique IDs:
    // 1) 2 Users
    @Test
    public void validateUniqueId_Invalid_2Users() {
        InputValidator validator = new InputValidator();
        Set<String> ids = new HashSet<>();

        User user1 = new User("sara Mohamed", "12345678A", null);
        User user2 = new User("Omar Salah", "12345678A", null);

        try{
            validator.validateUser(user1, ids);
        } catch (Exception e){
            fail();
        }

        assertThrows(RuntimeException.class , () -> validator.validateUser(user2, ids));
    }

    //InValid Unique IDs:
    // 1) 5 Users
    @Test
    public void validateUniqueId_Invalid_5Users() {
        InputValidator validator = new InputValidator();
        Set<String> ids = new HashSet<>();

        User user1 = new User("sara Mohamed", "12345678A", null);
        User user2 = new User("Omar Salah", "12345678A", null);
        User user3 = new User("Ali Mosa", "12345678A", null);
        User user4 = new User("Mohamed Ahmed", "12345878A", null);
        User user5 = new User("Amr Salah", "12345679A", null);

        try{
            validator.validateUser(user1, ids);
        } catch (Exception e){
            fail();
        }

        assertThrows(RuntimeException.class , () -> validator.validateUser(user2, ids));

        assertThrows(RuntimeException.class , () -> validator.validateUser(user3, ids));

        try{
            validator.validateUser(user4, ids);
        } catch (Exception e){
            fail();
        }

        try{
            validator.validateUser(user5, ids);
        } catch (Exception e){
            fail();
        }
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    //valid input file users
    @Test
    public void testReadUsers_validFile_success() throws Exception {
        FileReader service = new FileReader();

        String path = "test.txt";
 //      FileWriter w = new FileWriter("test.txt");
//        w.write("5\n12\n31\n45\n121\n4\n");
//        w.close();

        List<User> users = service.readUsers(path);

        assertEquals(4, users.size());

        assertEquals("Ahmed Ali", users.getFirst().getName());
        assertEquals("12345678A", users.getFirst().getUserId());
        assertEquals("JW123", users.get(0).getLikedMovieIds().get(0));
        assertEquals("A567", users.get(0).getLikedMovieIds().get(1));

        assertEquals("sara Mostafa", users.get(1).getName());
        assertEquals("87654322", users.get(1).getUserId());
        assertEquals("TC489", users.get(1).getLikedMovieIds().get(0));
        assertEquals("IM902", users.get(1).getLikedMovieIds().get(1));

        assertEquals("Omar Khaled", users.get(2).getName());
        assertEquals("11223344C", users.get(2).getUserId());
        assertEquals("F314", users.get(2).getLikedMovieIds().getFirst());

        assertEquals("Mona Youssef", users.get(3).getName());
        assertEquals("99887766D", users.get(3).getUserId());
        assertEquals("JW123", users.get(3).getLikedMovieIds().get(0));
        assertEquals("TC489", users.get(3).getLikedMovieIds().get(1));
        assertEquals("F314", users.get(3).getLikedMovieIds().get(2));
    }
}
