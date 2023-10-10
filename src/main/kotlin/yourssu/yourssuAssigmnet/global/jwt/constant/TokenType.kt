package yourssu.yourssuAssigmnet.global.jwt.constant

enum class TokenType {
    ACCESS, REFRESH;

    companion object {
        fun isAccessToken(tokenType: String): Boolean = ACCESS.name == tokenType
    }
}