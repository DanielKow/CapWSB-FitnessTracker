package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.exception.api.NotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.SimpleUserDto;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserDto;
import com.capgemini.wsb.fitnesstracker.user.api.UserEmailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                          .stream()
                          .map(userMapper::toDto)
                          .toList();
    }

    /**
     * Get id, name and surname of all users
     * @return List of SimpleUserDto
     */
    @GetMapping("simple")
    public List<SimpleUserDto> getAllSimpleUsers() {
        return userService.findAllUsers()
                          .stream()
                          .map(userMapper::toSimpleDto)
                          .toList();
    }

    /**
     * Get user by id
     * @param id of user
     * @return UserDto
     */
    @GetMapping("{id}")
    public UserDto getUser(@PathVariable Long id) {
        return userService.getUser(id)
                          .map(userMapper::toDto)
                          .orElseThrow(() -> new NotFoundException("User  not found"));
    }

    /**
     * Add user
     * @param userDto Data about user to add
     * @return Created user
     */
    @PostMapping
    public ResponseEntity<Object> addUser(@RequestBody UserDto userDto) throws InterruptedException {
        User user = userMapper.toEntity(userDto);

        try {
            UserDto createdUser = userMapper.toDto(userService.createUser(user));
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Delete user by id
     * @param id of user to delete
     * @return nothing
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Get users that have email containing given string ignoring case
     * @param email to search
     * @return List of users with matching emails, only id, name and surname
     */
    @GetMapping("email")
    public ResponseEntity<Object> findUsersByEmil(@RequestParam String email) {

        try {
            List<UserEmailDto> users = userService.findByEmail(email).stream().map(userMapper::toEmailDto).toList();
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    /**
     * Get users that have birthdate after given date
     * @param  date to search
     * @return List of users with matching birthdate
     */
    @GetMapping("older/{date}")
    public ResponseEntity<Object> findUsersOlderThan(@PathVariable LocalDate date) {
        List<UserDto> users = userService.findOlderThan(date).stream().map(userMapper::toDto).toList();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}