package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;

    /**
     * Creates new user in the database
     * @param user to create
     * @return created user
     */
    @Override
    public User createUser(final User user) {
        log.info("Creating User {}", user);
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new IllegalArgumentException("User email is already exist");
        }

        return userRepository.save(user);
    }

    /**
     * Deletes user with given id
     * @param id of user to delete
     */
    @Override
    public void deleteUser(long id) {
        log.info("Deleting User with id {}", id);
        userRepository.deleteById(id);
    }

    /**
     * Get user by id
     * @param userId of user
     * @return User with given id
     */
    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    /**
     * Get user by email
     * @param email of user
     * @return User with given email
     */
    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Get all users
     * @return List of all users
     */
    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

}