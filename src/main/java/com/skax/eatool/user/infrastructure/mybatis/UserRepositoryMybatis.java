package com.skax.eatool.user.infrastructure.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserRepositoryMybatis {

    Optional<UserDto> findById(Long id);

    Optional<UserDto> findByEmail(String email);

    Long save(UserDto user);

    List<UserDto> findAll();

    Page<UserDto> findAll(Pageable pageable);

    List<UserDto> findAllWithPageable(@Param("offset") long offset, @Param("pageSize") int pageSize);

    long countAll();

    /**
     * 상태별 사용자 수 조회
     */
    long countByStatus(@Param("status") String status);

    /**
     * 사용자 타입별 사용자 수 조회
     */
    long countByUserType(@Param("userType") String userType);

    /**
     * 오늘 로그인한 사용자 수 조회
     */
    long countTodayLoginUsers();
}
