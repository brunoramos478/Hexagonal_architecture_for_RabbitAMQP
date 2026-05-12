package br.com.fusion.banck.shared.mapper;

import br.com.fusion.banck.shared.dto.DtoResponse;
import br.com.fusion.banck.shared.dto.UserDto;

public class ResponseMapper {
    public static DtoResponse toResponse(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        DtoResponse response = new DtoResponse();
        response.setName(userDto.getFirstName());
        response.setLastName(userDto.getLastName());
        response.setRegion(userDto.getRegion());
        response.setCity(userDto.getCity());
        response.setState(userDto.getState());

        return response;
    }

}
