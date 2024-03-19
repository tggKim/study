<p>1일차</p>

스택은 mysql,jpa,스프링,타임리프 사용 블로그를 만들건데 만들면서 모르는건 찾아보고 공부한 내용 적을 예정임

* 1일차는 가볍게 mysql과 연동해서 Entity만들어서 db에 저장되는지 확인했음
* application.properties 설정을 통해서 db설정하는 것 까지 해결함, 연결할때 다시 찾아보자
* 일단 기본 구성은 Post객체에 제목, 내용, 생성일자, 수정일자를 추가할 계획

<p>2일차</p>

* 기본적인 Post객체를 만들고 이를 바탕으로 게시물 목록출력하고 게시물 조회 ,생성, 수정까지 구현함 아마 내일 삭제를 구현할듯
* 페이지는 현재까지 총 3개 목록 출력 페이지, 게시물 상세 정보 페이지, 게시물 수정 페이지와 게시물 생성 페이지는 하나의 html 파일로 만듬(파라미터가 넘어오냐 오지않냐에 따라 수정 혹은 생성페이지가 되도록 만듬)

# 하면서 배운 사실

### 기본키 생성전략

* AUTO-> IDENTIT, SEQUENCE, TABLE중 하나 자동으로 선택, 웬만하면 사용 x

* IDENTITY-> 키 생성을 데이터베이스에 위임, em.persist해서 영속화 시킬때 바로 쿼리가 db로 전송 되고 이때 반환받은 식별자 값으로 1차 캐시에 엔티티 등록해 관리, mysql등에서 사용

* SEQUENCE-> db의 시퀸스 활용해 증가 시킴, 오라클,h2 등에 사용

* TABLE-> 키 생성 전용 테이블 하나 만들어서 시퀸스 흉내내는 전략, 잘 사용 x

### Optional

* EntityManager 에서 찾는 값 없으면 null 반환함 그러니까 Optional로 null체크 해야됨

* // Optional의 value는 절대 null이 아니다.
  
Optional<String> optional = Optional.of("MyName");

* // Optional의 value는 값이 있을 수도 있고 null 일 수도 있다.
  
Optional<String> optional = Optional.ofNullable(getName());

* // 값이 없다면 "anonymous" 를 리턴

String name = optional.orElse("anonymous");

* //값을 사용하고자 하면 orElseThrow()나  orElse()사용

User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("user doesn't exist");

### 영속성 컨텍스트, merge(), 더티체킹

*  merge()는 준영속 상태 엔티티를 영속상태로 변환해줌, 예시로 html from으로 받아온 객체로 수정하고 싶을때 merge()로 영속 상태로 만들면 수정이 된다
  
* merge()하면 영속성 컨텍스트에서 기본키로 찾음 있으면 엔티티 수정, 없으면 새로 영속성 컨텍스트에 등록
  
* jpa에서는 트랜잭션(@Transactional) 끝난 시점에 처음상태인 스냅샷과 현 시점의 상태 비교해서 쿼리 날림(영속성 컨텍스트가 관리해야됨)

### 엔티티에  날짜 저장하기

* 엔티티 내부에 @Prepersist한 메소드는 엔티티 생성될때 작동
* 엔티티 내부에 @PreUpdate한 메소드는 엔티티 업데이트 될때 작동
* 이걸로 날짜 구현 가능

### 프론트와 데이터 주고받을 때 주의사항

*  html form통해 프론트에서 정보받는건 html body에서 쿼리형태로 받는거니까 @ModelAttribute사용(착각해서 @RequestBody를 사용했었다)

# 정리

* Optional과 영속상태에 대한 개념 더티체킹 그리고 생성날짜를 어떻게 db에 저장할지 알게됨

* 프론트랑 정보주고받을 땐 dto클래스를 생성해서 주고받는게 좋음(필요한 데이터만 주고받자) 
