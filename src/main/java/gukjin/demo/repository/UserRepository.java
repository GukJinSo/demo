package gukjin.demo.repository;

import gukjin.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

// 기본적 CRUD
// @Repository 필요없음
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
