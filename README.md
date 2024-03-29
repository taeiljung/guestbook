# 프로젝트 개요
![image](https://github.com/taeiljung/guestbook/assets/101981637/f351bb43-4686-4b15-8186-2df4f5a8dd0e)


* 본 프로젝트는 한국폴리텍대학I 서울정수캠퍼스 인공지능 소프트웨어과 정태일의
* 개인 프로젝트 작업물로, 2023년 4월부터 7월까지 약 4개월간 제작되었습니다.
* 개발에 참고한 자료 [코드로배우는 스프링부트 웹프로젝트](https://www.yes24.com/Product/Goods/96051853)


</br>
</br>

## 프로젝트 개발 기간
2023년 4월부터 2023년 6월까지 (3개월)

</br>
</br>

### 핵심 기술 스택
- **Java**: 프로젝트의 백엔드부분
- **스프링 부트(Spring Boot)**: 스프링 부트 프레임워크를 활용하여 빠르고 간편한 자동 구성과 기본 설정, 생산성 증진
- **Spring Data JPA**: 데이터베이스 연동을 위해 Spring Data JPA를 사용하였습니다. 이를 통해 객체-관계 매핑(ORM)과 데이터 액세스를 쉽게 구현
- **MariaDB**: DB 구성
- **heidiSQL**: DB접속 및 설정
- **Thymeleaf 및 부트스트랩**: 프론트엔드는 Thymeleaf 템플릿 엔진과 부트스트랩을 활용하였습니다.

</br>
</br>

### 기능 및 역할
- 방명록 작성, 조회, 수정, 삭제(CRUD)
- DB와 연동하여 쉬운 데이터관리

</br>
</br>

### 개발 도구
- IntelliJ IDEA

</br>
</br>

### 문제 해결 경험

프로젝트 진행 중 경험한 문제와, 해결방안을 기재하였습니다.

1. **DB연결문제 : DB포트충돌**
   - 이전에 설치해두었던 Mysql과 포트충돌이 발생, 
   - application.properties 내부의 spring.datasource.url=jdbc:mariadb://localhost:{{포트}}/bootex
   - 에서 포트번호를 3306에서 3307로 연결하여 문제해결
  

