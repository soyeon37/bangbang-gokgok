## DB Modeling

---

#### ER Diagram

![img](/springboot/erdiagram.png)

#### 구현 기능

Spring Boot + MyBatis + JSP를 활용하여 구현되었습니다.

DB와 연결이 되지 않을시 아래의 두 SQL을 Import 하셔야 합니다.

[SQL_All](/springboot//sql/Dump20230324.sql) : 모든 테이블과 데이터를 가져옵니다.

[SQL_TABLE](/springboot//sql/EnjoyTrip.sql) : 모든 테이블을 생성합니다.

### 2. 관광지 검색
- 지역, 시군구, 관광지 유형, 검색어를 통한 관광지 정보 검색 기능 제공
- 많은 관광지 중에 원하는 관광지를 찾을 수 있도록 추가 검색 기능 제공
- 관광지 여행 경로 추가 기능 가능
![search.png](/springboot//picture/search.png)

### 3. 사용자 계정
- 회원가입, 로그인 기능 구현
![join.png](/springboot/picture/join.png)

### 4. 여행 계획
- 원하는 여행지 경로 등록
- 여행 경로를 모두 탐방할 때의 이동 거리와 소요 시간 안내
- 나의 여행 계획 페이지에서 수정 및 삭제 가능
![tripplan.png](/springboot//picture/tripplan.png)

### 5. 공지사항
- 공지사항 게시글 등록, 수정, 삭제 기능 구현
![notice.png](/springboot//picture/notice.png)

### 6. 여행 정보 공유 게시판
- 여행 정보 게시글 등록, 수정, 삭제 기능 구현
![board.png](/springboot//picture/board.png)

### 7. 회원 정보
- 회원 정보 수정, 삭제 기능 구현
![mypage.png](/springboot//picture/mypage.png)# soyeon
