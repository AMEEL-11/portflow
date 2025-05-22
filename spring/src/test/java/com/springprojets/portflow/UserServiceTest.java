package com.springprojets.portflow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@example.com");
        testUser.setPassword("password");
        testUser.setRole(UserRole.CLIENT);
        testUser.setFirstName("John");
        testUser.setLastName("Doe");
    }

    @Test
    void createUser_Success() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User result = userService.createUser(testUser);

        assertNotNull(result);
        assertEquals(testUser.getEmail(), result.getEmail());
        verify(passwordEncoder).encode(testUser.getPassword());
        verify(userRepository).save(testUser);
    }

    @Test
    void createUser_EmailExists() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> userService.createUser(testUser));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void findByEmail_Success() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));

        Optional<User> result = userService.findByEmail("test@example.com");

        assertTrue(result.isPresent());
        assertEquals(testUser.getEmail(), result.get().getEmail());
    }

    @Test
    void findAllUsers_Success() {
        List<User> users = Arrays.asList(testUser);
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.findAllUsers();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void findById_Success() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(testUser));

        Optional<User> result = userService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(testUser.getId(), result.get().getId());
    }

    @Test
    void updateUser_Success() {
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User result = userService.updateUser(testUser);

        assertNotNull(result);
        assertEquals(testUser.getEmail(), result.getEmail());
        verify(userRepository).save(testUser);
    }

    @Test
    void deleteUser_Success() {
        doNothing().when(userRepository).deleteById(anyLong());

        userService.deleteUser(1L);

        verify(userRepository).deleteById(1L);
    }
} 