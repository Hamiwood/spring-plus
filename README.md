# SPRING PLUS

### 코드 기능 개선 프로젝트

#### Lv1_ 01. @Transactional의 이해
+ todoController의 saveTodo 메서드에 @Transactional 을 추가

#### Lv1_ 02. JWT의 이해
+ User에 nickname 컬럼 추가
+ JWTUtil 수정

#### Lv1_ 03. AOP의 이해
+ 해당 메서드가 실행 전 동작해야 하므로, pointCut의 url 수정 및 어노테이션 수정

#### Lv1_ 04. Controller 테스트의 이해
+ 400 에러가 발생되어야 하는 테스트이므로, then의 결과 값을 400 에러에 맞게 수정

#### Lv1_ 05. JPA의 이해
+ weather, 수정일의 기간 시작과 끝(startDate, endDate)을 해당 메서드의 RequestParam에 추가

#### Lv2_ 06. JPA Cascade
+ manager가 todo를 생성할 때, 함께 생성되어야 하므로 cascade = CascadeType.PERSIST 설정을 추가

#### Lv2_ 07. N+1
+ getComments()에서 각각 다른 user가 한 일정에 댓글을 여러개 달았을 때 N+1 문제가 발생
+ FetchJoin으로 한번에 Join하고 중복값이 없도록 Distinct를 사용

#### Lv2_ 08. QueryDSL
+ Q클래스를 사용하기 위해 Build에 의존성을 추가하고, CustomRepository를 만들어 QueryDSL 구현
+ N+1 문제가 발생하지 않도록 fetchJoin() 설정 추가

#### Lv2_ 09. Spring security
+ 기존 Filter와 Argument Resolver가 하던 역할을 security가 대신 진행
+ 오류 발생 위험 제거를 위해 관련 코드들을 전부 삭제하고 JwtFilter와 Util을 수정
+ AuthUser를 사용하던 모든 코드가 Security의 Authentication을 사용할 수 있도록 수정

#### Lv3_ 10. QueryDSL
+ 새로운 일정 검색 메서드 추가
+ 동적 검색을 위해 QueryDSL을 사용
+ 부분적인 검색을 위해 containsIgnorCase() 사용
+ Paging 처리

### 관련 자료

[과제 발제](https://teamsparta.notion.site/1382dc3ef51480da99c6db2988ef5396)

[과제 트러블 슈팅](https://velog.io/@hami/%ED%8A%B8%EB%9F%AC%EB%B8%94%EC%8A%88%ED%8C%85-%EC%BD%94%EB%93%9C%EA%B0%9C%EC%84%A0%EA%B3%BC%EC%A0%9C2)
