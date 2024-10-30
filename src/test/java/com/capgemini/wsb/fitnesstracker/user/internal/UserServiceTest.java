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

    @Test(expected = IllegalArgumentException.class)
    public void exceptionShouldBeThrown_whenUserExists() {
        // GIVEN
        UserRepository userRepositoryMock = mock(UserRepository.class);
        User existingUser = new User("Ola", "Wawrzyniak", LocalDate.of(2001, 3, 27), "olaw@poczta.pl");
        when(userRepositoryMock.findByEmail(isA(String.class))).thenReturn(Optional.of(existingUser));

        UserServiceImpl service = new UserServiceImpl(userRepositoryMock);
        User user = new User("Jan", "Kowalski", LocalDate.of(1970, 3, 11), "olaw@poczta.pl");

        // WHEN
        when(service.createUser(user)).thenThrow(new IllegalArgumentException("User email is already exist"));

        // THEN
        verify(userRepositoryMock).findByEmail("olaw@poczta.pl");
        verify(userRepositoryMock, never()).save(isA(User.class));
    }

    @Test
    public void userShouldBeDeleted_whenDeletingUser(){
        // GIVEN
        UserRepository userRepositoryMock = mock(UserRepository.class);
        UserServiceImpl service = new UserServiceImpl(userRepositoryMock);

        // WHEN
        service.deleteUser(11L);

        // THEN
        verify(userRepositoryMock).deleteById(11L);
    }

    @Test
    public void userShouldBeFindByEmailContainingIgnoreCase_whenFindingByEmail() {
        // Given
        UserRepository userRepositoryMock = mock(UserRepository.class);
        UserServiceImpl service = new UserServiceImpl(userRepositoryMock);

        // When
        service.findByEmail("test@");

        // Then
        verify(userRepositoryMock).findByEmailContainingIgnoreCase("test@");
    }

    @Test
    public void exceptionShouldBeThrown_whenEmailIsNotGivenForFinding() {
        // GIVEN
        UserRepository userRepositoryMock = mock(UserRepository.class);
        UserServiceImpl service = new UserServiceImpl(userRepositoryMock);

        // WHEN
        when(service.findByEmail("")).thenThrow(new IllegalArgumentException("Email is not given"));

        // THEN
        verify(userRepositoryMock, never()).findByEmailContainingIgnoreCase(isA(String.class));
    }
}

