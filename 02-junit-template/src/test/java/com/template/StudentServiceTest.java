package com.template;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * JUnit 5 template — covers the most common test patterns:
 *   @Test, @BeforeEach/@AfterEach, @BeforeAll/@AfterAll,
 *   @ParameterizedTest, @Nested, Mockito mocks, AssertJ assertions.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("StudentService Tests")
class StudentServiceTest {

    @Mock
    private StudentRepository repository;

    @InjectMocks
    private StudentService service;

    // ─── lifecycle ────────────────────────────────────────────────────────────

    @BeforeAll
    static void initAll() {
        // runs once before all tests in this class
    }

    @AfterAll
    static void tearDownAll() {
        // runs once after all tests in this class
    }

    @BeforeEach
    void init() {
        // runs before each test
    }

    @AfterEach
    void tearDown() {
        // runs after each test
    }

    // ─── basic tests ──────────────────────────────────────────────────────────

    @Test
    @DisplayName("findById returns student when present")
    void findById_returnsStudent_whenPresent() {
        Student student = new Student(1L, "Ada", "Lovelace", "ada@example.com", null);
        when(repository.findById(1L)).thenReturn(Optional.of(student));

        Student result = service.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo("Ada");
        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findById throws when student not found")
    void findById_throws_whenAbsent() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(99L))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("99");
    }

    // ─── parameterized tests ──────────────────────────────────────────────────

    @ParameterizedTest(name = "email={0} should be valid")
    @ValueSource(strings = {"ada@example.com", "user.name+tag@domain.org"})
    void validateEmail_acceptsValidEmails(String email) {
        assertThat(service.isValidEmail(email)).isTrue();
    }

    @ParameterizedTest(name = "firstName={0}, lastName={1}")
    @CsvSource({
            "Ada,  Lovelace",
            "Alan, Turing",
            "Grace, Hopper"
    })
    void createStudent_setsFullName(String first, String last) {
        Student s = service.create(first.trim(), last.trim(), first.trim().toLowerCase() + "@test.com", null);
        assertThat(s.getFirstName()).isEqualTo(first.trim());
        assertThat(s.getLastName()).isEqualTo(last.trim());
    }

    // ─── nested grouping ──────────────────────────────────────────────────────

    @Nested
    @DisplayName("when student already exists")
    class WhenStudentExists {

        @Test
        @DisplayName("save throws DuplicateEmailException")
        void save_throws_onDuplicateEmail() {
            when(repository.existsByEmail("ada@example.com")).thenReturn(true);

            assertThatThrownBy(() -> service.create("Ada", "L", "ada@example.com", null))
                    .isInstanceOf(DuplicateEmailException.class);
        }
    }
}
