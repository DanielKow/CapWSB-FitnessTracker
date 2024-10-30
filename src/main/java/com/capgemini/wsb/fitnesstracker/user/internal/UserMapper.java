package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.SimpleUserDto;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserDto;
import com.capgemini.wsb.fitnesstracker.user.api.UserEmailDto;
import org.springframework.stereotype.Component;

@Component
class UserMapper {

    UserDto toDto(User user) {
        return new UserDto(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate(),
                user.getEmail());
    }

    SimpleUserDto toSimpleDto(User user) {
        return new SimpleUserDto(user.getId(),
                                 user.getFirstName(),
                                 user.getLastName());
    }

    SimpleUserDto toSimpleDto(UserDto user) {
        return new SimpleUserDto(user.id(),
                user.firstName(),
                user.lastName());
    }

    UserEmailDto toEmailDto(UserDto user) {
        return new UserEmailDto(user.id(),
                                user.email());
    }

    User toEntity(UserDto userDto) {
        return new User(
                        userDto.firstName(),
                        userDto.lastName(),
                        userDto.birthdate(),
                        userDto.email());
    }

}
