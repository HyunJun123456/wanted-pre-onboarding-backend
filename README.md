# wanted-pre-onboarding-backend
**이름:** 원현준

## 데모 영상
- 구현한 API의 동작을 촬영한 데모 영상 링크: https://drive.google.com/file/d/1wC4p5wXoD2FKSJ22CHp9MQxHKP4kctuI/view?usp=sharing

## API 호출 방법

### 1. 회원가입
- **URL:** http://localhost:8081/api/join
- **Method:** POST
- **Validation 요구사항:**
  - **email:** 타입: 문자열, 필수: 예, 형식: 유효한 이메일 주소 (예: example@email.com)
  - **password:** 타입: 문자열, 필수: 예, 길이: 8자 이상
- **Request Body:** 
  ```json
  {
    "email": "test@nate.com",
    "password": "12345678"
  }
  ```
**필드 설명:**
- `code`: 응답 코드 (1: 성공, -1: 실패)
- `msg`: 응답 메시지
- `data`: 회원 정보. 실패 시 null

**성공 응답:** 
- **HTTP Status Code:** 201 Created
- **Response Body:** 

  ```json
  {
    "code": 1,
    "msg": "회원가입 성공",
    "data": {
      "id": 1,
      "email": "test@nate.com"
    }
  }
  ```
### 2. 실패 응답 (이메일 중복)

- **HTTP Status Code:** 409 Conflict
- **Response Body:** 
  ```json
  {
    "code": -1,
    "msg": "동일한 email이 존재합니다.",
    "data": null
  }
  ```
________________________________________

## 2. 로그인

- **URL:** http://localhost:8081/api/login
- **Method:** POST

### Validation 요구사항:

- **email:**
  - **타입:** 문자열
  - **필수:** 예
  - **형식:** 유효한 이메일 주소 (예: example@email.com)

- **password:**
  - **타입:** 문자열
  - **필수:** 예
  - **길이:** 8자 이상

### Request Body:

```json
{
  "email": "test@nate.com",
  "password": "12345678"
}
```

### 필드 설명:

- **code:** 응답 코드 (1: 성공, -1: 실패)
- **msg:** 응답 메시지
- **data:** 인증 토큰 정보. 실패 시 null

#### 1. 성공 응답:

- **HTTP Status Code:** 200 OK

**Response Body:**
```json
{
    "code": 1,
    "msg": "로그인 성공",
    "data": {
        "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3YW50ZWQiLCJyb2xlIjoiVVNFUiIsImlkIjoxLCJleHAiOjE2OTI0MjQ0OTN9.DmRhSSz0lRSmEzaosCZg_r3OoN9el9r5rbra_GGO-TzUFQr8uMfjmqDDRRHgSAE8IqdhXMn56EHuOyDtS-YDpw"
    }
}
```

#### 2. 실패 응답 (로그인 실패):

- **HTTP Status Code:** 401 Unauthorized

**Response Body:**
```json
{
    "code": -1,
    "msg": "로그인 실패",
    "data": null
}
```
________________________________________

### 3. 게시글 생성 (인증 필요)

- **URL:** http://localhost:8081/api/s/add/board
- **Method:** POST
- **Headers:** 
    - Authorization: Bearer [Your Token]

**Request Body:**
```json
{
  "title": "Sample Title",
  "content": "Sample Content"
}
```

**필드 설명:**
- `code`: 응답 코드 (1: 성공, -1: 실패)
- `msg`: 응답 메시지
- `data`: 추가적인 데이터 정보. 이 경우에는 제공되지 않으므로 null

**성공 응답:** 
- **HTTP Status Code:** 201 Created

**Response Body:**
```json
{
    "code": 1,
    "msg": "게시글 생성 완료",
    "data": null
}
```
________________________________________

### 4. 게시글 목록 조회

