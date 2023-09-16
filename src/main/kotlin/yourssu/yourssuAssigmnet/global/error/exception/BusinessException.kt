package yourssu.yourssuAssigmnet.global.error.exception

import yourssu.yourssuAssigmnet.global.error.ErrorCode
import kotlin.RuntimeException

open class BusinessException(val errorCode: ErrorCode) : RuntimeException(errorCode.message)
