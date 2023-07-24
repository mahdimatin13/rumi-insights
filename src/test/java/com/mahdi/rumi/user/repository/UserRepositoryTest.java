package com.mahdi.rumi.user.repository;

import com.mahdi.rumi.user.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository underTest;


    @Test
    void itShouldCheckWhenEmailExists() {

        // give
        String email = "mahdi@gmail.com";
        UserEntity user = new UserEntity(email, "12345678");
        underTest.save(user);

        // when
        boolean expected = underTest.selectExistsEmail(email);

        // then
        assertThat(expected).isTrue();
    }

    @Test
    void itShouldFindUserWhenEmailExists() {
        // give
        String email = "dennis@gmail.com";
        UserEntity user  = new UserEntity(email, "12345678");
        underTest.save(user);
        // when
        UserEntity expected = underTest.findByEmail(email);

        // then

        assertThat(expected).isEqualTo(user);
    }
}
