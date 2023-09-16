package yourssu.yourssuAssigmnet.global.error

import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class ErrorResponse(
    val time: String,
    val status: HttpStatus,  // ErrorCode의 HttpStatus를 사용
    val message: String,     // ErrorCode의 message를 사용
    val requestURI: String
) {
    companion object {
        private fun getCurrentTime(): String =
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS"))

        fun of(httpStatus: HttpStatus, message: String, requestURI: String): ErrorResponse {
            return ErrorResponse(
                time = getCurrentTime(),
                status = httpStatus,
                message = message,
                requestURI = requestURI
            )
        }

        fun of(httpStatus: HttpStatus, bindingResult: BindingResult, requestURI: String): ErrorResponse {
            return ErrorResponse(
                time = getCurrentTime(),
                status = httpStatus,
                message = createErrorMessage(bindingResult),
                requestURI = requestURI
            )
        }

        private fun createErrorMessage(bindingResult: BindingResult): String {
            return bindingResult.fieldErrors.joinToString(", ") { fieldError ->
                "[${fieldError.field}] ${fieldError.defaultMessage}"
            }
        }

        fun fromErrorCode(errorCode: ErrorCode, requestURI: String): ErrorResponse {
            return ErrorResponse(
                time = getCurrentTime(),
                status = errorCode.status,
                message = errorCode.message,
                requestURI = requestURI
            )
        }
    }
}
