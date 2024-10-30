package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.exception.api.NotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.SimpleUserDto;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody UserDto userDto) throws InterruptedException {
        User user = userMapper.toEntity(userDto);
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

}