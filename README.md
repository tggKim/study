# 1일차

스택은 mysql,jpa,스프링,타임리프 사용 블로그를 만들건데 만들면서 모르는건 찾아보고 공부한 내용 적을 예정임

* 1일차는 가볍게 mysql과 연동해서 Entity만들어서 db에 저장되는지 확인했음
* application.properties 설정을 통해서 db설정하는 것 까지 해결함, 연결할때 다시 찾아보자
* 일단 기본 구성은 Post객체에 제목, 내용, 생성일자, 수정일자를 추가할 계획



# 2일차

* 기본적인 Post객체를 만들고 이를 바탕으로 게시물 목록출력하고 게시물 조회 ,생성, 수정까지 구현함 아마 내일 삭제를 구현할듯
* 페이지는 현재까지 총 3개 목록 출력 페이지, 게시물 상세 정보 페이지, 게시물 수정 페이지와 게시물 생성 페이지는 하나의 html 파일로 만듬(파라미터가 넘어오냐 오지않냐에 따라 수정 혹은 생성페이지가 되도록 만듬)

## 하면서 배운 사실

### 1.기본키 생성전략

* AUTO-> IDENTIT, SEQUENCE, TABLE중 하나 자동으로 선택, 웬만하면 사용 x

* IDENTITY-> 키 생성을 데이터베이스에 위임, em.persist해서 영속화 시킬때 바로 쿼리가 db로 전송 되고 이때 반환받은 식별자 값으로 1차 캐시에 엔티티 등록해 관리, mysql등에서 사용

* SEQUENCE-> db의 시퀸스 활용해 증가 시킴, 오라클,h2 등에 사용

* TABLE-> 키 생성 전용 테이블 하나 만들어서 시퀸스 흉내내는 전략, 잘 사용 x

### 2.Optional

* EntityManager 에서 찾는 값 없으면 null 반환함 그러니까 Optional로 null체크 해야됨

* // Optional의 value는 절대 null이 아니다.
  
Optional<String> optional = Optional.of("MyName");

* // Optional의 value는 값이 있을 수도 있고 null 일 수도 있다.
  
Optional<String> optional = Optional.ofNullable(getName());

* // 값이 없다면 "anonymous" 를 리턴

String name = optional.orElse("anonymous");

* //값을 사용하고자 하면 orElseThrow()나  orElse()사용

User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("user doesn't exist");

### 3.영속성 컨텍스트, merge(), 더티체킹

*  merge()는 준영속 상태 엔티티를 영속상태로 변환해줌, 예시로 html from으로 받아온 객체로 수정하고 싶을때 merge()로 영속 상태로 만들면 수정이 된다
  
* merge()하면 영속성 컨텍스트에서 기본키로 찾음 있으면 엔티티 수정, 없으면 새로 영속성 컨텍스트에 등록
  
* jpa에서는 트랜잭션(@Transactional) 끝난 시점에 처음상태인 스냅샷과 현 시점의 상태 비교해서 쿼리 날림(영속성 컨텍스트가 관리해야됨)

### 4.엔티티에  날짜 저장하기

* 엔티티 내부에 @Prepersist한 메소드는 엔티티 생성될때 작동
  
* 엔티티 내부에 @PreUpdate한 메소드는 엔티티 업데이트 될때 작동
  
* 이걸로 날짜 구현 가능

### 5.프론트와 데이터 주고받을 때 주의사항

*  html form통해 프론트에서 정보받는건 html body에서 쿼리형태로 받는거니까 @ModelAttribute사용(착각해서 @RequestBody를 사용했었다)

## 정리

* Optional과 영속상태에 대한 개념 더티체킹 그리고 생성날짜를 어떻게 db에 저장할지 알게됨

* 프론트랑 정보주고받을 땐 dto클래스를 생성해서 주고받는게 좋음(필요한 데이터만 주고받자)



# 3일차

* delete기능 @RestContrroller로 구현함 조금 애를 먹음

* 리팩토링 함 merge()를 통한 업데이트말고 변경감지를 통해 업데이트 되도록 구현함

* 컨트롤러 계층에서 엔티티 객체 생성하지 않고 dto의 정보를 서비스에 넘겨 처리하도록 바꿈

