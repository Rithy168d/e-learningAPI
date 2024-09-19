package istad.co.eleariningapi.mapper;

import istad.co.eleariningapi.domain.User;
import istad.co.eleariningapi.features.auth.dto.RegisterRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    //  Map from dto to domain model
    User fromRegisterRequest(RegisterRequest registerRequest);

}
