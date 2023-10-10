package yourssu.yourssuAssigmnet.global.jwt.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*

data class JwtTokenDto(
    var grantType: String,
    var accessToken: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    var accessTokenExpireTime: Date,
    var refreshToken: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    var refreshTokenExpireTime: Date
){
    fun getAccessTokenWithGrantType(): String {
        if (grantType != null && grantType.isNotEmpty()) {
            return "$grantType $accessToken"
        }
        return accessToken
    }

    fun getRefreshTokenWithGrantType(): String {
        if (grantType != null && grantType.isNotEmpty()) {
            return "$grantType $refreshToken"
        }
        return refreshToken
    }
}