package com.subhankar.repository;
import com.subhankar.model.DO.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserDO, String> {
    boolean existsByUserId(String userId);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    UserDO findByEmail(String email);

    @Query("SELECT u FROM UserDO u WHERE u.email = :userName OR u.userId = :userName")
    UserDO findByEmailOrUserId(String userName);

    @Query("SELECT u FROM UserDO u WHERE u.userId = :userId")
    Optional<UserDO> findByUserId(String userId);
}
