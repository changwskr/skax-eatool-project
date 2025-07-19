package com.skax.eatool.user.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import com.skax.eatool.user.domain.User;
import com.skax.eatool.user.domain.UserStatus;
import com.skax.eatool.user.infrastructure.jpa.UserJpaEntity;
import com.skax.eatool.user.infrastructure.jpa.UserRepositoryJpa;
import com.skax.eatool.user.service.port.UserRepositoryPort;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryPortJpaCustomImpl implements UserRepositoryPort {

    private final UserRepositoryJpa userRepositoryJpa;

    @Override
    public Optional<User> findById(Long id) {
        log.info("[UserRepositoryPortJpaCustomImpl] findById START - id: {}", id);

        Optional<User> result = userRepositoryJpa.findById(id).map(UserJpaEntity::toModel);

        log.info("[UserRepositoryPortJpaCustomImpl] findById END - found: {}", result.isPresent());
        return result;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        log.info("[UserRepositoryPortJpaCustomImpl] findByEmail START - email: {}", email);

        Optional<User> result = userRepositoryJpa.findByEmail(email)
                .map(UserJpaEntity::toModel);

        log.info("[UserRepositoryPortJpaCustomImpl] findByEmail END - found: {}", result.isPresent());
        return result;
    }

    @Override
    public User save(User user) {
        log.info("[UserRepositoryPortJpaCustomImpl] save START - userId: {}", user.getUserId());

        User result = userRepositoryJpa.save(UserJpaEntity.from(user)).toModel();

        log.info("[UserRepositoryPortJpaCustomImpl] save END - userId: {}", result.getUserId());
        return result;
    }

    @Override
    public List<User> findAll() {
        log.info("[UserRepositoryPortJpaCustomImpl] findAll START");

        List<User> result = userRepositoryJpa.findAll()
                .stream()
                .map(UserJpaEntity::toModel)
                .toList();

        log.info("[UserRepositoryPortJpaCustomImpl] findAll END - count: {}", result.size());
        return result;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        log.info("[UserRepositoryPortJpaCustomImpl] findAll(Pageable) START - pageable: {}", pageable);

        Page<User> result = userRepositoryJpa.findAll(pageable)
                .map(UserJpaEntity::toModel);

        log.info("[UserRepositoryPortJpaCustomImpl] findAll(Pageable) END - totalElements: {}",
                result.getTotalElements());
        return result;
    }

    @Override
    @Deprecated
    public Page<User> findAdminUsers(Pageable pageable) {
        log.info("[UserRepositoryPortJpaCustomImpl] findAdminUsers START - pageable: {}", pageable);

        // 임시: role이 ADMIN인 사용자만 조회 (UserJpaEntity에 role 필드가 없다고 가정)
        // 실제로는 UserRepositoryJpa에 쿼리 메서드를 추가해야 함
        // return userRepositoryJpa.findByRole("ADMIN",
        // pageable).map(UserJpaEntity::toModel);
        // 임시로 전체 사용자를 반환
        Page<User> result = userRepositoryJpa.findAll(pageable).map(UserJpaEntity::toModel);

        log.info("[UserRepositoryPortJpaCustomImpl] findAdminUsers END - totalElements: {}", result.getTotalElements());
        return result;
    }

    @Override
    public User updateStatus(User user) {
        log.info("[UserRepositoryPortJpaCustomImpl] updateStatus START - userId: {}, status: {}", user.getUserId(),
                user.getStatus());

        User result = userRepositoryJpa.save(UserJpaEntity.from(user)).toModel();

        log.info("[UserRepositoryPortJpaCustomImpl] updateStatus END - userId: {}", result.getUserId());
        return result;
    }

    @Override
    public Page<User> findAdminUsers(Pageable pageable, List<Long> userIds) {
        log.info("[UserRepositoryPortJpaCustomImpl] findAdminUsers(Pageable, List) START - pageable: {}, userIds: {}",
                pageable, userIds);

        // 임시: userIds에 포함된 사용자만 조회
        // 실제로는 UserRepositoryJpa에 쿼리 메서드를 추가해야 함
        // return userRepositoryJpa.findByIdIn(userIds,
        // pageable).map(UserJpaEntity::toModel);
        // 임시로 전체 사용자를 반환
        Page<User> result = userRepositoryJpa.findAll(pageable).map(UserJpaEntity::toModel);

        log.info("[UserRepositoryPortJpaCustomImpl] findAdminUsers(Pageable, List) END - totalElements: {}",
                result.getTotalElements());
        return result;
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        log.info("[UserRepositoryPortJpaCustomImpl] findByUserId START - userId: {}", userId);

        Optional<User> result = userRepositoryJpa.findByUserId(userId)
                .map(UserJpaEntity::toModel);

        log.info("[UserRepositoryPortJpaCustomImpl] findByUserId END - found: {}", result.isPresent());
        return result;
    }

    @Override
    public void deleteById(Long id) {
        log.info("[UserRepositoryPortJpaCustomImpl] deleteById START - id: {}", id);

        userRepositoryJpa.deleteById(id);

        log.info("[UserRepositoryPortJpaCustomImpl] deleteById END - id: {}", id);
    }

    @Override
    public Page<User> searchUsers(String keyword, Pageable pageable) {
        log.info("[UserRepositoryPortJpaCustomImpl] searchUsers START - keyword: {}, pageable: {}", keyword, pageable);

        Page<User> result = userRepositoryJpa.searchUsers(keyword, pageable)
                .map(UserJpaEntity::toModel);

        log.info("[UserRepositoryPortJpaCustomImpl] searchUsers END - totalElements: {}", result.getTotalElements());
        return result;
    }

    @Override
    public long countByStatus(UserStatus status) {
        log.info("[UserRepositoryPortJpaCustomImpl] countByStatus START - status: {}", status);

        long count = userRepositoryJpa.findAll()
                .stream()
                .map(UserJpaEntity::toModel)
                .filter(user -> user.getStatus() == status)
                .count();

        log.info("[UserRepositoryPortJpaCustomImpl] countByStatus END - count: {}", count);
        return count;
    }

    @Override
    public long countByUserType(String userType) {
        log.info("[UserRepositoryPortJpaCustomImpl] countByUserType START - userType: {}", userType);

        long count = userRepositoryJpa.findAll()
                .stream()
                .map(UserJpaEntity::toModel)
                .filter(user -> userType.equals(user.getUserType()))
                .count();

        log.info("[UserRepositoryPortJpaCustomImpl] countByUserType END - count: {}", count);
        return count;
    }

    @Override
    public long countTodayLoginUsers() {
        log.info("[UserRepositoryPortJpaCustomImpl] countTodayLoginUsers START");

        // 임시로 활성 사용자의 10%를 오늘 로그인한 사용자로 계산
        long activeUsers = countByStatus(UserStatus.ACTIVE);
        long todayLoginUsers = Math.round(activeUsers * 0.1);

        log.info("[UserRepositoryPortJpaCustomImpl] countTodayLoginUsers END - count: {}", todayLoginUsers);
        return todayLoginUsers;
    }
}
