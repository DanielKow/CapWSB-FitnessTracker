package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.MissingEmailException;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserAlreadyExistsException;
import com.capgemini.wsb.fitnesstracker.user.api.UserDto;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

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
        UserServiceImpl service = new UserServiceImpl(userRepositoryMock, new UserMapper());
        UserDto user = new UserDto(null, "Ola", "Wawrzyniak", LocalDate.of(2001,3,27),"olaw@poczta.pl");

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

    @Test(expected = UserAlreadyExistsException.class)
    public void exceptionShouldBeThrown_whenUserExists() {
        // GIVEN
        UserRepository userRepositoryMock = mock(UserRepository.class);
        User existingUser = new User("Ola", "Wawrzyniak", LocalDate.of(2001, 3, 27), "olaw@poczta.pl");
        when(userRepositoryMock.findByEmail(isA(String.class))).thenReturn(Optional.of(existingUser));

        UserServiceImpl service = new UserServiceImpl(userRepositoryMock, new UserMapper());
        UserDto user = new UserDto(null, "Jan", "Kowalski", LocalDate.of(1970, 3, 11), "olaw@poczta.pl");

        // WHEN
        when(service.createUser(user)).thenThrow(new UserAlreadyExistsException("olaw@poczta.pl"));

        // THEN
        verify(userRepositoryMock).findByEmail("olaw@poczta.pl");
        verify(userRepositoryMock, never()).save(isA(User.class));
    }

    @Test
    public void userShouldBeDeleted_whenDeletingUser(){
        // GIVEN
        UserRepository userRepositoryMock = mock(UserRepository.class);
        UserServiceImpl service = new UserServiceImpl(userRepositoryMock, new UserMapper());

        // WHEN
        service.deleteUser(11L);

        // THEN
        verify(userRepositoryMock).deleteById(11L);
    }

    @Test
    public void userShouldBeFindByEmailContainingIgnoreCase_whenFindingByEmail() {
        // Given
        UserRepository userRepositoryMock = mock(UserRepository.class);
        UserServiceImpl service = new UserServiceImpl(userRepositoryMock, new UserMapper());

        // When
        service.findByEmail("test@");

        // Then
        verify(userRepositoryMock).findByEmailContainingIgnoreCase("test@");
    }

    @Test(expected = MissingEmailException.class)
    public void exceptionShouldBeThrown_whenEmailIsNotGivenForFinding() {
        // GIVEN
        UserRepository userRepositoryMock = mock(UserRepository.class);
        UserServiceImpl service = new UserServiceImpl(userRepositoryMock, new UserMapper());

        // WHEN
        when(service.findByEmail("")).thenThrow(new MissingEmailException());

        // THEN
        verify(userRepositoryMock, never()).findByEmailContainingIgnoreCase(isA(String.class));
    }

    @Test
    public void usersOlderThanDateShouldBeFound_whenFindingAfterDate() {
        // Given
        UserRepository userRepositoryMock = mock(UserRepository.class);
        UserServiceImpl service = new UserServiceImpl(userRepositoryMock, new UserMapper());

        // When
        service.findOlderThan(LocalDate.of(2000, 1, 1));

        // Then
        verify(userRepositoryMock).findByBirthdateAfter(LocalDate.of(2000, 1, 1));
    }

    @Test
    public void userShouldBeUpdate_whenUpdating()
    {
        // GIVEN
        final long id = 1L;
        UserRepository userRepositoryMock = mock(UserRepository.class);
        User existingUser = new User("Ola", "Wawrzyniak", LocalDate.of(2001,3,27),"olaw@poczta.pl");
        when(userRepositoryMock.findById(id)).thenReturn(Optional.of(existingUser));
        UserServiceImpl service = new UserServiceImpl(userRepositoryMock, new UserMapper());
        UserDto user = new UserDto(id, "Aleksandra", "Wawrzyniak", LocalDate.of(2001,3,27),"aw@poczta.pl");

        // WHEN
        service.updateUser(id, user);

        // THEN
        InOrder inOrder = Mockito.inOrder(userRepositoryMock);
        inOrder.verify(userRepositoryMock).findById(id);
        inOrder.verify(userRepositoryMock).save(argThat(u ->
                u.getFirstName().equals("Aleksandra")
                        && u.getLastName().equals("Wawrzyniak")
                        && u.getBirthdate().equals(LocalDate.of(2001, 3, 27))
                        && u.getEmail().equals("aw@poczta.pl")));
    }

}

