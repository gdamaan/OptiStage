package fr.ensitech.myproject.utils;

import fr.ensitech.myproject.entity.User;
import fr.ensitech.myproject.entity.dto.UserDto;

public abstract class Dto {

    public static UserDto userToDto(User user) {

        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstname(user.getFirstname());
        userDto.setLastname(user.getLastname());
        userDto.setEmail(user.getEmail());
        userDto.setBirthdate(user.getBirthdate());
        userDto.setIsActive(user.getIsActive());
        return userDto;
    }
}
