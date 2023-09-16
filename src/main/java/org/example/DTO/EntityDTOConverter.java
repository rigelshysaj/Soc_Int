package org.example.DTO;

import org.example.Entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EntityDTOConverter {

    private static ModelMapper modelMapper = new ModelMapper();

    public EntityDTOConverter() {
        modelMapper = new ModelMapper();
    }

    public static UserEntity convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, UserEntity.class);
    }

    public static UserDTO convertToDTO(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDTO.class);
    }

    // Altri metodi per le conversioni tra altre entità e DTO
}
