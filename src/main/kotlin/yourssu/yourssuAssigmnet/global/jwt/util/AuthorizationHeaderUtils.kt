package yourssu.yourssuAssigmnet.global.jwt.util

import yourssu.yourssuAssigmnet.global.error.ErrorCode
import yourssu.yourssuAssigmnet.global.error.exception.AuthenticationException
import yourssu.yourssuAssigmnet.global.jwt.constant.GrantType

object AuthorizationHeaderUtils {

    fun validateAuthorization(authorizationHeader: String?) {

        // 1. authorizationHeader 필수 체크
        if (authorizationHeader.isNullOrEmpty()) {
            throw AuthenticationException(ErrorCode.NOT_EXISTS_AUTHORIZATION)
        }

        // 2. authorizationHeader Bearer 체크
        val authorizations = authorizationHeader.split(" ")
        if (authorizations.size < 2 || (GrantType.BEARER.type != authorizations[0])) {
            throw AuthenticationException(ErrorCode.NOT_VALID_BEARER_GRANT_TYPE)
        }
    }

}
