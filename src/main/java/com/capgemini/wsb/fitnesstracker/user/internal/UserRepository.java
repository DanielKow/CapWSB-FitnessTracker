package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Query searching users by email address. It matches by exact match.
     *
     * @param email email of the user to search
     * @return {@link Optional} containing found user or {@link Optional#empty()} if none matched
     */
    default Optional<User> findByEmail(String email) {
        return findAll().stream()
                        .filter(user -> Objects.equals(user.getEmail(), email))
                        .findFirst();
    }

    /**
     * Query searching users by email address. It matches by containing the given email.
     * @param email of the user to search
     * @return List of users having matching email
     */
    List<User> findByEmailContainingIgnoreCase(String email);

    /**
     * Query searching users by date of birth.
     * @param date minimum date of birth
     * @return List of users having birthdate after the given date
     */
    List<User> findByBirthdateAfter(LocalDate date);
}
