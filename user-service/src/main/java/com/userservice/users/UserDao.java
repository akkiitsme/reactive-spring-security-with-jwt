package com.userservice.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<UserBean, Integer> {
     UserBean findByEmail(String username);

    //Mono<UserBean> findByEmail(String username);

    Optional<UserBean> findByUserId(int userId);

    List<UserBean> findByUserRole(Role role);

    UserBean findByPhoneNumber(String phoneNumber);

    //to format document press ctrl+alt+L
}