- **URL:** http://localhost:8081/api/fetch/boards?page=0&size=10
- **Method:** GET
- **Parameters:**
  - `page`: 조회하고자 하는 페이지 번호 (0부터 시작)
  - `size`: 한 페이지에 표시될 게시글 수

**필드 설명:**
- `code`: 응답 코드 (1: 성공, -1: 실패)
- `msg`: 응답 메시지
- `data`: 
  - `content`: 게시글 목록
  - `pageable`: 페이지 관련 정보
  - `sort`: 정렬 관련 정보
  - `offset`: 현재 페이지 시작 인덱스
  - `pageSize`: 한 페이지에 표시될 게시글 수
  - `pageNumber`: 현재 페이지 번호
  - `unpaged`: 페이지네이션 미사용 여부
  - `paged`: 페이지네이션 사용 여부
  - `totalElements`: 전체 게시글 수
  - `totalPages`: 전체 페이지 수
  - `last`: 마지막 페이지 여부
  - `size`: 한 페이지에 표시될 게시글 수
  - `number`: 현재 페이지 번호
  - `sort`: 정렬 관련 정보
  - `first`: 첫 번째 페이지 여부
  - `numberOfElements`: 현재 페이지에 있는 게시글 수
  - `empty`: 게시글이 없는 페이지 여부

**성공 응답:**
- **HTTP Status Code:** 200 OK

**Response Body:**
```json
{
    "code": 1,
    "msg": "게시글 목록 조회 완료",
    "data": {
        "content": [
            {
                "boardId": 1,
                "title": "타이틀",
                "content": "내용",
                "userId": 1
            }
        ],
        "pageable": {
            "sort": {
                "unsorted": true,
                "empty": true,
                "sorted": false
            },
            "offset": 0,
            "pageSize": 1,
            "pageNumber": 0,
            "unpaged": false,
            "paged": true
        },
        "totalElements": 2,
        "totalPages": 2,
        "last": false,
        "size": 1,
        "number": 0,
        "sort": {
            "unsorted": true,
            "empty": true,
            "sorted": false
        },
        "first": true,
        "numberOfElements": 1,
        "empty": false
    }
}
```
________________________________________

### 5. 게시글 상세 조회

- **URL:** http://localhost:8081/api/fetch/board/{boardId}
- **Method:** GET

**필드 설명:**
- `code`: 응답 코드 (1: 성공, -1: 실패)
- `msg`: 응답 메시지
- `data`: 
  - `boardId`: 게시글 ID
  - `title`: 게시글 제목
  - `content`: 게시글 내용
  - `userId`: 게시글 작성자 ID

**1. 성공 응답:**
- **HTTP Status Code:** 200 OK

**Response Body:**
```json
{
    "code": 1,
    "msg": "특정 게시글 조회 완료",
    "data": {
        "boardId": 1,
        "title": "타이틀",
        "content": "내용",
        "userId": 1
    }
}
```

### 2. 실패 응답 (게시글을 찾을 수 없는 경우)

- **HTTP Status Code:** 404 Not Found

**Response Body:**
```json
{
    "code": -1,
    "msg": "게시글을 찾을 수 없습니다.",
    "data": null
}
```
________________________________________

### 6. 게시글 수정 (인증 필요)

- **URL:** http://localhost:8081/api/s/edit/board
- **Method:** PATCH
- **Headers:** Authorization: Bearer [Your Token]

**필드 설명:**
- **boardId:** 수정하고자 하는 게시글의 ID
- **title:** 수정하고자 하는 게시글의 제목
- **content:** 수정하고자 하는 게시글의 내용

**Request Body:**
```json
{ 
  "boardId": 1, 
  "title": "Updated Title", 
  "content": "Updated Content" 
}
```

**필드 설명:**
- **code:** 응답 코드 (1: 성공, -1: 실패)
- **msg:** 응답 메시지

