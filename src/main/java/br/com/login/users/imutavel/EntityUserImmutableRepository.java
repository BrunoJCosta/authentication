package br.com.login.users.imutavel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface EntityUserImmutableRepository extends JpaRepository<EntityUserImmutable, Long> {

    Optional<EntityUserImmutable> findByEmailAndActiveTrue(String email);

}