package yourssu.yourssuAssigmnet.global.error.exception

import yourssu.yourssuAssigmnet.global.error.ErrorCode

class EntityNotFoundException(errorCode: ErrorCode) : BusinessException(errorCode)
