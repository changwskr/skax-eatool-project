package com.skax.eatool.controller;

import com.skax.eatool.user.domain.UserStatistics;
import com.skax.eatool.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * MainController 단위 테스트
 */
@ExtendWith(MockitoExtension.class)
class MainControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @InjectMocks
    private MainController mainController;

    @Test
    void testUserPage_Success() {
        // Given
        UserStatistics mockStatistics = UserStatistics.builder()
                .totalUsers(100)
                .activeUsers(80)
                .adminUsers(5)
                .todayLoginUsers(25)
                .serviceStatus("정상")
                .build();

        when(userService.getUserStatistics()).thenReturn(mockStatistics);

        // When
        String viewName = mainController.userPage(model);

        // Then
        assertEquals("service/user", viewName);

        // 모델에 추가된 속성들 확인
        verify(model).addAttribute("title", "User Management");
        verify(model).addAttribute("serviceName", "User");
        verify(model).addAttribute("description", "사용자 관리 및 인증 서비스");
        verify(model).addAttribute("userStatistics", mockStatistics);
        verify(model).addAttribute("totalUsers", 100L);
        verify(model).addAttribute("activeUsers", 80L);
        verify(model).addAttribute("adminUsers", 5L);
        verify(model).addAttribute("todayLoginUsers", 25L);
        verify(model).addAttribute("serviceStatus", "정상");

        verify(userService, times(1)).getUserStatistics();
    }

    @Test
    void testUserPage_WithZeroStatistics() {
        // Given
        UserStatistics mockStatistics = UserStatistics.builder()
                .totalUsers(0)
                .activeUsers(0)
                .adminUsers(0)
                .todayLoginUsers(0)
                .serviceStatus("정상")
                .build();

        when(userService.getUserStatistics()).thenReturn(mockStatistics);

        // When
        String viewName = mainController.userPage(model);

        // Then
        assertEquals("service/user", viewName);

        // 모델에 추가된 속성들 확인
        verify(model).addAttribute("totalUsers", 0L);
        verify(model).addAttribute("activeUsers", 0L);
        verify(model).addAttribute("adminUsers", 0L);
        verify(model).addAttribute("todayLoginUsers", 0L);
        verify(model).addAttribute("serviceStatus", "정상");
    }

    @Test
    void testUserPage_ServiceException() {
        // Given
        when(userService.getUserStatistics()).thenThrow(new RuntimeException("Database error"));

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            mainController.userPage(model);
        });

        verify(userService, times(1)).getUserStatistics();
    }
}