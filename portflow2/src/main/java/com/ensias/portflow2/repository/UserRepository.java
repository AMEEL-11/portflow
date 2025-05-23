package com.ensias.portflow2.repository;

import com.ensias.portflow2.model.User;
import com.ensias.portflow2.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByRole(Role role);
    List<User> findByActiveTrue();
    boolean existsByEmail(String email);
}