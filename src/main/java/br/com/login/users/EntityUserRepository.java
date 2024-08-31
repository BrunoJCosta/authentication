package br.com.login.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface EntityUserRepository extends JpaRepository<EntityUser, Long> {

    Optional<EntityUser> findByEmail(String email);
}