#### 1. 성공 응답:
- **HTTP Status Code:** 200 OK
- **Response Body:**
```json
{
    "code": 1,
    "msg": "특정 게시글 수정 완료",
    "data": null
}
```

#### 2. 실패 응답 (게시글을 찾을 수 없는 경우):
- **HTTP Status Code:** 404 Not Found
- **Response Body:**
```json
{
    "code": -1,
    "msg": "게시글을 찾을 수 없습니다.",
    "data": null
}
```
________________________________________

### 7. 게시글 삭제 (인증 필요)

- **URL:** http://localhost:8081/api/s/edit/board/{boardId}
- **Method:** DELETE
- **Headers:** Authorization: Bearer [Your Token]

#### 필드 설명:
- **code:** 응답 코드 (1: 성공, -1: 실패)
- **msg:** 응답 메시지

#### 1. 성공 응답:
- **HTTP Status Code:** 200 OK
- **Response Body:**
```json
{
    "code": 1,
    "msg": "특정 게시글 삭제 완료",
    "data": null
}
```

#### 2. 실패 응답 (게시글을 찾을 수 없는 경우):
- **HTTP Status Code:** 404 Not Found
- **Response Body:**
```json
{
    "code": -1,
    "msg": "게시글을 찾을 수 없습니다.",
    "data": null
}
```
________________________________________

## 데이터베이스 테이블 구조

### 1. User 테이블
- **ID:** Long, Primary Key, Auto Increment
- **EMAIL:** String, Length: 255
- **PASSWORD:** String, Length: 255
- **ROLE:** Enum (values: USER, ADMIN)

### 2. Board 테이블
- **ID:** Long, Primary Key, Auto Increment
- **TITLE:** String, Length: 255
- **CONTENT:** String
- **USER_ID:** Long, Foreign Key (References User.ID)
________________________________________

## 구현 방법 및 이유에 대한 설명

### 사용된 주요 기술 및 도구:
- **프레임워크:** Spring Boot
- **데이터베이스:** MySQL
- **ORM:** JPA (Java Persistence API)

### 주요 설계 및 아키텍처 결정:
- **RESTful API 설계**
- **JWT 인증**

### 사용된 주요 기술 및 도구:

- **프레임워크:** Spring Boot 
  - 이 프레임워크는 애플리케이션 개발의 빠른 시작과 프로덕션 준비 상태의 실행을 지원하기 때문에 선택하였습니다.
  
- **데이터베이스:** MySQL 
  - 안정성 및 확장성이 뛰어난 오픈 소스 관계형 데이터베이스로, 본 프로젝트의 데이터 저장 요구 사항을 충족시키기 위해 선택하였습니다.
  
- **ORM:** JPA (Java Persistence API) 
  - 객체 지향적인 코드 작성과 데이터베이스의 테이블 간의 매핑을 쉽게 관리할 수 있도록 도와주기 때문에 사용하였습니다. JPA는 개발의 생산성 향상 및 코드의 유지보수를 용이하게 합니다.

### 주요 설계 및 아키텍처 결정: 

- **RESTful API 설계:** 백엔드 서비스는 REST 원칙을 따르는 API로 구성하였습니다. 
  - 이를 통해 프론트엔드와의 통신이 표준화되며, 다양한 클라이언트(웹, 모바일 등)에서의 사용이 용이하게 되었습니다. 
  - RESTful 설계는 상태가 없는 서버와 클라이언트 간의 통신, 캐시 가능한 요청, 
자기 설명적인 API 엔드포인트 등의 장점을 제공하여 시스템의 확장성과 유지보수성을 향상시켰습니다.

- **JWT 인증:** 
  - 특정 기능 또는 접근법의 이유: 특정 기능을 구현하거나 특정 접근법을 선택한 이유를 설명합니다.
  - JWT를 사용하여 사용자 인증을 구현하였습니다. 이는 Stateless한 서버 아키텍처를 유지하면서 보안성을 확보하기 위한 결정이었습니다.

