package com.mahmoud.picturepub.repository;

import com.mahmoud.picturepub.entity.Authority;
import com.mahmoud.picturepub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    List<Authority> findByUser(User user);
}
