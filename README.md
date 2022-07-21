# signup-api


### 사용 기술

1. Spring Boot
2. JPA
3. JWT
4. H2
5. ModelMapper

### 기능 목록

1. 회원가입
2. 로그인
3. 내 정보 보기
4. 비밀번호 찾기(재설정)
5. 인증(회원가입, 비밀번호 찾기)

### API 사용 방법

1. 회원가입을 위한 인증 코드 발급
2. 인증코드 검증 후 ReferrerToken 발급
3. ReferrerToken을 이용한 회원가입
4. 로그인(핸드폰 번호 or 이메일)
5. 내 정보 보기
6. 비밀번호 찾기(재설정)을 위한 인증 코드 발급
7. 인증코드 검증 후 ReferrerToken 발급
8. ReferrerToken을 이용한 비밀번호 찾기(재설정)