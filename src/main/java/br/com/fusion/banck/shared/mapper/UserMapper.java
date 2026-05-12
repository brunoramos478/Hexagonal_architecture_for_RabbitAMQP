package br.com.fusion.banck.shared.mapper;

import br.com.fusion.banck.domain.entity.FusionApiEntity;
import br.com.fusion.banck.shared.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public FusionApiEntity toEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }

        FusionApiEntity entity = new FusionApiEntity();

        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setCpf(dto.getCpf());
        entity.setBirthDate(dto.getBirthDate());
        entity.setPhone(dto.getPhone());
        entity.setAddress(dto.getAddress());
        entity.setEmail(dto.getEmail());
        entity.setRegion(dto.getRegion());
        entity.setCity(dto.getCity());
        entity.setState(dto.getState());
        entity.setCep(dto.getCep());
        entity.setPassword(dto.getPassword());


        return entity;
    }

    public UserDto toDto(FusionApiEntity entity) {
        if (entity == null) {
            return null;
        }

        UserDto dto = new UserDto();

        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setCpf(entity.getCpf());
        dto.setBirthDate(entity.getBirthDate());
        dto.setPhone(entity.getPhone());
        dto.setAddress(entity.getAddress());
        dto.setEmail(entity.getEmail());
        dto.setRegion(entity.getRegion());
        dto.setCity(entity.getCity());
        dto.setState(entity.getState());
        dto.setCep(entity.getCep());
        dto.setPassword(entity.getPassword());

        return dto;
    }
}