* Post 내부에 업데이트 하는 메소드 만듬(캡슐화)

## 하면서 배운 사실

### 1. 자바스크립트 fetch()

* fetch()로 /blog/post/'+id  url롤 delete요청을 보내서 delete처리 하려고 했음 그런데 요청을 보내는걸 브라우저의 url이 바뀐다고 생각해서 /blog/post/'+id가 매핑된 컨트롤러에 리다이렉트 하도록 return "redirect:/blog/post/"+id; 했었음 근데 리다이렉트 되지않아서 생각해보니 fetch()는 해당 url에 요청을 보내고 결과를 받아오는거니까 컨트롤러가 요청을 받고 로직을 처리하지만 브라우저의 url이 변하지 않았으므로 view를 보여주지 않을거라는 생각에 이름

### 2. update 할때 merge()를 사용하면 안됨

* merge()를 하게되면 인자로 들어온 객체의 필드값이 null이면 필드의 값이 null로 업데이트 된다, 또한 merge()의 인자로 넣은 객체는 영속성이 되지않는다. 즉 merge()보다는 persist()와 트랜잭션을 통한 변경감지를 사용해야된다.
 
### 3. 컨트롤러에서 엔티티 생성 주의하자

* 꼭 필요한 사항이 아니면 dto로 받은 정보를 service계층으로 넘겨서 처리하자
 
### 3. 엔티티의 필드의 값을 수정할때는 setter보다는 내부에 메소드를 만들어서 처리하자(캡슐화)

## 정리

*  자바스크립트에서 fetch()하면 해당 url로 요청을 보내는거지 브라우저의 url이 변경되는건 아니다.(이게 맞는 건지 좀 헷갈림 좀 더 찾아봐야됨)

*  delete api 메소드는 그냥 @RestController로 리턴값 void로 만들었음 현재의 나로서는 딱히 보낼 데이터가 있어야되나? 라는 생각이 있음

*  merge()의 주의해야 할 점과 설계시 주의할 점 배움


# 4일차

* 댓글 기능 추가함

* 댓글 엔티티 comment 추가(Post와 1 대 다 관계)

* Post와 Comment 연관관계 매핑하는데 개념이 애매한 것이 많아서 힘들었음

* Post 삭제할때 연관된 Comment 삭제 안되서 당황했음

## 하면서 배운 사실

### 1. 연관관계 매핑하기

* 1:N인 매핑에서 양방향으로 매핑하면 1인 쪽에서 리스트를 가지는건 참조를 하기위해서임, N인 쪽에서 매핑하고 persist하고 트랙잭션이 끝난 후 db에 반영되면 나중에 1인 엔티티에서 접근할때 알아서 jpa가 db로 쿼리날림

* 여기서 연관관계 메서드 만드는 이유가 이해가 안됐는데 알고보니 트랜잭션이 일어나기 전에는 db에 반영이 안된 상태니까 1인쪽에서 참조하려고 해도 값이 없음 즉 이런 상황을 방지하기 위해 연관관계 메서드를 통해서 객체끼리 참조하게 만든는 것임

### 2. 연관관계 있는 엔티티 어떻게 저장할 것인가

* 이거는 감을 못잡겠더라 결국에는 Comment에 대한 repository따로 만들어서 내부에 post의 값을 설정하고 persist하도록 했는데 김영한님 강의보니까 영속성 전이를 이용해서 1인 엔티티 쪽에서 추가를 해 영속성 상태로 만들고 트랜잭션이 끝날때 db에 반영하는 식으로 해서 따로 repository 만들지 않았더라 그런데 내 경우에는 Comment가 생성될때 굳이 Post객체를 통해 Comment를 영속성 상태로 만들어 db에 반영하기보단 그냥 repository를 따로 만들어서 Comment의 post를 설정해준 후 db에 반영하는게 간단해 보였다 그래서 그렇게 만들었다.

### 3. 연관관계있는 테이블 어떻게 삭제할 것인가 

