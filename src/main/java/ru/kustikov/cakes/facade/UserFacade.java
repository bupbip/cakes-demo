package ru.kustikov.cakes.facade;

import org.springframework.stereotype.Component;
import ru.kustikov.cakes.dto.UserDTO;
import ru.kustikov.cakes.entity.User;

@Component
public class UserFacade {
    public UserDTO userToUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setInstagram(user.getInstagram());
        return userDTO;
    }
}
