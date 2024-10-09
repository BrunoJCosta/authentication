package br.com.login.configuration.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

interface EntityTokenRepository extends JpaRepository<EntityToken, Long> {

    @Query("select t from EntityToken t where t.token = ?1")
    Optional<EntityToken> findByToken(String token);

    @Query("select t from EntityToken t where t.username = ?1")
    Optional<EntityToken> findByUsername(String username);
}