package br.com.login.configuration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface EntityUserImmutableRepository extends JpaRepository<EntityUserImmutable, Long> {

    Optional<EntityUserImmutable> findByEmail(String email);
}
