# YourssuAssignment
유어슈 백엔드 리쿠르팅 과제입니다.

### API 명세
[API 명세](https://documenter.getpostman.com/view/23682054/2s9YC7SBT2)
- API 명세에는 최소 5개 이상의 Example request와 response가 포함되어 있습니다
- 예시 이미지
  - ![image](https://github.com/googoo9918/YourssuAssignment/assets/102513932/7eb22174-22db-46ef-a6bc-745e5394b051)

## 프로젝트 설명
- User, Article, Comment 패키지는 controller, dto, entity, mapper, repository, service로 구분되어 있습니다
- Common 패키지
  - Common 패키지에는 JPA Auditing을 위한 BaseTimeEntity 클래스와
  - 모든 RequestBody에 공통적으로 들어가는 정보를 담기 위한 BaseUserDto가 포함되어 있습니다
- global 패키지
  - 설정 및 예외처리를 위한 패키지입니다
  - config 패키지
    - 비밀번호 암호화를 위한 BCryptPasswordEncoder,
    - SpringSecurity 권한 허용을 위한 configure이 포함되어 있습니다
  - error 패키지
    - 비즈니스 예외 처리 정보를 담은 ErrorCode
    - 예외 처리 형식을 위한 ErrorResponse가 있습니다
    - GlobalExceptionHandler는 전역 에러 처리를 위한 클래스입니다
