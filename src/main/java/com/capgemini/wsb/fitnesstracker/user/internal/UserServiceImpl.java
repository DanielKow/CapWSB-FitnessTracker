package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Creates new user in the database
     * @param user to create
     * @return created user
     */
    @Override
    public User createUser(final UserDto user) {
        log.info("Creating User {}", user);
        if (user.id() != null) {
            throw new UserAlreadyExistsException(user.id());
        }

        if (userRepository.findByEmail(user.email()).isPresent()){
            throw new UserAlreadyExistsException(user.email());
        }

        User userEntity = userMapper.toEntity(user);
        return userRepository.save(userEntity);
    }

    /**
     * Deletes user with given id
     * @param id of user to delete
     */
    @Override
    public void deleteUser(final long id) {
        log.info("Deleting User with id {}", id);
        userRepository.deleteById(id);
    }

    /**
     * Updates user with given data
     * @param user to update
     * @return updated user
     */
    @Override
    public User updateUser(final long id, final UserDto user) {
        return null;
    }

    /**
     * Get user by id
     * @param userId of user
     * @return User with given id
     */
    @Override
    public Optional<UserDto> getUser(final Long userId) {
        return userRepository.findById(userId).map(userMapper::toDto);
    }

    /**
     * Get user by email
     * @param email of user
     * @return User with given email
     */
    @Override
    public Optional<UserDto> getUserByEmail(final String email) {
        return userRepository.findByEmail(email).map(userMapper::toDto);
    }

    /**
     * Get all users
     * @return List of all users
     */
    @Override
    public List<UserDto> findAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toDto).toList();
    }

    /**
     * Get users that have email containing given string ignoring case
     * @param email to search
     * @return List of users with matching emails
     */
    @Override
    public List<UserDto> findByEmail(final String email) {
        if (email == null || email.isEmpty()) {
            throw new MissingEmailException();
        }

        return userRepository.findByEmailContainingIgnoreCase(email).stream().map(userMapper::toDto).toList();
    }

    /**
     * Get users that are older than given date
     * @param date to compare
     * @return List of users older than given date
     */
    @Override
    public List<UserDto> findOlderThan(final LocalDate date) {
        return userRepository.findByBirthdateAfter(date).stream().map(userMapper::toDto).toList();
    }

}