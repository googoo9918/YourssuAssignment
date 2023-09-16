package yourssu.yourssuAssigmnet.global.error

import yourssu.yourssuAssigmnet.global.error.exception.BusinessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import javax.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory

@RestControllerAdvice
class GlobalExceptionHandler(private val httpServletRequest: HttpServletRequest) {

    private val log = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(BindException::class)
    protected fun handleBindException(e: BindException): ResponseEntity<ErrorResponse> {
        log.error("handleBindException", e)
        val errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, e.bindingResult, httpServletRequest.requestURI)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    protected fun handleMethodArgumentTypeMismatchException(e: MethodArgumentTypeMismatchException): ResponseEntity<ErrorResponse> {
        log.error("handleMethodArgumentTypeMismatchException", e)
        val errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, e.message ?: "", httpServletRequest.requestURI)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    protected fun handleHttpRequestMethodNotSupportedException(e: HttpRequestMethodNotSupportedException): ResponseEntity<ErrorResponse> {
        log.error("handleHttpRequestMethodNotSupportedException", e)
        val errorResponse = ErrorResponse.of(HttpStatus.METHOD_NOT_ALLOWED, e.message ?: "", httpServletRequest.requestURI)
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorResponse)
    }

    @ExceptionHandler(BusinessException::class)
    protected fun handleConflict(e: BusinessException): ResponseEntity<ErrorResponse> {
        log.error("BusinessException", e)
        val errorResponse = ErrorResponse.fromErrorCode(e.errorCode, httpServletRequest.requestURI)
        return ResponseEntity.status(e.errorCode.status).body(errorResponse)
    }

    @ExceptionHandler(Exception::class)
    protected fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        log.error("Exception", e)
        val errorResponse = ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, e.message ?: "", httpServletRequest.requestURI)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse)
    }
}
