package cloud.procevolution.hnknowledgebase.view.mapping;

import cloud.procevolution.hnknowledgebase.entity.User;
import cloud.procevolution.hnknowledgebase.view.dto.UserDto;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto userDto);
}