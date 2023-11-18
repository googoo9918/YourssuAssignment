package yourssu.yourssuAssigmnet.domain.user.mapper

import org.mapstruct.Mapper
import yourssu.yourssuAssigmnet.domain.user.dto.UserDto
import yourssu.yourssuAssigmnet.domain.user.entity.User

@Mapper(componentModel = "spring")
interface UserMapper {
    fun userPostDtoToUser(userPostDto: UserDto.Post): User
    fun userToUserResponse(user: User): UserDto.Response
}
