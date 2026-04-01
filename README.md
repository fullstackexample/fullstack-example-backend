## 🧠 Development Philosophy: "Deep Dive & Head-First" Learning

이 프로젝트는 단순히 동작하는 기능을 구현하는 것을 넘어, **기술의 근본 원리를 이해하고 스스로 해결하는 과정**에 초점을 맞췄습니다.

### 1. AI 의존 지양 (Anti-AI Generation)
* 코드를 단순히 생성해 주는 AI의 기능을 최소화하고, 모든 로직을 직접 설계하고 타이핑했습니다.
* AI는 정답을 알려주는 '자판기'가 아닌, 개념의 방향성을 토론하는 **'기술적 파트너'**로만 활용했습니다.
* "돌아가는 코드"보다 **"내가 설명할 수 있는 코드"**를 작성하는 것을 최우선 과제로 삼았습니다.

### 2. '머리 박기' 식 문제 해결 (Learning by Errors)
* 공식 문서(Spring Reference)와 에러 로그를 직접 분석하며 문제의 근본 원인을 파악했습니다.
* 수많은 시행착오와 에러를 성장의 자양분으로 삼아, 발생한 모든 이슈를 직접 해결하며 코드 한 줄 한 줄의 의도를 명확히 했습니다.

### 3. "왜?"라고 묻는 개발 (Focus on the 'Why')
* 특정 기능을 구현할 때 "왜 이 패턴을 써야 하는가?", "왜 이 어노테이션이 필요한가?"를 스스로에게 질문했습니다.
* (예: 단순한 `@Setter` 대신 도메인 메서드를 사용한 이유, `Entity` 대신 `DTO`를 계층 간 이동에 사용한 이유 등)

---

> "AI가 짜준 코드는 내 실력이 아니지만, 내가 고민해서 고친 에러는 내 실력이 된다."는 믿음으로 프로젝트를 진행했습니다.

---

## 🛠 Backend Architecture & API Design Principle

본 프로젝트의 백엔드는 유지보수성, 보안성, 그리고 확장성을 고려하여 **Layered Architecture**와 **DTO 분리 원칙**을 엄격히 준수하여 설계되었습니다.

### 1. Layered Architecture (계층형 아키텍처)
* **Controller:** 클라이언트의 요청을 받고 적절한 상태 코드와 데이터를 반환하는 지배인 역할을 수행합니다.
* **Service:** 실제 비즈니스 로직을 처리하며, 엔티티와 DTO 사이의 변환을 담당하는 요리사 역할을 수행합니다.
* **Repository:** 데이터베이스와의 직접적인 통신을 담당하는 창고지기 역할을 수행합니다.

### 2. DTO (Data Transfer Object) 전략
엔티티(Entity)를 외부로 직접 노출하지 않고, 용도에 따라 **Request**와 **Response** DTO를 엄격히 분리했습니다.
* **보안 강화:** 민감한 정보(비밀번호 등)의 유출을 원천 차단합니다.
* **Over-posting 방지:** 클라이언트로부터 수정 권한이 없는 필드가 유입되는 것을 방지합니다.
* **Java Record 활용:** 불변성을 보장하고 보일러플레이트 코드를 줄여 데이터 전달의 본질에 집중했습니다.

### 3. RESTful API & HTTP Status Codes
리소스의 상태에 따라 적절한 HTTP 메서드와 상태 코드를 사용하여 직관적인 API를 설계했습니다.

| Method | URI | Status Code | Description |
| :--- | :--- | :---: | :--- |
| **POST** | `/api/v1/users` | **201 Created** | 새로운 사용자 리소스 생성 성공 |
| **GET** | `/api/v1/users` | **200 OK** | 전체 사용자 목록 조회 성공 |
| **GET** | `/api/v1/users/{id}` | **200 OK** | 특정 사용자 상세 정보 조회 성공 |
| **PATCH** | `/api/v1/users/{id}` | **200 OK** | 기존 사용자 정보 일부 수정 성공 |
| **DELETE** | `/api/v1/users/{id}` | **204 No Content** | 리소스 삭제 성공 (응답 바디 없음) |

---

## 💡 Key Implementation Details

### 1. Entity Encapsulation (캡슐화)
* 외부에서 무분별하게 데이터를 수정할 수 없도록 `@Setter`를 제거했습니다.
* 엔티티 내부에 의도가 명확한 **도메인 메서드(`update`)**를 구현하여 객체 스스로 상태를 관리하게 했습니다.

### 2. Static Factory Method (정적 팩토리 메서드)
* `UserResponse.from(user)`와 같은 정적 팩토리 메서드를 사용하여 엔티티를 DTO로 변환하는 로직을 캡슐화했습니다.
* 생성자 대신 이름을 가진 메서드를 사용하여 가독성을 높이고 객체 생성 로직을 중앙 집중화했습니다.

### 3. Java Stream API
* 컬렉션 데이터를 처리할 때 `Stream`을 활용하여 가독성 높은 코드를 작성했습니다.
* `map()`을 통한 타입 변환, `filter()`를 통한 데이터 정제 등 선언적인 코드 작성을 지향합니다.

```java
// Stream API 활용 예시: Entity List -> DTO List 변환
public List<UserResponse> findAll() {
    return userRepository.findAll().stream()
        .map(UserResponse::from)
        .toList();
}
```

### 4. Builder Pattern
* Lombok의 `@Builder`를 적용하여 객체 생성 시 파라미터의 가독성을 높이고 실수를 방지했습니다.

---

## 🎓 Lessons Learned
* **왜 DTO를 나누는가?** 단순히 파일을 만드는 귀찮음을 넘어, 시스템의 안정성과 보안을 위해 반드시 필요한 '방화벽'임을 깨달았습니다.
* **왜 도메인 메서드를 쓰는가?** 데이터가 변화하는 지점을 엔티티 내부로 모으는 것이 유지보수 측면에서 얼마나 강력한지 체감했습니다.
* **RESTful의 가치:** 상태 코드 하나만으로도 프론트엔드와 명확하게 소통할 수 있다는 점을 학습했습니다.

---
