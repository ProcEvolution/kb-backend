package cloud.procevolution.hnknowledgebase.view.mapping;

import cloud.procevolution.hnknowledgebase.entity.Role;
import cloud.procevolution.hnknowledgebase.view.dto.RoleDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleDto roleToRoleDto(Role role);

    Role roleDtoToRole(RoleDto roleDto);
}
