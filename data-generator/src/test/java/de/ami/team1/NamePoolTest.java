package de.ami.team1;

import de.ami.team1.pojo.UserPojo;
import de.ami.team1.util.NamePool;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

@QuarkusTest
public class NamePoolTest {


    @Test
    private void getUser(){
        UserPojo userPojo = NamePool.generateUser();
        Assertions.assertTrue(userPojo.getDateOfBirth().isBefore(LocalDate.now()));
        Assertions.assertTrue(userPojo.getGender() == 'm' || userPojo.getGender() == 'f');
        Assertions.assertTrue(userPojo.getMail().contains("@"));
    }
}