* Post를 삭제하면 거기에 연관된 Comment들이 당연히 삭제될 줄 알았는데 이는 바로 오류가 떴다 찾아보니 연관관계가 있어서 Post를 지울 수 없었던 것, 이때 orphanRemoval = true 이나 cascade = CascadeType.ALL 을 Post에 사용해서 부모와 연관관계 끊기면 자동으로 삭제되게 했다. -> 하지만 이 방법은 자식이 참조하는 부모가 한군데인 경우 즉 하나의 부모가 자식들을 관리할 때 사용해야 된다는 것을 배움

## 정리

* 1 대 다 연관관계 매핑 좀 더 자세히 알게 됨

* 연관관계 있는 엔티티 저장 하는 방법에 대해 고민해봄

* 연관관계 있는 테이블 어떻게 삭제할 것인가 고민해봄 


# 5일차

* 조회수 기능을 추가함

## 하면서 배운 사실

### 1.쿠키

* 처음에는 단순히 /blog/post/{id} 요청이 들어오면 Post의 view가 1씩 증가되도록 만들었는데 이렇게 되면 새로고침하거나 수정 페이지에서 취소를 누르는 등 의도하지 않은 경우에도 조회수가 증가함

* 이를 해결하기 위해 쿠키를 사용하기로 함

* 쿠키는 서버가 쿠키를 보내면 클라이언트가 쿠키를 저장했다가 모든 요청에 쿠키를 자동으로 포함시킴(서버롤 요청을 보낼때 쿠키 저장소를 뒤져서 쿠키 헤더 만들어서 보냄)

* 쿠키의 경로는 이 경로를 포함한 하위 경로만 쿠키에 접근가능하다는 의미(setPath("/")로 접근가능)

* 그래서 쿠키를 사용해서 만약 쿠키에 현재 Post의 id 값이 없으면 조회수 1증가 있으면 증가하지 않도록 만듬

* 여기서 동일한 쿠키 객체를 setValue로 값 바꾼다음에 다시 addCookie하면 새로운 쿠키가 추가가 되는줄 알았는데 그게 아니라 바꿔진 값이 반영이 된다.

* 현재 이 기능은 User가 없는 관계로 한번 조회하면 해당 Post id가 쿠키에 저장되어서 조회수가 1에서 올라가지않음 즉 나중에 User를 추가하게 된다면 User별로 어떻게 조회수를 카운트 할 것 인지 공부해야된다.

## 정리

* 쿠키의 개념에 대해서 다시 공부하고 이를 이용해서 조회수 중복 방지 기능을 만들었으나 나중에 User을 추가하게 된다면 어떻게 User별로 조회수를 카운트 되게 만들 것인지 공부해야된


# 6일차

* 검증 기능을 추가했음

* 회원 추가와 회원 수정을 할때 뷰를 합쳤었는데 알아보기도 힘들고 추후에 관리하기 어려울거 같아 add.html 과 update.html로 나누었음

## 하면서 배운 사실

### 1.검증

* 검증기능을 공부하고 추가했는데 이걸 타임리프랑 연동하는데 어려움이 조금 있었음 요지는 @ModelAttribute로 받은 객체는 자동으로 모델에 들어가는데 이때 @ModelAttribute("이름") 을 통해서 타임리프에서 사용하려는 이름과 동일하게 해 줘야 되는 거였음

* 그리고 이때 모델에 자동으로 들어갈때 등록과 수정의 뷰 부분이 합쳐져있어서 이름을 정하기가 애매하고 보는 입장에서도 불편해서 나중에 기능을 추가하거나 변경할때 에로사항이 생길거 같아 뷰를 add.html과 update.html로 분리하고 Post 컨트롤러 로직도 분리했음

* 클라이언트와 서버가 데이터 주고받을 때 상황마다 요구사항이 달라질 수 있으므로 각각 객체를 만드는 것이 옳다고 배워서 바로 적용함 등록할떄는 RequestAddPost 객체를 받고 수정할때는 RequestUpdatePost 객체를 받도록 수정하였음

## 정리

* 뷰도 분리하고 주고받는 데이터도 귀찮다고 하나로 합치면 안되는 거였음 이게 항상 그런건진 모르겠지만 웬만해서는 가장 작은 단위로 쪼개 야하는거 같음 이게 결합도는 작게 응집도는 크게 

