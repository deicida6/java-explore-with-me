package ru.practicum.ewm.user.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.user.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> getUsersByIdIn(List<Long> ids, Pageable pageable);

    boolean existsByEmail(String email);

    User getUserById(Long userId);
}