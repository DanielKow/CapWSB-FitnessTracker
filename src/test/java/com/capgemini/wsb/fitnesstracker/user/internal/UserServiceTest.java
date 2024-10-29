package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;


public class UserServiceTest {


    @Test
    public void userShouldBeAdded_whenDoesNotExist()
    {
        // GIVEN
        UserRepository userRepositoryMock = mock(UserRepository.class);
        when(userRepositoryMock.findByEmail(isA(String.class))).thenReturn(Optional.empty());
        UserServiceImpl service = new UserServiceImpl(userRepositoryMock);
        User user = new User("Ola", "Wawrzyniak", LocalDate.of(2001,3,27),"olaw@poczta.pl");

        // WHEN
        service.createUser(user);

        // THEN
        InOrder inOrder = Mockito.inOrder(userRepositoryMock);
        inOrder.verify(userRepositoryMock).findByEmail("olaw@poczta.pl");
        inOrder.verify(userRepositoryMock).save(argThat(u ->
                u.getFirstName().equals("Ola")
                        && u.getLastName().equals("Wawrzyniak")
                        && u.getBirthdate().equals(LocalDate.of(2001, 3, 27))
                        && u.getEmail().equals("olaw@poczta.pl")));


    }
}
