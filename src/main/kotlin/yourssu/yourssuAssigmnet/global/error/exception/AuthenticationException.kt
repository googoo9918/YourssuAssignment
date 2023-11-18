package yourssu.yourssuAssigmnet.global.error.exception

import yourssu.yourssuAssigmnet.global.error.ErrorCode

class AuthenticationException(errorCode: ErrorCode) : BusinessException(errorCode)
