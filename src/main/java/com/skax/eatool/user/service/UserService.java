package com.skax.eatool.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;

import com.skax.eatool.user.exception.CustomException;
import com.skax.eatool.user.exception.ErrorCode;
import com.skax.eatool.user.controller.port.UserServicePort;
import com.skax.eatool.user.domain.User;
import com.skax.eatool.user.domain.UserCreate;
import com.skax.eatool.user.service.port.UserRepositoryPort;
import com.skax.eatool.user.domain.UserStatistics;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService implements UserServicePort {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoder passwordEncoder;

    // 생성자에 @Qualifier 추가
    public UserService(
            @Qualifier("userRepositoryPortJpaCustomImpl") UserRepositoryPort userRepositoryPort,
            PasswordEncoder passwordEncoder) {
        this.userRepositoryPort = userRepositoryPort;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입 메서드
    @Override
    @Transactional
    public User signUp(UserCreate userCreate) {
        log.info("[UserService.signUp START]");
        // 입력받은 이메일로 회원 존재 여부
        checkUserExistByEmail(userCreate.getEmail());
        User model = User.from(userCreate, passwordEncoder);
        // 기본권한 부여
        User savedModel = userRepositoryPort.save(model);
        log.info("[UserService.signUp END]");
        return savedModel;
    }

    // 인증
    @Override
    public String authenticate(String email, String rawPassword) {
        log.info("[UserService.authenticate START]");
        User user = userRepositoryPort.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));
        boolean matches = passwordEncoder.matches(rawPassword, user.getPassword());
        if (!matches) {
            throw new CustomException(ErrorCode.NOT_MATCHED_PASSWORD);
        }
        log.info("Authentication successful for user: {}", user.getEmail());
        log.info("[UserService.authenticate END]");
        return "Authentication successful";
    }

    // 전체 사용자 조회
    @Override
    public List<User> findAllUsers() {
        log.info("[UserService.findAllUsers START]");
        List<User> users = userRepositoryPort.findAll();
        log.info("[UserService.findAllUsers END]");
        return users;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        log.info("[UserService.findAll START]");
        Page<User> result = userRepositoryPort.findAll(pageable);
        log.info("[UserService.findAll END]");
        return result;
    }

    @Override
    public User getById(Long id) {
        log.info("[UserService.getById START]");
        User user = userRepositoryPort.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));
        log.info("[UserService.getById END]");
        return user;
    }

    @Override
    public Page<User> findAdminUsers(Pageable pageable) {
        log.info("[UserService.findAdminUsers START]");
        // Admin 사용자 조회 로직은 우선순위 - 모든 사용자 반환
        Page<User> result = userRepositoryPort.findAll(pageable);
        log.info("[UserService.findAdminUsers END]");
        return result;
    }

    @Override
    public User updateUserStatus(User user) {
        log.info("[UserService.updateUserStatus START]");
        // 조회
        User findUser = userRepositoryPort.findByEmail(user.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));

        // 상태 업데이트
        User updateUser = findUser.updateStatus(user.getStatus());
        User result = userRepositoryPort.updateStatus(updateUser);
        log.info("[UserService.updateUserStatus END]");
        return result;
    }

    @Override
    public User updateUser(User user) {
        log.info("[UserService.updateUser START]");
        // 조회
        User findUser = userRepositoryPort.findByEmail(user.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));

        User updatedUser = findUser.updateUser(user, passwordEncoder);
        User result = userRepositoryPort.save(updatedUser);
        log.info("[UserService.updateUser END]");
        return result;
    }

    public User update(User user) {
        log.info("[UserService.update START]");
        User existingUser = userRepositoryPort.findById(user.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));

        User updatedUser = existingUser.updateUser(user, passwordEncoder);
        User result = userRepositoryPort.save(updatedUser);
        log.info("[UserService.update END]");
        return result;
    }

    // 이메일로 존재 여부 체크
    private void checkUserExistByEmail(String email) {
        log.info("[UserService.checkUserExistByEmail START]");
        Optional<User> optionalUser = userRepositoryPort.findByEmail(email);
        if (optionalUser.isPresent()) {
            log.info("[UserService.checkUserExistByEmail END]");
            throw new CustomException(ErrorCode.EXIST_ELEMENT);
        }
        log.info("[UserService.checkUserExistByEmail END]");
    }

    /**
     * 사용자 생성 (관리자 컨트롤러)
     */
    @Override
    @Transactional
    public User createUser(User user) {
        log.info("[UserService.createUser START]");

        // 사용자 ID 중복 체크
        Optional<User> existingUser = userRepositoryPort.findByUserId(user.getUserId());
        if (existingUser.isPresent()) {
            log.info("[UserService.createUser END]");
            throw new CustomException(ErrorCode.EXIST_ELEMENT);
        }

        // 이메일 중복 체크
        checkUserExistByEmail(user.getEmail());

        // 새로운 User 객체 생성 (비밀번호 암호화 및 상태 설정)
        User newUser = User.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .userId(user.getUserId())
                .address(user.getAddress())
                .job(user.getJob())
                .age(user.getAge())
                .company(user.getCompany())
                .name(user.getName())
                .phone(user.getPhone())
                .department(user.getDepartment())
                .position(user.getPosition())
                .userType(user.getUserType())
                .password(passwordEncoder.encode(user.getPassword()))
                .status(user.getStatus() != null ? user.getStatus() : com.skax.eatool.user.domain.UserStatus.ACTIVE)
                .createdDate(java.time.LocalDateTime.now())
                .lastModifiedDate(java.time.LocalDateTime.now())
                .build();

        // 사용자 저장
        User savedUser = userRepositoryPort.save(newUser);
        log.info("User created successfully: {}", savedUser.getUserId());
        log.info("[UserService.createUser END]");

        return savedUser;
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        log.info("[UserService.findByUserId START] - userId: {}", userId);
        Optional<User> result = userRepositoryPort.findByUserId(userId);
        log.info("[UserService.findByUserId END] - found: {}", result.isPresent());
        return result;
    }

    @Override
    public void deleteUser(Long id) {
        log.info("[UserService.deleteUser START] - id: {}", id);
        userRepositoryPort.deleteById(id);
        log.info("[UserService.deleteUser END] - id: {}", id);
    }

    /**
     * 사용자 검색 (이름, 이메일, 사용자 ID로 검색)
     */
    public Page<User> searchUsers(String keyword, Pageable pageable) {
        log.info("[UserService.searchUsers START] - keyword: {}", keyword);

        if (keyword == null || keyword.trim().isEmpty()) {
            return userRepositoryPort.findAll(pageable);
        }

        Page<User> result = userRepositoryPort.searchUsers(keyword, pageable);

        log.info("[UserService.searchUsers END] - found: {}", result.getTotalElements());
        return result;
    }

    /**
     * 사용자 통계 정보 조회
     */
    public UserStatistics getUserStatistics() {
        log.info("[UserService.getUserStatistics START]");

        long totalUsers = userRepositoryPort.findAll().size();
        long activeUsers = userRepositoryPort.countByStatus(com.skax.eatool.user.domain.UserStatus.ACTIVE);
        long adminUsers = userRepositoryPort.countByUserType("ADMIN");
        long todayLoginUsers = userRepositoryPort.countTodayLoginUsers();

        UserStatistics statistics = UserStatistics.builder()
                .totalUsers(totalUsers)
                .activeUsers(activeUsers)
                .adminUsers(adminUsers)
                .todayLoginUsers(todayLoginUsers)
                .serviceStatus("정상")
                .build();

        log.info("[UserService.getUserStatistics END] - total: {}, active: {}, admin: {}",
                totalUsers, activeUsers, adminUsers);

        return statistics;
    }
}
