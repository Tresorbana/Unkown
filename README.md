# Advanced Java / Spring Boot Template Collection

## Core Hibernate Implementation (`01-hibernate-core`)

### Entities & Relationships

```
Student (id, firstName, lastName, email, dob)
  └── OneToMany → Enrolment  (lazy)

Course (id, title, level)
  └── OneToMany → Enrolment  (lazy)

Enrolment  ── composite PK: (student, course, term, year)
  @EmbeddedId EnrolmentId
    @ManyToOne Student  (lazy)
    @ManyToOne Course   (lazy)
    term  String
    year  int
```

**Composite key rule:** `EnrolmentId` is `@Embeddable` + `Serializable`.
`Enrolment` uses `@EmbeddedId`.  All fetching is `FetchType.LAZY`.

---

## Topic Templates

| # | Folder | Covers |
|---|--------|--------|
| 1 | `02-junit-template` | JUnit 5: @Test, @ParameterizedTest, @Nested, Mockito, AssertJ |
| 2 | `03-reactive-template` | Reactor: Mono, Flux, operators, error handling, backpressure, schedulers |
| 3 | `04-springboot-template` | REST Controller → Service → JPA Repository, application.yml |
| 4 | `05-jsp-template` | JSTL views, form submission, flash messages, web.xml |
| 5 | `06-servlets-template` | BaseServlet pattern, Login flow, AuthFilter |

---

## 15 Real-World Projects

| # | Project | Stack Highlights | Key Entities |
|---|---------|-----------------|--------------|
| 01 | University Management | Spring Boot + Hibernate + JSP + Servlets + JUnit | Student, Course, Enrolment (composite PK), Department, Lecturer, Grade |
| 02 | E-Learning Platform | Spring Boot + WebFlux (Reactive) + Hibernate | VideoLesson, Progress, reactive LessonController |
| 03 | Library Management | Spring Boot + Hibernate + JSP + Servlets | Book, Member, BorrowRecord (overdue fine logic) |
| 04 | Hospital Management | Spring Boot + Hibernate + Spring Security | Patient, Doctor, Appointment, Prescription, MedicalRecord |
| 05 | E-Commerce | Spring Boot + WebFlux + Hibernate + Spring Security | Product, Order, CartService (atomic add-to-cart) |
| 06 | Banking System | Spring Boot + Hibernate + Spring Security | Account, Transaction, TransferService (pessimistic lock) |
| 07 | HR Management | Spring Boot + Hibernate + JSP + Servlets | Employee (self-ref manager), LeaveRequest, PayrollService |
| 08 | Restaurant Management | Spring Boot + Hibernate + JSP + Servlets | MenuItem, RestaurantOrder, OrderLine, Kitchen status |
| 09 | Online Exam System | Spring Boot + Hibernate + JSP + Servlets | Exam, Question (MCQ/Essay), GradingService (auto-grade) |
| 10 | Hotel Booking | Spring Boot + Hibernate + Spring Security | Room, Booking (availability check) |
| 11 | Inventory Management | Spring Boot + Hibernate + JSP | InventoryItem, StockMovement, low-stock detection |
| 12 | Job Portal | Spring Boot + WebFlux + Hibernate | JobListing, Application (unique constraint: candidate+job) |
| 13 | Social Media | Spring Boot + WebFlux + Spring Security | UserProfile (follow graph), Post, SSE feed stream |
| 14 | Task Management | Spring Boot + WebFlux + Hibernate | Project, Board, Task (self-ref sub-tasks), Sprint |
| 15 | Event Management | Spring Boot + Hibernate + JSP + Servlets | Event, TicketType, Registration (QR code @PrePersist) |

---

## How to run any project

1. Copy the shared `hibernate.cfg.xml` / `application.yml` pattern and set your DB credentials.
2. Run `mvn spring-boot:run` (Spring Boot projects) or deploy the WAR to Tomcat (JSP/Servlet projects).
3. `hibernate.hbm2ddl.auto=update` will create tables automatically on first run.
