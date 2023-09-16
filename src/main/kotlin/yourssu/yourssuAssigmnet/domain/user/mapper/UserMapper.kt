package yourssu.yourssuAssigmnet.domain.user.mapper

import yourssu.yourssuAssigmnet.domain.user.entity.User
import org.mapstruct.Mapper
import yourssu.yourssuAssigmnet.domain.user.dto.UserDto

@Mapper(componentModel = "spring")
interface UserMapper {
    fun userPostDtoToUser(userPostDto: UserDto.Post): User
    fun userToUserResponse(user: User): UserDto.Response
}
