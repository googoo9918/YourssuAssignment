package yourssu.yourssuAssigmnet.global.error

import org.springframework.http.HttpStatus

enum class ErrorCode(val status: HttpStatus, val errorCode: String, val message: String) {
    //인증 & 인가
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A-001", "토큰이 만료되었습니다."),
    NOT_VALID_TOKEN(HttpStatus.UNAUTHORIZED, "A-002", "해당 토큰은 유효한 토큰이 아닙니다."),
    NOT_EXISTS_AUTHORIZATION(HttpStatus.UNAUTHORIZED, "A-003", "Authorization Header가 빈값입니다."),
    NOT_VALID_BEARER_GRANT_TYPE(HttpStatus.UNAUTHORIZED, "A-004", "인증 타입이 Bearer 타입이 아닙니다."),
    NOT_ACCESS_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, "A-005", "해당 토큰은 ACCESS TOKEN이 아닙니다."),
    //회원
    ALREADY_REGISTERED_MEMBER(HttpStatus.BAD_REQUEST, "M-001", "이미 가입된 계정 입니다."),
    MEMBER_NOT_EXISTS(HttpStatus.BAD_REQUEST, "M-002", "해당 회원은 존재하지 않습니다."),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "M-003", "비밀번호가 일치하지 않습니다."),

    //글
    ARTICLE_NOT_EXISTS(HttpStatus.BAD_REQUEST, "A-001", "해당 글은 존재하지 않습니다."),
    AUTHOR_MISMATCH(HttpStatus.UNAUTHORIZED, "A-002", "회원이 작성자와 일치하지 않습니다."),

    //댓글
    COMMENT_NOT_EXISTS(HttpStatus.BAD_REQUEST, "C-001", "해당 댓글은 존재하지 않습니다"),
    UNRELATED_COMMENT_TO_ARTICLE(HttpStatus.BAD_REQUEST, "C-002", "해당 게시글에 달린 댓글이 아닙니다")
}
