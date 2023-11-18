package yourssu.yourssuAssigmnet.global.resolver.authinfo

// 어노테이션의 적용 대상을 지정(파라미터)
@Target(AnnotationTarget.VALUE_PARAMETER)
// 어노테이션이 유지될 기간을 정의(런타임에도 유지)
@Retention(AnnotationRetention.RUNTIME)
// 문서포함여부
@MustBeDocumented
annotation class Auth
